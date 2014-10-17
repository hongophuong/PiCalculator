package vn.com.tma.training.pi.calculator;

import vn.com.tma.training.pi.algorithm.abstractclass.ParameterInput;

public class CalculatorParameter extends ParameterInput {

	final static String TYPE = "calculator";
	double[] parameters = new double[3];
	
	public CalculatorParameter(double totalNumberOfLoop, double numberOfThread, double loopEachThread) {
	}

	private boolean checkCalculatorParameter(double from, double to) {
		return ((to >= from) && (from >= 0)) ? true : false;
	}

	@Override
	public double[] getParameters() {
		return parameters;
	}

	@Override
	protected boolean checkParameter(double[] parameters) {
		if (parameters != null) {
			if (parameters.length >= 3 && checkCalculatorParameter(parameters[0],
					parameters[1])){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean istype(String type) {
		return type.equals(TYPE);
	}

}
