package com.utsc.project_coding_lads.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utsc.project_coding_lads.domain.Course;
import com.utsc.project_coding_lads.domain.Invoice;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.MissingInformationException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.repository.InvoiceRepository;
import com.utsc.project_coding_lads.service.CourseService;
import com.utsc.project_coding_lads.service.InvoiceService;
import com.utsc.project_coding_lads.service.UserService;
import com.utsc.project_coding_lads.validator.CourseValidator;
import com.utsc.project_coding_lads.validator.InvoiceValidator;
import com.utsc.project_coding_lads.validator.UserValidator;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService{
	
	@Autowired
	InvoiceRepository invoiceRepo;
	@Autowired
	UserService userService;
	@Autowired
	CourseService courseService;
	@Autowired
	CourseValidator courseValidator;
	@Autowired
	UserValidator userValidator;
	@Autowired
	InvoiceValidator invoiceValidator;

	@Override
	public List<Invoice> getUnpaidInvoice(int userId) throws ValidationFailedException {
		List<Invoice> result = new ArrayList<Invoice>();
		List<Invoice> invoices = getAllInvoicesByUserId(userId);
		
		int i = invoices.size();
		if(i==0) {
			return null;
		}
		for(int x = 0; x < i; x++) {
			if(invoices.get(x).getCost() != 0) {
				result.add(invoices.get(x));
			}
		}
		return result;
	}
	
	@Override
	public List<Invoice> getAllInvoicesByUserId(Integer userId) throws ValidationFailedException {
		User user = userService.findUserById(userId);
		userValidator.init(user);
		userValidator.validateExists();
		List<Invoice> invoices = user.getInvoices();
		return invoices;
	}
	
	@Override
	public List<Invoice> getAllInvoiceByCourseId(Integer courseId) throws ValidationFailedException {
		Course course = courseService.findCourseById(courseId);
		courseValidator.init(course);
		courseValidator.validateExist();
		List<Invoice> invoices = course.getInvoices();
		return invoices;
	}
	
	@Override
	public Invoice findInvoiceByUserIdAndCourseId(Integer userId, Integer courseId) throws ValidationFailedException {
		// List<Invoice> invoices = getAllInvoicesByUserId(userId);
		User user = userService.findUserById(userId);
		userValidator.init(user);
		userValidator.validateExists();
		Course course = courseService.findCourseById(courseId);
		courseValidator.init(course);
		courseValidator.validateExist();
		Invoice invoice = invoiceRepo.getOne(invoiceRepo.findInvoiceByUserIdCourseId(userId, courseId));
		return invoice;

		// if(invoices.size()==0) {
		// 	throw new Exception();
		// }
		// int i = invoices.size();
		// while (i!=0) {
		// 	if(invoices.get(i).getCourse().getId() == CourseId) {
		// 		return invoices.get(i);
		// 	}
		// }
		// return null;
		
	}
	
	@Override
	public Integer payInvoice(Integer invoiceId) throws Exception{
		Invoice inv = invoiceRepo.getOne(invoiceId);
		inv.setCost(0);
		return inv.getCost();
	}

	@Override
	public Integer payInvoicePerCourse(Integer userId, Integer courseId) throws Exception {
		Invoice i = findInvoiceByUserIdAndCourseId(userId, courseId);
		i.setCost(0);
		return i.getCost();
		
	}
	
	@Override
	public Integer InstructorPayment(Integer userId, Integer courseId) throws Exception {
		Invoice i = findInvoiceByUserIdAndCourseId(userId, courseId);
		Integer result = i.getCost(); 
		i.setCost(0);
		return result;
		
	}
	
	@Override
	public Boolean existsById(Integer invoiceId) {
		return invoiceRepo.existsById(invoiceId);
	}
	
	@Override
	public Invoice saveInvoice(Invoice inv) throws ValidationFailedException {
		if (inv == null)
			throw new MissingInformationException("Invoice body is null");
		invoiceValidator.init(inv.getCost(), inv.getUser(), inv.getCourse());
		invoiceValidator.validate();
		User user = userService.findUserById(inv.getUser().getId());
		inv.setUser(user);
		// user.getInvoices().add(inv);
		// System.out.println(user.getInvoices().get(1).getId());
		// User savedUser = userService.updateUser(user);
		// Invoice saved = savedUser.getInvoices().get(user.getInvoices().size() - 1);
		// return saved;
		Course course = courseService.findCourseById(inv.getCourse().getId());
		inv.setCourse(course);

		return invoiceRepo.save(inv);
	}
	
	public Invoice updateInvoice(Invoice invoice) throws ValidationFailedException {
		if(invoice == null) 
			throw new MissingInformationException("Invoice body is null");
		invoiceValidator.init(invoice.getCost(), invoice.getUser(), invoice.getCourse(), invoice.getId());
		invoiceValidator.validateExists();
		User user = userService.findUserById(invoice.getUser().getId());
		invoice.setUser(user);
		Course course = courseService.findCourseById(invoice.getCourse().getId());
		invoice.setCourse(course);
		return invoiceRepo.save(invoice);
	}
	
	public void deleteInvoiceById(Integer id) throws Exception{
		if (!existsById(id))
			throw new EntityNotExistException("The invoice does not exist.");
		invoiceRepo.deleteById(id);
	}
}
