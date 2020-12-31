package com.utsc.project_coding_lads.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utsc.project_coding_lads.domain.Course;
import com.utsc.project_coding_lads.domain.ImpactConsultant;
import com.utsc.project_coding_lads.domain.ImpactLearner;
import com.utsc.project_coding_lads.domain.ImpactLearnerCourse;
import com.utsc.project_coding_lads.domain.Invoice;
import com.utsc.project_coding_lads.domain.QuizQuestion;
import com.utsc.project_coding_lads.domain.QuizQuestionOption;
import com.utsc.project_coding_lads.domain.StudentAnswer;
import com.utsc.project_coding_lads.enums.QuizQuestionTypeEnum;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.MissingInformationException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.repository.ImpactLearnerRepository;
import com.utsc.project_coding_lads.service.CourseService;
import com.utsc.project_coding_lads.service.ImpactConsultantService;
import com.utsc.project_coding_lads.service.ImpactLearnerCourseService;
import com.utsc.project_coding_lads.service.ImpactLearnerService;
import com.utsc.project_coding_lads.service.InvoiceService;
import com.utsc.project_coding_lads.service.QuizQuestionService;
import com.utsc.project_coding_lads.service.StudentAnswerService;
import com.utsc.project_coding_lads.service.UserService;
import com.utsc.project_coding_lads.validator.CourseValidator;
import com.utsc.project_coding_lads.validator.QuizQuestionValidator;
import com.utsc.project_coding_lads.validator.UserValidator;

@Service
@Transactional
public class ImpactLearnerServiceImpl implements ImpactLearnerService {

	@Autowired
	ImpactLearnerRepository learnerRepo;
	@Autowired
	CourseService courseService;
	@Autowired
	ImpactConsultantService consultantService;
	@Autowired
	ImpactLearnerCourseService learnerCourseService;
	@Autowired
	UserService userService;
	@Autowired
	CourseValidator courseValidator;
	@Autowired
	UserValidator userValidator;
	@Autowired
	InvoiceService invoiceService;
	@Autowired
	QuizQuestionService questionService;
	@Autowired
	StudentAnswerService studentAnswerService;
	@Autowired
	QuizQuestionValidator questionValidator;
	
	@Override
	public Integer storeImpactLearner(ImpactLearner impactLearner) throws Exception {
		if (impactLearner == null)
			throw new MissingInformationException("Impact learner cannot be null.");
		return learnerRepo.save(impactLearner).getId();
	}

	@Override
	public Boolean existsById(Integer id) {
		return learnerRepo.existsById(id);
	}

	@Override
	public ImpactLearner findLearnerById(Integer id) throws ValidationFailedException {
		if (!existsById(id))
			throw new EntityNotExistException("The impact learner does not exist.");
		return learnerRepo.findById(id).get();
	}
	
	@Override
	public ImpactLearnerCourse addCourseToLearner(ImpactLearner student, Course course) throws Exception {
		if (student == null || course == null)
			throw new MissingInformationException("Student or course cannot be null.");
		courseValidator.init(courseService.findCourseById(course.getId()));
		courseValidator.validateExist();
		userValidator.init(userService.findUserById(student.getId()));
		userValidator.validateExists();
		userValidator.validateHasRole();
		ImpactLearnerCourse learnerCourse = new ImpactLearnerCourse();
		ImpactLearner savedStudent = findLearnerById(student.getId());
		Course savedCourse = courseService.findCourseById(course.getId());
		learnerCourse.setCourse(savedCourse);
		learnerCourse.setStudent(savedStudent);
		Integer savedLearnerCourseId = learnerCourseService.saveLearnerCourse(learnerCourse);
		learnerCourse = learnerCourseService.findLearnerCourseById(savedLearnerCourseId);
		
		savedStudent.getCourses().size();
		savedStudent.getCourses().add(learnerCourse);
		learnerRepo.save(savedStudent);
		
		savedCourse.getStudents().add(learnerCourse);
		courseService.updateCourse(savedCourse);
		
		Invoice inv = new Invoice();
		inv.setCourse(savedCourse);
		inv.setUser(userService.findUserById(student.getId()));
		inv.setCost(savedCourse.getCost());
		inv.setInitCost(savedCourse.getCost());
		invoiceService.saveInvoice(inv);
		
		return savedStudent.getCourses().get(savedStudent.getCourses().size()-1);
	}

