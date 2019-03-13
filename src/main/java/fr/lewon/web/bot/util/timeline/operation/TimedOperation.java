package fr.lewon.web.bot.util.timeline.operation;

public class TimedOperation {

	private Long execTime;
	private Operation operation;


	public TimedOperation(Long execTime, Operation operation) {
		this.execTime = execTime;
		this.operation = operation;
	}


	public Long getExecTime() {
		return execTime;
	}
	public void setExecTime(Long execTime) {
		this.execTime = execTime;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}

}
