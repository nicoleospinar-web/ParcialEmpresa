package co.edu.uniquindio.poo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Empresa {
    public static final int LIMITE_INVENTARIO_CRITICO = 5;
    private String nombre;
    private String nit;
    private Map<String, EquipoTecnologico> hashMaplistaEquipos = new HashMap<>();
    private Map<String, Cliente> hashMapclientes = new HashMap<> ();      // clave: número de documento
    private Map<String, Tecnico> hashMaptecnicos = new HashMap<>();      // clave: código
    private Map<String, Servicio> hashMapservicios = new HashMap<>();    // clave: código
    private Map<String, Repuesto> hashMaprepuestos = new HashMap<>();    // clave: código
    private Map<String, OrdenServicio> hashMapordenes = new HashMap<>(); // clave: código

    public Empresa(String nombre, String nit) {
        this.nombre = nombre;
        this.nit = nit;
    }

    private static boolean textoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    //------- FUNCIONALIDADES--------

    // 1. Registrar y buscar cliente
    public boolean registrarCliente (Cliente cliente) {
        if ( cliente == null || hashMapclientes.containsKey(cliente.getNumeroDocumento())) {
            return false;
        }
        hashMapclientes.put(cliente.getNumeroDocumento(), cliente);
        return true;
        }

    public Cliente buscarCliente (String numeroDocumento) {
        return hashMapclientes.get (numeroDocumento);
    }

    // 2. Registrar Equipo
    // El código de registro es único en toda la empresa
    public EquipoTecnologico buscarEquipo(String codigoRegistro) {
        for (Cliente cliente : hashMapclientes.values()) {
            EquipoTecnologico equipo = cliente.buscarEquipo(codigoRegistro);
            if (equipo != null) {
                return equipo;
            }
        }
        return null;
    }

    public boolean registrarEquipo (String documentoCliente, EquipoTecnologico equipo) {
        Cliente cliente = buscarCliente(documentoCliente);
        if (cliente == null || equipo == null || buscarEquipo(equipo.getCodigoRegistro()) != null) {
            return false;
        }
        return cliente.agregarEquipo(equipo);
    }

    // 3. Registrar Tecnico
    public boolean registrarTecnico (Tecnico tecnico) {
        if ( tecnico == null || hashMaptecnicos.containsKey(tecnico.getCodigo())) {
            return false;
        }
        hashMaptecnicos.put(tecnico.getCodigo(), tecnico);
        return true;
    }

    public Tecnico buscarTecnico (String codigo) {
        return hashMaptecnicos.get (codigo);
    }

    // 4. Registrar repuesto
    public boolean registrarRepuesto (Repuesto repuesto) {
        if (repuesto == null || hashMaprepuestos.containsKey(repuesto.getCodigo())) {
            return false;
        }
        hashMaprepuestos.put(repuesto.getCodigo(), repuesto);
        return true;
    }

        public Repuesto buscarRepuesto (String codigo) {
            return hashMaprepuestos.get(codigo);
        }


    // Servicio (Catalogo)
    public boolean registrarServicio (Servicio servicio) {
        if (servicio == null || !textoValido (servicio.getCodigo())
                || hashMapservicios.containsKey(servicio.getCodigo())) {
            return false;
        }
        hashMapservicios.put(servicio.getCodigo(), servicio);
        return true;
    }

    public Servicio buscarServicio (String codigo) {
        return hashMapservicios.get(codigo);
    }


    // 5. Crear orden de servicio
    // Crea una orden ABIERTA para un cliente existente
    public boolean crearOrden (String codigoOrden, String documentoCliente, String observaciones) {
        Cliente cliente = buscarCliente(documentoCliente);
        if (cliente == null || !textoValido(codigoOrden) || hashMapservicios.containsKey(codigoOrden)) {
            return false;
        }
        hashMapordenes.put(codigoOrden, new OrdenServicio (codigoOrden, LocalDate.now(), cliente, observaciones));
        return true;
    }

    public OrdenServicio buscarOrden (String codigo) {
        return hashMapordenes.get(codigo);
    }

    private DetalleOrdenServicio buscarDetalle (String codigoOrden, String codigoDetalle) {
        OrdenServicio orden = buscarOrden(codigoOrden);
        if (orden == null) {
            return null;
        }
        return orden.buscarDetalle(codigoDetalle);
    }

    // 6. Agregar detalle a una orden
    // El equipo debe ser del cliente de la orden
    public boolean agregarDetalleAOrden (String codigoOrden, String codigoDetalle,
                                        String codigoEquipo, String codigoServicio,
                                        String descripcionFalla) {
        OrdenServicio orden = buscarOrden(codigoOrden);
        Servicio servicio = buscarServicio(codigoServicio);
        if (orden == null || servicio == null
                || !textoValido(codigoDetalle) || !textoValido(descripcionFalla)) {
            return false;
        }
        EquipoTecnologico equipo = orden.getClienteResponsable().buscarEquipo(codigoEquipo);
        if (equipo == null) {
            return false;
        }
        return orden.agregarDetalle(new DetalleOrdenServicio(codigoDetalle, equipo, servicio, descripcionFalla));
    }

    // 7. Asignar técnico
    public boolean asignarTecnico (String codigoOrden, String codigoDetalle, String codigoTecnico) {
        DetalleOrdenServicio detalle = buscarDetalle(codigoOrden, codigoDetalle);
        Tecnico tecnico = buscarTecnico(codigoTecnico);
        if (detalle == null || tecnico == null) {
            return false;
        }
        return detalle.asignarTecnico(tecnico);
    }

    // Horas de trabajo y observaciones técnicas de una intervención
    public boolean registrarTrabajoDetalle (String codigoOrden, String codigoDetalle,
                                           double horas, String observaciones) {
        DetalleOrdenServicio detalle = buscarDetalle(codigoOrden, codigoDetalle);
        if (detalle == null) {
            return false;
        }
        return detalle.registrarTrabajo(horas, observaciones);
    }

    // 8. Registrar repuestos utilizados
    public boolean registrarRepuestoUtilizado (String codigoOrden, String codigoDetalle,
                                              String codigoRepuesto, int cantidad) {
        DetalleOrdenServicio detalle = buscarDetalle(codigoOrden, codigoDetalle);
        Repuesto repuesto = buscarRepuesto(codigoRepuesto);
        if (detalle == null || repuesto == null) {
            return false;
        }
        return detalle.agregarRepuesto(repuesto, cantidad);
    }

    //  9. Calcular valor de un detalle
    // Devuelve -1 si la orden o el detalle no existen
    public double calcularValorDetalle (String codigoOrden, String codigoDetalle) {
        DetalleOrdenServicio detalle = buscarDetalle(codigoOrden, codigoDetalle);
        if (detalle == null) {
            return -1;
        }
        return detalle.calcularCosto();
    }

    //  10. Calcular valor total de la orden
    // Devuelve -1 si la orden no existe
    public double calcularTotalOrden (String codigoOrden) {
        OrdenServicio orden = buscarOrden(codigoOrden);
        if (orden == null) {
            return -1;
        }
        return orden.calcularTotal();
    }

    //  11. Actualizar estado de un detalle
    public boolean actualizarEstadoDetalle (String codigoOrden, String codigoDetalle,
                                           EstadoDetalle nuevoEstado) {
        OrdenServicio orden = buscarOrden(codigoOrden);
        if (orden == null || nuevoEstado == null) {
            return false;
        }
        return orden.actualizarEstadoDetalle(codigoDetalle, nuevoEstado);
    }

    //  12. Finalizar una orden
    public boolean finalizarOrden(String codigoOrden) {
        OrdenServicio orden = buscarOrden(codigoOrden);
        if (orden == null) {
            return false;
        }
        return orden.finalizar();
    }

    //  13. Consultar órdenes de un cliente
    public ArrayList<OrdenServicio> consultarOrdenesDeCliente(String documentoCliente) {
        ArrayList<OrdenServicio> resultado = new ArrayList<>();
        for (OrdenServicio ordenServicio : hashMapordenes.values()) {
            if (ordenServicio.getClienteResponsable().getNumeroDocumento().equals(documentoCliente)) {
                resultado.add(ordenServicio);
            }
        }
        return resultado;
    }

    //  14. Consultar trabajos de un técnico
    // Devuelve los detalles del técnico agrupados por estado (null si el técnico no existe)
    public HashMap<EstadoDetalle, ArrayList<String>> consultarTrabajosDeTecnico(String codigoTecnico) {
        if (buscarTecnico(codigoTecnico) == null) {
            return null;
        }
        HashMap<EstadoDetalle, ArrayList<String>> agrupados = new HashMap<>();
        for (EstadoDetalle estadoDetalle : EstadoDetalle.values()) {
            agrupados.put(estadoDetalle, new ArrayList<>());
        }
        for (OrdenServicio orden : hashMapordenes.values()) {
            for (DetalleOrdenServicio detalleOrdenServicio : orden.getDetalles()) {
                Tecnico tecnico = detalleOrdenServicio.getTecnicoResponsable();
                if (tecnico != null && tecnico.getCodigo().equals(codigoTecnico)) {
                    agrupados.get(detalleOrdenServicio.getEstado()).add(orden.describirDetalle(detalleOrdenServicio));
                }
            }
        }
        return agrupados;
    }

    //  15. Consultar equipos pendientes
    // Equipos con alguna intervención PENDIENTE o EN_PROCESO (sin repetir equipos)
    public ArrayList<EquipoTecnologico> consultarEquiposPendientes() {
        HashMap<String, EquipoTecnologico> pendientes = new LinkedHashMap<>();
        for (OrdenServicio orden : hashMapordenes.values()) {
            for (DetalleOrdenServicio detalleOrdenServicio : orden.getDetalles()) {
                if (detalleOrdenServicio.getEstado() == EstadoDetalle.PENDIENTE
                        || detalleOrdenServicio.getEstado() == EstadoDetalle.EN_PROCESO) {
                    pendientes.put(detalleOrdenServicio.getEquipo().getCodigoRegistro(), detalleOrdenServicio.getEquipo());
                }
            }
        }
        return new ArrayList<>(pendientes.values());
    }

    // 16. Reporte de servicios
    // Cantidad y total por tipo de servicio, y total facturado por categoría de equipo
    // (no se cuentan las intervenciones canceladas)
    public String generarReporteServicios() {
        HashMap<String, Integer> cantidadPorTipo = new LinkedHashMap<>();
        HashMap<String, Double> totalPorTipo = new LinkedHashMap<>();
        HashMap<CategoriaEquipo, Double> totalPorCategoria = new LinkedHashMap<>();

        for (Servicio servicio : hashMapservicios.values()) {
            cantidadPorTipo.put(servicio.obtenerTipo(), 0);
            totalPorTipo.put(servicio.obtenerTipo(), 0.0);
        }
        for (CategoriaEquipo categoriaEquipo : CategoriaEquipo.values()) {
            totalPorCategoria.put(categoriaEquipo, 0.0);
        }

        for (OrdenServicio orden : hashMapordenes.values()) {
            for (DetalleOrdenServicio detalleOrdenServicio : orden.getDetalles()) {
                if (detalleOrdenServicio.getEstado() == EstadoDetalle.CANCELADO) {
                    continue;
                }
                String tipo = detalleOrdenServicio.getServicio().obtenerTipo();
                double costo = detalleOrdenServicio.calcularCosto();
                cantidadPorTipo.put(tipo, cantidadPorTipo.getOrDefault(tipo, 0) + 1);
                totalPorTipo.put(tipo, totalPorTipo.getOrDefault(tipo, 0.0) + costo);
                CategoriaEquipo categoria = detalleOrdenServicio.getEquipo().obtenerCategoria();
                totalPorCategoria.put(categoria, totalPorCategoria.get(categoria) + costo);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("--- Servicios por tipo ---\n");
        for (String tipo : cantidadPorTipo.keySet()) {
            sb.append(tipo).append(": ").append(cantidadPorTipo.get(tipo)).append(" servicio(s) | ")
                    .append(String.format("$%,.0f", totalPorTipo.get(tipo))).append("\n");
        }
        sb.append("--- Total facturado por categoría de equipo ---\n");
        for (CategoriaEquipo categoria : totalPorCategoria.keySet()) {
            sb.append(categoria).append(": ")
                    .append(String.format("$%,.0f", totalPorCategoria.get(categoria))).append("\n");
        }
        return sb.toString();
    }

    // 17. Reporte de técnicos
    // Intervenciones finalizadas por técnico y valor de los servicios atendidos
    public String generarReporteTecnicos() {
        HashMap<String, Integer> finalizadas = new LinkedHashMap<>();
        HashMap<String, Double> valores = new LinkedHashMap<>();
        for (Tecnico tecnico : hashMaptecnicos.values()) {
            finalizadas.put(tecnico.getCodigo(), 0);
            valores.put(tecnico.getCodigo(), 0.0);
        }

        for (OrdenServicio orden : hashMapordenes.values()) {
            for (DetalleOrdenServicio d : orden.getDetalles()) {
                Tecnico tecnico = d.getTecnicoResponsable();
                if (tecnico != null && d.getEstado() == EstadoDetalle.FINALIZADO) {
                    finalizadas.put(tecnico.getCodigo(), finalizadas.get(tecnico.getCodigo()) + 1);
                    valores.put(tecnico.getCodigo(), valores.get(tecnico.getCodigo()) + d.calcularCosto());
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Tecnico tecnico : hashMaptecnicos.values()) {
            sb.append(tecnico.getCodigo()).append(" ").append(tecnico.getNombre()).append(": ")
                    .append(finalizadas.get(tecnico.getCodigo())).append(" intervención(es) finalizada(s) | ")
                    .append(String.format("$%,.0f", valores.get(tecnico.getCodigo()))).append("\n");
        }
        if (hashMaptecnicos.isEmpty()) {
            sb.append("No hay técnicos registrados.\n");
        }
        return sb.toString();
    }

    // 18. Consultar inventario crítico
    public ArrayList<Repuesto> consultarInventarioCritico() {
        ArrayList<Repuesto> criticos = new ArrayList<>();
        for (Repuesto repuesto : hashMaprepuestos.values()) {
            if (repuesto.getCantidadDisponible() < LIMITE_INVENTARIO_CRITICO) {
                criticos.add(repuesto);
            }
        }
        return criticos;
    }
    public ArrayList<Cliente> getClientes() { return new ArrayList<>(hashMapclientes.values()); }
    public ArrayList<Tecnico> getTecnicos() { return new ArrayList<>(hashMaptecnicos.values()); }
    public ArrayList<Servicio> getServicios() { return new ArrayList<>(hashMapservicios.values()); }
    public ArrayList<Repuesto> getRepuestos() { return new ArrayList<>(hashMaprepuestos.values()); }
    public ArrayList<OrdenServicio> getOrdenes() { return new ArrayList<>(hashMapordenes.values()); }

    public String getNombre() { return nombre; }
    public String getNit() { return nit; }
}





