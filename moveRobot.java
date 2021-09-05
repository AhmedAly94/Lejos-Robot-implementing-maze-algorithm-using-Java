package lab5;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.hardware.Button;

public class MoveRobot {

	public static void main(String[] args) {
		final int DELAY_MS = 10;
		Menu menu = new Menu();
		SearchMaze s1 = new SearchMaze(menu);

		while (!Button.ENTER.isDown()) {
			Delay.msDelay(DELAY_MS);
			menu.moveCursor();// Scroll the menu
		}

		menu.BuzzerOn();// beep sound before start
		LCD.clear();
		s1.moveMaze();
		menu.BuzzerOn();// Sounds if wall is detected

	}

}
