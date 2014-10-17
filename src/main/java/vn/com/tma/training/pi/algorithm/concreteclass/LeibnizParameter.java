package vn.com.tma.training.pi.algorithm.concreteclass;

import vn.com.tma.training.pi.algorithm.abstractclass.ParameterInput;

public class LeibnizParameter extends ParameterInput {

	final static String TYPE = "leibniz";

	public LeibnizParameter() {
		this.parameters = new double[2];
		this.parameters[0] = 0;
		this.parameters[1] = 0;
	}

	private boolean checkLeibnizParameter(double from, double to) {
		return ((to >= from) && (from >= 0)) ? true : false;
	}

	@Override
	public boolean istype(String type) {
		return type.equals(TYPE);
	}

	@Override
	protected boolean checkParameter(double[] parameters) {
		if (parameters != null) {
			if (parameters.length >= 2 && checkLeibnizParameter(parameters[0],
					parameters[1])){
				return true;
			}
		}
		return false;
	}

}
