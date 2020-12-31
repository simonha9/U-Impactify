package com.utsc.project_coding_lads.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utsc.project_coding_lads.domain.Course;
import com.utsc.project_coding_lads.domain.ImpactConsultant;
import com.utsc.project_coding_lads.domain.ImpactLearner;
import com.utsc.project_coding_lads.domain.ImpactLearnerCourse;
import com.utsc.project_coding_lads.domain.Role;
import com.utsc.project_coding_lads.domain.SocialInitiative;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.enums.RoleEnum;
import com.utsc.project_coding_lads.exception.BadRequestException;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.UserTypeInvalidException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.repository.ImpactLearnerRepository;
import com.utsc.project_coding_lads.repository.UserRepository;
import com.utsc.project_coding_lads.service.CourseService;
import com.utsc.project_coding_lads.service.ImpactConsultantService;
import com.utsc.project_coding_lads.service.ImpactLearnerService;
import com.utsc.project_coding_lads.service.RoleService;
import com.utsc.project_coding_lads.service.SocialInitService;
import com.utsc.project_coding_lads.service.UserService;
import com.utsc.project_coding_lads.validator.UserValidator;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	ImpactLearnerRepository learnerRepo;
	@Autowired
	RoleService roleService;
	@Autowired
	ImpactConsultantService consultantService;
	@Autowired
	ImpactLearnerService learnerService;
	@Autowired
	SocialInitService socialInitService;
	@Autowired
	CourseService courseService;
	@Autowired
	UserValidator userValidator;

	@Override
	public Integer storeUser(User user) throws Exception {
		Integer id = null;
		if (user == null)
			throw new BadRequestException("User cannot be null");
		//if (user.getRole() == null)
		//	throw new BadRequestException("Role cannot be null");
		userValidator.init(user);
		userValidator.validate();

		if (user.getSocialInit() != null) {
			String socialInitName = user.getSocialInit().getName();
			if (socialInitName != null) {
				SocialInitiative userSocialInit = socialInitService.findSocialInitByName(socialInitName);
				if (userSocialInit != null) {
					user.setSocialInit(userSocialInit);
				} else {
					userSocialInit = new SocialInitiative();
					userSocialInit.setName(socialInitName);
					SocialInitiative savedSocialInit = socialInitService.storeSocialInit(userSocialInit);
					user.setSocialInit(savedSocialInit);
				}
				//Public user case
				if (user.getRole() == null) {
					id = userRepo.save(user).getId();
					return id;
				}
				
			}
		}

		if (user.getRole() != null) {
			String roleName = user.getRole().getName();
//			Role role = roleService.saveRole(user.getRole());
//			log.info("role: " + role.getName());
			Role userRole = roleService.findRoleByName(roleName);
			user.setRole(userRole);
			User savedUser = userRepo.save(user);
			if (roleName.equals(RoleEnum.IMPACT_LEARNER.name())) {
				ImpactLearner learner = new ImpactLearner();
				learner.setUser(savedUser);
				id = learnerService.storeImpactLearner(learner);
			} else if (roleName.equals(RoleEnum.IMPACT_CONSULTANT.name())) {
				ImpactConsultant consultant = new ImpactConsultant();
				consultant.setUser(savedUser);
				id = consultantService.storeImpactConsultantService(consultant);
			} else {
				throw new UserTypeInvalidException("Role must be impact_consultant or impact_learner");
			}
		}

		return id;
	}

	@Override
	public User findUserById(Integer id) throws EntityNotExistException {
		if (!existsById(id))
			throw new EntityNotExistException("That user does not exist");
		return userRepo.findById(id).get();
	}

	@Override
	public Boolean existsById(Integer id) {
		return userRepo.existsById(id);
	}

	@Override
	public User updateUser(User user) throws ValidationFailedException {
		User savedUser = findUserById(user.getId());
		savedUser = userRepo.save(user);
		return savedUser;
	}

}