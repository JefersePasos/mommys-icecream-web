package MommysIceCreamWeb.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import MommysIceCreamWeb.domain.Pedido;
import MommysIceCreamWeb.domain.PedidoProducto;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class PdfService {

    public void generarFacturaPDF(Pedido pedido, HttpServletResponse response) throws IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=factura-pedido-" + pedido.getId() + ".pdf");

        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        // ================================
        //    HEADER / LOGO
        // ================================
        Font tituloFont = new Font(Font.HELVETICA, 22, Font.BOLD);
        Font subFont = new Font(Font.HELVETICA, 12);

        Paragraph titulo = new Paragraph("Factura - Mommy's Ice Cream", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        documento.add(titulo);

        documento.add(new Paragraph(" "));
        documento.add(new Paragraph("Fecha: " + pedido.getCreated_at().toString(), subFont));
        documento.add(new Paragraph("ID Pedido: " + pedido.getId(), subFont));
        documento.add(new Paragraph("Estado: " + (pedido.isOrderStatus() ? "Confirmado" : "Pendiente"), subFont));

        documento.add(new Paragraph(" "));
        documento.add(new Paragraph(" "));

        // ================================
        //    TABLA DE PRODUCTOS
        // ================================
        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{2f, 4f, 3f, 3f});

        tabla.addCell("Cantidad");
        tabla.addCell("Producto");
        tabla.addCell("Precio unidad");
        tabla.addCell("Subtotal");

        for (PedidoProducto pp : pedido.getProductos()) {
            tabla.addCell(String.valueOf(pp.getCantidad()));
            tabla.addCell(pp.getProducto().getSabor());
            tabla.addCell("₡ " + pp.getPrecio());
            tabla.addCell("₡ " + (pp.getCantidad() * pp.getPrecio()));
        }

        documento.add(tabla);

        // ================================
        //    TOTAL FINAL
        // ================================
        documento.add(new Paragraph(" "));
        Paragraph total = new Paragraph("Total: ₡ " + pedido.getTotal(), new Font(Font.HELVETICA, 16, Font.BOLD));
        total.setAlignment(Element.ALIGN_RIGHT);
        documento.add(total);

        documento.close();
    }
}