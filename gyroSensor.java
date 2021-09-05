package lab5;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class GyroSensor {

	private EV3GyroSensor gyroSensor;
	private SampleProvider angle;
	private float[] angleReadings;
	private final int DELAY_MS = 10;

	public GyroSensor() {

		gyroSensor = new EV3GyroSensor(SensorPort.S3);
		angle = gyroSensor.getAngleMode();
		angleReadings = new float[angle.sampleSize()];

	}

	public int gyroAngle() {

		final int OFFSET = 0;

		while (true) { // fetching orientation continuously

			angle.fetchSample(angleReadings, OFFSET);

			float x = angleReadings[0];
			LCD.drawString("angle is: " + x, 0, 1);
			Delay.msDelay(DELAY_MS);
			return (int) angleReadings[0];
		}

	}

	public void resetAngle() {

		gyroSensor.reset();

	}
}