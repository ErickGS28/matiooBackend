package com.fmdc.matioo.user.model;

import com.fmdc.matioo.user.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findFirstByRecoveryCode(String recoveryCode);

    boolean existsByEmailAndIdNot(String email, Long id);
}
