package co.edu.uniquindio.poo.model;

import java.util.ArrayList;

public class DetalleOrdenServicio {

    private final String codigo;
    private EquipoTecnologico equipo;
    private Servicio servicio;
    private String descripcionFalla;
    private Tecnico tecnicoResponsable; // null hasta que se asigne
    private double horasTrabajo;
    private String observacionesTecnicas;
    private EstadoDetalle estado;
    private ArrayList<DetalleReparacion> repuestosUsados;

    public DetalleOrdenServicio(String codigo, EquipoTecnologico equipo, Servicio servicio,
                                String descripcionFalla) {
        this.codigo = codigo;
        this.equipo = equipo;
        this.servicio = servicio;
        this.descripcionFalla = descripcionFalla;
        this.tecnicoResponsable = null;
        this.horasTrabajo = 0;
        this.observacionesTecnicas = "";
        this.estado = EstadoDetalle.PENDIENTE;
        this.repuestosUsados = new ArrayList<>();
    }

    // Un detalle finalizado o cancelado ya no se modifica
    private boolean estaCerrado() {

        return estado == EstadoDetalle.FINALIZADO || estado == EstadoDetalle.CANCELADO;
    }

    // Asigna el técnico solo si está activo y su especialidad le permite atender el equipo
    public boolean asignarTecnico (Tecnico tecnico) {
        if (tecnico == null || estaCerrado()) {
            return false;
        }
        if (!tecnico.estaActivo() || !tecnico.puedeAtender(equipo)) {
            return false;
        }
        this.tecnicoResponsable = tecnico;
        return true;
    }

    public boolean registrarTrabajo(double horas, String observaciones) {
        if (horas < 0 || estaCerrado()) {
            return false;
        }
        this.horasTrabajo = horas;
        this.observacionesTecnicas = observaciones;
        return true;
    }

    // Para iniciar la intervención debe tener técnico asignado.
    public boolean cambiarEstado(EstadoDetalle nuevoEstado) {
        boolean permitido = false;
        if (estado == EstadoDetalle.PENDIENTE) {
            permitido = (nuevoEstado == EstadoDetalle.EN_PROCESO && tecnicoResponsable != null)
                    || nuevoEstado == EstadoDetalle.CANCELADO;
        } else if (estado == EstadoDetalle.EN_PROCESO) {
            permitido = nuevoEstado == EstadoDetalle.FINALIZADO
                    || nuevoEstado == EstadoDetalle.CANCELADO;
        }
        if (permitido) {
            estado = nuevoEstado;
        }
        return permitido;
    }

    // El costo lo calcula el servicio según sus propias reglas
    public double calcularCosto() {

        return servicio.calcularCostoFinal(this);
    }

    public double calcularCostoRepuestos() {
        double total = 0;
        for (DetalleReparacion detalleReparacion : repuestosUsados) {
            total += detalleReparacion.calcularSubtotal();
        }
        return total;
    }

    // Asigna un repuesto a esta intervención y lo descuenta del inventario
    public boolean agregarRepuesto(Repuesto repuesto, int cantidad) {
        if (repuesto == null || estaCerrado() || !servicio.admiteRepuestos()) {
            return false;
        }
        if (repuesto.getCategoriaCompatibilidad() != equipo.obtenerCategoria()) {
            return false;
        }
        if (!repuesto.descontar(cantidad)) { // valida disponibilidad y cantidades negativas
            return false;
        }
        repuestosUsados.add(new DetalleReparacion(repuesto, cantidad));
        return true;
    }

    public String getCodigo() { return codigo; }
    public EquipoTecnologico getEquipo() { return equipo; }
    public Servicio getServicio() { return servicio; }
    public String getDescripcionFalla() { return descripcionFalla; }
    public Tecnico getTecnicoResponsable() { return tecnicoResponsable; }
    public double getHorasTrabajo() { return horasTrabajo; }
    public String getObservacionesTecnicas() { return observacionesTecnicas; }
    public EstadoDetalle getEstado() { return estado; }
    public ArrayList<DetalleReparacion> getRepuestosUsados() {
        return new ArrayList<>(repuestosUsados);
    }

    @Override
    public String toString() {
        return "DetalleOrdenServicio{" +
                "codigo='" + codigo + '\'' +
                ", equipo=" + equipo +
                ", servicio=" + servicio +
                ", descripcionFalla='" + descripcionFalla + '\'' +
                ", tecnicoResponsable=" + tecnicoResponsable +
                ", horasTrabajo=" + horasTrabajo +
                ", observacionesTecnicas='" + observacionesTecnicas + '\'' +
                ", estado=" + estado +
                ", repuestosUsados=" + repuestosUsados +
                '}';
    }
}
