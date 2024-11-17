package kr.co.sootechsys.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.sootechsys.common.entity.Auth;

public interface AuthRepository extends JpaRepository<Auth, String> {
}
