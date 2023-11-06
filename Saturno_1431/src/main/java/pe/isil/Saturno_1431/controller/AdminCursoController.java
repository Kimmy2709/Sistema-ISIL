package pe.isil.Saturno_1431.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.isil.Saturno_1431.model.Curso;
import pe.isil.Saturno_1431.repository.CursoRepository;
import pe.isil.Saturno_1431.service.FileSystemStorageService;

@Controller
@RequestMapping("/admin/cursos")
public class AdminCursoController {

    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @GetMapping("")  //localhost:8080/admin/cursos = mostrar la pagina administrativa de los cursos
        // @RequestParam(required = false) es para llamarlo solo si se requiere,
    String index(Model model,
                 @PageableDefault(size = 5, sort = "nombre") Pageable pageable,
                 @RequestParam(required = false) String nombre)
    {
        Page<Curso> cursos;
        if(nombre != null && nombre.trim().isEmpty()){
            cursos= cursoRepository.findByNombreContaining(nombre,pageable);
        }else{
            cursos= cursoRepository.findAll(pageable);
        }
        model.addAttribute("cursos", cursos);
        return "admin/cursos/index";
    }

    //NUEVO: mostrar un formulario para registrar nuevo curso
    //método GET
    @GetMapping("/nuevo")
    String nuevo(Model model){
        model.addAttribute("curso", new Curso()); // se agrega como atributo una nueva instancia u objecto de la clase curso
        return "admin/cursos/nuevo";
    }
    //Crear: registrar nuevo curso
    //POST
    @PostMapping("/nuevo")
    String crear(Model model, @Validated Curso curso, BindingResult bindingResult, RedirectAttributes ra){ //bindingresult es para mostrar las validaciones

        if(curso.getImagen().isEmpty()){
            bindingResult.rejectValue("imagen", "MultipartNotEmpty");
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("curso", curso);
            return "admin/cursos/nuevo"; //return redirige hacia una vista o HTML
        }
        String rutaImagen= fileSystemStorageService.store(curso.getImagen());
        curso.setRutaImagen(rutaImagen);

        curso.setId(0);
        cursoRepository.save(curso);
        ra.addFlashAttribute("msgExito", "El curso se creó exitosamente");
        return "redirect:/admin/cursos"; //return redirige hacia una ruta URL
    }

    //Editar: obtener los datos del curso por su ID
    @GetMapping("/editar/{id}")
    String editar(Model model, @PathVariable("id") Integer id){
        Curso curso = cursoRepository.getById(id);
        model.addAttribute("curso", curso);
        return "admin/cursos/editar";
    }
    @PostMapping("/editar/{id}")
    String actualizar(Model model,@Validated Curso curso, BindingResult bindingResult, @PathVariable("id") Integer id, RedirectAttributes ra){
        if(bindingResult.hasErrors()){
            model.addAttribute("curso", curso);
            return "admin/cursos/editar";
        }
        Curso cursofromDB = cursoRepository.getById(id);
        cursofromDB.setArea(curso.getArea());
        cursofromDB.setCreditos(curso.getCreditos());
        cursofromDB.setHoras(curso.getHoras());
        cursofromDB.setNombre(curso.getNombre());
        cursofromDB.setModalidad(curso.getModalidad());
        cursofromDB.setNrc(curso.getNrc());

        cursoRepository.save(cursofromDB);
        ra.addFlashAttribute("msgExito", "El curso se actualizó exitosamente");
        return "redirect:/admin/cursos";
    }
    //Actualizar: actualización de datos
    //Delete: eliminar un curso
    @PostMapping("/eliminar/{id}")
    String Eliminar(@PathVariable("id") Integer id, RedirectAttributes ra){
        cursoRepository.deleteById(id);
        ra.addFlashAttribute("msgExito", "El curso se eliminó exitosamente");
        return "redirect:/admin/cursos";
    }
}
