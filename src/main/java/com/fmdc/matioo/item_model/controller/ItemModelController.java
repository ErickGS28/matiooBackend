package com.fmdc.matioo.item_model.controller;

import com.fmdc.matioo.item_model.model.ItemModelDTO;
import com.fmdc.matioo.item_model.model.ItemModel;
import com.fmdc.matioo.item_model.service.ItemModelService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/models")
@Validated
public class ItemModelController {

    @Autowired
    private ItemModelService itemModelService;

    // Crear un nuevo modelo
    @PostMapping
    public ResponseEntity<ItemModel> createModel(@Validated(ItemModelDTO.Create.class) @RequestBody ItemModelDTO dto) {
        ItemModel createdModel = itemModelService.createItemModel(dto);
        return ResponseEntity.ok(createdModel);
    }

    // Actualizar un modelo existente
    @PutMapping("/{id}")
    public ResponseEntity<ItemModel> updateModel(
            @PathVariable Long id,
            @Validated(ItemModelDTO.Update.class) @RequestBody ItemModelDTO dto) {
        ItemModel updatedModel = itemModelService.updateItemModel(id, dto);
        return ResponseEntity.ok(updatedModel);
    }

    // Cambiar el estado de un modelo
    @PatchMapping("/{id}/status")
    public ResponseEntity<ItemModel> changeStatus(
            @PathVariable Long id,
            @Validated(ItemModelDTO.ChangeStatus.class) @RequestBody ItemModelDTO dto) {
        ItemModel updatedModel = itemModelService.changeStatus(id, dto.getStatus());
        return ResponseEntity.ok(updatedModel);
    }

    // Obtener todos los modelos activos
    @GetMapping("/active")
    public ResponseEntity<List<ItemModel>> getActiveModels() {
        return ResponseEntity.ok(itemModelService.getActiveModels());
    }

    // Obtener todos los modelos inactivos
    @GetMapping("/inactive")
    public ResponseEntity<List<ItemModel>> getInactiveModels() {
        return ResponseEntity.ok(itemModelService.getInactiveModels());
    }

    // Eliminar un modelo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable Long id) {
        itemModelService.deleteItemModel(id);
        return ResponseEntity.noContent().build();
    }
}