package modelBaseTest;

public class TransitionSystem {
	private boolean logged = false, alertsPage = false, logOut = false; 
	private boolean postAlert = false, checkAlert = false, deleteAlerts = false, goodAlert = false, badState = false;
	
	boolean isLogged(){
		return logged;
	}
	
	boolean isOnAlertsPage(){
		return alertsPage;
	}

	boolean isLoggingOut(){
		return logOut;
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
	
	void goToLogOut(){
		if(logged && logOut){
			alertsPage = false;
			logged = false;
		}
	}
		
	void goToAlerts(){
		if(logged && !logOut && deleteAlerts){
			alertsPage = true;
			deleteAlerts = true;
		}
		else if(logged && !logOut && postAlert){
			alertsPage = true;
			postAlert = true;
		}
	}
	
	void postAlert(){
		if(logged && alertsPage && !checkAlert && !deleteAlerts){
			postAlert = true;
			badState = true;
		}else if(logged && alertsPage && checkAlert && !deleteAlerts){
			postAlert = true;
			goodAlert = true;
		}
	}

	void deleteAlert(){
		if(logged && alertsPage && !postAlert && !checkAlert){
			deleteAlerts = true;
		}
	}

	void checkGoodAlert(){
		if(logged && alertsPage && checkAlert && !deleteAlerts){
			goodAlert = true;
		}
	}
	
    void checkBadState(){
		if(logged && !checkAlert || deleteAlerts){
			badState = true;
		}
	}
}
