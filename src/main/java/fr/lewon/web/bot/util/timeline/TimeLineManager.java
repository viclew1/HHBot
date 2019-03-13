package fr.lewon.web.bot.util.timeline;

import java.util.ArrayList;
import java.util.List;

import fr.lewon.web.bot.util.timeline.operation.Operation;
import fr.lewon.web.bot.util.timeline.operation.TimedOperation;

public enum TimeLineManager {

	INSTANCE;

	private List<TimedOperation> toProcess = new ArrayList<>();

	private List<TimedOperation> addedActions = new ArrayList<>();


	public void addAction(Operation operation, Integer secondsUntilExec) {
		Long operationTime = System.currentTimeMillis() + secondsUntilExec * 1000;
		addedActions.add(new TimedOperation(operationTime, operation));
	}

	public void processActionsStored() throws Exception {
		List<TimedOperation> nonRenewedOperations = new ArrayList<>();
		for (TimedOperation toProcess : toProcess) {
			if (System.currentTimeMillis() < toProcess.getExecTime()) {
				continue;
			}
			Integer secondsUntilExec = toProcess.getOperation().process();
			if (secondsUntilExec == null) {
				nonRenewedOperations.add(toProcess);
			} else {
				toProcess.setExecTime(System.currentTimeMillis() + secondsUntilExec * 1000);
			}
		}
		toProcess.removeAll(nonRenewedOperations);
	}

	private void prepareAddedActions() {
		toProcess.addAll(addedActions);
		addedActions.clear();
	}

	public void run() throws Exception {
		while (true) {
			Long startTime = System.currentTimeMillis();
			prepareAddedActions();
			processActionsStored();
			Long sleepTime = startTime + 1000 - System.currentTimeMillis();
			if (sleepTime > 0) {
				Thread.sleep(sleepTime);
			}
		}
	}

}
