package MommysIceCreamWeb.demo.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import MommysIceCreamWeb.demo.model.Producto;
import MommysIceCreamWeb.demo.repository.ProductoRepository;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarTodos() { return productoRepository.findAll(); }
    public void guardar(Producto producto) { productoRepository.save(producto); }
    public Optional<Producto> obtenerPorId(Long id) { return productoRepository.findById(id); }
    public void eliminar(Long id) { productoRepository.deleteById(id); }
}
