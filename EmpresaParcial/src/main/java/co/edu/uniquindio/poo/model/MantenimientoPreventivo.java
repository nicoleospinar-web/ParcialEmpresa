package co.edu.uniquindio.poo.model;

public class MantenimientoPreventivo extends Servicio {


    public static final double VALOR_PORTATIL = 60000;
    public static final double VALOR_ESCRITORIO = 70000;
    public static final double VALOR_TELEFONO = 40000;

    public MantenimientoPreventivo(String codigo, String nombre, String descripcion) {
        // La tarifa base es la más baja; el costo final depende de la categoría del equipo
        super(codigo, nombre, descripcion, VALOR_TELEFONO);
    }

    @Override
    public double calcularCostoFinal(DetalleOrdenServicio detalle) {
        CategoriaEquipo categoria = detalle.getEquipo().obtenerCategoria();
        switch (categoria) {
            case PORTATIL:
                return VALOR_PORTATIL;
            case ESCRITORIO:
                return VALOR_ESCRITORIO;
            case TELEFONO:
                return VALOR_TELEFONO;
            default:
                throw new IllegalStateException("Categoría no soportada: " + categoria);
        }
    }

    @Override
    public String obtenerTipo() {
        return "Mantenimiento preventivo";
    }
}
