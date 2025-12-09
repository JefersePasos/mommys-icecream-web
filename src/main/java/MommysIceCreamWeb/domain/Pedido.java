package MommysIceCreamWeb.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;

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
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt = LocalDateTime.now();

    private boolean status = true;

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

    public LocalDateTime getCreated_at() { return createdAt; }
    public void setCreated_at(LocalDateTime created_at) { this.createdAt = created_at; }

    public LocalDateTime getModified_at() { return modifiedAt; }
    public void setModified_at(LocalDateTime modified_at) { this.modifiedAt = modified_at; }

    public List<PedidoProducto> getProductos() { return productos; }
    public void setProductos(List<PedidoProducto> productos) { this.productos = productos; }

    //Para el total del precio de los productos
    public double getTotal() {
        if (productos == null) return 0;
        return productos.stream()
            .mapToDouble(p -> p.getCantidad() * p.getPrecio())
            .sum();
    }

    //Para costa rica y su precio
    public String getTotalColones() {
        double total = getTotal(); // ya calculado

        Locale costaRica = new Locale("es", "CR");
        NumberFormat formato = NumberFormat.getCurrencyInstance(costaRica);

        return formato.format(total);
    }

    //Para el total de la cantidad de apretados comprados y se muestren en el historial de pedidos
    public int getCantidadTotal() {
        if (productos == null) return 0;

        return productos.stream()
            .mapToInt(PedidoProducto::getCantidad)
            .sum();
    }
}