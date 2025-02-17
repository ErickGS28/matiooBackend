package com.fmdc.matioo.item_type.repository;

import com.fmdc.matioo.item_type.model.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {
    // Verificar si existe un tipo de 'bien' con un nombre específico
    boolean existsByName(String name);

    Optional<ItemType> findByName(String name);


    // Verificar si existe un tipo de 'bien' con un nombre específico, excluyendo un ID (útil para actualizaciones)
    boolean existsByNameAndIdNot(String name, Long id);


    List<ItemType> findByStatus(boolean status);
}
