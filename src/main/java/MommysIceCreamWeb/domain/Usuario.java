package MommysIceCreamWeb.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity //JPA
@Data //Lombok
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String primerApellido;
    private String segundoApellido;

    private LocalDate fechaNacimiento;

    @Column(unique = true)
    private String correo;

    private String contrasena;

    private String provincia;
    private String canton;
    private String distrito;
    private String direccionExacta;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefono> telefonos;
}