package com.tma.gbst.pi.algorithm.concreteclass;

import com.tma.gbst.pi.algorithm.abstractclass.AlgorithmTask;
import com.tma.gbst.pi.algorithm.abstractclass.IAlgorithm;

public class LeibnizTask extends AlgorithmTask {

	@Override
	protected IAlgorithm createAlgorithmObject() {
		return new LeibnizAlgorithm();
	}

}
