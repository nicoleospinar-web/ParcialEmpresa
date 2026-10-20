package co.edu.uniquindio.poo.model;

public class ComputadorPortatil extends EquipoTecnologico {

    private double tamanioPulgadas;
    private int capacidadMemoriaRam;
    private boolean bateriaExtraible;

    public ComputadorPortatil(String codigoRegistro, String numeroSerie, String marca, String modelo, int anioFabricacion, String descripcionEstado, int capacidadAlmacenamiento, double tamanioPulgadas, int capacidadMemoriaRam, boolean bateriaExtraible) {
        super(codigoRegistro, numeroSerie, marca, modelo, anioFabricacion, descripcionEstado, capacidadAlmacenamiento);
        this.tamanioPulgadas = tamanioPulgadas;
        this.capacidadMemoriaRam = capacidadMemoriaRam;
        this.bateriaExtraible = bateriaExtraible;
    }

    @Override
    public CategoriaEquipo obtenerCategoria() {
        return CategoriaEquipo.PORTATIL;
    }

    @Override
    public boolean esValido() {

        return super.esValido() && tamanioPulgadas > 0 && capacidadMemoriaRam > 0;
    }
    public double getTamanioPulgadas() { return tamanioPulgadas; }

    public int getCapacidadMemoriaRam() { return capacidadMemoriaRam; }

    public boolean isBateriaExtraible() { return bateriaExtraible; }

    @Override
    public String toString() {
        return "ComputadorPortatil{" +
                "tamanioPulgadas=" + tamanioPulgadas +
                ", capacidadMemoriaRam=" + capacidadMemoriaRam +
                ", bateriaExtraible=" + bateriaExtraible +
                '}';
    }
}
