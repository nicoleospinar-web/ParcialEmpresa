package co.edu.uniquindio.poo.model;

public class DetalleReparacion {
    private int cantidadRequerida;
    private double precioUnitario;
    private Repuesto repuesto;

    public DetalleReparacion(Repuesto repuesto, int cantidadRequerida) {
        this.repuesto = repuesto;
        this.cantidadRequerida = cantidadRequerida;
        this.precioUnitario = repuesto.getPrecioUnitario();
    }

    public double calcularSubtotal() {

        return cantidadRequerida * precioUnitario;
    }

    public Repuesto getRepuesto() { return repuesto; }
    public int getCantidadRequerida() { return cantidadRequerida; }
    public double getPrecioUnitario() { return precioUnitario; }


    @Override
    public String toString() {
        return "DetalleReparacion{" +
                "cantidadDisponible=" + cantidadRequerida +
                ", precioUnitario=" + precioUnitario +
                '}';
    }
}
