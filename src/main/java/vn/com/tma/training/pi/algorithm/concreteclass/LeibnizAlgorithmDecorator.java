package vn.com.tma.training.pi.algorithm.concreteclass;

import vn.com.tma.training.pi.algorithm.abstractclass.AlgorithmDecorator;
import vn.com.tma.training.pi.algorithm.abstractclass.IAlgorithm;
import vn.com.tma.training.pi.algorithm.abstractclass.ParameterInput;

public class LeibnizAlgorithmDecorator extends AlgorithmDecorator {

	public LeibnizAlgorithmDecorator(IAlgorithm a) {
		algorithms = a;
	}

	@Override
	public void setParameter(ParameterInput input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double runAlgorithm() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getResult() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}