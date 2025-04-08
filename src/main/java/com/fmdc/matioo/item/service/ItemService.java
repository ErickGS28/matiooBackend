package com.fmdc.matioo.item.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fmdc.matioo.brand.model.Brand;
import com.fmdc.matioo.brand.repository.BrandRepository;
import com.fmdc.matioo.item.model.Item;
import com.fmdc.matioo.item.model.ItemDTO;
import com.fmdc.matioo.item.repository.ItemRepository;
import com.fmdc.matioo.item_model.model.ItemModel;
import com.fmdc.matioo.item_model.repository.ItemModelRepository;
import com.fmdc.matioo.item_type.model.ItemType;
import com.fmdc.matioo.item_type.repository.ItemTypeRepository;
import com.fmdc.matioo.user.model.AppUser;
import com.fmdc.matioo.user.repository.AppUserRepository;
import com.fmdc.matioo.utils.Message;
import com.fmdc.matioo.utils.TypesResponse;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemTypeRepository itemTypeRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ItemModelRepository itemModelRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    // GET ALL ITEMS
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        return new ResponseEntity<>(new Message(itemRepository.findAll(), "Lista de bienes", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // FIND ITEM BY ID
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Long id) {
        return itemRepository.findById(id)
                .map(item -> new ResponseEntity<>(new Message(item, "Bien encontrado", TypesResponse.SUCCESS), HttpStatus.OK))
                .orElse(new ResponseEntity<>(new Message("El bien no fue encontrado", TypesResponse.ERROR), HttpStatus.NOT_FOUND));
    }

    // FIND BY MODEL (id)
    public List<Item> getItemsByModel(ItemModel model) {
        return itemRepository.findByModel(model);
    }

    // FIND BY MODEL (name)
    public List<Item> getItemsByModelName(String modelName) {
        return itemRepository.findByModel_Name(modelName);
    }

    // FIND BY ITEMTYPE (id)
    public List<Item> getItemsByItemType(ItemType itemType) {
        return itemRepository.findByItemType(itemType);
    }

    // FIND BY ITEMTYPE (name)
    public List<Item> getItemsByItemTypeName(String itemTypeName) {
        return itemRepository.findByItemType_Name(itemTypeName);
    }

    // FIND BY BRAND (id)
    public List<Item> getItemsByBrand(Brand brand) {
        return itemRepository.findByBrand(brand);
    }

    // FIND BY BRAND (name)
    public List<Item> getItemsByBrandName(String brandName) {
        return itemRepository.findByBrand_Name(brandName);
    }

    // FIND ITEM BY SERIAL NUMBER
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findBySerialNumber(String serialNumber) {
        return itemRepository.findBySerialNumber(serialNumber)
                .map(item -> new ResponseEntity<>(new Message(item, "Bien encontrado", TypesResponse.SUCCESS), HttpStatus.OK))
                .orElse(new ResponseEntity<>(new Message("El bien no fue encontrado", TypesResponse.ERROR), HttpStatus.NOT_FOUND));
    }

    // FIND ITEM BY CODE
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findByCode(String code) {
        return itemRepository.findByCode(code)
                .map(item -> new ResponseEntity<>(new Message(item, "Bien encontrado", TypesResponse.SUCCESS), HttpStatus.OK))
                .orElse(new ResponseEntity<>(new Message("El bien no fue encontrado", TypesResponse.ERROR), HttpStatus.NOT_FOUND));
    }

    // FIND BY ASSIGNEDTO (id)
    public List<Item> getItemsByAssignedTo(AppUser assignedTo) {
        return itemRepository.findByAssignedTo(assignedTo);
    }

    // FIND BY ASSIGNEDTO (name)
    public List<Item> getItemsByAssignedToFullName(String assignedToFullName) {
        return itemRepository.findByAssignedTo_FullName(assignedToFullName);
    }

    // FIND ITEM BY LOCATION
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findByLocation(String location) {
        return itemRepository.findByLocation(location)
                .map(item -> new ResponseEntity<>(new Message(item, "Bien encontrado", TypesResponse.SUCCESS), HttpStatus.OK))
                .orElse(new ResponseEntity<>(new Message("El bien no fue encontrado", TypesResponse.ERROR), HttpStatus.NOT_FOUND));
    }

    // CREATE ITEM
    public ResponseEntity<Message> createItem(ItemDTO dto) {
        if (itemRepository.existsBySerialNumber(dto.getSerialNumber())) {
            return ResponseEntity.badRequest().body(new Message("El número de serie ya existe.", TypesResponse.WARNING));
        }
        if (itemRepository.existsByCode(dto.getCode())) {
            return ResponseEntity.badRequest().body(new Message("El código ya existe.", TypesResponse.WARNING));
        }
        try {
            // Cargar las entidades completas a partir de los ids del DTO
            ItemType itemType = itemTypeRepository.findById(dto.getItemTypeId())
                    .orElseThrow(() -> new RuntimeException("Tipo de bien no encontrado"));
            Brand brand = brandRepository.findById(dto.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
            ItemModel model = itemModelRepository.findById(dto.getModelId())
                    .orElseThrow(() -> new RuntimeException("Modelo no encontrado"));

            // Se valida si el dueño viene nulo
            AppUser owner = null;
            if (dto.getOwnerId() != null) {
                owner = appUserRepository.findById(dto.getOwnerId())
                        .orElseThrow(() -> new RuntimeException("Dueño no encontrado"));
            }

            AppUser assignedTo = null;
            if (dto.getAssignedToId() != null) {
                assignedTo = appUserRepository.findById(dto.getAssignedToId())
                        .orElseThrow(() -> new RuntimeException("Usuario asignado no encontrado"));
            }

            /**
             * Creación del item, ahora incluyendo 'name'.
             */
            Item item = new Item(
                    itemType,
                    brand,
                    model,
                    dto.getSerialNumber(),
                    dto.getCode(),
                    owner,
                    assignedTo,
                    dto.getLocation(),
                    dto.getName(),   // <-- Asignación del nuevo campo
                    true
            );

            itemRepository.save(item);
            return new ResponseEntity<>(new Message(item, "El bien se ha creado correctamente", TypesResponse.SUCCESS), HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new Message("El número de serie o código ya está en uso", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
    }


    // UPDATE ITEM
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> updateItem(ItemDTO dto) {
        Optional<Item> optionalItem = itemRepository.findById(dto.getId());
        if (!optionalItem.isPresent()) {
            return new ResponseEntity<>(new Message("El bien no se encuentra", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        // Verificar si el serialNumber o code ya están en uso
        Item existingItem = optionalItem.get();
        if (itemRepository.existsBySerialNumber(dto.getSerialNumber())
                && !existingItem.getSerialNumber().equals(dto.getSerialNumber())) {
            return new ResponseEntity<>(new Message("El número de serie ya está en uso", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (itemRepository.existsByCode(dto.getCode())
                && !existingItem.getCode().equals(dto.getCode())) {
            return new ResponseEntity<>(new Message("El código ya está en uso", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        try {
            // Cargar las entidades completas
            ItemType itemType = itemTypeRepository.findById(dto.getItemTypeId())
                    .orElseThrow(() -> new RuntimeException("Tipo de bien no encontrado"));
            Brand brand = brandRepository.findById(dto.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
            ItemModel model = itemModelRepository.findById(dto.getModelId())
                    .orElseThrow(() -> new RuntimeException("Modelo no encontrado"));

            // Si el owner viene nulo, se asigna null; de lo contrario se busca la entidad
            AppUser owner = null;
            if (dto.getOwnerId() != null) {
                owner = appUserRepository.findById(dto.getOwnerId())
                        .orElseThrow(() -> new RuntimeException("Dueño no encontrado"));
            }

            // Mismo manejo para assignedTo
            AppUser assignedTo = null;
            if (dto.getAssignedToId() != null) {
                assignedTo = appUserRepository.findById(dto.getAssignedToId())
                        .orElseThrow(() -> new RuntimeException("Usuario asignado no encontrado"));
            }

            // Actualizar el objeto existente
            existingItem.setItemType(itemType);
            existingItem.setBrand(brand);
            existingItem.setModel(model);
            existingItem.setSerialNumber(dto.getSerialNumber());
            existingItem.setCode(dto.getCode());
            existingItem.setOwner(owner);
            existingItem.setAssignedTo(assignedTo);
            existingItem.setLocation(dto.getLocation());
            existingItem.setName(dto.getName());

            itemRepository.save(existingItem);
            return new ResponseEntity<>(new Message(existingItem, "El bien se ha actualizado correctamente", TypesResponse.SUCCESS), HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new Message("Error de integridad de datos", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
    }


    @Transactional(readOnly = true)
    public ResponseEntity<Message> getActiveItems() {
        List<Item> activeItems = itemRepository.findByStatus(true);
        if (activeItems.isEmpty()) {
            return new ResponseEntity<>(new Message("No hay bienes activos.", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(activeItems, "Bienes activos obtenidos con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // BIENES INACTIVOS
    @Transactional(readOnly = true)
    public ResponseEntity<Message> getInactiveItems() {
        List<Item> inactiveItems = itemRepository.findByStatus(false);
        if (inactiveItems.isEmpty()) {
            return new ResponseEntity<>(new Message("No hay bienes inactivos.", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(inactiveItems, "Bienes inactivos obtenidos con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // CHANGE ITEM STATUS (ENABLE/DISABLE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> changeStatus(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (!optionalItem.isPresent()) {
            return new ResponseEntity<>(new Message("El bien no existe", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        Item item = optionalItem.get();
        item.setStatus(!item.isStatus());

        itemRepository.save(item);
        return new ResponseEntity<>(new Message(item, "El status del bien ha sido cambiado", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> assignItem(Long id, Long userId) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (!optionalItem.isPresent()) {
            return new ResponseEntity<>(new Message("El bien no existe", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        // Cargar el usuario completo
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Item item = optionalItem.get();
        item.setAssignedTo(user);
        itemRepository.save(item);

        return new ResponseEntity<>(new Message(item, "El bien ha sido asignado correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> unassignItem(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (!optionalItem.isPresent()) {
            return new ResponseEntity<>(new Message("El bien no existe", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        Item item = optionalItem.get();
        item.setAssignedTo(null);
        itemRepository.save(item);

        return new ResponseEntity<>(new Message(item, "El bien ha sido desasignado correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }

        // Buscar items que no tengan asignado a nadie
        @Transactional(readOnly = true)
        public ResponseEntity<Message> getUnassignedItems() {
            List<Item> unassignedItems = itemRepository.findByAssignedToIsNull();
            if (unassignedItems.isEmpty()) {
                return new ResponseEntity<>(new Message("No hay bienes sin asignar.", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new Message(unassignedItems, "Bienes sin asignar obtenidos con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
        }

}
