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
    private BrandRepository brandModelRepository;
    // Crear nueva marca
    public Brand createBrandModel (BrandDTO dto) {
        if (brandModelRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("El nombre de la marca ya existe.");
        }
        Brand brandModel = new Brand();
        brandModel.setName(dto.getName());
        brandModel.setStatus(dto.getStatus() != null ? dto.getStatus() : true); // Default status = true
        return brandModelRepository.save(brandModel);
    }




    // Actualizar marca
    public Brand updateBrandModel (Long id, BrandDTO dto) {
        Optional<Brand> optionalBrandModel = brandModelRepository.findById(id);
        if (optionalBrandModel.isPresent()) {
            throw new IllegalArgumentException("La marca no existe.");
        }

        Brand brandModel = optionalBrandModel.get();

        if (dto.getName() != null && !dto.getName().equals(brandModel.getName())) {
            if (brandModelRepository.existsByNameAndIdNot(dto.getName(), id)) {
                throw new IllegalArgumentException("El nombre de la marca ya existe.");
            }
            brandModel.setName(dto.getName());
        }

        if (dto.getStatus() != null) {
            brandModel.setStatus(dto.getStatus());
        }

        return brandModelRepository.save(brandModel);
    }


        // Obtener todas las marcas activas
        public List<Brand> getActiveBrands(){
            return brandModelRepository.findByStatus(true);
        }

        // Obtener todas las marcas inactivas
        public List<Brand> getInactiveBrands(){
            return brandModelRepository.findByStatus(false);
        }

        // Cambiar estado de una marca
        public Brand changeStatus(Long id, Boolean status) {
            Optional<Brand> optionalBrandModel = brandModelRepository.findById(id);
            if (optionalBrandModel.isEmpty()) {
                throw new IllegalArgumentException("La marca no existe.");
            }

            Brand brandModel = optionalBrandModel.get();
            brandModel.setStatus(status); // Actualiza el estado
            return brandModelRepository.save(brandModel); // Guarda cambios en BD
        }


        // Elimina una marca
        public void deleteBrandModel(Long id) {
        brandModelRepository.deleteById(id);
        }
    }

