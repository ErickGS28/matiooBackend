package com.fmdc.matioo.common_area.controller;

import com.fmdc.matioo.common_area.model.CommonAreaDTO;
import com.fmdc.matioo.common_area.service.CommonAreaService;
import com.fmdc.matioo.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commonareas")
public class CommonAreaController {


    @Autowired
    private CommonAreaService commonAreaService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> getAll() {
        return commonAreaService.findAll();
    }
    @GetMapping("/active")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> getActiveAreas() {
        return commonAreaService.findActiveAreas();
    }

    @GetMapping("/inactive")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> getInactiveAreas() {
        return commonAreaService.findInactiveAreas();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> getById(@PathVariable Long id) {
        return commonAreaService.findById(id);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> create(@Validated(CommonAreaDTO.Create.class) @RequestBody CommonAreaDTO dto) {
        return commonAreaService.create(dto);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> update(@Validated(CommonAreaDTO.Update.class) @RequestBody CommonAreaDTO dto) {
        return commonAreaService.update(dto);
    }

    @PutMapping("/change-status/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> changeStatus(@PathVariable Long id) {
        return commonAreaService.changeStatus(id);
    }
}
