package com.yasir.Eta.Repositories;

import com.yasir.Eta.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User getById(Long id);
    /*User findByEmailAndPassword(String email, String password);
    User findByUsernameAndPassword(String username, String password);
    User findByEmailAndUsername(String email, String username);
    User findByEmailAndUsernameAndPassword(String email, String username, String password);
    User findByEmailAndUsernameAndPasswordAndId(String email, String username, String password, Long id);
    User findByEmailAndId(String email, Long id);
    User findByUsernameAndId(String username, Long id);
    User findByEmailAndUsernameAndId(String email, String username, Long id);
    User findByEmailAndPasswordAndId(String email, String password, Long id);
    User findByUsernameAndPasswordAndId(String username, String password, Long id);
    User findByEmailAndUsernameAndPasswordAndId(String email, String username, String password, Long id);
    User findByEmailAndUsernameAndPasswordAndIsVerified(String email, String username, String password, Boolean isVerified);
    User findByEmailAndUsernameAndPasswordAndIsVerifiedAndId(String email, String username, String password, Boolean isVerified, Long id);
    User findByEmailAndUsernameAndPasswordAndIsVerifiedAndIsEnabled(String email, String username, String password, Boolean isVerified, Boolean isEnabled);
    User findByEmailAndUsernameAndPasswordAndIsVerifiedAndIsEnabledAndId(String email, String username, String password, Boolean isVerified, Boolean isEnabled, Long id);
    User findByEmailAndUsernameAndPasswordAndIsVerifiedAndIsEnabledAndIsDeleted(String email, String username, String password, Boolean isVerified, Boolean isEnabled, Boolean isDeleted);
    User findByEmailAndUsernameAndPasswordAndIsVerifiedAndIsEnabledAndIsDeletedAndId(String email, String username, String password, Boolean isVerified, Boolean isEnabled, Boolean isDeleted, Long id);
    User findByEmailAndUsernameAndPasswordAndIsVerifiedAndIsEnabledAndIsDeletedAndIsLocked(String email, String username, String password, Boolean isVerified, Boolean isEnabled, Boolean isDeleted, Boolean isLocked);
    User findByEmailAndUsernameAndPasswordAndIsVerifiedAndIsEnabledAndIsDeletedAndIsLockedAndId(String email, String username, String password, Boolean isVerified, Boolean isEnabled, Boolean isDeleted, Boolean isLocked, Long id);
    User findByEmailAndUsernameAndPasswordAndIsVerifiedAndIsEnabledAndIsDeletedAndIsLockedAndIsExpired(String email, String username
    */
}

