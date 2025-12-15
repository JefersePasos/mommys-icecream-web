
package MommysIceCreamWeb.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; // Ejemplo: "ROLE_USER", "ROLE_ADMIN"
    @Column(name = "nombre_en")
    private String nombreEn; // Nombre del rol en inglés
    private boolean activo = true;
}
