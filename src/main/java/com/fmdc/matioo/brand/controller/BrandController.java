package com.fmdc.matioo.brand.controller;

import com.fmdc.matioo.brand.model.Brand;
import com.fmdc.matioo.brand.model.BrandDTO;
import com.fmdc.matioo.brand.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/models")
@Validated
public class BrandController {
    @Autowired
    private BrandService brandService;

    // Crear nueva marca
    @PostMapping
    public ResponseEntity<Brand> create(@Validated(BrandDTO.Create.class) @RequestBody BrandDTO dto) {
        Brand create_brand = brandService.createBrand(dto);
        return ResponseEntity.ok(create_brand);
    }




    // Actualizar marca
    @PutMapping("/{id}")
    public ResponseEntity<Brand> update(
            @PathVariable Long id,
            @Validated(BrandDTO.Update.class) @RequestBody BrandDTO dto) {
        Brand updateBrand = brandService.updateBrand(id, dto);
        return ResponseEntity.ok(updateBrand);
    }




    // Cambiar estado de una marca
    @PatchMapping("/{id}/status")
    public ResponseEntity<Brand> changeStatus(
            @PathVariable Long id,
            @Validated(BrandDTO.ChangeStatus.class) @RequestBody BrandDTO dto) {
        Brand updateBrand = brandService.updateBrand(id, dto);
        return ResponseEntity.ok(updateBrand);
    }



    // Obtener todas las marcas activas
    @GetMapping("/active")
    public ResponseEntity<List<Brand>> getActiveBrands() {
        return ResponseEntity.ok(brandService.getActiveBrands());
    }




    // Obtener todas las marcas inactivas
    @GetMapping("/inactive")
    public ResponseEntity<List<Brand>> getInactiveBrands() {
        return ResponseEntity.ok(brandService.getInactiveBrands());
    }




    // Eliminar marca
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}
