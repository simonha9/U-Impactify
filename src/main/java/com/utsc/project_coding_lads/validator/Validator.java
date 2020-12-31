package com.utsc.project_coding_lads.validator;

import com.utsc.project_coding_lads.exception.ValidationFailedException;

public interface Validator {

	public void validate() throws ValidationFailedException;
}
