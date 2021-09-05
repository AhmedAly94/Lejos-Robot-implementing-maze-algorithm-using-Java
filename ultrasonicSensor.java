package lab5;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class UltrasonicSensor {

	private EV3UltrasonicSensor distanceSensor;
	private int OFFSET = 0;
	private final int CONVERSION_TO_CM=100;

	public UltrasonicSensor() {
		distanceSensor = new EV3UltrasonicSensor(SensorPort.S4);
	}

	public float getUltrasonicReadings() {

		float currentDistance = 0;
		float[] distanceReadings = new float[distanceSensor.sampleSize()];

		SampleProvider distance = distanceSensor.getDistanceMode();// read distance

		distance.fetchSample(distanceReadings, OFFSET);
		currentDistance = distanceReadings[0] * CONVERSION_TO_CM;

		LCD.drawString("The dist is: " + currentDistance + "cm", 0, 2);// print distance in cm to LCD

		return currentDistance;

	}
}