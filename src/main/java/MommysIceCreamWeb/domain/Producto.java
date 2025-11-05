
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

    @Column(name = "product")
    private String nombre;

    private boolean status;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    public Producto() {}
    public Producto(String nombre, boolean status) {
        this.nombre = nombre;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
    public LocalDateTime getModified_at() { return modified_at; }
    public void setModified_at(LocalDateTime modified_at) { this.modified_at = modified_at; }
}