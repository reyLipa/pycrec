package com.todocode.pycrecer.controller;

import com.todocode.pycrecer.entities.Asistente;
import com.todocode.pycrecer.exceptions.AsistenteExistenteException;
import com.todocode.pycrecer.services.AsistenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;
@Controller
@RequestMapping("/asistentes")
@SessionAttributes("asistente")
public class AsistenteController {

    @Autowired
    private AsistenteService asistenteService;

    @GetMapping("/buscarAsistentes")
    public List<Asistente> buscarAsistente(){
        return asistenteService.buscarAsistentes();
    }
    @GetMapping("/buscarAsistentePorId/{id}")
    public Asistente buscarPorId(@PathVariable Long id){
        return asistenteService.buscarPorId(id);
    }
    @GetMapping("/buscarAsistentePorDni/{dni}")
    public Asistente buscarPorDni(@PathVariable String dni){
        return asistenteService.buscarPorDni(dni);
    }
    //    @DeleteMapping("/eliminarAsistente/{id}")
//    public void eliminar(@PathVariable Long id){
//        asistenteService.eliminar(id);
//    }
    @GetMapping("/{id}")
    public String eliminar(@PathVariable Long id){
        asistenteService.eliminar(id);
        return "redirect:/asistentes";
    }
    @PutMapping("/actualizaAsistente/{id}")
    public Asistente actualizarParticipante(@RequestBody Asistente nuevoAsistente, @PathVariable Long id){
        return asistenteService.actualizarAsistente(nuevoAsistente, id);
    }
    @GetMapping("/buscar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("dni", new String());
        return "formulario"; // Nombre de la vista Thymeleaf
    }

    // Procesar búsqueda
    @PostMapping("/buscar")
    public String buscarPorDni(@RequestParam String dni, Model model) {
        //System.out.println("DNI recibido: " + dni);
        try {
            Asistente asistente = asistenteService.buscarPorDni(dni);
            //System.out.println("Participante encontrado: " + participante);
            model.addAttribute("asistente", asistente);
        } catch (RuntimeException e) {
            System.out.println("Error al buscar participante: " + e.getMessage());
            model.addAttribute("error", "No se encontró un asistente con DNI "+e.getMessage());
        }
        return "formulario";

    }
    @GetMapping
    public String main(Model model){
        List asistentes=asistenteService.buscarAsistentes();
        model.addAttribute("asistentes",asistentes);
        return "main";
    }
    @GetMapping("/formulario")
    public String formulario2(Model model){
        Asistente asistente=new Asistente();
        model.addAttribute("tituloForm","Lista de Asistentes");
        model.addAttribute("boton", "Enviar");
        model.addAttribute("asistente", asistente);
        return "formulario2";
    }
    @GetMapping("/formulario/{id}")
    public String editar(@PathVariable Long id, Model model){
        Asistente asistente=asistenteService.editar(id);
        model.addAttribute("asistente", asistente);
        model.addAttribute("tituloForm", "Edicion de asistente");
        model.addAttribute("boton", "Modificar");
        return "formulario2";

    }

    @PostMapping
    public String alta(Asistente asistente, Long id,Model model, SessionStatus sessionStatus) {
        System.out.println("asistente de la lista "+asistente);
        try {
            asistenteService.altaOModificacion(asistente,id);
        }catch (AsistenteExistenteException e){
            model.addAttribute("error", e.getMessage());
            model.addAttribute("tituloForm", "Formulario de Asistentes");
            model.addAttribute("boton", "Enviar");

            return "formulario2";
        }
        sessionStatus.setComplete();
//        return "producto/main";
        return "redirect:asistentes";
    }
}
