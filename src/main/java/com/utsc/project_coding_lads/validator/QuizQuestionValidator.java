package com.utsc.project_coding_lads.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.utsc.project_coding_lads.domain.QuizQuestionOption;
import com.utsc.project_coding_lads.enums.QuizQuestionTypeEnum;
import com.utsc.project_coding_lads.exception.ValidationFailedException;

@Component
public class QuizQuestionValidator implements Validator {


	private String questionType;
	private List<QuizQuestionOption> questionOptions = new ArrayList<>();
	
	public void init(String questionType, List<QuizQuestionOption> questionOptions) {
		this.questionType = questionType;
		this.questionOptions.addAll(questionOptions);
	}
	
	@Override
	public void validate() throws ValidationFailedException {
		if (!(questionType.equals(QuizQuestionTypeEnum.MULTIPLE_CHOICE.name()) || questionType.equals(QuizQuestionTypeEnum.SHORT_ANSWER.name()))) {
			throw new ValidationFailedException("questionType has to be either " + QuizQuestionTypeEnum.MULTIPLE_CHOICE.name() + " or " + QuizQuestionTypeEnum.SHORT_ANSWER.name());
		}
		if (questionType.equals(QuizQuestionTypeEnum.MULTIPLE_CHOICE.name())) {
			if (questionOptions.isEmpty()) throw new ValidationFailedException(QuizQuestionTypeEnum.MULTIPLE_CHOICE.name() + " questions cannot have empty question option lists.");
		}
	}

	
	
}
