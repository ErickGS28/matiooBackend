package com.fmdc.matioo.common_area.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "common_areas")
public class CommonArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean status = true;

    public CommonArea() {}

    public CommonArea(Long id, String name, boolean status) {
        this.id = id;
        this.name = name;
        this.status = true;
    }

    public CommonArea(String name, boolean status) {
        this.name = name;
        this.status = true;
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
