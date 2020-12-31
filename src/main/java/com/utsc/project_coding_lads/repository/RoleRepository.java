package com.utsc.project_coding_lads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.utsc.project_coding_lads.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

	@Query(value = "SELECT r FROM Role r WHERE r.name = :roleName")
	public Role findRoleByName(@Param("roleName") String roleName);
}
