package com.utsc.project_coding_lads.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utsc.project_coding_lads.domain.Course;
import com.utsc.project_coding_lads.domain.ImpactLearner;
import com.utsc.project_coding_lads.domain.ImpactLearnerCourse;
import com.utsc.project_coding_lads.domain.Quiz;
import com.utsc.project_coding_lads.domain.QuizQuestion;
import com.utsc.project_coding_lads.domain.StudentAnswer;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.exception.MissingInformationException;
import com.utsc.project_coding_lads.repository.QuizRepository;
import com.utsc.project_coding_lads.service.CourseService;
import com.utsc.project_coding_lads.service.ImpactLearnerService;
import com.utsc.project_coding_lads.service.QuizQuestionService;
import com.utsc.project_coding_lads.service.QuizService;
import com.utsc.project_coding_lads.service.SolutionService;
import com.utsc.project_coding_lads.service.StudentAnswerService;
import com.utsc.project_coding_lads.validator.QuizValidator;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {

	@Autowired
	QuizRepository quizRepo;
	@Autowired
	QuizValidator quizValidator;
	@Autowired
	QuizQuestionService quizQuestionService;
	@Autowired
	SolutionService solnService;
	@Autowired
	StudentAnswerService studentAnswerService;
	@Autowired
	CourseService courseService;
	@Autowired
	ImpactLearnerService impactLearnerService;
	
	final static Logger log = LoggerFactory.getLogger(QuizServiceImpl.class);
	
	@Override
	public Integer createQuiz(Quiz quiz) throws ValidationFailedException {
		quizValidator.init(quiz.getCourse(), quiz.getQuizStartDate(), quiz.getQuizEndDate(), quiz.getQuizQuestions());
		quizValidator.validate();
		List<QuizQuestion> savedQuestions = new ArrayList<>();
		Course savedCourse = courseService.findCourseById(quiz.getCourse().getId());
		quiz.setCourse(savedCourse);
		Quiz savedQuiz = quizRepo.save(quiz);
		
		for (QuizQuestion question : quiz.getQuizQuestions()) {
			question.setQuiz(savedQuiz);
			Integer savedQuestionId =  quizQuestionService.createQuizQuestion(question);
			QuizQuestion savedQuestion = quizQuestionService.findQuizQuestionById(savedQuestionId);
			savedQuestion.setQuiz(savedQuiz);
			savedQuestion.getQuestionOptions().size();
			savedCourse.getStudents().size();
			for (ImpactLearnerCourse student : savedCourse.getStudents()) {
				StudentAnswer studentAnswer = new StudentAnswer();
				studentAnswer.setQuestion(savedQuestion);
				ImpactLearner learner = impactLearnerService.findLearnerById(student.getStudent().getId());
				studentAnswer.setStudent(learner);
				Integer savedStudentAnswerId = studentAnswerService.createBlankStudentAnswer(studentAnswer);
				studentAnswer = studentAnswerService.findStudentAnswerById(savedStudentAnswerId);
				savedQuestion.getStudentAnswers().add(studentAnswer);
				learner.getQuestions().add(studentAnswer);
				impactLearnerService.updateImpactLearner(learner);
			}
			savedQuestionId = quizQuestionService.updateQuizQuestion(savedQuestion);
			savedQuestion = quizQuestionService.findQuizQuestionById(savedQuestionId);
			savedQuestions.add(question);
		}
		quiz.getQuizQuestions().clear();
		quiz.getQuizQuestions().addAll(savedQuestions);
		savedQuiz = quizRepo.save(quiz);
		savedCourse.getQuizzes().add(savedQuiz);
		courseService.updateCourse(savedCourse);
		return savedQuiz.getId();
	}

	@Override
	public Quiz findQuizById(Integer id) throws ValidationFailedException {
		return quizRepo.findById(id).get();
	}

	@Override
	public List<Quiz> findAllQuizzesByCourseId(Integer id) throws ValidationFailedException {
		if (id == null)
			throw new MissingInformationException("Course id is null");
		Course course = courseService.findCourseById(id);
		List<Quiz> quizzes = course.getQuizzes();
		return quizzes;
	}

	@Override
	public Integer updateQuiz(Quiz quiz) throws ValidationFailedException {
		if (!existsById(quiz.getId())) throw new EntityNotExistException("That quiz does not exist.");
		return quizRepo.save(quiz).getId();
	}

	@Override
	public void deleteQuizById(Integer id) throws ValidationFailedException {
		if (!existsById(id)) throw new EntityNotExistException("That quiz does not exist.");
		quizRepo.deleteById(id);
	}

	@Override
	public Boolean existsById(Integer id) {
		return quizRepo.existsById(id);
	}

}
