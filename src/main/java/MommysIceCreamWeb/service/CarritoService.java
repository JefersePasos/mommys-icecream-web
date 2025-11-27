package MommysIceCreamWeb.service;

import MommysIceCreamWeb.domain.CarritoItem;
import MommysIceCreamWeb.domain.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CarritoService {

    private final List<CarritoItem> carrito = new ArrayList<>();

    public List<CarritoItem> getItems() {
        return carrito;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        for (CarritoItem item : carrito) {
            if (item.getProducto().getId().equals(producto.getId())) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        carrito.add(new CarritoItem(producto, cantidad));
    }

    public void eliminarProducto(Long productoId) {
        Iterator<CarritoItem> iterator = carrito.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getProducto().getId().equals(productoId)) {
                iterator.remove();
                break;
            }
        }
    }

    public double calcularTotal() {
        return carrito.stream()
                .mapToDouble(CarritoItem::getSubtotal)
                .sum();
    }

    public void vaciar() {
        carrito.clear();
    }

    // Aumentar cantidad
    public void aumentarCantidad(Long productoId) {
        carrito.forEach(item -> {
            if (item.getProducto().getId().equals(productoId)) {
                item.setCantidad(item.getCantidad() + 1);
            }
        });
    }

    // Disminuir cantidad
    public void disminuirCantidad(Long productoId) {
        carrito.forEach(item -> {
            if (item.getProducto().getId().equals(productoId) && item.getCantidad() > 1) {
                item.setCantidad(item.getCantidad() - 1);
            }
        });
    }
}