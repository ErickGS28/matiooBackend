package com.fmdc.matioo.item.repository;

import com.fmdc.matioo.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    
    boolean existsBySerialNumber(String serialNumber);
    Optional<Item> findBySerialNumber(String serialNumber);

    boolean existsByCode(String code);
    Optional<Item> findByCode(String code);

    List<Item> findByStatus(boolean status);


    // Verificar si existe un modelo con un nombre específico, excluyendo un ID (útil para actualizaciones)
    //boolean existsByNameAndIdNot(String name, Long id);
    //boolean existsByEmailAndIdNot(String email, Long id);
}