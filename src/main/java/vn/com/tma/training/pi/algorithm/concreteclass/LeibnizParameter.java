package vn.com.tma.training.pi.algorithm.concreteclass;

import java.util.ArrayList;
import java.util.List;

import vn.com.tma.training.pi.algorithm.abstractclass.ParameterInput;

public class LeibnizParameter extends ParameterInput {

	final static String TYPE = "leibniz";

	public LeibnizParameter() {
		this.parameters = new ArrayList<Object>(2);
		this.parameters.add(0);
		this.parameters.add(0);
	}

	@Override
	public boolean istype(String type) {
		return type.equals(TYPE);
	}

	@Override
	protected boolean checkParameter(List<?> parameters) {
		if (parameters != null) {
			if (parameters.size() >= 2
					&& checkLeibnizParameter((Double) parameters.get(0),
							(Double) parameters.get(1))) {
				return true;
			}
		}
		return false;
	}

	private boolean checkLeibnizParameter(double from, double to) {
		return ((to >= from) && (from >= 0)) ? true : false;
	}

}
