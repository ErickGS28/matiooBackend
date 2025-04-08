package com.fmdc.matioo.item_model.repository;

import com.fmdc.matioo.item_model.model.ItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemModelRepository extends JpaRepository<ItemModel, Long> {
    
    // Verificar si existe un modelo con un nombre específico
    boolean existsByName(String name);

    Optional<ItemModel> findByName(String name);


    // Verificar si existe un modelo con un nombre específico, excluyendo un ID (útil para actualizaciones)
    boolean existsByNameAndIdNot(String name, Long id);

    
    List<ItemModel> findByStatus(boolean status);

 
}