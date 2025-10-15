package com.brandao.dscatalog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.brandao.dscatalog.entities.User;
import com.brandao.dscatalog.projections.UserDetailsProjection;

public interface UserRepository extends JpaRepository<User, Long>{

    User findByEmail(String email);

      @Query(nativeQuery = true, value = """
			SELECT tb_user.email AS username, tb_user.password, tb_roles.id AS roleId, tb_roles.authority
			FROM tb_user
			INNER JOIN tb_user_role ON tb_user.id = tb_user_role.user_id
			INNER JOIN tb_roles ON tb_roles.id = tb_user_role.role_id
			WHERE tb_user.email = :email
		""")
    List<UserDetailsProjection> searchUserAndRolesByEmail(String email);

}
