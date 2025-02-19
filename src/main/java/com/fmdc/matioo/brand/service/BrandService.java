package com.fmdc.matioo.brand.service;

import com.fmdc.matioo.brand.model.Brand;
import com.fmdc.matioo.brand.model.BrandDTO;
import com.fmdc.matioo.brand.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    // Obtener todas las marcas
    public List<Brand> findAllBrands() {
        return brandRepository.findAll();
    }

    // Crear nueva marca
    public Brand createBrand (BrandDTO dto) {
        if (brandRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("El nombre de la marca ya existe.");
        }
        Brand brandModel = new Brand();
        brandModel.setName(dto.getName());
        brandModel.setStatus(dto.getStatus() != null ? dto.getStatus() : true); // Default status = true
        return brandRepository.save(brandModel);
    }

    // Actualizar marca
    public Brand updateBrand (Long id, BrandDTO dto) {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (optionalBrand.isPresent()) {
            throw new IllegalArgumentException("La marca no existe.");
        }

        Brand brandModel = optionalBrand.get();

        if (dto.getName() != null && !dto.getName().equals(brandModel.getName())) {
            if (brandRepository.existsByNameAndIdNot(dto.getName(), id)) {
                throw new IllegalArgumentException("El nombre de la marca ya existe.");
            }
            brandModel.setName(dto.getName());
        }

        if (dto.getStatus() != null) {
            brandModel.setStatus(dto.getStatus());
        }

        return brandRepository.save(brandModel);
    }


        // Obtener todas las marcas activas
        public List<Brand> getActiveBrands(){
            return brandRepository.findByStatus(true);
        }

        // Obtener todas las marcas inactivas
        public List<Brand> getInactiveBrands(){
            return brandRepository.findByStatus(false);
        }

        // Cambiar estado de una marca
        public Brand changeStatus(Long id, Boolean status) {
            Optional<Brand> optionalBrandModel = brandRepository.findById(id);
            if (optionalBrandModel.isEmpty()) {
                throw new IllegalArgumentException("La marca no existe.");
            }

            Brand brandModel = optionalBrandModel.get();
            brandModel.setStatus(status); // Actualiza el estado
            return brandRepository.save(brandModel); // Guarda cambios en BD
        }


        // Elimina una marca
        public void deleteBrand(Long id) {
            brandRepository.deleteById(id);
        }
    }

