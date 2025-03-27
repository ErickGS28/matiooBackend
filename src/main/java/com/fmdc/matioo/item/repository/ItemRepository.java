package com.fmdc.matioo.item.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fmdc.matioo.brand.model.Brand;
import com.fmdc.matioo.item.model.Item;
import com.fmdc.matioo.item_model.model.ItemModel;
import com.fmdc.matioo.item_type.model.ItemType;
import com.fmdc.matioo.user.model.AppUser;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    //MODELO
    List<Item> findByModel(ItemModel model);

    List<Item> findByModel_Name(String modelName);

    //MARCA
    List<Item> findByBrand(Brand brand);

    List<Item> findByBrand_Name(String brandName);

    //ITEMTYPE
    List<Item> findByItemType(ItemType itemType);

    List<Item> findByItemType_Name(String itemTypeName);

    //NUMERO DE SERIE
    boolean existsBySerialNumber(String serialNumber);

    Optional<Item> findBySerialNumber(String serialNumber);

    //CODIGO
    boolean existsByCode(String code);

    Optional<Item> findByCode(String code);

    //ASSIGNEDTO
    List<Item> findByAssignedTo(AppUser assignedTo);
    List<Item> findByAssignedTo_FullName(String itemAssignedToFullName);

    //LOCATION
    boolean existsByLocation(String location);
    Optional<Item> findByLocation(String location);

    List<Item> findByStatus(boolean status);

    List<Item> findByAssignedToIsNull();
}
