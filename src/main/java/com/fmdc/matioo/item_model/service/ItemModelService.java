package com.fmdc.matioo.item_model.service;

import com.fmdc.matioo.item_model.model.ItemModel;
import com.fmdc.matioo.item_model.model.ItemModelDTO;
import com.fmdc.matioo.item_model.repository.ItemModelRepository;
import com.fmdc.matioo.utils.Message;
import com.fmdc.matioo.utils.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemModelService {

    @Autowired
    private ItemModelRepository itemModelRepository;


    // Obtener todos los modelos (sin filtrar por estado)
public ResponseEntity<Message> getAllModels() {
    List<ItemModel> allModels = itemModelRepository.findAll();
    if (allModels.isEmpty()) {
        return new ResponseEntity<>(new Message("No hay modelos disponibles.", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(new Message(allModels, "Todos los modelos obtenidos con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
}

    // Crear un nuevo modelo
    public ResponseEntity<Message> createItemModel(ItemModelDTO dto) {
        if (itemModelRepository.existsByName(dto.getName())) {
            return new ResponseEntity<>(new Message("El nombre del modelo ya existe.", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        ItemModel model = new ItemModel();
        model.setName(dto.getName());
        model.setPhoto(dto.getPhoto());
        model.setStatus(dto.getStatus() != null ? dto.getStatus() : true); // Default status = true
        ItemModel savedModel = itemModelRepository.save(model);
        return new ResponseEntity<>(new Message(savedModel, "Modelo creado con éxito.", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    // Actualizar un modelo existente
    public ResponseEntity<Message> updateItemModel(Long id, ItemModelDTO dto) {
        Optional<ItemModel> optionalModel = itemModelRepository.findById(id);
        if (optionalModel.isEmpty()) {
            return new ResponseEntity<>(new Message("El modelo no existe.", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        ItemModel model = optionalModel.get();
        if (dto.getName() != null && !dto.getName().equals(model.getName())) {
            if (itemModelRepository.existsByNameAndIdNot(dto.getName(), id)) {
                return new ResponseEntity<>(new Message("El nombre del modelo ya existe.", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
            }
            model.setName(dto.getName());
        }
        if (dto.getPhoto() != null) {
            model.setPhoto(dto.getPhoto());
        }
        if (dto.getStatus() != null) {
            model.setStatus(dto.getStatus());
        }
        ItemModel updatedModel = itemModelRepository.save(model);
        return new ResponseEntity<>(new Message(updatedModel, "Modelo actualizado con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Obtener todos los modelos activos
    public ResponseEntity<Message> getActiveModels() {
        List<ItemModel> activeModels = itemModelRepository.findByStatus(true);
        if (activeModels.isEmpty()) {
            return new ResponseEntity<>(new Message("No hay modelos activos.", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(activeModels, "Modelos activos obtenidos con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Obtener todos los modelos inactivos
    public ResponseEntity<Message> getInactiveModels() {
        List<ItemModel> inactiveModels = itemModelRepository.findByStatus(false);
        if (inactiveModels.isEmpty()) {
            return new ResponseEntity<>(new Message("No hay modelos inactivos.", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(inactiveModels, "Modelos inactivos obtenidos con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Cambiar el estado de un modelo
    public ResponseEntity<Message> changeStatus(Long id, Boolean status) {
        Optional<ItemModel> optionalModel = itemModelRepository.findById(id);
        if (optionalModel.isEmpty()) {
            return new ResponseEntity<>(new Message("El modelo no existe.", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        ItemModel model = optionalModel.get();
        model.setStatus(status); // Actualiza el estado
        ItemModel updatedModel = itemModelRepository.save(model);
        String statusMessage = status ? "activado" : "desactivado";
        return new ResponseEntity<>(new Message(updatedModel, "Modelo " + statusMessage + " con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Eliminar un modelo
    public ResponseEntity<Message> deleteItemModel(Long id) {
        Optional<ItemModel> optionalModel = itemModelRepository.findById(id);
        if (optionalModel.isEmpty()) {
            return new ResponseEntity<>(new Message("El modelo no existe.", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        itemModelRepository.deleteById(id);
        return new ResponseEntity<>(new Message("Modelo eliminado con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }
}