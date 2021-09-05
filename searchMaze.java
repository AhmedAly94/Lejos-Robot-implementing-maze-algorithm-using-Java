package lab5;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class SearchMaze {

	private int numberOfWalls = 0;
	private Menu menu;
	private ColorSensor colorSensor;
	private UltrasonicSensor ultrasonicSensor;
	private EV3LargeRegulatedMotor rightMotor, leftMotor;
	private RobotMotion robotMotion;
	private FeedbackTurning feedbackTurning;

	private int[] arrayOfWalls = { 1, 1, 1, 1 };

	public SearchMaze(Menu menu) {

		this.menu = menu;
		colorSensor = new ColorSensor();
		ultrasonicSensor = new UltrasonicSensor();
		rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		robotMotion = new RobotMotion(rightMotor, leftMotor);
		feedbackTurning = new FeedbackTurning(rightMotor, leftMotor);

	}
	/*
	 * The method loops till the color selected by the user from the menu is
	 * detected. Then it stops the robot and displays a message on the LCD.
	 */

	public void moveMaze() {

		while (!isColorDetected()) {
			// loop until wall color is detected
		}
		robotMotion.stopMotors();
		LCD.clear();// clear to avoid overlap on LCD
		LCD.drawString("Target Detected", 0, 0);// Maze Solved
		LCD.drawString("Congratulations!!", 0, 1);
	}

	/*
	 * The method scans each array walls and sets each index with the distance
	 * between the wall and the robot if it has no wall. Otherwise, the array
	 * element is 1 by default. The robot exits each tile by looping for the array
	 * index with no wall found and choosing the first no wall path with the least
	 * array index number.
	 * 
	 * @return returns true if selected color is found.
	 */
	public boolean isColorDetected() {
		boolean checkColorResult = false;
		int orientation = 0;
		int[] tileWalls = scan();

		checkColorResult = checkDeadEnds();
		numberOfWalls = 0; // reset to check for other dead ends

		for (int j = 0; j < 4; j++) { // Turn to Orientation with no wall and then move

			if (tileWalls[j] != 1) {

				switch (j) {
				case 0:
					orientation = 0;

					break;
				case 1:
					orientation = 90;

					break;
				case 2:
					orientation = 180;

					break;
				case 3:
					orientation = 270;

					break;
				}

				feedbackTurning.turn(feedbackTurning.getOrientation() + orientation);

				feedbackTurning.turn(orientation - feedbackTurning.getAccumulatedError());// to eliminate deviation
																							// after rotation
				robotMotion.approachWall(tileWalls[j]);// robot moves straight until a wall is detected
				resetArray();
				feedbackTurning.resetAngle();
			}

		}

		return checkColorResult;
	}

	public float getDistance() {

		float distance;
		distance = ultrasonicSensor.getUltrasonicReadings();
		return distance;

	}

	/*
	 * Count the number of walls and create an array of walls for each tile.
	 * 
	 * @return returns the array of walls for each tile
	 */
	public int[] scan() {
		double wallRange = 20.0;
		for (int i = 0; i < 4; i++) {
			if (getDistance() > wallRange) { // if there is no wall

				arrayOfWalls[i] = (int) getDistance(); // update array with distance
			} else { // if no wall update it with 1
				arrayOfWalls[i] = 1;
				numberOfWalls++;
				LCD.drawString("NWalls is: " + numberOfWalls, 0, 6);

			}
			feedbackTurning.turn(feedbackTurning.getOrientation() + 90);

		}
		feedbackTurning.resetAngle();
		return arrayOfWalls;
	}

	public boolean getColor() {

		robotMotion.forwardToDistance();
		if (colorSensor.detectColor() == menu.getSelectedColor()) {
			return true;
		}

		return false;
	}

	/*
	 * It checks for dead ends by checking the number of walls, if a dead end is
	 * reached it checks the color of each wall.
	 * 
	 * @return returns true if selected color is detected
	 */
	public boolean checkDeadEnds() {
		int orientation;
		if (numberOfWalls == 3) {

			for (int x = 0; x < 4; x++) {
				getColor();
				if (getColor() == false) {
					robotMotion.backwardToDistance(); // Move robot back to tile center
					orientation = feedbackTurning.getOrientation() + 90;
					feedbackTurning.turn(orientation);// rotate to check other walls
					feedbackTurning.turn(orientation - feedbackTurning.getAccumulatedError());// to eliminate deviation
																								// after rotation
				} else {
					return true;
				}

			}
		}
		return false;

	}

	public void resetArray() { // resets the array of tiles
		for (int i = 0; i < 4; i++) {
			arrayOfWalls[i] = 1;
		}
	}

}