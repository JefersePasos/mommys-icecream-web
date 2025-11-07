
package MommysIceCreamWeb.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long id;

    @Column(name = "flavor")
    private String sabor;

    private boolean status;
    @Column(name = "imagen")
    private String imagen;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    public Producto() {}
    public Producto(String sabor, boolean status) {
        this.sabor = sabor;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSabor() { return sabor; }
    public void setSabor(String sabor) { this.sabor = sabor; }
    // Alias para mantener compatibilidad con la vista
    public String getNombre() { return sabor; }
    public void setNombre(String sabor) { this.sabor = sabor; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
    public LocalDateTime getModified_at() { return modified_at; }
    public void setModified_at(LocalDateTime modified_at) { this.modified_at = modified_at; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
}