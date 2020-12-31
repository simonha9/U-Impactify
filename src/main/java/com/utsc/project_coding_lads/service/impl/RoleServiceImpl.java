package com.utsc.project_coding_lads.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utsc.project_coding_lads.domain.Role;
import com.utsc.project_coding_lads.repository.RoleRepository;
import com.utsc.project_coding_lads.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepo;

	@Override
	public Role findRoleByName(String roleName) {
		return roleRepo.findRoleByName(roleName);
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepo.save(role);
	}


}