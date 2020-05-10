package repository;

import edu.utdallas.csmc.web.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, DataTablesRepository<User, UUID> {

    Optional<User> findByUsername(String username);
    Optional<User> findByCardId(String cardId);
    Optional<User> findByScancode(String scanCode);


    @Query(nativeQuery = true,
            value = "SELECT distinct u.id,u.first_name,u.last_name,u.netid,u.card_id,u.scancode FROM user u " +
                    "JOIN user_roles ur " +
                    "ON u.id = ur.user_id " +
                    "JOIN role r " +
                    "ON ur.role_id = r.id " +
                    "WHERE LOWER(u.netid) LIKE %:word% " +
                    "OR LOWER(u.first_name) LIKE %:word% " +
                    "OR LOWER(u.last_name) LIKE %:word% " +
                    "OR LOWER(r.name) LIKE %:word% ",
            countQuery = "SELECT COUNT(*) FROM ( " +
                    "SELECT distinct u.id,u.first_name,u.last_name,u.netid,u.card_id,u.scancode FROM user u " +
                    "JOIN user_roles ur " +
                    "ON u.id = ur.user_id " +
                    "JOIN role r " +
                    "ON ur.role_id = r.id " +
                    "WHERE LOWER(u.netid) LIKE %:word% " +
                    "OR LOWER(u.first_name) LIKE %:word% " +
                    "OR LOWER(u.last_name) LIKE %:word% " +
                    "OR LOWER(r.name) LIKE %:word% " +
                    ") c")
    Page<User> findUsersByPage2(@Param("word") String word, Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from User u where u.id=:userId")
    void deleteUser(@Param("userId") UUID userId);
}
