package com.fmdc.matioo.item.controller;

import java.util.List;

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

import com.fmdc.matioo.brand.model.Brand;
import com.fmdc.matioo.item.model.Item;
import com.fmdc.matioo.item.model.ItemDTO;
import com.fmdc.matioo.item.service.ItemService;
import com.fmdc.matioo.item_model.model.ItemModel;
import com.fmdc.matioo.item_type.model.ItemType;
import com.fmdc.matioo.user.model.AppUser;
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

    //BUSQUEDA POR ID
    @GetMapping("/id/{id}")
    public ResponseEntity<Message> getById(@PathVariable Long id) {
        return itemService.findById(id);
    }

    //BUSQUEDA POR MODELO
    //id
    @GetMapping("/byModelId/{modelId}")
    public List<Item> getItemsByModelId(@PathVariable Long modelId) {
        ItemModel model = new ItemModel();
        model.setId(modelId); // Configura el modelo con el ID
        return itemService.getItemsByModel(model);
    }

    //nombre
    @GetMapping("/byModelName/{modelName}")
    public List<Item> getItemsByModelName(@PathVariable String modelName) {
        return itemService.getItemsByModelName(modelName);
    }

    //BUSQUEDA POR MARCA
    //id
    @GetMapping("/byBrandId/{brandId}")
    public List<Item> getItemsByBrandId(@PathVariable Long brandId) {
        Brand brand = new Brand();
        brand.setId(brandId);
        return itemService.getItemsByBrand(brand);
    }

    //nombre
    @GetMapping("/byBrandName/{brandName}")
    public List<Item> getItemsByBrandName(@PathVariable String brandName) {
        return itemService.getItemsByBrandName(brandName);
    }

    //BUSQUEDA POR ITEMTYPE
    //id
    @GetMapping("/byItemTypeId/{itemTypeId}")
    public List<Item> getItemsByItemTypeId(@PathVariable Long itemTypeId) {
        ItemType itemType = new ItemType();
        itemType.setId(itemTypeId);
        return itemService.getItemsByItemType(itemType);
    }

    // nombre
    @GetMapping("/byItemTypeName/{itemTypeName}")
    public List<Item> getItemsByItemTypeName(@PathVariable String itemTypeName) {
        return itemService.getItemsByItemTypeName(itemTypeName);
    }

    //BUSQUEDA POR NUMERO DE SERIE
    @GetMapping("/bySerialNumber/{serialNumber}")
    public ResponseEntity<Message> getBySerialNumber(@PathVariable String serialNumber) {
        return itemService.findBySerialNumber(serialNumber);
    }

    //BUSQUEDA POR CODE
    @GetMapping("/byCode/{code}")
    public ResponseEntity<Message> getByCode(@PathVariable String code) {
        return itemService.findByCode(code);
    }

    //BUSQUEDA POR ASSIGNED TO
    //id
    @GetMapping("/byAssignedToId/{assignedToId}")
    public List<Item> getItemsByAssignedToId(@PathVariable Long assignedToId) {
        AppUser assignedTo = new AppUser();
        assignedTo.setId(assignedToId);
        return itemService.getItemsByAssignedTo(assignedTo);
    }

    // nombre
    @GetMapping("/byAssignedToFullName/{assignedToFullName}")
    public List<Item> getItemsByAssignedToFullName(@PathVariable String assignedToFullName) {
        return itemService.getItemsByAssignedToFullName(assignedToFullName);
    }

    //BUSQUEDA POR LOCATION
    @GetMapping("/byLocation/{location}")
    public ResponseEntity<Message> getByLocation(@PathVariable String location) {
        return itemService.findByLocation(location);
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

    // Obtener todos los modelos activos
    @GetMapping("/active")
    public ResponseEntity<Message> getActiveItems() {
        return itemService.getActiveItems();
    }

    // Obtener todos los modelos inactivos
    @GetMapping("/inactive")
    public ResponseEntity<Message> getInactiveItems() {
        return itemService.getInactiveItems();
    }
}
