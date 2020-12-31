package com.utsc.project_coding_lads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.utsc.project_coding_lads.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	@Query(value = "SELECT u FROM User u WHERE u.username = :username")
	public User findUserByUsername(@Param("username") String username);
}
