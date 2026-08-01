package org.SprintForge.modules.user.repository;

import org.SprintForge.modules.user.entity.UserCapacity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCapacityRepository extends JpaRepository<UserCapacity, Long> {

    Optional<UserCapacity> findByUserIdAndIsDeletedFalse(Long userId);
}
