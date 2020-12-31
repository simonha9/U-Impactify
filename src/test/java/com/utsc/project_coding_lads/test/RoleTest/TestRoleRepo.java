package com.utsc.project_coding_lads.test.RoleTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.utsc.project_coding_lads.Application;
import com.utsc.project_coding_lads.domain.Role;
import com.utsc.project_coding_lads.repository.RoleRepository;
import com.utsc.project_coding_lads.service.RoleService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class TestRoleRepo {

	@Autowired
	RoleRepository roleRepo;
	@Autowired
	RoleService roleService;
	
	@Test
	public void test() throws Exception {
		Role role1 = new Role("IMPACT_LEARNER");
		Role role2 = new Role("IMPACT_CONSULTANT");
		
		Role role_1 = roleRepo.save(role1);
		Role role_2 = roleRepo.save(role2);
		
		Assert.assertNotNull(role_1.getId());
		Assert.assertNotNull(role_2.getId());
		
		Role impactLearnerId = roleService.findRoleByName("IMPACT_LEARNER");
		Role impactConsultantId = roleService.findRoleByName("IMPACT_CONSULTANT");

		Assert.assertEquals(role_1.getId(), impactLearnerId.getId());
		Assert.assertEquals(role_2.getId(), impactConsultantId.getId());
	}

}
