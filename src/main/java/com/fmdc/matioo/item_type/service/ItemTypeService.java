package com.fmdc.matioo.item_type.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fmdc.matioo.item_type.model.ItemType;
import com.fmdc.matioo.item_type.model.ItemTypeDTO;
import com.fmdc.matioo.item_type.repository.ItemTypeRepository;
import com.fmdc.matioo.utils.Message;
import com.fmdc.matioo.utils.TypesResponse;

import java.util.Optional;

@Service
public class ItemTypeService {

    @Autowired
    private ItemTypeRepository itemTypeRepository;

    // Obtener todos los tipos de bien
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAllItemTypes() {
        return new ResponseEntity<>(new Message(itemTypeRepository.findAll(), "Lista de tipos de bien obtenida con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Obtener tipo de bien por ID
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Long id) {
        Optional<ItemType> optionalItemType = itemTypeRepository.findById(id);
        if (!optionalItemType.isPresent()) {
            return new ResponseEntity<>(new Message("El tipo de bien no existe.", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(optionalItemType.get(), "Tipo de bien obtenido con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Crear nuevo tipo de bien
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> createItemType(ItemTypeDTO dto) {
        if (itemTypeRepository.existsByName(dto.getName())) {
            return new ResponseEntity<>(new Message("El nombre del tipo de bien ya existe.", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        ItemType itemType = new ItemType();
        itemType.setName(dto.getName());
        itemType.setStatus(dto.getStatus() != null ? dto.getStatus() : true); // Default status = true
        itemTypeRepository.save(itemType);
        return new ResponseEntity<>(new Message(itemType, "Tipo de bien creado con éxito.", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    // Actualizar tipo de bien
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> updateItemType(ItemTypeDTO dto) {
        Optional<ItemType> optionalItemType = itemTypeRepository.findById(dto.getId());
        if (!optionalItemType.isPresent()) {
            return new ResponseEntity<>(new Message("El tipo de bien no existe.", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        ItemType itemType = optionalItemType.get();

        if (dto.getName() != null && !dto.getName().isEmpty() && !dto.getName().equals(itemType.getName())) {
            if (itemTypeRepository.existsByNameAndIdNot(dto.getName(), dto.getId())) {
                return new ResponseEntity<>(new Message("El nombre del tipo de bien ya existe.", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
            }
            itemType.setName(dto.getName());
        }

        if (dto.getStatus() != null) {
            itemType.setStatus(dto.getStatus());
        }

        itemTypeRepository.save(itemType);
        return new ResponseEntity<>(new Message(itemType, "Tipo de bien actualizado con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Obtener todos los tipos de bien activos
    @Transactional(readOnly = true)
    public ResponseEntity<Message> getActiveItemTypes() {
        return new ResponseEntity<>(new Message(itemTypeRepository.findByStatus(true), "Lista de tipos de bien activos obtenida con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Obtener todos los tipos de bien inactivos
    @Transactional(readOnly = true)
    public ResponseEntity<Message> getInactiveItemTypes() {
        return new ResponseEntity<>(new Message(itemTypeRepository.findByStatus(false), "Lista de tipos de bien inactivos obtenida con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Cambiar estado de un tipo de bien
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> changeStatus(Long id) {
        Optional<ItemType> optionalItemType = itemTypeRepository.findById(id);
        if (!optionalItemType.isPresent()) {
            return new ResponseEntity<>(new Message("El tipo de bien no existe.", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }
        ItemType itemType = optionalItemType.get();
        itemType.setStatus(!itemType.isStatus()); // Cambia el estado automáticamente
        itemTypeRepository.save(itemType);
        String statusMessage = itemType.isStatus() ? "Tipo de bien activado con éxito." : "Tipo de bien desactivado con éxito.";
        return new ResponseEntity<>(new Message(itemType, statusMessage, TypesResponse.SUCCESS), HttpStatus.OK);
    }
}
