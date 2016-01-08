package zoo.generator.model;

import java.nio.ByteBuffer;

import zoo.generator.enums.EventType;

public class EventTrame extends Trame {
	
	private short cageNumber;
	private EventType eventType;
	
	private byte magicNumber = 0x01;
	
	public EventTrame(short cageNumber, EventType eventType){
		this.cageNumber = cageNumber;
		this.eventType = eventType;
	}
	
	@Override
	public byte[] getBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(4); 
		buffer.put(magicNumber);
		buffer.put(super.getBytes(cageNumber));
		buffer.put(eventType.getCode());
		
		return buffer.array();
	}
	
	@Override
	public String toString() {
		return "[ID = " + cageNumber + " | EVT = " + this.eventType.name() + "]";
	}
	
}
