/**
 * This code is use for training purpose in TMA Solution
 * Make a Pi number calculator
 */

package com.tma.gbst.pi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.tma.gbst.pi.algorithm.concreteclass.LeibnizWorkShop;
import com.tma.gbst.pi.calculator.CalculatorParameter;
import com.tma.gbst.pi.calculator.MultiThreadPiCalculator;

/**
 * This class use the PiCalculator and support the way to control program: pause
 * calculation, continue calculation, exit program
 * 
 */
public class App {
	static BigDecimal numberOfCalculation, calculationEachThread;
	static int numberOfThread;
	static LeibnizWorkShop workshop = new LeibnizWorkShop();
	static CalculatorParameter input = new CalculatorParameter();
	static List<Object> parameters = new ArrayList<Object>();
	static MultiThreadPiCalculator multiThreadPiCalculator = new MultiThreadPiCalculator();

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String control;

		while (true) {
			while (numberOfCalculation == null) {
				System.out
						.println("Input Parameter: [Number of calculations] [Number of threads] [Calculations each thread] ");
				String inputString = scanner.nextLine();
				String[] parameterStr = inputString.split(" ");
				try {
					if (parameterStr.length > 0) {
						numberOfCalculation = new BigDecimal(parameterStr[0]);
					}
					if (parameterStr.length > 1) {
						numberOfThread = new Integer(parameterStr[1]);
					}
					if (parameterStr.length > 2) {
						calculationEachThread = new BigDecimal(parameterStr[2]);
					}
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
					continue;
				}
			}
			parameters.add(numberOfCalculation);
			parameters.add(numberOfThread);
			parameters.add(calculationEachThread);

			new Thread(new Runnable() {

				public void run() {
					try {
						System.out
								.println("-----------Calculate-PI-----------");
						input.setParameters(parameters);
						multiThreadPiCalculator.setWorkShop(workshop, input);
						multiThreadPiCalculator.calculate();
					} catch (ArithmeticException e) {
						System.out.println("Error: " + e.getMessage());
					} finally {
						parameters.clear();
						numberOfCalculation = null;
					}

				}
			}).start();

			System.out.println("---------------Control-Guide-----------------");
			System.out.println("Input \'i\' to show current calculator info");
			System.out.println("Input \'s\' to stop calculation");
			System.out.println("Input \'n\' to start a new calculation");
			System.out.println("Input \'e\' to exit program");
			System.out.println("---------------------------------------------");

			while (!(control = scanner.nextLine()).equals("n")) {
				if (control.equals("e")) {
					scanner.close();
					System.out.println("Exited Program");
					System.exit(0);
				}
				if (control.equals("s")) {
					multiThreadPiCalculator.stopCalculation();
				}
				if (control.equals("i")) {
					multiThreadPiCalculator.showInfo();
				}
			}
			multiThreadPiCalculator.stopCalculation();
		}

	}
}
