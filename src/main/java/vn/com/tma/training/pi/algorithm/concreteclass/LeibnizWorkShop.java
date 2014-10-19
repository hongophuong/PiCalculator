package vn.com.tma.training.pi.algorithm.concreteclass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import vn.com.tma.training.pi.algorithm.abstractclass.AlgorithmWorkShop;
import vn.com.tma.training.pi.algorithm.abstractclass.CalculationTask;
import vn.com.tma.training.pi.calculator.CalculatorParameter;

public class LeibnizWorkShop implements AlgorithmWorkShop {
	double nCalculation, nCalcEachThread;
	int nThread;
	private List<CalculationTask> taskList;
	private ExecutorService executor; // To create thread pool

	private double Pi; // store calculated result

	public void setParameter(CalculatorParameter input) {
		List<?> parameters = input.getParameters();
		this.nCalculation = ((BigDecimal) parameters.get(0)).doubleValue();
		this.nThread = (Integer) parameters.get(1);
		this.nCalcEachThread = ((BigDecimal) parameters.get(2)).doubleValue();
	}

	public double calculate() {
		int nTask;
		double lowerBound;
		Pi = 0.0;
		// Create a threadpool have fixed number of threads
		executor = Executors.newFixedThreadPool(nThread);
		if (nCalculation == 0) {
			nTask = 1;
		} else {
			nTask = (int) (nCalculation / nCalcEachThread); // compute number of
															// task
		}

		// Create a fixed number of task instance, it equal to number of threads
		// This help to not create too many task objects
		taskList = new ArrayList<CalculationTask>();
		int taskListSize = Math.min(nTask, nThread);
		for (int i = 0; i < taskListSize; i++) {
			CalculationTask temp = new CalculationTask();
			temp.setAlgorithm(new LeibnizAlgorithm());
			taskList.add(temp);
		}

		Iterator<CalculationTask> taskIterator = taskList.iterator();
		CalculationTask temp = null;
		try {
			LeibnizParameter parameter = new LeibnizParameter();
			List<Object> parameterList = new ArrayList<Object>();
			for (int i = 0; i < nTask; i++) {
				// devide calculation for each task
				if (taskIterator.hasNext()) {
					temp = taskIterator.next();
					lowerBound = i * nCalcEachThread;
					parameterList.add(lowerBound + (i == 0 ? 0 : 1));
					parameterList.add(lowerBound + nCalcEachThread);
					parameter.setParameters(parameterList);
					temp.setAlgorithmInput(parameter);
					parameterList.clear();
				}
				// when devide calculation for all task instance, let the thread
				// pool do these tasks
				if (((i + 1) % taskListSize) == 0) {
					List<Future<Double>> results = executor.invokeAll(taskList);
					for (Future<Double> result : results) {
						Pi += result.get();
					}
					taskIterator = taskList.iterator(); // reset index of task
				}
			}

			// // the last task contain calculation may remained
			// if ((nCalculation - completedCalculation) > 0) {
			// taskList.get(0).setStartNumber(completedCalculation + 1);
			// taskList.get(0).setEndNumber(nCalculation);
			// Future<IAlgorithm> result = executor.submit(taskList.get(0));
			// Pi += result.get().getSum();
			// }
		} catch (Exception e) {
			// e.printStackTrace();
		}
		executor.shutdown(); // Thread pool accept no more task

		// System.out.println("completed calculation: " + completedCalculation);
		System.out.println("Finished all threads. PI: " + Pi);
		System.out.println("Result in Math.PI:        " + Math.PI);
		return Pi;

	}

}
