package com.brandao.dscatalog.services;

import java.util.List;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brandao.dscatalog.dtos.request.UserCreatedRequestDTO;
import com.brandao.dscatalog.dtos.request.UserRequestDTO;
import com.brandao.dscatalog.dtos.response.UserResponseDTO;
import com.brandao.dscatalog.entities.Roles;
import com.brandao.dscatalog.entities.User;
import com.brandao.dscatalog.mappers.UserMapper;
import com.brandao.dscatalog.projections.UserDetailsProjection;
import com.brandao.dscatalog.repositories.UserRepository;
import com.brandao.dscatalog.services.exceptions.DatabaseException;
import com.brandao.dscatalog.services.exceptions.EmptyRequestException;
import com.brandao.dscatalog.services.exceptions.NotFoundException;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional(readOnly = true)
    public Page<UserResponseDTO> findAllUsers(Pageable pageable) {

        Page<User> list = repository.findAll(pageable);

        Page<UserResponseDTO> dtoPage = list.map(UserMapper::toResponse);

        return dtoPage;
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findUserById(Long id) {

        User User = repository.findById(id).orElseThrow(() -> new NotFoundException("item not found"));

        UserResponseDTO dto = UserMapper.toResponse(User);

        return dto;
    }

    @Transactional
    public UserResponseDTO userCreated(UserCreatedRequestDTO request) {

        if (request == null)
            throw new EmptyRequestException("object cannot be null");

        User entity = UserMapper.toEntity(request);        
        
        entity.setUserRoles(roleService.findStandardEntityRoleByid(1L));

        entity.setPassword(passwordEncoder.encode(entity.getPassword()));

        repository.save(entity);

        UserResponseDTO response = UserMapper.toResponse(entity);

        return response;

    }

    @Transactional
    public UserResponseDTO createNewUser(UserRequestDTO request) {

        if (request == null)
            throw new EmptyRequestException("object cannot be null");

        User entity = UserMapper.toEntity(request);

        entity.setUserRoles(getRoles(request));
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));

        repository.save(entity);

        UserResponseDTO response = UserMapper.toResponse(entity);

        return response;

    }

    @Transactional
    public UserResponseDTO updateUser(UserRequestDTO request, Long id) {

        User entity = repository.findById(id).orElseThrow(() -> new NotFoundException("item not found"));

        UserMapper.apply(request, entity);
        
        entity.setUserRoles(getRoles(request));

        entity.setPassword(passwordEncoder.encode(entity.getPassword()));

        repository.save(entity);

        return UserMapper.toResponse(entity);
        
    }

    @Transactional
    public void deleteUser(Long id) {

        if (!repository.existsById(id))
            throw new NotFoundException("id not found");

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Cannot delete entity: it's related to other data.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("Email not found");
        }

        User user = new User();
        user.setEmail(result.get(0).getUsername());
        user.setPassword(result.get(0).getPassword());

        for (UserDetailsProjection userDetailsProjection : result) {

            user.addRole(new Roles(userDetailsProjection.getRoleId(), userDetailsProjection.getAuthority()));
        }

        return user;

    }

    @Transactional
    protected void updateUserInternal(User entity) {

        entity.setPassword(passwordEncoder.encode(entity.getPassword()));       

        repository.save(entity);
        
    }

    private Set<Roles> getRoles(UserRequestDTO dto) {

        return roleService.findRolesByName(dto.getRoles());

    }    

}