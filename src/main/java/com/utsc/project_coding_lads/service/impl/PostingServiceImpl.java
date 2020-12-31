package com.utsc.project_coding_lads.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.utsc.project_coding_lads.domain.Posting;
import com.utsc.project_coding_lads.domain.SocialInitiative;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.MissingInformationException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.repository.PostingRepository;
import com.utsc.project_coding_lads.service.PostingService;
import com.utsc.project_coding_lads.service.SocialInitService;
import com.utsc.project_coding_lads.service.UserService;
import com.utsc.project_coding_lads.validator.PostingValidator;
import com.utsc.project_coding_lads.validator.UserValidator;

@Service
@Transactional
public class PostingServiceImpl implements PostingService {

	@Autowired
	PostingRepository postingRepo;
	@Autowired
	UserService userService;
	@Autowired
	SocialInitService socialInitService;
	@Autowired
	PostingValidator postingValidator;
	@Autowired
	UserValidator userValidator;

	@Override
	public Posting savePosting(Posting posting) throws ValidationFailedException {
		if (posting == null)
			throw new MissingInformationException("Posting body is null");
		postingValidator.init(posting.getName(), posting.getPostingDesc(),
				posting.getPostingCreator(), posting.getPostingType(), posting.getPostingDate(), posting.getSocialInit());
		postingValidator.validate();
		SocialInitiative savedSocialInit = socialInitService.findSocialInitByName(posting.getSocialInit().getName());
		posting.setSocialInit(savedSocialInit);
		User user = userService.findUserById(posting.getPostingCreator().getId());
		posting.setPostingCreator(user);
		user.getPostings().add(posting);
		User savedUser = userService.updateUser(user);
		return savedUser.getPostings().get(savedUser.getPostings().size()-1);
	}

	@Override
	public Posting findPostingById(Integer postingId) throws ValidationFailedException {
		if (!existsById(postingId)) {
			throw new EntityNotExistException("That posting does not exist.");
		}
		return postingRepo.findById(postingId).get();
	}

	@Override
	public void deletePostingById(Integer postingId) throws Exception {
		postingRepo.deleteById(postingId);
	}

	@Override
	public Posting updatePosting(Posting posting) throws ValidationFailedException {
		if (posting == null)
			throw new MissingInformationException("Posting body is null");
//		postingValidator.init(posting.getName(), posting.getPostingDesc(),
//				posting.getPostingCreator(), posting.getPostingType(), posting.getPostingDate(), posting.getId());
		postingValidator.init(posting.getName(), posting.getPostingDesc(),
				posting.getPostingCreator(), posting.getPostingType(), posting.getPostingDate(), posting.getSocialInit(), posting.getId());
		postingValidator.validateExists();
		SocialInitiative savedSocialInit = socialInitService.findSocialInitByName(posting.getSocialInit().getName());
		posting.setSocialInit(savedSocialInit);
		User user = userService.findUserById(posting.getPostingCreator().getId());
		posting.setPostingCreator(user);
		return postingRepo.save(posting);
	}

	@Override
	public Boolean existsById(Integer postingId) {
		return postingRepo.existsById(postingId);
	}

	@Override
	public List<Posting> findAllPostingsByUserId(Integer userId) throws ValidationFailedException {
		User user = userService.findUserById(userId);
		userValidator.init(user);
		userValidator.validateEmployee();
		user.getPostings().size();
		List<Posting> postings = user.getPostings();
		return postings;
	}

	@Override
	public List<Posting> findAllPostingsByUserIdDate(Integer userId, LocalDateTime date)
			throws ValidationFailedException {
		if (date == null) throw new ValidationFailedException("Date cannot be null.");
		List<Posting> postings = findAllPostingsByUserId(userId);
		List<Posting> postingsByDate = new ArrayList<>();
		for (Posting posting : postings) {
			if (posting.getPostingDate().isAfter(date)) {
				postingsByDate.add(posting);
			}
		}
		return postingsByDate;
	}

	@Override
	public List<Posting> getAllPostings() {
		return postingRepo.findAll();
	}
	
	

}
