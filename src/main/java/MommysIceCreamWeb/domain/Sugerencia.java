package MommysIceCreamWeb.domain;

public class Sugerencia {
    private String sabor;
    private String descripcion; 

    public Sugerencia() {}

    public Sugerencia(String sabor, String descripcion) {
        this.sabor = sabor;
        this.descripcion = descripcion;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
