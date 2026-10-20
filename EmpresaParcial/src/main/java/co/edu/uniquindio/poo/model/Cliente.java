package co.edu.uniquindio.poo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cliente {
    private String numeroDocumento;
    private String nombreCompleto;
    private String numeroTelefono;
    private String correo;
    private String direccionResidencia;
    private Empresa OwnedByEmpresa;
    private Map<String, EquipoTecnologico> hashMapEquipos = new HashMap<>();

    public Cliente(String numeroDocumento, String nombreCompleto, String numeroTelefono, String correo, String direccionResidencia) {
        this.numeroDocumento = numeroDocumento;
        this.nombreCompleto = nombreCompleto;
        this.numeroTelefono = numeroTelefono;
        this.correo = correo;
        this.direccionResidencia = direccionResidencia;
    }

    // Valida los datos personales: documento numérico, teléfono de 7 a 10 dígitos y correo con formato básico
    public boolean esValido() {
        return textoValido(numeroDocumento) && numeroDocumento.matches("\\d+")
                && textoValido(nombreCompleto)
                && textoValido(numeroTelefono) && numeroTelefono.matches("\\d{7,10}")
                && textoValido(correo) && correo.contains("@") && correo.contains(".")
                && textoValido(direccionResidencia);
    }

    private static boolean textoValido (String texto) {

        return texto != null && !texto.trim().isEmpty();
    }

    // Rechaza equipos nulos o con código de registro repetido
    public boolean agregarEquipo (EquipoTecnologico equipo) {
        if (equipo == null || hashMapEquipos.containsKey(equipo.getCodigoRegistro())) {
            return false;
        }
        hashMapEquipos.put(equipo.getCodigoRegistro(), equipo);
        return true;
    }

    public EquipoTecnologico buscarEquipo(String codigoRegistro) {

        return hashMapEquipos.get(codigoRegistro);
    }

    public ArrayList<EquipoTecnologico> getEquipos() {

        return new ArrayList<>(hashMapEquipos.values());
    }

    public String getNumeroDocumento() { return numeroDocumento; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getNumeroTelefono() { return numeroTelefono; }
    public String getCorreo() { return correo; }
    public String getDireccionResidencia() { return direccionResidencia; }

    @Override
    public String toString() {
        return "Cliente{" +
                "numeroDocumento='" + numeroDocumento + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", numeroTelefono='" + numeroTelefono + '\'' +
                ", correo='" + correo + '\'' +
                ", direccionResidencia='" + direccionResidencia + '\'' +
                ", hashMaplistaEquipos=" + hashMapEquipos +
                '}';
    }
}
