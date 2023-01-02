 package modelBaseTest;

import java.io.FileNotFoundException;
import java.util.Random;
import org.junit.Test;
import junit.framework.Assert;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GraphListener;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.Tester;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;


@SuppressWarnings("deprecation")
public class SystemModelTest implements FsmModel{
	private SystemStates modelState;
	private TransitionSystem sut;
	private boolean logged = false, alertsPage = false, homePage = false; 
	private boolean postAlert = false, checkAlert = false, deleteAlerts = false;
	
	public SystemStates getState() {
		return modelState;
	}
	
	public void reset(final boolean b) {
		modelState = SystemStates.NOT_LOGGED;
		logged = false; 
		alertsPage = false; 
		homePage = false; 
		postAlert = false;
		checkAlert = false;
		deleteAlerts = false;
		
		if (b) {
			sut = new TransitionSystem();
		}
	}
	
	@Test
	public void ModelRunner() throws FileNotFoundException {
		final Tester tester = new GreedyTester(new SystemModelTest());
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
