package MommysIceCreamWeb.controller;

import MommysIceCreamWeb.service.CarritoService;
import MommysIceCreamWeb.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comprar")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private CarritoService carritoService;

    @PostMapping
    public String procesarCompra() {
        pedidoService.realizarPedido(carritoService.getItems());
        carritoService.vaciar(); // Limpia el carrito
        return "compras/confirmacion";
    }
}