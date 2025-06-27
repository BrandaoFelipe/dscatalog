package com.brandao.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandao.dscatalog.entities.Roles;

public interface RoleRepository extends JpaRepository<Roles, Long>{

}
