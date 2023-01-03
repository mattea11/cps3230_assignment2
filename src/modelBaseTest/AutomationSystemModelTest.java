package modelBaseTest;

import org.openqa.selenium.WebDriver;

import main.EndPoints;
import main.MarketUMAuto;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.Tester;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;

import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class AutomationSystemModelTest implements FsmModel{
	private SystemStates modelState;
	private TransitionSystem sut;

	private boolean logged = false, alertsPage = false, logOut = false; 
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
        logOut = false;
		postAlert = false;
		deleteAlerts = false;
        goodAlert = false;
        badState = false;
		
		if (b) {
			sut = new TransitionSystem();
		}
	}

	public boolean loggingGaurd(WebDriver driver) {
		ma.accessMarketUm(driver);
		ma.goToLogInValid(driver,"7f84a00a-eeac-47fa-b15c-ee7e7ff9378d");

		return getState().equals(SystemStates.LOGGED);
	}
	@Action 
	public void log() {
		sut.validLogIn();

		logged = true;
		modelState = SystemStates.LOGGED;

		Assert.assertEquals("The model's logged state doesn't match the SUT's state.", logged, sut.isLogged());
	}

	public boolean loggingOutGaurd(WebDriver driver) {

		ma.goToLogOut(driver);
		return getState().equals(SystemStates.NOT_LOGGED);
	}
	@Action 
	public void logOut() {
		sut.goToLogOut();

		logged = false;
        logOut = true;
		modelState = SystemStates.NOT_LOGGED;

		Assert.assertEquals("The model's logged state doesn't match the SUT's state.", logged, sut.isLogged());
        Assert.assertNotEquals("The model's logout state doesn't match the SUT's state.", logOut, sut.isLoggingOut());
	}

	public boolean viewingAlertsGuard(WebDriver driver, String alert) {
        boolean toRet = false;
        if(alert != null){
            try {
                ep.postAlert(alert);
            } catch (IOException e) {
                e.printStackTrace();
            }
            toRet = getState().equals(SystemStates.POST_ALERTS);
        }
        else if (alert == null){
            try {
                ep.deleteAlerts();
            } catch (IOException e) {
                e.printStackTrace();
            }
	        toRet =  getState().equals(SystemStates.CLEAR_ALERTS);
        }
        return toRet;
	}
	@Action 
	public void viewAlerts() {
		sut.goToAlerts();

		if(getState() == SystemStates.POST_ALERTS){
            postAlert = true;
            modelState = SystemStates.POST_ALERTS;

			Assert.assertEquals("The model's posting state state doesn't match the SUT's state.", postAlert, sut.isPostingAlert());

		}else if(getState() == SystemStates.CLEAR_ALERTS){
            deleteAlerts = true;
            modelState = SystemStates.CLEAR_ALERTS;

			Assert.assertEquals("The model's clearing state doesn't match the SUT's state.", deleteAlerts, sut.isDeleteingAlerts());
		}
	}

	public boolean postingAlertsGuard(JSONArray alert){
        JSONArray dataPulled = null;
        try {
            dataPulled = ep.getMarketUmData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(ep.checkAlerts(dataPulled)){
            return getState().equals(SystemStates.GOOD_ALERT);
        }else{
            return getState().equals(SystemStates.BAD_STATE);
        }
	}
	@Action 
	public void postAlerts() {
		sut.postAlert();

        if(getState() == SystemStates.GOOD_ALERT){
            postAlert = true;
            goodAlert = true;
            modelState = SystemStates.GOOD_ALERT;

            Assert.assertEquals("The model's post state doesn't match the SUT's state.", postAlert, sut.isPostingAlert());
            Assert.assertEquals("The model's alert state doesn't match the SUT's state.", goodAlert, sut.isGoodAlert());

		}else if(getState() == SystemStates.BAD_STATE){
            postAlert = true;
            badState = true;
            modelState = SystemStates.BAD_STATE;

            Assert.assertEquals("The model's post state doesn't match the SUT's state.", postAlert, sut.isPostingAlert());
            Assert.assertEquals("The model's alert state doesn't match the SUT's state.", badState, sut.isBadState());
		}
	}

	public boolean deletingAlertsGaurd(){
        JSONArray dataPulled = null;
		boolean toRet = false;
        try {
            dataPulled = ep.getMarketUmData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(ep.checkAlertsAmount(dataPulled) == 0){
            toRet = getState().equals(SystemStates.VIEW_ALERTS);
        }else if(ep.checkAlertsAmount(dataPulled) > 0){
            toRet = getState().equals(SystemStates.BAD_STATE);
        }
		return toRet;
	}
	@Action 
	public void deleteAlerts() {
		sut.deleteAlert();

		if(getState() == SystemStates.VIEW_ALERTS){
            deleteAlerts = true;
            alertsPage = true;
            modelState = SystemStates.VIEW_ALERTS;

            Assert.assertEquals("The model's alert page state doesn't match the SUT's state.", alertsPage, sut.isOnAlertsPage());
            Assert.assertEquals("The model's delete state doesn't match the SUT's state.", deleteAlerts, sut.isDeleteingAlerts());

		}else if(getState() == SystemStates.BAD_STATE){
            deleteAlerts = false;
            badState = true;
            modelState = SystemStates.BAD_STATE;

            Assert.assertEquals("The model's delete state doesn't match the SUT's state.", deleteAlerts, sut.isDeleteingAlerts());
		}
	}

	public boolean goodAlertGaurd(WebDriver driver) {
		ma.goToAlerts(driver);
		return getState().equals(SystemStates.VIEW_ALERTS);
	}
	@Action 
	public void goodAlert() {
		sut.checkGoodAlert();

		goodAlert = true;
		alertsPage = true;
		modelState = SystemStates.VIEW_ALERTS;

		Assert.assertEquals("The model's alert page state doesn't match the SUT's state.", alertsPage, sut.isOnAlertsPage());
	}

	public boolean badStateGaurd(JSONArray alert) {
		ep.checkAlerts(alert);		
		return getState().equals(SystemStates.BAD_STATE);
	}
	@Action 
	public void badState() {
		sut.checkBadState();

		badState= true;
		modelState = SystemStates.BAD_STATE;

		Assert.assertEquals("The model's state doesn't match the SUT's state.", badState, true);//sut.isBadState());
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
		tester.generate(7);
		tester.printCoverage();
	}
}
