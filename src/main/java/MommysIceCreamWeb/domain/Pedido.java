package MommysIceCreamWeb.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "order_status")
    private boolean orderStatus = true;

    private boolean status = true;

    private LocalDateTime created_at = LocalDateTime.now();
    private LocalDateTime modified_at = LocalDateTime.now();

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoProducto> productos;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public boolean isOrderStatus() { return orderStatus; }
    public void setOrderStatus(boolean orderStatus) { this.orderStatus = orderStatus; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }

    public LocalDateTime getModified_at() { return modified_at; }
    public void setModified_at(LocalDateTime modified_at) { this.modified_at = modified_at; }

    public List<PedidoProducto> getProductos() { return productos; }
    public void setProductos(List<PedidoProducto> productos) { this.productos = productos; }
}