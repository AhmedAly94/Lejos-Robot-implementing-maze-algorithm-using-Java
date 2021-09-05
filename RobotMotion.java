package lab5;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.RegulatedMotor;

public class RobotMotion {

	private EV3LargeRegulatedMotor rightMotor;
	private RegulatedMotor leftMotor;

	private int speed = 500, gearRatio = 3, wheelRadius = 3, covertToDegrees = 57;

	public RobotMotion(EV3LargeRegulatedMotor rightMotor, EV3LargeRegulatedMotor leftMotor) {
		this.rightMotor = rightMotor;
		this.leftMotor = leftMotor;
	}

	public void stopMotors() {

		rightMotor.stop();
		leftMotor.stop();

	}

	public void forwardToDistance() { /// move forward specific distance
		int thresholdDistance = 8;
		int alphaWheel = thresholdDistance / wheelRadius;

		rightMotor.setSpeed(speed);
		leftMotor.setSpeed(speed);

		rightMotor.rotate(calculateForward(alphaWheel, gearRatio, covertToDegrees), true);
		leftMotor.rotate(calculateForward(alphaWheel, gearRatio, covertToDegrees), false);

	}

	public void backwardToDistance() { /// move backward specific distance

		int desireddistance = 11;
		int alphaWheel = desireddistance / wheelRadius;

		rightMotor.setSpeed(speed);
		leftMotor.setSpeed(speed);

		rightMotor.rotate(calculateBackward(alphaWheel, gearRatio, covertToDegrees), true);
		leftMotor.rotate(calculateBackward(alphaWheel, gearRatio, covertToDegrees), false);

	}

	public void approachWall(int desireddistance) { // move before wall by 5 cm

		int thresholdDistance = 5;

		rightMotor.setSpeed(speed);
		leftMotor.setSpeed(speed);
		int alphaWheel = (Math.abs((int) (desireddistance - thresholdDistance))) / wheelRadius;

		rightMotor.rotate(calculateForward(alphaWheel, gearRatio, covertToDegrees), true);
		leftMotor.rotate(calculateForward(alphaWheel, gearRatio, covertToDegrees), false);

	}

	public static int calculateForward(int alphaWheel, int gearRatio, int covertToDegrees) {
		int alphaMotor = 0;
		alphaMotor = -(alphaWheel * gearRatio * covertToDegrees);
		return alphaMotor;
	}

	public static int calculateBackward(int alphaWheel, int gearRatio, int covertToDegrees) {

		int alphaMotor = 0;
		alphaMotor = (alphaWheel * gearRatio * covertToDegrees);
		return alphaMotor;
	}

}