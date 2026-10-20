package co.edu.uniquindio.poo.model;

import java.time.LocalDate;

public abstract class EquipoTecnologico {
    private String codigoRegistro;
    private String numeroSerie;
    private String marca;
    private String modelo;
    private int anioFabricacion;
    private String descripcionEstado;
    private int capacidadAlmacenamiento;

    public EquipoTecnologico(String codigoRegistro, String numeroSerie, String marca, String modelo, int anioFabricacion, String descripcionEstado, int capacidadAlmacenamiento) {
        this.codigoRegistro = codigoRegistro;
        this.numeroSerie = numeroSerie;
        this.marca = marca;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.descripcionEstado = descripcionEstado;
        this.capacidadAlmacenamiento = capacidadAlmacenamiento;
    }

    // Cada subclase indica a qué categoría pertenece
    public abstract CategoriaEquipo obtenerCategoria();

    // Valida los datos comunes - las subclases la sobrescriben para validar los suyos
    public boolean esValido() {
        int anioActual = LocalDate.now().getYear();
        return textoValido(codigoRegistro) && textoValido(numeroSerie)
                && textoValido(marca) && textoValido(modelo)
                && textoValido(descripcionEstado)
                && anioFabricacion >= 1990 && anioFabricacion <= anioActual
                && capacidadAlmacenamiento > 0;
    }

    protected static boolean textoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public String getCodigoRegistro() { return codigoRegistro; }

    public String getNumeroSerie() { return numeroSerie; }

    public String getMarca() { return marca; }

    public String getModelo() { return modelo; }

    public int getAnioFabricacion() { return anioFabricacion; }

    public String getDescripcionEstado() { return descripcionEstado; }

    public int getCapacidadAlmacenamiento() { return capacidadAlmacenamiento; }

    @Override
    public String toString() {
        return "EquipoTecnologico{" +
                "codigoRegistro='" + codigoRegistro + '\'' +
                ", numeroSerie='" + numeroSerie + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", anioFabricacion=" + anioFabricacion +
                ", descripcionEstado='" + descripcionEstado + '\'' +
                ", capacidadAlmacenamiento=" + capacidadAlmacenamiento +
                '}';
    }
}
