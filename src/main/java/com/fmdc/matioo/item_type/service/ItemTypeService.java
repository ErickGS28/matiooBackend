package com.fmdc.matioo.item_type.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fmdc.matioo.item_type.model.ItemType;
import com.fmdc.matioo.item_type.model.ItemTypeDTO;
import com.fmdc.matioo.item_type.repository.ItemTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ItemTypeService {
    @Autowired
    private ItemTypeRepository itemTypeRepository;


    // Obtener todos los tipos de bien
    public List<ItemType> findAllItemTypes() {
        return itemTypeRepository.findAll();
    }


    // Crear nuevo tipo de bien
    public ItemType createItemType (ItemTypeDTO dto) {
        if (itemTypeRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("El nombre del tipo de bien ya existe.");
        }
        ItemType item_type = new ItemType();
        item_type.setName(dto.getName());
        item_type.setStatus(dto.getStatus() != null ? dto.getStatus() : true); // Default status = true
        return itemTypeRepository.save(item_type);
    }

    // Actualizar tipo de bien
    public ItemType updateItemType (Long id, ItemTypeDTO dto) {
        Optional<ItemType> optionalItemType = itemTypeRepository.findById(id);
        if (optionalItemType.isPresent()) {
            throw new IllegalArgumentException("El tipo de bien no existe.");
        }

        ItemType item_type = optionalItemType.get();

        if (dto.getName() != null && !dto.getName().equals(item_type.getName())) {
            if (itemTypeRepository.existsByNameAndIdNot(dto.getName(), id)) {
                throw new IllegalArgumentException("El nombre del tipo de bien ya existe.");
            }
            item_type.setName(dto.getName());
        }

        if (dto.getStatus() != null) {
            item_type.setStatus(dto.getStatus());
        }

        return itemTypeRepository.save(item_type);
    }

    // Obtener todos los tipos de bien activos
    public List<ItemType> getActiveItemTypes(){
        return itemTypeRepository.findByStatus(true);
    }

    // Obtener todos los tipos de bien inactivos
    public List<ItemType> getInactiveItemTypes(){
        return itemTypeRepository.findByStatus(false);
    }


    // Cambiar estado de un tipo de bien
    public ItemType changeStatus(Long id, Boolean status) {
        Optional<ItemType> optionalItemType = itemTypeRepository.findById(id);
        if (optionalItemType.isEmpty()) {
            throw new IllegalArgumentException("El tipo de bien no existe.");
        }

        ItemType item_type = optionalItemType.get();
        item_type.setStatus(status); // Actualiza el estado
        return itemTypeRepository.save(item_type); // Guarda cambios en BD
    }


    // Elimina un tipo de bien
    public void deleteItemType(Long id) {
        itemTypeRepository.deleteById(id);
    }
}
