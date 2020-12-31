package com.utsc.project_coding_lads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.utsc.project_coding_lads.domain.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer>{
	
    @Query(value = "SELECT inv.id FROM Invoice inv WHERE inv.user_id = :uid AND inv.course_id = :cid", nativeQuery = true)
    public Integer findInvoiceByUserIdCourseId(@Param("uid") Integer user_id, @Param("cid") Integer course_id);
	
}
