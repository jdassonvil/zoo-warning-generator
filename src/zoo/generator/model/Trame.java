package zoo.generator.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class Trame {

	public abstract byte[] getBytes(); 
	
	protected byte[] getBytes(short value){
		
		ByteBuffer buffer = ByteBuffer.allocate(2);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putShort(value);
		return buffer.array();			
	}
}
