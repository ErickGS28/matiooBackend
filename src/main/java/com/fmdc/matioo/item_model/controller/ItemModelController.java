package com.fmdc.matioo.item_model.controller;

import com.fmdc.matioo.item_model.model.ItemModelDTO;
import com.fmdc.matioo.item_model.service.ItemModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item-models")
@Validated
public class ItemModelController {

    @Autowired
    private ItemModelService itemModelService;

    // Obtener todos los modelos
@GetMapping("/all")
public ResponseEntity<?> getAllModels() {
    return itemModelService.getAllModels();
}

    // Crear un nuevo modelo
    @PostMapping("/save")
    public ResponseEntity<?> createModel(@Validated(ItemModelDTO.Create.class) @RequestBody ItemModelDTO dto) {
        return itemModelService.createItemModel(dto);
    }

    // Actualizar un modelo existente
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateModel(
            @PathVariable Long id,
            @Validated(ItemModelDTO.Update.class) @RequestBody ItemModelDTO dto) {
        return itemModelService.updateItemModel(id, dto);
    }

    // Cambiar el estado de un modelo
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> changeStatus(
            @PathVariable Long id,
            @Validated(ItemModelDTO.ChangeStatus.class) @RequestBody ItemModelDTO dto) {
        return itemModelService.changeStatus(id, dto.getStatus());
    }

    // Obtener todos los modelos activos
    @GetMapping("/active")
    public ResponseEntity<?> getActiveModels() {
        return itemModelService.getActiveModels();
    }

    // Obtener todos los modelos inactivos
    @GetMapping("/inactive")
    public ResponseEntity<?> getInactiveModels() {
        return itemModelService.getInactiveModels();
    }

    // Eliminar un modelo
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteModel(@PathVariable Long id) {
        return itemModelService.deleteItemModel(id);
    }
}