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
}
