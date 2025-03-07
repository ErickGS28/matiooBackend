package com.fmdc.matioo.common_area.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommonAreaDTO {

    @NotNull(groups = {Update.class, ChangeStatus.class}, message = "El ID no puede ser nulo.")
    private Long id;

    @NotBlank(groups = {Create.class, Update.class}, message = "El nombre no puede estar vacío.")
    @Size(max = 100, groups = {Create.class, Update.class}, message = "El nombre no puede exceder los 100 caracteres.")
    private String name;

    @NotNull(groups = {ChangeStatus.class}, message = "El estado no puede ser nulo.")
    private Boolean status;

    // Constructor vacío
    public CommonAreaDTO() {}

    // Constructor con parámetros
    public CommonAreaDTO(Long id, String name, Boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    // Interfaces para validaciones
    public interface Create {}
    public interface Update {}
    public interface ChangeStatus {}
}
