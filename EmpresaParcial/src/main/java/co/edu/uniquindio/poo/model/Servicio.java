package co.edu.uniquindio.poo.model;

public abstract class Servicio {

    private String codigo;
    private String nombre;
    private String descripcion;
    private double tarifaBase;

    public Servicio(String codigo, String nombre, String descripcion, double tarifaBase) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.tarifaBase = tarifaBase;
        }

    // Cada tipo de servicio calcula su costo con sus propias reglas
    public abstract double calcularCostoFinal(DetalleOrdenServicio detalle);

    // Nombre del tipo de servicio, usado en los reportes
    public abstract String obtenerTipo();

    // Por defecto un servicio no usa repuestos; la reparación lo sobrescribe
    public boolean admiteRepuestos() {
        return false;
    }

    public String getCodigo() { return codigo; }

    public String getNombre() { return nombre; }

    public String getDescripcion() { return descripcion; }

    public double getTarifaBase() { return tarifaBase; }

    @Override
    public String toString() {
        return "Servicio{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tarifaBase=" + tarifaBase +
                '}';
    }
}