	@Override
	public List<ImpactLearnerCourse> findCoursesByLearnerId(Integer id) throws Exception {
		ImpactLearner savedStudent = findLearnerById(id);
		savedStudent.getCourses().size();
		List<ImpactLearnerCourse> courses = savedStudent.getCourses();
		return courses;
	}

	@Override
	public Boolean removeCourseFromLearner(ImpactLearner student, Course course) throws Exception {
		if (student == null || course == null)
			throw new MissingInformationException("Student or course cannot be null.");
		courseValidator.init(courseService.findCourseById(course.getId()));
		courseValidator.validateExist();
		userValidator.init(userService.findUserById(student.getId()));
		userValidator.validate();
		userValidator.validateExists();
		userValidator.validateHasRole();
		ImpactLearner savedStudent = findLearnerById(student.getId());
		Course savedCourse = courseService.findCourseById(course.getId());
		savedStudent.getCourses().size();
		List<ImpactLearnerCourse> courses = savedStudent.getCourses();
		for (ImpactLearnerCourse ilc: courses) {
			if (ilc.getCourse().equals(savedCourse)) {
				learnerCourseService.deleteById(ilc.getId());
				courses.remove(ilc);
				learnerRepo.save(savedStudent);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<ImpactLearnerCourse> findCoursesByInstructorId(Integer studentId, Integer instructorId) throws Exception {
		if (studentId == null || instructorId == null)
			throw new MissingInformationException("Student Id or instructor cannot be null");
		consultantService.findImpactConsultantById(instructorId);
		userValidator.init(userService.findUserById(instructorId));
		userValidator.validate();
		userValidator.validateExists();
		userValidator.validateHasRole();
		ImpactLearner savedStudent = findLearnerById(studentId);
		ImpactConsultant savedInstructor = consultantService.findImpactConsultantById(instructorId);
		savedStudent.getCourses().size();
		List<ImpactLearnerCourse> foundCourses = new ArrayList<ImpactLearnerCourse>();
		for (ImpactLearnerCourse ilc: savedStudent.getCourses()) {
			if (ilc.getCourse().getInstructor().getId() == savedInstructor.getId())
				foundCourses.add(ilc);
		}
		return foundCourses;
	}

	@Override
	public Integer updateImpactLearner(ImpactLearner impactLearner) throws ValidationFailedException {
		if (!existsById(impactLearner.getId())) throw new EntityNotExistException("That Impact Learner does not exist.");
		return learnerRepo.save(impactLearner).getId();
	}

	@Override
	public List<StudentAnswer> answerQuizQuestions(List<StudentAnswer> studentAnswers) throws Exception {
		if (studentAnswers == null || studentAnswers.size() == 0)
			throw new MissingInformationException("The list of student answers cannot be null or empty");
		List<StudentAnswer> savedStudentAnswers = new ArrayList<>();
		for (StudentAnswer studentAnswer: studentAnswers) {
			if (studentAnswer.getStudent() == null || studentAnswer.getQuestion() == null || studentAnswer.getStudent().getId() == null
					|| studentAnswer.getQuestion().getId() == null || studentAnswer.getStudentAnswer() == null)
				throw new MissingInformationException("The question, question ID, student, student ID, or answer cannot be null");
			questionValidator.init(questionService.findQuizQuestionById(studentAnswer.getQuestion().getId()).getQuestionType(),
					questionService.findQuizQuestionById(studentAnswer.getQuestion().getId()).getQuestionOptions());
			questionValidator.validate();
			userValidator.init(userService.findUserById(studentAnswer.getStudent().getId()));
			userValidator.validate();
			userValidator.validateExists();
			userValidator.validateHasRole();
			StudentAnswer savedAnswer = studentAnswerService.findByStudentAndQuestion(studentAnswer.getQuestion().getId(), studentAnswer.getStudent().getId());
			savedAnswer.setStudentAnswer(studentAnswer.getStudentAnswer());
			Integer updatedAnswerId = studentAnswerService.updateStudentAnswer(savedAnswer);
			savedStudentAnswers.add(studentAnswerService.findStudentAnswerById(updatedAnswerId));
		}
		return savedStudentAnswers;
	}

}