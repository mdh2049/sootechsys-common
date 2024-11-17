package kr.co.sootechsys.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.sootechsys.common.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
   @EntityGraph(attributePaths = "auths")
   Optional<User> findOneWithAuthsByUsername(String username);
}
