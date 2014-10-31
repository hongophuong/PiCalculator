package com.tma.gbst.pi.algorithm.abstractclass;

import java.util.InputMismatchException;

public abstract class ParameterInput {

	protected Object[] parameters;

	final public void setParameters(Object... parameters) {
		if (!checkParameter(parameters)) {
			throw new InputMismatchException("Wrong Leibniz formula input!");
		}
		this.parameters = parameters;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public abstract boolean istype(String type);

	protected abstract boolean checkParameter(Object... parameters);
	
}
