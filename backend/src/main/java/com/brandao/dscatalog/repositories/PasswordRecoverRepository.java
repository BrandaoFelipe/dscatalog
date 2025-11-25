package com.brandao.dscatalog.repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brandao.dscatalog.entities.PasswordRecover;

public interface PasswordRecoverRepository extends JpaRepository<PasswordRecover, Long>{

    @Query(nativeQuery = true, value = "SELECT * FROM tb_password_recover WHERE token = :token AND expiration > :now")
    List<PasswordRecover> searchValidTokens(@Param("token") String token, @Param("now") Instant now );
    

}