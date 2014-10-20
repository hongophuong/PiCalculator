/**
 * This code is use for training purpose in TMA Solution
 * Make a Pi number calculator
 */

package vn.com.tma.training.pi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import vn.com.tma.training.pi.algorithm.concreteclass.LeibnizWorkShop;
import vn.com.tma.training.pi.calculator.CalculatorParameter;
import vn.com.tma.training.pi.calculator.MultiThreadPiCalculator;

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
					System.out.println(inputString);
					if (parameterStr.length > 0) {
						numberOfCalculation = new BigDecimal(parameterStr[0]);
						System.out.println(numberOfCalculation);
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
						System.out.println("-----------------Calculate-PI-------------------");						
						input.setParameters(parameters);
						workshop.setParameter(input);
						multiThreadPiCalculator.setWorkShop(workshop);
						multiThreadPiCalculator.calculate();
					} catch (ArithmeticException e) {
						System.out.println("Error: " + e.getMessage());
					} finally {
						parameters.clear();
						numberOfCalculation = null;
					}

				}
			}).start();

			System.out
					.println("-----------------Control-Guide-------------------");
			System.out.println("Input \'s\' to stop calculation");
			System.out
					.println("Input \'n\' to stop current calculation an start a new one");
			System.out.println("Input \'e\' to exit program");
			System.out
					.println("-------------------------------------------------");

			while (!(control = scanner.nextLine()).equals("n")) {
				if (control.equals("e")) {
					scanner.close();
					System.out.println("Exited Program");
					System.exit(0);
				}
				if (control.equals("s")) {
					multiThreadPiCalculator.stopCalculation();
				}
			}
		}

	}
}
