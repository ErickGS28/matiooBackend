package com.fmdc.matioo.item_model.controller;

import com.fmdc.matioo.item_model.model.ItemModelDTO;
import com.fmdc.matioo.item_model.service.ItemModelService;
import com.fmdc.matioo.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> getAllModels() {
        return itemModelService.getAllModels();
    }

    // Obtener modelo por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> findById(@PathVariable Long id) {
        return itemModelService.findById(id);
    }
    
    // Crear un nuevo modelo
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> createModel(@Validated(ItemModelDTO.Create.class) @RequestBody ItemModelDTO dto) {
        return itemModelService.createItemModel(dto);
    }

    // Actualizar un modelo existente (sin recibir id en la URL)
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> updateModel(@Validated(ItemModelDTO.Update.class) @RequestBody ItemModelDTO dto) {
        return itemModelService.updateItemModel(dto);
    }

    // Cambiar el estado de un modelo (solo con id en la URL, sin body)
    @PutMapping("/change-status/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> changeStatus(@PathVariable Long id) {
        return itemModelService.changeStatus(id);
    }

    // Obtener todos los modelos activos
    @GetMapping("/active")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> getActiveModels() {
        return itemModelService.getActiveModels();
    }

    // Obtener todos los modelos inactivos
    @GetMapping("/inactive")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> getInactiveModels() {
        return itemModelService.getInactiveModels();
    }

    // Eliminar un modelo
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> deleteModel(@PathVariable Long id) {
        return itemModelService.deleteItemModel(id);
    }

    @PostMapping(value = "/save-with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Message> createModelWithImage(
            @RequestPart("dto") @Validated(ItemModelDTO.Create.class) ItemModelDTO dto,
            @RequestPart("image") MultipartFile file) {
        return itemModelService.createItemModelWithImage(dto, file);
    }

}
