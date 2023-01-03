package modelBaseTest;

public class TransitionSystem {
	private boolean logged = false, alertsPage = false; 
	private boolean postAlert = false, checkAlert = false, deleteAlerts = false, goodAlert = false, badState = false;
	
	boolean isLogged(){
		return logged;
	}
	
	boolean isOnAlertsPage(){
		return alertsPage;
	}
	
	boolean isPostingAlert(){
		return postAlert;
	}
	
	boolean isCheckingAlerts(){
		return checkAlert;
	}
	
	boolean isDeleteingAlerts(){
		return deleteAlerts;
	}

    boolean isGoodAlert(){
		return goodAlert;
	}

    boolean isBadState(){
		return badState;
	}
	
	void validLogIn(){
		if(!logged && !alertsPage){
			logged = true;
		}
	}
	
	void invalidLogIn(){
		if(!logged && !alertsPage){
			// ma.accessMarketUm(driver);
			// ma.goToLogInInvalid(driver);
			logged = false;
		}
	}

	void goToLogOut(){
		if(logged){
			alertsPage = false;
			logged = false;
		}
	}
		
	void goToAlerts(){
		if(logged){
			alertsPage = true;
		}
	}
	
	void postAlert(){
		if(logged && alertsPage && !checkAlert && !deleteAlerts){
			postAlert = true;
		}
	}

	void checkGoodAlert(){
		if(logged && alertsPage && checkAlert && !deleteAlerts){
			goodAlert = true;
		}
	}
	
    void checkBadState(){
		if(logged && alertsPage && !checkAlert){
			badState = true;
		}
	}

	void deleteAlert(){
		if(logged && alertsPage && !postAlert && !checkAlert){
			deleteAlerts = true;
		}
	}
}
