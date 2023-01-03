// package modelBaseTest;
//
//import java.io.FileNotFoundException;
//import java.util.Random;
//
//import javax.xml.ws.Action;
//
//import org.junit.Assert;
//import org.junit.Test;
//import nz.ac.waikato.modeljunit.FsmModel;
//import nz.ac.waikato.modeljunit.GreedyTester;
//import nz.ac.waikato.modeljunit.StopOnFailureListener;
//import nz.ac.waikato.modeljunit.Tester;
//import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
//import nz.ac.waikato.modeljunit.coverage.StateCoverage;
//import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
//
//public class SystemModelTest implements FsmModel{
//	private SystemStates modelState;
//	private TransitionSystem sut;
//	private boolean logged = false, alertsPage = false; 
//	private boolean postAlert = false, checkAlert = false, deleteAlerts = false;
//	
//	public SystemStates getState() {
//		return modelState;
//	}
//	
//	public void reset(final boolean b) {
//		modelState = SystemStates.NOT_LOGGED;
//		logged = false; 
//		alertsPage = false; 
//		homePage = false; 
//		postAlert = false;
//		checkAlert = false;
//		deleteAlerts = false;
//		
//		if (b) {
//			sut = new TransitionSystem();
//		}
//	}
//
//	public boolean logging() {
//		return getState().equals(SystemStates.NOT_LOGGED);
//	}
//	public @Action void log() {
//		sut.invalidLogIn();
//
//		logged = true;
//		modelState = SystemStates.LOGGED;
//
//		Assert.assertEquals("The model's logged state doesn't match the SUT's state.", logged, sut.isLogged());
//	}
//
//	public boolean loggingOut() {
//		return getState().equals(SystemStates.NOT_LOGGED);
//	}
//	public @Action void logOut() {
//		sut.goToLogOut();
//
//		logged = false;
//		modelState = SystemStates.NOT_LOGGED;
//
//		Assert.assertEquals("The model's logged state doesn't match the SUT's state.", logged, sut.isLogged());
//	}
//
//	public boolean viewingAlerts() {
//		return getState().equals(SystemStates.VIEW_ALERTS);
//	}
//	public @Action void viewAlerts() {
//		sut.goToAlerts();
//
//		alertsPage = true;
//		modelState = SystemStates.VIEW_ALERTS;
//
//		Assert.assertEquals("The model's page state doesn't match the SUT's state.", alertsPage, sut.isOnAlertsPage ());
//	}
//
//	public boolean postingAlerts() {
//		return getState().equals(SystemStates.VIEW_ALERTS);
//	}
//	public @Action void postAlerts() {
//		sut.postAlert();
//
//		alertsPage = true;
//		postAlert = true;
//		modelState = SystemStates.POST_ALERTS;
//
//		Assert.assertEquals("The model's post state doesn't match the SUT's state.", postAlert, sut.isPostingAlert());
//	}
//
//	public boolean deletingAlerts() {
//		return getState().equals(SystemStates.VIEW_ALERTS);
//	}
//	public @Action void deleteAlerts() {
//		sut.deleteAlert();
//
//		alertsPage = true;
//		deleteAlerts = true;
//		modelState = SystemStates.CLEAR_ALERTS;
//
//		Assert.assertEquals("The model's post state doesn't match the SUT's state.", deleteAlerts, sut.isDeleteingAlerts ());
//	}
//
//	public boolean checkingAlerts() {
//		return getState().equals(SystemStates.VIEW_ALERTS);
//	}
//	public @Action void checkAlerts() {
//		sut.checkAlert();
//
//		// alertsPage = true;
//		checkAlert = true;
//		modelState = SystemStates.CLEAR_ALERTS;
//
//		Assert.assertEquals("The model's post state doesn't match the SUT's state.", deleteAlerts, sut.isDeleteingAlerts ());
//	}
//
//	@Test
//	public void ModelRunner() throws FileNotFoundException {
//		final Tester tester = new GreedyTester(new SystemModelTest());
//		tester.setRandom(new Random());
//		tester.addListener(new StopOnFailureListener());
//		tester.addListener("verbose");
//		tester.addCoverageMetric(new TransitionPairCoverage());
//		tester.addCoverageMetric(new StateCoverage());
//		tester.addCoverageMetric(new ActionCoverage());
//		tester.generate(250);
//		tester.printCoverage();
//	}
//}
