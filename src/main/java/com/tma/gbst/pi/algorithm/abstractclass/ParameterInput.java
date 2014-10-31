package com.tma.gbst.pi.algorithm.abstractclass;

import java.util.InputMismatchException;
import java.util.List;

public abstract class ParameterInput {

	protected List<Object> parameters;

	final public void setParameters(List<Object> parameters) {
		if (!checkParameter(parameters)) {
			throw new InputMismatchException("Wrong Leibniz formula input!");
		}
		this.parameters = parameters;
	}

	public List<?> getParameters() {
		return parameters;
	}

	public abstract boolean istype(String type);

	protected abstract boolean checkParameter(List<Object> parameters);
	
}
