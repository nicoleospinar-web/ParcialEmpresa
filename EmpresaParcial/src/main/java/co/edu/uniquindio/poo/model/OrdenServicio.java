package co.edu.uniquindio.poo.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class OrdenServicio {

    private String codigo;
    private LocalDate fechaCreacion;
    private Cliente clienteResponsable;
    private String observacionesGenerales;
    private EstadoOrden estado;
    private ArrayList <DetalleOrdenServicio> detalles;

    public OrdenServicio(String codigo, LocalDate fechaCreacion, Cliente clienteResponsable, String observacionesGenerales) {
        this.codigo = codigo;
        this.fechaCreacion = fechaCreacion;
        this.clienteResponsable = clienteResponsable;
        this.observacionesGenerales = observacionesGenerales;
        this.estado = EstadoOrden.ABIERTA;
        this.detalles = new ArrayList<>();
    }


    private boolean estaCerrada() {
        return estado == EstadoOrden.FINALIZADA || estado == EstadoOrden.ENTREGADA
                || estado == EstadoOrden.CANCELADA;
    }

    // Rechaza detalles repetidos, equipos que no son del cliente y órdenes ya cerradas
    public boolean agregarDetalle(DetalleOrdenServicio detalle) {
        if (detalle == null || estaCerrada() || buscarDetalle(detalle.getCodigo()) != null) {
            return false;
        }
        String codigoEquipo = detalle.getEquipo().getCodigoRegistro();
        if (clienteResponsable.buscarEquipo(codigoEquipo) == null) {
            return false;
        }
        detalles.add(detalle);
        return true;
    }

    public DetalleOrdenServicio buscarDetalle(String codigo) {
        for (DetalleOrdenServicio detalleOrdenServicio : detalles) {
            if (detalleOrdenServicio.getCodigo().equals(codigo)) {
                return detalleOrdenServicio;
            }
        }
        return null;
    }

    // Actualiza el estado de un detalle; la primera intervención en proceso pone la orden EN_PROCESO
    public boolean actualizarEstadoDetalle(String codigoDetalle, EstadoDetalle nuevoEstado) {
        DetalleOrdenServicio detalle = buscarDetalle(codigoDetalle);
        if (detalle == null || !detalle.cambiarEstado(nuevoEstado)) {
            return false;
        }
        if (estado == EstadoOrden.ABIERTA && nuevoEstado == EstadoDetalle.EN_PROCESO) {
            estado = EstadoOrden.EN_PROCESO;
        }
        return true;
    }

    // Se finaliza si todos los detalles no cancelados están finalizados (y hay al menos uno)
    public boolean finalizar() {
        if (estaCerrada()) {
            return false;
        }
        boolean hayFinalizados = false;
        for (DetalleOrdenServicio d : detalles) {
            if (d.getEstado() == EstadoDetalle.CANCELADO) {
                continue;
            }
            if (d.getEstado() != EstadoDetalle.FINALIZADO) {
                return false;
            }
            hayFinalizados = true;
        }
        if (!hayFinalizados) {
            return false;
        }
        estado = EstadoOrden.FINALIZADA;
        return true;
    }

    // Suma el costo de cada intervención, sin contar las canceladas
    public double calcularTotal() {
        double total = 0;
        for (DetalleOrdenServicio detalleOrdenServicio : detalles) {
            if (detalleOrdenServicio.getEstado() != EstadoDetalle.CANCELADO) {
                total += detalleOrdenServicio.calcularCosto();
            }
        }
        return total;
    }

    // Texto con la orden, sus equipos, servicios, estados y valores
    public String generarResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("Orden ").append(codigo).append(" | ").append(fechaCreacion)
                .append(" | Estado: ").append(estado).append("\n");
        if (detalles.isEmpty()) {
            sb.append("  (sin detalles)\n");
        }
        for (DetalleOrdenServicio d : detalles) {
            EquipoTecnologico e = d.getEquipo();
            sb.append("  - ").append(d.getCodigo()).append(" | ")
                    .append(e.getCodigoRegistro()).append(" ").append(e.getMarca()).append(" ")
                    .append(e.getModelo()).append(" | ").append(d.getServicio().obtenerTipo())
                    .append(" | ").append(d.getEstado())
                    .append(" | ").append(String.format("$%,.0f", d.calcularCosto())).append("\n");
        }
        sb.append("  Total: ").append(String.format("$%,.0f", calcularTotal()));
        return sb.toString();
    }

    // Descripción corta de un detalle, con el código de la orden a la que pertenece
    public String describirDetalle(DetalleOrdenServicio d) {
        EquipoTecnologico e = d.getEquipo();
        return "[" + codigo + "/" + d.getCodigo() + "] " + e.getCodigoRegistro() + " "
                + e.getMarca() + " " + e.getModelo() + " | " + d.getServicio().obtenerTipo()
                + " | " + String.format("$%,.0f", d.calcularCosto());
    }

    public String getCodigo() { return codigo; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public Cliente getClienteResponsable() { return clienteResponsable; }
    public String getObservacionesGenerales() { return observacionesGenerales; }
    public EstadoOrden getEstado() { return estado; }
    public ArrayList<DetalleOrdenServicio> getDetalles() { return new ArrayList<>(detalles); }

    public void setEstado(EstadoOrden estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "OrdenServicio{" +
                "codigo='" + codigo + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", clienteResponsable=" + clienteResponsable +
                ", observacionesGenerales='" + observacionesGenerales + '\'' +
                ", estado=" + estado +
                ", detalles=" + detalles +
                '}';
    }
}
