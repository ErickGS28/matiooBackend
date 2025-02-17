package com.fmdc.matioo.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmdc.matioo.item.model.ItemDTO;
import com.fmdc.matioo.item.service.ItemService;
import com.fmdc.matioo.utils.Message;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/all")
    public ResponseEntity<Message> getAllUsers() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getById(@PathVariable Long id) {
        return itemService.findById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Message> createItem(@Validated(ItemDTO.Create.class) @RequestBody ItemDTO dto) {
        return itemService.createItem(dto);
    }

    @PutMapping("/update")
    public ResponseEntity<Message> updateItem(@Validated(ItemDTO.Update.class) @RequestBody ItemDTO dto) {
        return itemService.updateItem(dto);
    }

    @PutMapping("/change-status/{id}")
    public ResponseEntity<Message> changeStatus(@PathVariable Long id) {
        return itemService.changeStatus(id);
    }
}
