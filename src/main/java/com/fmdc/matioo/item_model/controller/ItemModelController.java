package com.fmdc.matioo.item_model.controller;

import com.fmdc.matioo.item_model.model.ItemModelDTO;
import com.fmdc.matioo.item_model.service.ItemModelService;
import com.fmdc.matioo.utils.Message;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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

    @GetMapping("/image/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Resource> getImage(@PathVariable Long id) {
        String imagePath = itemModelService.getImagePathById(id);
        if (imagePath == null) {
            return ResponseEntity.notFound().build();
        }

        File imageFile = new File(imagePath);
        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(imageFile));
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Ajusta el tipo de contenido según el tipo de imagen
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + imageFile.getName())
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping(value = "/update-with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> updateModelWithImage(
            @RequestPart("dto") @Validated(ItemModelDTO.Update.class) ItemModelDTO dto,
            @RequestPart("image") MultipartFile file) {
        return itemModelService.updateItemModelWithImage(dto, file);
    }

}
