package com.fmdc.matioo.item_type.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fmdc.matioo.brand.model.Brand;
import com.fmdc.matioo.brand.model.BrandDTO;
import com.fmdc.matioo.item.model.Item;
import com.fmdc.matioo.item_type.model.ItemType;
import com.fmdc.matioo.item_type.model.ItemTypeDTO;
import com.fmdc.matioo.item_type.service.ItemTypeService;

import java.util.List;

@RestController
@RequestMapping("/item-type-models")
@Validated
public class ItemTypeController {
    @Autowired
    private ItemTypeService itemTypeService;

    // Obtener todos los tipos de bien
    @GetMapping("/all")
    public ResponseEntity<List<ItemType>> getAllItemTypes() {
        return ResponseEntity.ok(itemTypeService.findAllItemTypes());
    }
    // Crear nuevo tipo de bien
    @PostMapping("/save")
    public ResponseEntity<ItemType> create(@Validated(ItemTypeDTO.Create.class) @RequestBody ItemTypeDTO dto) {
        ItemType create_itemType = itemTypeService.createItemType(dto);
        return ResponseEntity.ok(create_itemType);
    }


    // Actualizar tipo de bien
    @PutMapping("update/{id}")
    public ResponseEntity<ItemType> update(
            @PathVariable Long id,
            @Validated(ItemTypeDTO.Update.class) @RequestBody ItemTypeDTO dto) {
        ItemType update_ItemType = itemTypeService.updateItemType(id, dto);
        return ResponseEntity.ok(update_ItemType);
    }


    // Cambiar estado de un tipo de bien
    @PatchMapping("/{id}/status")
    public ResponseEntity<ItemType> changeStatus(
            @PathVariable Long id,
            @Validated(ItemTypeDTO.ChangeStatus.class) @RequestBody ItemTypeDTO dto) {
        ItemType update_itemType = itemTypeService.updateItemType(id, dto);
        return ResponseEntity.ok(update_itemType);
    }


    // Obtener todos tipos de bien activos
    @GetMapping("/active")
    public ResponseEntity<List<ItemType>> getActiveBrands() {
        return ResponseEntity.ok(itemTypeService.getActiveItemTypes());
    }



    // Obtener todos los tipos de bien inactivos
    @GetMapping("/inactive")
    public ResponseEntity<List<ItemType>> getInactiveItemTypes() {
        return ResponseEntity.ok(itemTypeService.getInactiveItemTypes());
    }



    // Eliminar tipo de bien
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemTypeService.deleteItemType(id);
        return ResponseEntity.noContent().build();
    }
}
