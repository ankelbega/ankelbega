package com.sda.carrental.services;

import com.sda.carrental.entities.Branch;
import com.sda.carrental.entities.Role;
import com.sda.carrental.entities.User;
import com.sda.carrental.errors.NotFoundException;
import com.sda.carrental.models.pojo.UserRequestParam;
import com.sda.carrental.repositories.BranchRepository;
import com.sda.carrental.models.constants.RoleEnum;
import com.sda.carrental.repositories.RoleRepository;
import com.sda.carrental.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public User saveTheUser(final UserRequestParam userRequestParam, final Long roleId) {
        User user = new User();
        Role role = this.roleRepository.findById(roleId).orElseThrow(() ->
                new NotFoundException("Role not found with such id!"));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setFirstName(userRequestParam.getFirstName());
        user.setLastName(userRequestParam.getLastName());
        user.setAddress(userRequestParam.getAddress());
        user.setEmail(userRequestParam.getEmail());
        user.setUsername(userRequestParam.getUsername());
        user.setPassword(userRequestParam.getPassword());
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    public List<User> readAllCustomer() {
        return this.userRepository.findCustomer();
    }

    public List<User> getAllUsersAssignedInABranch(final String role, final Long branchId) {
        return userRepository.findUsersAssignedInABranch(role, branchId);
    }

    public List<User> getAllUsersNotAssignedInABranch(final String role) {

        return userRepository.findUsersNotAssignedInABranch(role);
    }

    public List<User> assignEmployeesToBranch(final List<Long> employeesIds, final Long branchId) {

        Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new NotFoundException("Branch not found!"));

        return employeesIds.stream().map((employeeId) -> {
            User user = this.userRepository.findIfUserHasRole(employeeId, RoleEnum.ROLE_EMPLOYEE.toString()).orElseThrow(() -> new NotFoundException("Employee not found!"));
            user.setBranch(branch);
            return userRepository.save(user);

        }).collect(Collectors.toList());
    }

    @Transactional
    public void assignManagerToBranch(final Long managerId, final Long branchId) {
        removePreviousManagerFromBranch(branchId);
        Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new NotFoundException("Branch not found!"));
        User manager = this.userRepository.findIfUserHasRole(managerId, RoleEnum.ROLE_MANAGER.toString()).orElseThrow(() -> new NotFoundException("Manager not found!"));
        manager.setBranch(branch);
        userRepository.save(manager);
    }

    public Optional<User> findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> optionalUser = this.userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return optionalUser.get();
    }


    @Transactional
    public void removePreviousManagerFromBranch(final Long branchId) {
        branchRepository.findById(branchId).orElseThrow(() -> new NotFoundException("Branch not found!"));
        this.userRepository.removePreviousManagerFromBranch(branchId);
    }

    public void removeUserFromBranch(final Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found!"));
        user.setBranch(null);
        userRepository.save(user);
    }
}
