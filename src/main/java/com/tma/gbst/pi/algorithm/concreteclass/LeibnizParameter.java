package com.tma.gbst.pi.algorithm.concreteclass;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import com.tma.gbst.pi.algorithm.abstractclass.ParameterInput;


public class LeibnizParameter extends ParameterInput {

	final public static String TYPE = "leibniz";

	/**
	 * Create new parameter object for LeibnizAlgorithm. By default, this
	 * parameter object have a list of 2 double number
	 */
	public LeibnizParameter() {
		this.parameters = new ArrayList<Object>(2);
		this.parameters.add(0.0);
		this.parameters.add(0.0);
	}

	@Override
	public boolean istype(String type) {
		return type.equals(TYPE);
	}

	@Override
	protected boolean checkParameter(List<Object> parameters) {
		if (parameters != null) {
			if (parameters.size() >= 2 && checkLeibnizParameter(parameters)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param parameters a list of parameters 
	 * @return true if parameter is legal 
	 */
	private boolean checkLeibnizParameter(List<Object> parameters) {
		if (parameters.get(0) == null || parameters.get(1) == null) {
			throw new NullPointerException("Leibniz parameter can not be null");
		}
		if (parameters.get(0) instanceof Double
				&& parameters.get(1) instanceof Double) {
			double from = (Double) parameters.get(0);
			double to = (Double) parameters.get(1);
			return ((to >= from) && (from >= 0)) ? true : false;
		} else {
			throw new InputMismatchException(
					"Input Leibniz paramater must be Double");
		}
	}
}
