package vn.com.tma.training.pi.algorithm.abstractclass;

import java.util.InputMismatchException;

public abstract class ParameterInput {

	protected double[] parameters;

	final public void setParameters(double[] parameters) {
		if (!checkParameter(parameters)) {
			throw new InputMismatchException("Wrong Leibniz formula input!");
		}
		this.parameters = parameters;
	}

	public double[] getParameters() {
		return parameters;
	}

	public abstract boolean istype(String type);

	protected abstract boolean checkParameter(double[] parameters);

}
