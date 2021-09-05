package lab5;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class FeedbackTurning implements Turner {

	private GyroSensor gyroSensor = new GyroSensor();
	private EV3LargeRegulatedMotor rightMotor, leftMotor;
	private int accumulatedError = 0;
	private final int INITIAL_SPEED= 250;
	private final int DELAY_MS = 100;

	public FeedbackTurning(EV3LargeRegulatedMotor rightMotor, EV3LargeRegulatedMotor leftMotor) {
		this.rightMotor = rightMotor;
		this.leftMotor = leftMotor;

	}

	public void setSpeed(int degreesPerSecond) {
		rightMotor.startSynchronization();
		rightMotor.setSpeed(degreesPerSecond);
		leftMotor.setSpeed(degreesPerSecond);
		rightMotor.endSynchronization();
	}

	/*
	 * The method performs proportional speed control. The error is calculated by
	 * subtracting the readings of the Gyro sensor from the desired rotation degrees
	 * and then the error multiplied by a proportional gain. It loops till the
	 * desired orientation is reached by a certain predefined uncertainty delta. The
	 * speed is then gradually decreased till the desired orientation is reached. It
	 * also accumulates the error to be compensated at the end of each rotation in
	 * the SearchMaze class.
	 * 
	 * @param degress the desired orientation to be reached by the robot
	 */
	public void turn(int degrees) {
		double delta = 0.2;
		int pGain = 15;

		setSpeed(INITIAL_SPEED);

		while ((((degrees - Math.abs(getOrientation()))) >= delta)) {

			int errorPrint = (degrees - Math.abs(getOrientation()));

			setSpeed(pGain * errorPrint);
			accumulatedError += errorPrint;
			LCD.drawString("Error: " + errorPrint, 0, 5);

			rightMotor.backward();
			leftMotor.forward();

		}

		// Stop the robot if desired orientation is reached
		rightMotor.stop();
		leftMotor.stop();
		Delay.msDelay(DELAY_MS);

	}

	public int getOrientation() {// returns current orientation of the robot

		int currentAngle;

		currentAngle = gyroSensor.gyroAngle();// continuously fetch the feedback angle from the Gyrosensor

		return currentAngle;

	}

	public void resetAngle() {

		gyroSensor.resetAngle();

	}

	public int getAccumulatedError() {
		return accumulatedError;
	}

}