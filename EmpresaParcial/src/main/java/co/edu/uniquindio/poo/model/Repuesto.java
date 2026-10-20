package co.edu.uniquindio.poo.model;

public class Repuesto {

    private String codigo;
    private String nombre;
    private String descripcion;
    private double precioUnitario;
    private int cantidadDisponible;
    private CategoriaEquipo categoriaCompatibilidad;

    public Repuesto(String codigo, String nombre, String descripcion, double precioUnitario,
                    int cantidadDisponible, CategoriaEquipo categoriaCompatibilidad) {
        if (precioUnitario < 0 || cantidadDisponible < 0) {
            throw new IllegalArgumentException("El precio y la cantidad no pueden ser negativos");
        }
        if (codigo == null || codigo.trim().isEmpty() || nombre == null || nombre.trim().isEmpty()
                || categoriaCompatibilidad == null) {
            throw new IllegalArgumentException("El código, el nombre y la categoría son obligatorios");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.cantidadDisponible = cantidadDisponible;
        this.categoriaCompatibilidad = categoriaCompatibilidad;
    }

    // Verifica que la cantidad pedida sea positiva y no supere las existencias
    public boolean hayDisponibilidad(int cantidad) {
        return cantidad > 0 && cantidad <= cantidadDisponible;
    }

    // Descuenta del inventario solo si hay disponibilidad
    public boolean descontar(int cantidad) {
        if (!hayDisponibilidad(cantidad)) {
            return false;
        }
        cantidadDisponible -= cantidad;
        return true;
    }

    public String getCodigo() { return codigo; }

    public String getNombre() { return nombre; }

    public String getDescripcion() { return descripcion; }

    public double getPrecioUnitario() { return precioUnitario; }

    public int getCantidadDisponible() { return cantidadDisponible; }

    public CategoriaEquipo getCategoriaCompatibilidad() {
        return categoriaCompatibilidad;
    }

    @Override
    public String toString() {
        return "Repuesto{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioUnitario=" + precioUnitario +
                ", cantidadDisponible=" + cantidadDisponible +
                ", categoriaCompatibilidad=" + categoriaCompatibilidad +
                '}';
    }
}
