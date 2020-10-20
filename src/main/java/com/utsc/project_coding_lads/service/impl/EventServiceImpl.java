package com.utsc.project_coding_lads.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utsc.project_coding_lads.domain.Event;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.MissingInformationException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.repository.EventRepository;
import com.utsc.project_coding_lads.service.EventService;
import com.utsc.project_coding_lads.service.UserService;
import com.utsc.project_coding_lads.validator.EventValidator;
import com.utsc.project_coding_lads.validator.UserValidator;

@Service
@Transactional
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepository eventRepo;
	@Autowired
	UserService userService;
	@Autowired
	EventValidator eventValidator;
	@Autowired
	UserValidator userValidator;

	@Override
	public Event saveEvent(Event event) throws ValidationFailedException {
		if (event == null)
			throw new MissingInformationException("Event body is null");
		eventValidator.init(event.getEventName(), event.getEventDesc(), event.getEventCreator(), event.getEventDate());
		eventValidator.validate();
		User user = userService.findUserById(event.getEventCreator().getId());
		event.setEventCreator(user);
		return eventRepo.save(event);
	}

	@Override
	public Event findEventById(Integer eventId) throws ValidationFailedException {
		if (!existsById(eventId)) {
			throw new EntityNotExistException("That Event does not exist.");
		}
		return eventRepo.getOne(eventId);
	}

	@Override
	public void deleteEventById(Integer eventId) throws Exception {
		eventRepo.deleteById(eventId);
	}

	@Override
	public Event updateEvent(Event event) throws ValidationFailedException {
		if (event == null)
			throw new MissingInformationException("Event body is null");
		eventValidator.init(event.getEventName(), event.getEventDesc(), event.getEventCreator(), event.getEventDate(), event.getId());
		eventValidator.validateExists();
		return eventRepo.save(event);
	}

	@Override
	public Boolean existsById(Integer eventId) {
		return eventRepo.existsById(eventId);
	}

	@Override
	public List<Event> findAllEventsByUserId(Integer userId) throws ValidationFailedException {
		User user = userService.findUserById(userId);
		userValidator.init(user);
		userValidator.validateExists();
		return user.getEvents();
	}

	@Override
	public List<Event> findAllEventsByUserIdDate(Integer userId, LocalDateTime date) throws ValidationFailedException {
		if (date == null) throw new ValidationFailedException("Date cannot be null.");
		List<Event> eventsByDate = new ArrayList<>();
		List<Event> events = findAllEventsByUserId(userId);
		for (Event event : events) {
			if (event.getEventDate().isAfter(date)) {
				eventsByDate.add(event);
			}
		}
		return eventsByDate;
	}

}
