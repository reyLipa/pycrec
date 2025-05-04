package com.todocode.pycrecer.repositories;

import com.todocode.pycrecer.entities.Asistente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AsistenteRepository extends JpaRepository<Asistente, Long> {
    Optional<Asistente> findByDni(String dni);
}
