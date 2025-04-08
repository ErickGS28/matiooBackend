package com.fmdc.matioo.common_area.service;

import com.fmdc.matioo.common_area.model.CommonArea;
import com.fmdc.matioo.common_area.model.CommonAreaDTO;
import com.fmdc.matioo.common_area.repository.CommonAreaRepository;
import com.fmdc.matioo.utils.Message;
import com.fmdc.matioo.utils.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class CommonAreaService {

    @Autowired
    private CommonAreaRepository commonAreaRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        return new ResponseEntity<>(
                new Message(commonAreaRepository.findAll(), "Lista de áreas comunes obtenida con éxito.", TypesResponse.SUCCESS),
                HttpStatus.OK
        );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findActiveAreas() {
        return new ResponseEntity<>(
                new Message(commonAreaRepository.findByStatus(true), "Lista de áreas activas obtenida con éxito.", TypesResponse.SUCCESS),
                HttpStatus.OK
        );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findInactiveAreas() {
        return new ResponseEntity<>(
                new Message(commonAreaRepository.findByStatus(false), "Lista de áreas inactivas obtenida con éxito.", TypesResponse.SUCCESS),
                HttpStatus.OK
        );
    }


    @Transactional
    public ResponseEntity<Message> create(CommonAreaDTO dto) {
        if (commonAreaRepository.existsByName(dto.getName())) {
            return new ResponseEntity<>(
                    new Message("El nombre del área ya existe.", TypesResponse.WARNING),
                    HttpStatus.BAD_REQUEST
            );
        }

        CommonArea commonArea = new CommonArea();
        commonArea.setName(dto.getName());
        // Se establece el estado; si no se indica, se usa true por defecto
        commonArea.setStatus(dto.getStatus() != null ? dto.getStatus() : true);

        commonAreaRepository.save(commonArea);
        return new ResponseEntity<>(
                new Message(commonArea, "Área creada con éxito.", TypesResponse.SUCCESS),
                HttpStatus.CREATED
        );
    }

    @Transactional
    public ResponseEntity<Message> update(CommonAreaDTO dto) {
        if (dto.getId() == null) {
            return new ResponseEntity<>(
                    new Message("El ID del área es obligatorio.", TypesResponse.WARNING),
                    HttpStatus.BAD_REQUEST
            );
        }

        Optional<CommonArea> optionalCommonArea = commonAreaRepository.findById(dto.getId());
        if (!optionalCommonArea.isPresent()) {
            return new ResponseEntity<>(
                    new Message("El área no existe.", TypesResponse.WARNING),
                    HttpStatus.NOT_FOUND
            );
        }

        CommonArea commonArea = optionalCommonArea.get();
        if (dto.getName() != null && !dto.getName().isEmpty() && dto.getName().length() <= 100) {
            if (commonAreaRepository.existsByNameAndIdNot(dto.getName(), dto.getId())) {
                return new ResponseEntity<>(
                        new Message("El nombre del área ya existe.", TypesResponse.WARNING),
                        HttpStatus.BAD_REQUEST
                );
            }
            commonArea.setName(dto.getName());
        }

        if (dto.getStatus() != null) {
            commonArea.setStatus(dto.getStatus());
        }

        commonAreaRepository.save(commonArea);
        return new ResponseEntity<>(
                new Message(commonArea, "Área actualizada con éxito.", TypesResponse.SUCCESS),
                HttpStatus.OK
        );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Long id) {
        Optional<CommonArea> commonArea = commonAreaRepository.findById(id);
        if (!commonArea.isPresent()) {
            return new ResponseEntity<>(
                    new Message("El área no existe.", TypesResponse.WARNING),
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
                new Message(commonArea.get(), "Área obtenida con éxito.", TypesResponse.SUCCESS),
                HttpStatus.OK
        );
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> changeStatus(Long id) {
        Optional<CommonArea> optionalCommonArea = commonAreaRepository.findById(id);
        if (!optionalCommonArea.isPresent()) {
            return new ResponseEntity<>(
                    new Message("El área no existe.", TypesResponse.WARNING),
                    HttpStatus.BAD_REQUEST
            );
        }

        CommonArea commonArea = optionalCommonArea.get();
        // Se invierte el estado actual
        commonArea.setStatus(!commonArea.isStatus());

        commonAreaRepository.save(commonArea);
        String statusMessage = commonArea.isStatus() ? "Área activada con éxito." : "Área desactivada con éxito.";

        return new ResponseEntity<>(
                new Message(commonArea, statusMessage, TypesResponse.SUCCESS),
                HttpStatus.OK
        );
    }
}
