package com.brandao.dscatalog.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.brandao.dscatalog.entities.Roles;

public interface RoleRepository extends JpaRepository<Roles, Long>{

    @Query("SELECT r.authority FROM Roles r WHERE r.authority IN :names")
    public Set<Roles> searchRolesNames(@Param("names") List<String> names);

}