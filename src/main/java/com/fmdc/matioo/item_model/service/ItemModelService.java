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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemModelService {

    @Autowired
    private ItemModelRepository itemModelRepository;

    // Obtener todos los modelos
    @Transactional(readOnly = true)
    public ResponseEntity<Message> getAllModels() {
        List<ItemModel> allModels = itemModelRepository.findAll();
        if (allModels.isEmpty()) {
            return new ResponseEntity<>(new Message("No hay modelos disponibles.", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Message(allModels, "Todos los modelos obtenidos con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Long id) {
        Optional<ItemModel> optionalModel = itemModelRepository.findById(id);
        return optionalModel.map(itemModel -> new ResponseEntity<>(new Message(itemModel, "Modelo obtenido con éxito.", TypesResponse.SUCCESS), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new Message("El modelo no existe.", TypesResponse.ERROR), HttpStatus.NOT_FOUND));
    }


    // Crear un nuevo modelo
    @Transactional
    public ResponseEntity<Message> createItemModel(ItemModelDTO dto) {
        if (itemModelRepository.existsByName(dto.getName())) {
            return new ResponseEntity<>(new Message("El nombre del modelo ya existe.", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        ItemModel model = new ItemModel();
        model.setName(dto.getName());
        model.setPhoto(dto.getPhoto());
        model.setStatus(dto.getStatus() != null ? dto.getStatus() : true);
        itemModelRepository.save(model);
        return new ResponseEntity<>(new Message(model, "Modelo creado con éxito.", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }

    // Actualizar un modelo existente (ID obtenido del DTO)
    @Transactional
    public ResponseEntity<Message> updateItemModel(ItemModelDTO dto) {
        if (dto.getId() == null) {
            return new ResponseEntity<>(new Message("El ID del modelo es obligatorio.", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        Optional<ItemModel> optionalModel = itemModelRepository.findById(dto.getId());
        if (optionalModel.isEmpty()) {
            return new ResponseEntity<>(new Message("El modelo no existe.", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        ItemModel model = optionalModel.get();
        if (dto.getName() != null && !dto.getName().equals(model.getName())) {
            if (itemModelRepository.existsByNameAndIdNot(dto.getName(), dto.getId())) {
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

        itemModelRepository.save(model);
        return new ResponseEntity<>(new Message(model, "Modelo actualizado con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Obtener todos los modelos activos
    @Transactional(readOnly = true)
    public ResponseEntity<Message> getActiveModels() {
        List<ItemModel> activeModels = itemModelRepository.findByStatus(true);
        if (activeModels.isEmpty()) {
            return new ResponseEntity<>(new Message("No hay modelos activos.", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(activeModels, "Modelos activos obtenidos con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Obtener todos los modelos inactivos
    @Transactional(readOnly = true)
    public ResponseEntity<Message> getInactiveModels() {
        List<ItemModel> inactiveModels = itemModelRepository.findByStatus(false);
        if (inactiveModels.isEmpty()) {
            return new ResponseEntity<>(new Message("No hay modelos inactivos.", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Message(inactiveModels, "Modelos inactivos obtenidos con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Cambiar el estado de un modelo (solo con id en la URL)
    @Transactional
    public ResponseEntity<Message> changeStatus(Long id) {
        Optional<ItemModel> optionalModel = itemModelRepository.findById(id);
        if (optionalModel.isEmpty()) {
            return new ResponseEntity<>(new Message("El modelo no existe.", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        ItemModel model = optionalModel.get();
        model.setStatus(!model.isStatus()); // Cambia el estado automáticamente

        itemModelRepository.save(model);
        String statusMessage = model.isStatus() ? "Modelo activado con éxito." : "Modelo desactivado con éxito.";

        return new ResponseEntity<>(new Message(model, statusMessage, TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // Eliminar un modelo
    @Transactional
    public ResponseEntity<Message> deleteItemModel(Long id) {
        Optional<ItemModel> optionalModel = itemModelRepository.findById(id);
        if (optionalModel.isEmpty()) {
            return new ResponseEntity<>(new Message("El modelo no existe.", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
        itemModelRepository.deleteById(id);
        return new ResponseEntity<>(new Message("Modelo eliminado con éxito.", TypesResponse.SUCCESS), HttpStatus.OK);
    }
}
