package com.tma.gbst.pi.algorithm.abstractclass;

import com.tma.gbst.pi.calculator.CalculatorParameter;


/**
 * A workshop have a number of worker (threads) will do some computing tasks. It
 * take a CalculatorParameter and manage to divide task for each worker.
 * 
 * @author hngophuong
 * @see CalculatorParameter
 */
public interface AlgorithmWorkshop {

	public AlgorithmWorkshop setParameter(ParameterInput input);

	public double run();

	public void stop();

	public String showInfo();

}