package com.fmdc.matioo.item.model;
import com.fmdc.matioo.item_type.model.ItemType;
import com.fmdc.matioo.item_model.model.ItemModel;
import com.fmdc.matioo.brand.model.Brand;
import com.fmdc.matioo.user.model.AppUser;
import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_type_id")
    private ItemType itemType;

    @ManyToOne(optional = false)
    private Brand brand;

    @ManyToOne(optional = false)
    private ItemModel model;

    @Column(name = "serial_number", length = 50, nullable = false, unique = true)
    private String serialNumber;

    @Column(name = "code", length = 50, nullable = false, unique = true)
    private String code;

    @ManyToOne()
    private AppUser responsible;

    @Column(name = "location", length = 150)
    private String location;

    @Column(name = "status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean status = true;

    public Item() {
    }

    public Item(Long id, ItemType itemType, Brand brand, ItemModel model, String serialNumber, String code, AppUser responsible, String location, boolean status) {
        this.id = id;
        this.itemType = itemType;
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
        this.code = code;
        this.responsible = responsible;
        this.location = location;
        this.status = status;
    }

    public Item(ItemType itemType, Brand brand, ItemModel model, String serialNumber, String code, AppUser responsible, String location, boolean status) {
        this.itemType = itemType;
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
        this.code = code;
        this.responsible = responsible;
        this.location = location;
        this.status = status;
    }

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

    public AppUser getResponsible() {
        return responsible;
    }

    public void setResponsible(AppUser responsible) {
        this.responsible = responsible;
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
}
