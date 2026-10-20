package co.edu.uniquindio.poo.model;

public class ReparacionCorrectiva extends Servicio {


    public static final double VALOR_HORA_MANO_OBRA = 45000;

    public ReparacionCorrectiva(String codigo, String nombre, String descripcion) {
        super(codigo, nombre, descripcion, VALOR_HORA_MANO_OBRA);
    }

    @Override
    public double calcularCostoFinal(DetalleOrdenServicio detalle) {
        double manoObra = detalle.getHorasTrabajo() * VALOR_HORA_MANO_OBRA;
        return manoObra + detalle.calcularCostoRepuestos();
    }

    @Override
    public String obtenerTipo() {
        return "Reparación correctiva";
    }

    @Override
    public boolean admiteRepuestos() {
        return true;
    }
}