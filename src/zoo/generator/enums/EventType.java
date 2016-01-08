package zoo.generator.enums;

public enum EventType {

	OPEN((byte) 0x01, "Ouverture d'une cage"), CLOSE((byte) 0x02, "Fermeture d'une cage"), PINFAILED((byte) 0x03,
			"Mauvais code"), FORCED((byte) 0x04, "Ouverture forcée"), SYSTEMFAILURE((byte) 0x05, "Panne du sysètme");

	private byte code;
	private String text;

	private EventType(byte code, String text) {
		this.code = code;
		this.text = text;
	}

	public byte getCode() {
		return this.code;
	}

	@Override
	public String toString() {
		return this.text;
	}
}
