package com.fmdc.matioo.brand.controller;

import com.fmdc.matioo.brand.model.BrandDTO;
import com.fmdc.matioo.brand.service.BrandService;
import com.fmdc.matioo.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brands")
@Validated
public class BrandController {
    @Autowired
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    // Obtener todas las marcas
    @GetMapping("/all")
    public ResponseEntity<Message> getAllBrands() {
        return brandService.findAllBrands();
    }

    // Crear nueva marca
    @PostMapping("/save")
    public ResponseEntity<Message> create(@Validated(BrandDTO.Create.class) @RequestBody BrandDTO dto) {
        return brandService.createBrand(dto);
    }


    // Actualizar marca
    @PutMapping("update/{id}")
    public ResponseEntity<Message> update(
            @PathVariable Long id,
            @Validated(BrandDTO.Update.class) @RequestBody BrandDTO dto) {
        return brandService.updateBrand(id, dto);
    }

    // Cambiar estado de una marca
    @PutMapping("/status/{id}")
    public ResponseEntity<Message> changeStatus(@PathVariable Long id, Boolean status) {
        return brandService.changeStatus(id);
    }


    // Obtener todas las marcas activas
    @GetMapping("/active")
    public ResponseEntity<Message> getActiveBrands() {
        return brandService.getActiveBrands();
    }

    // Obtener todas las marcas inactivas
    @GetMapping("/inactive")
    public ResponseEntity<Message> getInactiveBrands() {
        return brandService.getInactiveBrands();
    }
}
