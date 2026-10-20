package co.edu.uniquindio.poo.model;

public class TelefonoInteligente extends EquipoTecnologico {
    private String sistemaOperativo;
    private int capacidadBateriaMah;
    private boolean compatible5G;

    public TelefonoInteligente(String codigoRegistro, String numeroSerie, String marca, String modelo, int anioFabricacion, String descripcionEstado, int capacidadAlmacenamiento, String sistemaOperativo, int capacidadBateriaMah, boolean compatible5G) {
        super(codigoRegistro, numeroSerie, marca, modelo, anioFabricacion, descripcionEstado, capacidadAlmacenamiento);
        this.sistemaOperativo = sistemaOperativo;
        this.capacidadBateriaMah = capacidadBateriaMah;
        this.compatible5G = compatible5G;
    }

    @Override
    public boolean esValido() {
        return super.esValido() && textoValido(sistemaOperativo) && capacidadBateriaMah > 0;
    }

    @Override
    public CategoriaEquipo obtenerCategoria() {
        return CategoriaEquipo.TELEFONO;
    }

    public String getSistemaOperativo() { return sistemaOperativo; }

    public int getCapacidadBateriaMah() { return capacidadBateriaMah; }

    public boolean isCompatible5G() { return compatible5G; }

    @Override
    public String toString() {
        return "TelefonoInteligente{" +
                "sistemaOperativo='" + sistemaOperativo + '\'' +
                ", capacidadBateriaMah=" + capacidadBateriaMah +
                ", compatible5G=" + compatible5G +
                '}';
    }
}
