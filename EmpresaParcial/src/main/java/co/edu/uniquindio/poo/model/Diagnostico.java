package co.edu.uniquindio.poo.model;

public class Diagnostico extends Servicio {

    public static final double COSTO_FIJO = 35000;

    public Diagnostico(String codigo, String nombre, String descripcion) {
        super(codigo, nombre, descripcion, COSTO_FIJO);
    }

    @Override
    public double calcularCostoFinal(DetalleOrdenServicio detalle) {
        return COSTO_FIJO;
    }

    @Override
    public String obtenerTipo() {
        return "Diagnóstico";
    }
}
