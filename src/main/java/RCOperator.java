public class RCOperator {

	String number;
	String timeStart;
	String timeStop;


	public RCOperator(String number, String timeStart, String timeStop) {
		this.number = number;
		this.timeStart = timeStart;
		this.timeStop = timeStop;
	}

	public RCOperator(String number) {
		this(number, "00:00", "00:00");
	}

}