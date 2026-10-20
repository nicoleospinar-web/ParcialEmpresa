package co.edu.uniquindio.poo.model;

public class ComputadorEscritorio extends EquipoTecnologico {

    private String tipoProcesador;
    private int capacidadMemoriaRam;
    private boolean tarjetaGrafica;

    public ComputadorEscritorio(String codigoRegistro, String numeroSerie, String marca, String modelo, int anioFabricacion, String descripcionEstado, int capacidadAlmacenamiento, String tipoProcesador, int capacidadMemoriaRam, boolean tarjetaGrafica) {
        super(codigoRegistro, numeroSerie, marca, modelo, anioFabricacion, descripcionEstado, capacidadAlmacenamiento);
        this.tipoProcesador = tipoProcesador;
        this.capacidadMemoriaRam = capacidadMemoriaRam;
        this.tarjetaGrafica = tarjetaGrafica;
    }

    @Override
    public CategoriaEquipo obtenerCategoria() {
        return CategoriaEquipo.ESCRITORIO;
    }

    @Override
    public boolean esValido() {

        return super.esValido() && textoValido(tipoProcesador) && capacidadMemoriaRam > 0;
    }

    public String getTipoProcesador() { return tipoProcesador; }

    public int getCapacidadMemoriaRam() { return capacidadMemoriaRam; }

    public boolean isTarjetaGrafica() { return tarjetaGrafica; }

    @Override
    public String toString() {
        return "ComputadorEscritorio{" +
                "tipoProcesador='" + tipoProcesador + '\'' +
                ", capacidadMemoriaRam=" + capacidadMemoriaRam +
                ", tarjetaGrafica=" + tarjetaGrafica +
                '}';
    }
}
