/**
 * This code is use for training purpose in TMA Solution
 * Make a Pi number calculator
 */

package com.tma.gbst.pi;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.tma.gbst.pi.algorithm.abstractclass.AlgorithmWorkshop;
import com.tma.gbst.pi.algorithm.concreteclass.LeibnizWorkshop;
import com.tma.gbst.pi.calculator.CalculatorParameter;
import com.tma.gbst.pi.calculator.PiCalculator;

/**
 * This class use the PiCalculator and support the way to control program: pause
 * calculation, continue calculation, exit program
 * 
 */
public class App {
	static BigDecimal numberOfCalculations, calculationEachThread;
	static int numberOfThreads;
	static AlgorithmWorkshop workshop = new LeibnizWorkshop();
	static CalculatorParameter input = new CalculatorParameter();
	static PiCalculator piCalculator = null;
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		String control;
		final boolean[] running = new boolean[1];
		running[0] = false;

		while (true) {

			getInputFromUser();

			new Thread(new Runnable() {

				public void run() {
					try {
						running[0] = true;
						input.setParameters(numberOfCalculations,
								numberOfThreads, calculationEachThread);
						piCalculator = PiCalculator.newInstance(workshop, input);
						piCalculator.calculate();
						piCalculator.showInfo();
						running[0] = false;
						printControlGuide();
					} catch (Exception e) {
						System.out.println("Error: " + e.getMessage());
					} finally {
						numberOfCalculations = null;
					}

				}
			}).start();

			printControlGuide();

			while (!(control = scanner.nextLine()).equals("n")) {
				if (control.equals("e")) {
					scanner.close();
					System.out.println("Exited Program");
					System.exit(0);
				}
				if (running[0]) {

					if (control.equals("s")) {
						piCalculator.stopCalculation();
					}
					if (control.equals("i")) {
						piCalculator.showInfo();
					}
				}
			}
			if (running[0]) {
				piCalculator.stopCalculation();
			}
		}

	}

	private static void printControlGuide() {
		System.out.println("---------------Control-Guide-----------------");
		System.out.println("Input \'i\' to show current calculator info");
		System.out.println("Input \'s\' to stop calculation");
		System.out.println("Input \'n\' to start a new calculation");
		System.out.println("Input \'e\' to exit program");
		System.out.println("---------------------------------------------");
	}

	private static void getInputFromUser() {
		while (numberOfCalculations == null) {
			System.out
					.println("Input Parameter: [Number of calculations] [Number of threads] [Calculations each thread] ");
			String inputString = scanner.nextLine();
			while(!(inputString.length()>0)){
				inputString = scanner.nextLine();
			}
			String[] parameterStr = inputString.split(" ");
			try {
				if (parameterStr.length > 0) {
					numberOfCalculations = new BigDecimal(parameterStr[0]);
				}
				if (parameterStr.length > 1) {
					numberOfThreads = new Integer(parameterStr[1]);
				}
				if (parameterStr.length > 2) {
					calculationEachThread = new BigDecimal(parameterStr[2]);
				}
			} catch (InputMismatchException | NumberFormatException e) {
				System.out.println("Error: " + e.getCause());
				continue;
			}
		}
	}
}
