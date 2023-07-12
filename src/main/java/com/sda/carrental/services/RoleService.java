package com.sda.carrental.services;

import com.sda.carrental.entities.Role;
import com.sda.carrental.repositories.RoleRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role findById(final Long roleId) throws NotFoundException {
        return roleRepository.findById(roleId).orElseThrow(() ->
                new NotFoundException("No role with such id is found")
        );

    }
}
