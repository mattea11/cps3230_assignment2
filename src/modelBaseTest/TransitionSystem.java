//package modelBaseTest;
//
//import java.io.IOException;
//
//import main.EndPoints;
//import main.MarketUMAuto;
//
//import org.json.JSONArray;
//import org.openqa.selenium.WebDriver;
//
//public class TransitionSystem {
//	private boolean logged = false, alertsPage = false; 
//	private boolean postAlert = false, checkAlert = false, deleteAlerts = false;
//	WebDriver driver = null;
//	MarketUMAuto ma = new MarketUMAuto();
//	EndPoints ep = new EndPoints();
//	
//	boolean isLogged(){
//		return logged;
//	}
//	
//	boolean isOnAlertsPage(){
//		return alertsPage;
//	}
//	
//	boolean isPostingAlert(){
//		return postAlert;
//	}
//	
//	boolean isCheckingAlerts(){
//		return checkAlert;
//	}
//	
//	boolean isDeleteingAlerts(){
//		return deleteAlerts;
//	}
//	
//	void validLogIn(WebDriver driver){
//		if(!logged && !alertsPage){
//			ma.accessMarketUm(driver);
//			ma.goToLogInInvalid(driver);
//			logged = true;
//		}
//	}
//	
//	void invalidLogIn(WebDriver driver){
//		if(!logged && !alertsPage){
//			ma.accessMarketUm(driver);
//			ma.goToLogInInvalid(driver);
//			logged = false;
//		}
//	}
//
//	void goToLogOut(WebDriver driver){
//		if(logged){
//			ma.goToLogOut(driver);
//			alertsPage = false;
//			logged = false;
//		}
//	}
//		
//	void goToAlerts(WebDriver driver){
//		if(logged){
//			ma.goToAlerts(driver);
//			alertsPage = true;
//		}
//	}
//	
//	void postAlert(String alert) throws IOException{
//		if(logged && alertsPage && !checkAlert && !deleteAlerts){
//			ep.postAlert(alert);
//			postAlert = true;
//		}
//	}
//
//	void checkAlert(String alert) throws IOException{
//		if(logged && alertsPage && !checkAlert && !deleteAlerts){
//			ep.checkAlert(alert);
//			checkAlert = true;
//		}
//	}
//	
//	void deleteAlert() throws IOException{
//		if(logged && alertsPage && !checkAlert){
//			ep.deleteAlerts();
//			deleteAlerts = true;
//		}
//	}
//}
