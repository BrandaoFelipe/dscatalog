package com.brandao.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandao.dscatalog.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
