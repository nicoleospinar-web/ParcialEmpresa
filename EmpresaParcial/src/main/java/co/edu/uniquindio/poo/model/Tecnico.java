package co.edu.uniquindio.poo.model;

public class Tecnico {
    private String codigo;
    private String nombre;
    private String documentoIdentificacion;
    private EstadoLaboral estadoLaboral;
    private Especialidad especialidad;
    private Empresa OwnedByEmpresa;

    public Tecnico(String codigo, String nombre, String documentoIdentificacion, EstadoLaboral estadoLaboral, Especialidad especialidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.documentoIdentificacion = documentoIdentificacion;
        this.estadoLaboral = estadoLaboral;
        this.especialidad = especialidad;
    }

    public boolean esValido() {
        return textoValido(codigo) && textoValido(nombre) && textoValido(documentoIdentificacion)
                && especialidad != null && estadoLaboral != null;
    }

    private static boolean textoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public boolean estaActivo() {
        return estadoLaboral == EstadoLaboral.ACTIVO;
    }

    // Un técnico está autorizado según su especialidad:
    // computadores -> portátiles y escritorio, móviles -> teléfonos, soporte general -> todo
    public boolean puedeAtender(EquipoTecnologico equipo) {
        CategoriaEquipo categoria = equipo.obtenerCategoria();
        switch (especialidad) {
            case COMPUTADORES:
                return categoria == CategoriaEquipo.PORTATIL
                        || categoria == CategoriaEquipo.ESCRITORIO;
            case DISPOSITIVOS_MOVILES:
                return categoria == CategoriaEquipo.TELEFONO;
            case SOPORTE_GENERAL:
                return true;
            default:
                return false;
        }
    }

    public String getCodigo() { return codigo; }

    public String getNombre() { return nombre; }

    public String getDocumentoIdentificacion() { return documentoIdentificacion; }

    public EstadoLaboral getEstadoLaboral() { return estadoLaboral; }

    public Especialidad getEspecialidad() { return especialidad; }

    public Empresa getOwnedByEmpresa() { return OwnedByEmpresa; }

    public void setEstadoLaboral(EstadoLaboral estadoLaboral) {
        this.estadoLaboral = estadoLaboral;
    }

    @Override
    public String toString() {
        return "Tecnico{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", documentoIdentificacion='" + documentoIdentificacion + '\'' +
                ", estadoLaboral=" + estadoLaboral +
                ", especialidad=" + especialidad +
                ", OwnedByEmpresa=" + OwnedByEmpresa +
                '}';
    }
}

