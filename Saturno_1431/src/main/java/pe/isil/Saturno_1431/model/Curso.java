package pe.isil.Saturno_1431.model;


import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Curso {

    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincremental
    @Id//para primary key
    private Integer id;

    @Column(unique = true)
    @NotNull
    @NotBlank
    private String nrc;

    @NotNull
    @Size(max = 50, min= 2)
    private String nombre;

    @NotNull
    @Min(1)
    @Max(4)
    private Integer creditos;

    @NotNull
    @Min(1)
    @Max(4)
    private Integer horas;

    @NotNull
    @NotBlank
    private String modalidad;

    @NotNull
    @NotBlank
    private String area;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    @PrePersist// pre(antes de insertar asignar el valor en la fecha de creacion)
    void prePersist(){
        fechaCreacion= LocalDateTime.now();
    }
    @PreUpdate //pre(antes de actualizar asignar el valor en la fecha de actualización)
    void preUpdate(){
        fechaActualizacion = LocalDateTime.now();
    }

    private String rutaImagen;

    @Transient //esta anotación indica que el campo existe solo cuando se ejecuta la app, no en la bd
    private MultipartFile imagen;
}
