package com.openclassrooms.moneytransfer.repository;

import com.openclassrooms.moneytransfer.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    public List<Role> findByRoleName(String roleName);

}
