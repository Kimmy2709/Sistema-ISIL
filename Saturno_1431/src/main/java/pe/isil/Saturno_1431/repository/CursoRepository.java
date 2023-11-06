package pe.isil.Saturno_1431.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.isil.Saturno_1431.model.Curso;

import java.util.List;

@Repository // repository nos permite tener acceso a datos
public interface CursoRepository extends JpaRepository<Curso, Integer> {

    //en el repository se pueden hacer consultas, el find es equivalente a select/buscar
    // SELECT * FROM cursos WHERE nombre like '%?%'
    //Page es un listado de cursos pero sirve para paginar
    Page<Curso> findByNombreContaining(String nombre, Pageable pageable);

    //paginar por NRC
    Page<Curso> findByNrcContaining(String nrc, Pageable pageable);

    //paginar por 5 cursos por fecha de creacion
    List<Curso> findTop5ByOrderByFechaCreacionDesc();
}
