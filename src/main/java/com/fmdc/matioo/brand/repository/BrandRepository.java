package com.fmdc.matioo.brand.repository;

import com.fmdc.matioo.brand.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    // Verificar si existe una marca con un nombre específico
    boolean existsByName(String name);

    Optional<Brand> findByName(String name);


    // Verificar si existe una marca con un nombre específico, excluyendo un ID (útil para actualizaciones)
    boolean existsByNameAndIdNot(String name, Long id);


    List<Brand> findByStatus(boolean status);
}
