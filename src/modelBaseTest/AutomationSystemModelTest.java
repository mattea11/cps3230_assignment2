package modelBaseTest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import javax.xml.ws.Action;

import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.Tester;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import main.EndPoints;
import main.MarketUMAuto;

public class AutomationSystemModelTest implements FsmModel{
	private SystemStates modelState;
	private TransitionSystem sut;

	private boolean logged = false, alertsPage = false; 
	private boolean postAlert = false, deleteAlerts = false, goodAlert = false, badState = false;

	MarketUMAuto ma = new MarketUMAuto();
	EndPoints ep = new EndPoints();
	
	public SystemStates getState() {
		return modelState;
	}
	
	public void reset(final boolean b) {
		modelState = SystemStates.NOT_LOGGED;
		logged = false; 
		alertsPage = false; 
		postAlert = false;
		deleteAlerts = false;
		
		if (b) {
			sut = new TransitionSystem();
		}
	}

	public boolean unLoggedGaurd(WebDriver driver) {
		ma.accessMarketUm(driver);
		ma.goToLogInValid(driver,"7f84a00a-eeac-47fa-b15c-ee7e7ff9378d");

		return getState().equals(SystemStates.LOGGED);
	}
	public @Action void log() {
		sut.invalidLogIn();

		logged = false;
		modelState = SystemStates.NOT_LOGGED;

		Assert.assertEquals("The model's logged state doesn't match the SUT's state.", logged, sut.isLogged());
	}

	public boolean loggingOutGaurd() {
		// ma.goToLogOut(driver);
		return getState().equals(SystemStates.NOT_LOGGED);
	}
	public @Action void logOut() {
		sut.goToLogOut();

		logged = false;
		modelState = SystemStates.NOT_LOGGED;

		Assert.assertEquals("The model's logged state doesn't match the SUT's state.", logged, sut.isLogged());
	}

	public boolean viewingAlertsGuard(WebDriver driver) {

		return getState().equals(SystemStates.POST_ALERTS) || getState().equals(SystemStates.CLEAR_ALERTS);
	}
	public @Action void viewAlerts(JSONArray alert) {
		sut.goToAlerts();

		if(getState() == SystemStates.POST_ALERTS){
			try {
				ep.postAlert(alert);
				postAlert = true;
				modelState = SystemStates.POST_ALERTS;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(getState() == SystemStates.CLEAR_ALERTS){
			try {
				ep.deleteAlerts();
				deleteAlerts = true;
				modelState = SystemStates.CLEAR_ALERTS;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Assert.assertEquals("The model's postiong state state doesn't match the SUT's state.", postAlert, sut.isPostingAlert());
		Assert.assertEquals("The model's clearing state doesn't match the SUT's state.", deleteAlerts, sut.isDeleteingAlerts());
	}

	public boolean postingAlertsGuard(JSONArray alert) throws IOException {
		ep.postAlert(alert);
		return getState().equals(SystemStates.POST_ALERTS);
	}
	public @Action void postAlerts() {
		sut.postAlert();

		postAlert = true;
		modelState = SystemStates.POST_ALERTS;

		Assert.assertEquals("The model's post state doesn't match the SUT's state.", postAlert, sut.isPostingAlert());
	}

	public boolean deletingAlertsGaurd() throws IOException {
		ep.deleteAlerts();
		return getState().equals(SystemStates.CLEAR_ALERTS);
	}
	public @Action void deleteAlerts() {
		sut.deleteAlert();

		deleteAlerts = true;
		modelState = SystemStates.CLEAR_ALERTS;

		Assert.assertEquals("The model's post state doesn't match the SUT's state.", deleteAlerts, sut.isDeleteingAlerts());
	}

	public boolean goodAlertGaurd(JSONArray alert) {
		ep.checkAlerts(alert);
		return getState().equals(SystemStates.GOOD_ALERT);
	}
	public @Action void goodAlert() {
		sut.checkGoodAlert();

		goodAlert = true;
		modelState = SystemStates.GOOD_ALERT;

		Assert.assertEquals("The model's alert state doesn't match the SUT's state.", goodAlert, sut.isGoodAlert());
	}

	public boolean badStateGaurd(JSONArray alert) {
		ep.checkAlerts(alert);		
		return getState().equals(SystemStates.BAD_STATE);
	}
	public @Action void badState() {
		sut.checkBadState();

		badState= true;
		modelState = SystemStates.BAD_STATE;

		Assert.assertEquals("The model's state doesn't match the SUT's state.", badState, sut.isBadState());
	}

	@Test
	public void ModelRunner() throws FileNotFoundException {
		final Tester tester = new GreedyTester(new AutomationSystemModelTest());
		tester.setRandom(new Random());
		tester.addListener(new StopOnFailureListener());
		tester.addListener("verbose");
		tester.addCoverageMetric(new TransitionPairCoverage());
		tester.addCoverageMetric(new StateCoverage());
		tester.addCoverageMetric(new ActionCoverage());
		tester.generate(250);
		tester.printCoverage();
	}
}
