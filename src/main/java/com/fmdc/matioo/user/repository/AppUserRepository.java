package com.fmdc.matioo.user.repository;

import com.fmdc.matioo.item.model.Item;
import com.fmdc.matioo.user.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findFirstByEmailAndRecoveryCode(String email, String recoveryCode);
    Optional<AppUser> findFirstByRecoveryCode(String recoveryCode);
    boolean existsByEmailAndIdNot(String email, Long id);
    List<AppUser> findByStatus(boolean status);


}
