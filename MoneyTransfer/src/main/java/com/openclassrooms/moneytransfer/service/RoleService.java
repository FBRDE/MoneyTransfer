package com.openclassrooms.moneytransfer.service;

import com.openclassrooms.moneytransfer.model.Role;
import com.openclassrooms.moneytransfer.repository.RoleRepository;
import com.openclassrooms.moneytransfer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    public Collection<Role> findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
