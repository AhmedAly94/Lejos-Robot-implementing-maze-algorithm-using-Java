package lab5;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;

public class ColorSensor {

	private EV3ColorSensor ColorSensor;

	public ColorSensor() {
		ColorSensor = new EV3ColorSensor(SensorPort.S1);
	}

	public String detectColor() {

		final int OFFSET = 0;// ColorSensor
		String lcdPrintColorID = "";

		SensorMode colorID = ColorSensor.getColorIDMode();

		float[] readings = new float[colorID.sampleSize()]; // array of sensor readings

		colorID.fetchSample(readings, OFFSET);

		/// Check different color IDs
		switch ((int) readings[OFFSET]) {

		case Color.NONE:
			lcdPrintColorID = "NONE";
			break;
		case Color.BLACK:
			lcdPrintColorID = "BLACK";
			break;
		case Color.BLUE:
			lcdPrintColorID = "BLUE";
			break;
		case Color.GREEN:
			lcdPrintColorID = "GREEN";
			break;
		case Color.YELLOW:
			lcdPrintColorID = "YELLOW";
			break;
		case Color.RED:
			lcdPrintColorID = "RED";
			break;
		case Color.WHITE:
			lcdPrintColorID = "WHITE";
			break;
		case Color.BROWN:
			lcdPrintColorID = "BROWN";
			break;
		default:
			lcdPrintColorID = "UNKNOWN";
			break;
		}

		LCD.drawString("color is: " + lcdPrintColorID, 0, 4);

		return lcdPrintColorID;
	}

}
