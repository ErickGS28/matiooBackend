package com.fmdc.matioo.item_model.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fmdc.matioo.item.model.Item;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "models")
public class ItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "photo")
    private String photo;

    @Column(name = "status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean status = true;

    @OneToMany(mappedBy = "model")
    @JsonIgnore
    private List<Item> items;

    public ItemModel() {}

    public ItemModel(Long id, String name, String photo, boolean status) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.status = true;
    }

    public ItemModel(String name, String photo, boolean status) {
        this.name = name;
        this.photo = photo;
        this.status = true;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
