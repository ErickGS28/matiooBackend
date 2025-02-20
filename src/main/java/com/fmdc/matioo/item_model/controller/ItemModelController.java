package com.fmdc.matioo.item_model.controller;

import com.fmdc.matioo.item_model.model.ItemModelDTO;
import com.fmdc.matioo.item_model.service.ItemModelService;
import com.fmdc.matioo.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item-models")
@Validated
public class ItemModelController {

    @Autowired
    private final ItemModelService itemModelService;

    public ItemModelController(ItemModelService itemModelService) {
        this.itemModelService = itemModelService;
    }

    // Obtener todos los modelos
    @GetMapping("/all")
    public ResponseEntity<Message> getAllModels() {
        return itemModelService.getAllModels();
    }

    // Obtener modelo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable Long id) {
        return itemModelService.findById(id);
    }
    
    // Crear un nuevo modelo
    @PostMapping("/save")
    public ResponseEntity<Message> createModel(@Validated(ItemModelDTO.Create.class) @RequestBody ItemModelDTO dto) {
        return itemModelService.createItemModel(dto);
    }

    // Actualizar un modelo existente (sin recibir id en la URL)
    @PutMapping("/update")
    public ResponseEntity<Message> updateModel(@Validated(ItemModelDTO.Update.class) @RequestBody ItemModelDTO dto) {
        return itemModelService.updateItemModel(dto);
    }

    // Cambiar el estado de un modelo (solo con id en la URL, sin body)
    @PutMapping("/change-status/{id}")
    public ResponseEntity<Message> changeStatus(@PathVariable Long id) {
        return itemModelService.changeStatus(id);
    }

    // Obtener todos los modelos activos
    @GetMapping("/active")
    public ResponseEntity<Message> getActiveModels() {
        return itemModelService.getActiveModels();
    }

    // Obtener todos los modelos inactivos
    @GetMapping("/inactive")
    public ResponseEntity<Message> getInactiveModels() {
        return itemModelService.getInactiveModels();
    }

    // Eliminar un modelo
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteModel(@PathVariable Long id) {
        return itemModelService.deleteItemModel(id);
    }
}
