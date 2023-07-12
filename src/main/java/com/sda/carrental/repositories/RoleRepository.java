package com.sda.carrental.repositories;

import com.sda.carrental.entities.Revenue;
import com.sda.carrental.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
