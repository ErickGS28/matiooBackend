package com.fmdc.matioo.item.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fmdc.matioo.item.model.Item;
import com.fmdc.matioo.item.model.ItemDTO;
import com.fmdc.matioo.item.repository.ItemRepository;
import com.fmdc.matioo.utils.Message;
import com.fmdc.matioo.utils.TypesResponse;

@Service
@Transactional

public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

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

    // CREATE ITEM
    public ResponseEntity<Message> createItem(ItemDTO dto) {
        if (itemRepository.existsBySerialNumber(dto.getSerialNumber())) {
            return ResponseEntity.badRequest().body(new Message("El número de serie ya existe.", TypesResponse.WARNING));
        }

        if (itemRepository.existsByCode(dto.getCode())) {
            return ResponseEntity.badRequest().body(new Message("El código ya existe.", TypesResponse.WARNING));
        }

        
        try {
            Item item = new Item(
                    dto.getItemType(),
                    dto.getBrand(),
                    dto.getModel(),
                    dto.getSerialNumber(),
                    dto.getCode(),
                    dto.getOwner(),
                    dto.getAssignedTo(),
                    dto.getLocation(),
                    true
            );

            item.setStatus(true);
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
        if (itemRepository.existsBySerialNumber(dto.getSerialNumber()) && !optionalItem.get().getSerialNumber().equals(dto.getSerialNumber())) {
            return new ResponseEntity<>(new Message("El número de serie ya está en uso", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (itemRepository.existsByCode(dto.getCode()) && !optionalItem.get().getCode().equals(dto.getCode())) {
            return new ResponseEntity<>(new Message("El código ya está en uso", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        // Actualizar el objeto
        Item item = optionalItem.get();
        item.setItemType(dto.getItemType());
        item.setBrand(dto.getBrand());
        item.setModel(dto.getModel());
        item.setSerialNumber(dto.getSerialNumber());
        item.setCode(dto.getCode());
        item.setOwner(dto.getOwner());
        item.setAssignedTo(dto.getAssignedTo());
        item.setLocation(dto.getLocation());
        item.setStatus(dto.isStatus());

        itemRepository.save(item);

        return new ResponseEntity<>(new Message(item, "El bien se ha actualizado correctamente", TypesResponse.SUCCESS), HttpStatus.OK);
    }


    // GET ACTIVE ITEMS
    public List<Item> getActiveModels() {
        return itemRepository.findByStatus(true);
    }

    // GET INACTIVE ITEMS
    public List<Item> getInactiveModels() {
        return itemRepository.findByStatus(false);
    }
    
    // CHANGE USER STATUS (ENABLE/DISABLE)
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

}



