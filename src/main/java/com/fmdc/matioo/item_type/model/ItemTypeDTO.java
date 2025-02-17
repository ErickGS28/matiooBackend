package com.fmdc.matioo.item_type.model;

import com.fmdc.matioo.item_model.model.ItemModelDTO.Create;

import jakarta.validation.constraints.*;

public class ItemTypeDTO {

     @NotNull(groups = {Update.class, ChangeStatus.class}, message = "ID no puede ser nulo.")
    private Long id;

    @NotBlank(groups = {Create.class, Update.class}, message = "El nombre no puede estar vacío.")
    @Size(max = 100, groups = {Create.class, Update.class}, message = "El nombre no puede exceder los 100 caracteres.")
    private String name;

    @NotNull(groups = {ChangeStatus.class}, message = "El estado no puede ser nulo.")
    private Boolean status;

    // Constructor vacío
    public ItemTypeDTO() {}

    public ItemTypeDTO(Long id, String name, Boolean status) {
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


    // Validaciones
    public interface Create {}
    public interface Update {}
    public interface ChangeStatus {}
}
