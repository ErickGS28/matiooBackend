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
    private BrandService brandModelService;

    // Crear nueva marca
    @PostMapping
    public ResponseEntity<Brand> create(@Validated(BrandDTO.Create.class) @RequestBody BrandDTO dto) {
        Brand createModel = brandModelService.createBrandModel(dto);
        return ResponseEntity.ok(createModel);
    }




    // Actualizar marca
    @PutMapping("/{id}")
    public ResponseEntity<Brand> update(
            @PathVariable Long id,
            @Validated(BrandDTO.Update.class) @RequestBody BrandDTO dto
    ) {
        Brand updateModel = brandModelService.updateBrandModel(id, dto);
        return ResponseEntity.ok(updateModel);
    }




    // Cambiar estado de una marca
    @PatchMapping("/{id}/status")
    public ResponseEntity<Brand> changeStatus(
            @PathVariable Long id,
            @Validated(BrandDTO.ChangeStatus.class) @RequestBody BrandDTO dto
    ) {
        Brand updateModel = brandModelService.updateBrandModel(id, dto);
        return ResponseEntity.ok(updateModel);
    }



    // Obtener todas las marcas ativas
    @GetMapping("/active")
    public ResponseEntity<List<Brand>> getActiveBrands() {
        return ResponseEntity.ok(brandModelService.getActiveBrands());
    }




    // Obtener todas las marcas inactivas
    @GetMapping("/inactive")
    public ResponseEntity<List<Brand>> getInactiveBrands() {
        return ResponseEntity.ok(brandModelService.getInactiveBrands());
    }




    // Eliminar marca
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        brandModelService.deleteBrandModel(id);
        return ResponseEntity.noContent().build();
    }
}
