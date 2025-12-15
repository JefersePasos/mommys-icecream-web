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
        // Generar traducciones faltantes entre ES <-> EN. Si el servicio de traducciA3n no responde,
        // copiamos el mismo texto para no dejar los campos en blanco.
        if (!StringUtils.hasText(producto.getSaborEn()) && StringUtils.hasText(producto.getSabor())) {
            String local = traducirSaborEsEn(producto.getSabor());
            if (StringUtils.hasText(local) && !local.equalsIgnoreCase(producto.getSabor())) {
                producto.setSaborEn(local);
            } else {
                translationService.translate(producto.getSabor(), "es", "en")
                        .ifPresentOrElse(producto::setSaborEn, () -> producto.setSaborEn(producto.getSabor()));
            }
        }
        if (!StringUtils.hasText(producto.getDescripcionEn()) && StringUtils.hasText(producto.getDescripcion())) {
            translationService.translate(producto.getDescripcion(), "es", "en")
                    .ifPresentOrElse(producto::setDescripcionEn, () -> producto.setDescripcionEn(producto.getDescripcion()));
        }
        if (!StringUtils.hasText(producto.getSabor()) && StringUtils.hasText(producto.getSaborEn())) {
            String local = traducirSaborEnEs(producto.getSaborEn());
            if (StringUtils.hasText(local) && !local.equalsIgnoreCase(producto.getSaborEn())) {
                producto.setSabor(local);
            } else {
                translationService.translate(producto.getSaborEn(), "en", "es")
                        .ifPresentOrElse(producto::setSabor, () -> producto.setSabor(producto.getSaborEn()));
            }
        }
        if (!StringUtils.hasText(producto.getDescripcion()) && StringUtils.hasText(producto.getDescripcionEn())) {
            translationService.translate(producto.getDescripcionEn(), "en", "es")
                    .ifPresentOrElse(producto::setDescripcion, () -> producto.setDescripcion(producto.getDescripcionEn()));
        }
        productoRepository.save(producto);
    }

    // TraducciA3n local rA!pida para sabores conocidos
    private String traducirSaborEsEn(String sabor) {
        if (sabor == null) return null;
        String clave = sabor.trim().toLowerCase();
        return switch (clave) {
            case "fresa" -> "Strawberry";
            case "chocolate" -> "Chocolate";
            case "sandía", "sandia" -> "Watermelon";
            case "naranja" -> "Orange";
            case "maracuyá", "maracuya" -> "Passion Fruit";
            case "coco" -> "Coconut";
            case "arándanos", "arandanos" -> "Blueberries";
            case "fresas con crema" -> "Strawberries and Cream";
            case "yogurt con mora" -> "Blackberry Yogurt";
            case "galleta maría", "galleta maria" -> "Maria Cookie";
            default -> sabor;
        };
    }

    private String traducirSaborEnEs(String saborEn) {
        if (saborEn == null) return null;
        String clave = saborEn.trim().toLowerCase();
        return switch (clave) {
            case "strawberry" -> "Fresa";
            case "chocolate" -> "Chocolate";
            case "watermelon" -> "Sandía";
            case "orange" -> "Naranja";
            case "passion fruit" -> "Maracuyá";
            case "coconut" -> "Coco";
            case "blueberries" -> "Arándanos";
            case "strawberries and cream" -> "Fresas con Crema";
            case "blackberry yogurt" -> "Yogurt con Mora";
            case "maria cookie" -> "Galleta María";
            default -> saborEn;
        };
    }
    public Optional<Producto> obtenerPorId(Long id) { return productoRepository.findById(id); }
    public void eliminar(Long id) { productoRepository.deleteById(id); }
}
