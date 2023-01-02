package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.openqa.selenium.WebDriver;

public class Runner {
	
	public static void noBadStatePaths(WebDriver driver, List<String> alerts, JSONArray dataPulled) throws IOException{

        EndPoints ep = new EndPoints();
        MarketUMAuto ma = new MarketUMAuto();
		
        // access website and log in
		driver = ma.accessMarketUm(driver);
	    driver = ma.goToLogInValid(driver, "7f84a00a-eeac-47fa-b15c-ee7e7ff9378d");

	    
	    // post some alerts
	    for (int i = 0; i < alerts.size(); i++) {
	    	ep.postAlert(alerts.get(i));
	    }

		// go to alerts page to view the alerts
		driver = ma.goToAlerts(driver);
	   
	  
		// delete all the alerts
		ep.deleteAlerts();
 
		// go to alerts page to view the alerts
		driver = ma.goToAlerts(driver);

		// use web point to pull data from the site 
		dataPulled = ep.getMarketUmData();
		System.out.println(dataPulled);
		System.out.println(ep.getState(dataPulled));
		// System.out.println(ep.checkAlerts(dataPulled));
		// System.out.println(ep.checkAlertsAmount(dataPulled));

		
	}
	
	public static void badStatePaths(WebDriver driver, List<String> alerts, JSONArray dataPulled){
		
	}
	
    public static void main(String[] args) throws Exception {

        WebDriver driver = null;
        List<String> alerts = new ArrayList<>();
        JSONArray dataPulled = null;
        
        alerts.add("{\"alertType\":6,\"heading\":\"\",\"description\":\"\",\"url\":\"https://www.scanmalta.com/shop/jbl-vibe-100-true-wireless-white-bluetooth-earbuds.html\",\"imageUrl\":\"https://www.scanmalta.com/shop/pub/media/catalog/product/uk-odyssey-g3-24g35tf-390833-lf24g35tfwuxxu-454502153_1.jpg\",\"postedBy\":\"7f84a00a-eeac-47fa-b15c-ee7e7ff9378d\",\"priceInCents\":4900,\"postDate\": \"2022-10-11T21:38:54.3080651Z\"}");
        alerts.add("{\"alertType\":6,\"heading\":\"laptop\",\"description\":\"JBL Vibe 100 True Wireless White Bluetooth Earbuds\",\"url\":\"https://www.scanmalta.com/shop/jbl-vibe-100-true-wireless-pink-bluetooth-earbuds.html\",\"imageUrl\":\"https://www.scanmalta.com/shop/pub/media/catalog/product/uk-odyssey-g3-24g35tf-390833-lf24g35tfwuxxu-454502153_1.jpg\",\"postedBy\":\"7f84a00a-eeac-47fa-b15c-ee7e7ff9378d\",\"priceInCents\":4900,\"postDate\": \"2022-10-11T21:38:54.3080651Z\"}");
        alerts.add("{\"alertType\":6,\"heading\":\"desk\",\"description\":\"JBL Vibe 100 True Wireless White Bluetooth Earbuds\",\"url\":\"https://www.scanmalta.com/shop/jbl-vibe-100-true-wireless-black-bluetooth-earbuds.html\",\"imageUrl\":\"https://www.scanmalta.com/shop/pub/media/catalog/product/uk-odyssey-g3-24g35tf-390833-lf24g35tfwuxxu-454502153_1.jpg\",\"postedBy\":\"7f84a00a-eeac-47fa-b15c-ee7e7ff9378d\",\"priceInCents\":4900,\"postDate\": \"2022-10-11T21:38:54.3080651Z\"}");


        noBadStatePaths(driver, alerts, dataPulled);

    }
} 