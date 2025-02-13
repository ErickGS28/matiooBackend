package com.fmdc.matioo.brand.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fmdc.matioo.item.model.Item;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean status = true;

    @OneToMany(mappedBy = "brand")
    @JsonIgnore
    private List<Item> items;

    public Brand() {}

    public Brand(Long id, String name, boolean status) {
        this.id = id;
        this.name = name;
        this.status = true;
    }

    public Brand(String name, boolean status) {
        this.name = name;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
