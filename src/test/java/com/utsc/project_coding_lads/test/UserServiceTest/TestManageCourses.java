package com.utsc.project_coding_lads.test.UserServiceTest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.utsc.project_coding_lads.Application;
import com.utsc.project_coding_lads.domain.Course;
import com.utsc.project_coding_lads.domain.ImpactConsultant;
import com.utsc.project_coding_lads.domain.ImpactLearner;
import com.utsc.project_coding_lads.domain.ImpactLearnerCourse;
import com.utsc.project_coding_lads.domain.Role;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.repository.ImpactLearnerCourseRepository;
import com.utsc.project_coding_lads.repository.ImpactLearnerRepository;
import com.utsc.project_coding_lads.repository.RoleRepository;
import com.utsc.project_coding_lads.service.CourseService;
import com.utsc.project_coding_lads.service.ImpactConsultantService;
import com.utsc.project_coding_lads.service.ImpactLearnerService;
import com.utsc.project_coding_lads.service.PostingService;
import com.utsc.project_coding_lads.service.SocialInitService;
import com.utsc.project_coding_lads.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class TestManageCourses {
	
	@Autowired
	UserService userService;
	@Autowired
	ImpactLearnerRepository learnerRepo;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	SocialInitService siService;
	@Autowired
	PostingService postingService;
	@Autowired
	ImpactConsultantService consultantService;
	@Autowired
	ImpactLearnerService learnerService;
	@Autowired
	CourseService courseService;

	@Test
	public void testCRUDService() throws Exception {
		User user = new User();
		user.setAge(20);
		user.setFirstName("instructor");
		user.setLastName("lastname");
		user.setUsername("instructor");
		user.setHashedPassword("password");
		Role consultant = new Role();
		consultant.setName("IMPACT_CONSULTANT");
		Role savedConsultant = roleRepo.save(consultant);
		user.setRole(savedConsultant);
		Integer instructorId = userService.storeUser(user);
		ImpactConsultant savedInstructor = consultantService.findImpactConsultantById(instructorId);
		Assert.assertNotNull(savedInstructor);
		
		Course course = new Course();
		course.setCost(100);
		course.setCourseName("course 1");
		course.setCourseDesc("desc");
		course.setInstructor(savedInstructor);
		Integer savedCourseId = courseService.storeCourse(course).getId();
		Course savedCourse = courseService.findCourseById(savedCourseId);
		Assert.assertNotNull(savedCourse);
		
		User user2 = new User();
		user2.setAge(90);
		user2.setFirstName("student");
		user2.setLastName("lastname");
		user2.setUsername("student");
		user2.setHashedPassword("password");
		Role learner = new Role();
		learner.setName("IMPACT_LEARNER");
		Role savedLearner = roleRepo.save(learner);
		user2.setRole(savedLearner);
		Integer studentId = userService.storeUser(user2);
		ImpactLearner savedStudent = learnerService.findLearnerById(studentId);
		Assert.assertNotNull(savedStudent);
		Assert.assertNotNull(savedStudent.getUser().getInvoices());
		
		learnerService.addCourseToLearner(savedStudent, savedCourse);
		List<ImpactLearnerCourse> courses = learnerService.findCoursesByLearnerId(savedStudent.getId());
		Assert.assertFalse(courses.isEmpty());
		
		learnerService.removeCourseFromLearner(savedStudent, savedCourse);
		List<ImpactLearnerCourse> coursesRemoved = learnerService.findCoursesByLearnerId(savedStudent.getId());
		Assert.assertTrue(coursesRemoved.isEmpty());
		
		learnerService.addCourseToLearner(savedStudent, savedCourse);
		List<ImpactLearnerCourse> instructorCourse = learnerService.findCoursesByInstructorId(savedStudent.getId(), savedInstructor.getId());
		Assert.assertEquals(1, instructorCourse.size());
		
		Course course2 = new Course();
		course2.setCost(100);
		course2.setCourseName("course 2");
		course2.setCourseDesc("desc");
		course2.setInstructor(savedInstructor);
		Integer savedCourse2Id = courseService.storeCourse(course2).getId();
		Course savedCourse2 = courseService.findCourseById(savedCourse2Id);
		Assert.assertNotNull(savedCourse2);
		
		learnerService.addCourseToLearner(savedStudent, savedCourse2);
		List<ImpactLearnerCourse> coursesUpdated = learnerService.findCoursesByLearnerId(savedStudent.getId());
		Assert.assertFalse(coursesUpdated.isEmpty());
		Assert.assertEquals(2, coursesUpdated.size());
		
		User user3 = new User();
		user3.setAge(80);
		user3.setFirstName("instructor 2");
		user3.setLastName("lastname");
		user3.setUsername("instructor 2");
		user3.setHashedPassword("password");
		user3.setRole(savedConsultant);
		Integer instructor2Id = userService.storeUser(user3);
		ImpactConsultant savedInstructor2 = consultantService.findImpactConsultantById(instructor2Id);
		Assert.assertNotNull(savedInstructor2);
		
		Course course3 = new Course();
		course3.setCost(100);
		course3.setCourseName("course 3");
		course3.setCourseDesc("desc");
		course3.setInstructor(savedInstructor2);
		Integer savedCourse3Id = courseService.storeCourse(course3).getId();
		Course savedCourse3 = courseService.findCourseById(savedCourse3Id);
		Assert.assertNotNull(savedCourse3);
		
		learnerService.addCourseToLearner(savedStudent, savedCourse3);
		List<ImpactLearnerCourse> coursesInstructor1 = learnerService.findCoursesByInstructorId(savedStudent.getId(), savedInstructor.getId());
		List<ImpactLearnerCourse> coursesInstructor2 = learnerService.findCoursesByInstructorId(savedStudent.getId(), savedInstructor2.getId());
		Assert.assertFalse(coursesInstructor1.isEmpty());
		Assert.assertFalse(coursesInstructor2.isEmpty());
		Assert.assertEquals(2, coursesInstructor1.size());
		Assert.assertEquals(1, coursesInstructor2.size());
		
//		savedStudent = learnerService.findLearnerById(savedStudent.getId());
//		savedStudent.getCourses().size();
//		courses = savedStudent.getCourses();
//		for (ImpactLearnerCourse impactLearnerCourse : courses) {
//			System.out.println(impactLearnerCourse.getCourse().getCourseName());
//		}
	}

}
