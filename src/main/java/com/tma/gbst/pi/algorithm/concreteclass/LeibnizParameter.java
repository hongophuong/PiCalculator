package com.tma.gbst.pi.algorithm.concreteclass;

import java.util.InputMismatchException;

import com.tma.gbst.pi.algorithm.abstractclass.ParameterInput;

public class LeibnizParameter extends ParameterInput {

	final public static String TYPE = "leibniz";

	/**
	 * Create new parameter object for LeibnizAlgorithm. By default, this
	 * parameter object have a list of 2 double number
	 */
	public LeibnizParameter() {
		this.parameters = new Object[2];
		parameters[0] = parameters[1] = 0;
	}

	@Override
	public boolean istype(String type) {
		return type.equals(TYPE);
	}

	@Override
	protected boolean checkParameter(Object... parameters) {
		if (parameters != null) {
			if (parameters.length >= 2 && checkLeibnizParameter(parameters)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param parameters
	 *            a list of parameters
	 * @return true if parameter is legal
	 */
	private boolean checkLeibnizParameter(Object... parameters) {
		if (parameters[0] == null || parameters[1] == null) {
			throw new NullPointerException("Leibniz parameter can not be null");
		}
		if (parameters[0] instanceof Double && parameters[1] instanceof Double) {
			double from = (Double) parameters[0];
			double to = (Double) parameters[1];
			return ((to >= from) && (from >= 0)) ? true : false;
		} else {
			throw new InputMismatchException(
					"Input Leibniz paramater must be Double");
		}
	}
}
