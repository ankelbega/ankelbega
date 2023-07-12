package com.sda.carrental.repositories;

import com.sda.carrental.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u.*\n" +
            "from  user u inner join user_role ur on u.user_Id = ur.user_Id inner join role r on ur.role_Id= r.role_Id\n" +
            "where r.role_Id=1;", nativeQuery = true)
    List<User> findCustomer();


    @Query(value = "select u.*\n" +
            "from user u inner join branch b on u.branch_id=b.branch_id\n" +
            "where u.branch_id = :branch_id", nativeQuery = true)
    List<User> selectUsersWithGivenBranchId(@Param("branch_id") final Long branchId);


    @Query(value = "SELECT u FROM User u inner join u.roles r " +
            "WHERE r.role =:role and u.branch.id =:branchId")
    List<User> findUsersAssignedInABranch(@Param("role") final String role, @Param("branchId") final Long branchId);

    @Query(value = "SELECT u FROM User u inner join u.roles r " +
            "WHERE r.role =:role and u.branch.id = null")
    List<User> findUsersNotAssignedInABranch(@Param("role") final String role);

    @Modifying
    @Query(value = "update user u \n" +
            "inner join user_role ur on u.user_id = ur.user_id \n" +
            "inner join role r on r.role_id = ur.role_id \n" +
            "set u.branch_id=null where r.role_id = 2 and u.branch_id =:branch_id", nativeQuery = true)
    void removePreviousManagerFromBranch(@Param("branch_id") final Long branchId);

    @Query(value = "SELECT u FROM User u inner join u.roles r " +
            "where u.id =:userId and r.role =:role")
    Optional<User> findIfUserHasRole(@Param("userId") final Long userId, @Param("role") final String role);

    Optional<User> findByUsername(String username);
}
