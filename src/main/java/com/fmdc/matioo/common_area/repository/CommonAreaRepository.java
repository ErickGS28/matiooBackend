package com.fmdc.matioo.common_area.repository;

import com.fmdc.matioo.common_area.model.CommonArea;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface CommonAreaRepository extends JpaRepository<CommonArea, Long> {

    // Verifica si existe un área común con un nombre específico
    boolean existsByName(String name);

    // Verifica si existe un área común con un nombre específico, excluyendo un ID (para actualización)
    boolean existsByNameAndIdNot(String name, Long id);

    Optional<CommonArea> findByName(String name);

    // Método para obtener áreas por estado
    List<CommonArea> findByStatus(boolean status);
}
