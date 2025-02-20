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

    @GetMapping("/all")
    public ResponseEntity<Message> getAllBrands() {
        return brandService.findAllBrands();
    }

    @PostMapping("/save")
    public ResponseEntity<Message> create(@Validated(BrandDTO.Create.class) @RequestBody BrandDTO dto) {
        return brandService.createBrand(dto);
    }

    @PutMapping("/update")
    public ResponseEntity<Message> update(@Validated(BrandDTO.Update.class) @RequestBody BrandDTO dto) {
        return brandService.updateBrand(dto);
    }

    @PutMapping("/change-status/{id}")
    public ResponseEntity<Message> changeStatus(@PathVariable Long id) {
        return brandService.changeStatus(id);
    }

    @GetMapping("/active")
    public ResponseEntity<Message> getActiveBrands() {
        return brandService.getActiveBrands();
    }

    @GetMapping("/inactive")
    public ResponseEntity<Message> getInactiveBrands() {
        return brandService.getInactiveBrands();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable Long id) {
        return brandService.findById(id);
    }
}
