package com.utsc.project_coding_lads.service;

import java.util.List;

import com.utsc.project_coding_lads.domain.Invoice;
import com.utsc.project_coding_lads.exception.ValidationFailedException;

public interface InvoiceService {
	
	public Integer payInvoice(Integer invoiceId) throws Exception;

	public List<Invoice> getUnpaidInvoice(int userId) throws ValidationFailedException;
	
	public List<Invoice> getAllInvoicesByUserId(Integer userId) throws ValidationFailedException;

	public List<Invoice> getAllInvoiceByCourseId(Integer courseId) throws ValidationFailedException;

	public Invoice findInvoiceByUserIdAndCourseId(Integer userId, Integer courseId) throws ValidationFailedException;

	public Integer payInvoicePerCourse(Integer userId, Integer courseId) throws Exception;
	
	public Integer InstructorPayment(Integer userId, Integer courseId) throws Exception;
	
	public Invoice updateInvoice(Invoice invoice) throws ValidationFailedException;
	
	public void deleteInvoiceById(Integer id) throws Exception;

	Boolean existsById(Integer invoiceId);

	Invoice saveInvoice(Invoice inv) throws ValidationFailedException; 
}
