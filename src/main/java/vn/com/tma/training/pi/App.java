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
	static MultiThreadPiCalculator multiThreadPiCalculator = new MultiThreadPiCalculator();
	static BigDecimal numberOfCalculation, calculationEachThread;
	static int numberOfThread;
	static LeibnizWorkShop workshop = new LeibnizWorkShop();
	static CalculatorParameter input = new CalculatorParameter();
	static List<Object> parameters = new ArrayList<Object>();
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String control;
		
		while (true) {
			System.out.println("Number of calculations n =  ");
			numberOfCalculation = scanner.nextBigDecimal();
			System.out.println("Number of threads t (0 < t <"
					+ CalculatorParameter.MAX_THREAD + ") ");
			System.out
					.println("If input 0 then t = number of processor on computer! t = ");
			numberOfThread = scanner.nextInt();

			System.out.println("Number of calculations each thread c = ");
			calculationEachThread = scanner.nextBigDecimal();
			System.out
					.println("-----------------Calculate-PI-------------------");
			parameters.add(numberOfCalculation);
			parameters.add(numberOfThread);
			parameters.add(calculationEachThread);
			
			new Thread(new Runnable() {

				public void run() {
					try {
						long startPoint = System.currentTimeMillis();
						input.setParameters(parameters);
						workshop.setParameter(input);
						workshop.calculate();
						System.out.println("Eslapsed Time: "
								+ (System.currentTimeMillis() - startPoint));
					}  catch (ArithmeticException e) {
						System.out.println("Error: " + e.getMessage());
					} finally{
						parameters.clear();
					}

				}
			}).start();

			System.out
					.println("-----------------Control-Guide-------------------");
			System.out.println("Input \'p\' to pause calculation");
			System.out.println("Input \'c\' to continue calculation");
			System.out.println("Input \'s\' to stop calculation");
			System.out
					.println("Input \'next\' to stop current calculation an start a new one");
			System.out.println("Input \'e\' to exit program");
			System.out
					.println("-------------------------------------------------");

			while (!(control = scanner.next()).equals("next")) {
				if (control.equals("e")) {
					scanner.close();
					System.out.println("Exited Program");
					System.exit(0);
				}
				if (control.equals("p")) {
					multiThreadPiCalculator.pauseCalculation();
				}
				if (control.equals("c")) {
					multiThreadPiCalculator.continueCalculation();
				}
				if (control.equals("s")) {
					multiThreadPiCalculator.stopCalculation();
				}
			}
//			if (multiThreadPiCalculator.isRunning()) {
//				multiThreadPiCalculator.stopCalculation();
//			}
		}

	}
}
