package com.soutenence.publiciteApp.repository;

import com.soutenence.publiciteApp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(@Param("email") String email);

    @Query(value = "SELECT u.* FROM user u JOIN user_roles ur ON u.id = ur.users_id JOIN role r ON ur.roles_id = r.id WHERE r.name = :role", nativeQuery = true)
    Page<User> findAllUserByRole(Pageable pageable, @Param("role") String role);

   @Query(value = "SELECT u.* FROM user u JOIN user_roles ur ON u.id = ur.users_id JOIN role r ON ur.roles_id = r.id WHERE r.name = :role AND email = :mail", nativeQuery = true)
   Page<User> findAllUserByRoleAndMail(Pageable pageable, @Param("role") String role, @Param("mail") String mail);

    @Query(value = """
            SELECT us
            FROM User us
            JOIN us.abonnementList ab
            WHERE ab.actif=true
            """)
    Page<User> findAllUserByAbnmentActif(Pageable pageable);

    @Query(value = """
            SELECT us
            FROM User us
            JOIN us.abonnementList ab
            WHERE ab.actif=false
            """)
    Page<User> findAllUserByNoActifAbnment(Pageable pageable);

    Page<User> findAllCustomerByCreatedATBetween(Pageable pageable, LocalDate localDate1, LocalDate localDate2);
}
