package com.utsc.project_coding_lads.test.EventTest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.utsc.project_coding_lads.Application;
import com.utsc.project_coding_lads.domain.Event;
import com.utsc.project_coding_lads.domain.Role;
import com.utsc.project_coding_lads.domain.SocialInitiative;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.repository.RoleRepository;
import com.utsc.project_coding_lads.service.EventService;
import com.utsc.project_coding_lads.service.SocialInitService;
import com.utsc.project_coding_lads.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class TestEventService {

	@Autowired
	EventService eventService;
	@Autowired
	UserService userService;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	SocialInitService socialInitService;
	
	@Test
	public void testCRUD() throws Exception {
		/* User user = new User();
		user.setAge(90);
		user.setFirstName("firstName");
		user.setLastName("lastname");
		user.setUsername("username");
		user.setHashedPassword("pw");
		Role role1 = new Role("IMPACT_LEARNER");
		Role role_1 = roleRepo.save(role1);
		user.setRole(role_1);
		SocialInitiative socialInit = new SocialInitiative();
		socialInit.setName("org");
		SocialInitiative saved = socialInitService.storeSocialInit(socialInit);
		
		user.setSocialInit(saved);
		Integer id = userService.storeUser(user);
		User savedUser = userService.findUserById(id);
		
		Event event = new Event();
		event.setEventName("name");
		event.setEventDesc("desc");
		LocalDateTime start = LocalDateTime.of(2000, 10, 31, 00, 00, 00);
		LocalDateTime end = LocalDateTime.of(2000, 10, 31, 00, 10, 00);
		event.setEventStartDate(start);
		event.setEventEndDate(end);
		event.setEventCreator(savedUser);
		event.setImgUrl("url");
		
		Event savedEvent = eventService.saveEvent(event);
		Assert.assertNotNull(savedEvent.getId());
		Assert.assertEquals("url", event.getImgUrl());
		Event getEvent = eventService.findEventById(savedEvent.getId());
		Assert.assertNotNull(getEvent);
		Assert.assertEquals(savedEvent.getId(), getEvent.getId());
		
		List<Event> events0 = eventService.findAllEventsByUserId(savedUser.getId());
		Assert.assertFalse(events0.isEmpty());
		Event event0 = events0.get(0);
		Assert.assertEquals(getEvent.getEventName(), event0.getEventName());
		
		savedEvent.setEventName("new name");
		savedEvent = eventService.updateEvent(savedEvent);
		Assert.assertEquals("new name", savedEvent.getEventName());
		
		List<Event> events1 = eventService.findAllEventsByUserId(savedUser.getId());
		Event event1 = events1.get(0);
		Assert.assertEquals("new name", event1.getEventName());
		
		eventService.deleteEventById(savedEvent.getId());
		
		Boolean exist = eventService.existsById(id);
		Assert.assertFalse(exist); */
	}
	
}
