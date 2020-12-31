package com.utsc.project_coding_lads.test.UserServiceTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.BeforeTransaction;

import com.utsc.project_coding_lads.Application;
import com.utsc.project_coding_lads.domain.Course;
import com.utsc.project_coding_lads.domain.ImpactConsultant;
import com.utsc.project_coding_lads.domain.ImpactLearner;
import com.utsc.project_coding_lads.domain.Invoice;
import com.utsc.project_coding_lads.domain.Quiz;
import com.utsc.project_coding_lads.domain.QuizQuestion;
import com.utsc.project_coding_lads.domain.QuizQuestionOption;
import com.utsc.project_coding_lads.domain.Role;
import com.utsc.project_coding_lads.domain.Solution;
import com.utsc.project_coding_lads.domain.StudentAnswer;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.enums.QuizQuestionTypeEnum;
import com.utsc.project_coding_lads.exception.MissingInformationException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.repository.CourseRepository;
import com.utsc.project_coding_lads.repository.RoleRepository;
import com.utsc.project_coding_lads.service.CourseService;
import com.utsc.project_coding_lads.service.ImpactConsultantService;
import com.utsc.project_coding_lads.service.ImpactLearnerService;
import com.utsc.project_coding_lads.service.QuizService;
import com.utsc.project_coding_lads.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@Transactional
class TestAnsweringQuiz {

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
	ImpactLearnerService learnerService;
	@Autowired
	QuizService quizService;
	
	User user;
	ImpactConsultant savedInstructor;
	User user2;
	ImpactLearner savedStudent;
	Course course;
	Course savedCourse;
	
	@BeforeTransaction
	public void setup() throws Exception {
		user = new User();
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
		savedInstructor = consultantService.findImpactConsultantById(instructorId);
		
		user2 = new User();
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
		savedStudent = learnerService.findLearnerById(studentId);
		
		course = new Course();
		course.setCost(123);
		course.setCourseDesc("desc");
		course.setCourseName("name");
		course.setInstructor(savedInstructor);
		savedCourse = courseService.storeCourse(course);
		learnerService.addCourseToLearner(savedStudent, savedCourse);
	}
	
	@Test
	public void testQuizServiceCRUD() throws Exception {
		Quiz quiz = new Quiz();
		LocalDateTime start = LocalDateTime.of(2000, 10, 31, 00, 00, 00);
		quiz.setQuizStartDate(start);
		LocalDateTime end = LocalDateTime.of(2000, 10, 31, 00, 10, 00);
		quiz.setQuizEndDate(end);
		quiz.setCourse(savedCourse);
		
		QuizQuestion question = new QuizQuestion();
		question.setQuestionType(QuizQuestionTypeEnum.SHORT_ANSWER.name());
		question.setQuestion("Short answer question 1");
		Solution solution = new Solution();
		solution.setAnswer("answer");
		question.setSolution(solution);
		quiz.getQuizQuestions().add(question);
		
		QuizQuestion question2 = new QuizQuestion();
		question2.setQuestionType(QuizQuestionTypeEnum.SHORT_ANSWER.name());
		question2.setQuestion("Short answer question 2");
		Solution solution2 = new Solution();
		solution2.setAnswer("answer 2");
		question2.setSolution(solution2);
		quiz.getQuizQuestions().add(question2);
		
		Integer savedQuizId = quizService.createQuiz(quiz);
		Quiz savedQuiz = quizService.findQuizById(savedQuizId);
		savedQuiz.getQuizQuestions().size();
		savedQuiz.getQuizQuestions().get(0).getStudentAnswers().size();
		Assert.assertFalse(savedQuiz.getQuizQuestions().isEmpty());
		Assert.assertFalse(savedQuiz.getQuizQuestions().get(0).getStudentAnswers().isEmpty());
		Assert.assertFalse(savedQuiz.getQuizQuestions().get(1).getStudentAnswers().isEmpty());
//		Assert.assertTrue(savedQuiz.getQuizQuestions().get(0).getStudentAnswers().size() == 1);
		
		QuizQuestion qn = savedQuiz.getQuizQuestions().get(0);
		Solution soln = qn.getSolution();
		StudentAnswer answer1 = new StudentAnswer();
		answer1.setQuestion(qn);
		answer1.setStudent(savedStudent);
		answer1.setStudentAnswer("answer");
		Assert.assertNotNull(soln);
		Assert.assertEquals("answer", soln.getAnswer());
		
		QuizQuestion qn2 = savedQuiz.getQuizQuestions().get(1);
		Solution soln2 = qn2.getSolution();
		StudentAnswer answer2 = new StudentAnswer();
		answer2.setQuestion(qn2);
		answer2.setStudent(savedStudent);
		answer2.setStudentAnswer("answer 2");
		Assert.assertNotNull(soln2);
		Assert.assertEquals("answer 2", soln2.getAnswer());
		
		List<StudentAnswer> studentAnswers = new ArrayList<>();
		studentAnswers.add(answer1);
		studentAnswers.add(answer2);
		
		List<StudentAnswer> answers = learnerService.answerQuizQuestions(studentAnswers);
		Assert.assertNotNull(answers);
		Assert.assertFalse(answers.isEmpty());
		
		for (StudentAnswer studentAnswer: qn.getStudentAnswers()) {
			if (studentAnswer.getStudent().equals(savedStudent)) {
				System.out.println(studentAnswer.getStudentAnswer());
				Assert.assertEquals("answer", studentAnswer.getStudentAnswer());
			}
		}
		
		for (StudentAnswer studentAnswer: qn2.getStudentAnswers()) {
			if (studentAnswer.getStudent().equals(savedStudent)) {
				System.out.println(studentAnswer.getStudentAnswer());
				Assert.assertEquals("answer 2", studentAnswer.getStudentAnswer());
			}
		}
		
		studentAnswers.get(0).setStudentAnswer(null);
		try {
			learnerService.answerQuizQuestions(studentAnswers);
		} catch(Exception e) {
			Assert.assertTrue(e.getClass().getSimpleName().equals("MissingInformationException"));
		}
		
		
		
		
	}

}
