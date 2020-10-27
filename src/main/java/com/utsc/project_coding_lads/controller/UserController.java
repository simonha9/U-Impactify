package com.utsc.project_coding_lads.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utsc.project_coding_lads.domain.Application;
import com.utsc.project_coding_lads.domain.Event;
import com.utsc.project_coding_lads.domain.Posting;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.exception.BadRequestException;
import com.utsc.project_coding_lads.exception.EntityAlreadyExistsException;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.security.PasswordHash;
import com.utsc.project_coding_lads.security.SecurityConfig;
import com.utsc.project_coding_lads.service.ApplicationService;
import com.utsc.project_coding_lads.service.EventService;
import com.utsc.project_coding_lads.service.PostingService;
import com.utsc.project_coding_lads.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/" + UserService.SERVICE_NAME)
public class UserController extends BaseController {

	@Autowired
	SecurityConfig security;
	@Autowired
	UserService userService;
	@Autowired
	PostingService postingService;
	@Autowired
	EventService eventService;
	@Autowired
	ApplicationService appService;
	final static Logger log = LoggerFactory.getLogger(UserController.class);

	@PostMapping(path = "/signup")
	@ApiOperation(value = "create a new user", response = User.class)
	public Integer storeUser(@RequestBody User user) throws Exception {
		try {
			PasswordHash encoder = new PasswordHash();
			user.setHashedPassword(encoder.passwordEncoder(user.getHashedPassword()));
			Integer id = userService.storeUser(user);
//			log.info("userid: " + id);
			return id;
		} catch (NullPointerException e) {
			log.info("Could not store user: ", e);
			throw new BadRequestException("Request cannot be null");
		} catch (DataIntegrityViolationException e) {
			throw new EntityAlreadyExistsException("Username already exists");
		}
	}

	@PostMapping(path = "/createPosting")
	@ApiOperation(value = "create a new posting", response = Posting.class)
	public Posting createPosting(@RequestBody Posting posting) throws ValidationFailedException {
		Posting savedPosting = null;
		savedPosting = postingService.savePosting(posting);
		return savedPosting;
	}

	@GetMapping(path = "/getUser/{id}")
	@ApiOperation(value = "find a user by id", response = User.class)
	public User getUser(@PathVariable("id") Integer id) throws EntityNotExistException {
		return userService.findUserById(id);
	}

	@PostMapping(path = "/updatePosting")
	@ApiOperation(value = "update a posting", response = Posting.class)
	public Posting updatePosting(@RequestBody Posting posting) throws ValidationFailedException {
		return postingService.updatePosting(posting);
	}

	@PostMapping(path = "/deletePosting/{id}")
	@ApiOperation(value = "Delete a posting", response = Boolean.class)
	public Boolean deletePosting(@PathVariable("id") Integer id) throws Exception {
		Boolean ok = true;
		postingService.deletePostingById(id);
		return ok;
	}

	@GetMapping(path = "/getPosting/{id}")
	@ApiOperation(value = "find a posting by id", response = Posting.class)
	public Posting getPosting(@PathVariable("id") Integer id) throws ValidationFailedException {
		return postingService.findPostingById(id);
	}

	@GetMapping(path = "/getPostings/{id}")
	@ApiOperation(value = "find all postings by userId", response = Posting.class, responseContainer = "List")
	public List<Posting> getPostings(@PathVariable("id") Integer userId) throws ValidationFailedException {
		return postingService.findAllPostingsByUserId(userId);
	}

	@GetMapping(path = "/getPostingsByDate/{id}")
	@ApiOperation(value = "find all postings by userId", response = Posting.class, responseContainer = "List")
	public List<Posting> getPostingsByDate(@PathVariable("id") Integer userId, @RequestBody LocalDateTime date)
			throws ValidationFailedException {
		return postingService.findAllPostingsByUserIdDate(userId, date);
	}

	@PostMapping(path = "/createEvent")
	@ApiOperation(value = "create a new event", response = Event.class)
	public Event createEvent(@RequestBody Event event) throws ValidationFailedException {
		return eventService.saveEvent(event);
	}

	@PostMapping(path = "/updateEvent")
	@ApiOperation(value = "update a event", response = Event.class)
	public Event updateEvent(@RequestBody Event event) throws Exception {
		return eventService.updateEvent(event);
	}

	@PostMapping(path = "/deleteEvent/{id}")
	@ApiOperation(value = "Delete a event", response = Boolean.class)
	public Boolean deleteEvent(@PathVariable("id") Integer id) throws Exception {
		Boolean ok = true;
		eventService.deleteEventById(id);
		return ok;
	}

	@GetMapping(path = "/getEvent/{id}")
	@ApiOperation(value = "find a event by id", response = Event.class)
	public Event getEvent(@PathVariable("id") Integer id) throws ValidationFailedException {
		return eventService.findEventById(id);
	}

	@GetMapping(path = "/getEvents/{id}")
	@ApiOperation(value = "find all events by userId", response = Event.class, responseContainer = "List")
	public List<Event> getEvents(@PathVariable("id") Integer userId) throws ValidationFailedException {
		return eventService.findAllEventsByUserId(userId);
	}

	@GetMapping(path = "/getEventsByDate/{id}")
	@ApiOperation(value = "find all events by userId after date", response = Event.class, responseContainer = "List")
	public List<Event> getEventsByDate(@PathVariable("id") Integer userId, @RequestBody LocalDateTime date) throws ValidationFailedException {
		return eventService.findAllEventsByUserIdDate(userId, date);
	}

	@PostMapping(path = "/login")
	@ApiOperation(value = "login user", response = User.class)
	public Integer login(@RequestBody User user) throws Exception {
		try {
			PasswordHash encoder = new PasswordHash();
			user.setHashedPassword(encoder.passwordEncoder(user.getHashedPassword()));

		} catch (Exception e) {
			log.info("Could not load user: ", e);
		}
		return security.authentication(user);
	}
	
	@PostMapping(path = "/apply")
	@ApiOperation(value = "user apply posting", response = Application.class)
	public Application apply(@RequestBody Application app) throws Exception {
		Application savedApp = appService.storeApplication(app);
		return savedApp;
	}

}