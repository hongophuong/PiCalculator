package vn.com.tma.training.pi.calculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import vn.com.tma.training.pi.algorithm.abstractclass.ParameterInput;

public class CalculatorParameter extends ParameterInput {

	public static final String TYPE = "calculator";
	private final BigDecimal MAX_DOUBLE = new BigDecimal(Double.MAX_VALUE);
	private final BigDecimal DEFAULT_LOOP_EACH_THREAD = new BigDecimal("1E6");
	public static final int MAX_THREAD = 100;

	public CalculatorParameter() {
		this.parameters = new ArrayList<Object>(3);
		this.parameters.add(new BigDecimal(0));
		this.parameters.add(new Integer(1));
		this.parameters.add(new BigDecimal(0));
	}

	private void autoFillParameter() {
		BigDecimal totalNumberOfLoop = (BigDecimal) this.parameters.get(0);
		BigDecimal loopEachThread = (BigDecimal) this.parameters.get(2);
		Integer numberOfThread = (Integer) this.parameters.get(1);

		if (totalNumberOfLoop == null) {
			throw new NullPointerException("Number of loop is null!");
		}
		if (numberOfThread == null) {
			parameters.set(1, Runtime.getRuntime().availableProcessors());
		}
		if (loopEachThread == null) {
			if (totalNumberOfLoop.compareTo(DEFAULT_LOOP_EACH_THREAD) > 0) {
				parameters.set(1, DEFAULT_LOOP_EACH_THREAD);
			} else {
				parameters.set(1, totalNumberOfLoop);

			}
		}
	}

	private boolean checkCalculatorParameter(BigDecimal numberOfLoop,
			Integer numberOfThread, BigDecimal loopEachThread) {
		if (numberOfLoop.compareTo(MAX_DOUBLE) > 0
				|| loopEachThread.compareTo(MAX_DOUBLE) > 0) {
			throw new ArithmeticException("Double number is overflowed!");
		}
		if (numberOfThread <= 0 || numberOfThread > MAX_THREAD) {
			return false;
		}
		if (numberOfLoop.compareTo(loopEachThread) < 0
				|| loopEachThread.compareTo(BigDecimal.ZERO) < 0) {
			return false;
		}
		return true;
	}

	@Override
	protected boolean checkParameter(List<?> parameters) {
		if (parameters != null) {
			if (parameters.size() >= 3) {
				if (parameters.get(0) instanceof BigDecimal
						&& parameters.get(1) instanceof Integer
						&& parameters.get(2) instanceof BigDecimal) {
					autoFillParameter();
					return checkCalculatorParameter(
							(BigDecimal) parameters.get(0),
							(Integer) parameters.get(1),
							(BigDecimal) parameters.get(2));
				} else {
					throw new InputMismatchException(
							"Wrong type of inputed parameter!");
				}
			}
		}
		return false;
	}

	@Override
	public boolean istype(String type) {
		return type.equals(TYPE);
	}

}
