package fr.lewon.web.bot.util.timeline.operation;

public abstract class Operation {

	/**
	 * Exécute l'operation et retourne le nombre de secondes avant sa prochaine exécution
	 * @return
	 * @throws Exception
	 */
	public abstract Integer process() throws Exception;

}
