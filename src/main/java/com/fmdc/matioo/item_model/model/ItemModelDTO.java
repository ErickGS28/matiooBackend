package com.fmdc.matioo.item_model.model;

import jakarta.validation.constraints.*;

public class ItemModelDTO {

    @NotNull(groups = {Update.class, ChangeStatus.class}, message = "ID no puede ser nulo.")
    private Long id;

    @NotBlank(groups = {Create.class, Update.class}, message = "El nombre no puede estar vacío.")
    @Size(max = 100, groups = {Create.class, Update.class}, message = "El nombre no puede exceder los 100 caracteres.")
    private String name;

    @Size(max = 255, groups = {Create.class, Update.class}, message = "La URL de la foto no puede tener más de 255 caracteres.")
    private String photo;

    //@NotNull(groups = {Create.class, Update.class, ChangeStatus.class}, message = "El estado no puede ser nulo.")
    private Boolean status;

    // Constructor vacío
    public ItemModelDTO() {}

    public ItemModelDTO(Long id, String name, String photo, Boolean status) {
        this.id = id;
        this.name = name;
        this.photo = photo;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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