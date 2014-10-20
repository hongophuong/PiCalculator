package vn.com.tma.training.pi.algorithm.abstractclass;

import vn.com.tma.training.pi.calculator.CalculatorParameter;

public interface AlgorithmWorkShop {

	public void setParameter(CalculatorParameter input);

	public double run();

	public void stop();

}