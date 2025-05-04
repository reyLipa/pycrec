package com.todocode.pycrecer.services;

import com.todocode.pycrecer.entities.Asistente;
import com.todocode.pycrecer.exceptions.AsistenteExistenteException;
import com.todocode.pycrecer.exceptions.AsistenteNoEncontradoException;
import com.todocode.pycrecer.repositories.AsistenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AsistenteService {
    @Autowired
    private AsistenteRepository asistenteRepository;

    public Asistente crearAsistente(Asistente asistente){
        return asistenteRepository.save(asistente);
    }
    public List<Asistente> buscarAsistentes(){
        return asistenteRepository.findAll();
    }

    public Asistente buscarPorId(Long id){
        return asistenteRepository.findById(id).orElse(null);
    }

    public Asistente buscarPorDni(String dni){
        return asistenteRepository.findByDni(dni).orElseThrow(()->new AsistenteNoEncontradoException(dni));
    }

    public void eliminar(Long id){
        asistenteRepository.deleteById(id);
    }
    public Asistente actualizarAsistente(Asistente nuevoAsistente, Long id){
        Asistente asistenteActual=buscarPorId(id);
        asistenteActual.setNombre(nuevoAsistente.getNombre());
        asistenteActual.setApellido(nuevoAsistente.getApellido());
        asistenteActual.setDni(nuevoAsistente.getDni());
        asistenteActual.setFecha(nuevoAsistente.getFecha());
        return asistenteRepository.save(asistenteActual);

    }

    public Asistente editar(Long id){
        return asistenteRepository.getById(id);
    }

    //    public boolean altaOModificacion(Asistente asistente, Long id){
//        if (id!=null){
//        this.actualizarAsistente(asistente,id);
//    }else {
//        this.crearAsistente(asistente);
//    }
//    return Boolean.TRUE;
//    }
    public boolean altaOModificacion(Asistente asistente, Long id) {
        if (asistente.getId() == null) {
            Optional<Asistente> asistenteExistente = asistenteRepository.findByDni(asistente.getDni().trim());
            if (asistenteExistente.isPresent()) {
                throw new AsistenteExistenteException("Asistente con dni " + asistente.getDni() + " ya existe");
            }
        }
        asistenteRepository.save(asistente);
        return Boolean.TRUE;
    }
}
