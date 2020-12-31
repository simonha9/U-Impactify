package com.utsc.project_coding_lads.test.course;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.utsc.project_coding_lads.Application;
import com.utsc.project_coding_lads.domain.Course;
import com.utsc.project_coding_lads.domain.ImpactConsultant;
import com.utsc.project_coding_lads.domain.ImpactLearner;
import com.utsc.project_coding_lads.domain.Quiz;
import com.utsc.project_coding_lads.domain.QuizQuestion;
import com.utsc.project_coding_lads.domain.QuizQuestionOption;
import com.utsc.project_coding_lads.domain.Role;
import com.utsc.project_coding_lads.domain.Solution;
import com.utsc.project_coding_lads.domain.StudentAnswer;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.enums.QuizQuestionTypeEnum;
import com.utsc.project_coding_lads.repository.CourseRepository;
import com.utsc.project_coding_lads.repository.RoleRepository;
import com.utsc.project_coding_lads.service.CourseService;
import com.utsc.project_coding_lads.service.ImpactConsultantService;
import com.utsc.project_coding_lads.service.ImpactLearnerService;
import com.utsc.project_coding_lads.service.QuizQuestionService;
import com.utsc.project_coding_lads.service.QuizService;
import com.utsc.project_coding_lads.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@Transactional
public class TestQuizRepo {

	@Autowired
	CourseService courseService;
	@Autowired
	CourseRepository courseRepo;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	UserService userService;
	@Autowired
	ImpactConsultantService consultantService;
	@Autowired
	ImpactLearnerService ilService;
	@Autowired
	QuizService quizService;
	@Autowired
	QuizQuestionService quizQnService;
	
	@Test
	public void testQuizCRUD() throws Exception {
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
		
		Course course = new Course();
		course.setCost(123);
		course.setCourseDesc("desc");
		course.setCourseName("name");
		course.setInstructor(savedInstructor);
		Integer courseId = courseService.storeCourse(course).getId();
		Course savedCourse = courseService.findCourseById(courseId);
		
		Quiz quiz = new Quiz();
		LocalDateTime start = LocalDateTime.of(2000, 10, 31, 00, 00, 00);
		quiz.setQuizStartDate(start);
		LocalDateTime end = LocalDateTime.of(2000, 10, 31, 00, 10, 00);
		quiz.setQuizEndDate(end);
		quiz.setCourse(savedCourse);
		
		savedCourse.getQuizzes().add(quiz);
		savedCourse = courseRepo.save(savedCourse);
		savedCourse.getQuizzes().size();
		Assert.assertFalse(savedCourse.getQuizzes().isEmpty());
	}
	
	@Test
	public void testQuizServiceCRUD() throws Exception {
		Role consultant = new Role();
		consultant.setName("IMPACT_CONSULTANT");
		Role savedConsultant = roleRepo.save(consultant);
		
		Role learnerRole = new Role();
		learnerRole.setName("IMPACT_LEARNER");
		Role savedLearnerRole = roleRepo.save(learnerRole);
		
		User user = new User();
		user.setAge(20);
		user.setFirstName("instructor");
		user.setLastName("lastname");
		user.setUsername("instructor");
		user.setHashedPassword("password");
		user.setRole(savedConsultant);
		Integer instructorId = userService.storeUser(user);
		ImpactConsultant savedInstructor = consultantService.findImpactConsultantById(instructorId);
		
		User learner = new User();
		learner.setAge(20);
		learner.setFirstName("learner");
		learner.setLastName("lastname");
		learner.setUsername("learner");
		learner.setHashedPassword("password");
		learner.setRole(savedLearnerRole);
		Integer learnerId = userService.storeUser(learner);
		ImpactLearner student = ilService.findLearnerById(learnerId);
		
		Course course = new Course();
		course.setCost(123);
		course.setCourseDesc("desc");
		course.setCourseName("name");
		course.setInstructor(savedInstructor);
		Course savedCourse = courseService.storeCourse(course);
		
		ilService.addCourseToLearner(student, savedCourse);
		
		Quiz quiz = new Quiz();
		LocalDateTime start = LocalDateTime.of(2000, 10, 31, 00, 00, 00);
		quiz.setQuizStartDate(start);
		LocalDateTime end = LocalDateTime.of(2000, 10, 31, 00, 10, 00);
		quiz.setQuizEndDate(end);
		quiz.setCourse(savedCourse);
		
		QuizQuestion question = new QuizQuestion();
		question.setQuestionType(QuizQuestionTypeEnum.MULTIPLE_CHOICE.name());
		QuizQuestionOption option = new QuizQuestionOption();
		option.setQuestionOption("A");
		question.getQuestionOptions().add(option);
		Solution solution = new Solution();
		solution.setAnswer("answer");
		question.setSolution(solution);
		quiz.getQuizQuestions().add(question);
		
		Integer savedQuizId = quizService.createQuiz(quiz);
		List<QuizQuestion> savedQuestions = quizQnService.findQuestionsByQuizId(savedQuizId);
		Assert.assertFalse(savedQuestions.isEmpty());
		
		QuizQuestion qn = savedQuestions.get(0);
		QuizQuestionOption op = qn.getQuestionOptions().get(0);
		Assert.assertEquals(option.getQuestionOption(), op.getQuestionOption());
		
		qn = quizQnService.findQuizQuestionById(qn.getId());
		qn.getStudentAnswers().size();
		Assert.assertFalse(qn.getStudentAnswers().isEmpty());
	}

}