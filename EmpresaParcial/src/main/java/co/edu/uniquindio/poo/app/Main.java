package co.edu.uniquindio.poo.app;
import co.edu.uniquindio.poo.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Empresa empresa = new Empresa("TechRepair", "900123456-1");
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            System.out.println();
            ejecutarOpcion(opcion, empresa);
            System.out.println();
        } while (opcion != 0);
        System.out.println("Hasta luego.");
    }

    // ===================== Menú =====================
    private static void mostrarMenu() {
        System.out.println("========== TECHREPAIR ==========");
        System.out.println(" 1. Registrar cliente");
        System.out.println(" 2. Registrar equipo");
        System.out.println(" 3. Registrar técnico");
        System.out.println(" 4. Registrar repuesto");
        System.out.println(" 5. Crear orden de servicio");
        System.out.println(" 6. Agregar detalle a una orden");
        System.out.println(" 7. Asignar técnico a un detalle");
        System.out.println(" 8. Registrar repuestos utilizados");
        System.out.println(" 9. Calcular valor de un detalle");
        System.out.println("10. Calcular valor total de la orden");
        System.out.println("11. Actualizar estado de un detalle");
        System.out.println("12. Finalizar una orden");
        System.out.println("13. Consultar órdenes de un cliente");
        System.out.println("14. Consultar trabajos de un técnico");
        System.out.println("15. Consultar equipos pendientes");
        System.out.println("16. Reporte de servicios");
        System.out.println("17. Reporte de técnicos");
        System.out.println("18. Consultar inventario crítico");
        System.out.println("19. Registrar horas y observaciones de un detalle");
        System.out.println("20. Ver listados (clientes, técnicos, servicios, repuestos, órdenes)");
        System.out.println("21. Cargar datos de ejemplo");
        System.out.println(" 0. Salir");
        System.out.println("================================");
    }

    private static void ejecutarOpcion(int opcion, Empresa empresa) {
        switch (opcion) {
            case 1: registrarCliente(empresa); break;
            case 2: registrarEquipo(empresa); break;
            case 3: registrarTecnico(empresa); break;
            case 4: registrarRepuesto(empresa); break;
            case 5: crearOrden(empresa); break;
            case 6: agregarDetalle(empresa); break;
            case 7: asignarTecnico(empresa); break;
            case 8: registrarRepuestoUtilizado(empresa); break;
            case 9: calcularValorDetalle(empresa); break;
            case 10: calcularTotalOrden(empresa); break;
            case 11: actualizarEstadoDetalle(empresa); break;
            case 12: finalizarOrden(empresa); break;
            case 13: consultarOrdenesCliente(empresa); break;
            case 14: consultarTrabajosTecnico(empresa); break;
            case 15: consultarEquiposPendientes(empresa); break;
            case 16: System.out.print(empresa.generarReporteServicios()); break;
            case 17: System.out.print(empresa.generarReporteTecnicos()); break;
            case 18: consultarInventarioCritico(empresa); break;
            case 19: registrarTrabajoDetalle(empresa); break;
            case 20: verListados(empresa); break;
            case 21: cargarDatosEjemplo(empresa); break;
            case 0: break;
            default: System.out.println("Opción no válida.");
        }
    }

    // ===================== Lectura de datos =====================
    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    private static int leerEnRango(String mensaje, int minimo, int maximo) {
        while (true) {
            int valor = leerEntero(mensaje);
            if (valor >= minimo && valor <= maximo) {
                return valor;
            }
            System.out.println("Elija un número entre " + minimo + " y " + maximo + ".");
        }
    }

    private static double leerDecimal(String mensaje) {
        while (true) {
            try {
                return Double.parseDouble(leerTexto(mensaje).replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private static boolean leerSiNo(String mensaje) {
        while (true) {
            String respuesta = leerTexto(mensaje + " (s/n): ").toLowerCase();
            if (respuesta.equals("s")) {
                return true;
            }
            if (respuesta.equals("n")) {
                return false;
            }
            System.out.println("Responda s o n.");
        }
    }

    private static void imprimirResultado(boolean exito, String mensajeExito, String mensajeError) {
        System.out.println(exito ? mensajeExito : mensajeError);
    }

    private static <T> void imprimirLista(String titulo, ArrayList<T> lista) {
        System.out.println("--- " + titulo + " ---");
        if (lista.isEmpty()) {
            System.out.println("(ninguno)");
        }
        for (T elemento : lista) {
            System.out.println(elemento);
        }
    }

    // ===================== Opciones =====================
    private static void registrarCliente(Empresa empresa) {
        String documento = leerTexto("Número de documento: ");
        String nombre = leerTexto("Nombre completo: ");
        String telefono = leerTexto("Teléfono: ");
        String correo = leerTexto("Correo electrónico: ");
        String direccion = leerTexto("Dirección de residencia: ");
        Cliente cliente = new Cliente (documento, nombre, telefono, correo, direccion);
        imprimirResultado(empresa.registrarCliente(cliente),
                "Cliente registrado.",
                "No se pudo registrar: datos inválidos (documento numérico, teléfono de 7 a 10 dígitos, "
                        + "correo válido, campos obligatorios) o documento repetido.");
    }

    private static void registrarEquipo(Empresa empresa) {
        String documento = leerTexto("Documento del cliente dueño del equipo: ");
        if (empresa.buscarCliente(documento) == null) {
            System.out.println("El cliente no existe.");
            return;
        }
        System.out.println("Categoría: 1. Portátil  2. Escritorio  3. Teléfono inteligente");
        int categoria = leerEnRango("Categoría: ", 1, 3);

        String codigo = leerTexto("Código de registro: ");
        String serie = leerTexto("Número de serie: ");
        String marca = leerTexto("Marca: ");
        String modelo = leerTexto("Modelo: ");
        int anio = leerEntero("Año de fabricación: ");
        String estadoFisico = leerTexto("Descripción del estado físico: ");
        int almacenamiento = leerEntero("Capacidad de almacenamiento (GB): ");

        EquipoTecnologico equipo;
        if (categoria == 1) {
            double pulgadas = leerDecimal("Tamaño de pantalla (pulgadas): ");
            int ram = leerEntero("Memoria RAM (GB): ");
            boolean extraible = leerSiNo("¿Batería extraíble?");
            equipo = new ComputadorPortatil(codigo, serie, marca, modelo, anio, estadoFisico,
                    almacenamiento, pulgadas, ram, extraible);
        } else if (categoria == 2) {
            String procesador = leerTexto("Tipo de procesador: ");
            int ram = leerEntero("Memoria RAM (GB): ");
            boolean grafica = leerSiNo("¿Tarjeta gráfica dedicada?");
            equipo = new ComputadorEscritorio(codigo, serie, marca, modelo, anio, estadoFisico,
                    almacenamiento, procesador, ram, grafica);
        } else {
            String sistema = leerTexto("Sistema operativo: ");
            int bateria = leerEntero("Capacidad de batería (mAh): ");
            boolean cincoG = leerSiNo("¿Compatible con 5G?");
            equipo = new TelefonoInteligente(codigo, serie, marca, modelo, anio, estadoFisico,
                    almacenamiento, sistema, bateria, cincoG);
        }
        imprimirResultado(empresa.registrarEquipo(documento, equipo),
                "Equipo registrado.",
                "No se pudo registrar: datos inválidos, o código de registro / número de serie repetido.");
    }

    private static void registrarTecnico(Empresa empresa) {
        String codigo = leerTexto("Código: ");
        String nombre = leerTexto("Nombre: ");
        String documento = leerTexto("Documento de identificación: ");
        System.out.println("Especialidad: 1. Computadores  2. Dispositivos móviles  3. Soporte general");
        int esp = leerEnRango("Especialidad: ", 1, 3);
        Especialidad especialidad = Especialidad.values()[esp - 1];
        System.out.println("Estado laboral: 1. Activo  2. Inactivo");
        int est = leerEnRango("Estado laboral: ", 1, 2);
        EstadoLaboral estado = EstadoLaboral.values()[est - 1];
        Tecnico tecnico = new Tecnico(codigo, nombre, documento, estado, especialidad);
        imprimirResultado(empresa.registrarTecnico(tecnico),
                "Técnico registrado.",
                "No se pudo registrar: datos incompletos o código repetido.");
    }

    private static void registrarRepuesto(Empresa empresa) {
        String codigo = leerTexto("Código: ");
        String nombre = leerTexto("Nombre: ");
        String descripcion = leerTexto("Descripción: ");
        double precio = leerDecimal("Precio unitario: ");
        int cantidad = leerEntero("Cantidad inicial en inventario: ");
        System.out.println("Categoría de compatibilidad: 1. Portátil  2. Escritorio  3. Teléfono");
        int cat = leerEnRango("Categoría: ", 1, 3);
        CategoriaEquipo categoria = CategoriaEquipo.values()[cat - 1];
        try {
            Repuesto repuesto = new Repuesto(codigo, nombre, descripcion, precio, cantidad, categoria);
            imprimirResultado(empresa.registrarRepuesto(repuesto),
                    "Repuesto registrado.", "No se pudo registrar: el código ya existe.");
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo registrar: " + e.getMessage());
        }
    }

    private static void crearOrden(Empresa empresa) {
        String codigo = leerTexto("Código de la orden: ");
        String documento = leerTexto("Documento del cliente: ");
        String observaciones = leerTexto("Observaciones generales: ");
        imprimirResultado(empresa.crearOrden(codigo, documento, observaciones),
                "Orden creada en estado ABIERTA.",
                "No se pudo crear: el cliente no existe, el código está vacío o ya existe.");
    }

    private static void agregarDetalle(Empresa empresa) {
        String codigoOrden = leerTexto("Código de la orden: ");
        OrdenServicio orden = empresa.buscarOrden(codigoOrden);
        if (orden == null) {
            System.out.println("La orden no existe.");
            return;
        }
        imprimirLista("Equipos del cliente " + orden.getClienteResponsable().getNombreCompleto(),
                orden.getClienteResponsable().getEquipos());
        imprimirLista("Servicios disponibles", empresa.getServicios());
        String codigoDetalle = leerTexto("Código del detalle (único en la orden): ");
        String codigoEquipo = leerTexto("Código del equipo: ");
        String codigoServicio = leerTexto("Código del servicio: ");
        String falla = leerTexto("Falla o necesidad reportada: ");
        imprimirResultado(empresa.agregarDetalleAOrden(codigoOrden, codigoDetalle, codigoEquipo,
                        codigoServicio, falla),
                "Detalle agregado.",
                "No se pudo agregar: revise que el equipo sea del cliente, el servicio exista, "
                        + "el código del detalle no se repita y la orden no esté cerrada.");
    }

    private static void asignarTecnico(Empresa empresa) {
        String codigoOrden = leerTexto("Código de la orden: ");
        String codigoDetalle = leerTexto("Código del detalle: ");
        imprimirLista("Técnicos", empresa.getTecnicos());
        String codigoTecnico = leerTexto("Código del técnico: ");
        imprimirResultado(empresa.asignarTecnico(codigoOrden, codigoDetalle, codigoTecnico),
                "Técnico asignado.",
                "No se pudo asignar: el técnico debe estar activo y su especialidad debe permitir "
                        + "atender ese equipo; el detalle no debe estar cerrado.");
    }

    private static void registrarRepuestoUtilizado(Empresa empresa) {
        String codigoOrden = leerTexto("Código de la orden: ");
        String codigoDetalle = leerTexto("Código del detalle: ");
        imprimirLista("Repuestos", empresa.getRepuestos());
        String codigoRepuesto = leerTexto("Código del repuesto: ");
        int cantidad = leerEntero("Cantidad: ");
        imprimirResultado(empresa.registrarRepuestoUtilizado(codigoOrden, codigoDetalle,
                        codigoRepuesto, cantidad),
                "Repuesto registrado en la reparación y descontado del inventario.",
                "No se pudo registrar: debe ser una reparación correctiva abierta, el repuesto debe ser "
                        + "compatible con el equipo y la cantidad debe ser positiva y no superar las existencias.");
    }

    private static void calcularValorDetalle(Empresa empresa) {
        String codigoOrden = leerTexto("Código de la orden: ");
        String codigoDetalle = leerTexto("Código del detalle: ");
        double valor = empresa.calcularValorDetalle(codigoOrden, codigoDetalle);
        if (valor < 0) {
            System.out.println("La orden o el detalle no existen.");
        } else {
            System.out.println("Valor del detalle: " + String.format("$%,.0f", valor));
        }
    }

    private static void calcularTotalOrden(Empresa empresa) {
        String codigoOrden = leerTexto("Código de la orden: ");
        double total = empresa.calcularTotalOrden(codigoOrden);
        if (total < 0) {
            System.out.println("La orden no existe.");
        } else {
            System.out.println("Valor total de la orden: " + String.format("$%,.0f", total));
        }
    }

    private static void actualizarEstadoDetalle(Empresa empresa) {
        String codigoOrden = leerTexto("Código de la orden: ");
        String codigoDetalle = leerTexto("Código del detalle: ");
        System.out.println("Nuevo estado: 1. En proceso  2. Finalizado  3. Cancelado");
        int opcion = leerEnRango("Nuevo estado: ", 1, 3);
        EstadoDetalle nuevo = EstadoDetalle.values()[opcion];
        imprimirResultado(empresa.actualizarEstadoDetalle(codigoOrden, codigoDetalle, nuevo),
                "Estado actualizado.",
                "No se pudo actualizar: el flujo es PENDIENTE -> EN_PROCESO (requiere técnico) -> FINALIZADO; "
                        + "se puede cancelar mientras no esté cerrado.");
    }

    private static void finalizarOrden(Empresa empresa) {
        String codigoOrden = leerTexto("Código de la orden: ");
        imprimirResultado(empresa.finalizarOrden(codigoOrden),
                "Orden finalizada.",
                "No se pudo finalizar: todos los detalles (no cancelados) deben estar finalizados.");
    }

    private static void consultarOrdenesCliente(Empresa empresa) {
        String documento = leerTexto("Documento del cliente: ");
        if (empresa.buscarCliente(documento) == null) {
            System.out.println("El cliente no existe.");
            return;
        }
        ArrayList<OrdenServicio> ordenes = empresa.consultarOrdenesDeCliente(documento);
        if (ordenes.isEmpty()) {
            System.out.println("El cliente no tiene órdenes.");
        }
        for (OrdenServicio orden : ordenes) {
            System.out.println(orden.generarResumen());
        }
    }

    private static void consultarTrabajosTecnico(Empresa empresa) {
        String codigoTecnico = leerTexto("Código del técnico: ");
        HashMap<EstadoDetalle, ArrayList<String>> trabajos =
                empresa.consultarTrabajosDeTecnico(codigoTecnico);
        if (trabajos == null) {
            System.out.println("El técnico no existe.");
            return;
        }
        for (EstadoDetalle estado : EstadoDetalle.values()) {
            imprimirLista(estado.toString(), trabajos.get(estado));
        }
    }

    private static void consultarEquiposPendientes(Empresa empresa) {
        imprimirLista("Equipos con intervenciones sin finalizar", empresa.consultarEquiposPendientes());
    }

    private static void consultarInventarioCritico(Empresa empresa) {
        imprimirLista("Repuestos con menos de " + Empresa.LIMITE_INVENTARIO_CRITICO + " unidades",
                empresa.consultarInventarioCritico());
    }

    private static void registrarTrabajoDetalle(Empresa empresa) {
        String codigoOrden = leerTexto("Código de la orden: ");
        String codigoDetalle = leerTexto("Código del detalle: ");
        double horas = leerDecimal("Horas de trabajo: ");
        String observaciones = leerTexto("Observaciones técnicas: ");
        imprimirResultado(empresa.registrarTrabajoDetalle(codigoOrden, codigoDetalle, horas, observaciones),
                "Trabajo registrado.",
                "No se pudo registrar: el detalle no existe, está cerrado o las horas son negativas.");
    }

    private static void verListados(Empresa empresa) {
        imprimirLista("Clientes", empresa.getClientes());
        imprimirLista("Técnicos", empresa.getTecnicos());
        imprimirLista("Servicios", empresa.getServicios());
        imprimirLista("Repuestos", empresa.getRepuestos());
        imprimirLista("Órdenes", empresa.getOrdenes());
    }

    // ===================== Datos de ejemplo para la demostración =====================
    private static void cargarDatosEjemplo(Empresa empresa) {
        empresa.registrarCliente(new Cliente("1094000111", "Laura Gómez", "3101234567",
                "laura@correo.com", "Calle 10 # 5-20, Armenia"));
        empresa.registrarCliente(new Cliente("1094000222", "Juan Pérez", "3207654321",
                "juan@correo.com", "Carrera 14 # 20-35, Armenia"));

        empresa.registrarEquipo("1094000111", new ComputadorPortatil("EQ-001", "SN-A1", "Lenovo",
                "IdeaPad", 2021, "Pantalla rayada", 512, 15.6, 16, false));
        empresa.registrarEquipo("1094000111", new ComputadorPortatil("EQ-002", "SN-A2", "HP",
                "Pavilion", 2020, "Teclado desgastado", 256, 14.0, 8, true));
        empresa.registrarEquipo("1094000111", new TelefonoInteligente("EQ-003", "SN-B1", "Samsung",
                "A54", 2023, "Sin daños visibles", 128, "Android", 5000, true));
        empresa.registrarEquipo("1094000222", new ComputadorEscritorio("EQ-004", "SN-C1", "Dell",
                "OptiPlex", 2019, "Ventilador ruidoso", 1000, "Intel i5", 16, false));

        empresa.registrarTecnico(new Tecnico("T-01", "Carlos Ruiz", "79000111",
                EstadoLaboral.ACTIVO, Especialidad.COMPUTADORES));
        empresa.registrarTecnico(new Tecnico("T-02", "Ana Mejía", "52000222",
                EstadoLaboral.ACTIVO, Especialidad.DISPOSITIVOS_MOVILES));
        empresa.registrarTecnico(new Tecnico("T-03", "Pedro Díaz", "80000333",
                EstadoLaboral.INACTIVO, Especialidad.SOPORTE_GENERAL));

        empresa.registrarServicio(new Diagnostico("S-01", "Diagnóstico", "Identifica fallas del equipo"));
        empresa.registrarServicio(new MantenimientoPreventivo("S-02", "Mantenimiento preventivo",
                "Limpieza, revisión y optimización"));
        empresa.registrarServicio(new ReparacionCorrectiva("S-03", "Reparación correctiva",
                "Soluciona fallas identificadas"));

        empresa.registrarRepuesto(new Repuesto("R-01", "Pantalla 15.6", "Pantalla LED", 150000, 5,
                CategoriaEquipo.PORTATIL));
        empresa.registrarRepuesto(new Repuesto("R-02", "Disco SSD 512GB", "Disco de estado sólido",
                120000, 3, CategoriaEquipo.PORTATIL));
        empresa.registrarRepuesto(new Repuesto("R-03", "Batería teléfono", "Batería de repuesto",
                80000, 10, CategoriaEquipo.TELEFONO));
        empresa.registrarRepuesto(new Repuesto("R-04", "Fuente de poder", "Fuente 500W", 95000, 2,
                CategoriaEquipo.ESCRITORIO));

        // Una orden con intervenciones en distintos estados
        empresa.crearOrden("O-001", "1094000111", "Cliente trae 2 equipos");
        empresa.agregarDetalleAOrden("O-001", "D-1", "EQ-001", "S-01", "No enciende la pantalla");
        empresa.agregarDetalleAOrden("O-001", "D-2", "EQ-001", "S-03", "Cambio de pantalla y disco");
        empresa.agregarDetalleAOrden("O-001", "D-3", "EQ-003", "S-02", "Mantenimiento general");
        empresa.asignarTecnico("O-001", "D-1", "T-01");
        empresa.asignarTecnico("O-001", "D-2", "T-01");
        empresa.asignarTecnico("O-001", "D-3", "T-02");
        empresa.registrarTrabajoDetalle("O-001", "D-2", 2, "Pantalla y disco reemplazados");
        empresa.registrarRepuestoUtilizado("O-001", "D-2", "R-01", 1);
        empresa.registrarRepuestoUtilizado("O-001", "D-2", "R-02", 1);
        empresa.actualizarEstadoDetalle("O-001", "D-1", EstadoDetalle.EN_PROCESO);
        empresa.actualizarEstadoDetalle("O-001", "D-1", EstadoDetalle.FINALIZADO);
        empresa.actualizarEstadoDetalle("O-001", "D-2", EstadoDetalle.EN_PROCESO);

        System.out.println("Datos de ejemplo cargados (2 clientes, 4 equipos, 3 técnicos, 3 servicios, "
                + "4 repuestos y la orden O-001).");
    }
}



