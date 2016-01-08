package zoo.generator.model;

import java.nio.ByteBuffer;

public class InfoTrame extends Trame {
	
	private byte magicNumber = 0x00;
	
	private short cageNumber;
	private byte temperature;
	private byte humidity;
	private byte sound;
	
	public InfoTrame(short cageNumber, byte temperature, byte humidity, byte sound) {
		super();
		this.cageNumber = cageNumber;
		this.temperature = temperature;
		this.humidity = humidity;
		this.sound = sound;
	}
	
	public InfoTrame(int cageNumber, int temperature, int humidity, int sound){
		
		this.cageNumber = (short) cageNumber;
		this.temperature = (byte) temperature;
		this.humidity = (byte) humidity;
		this.sound = (byte) sound;
	}
	
	@Override
	public byte[] getBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(6); 
		buffer.put(magicNumber);
		buffer.put(super.getBytes(cageNumber));
		buffer.put(temperature);
		buffer.put(humidity);
		buffer.put(sound);
	
		return buffer.array();
	}

	@Override
	public String toString() {
		return "[ID = " + cageNumber + " | T = " + temperature + " | H = " + humidity + " | D= " + sound + "]";
	}
	

}
