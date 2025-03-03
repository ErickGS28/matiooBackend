package com.fmdc.matioo.item.model;
import com.fmdc.matioo.brand.model.Brand;
import com.fmdc.matioo.item_model.model.ItemModel;
import com.fmdc.matioo.item_type.model.ItemType;
import com.fmdc.matioo.user.model.AppUser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



public class ItemDTO {

    @NotNull(groups = {Update.class, ChangeStatus.class}, message = "El ID no puede ser nulo.")
    private Long id;
    
    @NotNull(groups = {Create.class, Update.class}, message = "El Tipo del bien no puede estar vacío.")
    // Aquí removí el @Size, ya que ItemType es un objeto, no una cadena
    private ItemType itemType;
    
    @NotNull(groups = {Create.class, Update.class}, message = "La Marca del bien no puede estar vacía.")
    // Aquí removí el @Size, ya que Brand es un objeto, no una cadena
    private Brand brand;
    
    @NotNull(groups = {Create.class, Update.class}, message = "El Modelo del bien no puede estar vacía.")
    // Aquí removí el @Size, ya que ItemModel es un objeto, no una cadena
    private ItemModel model;
    
    @NotBlank(groups = {Create.class, Update.class}, message = "El Numero de serie del bien no puede estar vacía.")
    @Size(max = 100, groups = {Create.class, Update.class}, message = "El Numero de serie del bien no puede exceder los 100 caracteres.")
    private String serialNumber;
    
    @NotBlank(groups = {Create.class, Update.class}, message = "El Codigo del bien no puede estar vacío.")
    @Size(max = 100, groups = {Create.class, Update.class}, message = "El Codigo del bien no puede exceder los 100 caracteres.")
    private String code;
    
    @NotNull(groups = {Create.class, Update.class}, message = "El Dueño del bien no puede estar vacío.")
    private AppUser owner;
    
    @NotNull(groups = {Create.class, Update.class}, message = "El Asignado del bien no puede estar vacío.")
    private AppUser assignedTo;    
    
    @NotBlank(groups = {Create.class, Update.class}, message = "La ubicacion no puede estar vacía.")
    @Size(max = 100, groups = {Create.class, Update.class}, message = "La ubicacion no puede exceder los 100 caracteres.")
    private String location;
    
    @NotNull(groups = {ChangeStatus.class}, message = "El estado no puede estar vacío")
    private Boolean status;
    
    public ItemDTO() {}

    public ItemDTO(Long id, ItemType itemType, Brand brand, ItemModel model,
                String serialNumber, String code, AppUser owner,
                AppUser assignedTo, String location, boolean status) {
        this.id = id;
        this.itemType = itemType;
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
        this.code = code;
        this.owner = owner;
        this.assignedTo = assignedTo;
        this.location = location;
        this.status = status;
    }

   // GETTERS & SETTERS
   public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public ItemType getItemType() {
    return itemType;
}

public void setItemType(ItemType itemType) {
    this.itemType = itemType;
}

public Brand getBrand() {
    return brand;
}

public void setBrand(Brand brand) {
    this.brand = brand;
}

public ItemModel getModel() {
    return model;
}

public void setModel(ItemModel model) {
    this.model = model;
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

public AppUser getOwner() {
    return owner;
}

public void setOwner(AppUser owner) {
    this.owner = owner;
}

public AppUser getAssignedTo() {
    return assignedTo;
}

public void setAssignedTo(AppUser assignedTo) {
    this.assignedTo = assignedTo;
}

public String getLocation() {
    return location;
}

public void setLocation(String location) {
    this.location = location;
}

public boolean isStatus() {
    return status;
}

public void setStatus(boolean status) {
    this.status = status;
}
    

    // Validaciones
    public interface Create {}
    public interface Update {}
    public interface ChangeStatus {}
}