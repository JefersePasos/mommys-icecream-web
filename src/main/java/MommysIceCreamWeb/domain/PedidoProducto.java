package MommysIceCreamWeb.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_product")
public class PedidoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_product")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_order")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Producto producto;

    @Column(name = "cantidad")
    private int cantidad;
    
    private double precio;

    private boolean status = true;

    private LocalDateTime created_at = LocalDateTime.now();
    private LocalDateTime modified_at = LocalDateTime.now();

    // Getters y Setters

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }

    public LocalDateTime getModified_at() { return modified_at; }
    public void setModified_at(LocalDateTime modified_at) { this.modified_at = modified_at; }
}