package com.fmdc.matioo.item_model.service;


import com.fmdc.matioo.item_model.model.ItemModel;
import com.fmdc.matioo.item_model.model.ItemModelDTO;
import com.fmdc.matioo.item_model.repository.ItemModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemModelService {

    @Autowired
    private ItemModelRepository itemModelRepository;

    // Crear un nuevo modelo
    public ItemModel createItemModel(ItemModelDTO dto) {
        if (itemModelRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("El nombre del modelo ya existe.");
        }
        ItemModel model = new ItemModel();
        model.setName(dto.getName());
        model.setPhoto(dto.getPhoto());
        model.setStatus(dto.getStatus() != null ? dto.getStatus() : true); // Default status = true
        return itemModelRepository.save(model);
    }

    // Actualizar un modelo existente
    public ItemModel updateItemModel(Long id, ItemModelDTO dto) {
        Optional<ItemModel> optionalModel = itemModelRepository.findById(id);
        if (optionalModel.isEmpty()) {
            throw new IllegalArgumentException("El modelo no existe.");
        }

        ItemModel model = optionalModel.get();

        if (dto.getName() != null && !dto.getName().equals(model.getName())) {
            if (itemModelRepository.existsByNameAndIdNot(dto.getName(), id)) {
                throw new IllegalArgumentException("El nombre del modelo ya existe.");
            }
            model.setName(dto.getName());
        }

        if (dto.getPhoto() != null) {
            model.setPhoto(dto.getPhoto());
        }

        if (dto.getStatus() != null) {
            model.setStatus(dto.getStatus());
        }

        return itemModelRepository.save(model);
    }

    // Obtener todos los modelos activos
    public List<ItemModel> getActiveModels() {
        return itemModelRepository.findByStatus(true);
    }

    // Obtener todos los modelos inactivos
    public List<ItemModel> getInactiveModels() {
        return itemModelRepository.findByStatus(false);
    }

    // Cambiar el estado de un modelo
public ItemModel changeStatus(Long id, Boolean status) {
    Optional<ItemModel> optionalModel = itemModelRepository.findById(id);
    if (optionalModel.isEmpty()) {
        throw new IllegalArgumentException("El modelo no existe.");
    }

    ItemModel model = optionalModel.get();
    model.setStatus(status); // Actualiza el estado
    return itemModelRepository.save(model); // Guarda los cambios en la base de datos
}

    // Eliminar un modelo
    public void deleteItemModel(Long id) {
        itemModelRepository.deleteById(id);
    }
}