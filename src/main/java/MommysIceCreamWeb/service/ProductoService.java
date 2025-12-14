package MommysIceCreamWeb.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import MommysIceCreamWeb.domain.Producto;
import MommysIceCreamWeb.repository.ProductoRepository;
import org.springframework.util.StringUtils;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private TranslationService translationService;

    public List<Producto> listarTodos() { return productoRepository.findAll(); }
    public List<Producto> listarDisponibles() { return productoRepository.findByStatusTrue(); }
    public List<Producto> listar3RecientesDisponibles() { return productoRepository.findTop3ByStatusTrueOrderByIdDesc(); }
    public void guardar(Producto producto) {
        // Generar traducciones faltantes entre ES <-> EN
        if (!StringUtils.hasText(producto.getSaborEn()) && StringUtils.hasText(producto.getSabor())) {
            translationService.translate(producto.getSabor(), "es", "en").ifPresent(producto::setSaborEn);
        }
        if (!StringUtils.hasText(producto.getDescripcionEn()) && StringUtils.hasText(producto.getDescripcion())) {
            translationService.translate(producto.getDescripcion(), "es", "en").ifPresent(producto::setDescripcionEn);
        }
        if (!StringUtils.hasText(producto.getSabor()) && StringUtils.hasText(producto.getSaborEn())) {
            translationService.translate(producto.getSaborEn(), "en", "es").ifPresent(producto::setSabor);
        }
        if (!StringUtils.hasText(producto.getDescripcion()) && StringUtils.hasText(producto.getDescripcionEn())) {
            translationService.translate(producto.getDescripcionEn(), "en", "es").ifPresent(producto::setDescripcion);
        }
        productoRepository.save(producto);
    }
    public Optional<Producto> obtenerPorId(Long id) { return productoRepository.findById(id); }
    public void eliminar(Long id) { productoRepository.deleteById(id); }
}
