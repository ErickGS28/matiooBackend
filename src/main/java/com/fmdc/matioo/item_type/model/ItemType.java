package com.fmdc.matioo.item_type.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fmdc.matioo.item.model.Item;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "item_types")
public class ItemType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean status = true;

    @OneToMany(mappedBy = "itemType")
    @JsonIgnore
    private List<Item> items;


    public ItemType() {}

    public ItemType(Long id, String name, boolean status) {
        this.id = id;
        this.name = name;
        this.status = true;
    }

    public ItemType(String name) {
        this.name = name;
        this.status = true;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> Items) {
        this.items = Items;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
