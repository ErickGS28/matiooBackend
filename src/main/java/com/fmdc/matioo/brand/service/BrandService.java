package com.fmdc.matioo.brand.service;

import com.fmdc.matioo.brand.model.Brand;
import com.fmdc.matioo.brand.model.BrandDTO;
import com.fmdc.matioo.brand.repository.BrandRepository;
import com.fmdc.matioo.utils.Message;
import com.fmdc.matioo.utils.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BrandService {
    
    @Autowired
    private BrandRepository brandRepository;

    // Obtener todas las marcas
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAllBrands() {
        return new ResponseEntity<>(new Message(brandRepository.findAll(), "Lista de marcas obtenida con éxito.", TypesResponse.SUCCESS), HttpStatus.OK
        );
    }

    // Crear nueva marca
    @Transactional(readOnly = true)
    public ResponseEntity<Message> createBrand(BrandDTO dto) {
        if (brandRepository.existsByName(dto.getName())) {return new ResponseEntity<>(new Message("El nombre de la marca ya existe.", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getName() == null || dto.getName().isEmpty()) {
            return new ResponseEntity<>(new Message("El nombre de la marca no debe de ser nulo", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        if (dto.getName().length() < 100) {
            return new ResponseEntity<>(new Message("El nombre de la marca no debe de pasar de los 100 caracteres", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
    
        Brand brandModel = new Brand();
        brandModel.setName(dto.getName());
        brandModel.setStatus(dto.getStatus() != null ? dto.getStatus() : true); // Default status = true
    
        brandRepository.save(brandModel);
        return new ResponseEntity<>(
                new Message(brandModel, "Marca creada con éxito.", TypesResponse.SUCCESS),
                HttpStatus.CREATED
        );
    }
    

    // Actualizar marca
    @Transactional(readOnly = true)
    public ResponseEntity<Message> updateBrand(Long id, BrandDTO dto) {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (!optionalBrand.isPresent()) {
            return new ResponseEntity<>(new Message("La marca no existe.", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        Brand brandModel = optionalBrand.get();

        if (dto.getName() != null && !dto.getName().isEmpty() && !dto.getName().equals(brandModel.getName()) && dto.getName().length() < 100) {
            if (brandRepository.existsByNameAndIdNot(dto.getName(), id)) {
                return new ResponseEntity<>(new Message("El nombre de la marca ya existe.", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
            }
            brandModel.setName(dto.getName());
        }

        if (dto.getStatus() != null) {
            brandModel.setStatus(dto.getStatus());
        }
        brandRepository.save(brandModel);
        return new ResponseEntity<>(new Message(brandModel, "Marca actualizada con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }
    

    // Obtener todas las marcas activas
    @Transactional(readOnly = true)
    public ResponseEntity<Message> getActiveBrands() {
        return new ResponseEntity<>(new Message(brandRepository.findByStatus(true), "Lista de marcas activas obtenida con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Obtener todas las marcas inactivas
    public ResponseEntity<Message> getInactiveBrands() {
        return new ResponseEntity<>(new Message(brandRepository.findByStatus(false), "Lista de marcas inactivas obtenida con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Cambiar estado de una marca
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> changeStatus(Long id) {
        Optional<Brand> optionalBrandModel = brandRepository.findById(id);

        if (!optionalBrandModel.isPresent()) {
            return new ResponseEntity<>(new Message("La marca no existe", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Brand brandModel = optionalBrandModel.get();
        brandModel.setStatus(!brandModel.isStatus()); // Actualiza el estado

        brandRepository.save(brandModel);
        String statusMessage = brandModel.isStatus() ? "Marca activada con éxito." : "Marca desactivada con éxito.";

        return new ResponseEntity<>(new Message(brandModel, statusMessage, TypesResponse.SUCCESS), HttpStatus.OK);
    }
}
