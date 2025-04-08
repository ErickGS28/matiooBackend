package com.fmdc.matioo.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ItemDTO {

    @NotNull(groups = {Update.class, ChangeStatus.class}, message = "El ID no puede ser nulo.")
    private Long id;

    @NotNull(groups = {Create.class, Update.class}, message = "El id del tipo de bien no puede estar vacío.")
    private Long itemTypeId;

    @NotNull(groups = {Create.class, Update.class}, message = "El id de la marca no puede estar vacío.")
    private Long brandId;

    @NotNull(groups = {Create.class, Update.class}, message = "El id del modelo del bien no puede estar vacío.")
    private Long modelId;

    @NotBlank(groups = {Create.class, Update.class}, message = "El número de serie del bien no puede estar vacío.")
    @Size(max = 100, groups = {Create.class, Update.class}, message = "El número de serie no puede exceder los 100 caracteres.")
    private String serialNumber;

    @NotBlank(groups = {Create.class, Update.class}, message = "El código del bien no puede estar vacío.")
    @Size(max = 100, groups = {Create.class, Update.class}, message = "El código no puede exceder los 100 caracteres.")
    private String code;

    private Long ownerId;

    private Long assignedToId;

    @NotBlank(groups = {Create.class, Update.class}, message = "La ubicación no puede estar vacía.")
    @Size(max = 100, groups = {Create.class, Update.class}, message = "La ubicación no puede exceder los 100 caracteres.")
    private String location;

    /**
     * Nueva propiedad 'name' en el DTO.
     * Ajusta las validaciones según tus requisitos.
     */
    @Size(max = 255, groups = {Create.class, Update.class}, message = "El nombre no puede exceder los 255 caracteres.")
    private String name;

    public ItemDTO() {
    }

    public ItemDTO(Long id, Long itemTypeId, Long brandId, Long modelId,
                   String serialNumber, String code, Long ownerId,
                   Long assignedToId, String location, String name) {
        this.id = id;
        this.itemTypeId = itemTypeId;
        this.brandId = brandId;
        this.modelId = modelId;
        this.serialNumber = serialNumber;
        this.code = code;
        this.ownerId = ownerId;
        this.assignedToId = assignedToId;
        this.location = location;
        this.name = name;
    }

    // GETTERS & SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Long itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Interfaces para validación
    public interface Create {}
    public interface Update {}
    public interface ChangeStatus {}
}
