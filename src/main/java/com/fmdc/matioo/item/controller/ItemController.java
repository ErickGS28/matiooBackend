package com.fmdc.matioo.item.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<Message> getAllUsers() {
        return itemService.findAll();
    }

    //BUSQUEDA POR ID
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/id/{id}")
    public ResponseEntity<Message> getById(@PathVariable Long id) {
        return itemService.findById(id);
    }


    //BUSQUEDA POR MODELO
    //id
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/byModelId/{modelId}")
    public List<Item> getItemsByModelId(@PathVariable Long modelId) {
        ItemModel model = new ItemModel();
        model.setId(modelId); // Configura el modelo con el ID
        return itemService.getItemsByModel(model);
    }
    //nombre
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/byModelName/{modelName}")
    public List<Item> getItemsByModelName(@PathVariable String modelName) {
        return itemService.getItemsByModelName(modelName);
    }
    //BUSQUEDA POR MARCA
    //id
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/byBrandId/{brandId}")
    public List<Item> getItemsByBrandId(@PathVariable Long brandId) {
        Brand brand = new Brand();
        brand.setId(brandId);
        return itemService.getItemsByBrand(brand);
    }

    //nombre
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/byBrandName/{brandName}")
    public List<Item> getItemsByBrandName(@PathVariable String brandName) {
        return itemService.getItemsByBrandName(brandName);
    }

    //BUSQUEDA POR ITEMTYPE
    //id
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/byItemTypeId/{itemTypeId}")
    public List<Item> getItemsByItemTypeId(@PathVariable Long itemTypeId) {
        ItemType itemType = new ItemType();
        itemType.setId(itemTypeId);
        return itemService.getItemsByItemType(itemType);
    }

    // nombre
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/byItemTypeName/{itemTypeName}")
    public List<Item> getItemsByItemTypeName(@PathVariable String itemTypeName) {
        return itemService.getItemsByItemTypeName(itemTypeName);
    }

    //BUSQUEDA POR NUMERO DE SERIE
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/bySerialNumber/{serialNumber}")
    public ResponseEntity<Message> getBySerialNumber(@PathVariable String serialNumber) {
        return itemService.findBySerialNumber(serialNumber);
    }

    //BUSQUEDA POR CODE
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/byCode/{code}")
    public ResponseEntity<Message> getByCode(@PathVariable String code) {
        return itemService.findByCode(code);
    }

    //BUSQUEDA POR ASSIGNED TO
    //id
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INTERN', 'RESPONSIBLE')")
    @GetMapping("/byAssignedToId/{assignedToId}")
    public List<Item> getItemsByAssignedToId(@PathVariable Long assignedToId) {
        AppUser assignedTo = new AppUser();
        assignedTo.setId(assignedToId);
        return itemService.getItemsByAssignedTo(assignedTo);
    }

    // nombre
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/byAssignedToFullName/{assignedToFullName}")
    public List<Item> getItemsByAssignedToFullName(@PathVariable String assignedToFullName) {
        return itemService.getItemsByAssignedToFullName(assignedToFullName);
    }

    //BUSQUEDA POR LOCATION
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/byLocation/{location}")
    public ResponseEntity<Message> getByLocation(@PathVariable String location) {
        return itemService.findByLocation(location);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Message> createItem(@Validated(ItemDTO.Create.class) @RequestBody ItemDTO dto) {
        return itemService.createItem(dto);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<Message> updateItem(@Validated(ItemDTO.Update.class) @RequestBody ItemDTO dto) {
        return itemService.updateItem(dto);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/change-status/{id}")
    public ResponseEntity<Message> changeStatus(@PathVariable Long id) {
        return itemService.changeStatus(id);
    }

    // Obtener todos los modelos activos
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/active")
    public ResponseEntity<Message> getActiveItems() {
        return itemService.getActiveItems();
    }

    // Obtener todos los modelos inactivos
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/inactive")
    public ResponseEntity<Message> getInactiveItems() {
        return itemService.getInactiveItems();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INTERN')")
    @PutMapping("/{id}/assign")
    public ResponseEntity<Message> assignItem(@PathVariable Long id, @RequestBody Long userId) {
        // Lógica: validar que el bien exista, que el usuario que se asigna sea válido,
        // y actualizar solo el campo assignedTo.
        // Además, podrías restringir que si el rol es INTERN, solo pueda asignarse a sí mismo.
        return itemService.assignItem(id, userId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INTERN')")
    @PutMapping("/{id}/unassign")
    public ResponseEntity<Message> unassignItem(@PathVariable Long id) {
        // Lógica: validar que el bien exista y actualizar el campo assignedTo a null.
        return itemService.unassignItem(id);
    }


        // Buscar items que no tengan asignado a nadie
        @PreAuthorize("hasAnyAuthority('ADMIN','INTERN')")
        @GetMapping("/unassigned")
        public ResponseEntity<Message> getUnassignedItems() {
            return itemService.getUnassignedItems();
        }


    @PreAuthorize("hasAnyAuthority('ADMIN','RESPONSIBLE')")
    @GetMapping("/byOwnerId/{ownerId}")
    public ResponseEntity<Message> getItemsByOwner(@PathVariable Long ownerId) {
        return itemService.getItemsByOwner(ownerId);
    }
}
