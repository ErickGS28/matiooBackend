package com.fmdc.matioo.item_type.controller;

import com.fmdc.matioo.item_type.model.ItemTypeDTO;
import com.fmdc.matioo.item_type.service.ItemTypeService;
import com.fmdc.matioo.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item-types")
@Validated
public class ItemTypeController {
    @Autowired
    private final ItemTypeService itemTypeService;

    public ItemTypeController(ItemTypeService itemTypeService) {
        this.itemTypeService = itemTypeService;
    }

    // Obtener todos los tipos de bien
    @GetMapping("/all")
    public ResponseEntity<Message> getAllItemTypes() {
        return itemTypeService.findAllItemTypes();
    }

    // Crear nuevo tipo de bien
    @PostMapping("/save")
    public ResponseEntity<Message> create(@Validated(ItemTypeDTO.Create.class) @RequestBody ItemTypeDTO dto) {
        return itemTypeService.createItemType(dto);
    }

    // Actualizar tipo de bien
    @PutMapping("update/{id}")
    public ResponseEntity<Message> update(
            @PathVariable Long id,
            @Validated(ItemTypeDTO.Update.class) @RequestBody ItemTypeDTO dto) {
        return itemTypeService.updateItemType(id, dto);
    }

    // Cambiar estado de un tipo de bien
    @PutMapping("/status/{id}")
    public ResponseEntity<Message> changeStatus(@PathVariable Long id) {
        return itemTypeService.changeStatus(id);
    }

    // Obtener todos tipos de bien activos
    @GetMapping("/active")
    public ResponseEntity<Message> getActiveItemTypes() {
        return itemTypeService.getActiveItemTypes();
    }

    // Obtener todos los tipos de bien inactivos
    @GetMapping("/inactive")
    public ResponseEntity<Message> getInactiveItemTypes() {
        return itemTypeService.getInactiveItemTypes();     }
}
