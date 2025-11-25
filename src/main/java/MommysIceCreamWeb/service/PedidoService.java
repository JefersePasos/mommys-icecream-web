package MommysIceCreamWeb.service;

import MommysIceCreamWeb.domain.CarritoItem;
import MommysIceCreamWeb.domain.Pedido;
import MommysIceCreamWeb.domain.PedidoProducto;
import MommysIceCreamWeb.domain.Usuario;
import MommysIceCreamWeb.repository.PedidoRepository;
import MommysIceCreamWeb.repository.PedidoProductoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoProductoRepository pedidoProductoRepository;

    @Autowired
    private HttpSession session;

    public Pedido findById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    public void realizarPedido(List<CarritoItem> items) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setOrderStatus(true); // true = confirmado
        pedidoRepository.save(pedido);

        for (CarritoItem item : items) {
            PedidoProducto pp = new PedidoProducto();
            pp.setPedido(pedido);
            pp.setProducto(item.getProducto());
            pp.setCantidad(item.getCantidad());
            pp.setPrecio(item.getProducto().getPrecio());
            pedidoProductoRepository.save(pp);
        }
        
    }

    public List<Pedido> obtenerPedidosPorUsuario(Usuario usuario) {
        return pedidoRepository.findByUsuarioOrderByCreatedAtDesc(usuario);
    }
}