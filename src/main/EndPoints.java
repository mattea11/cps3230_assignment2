package main;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EndPoints {
    public JSONArray getMarketUmData() throws IOException{
    	
    	String inline = "";
    	StringBuilder responseContent = new StringBuilder();
        JSONArray jsonArr;
        BufferedReader rd;
        String respString;
        
        URL market_alert_um_url = new URL("https://api.marketalertum.com/EventsLog/7f84a00a-eeac-47fa-b15c-ee7e7ff9378d");
        HttpURLConnection connect = (HttpURLConnection) market_alert_um_url.openConnection(); 
        
        connect.setRequestMethod("GET");
        connect.setRequestProperty("Content-Type", "application/json");
        connect.setRequestProperty("Accept", "application/json");
        connect.setDoOutput(true);
        
        System.out.println("Get resp:\t" + connect.getResponseCode());
        
        try{
			
			if (connect.getResponseCode() >= 300) {
				rd = new BufferedReader(new InputStreamReader(connect.getErrorStream()));
				while ((inline = rd.readLine()) != null) {
					responseContent.append(inline);
				}
				rd.close();
			}
			else {
				rd = new BufferedReader(new InputStreamReader(connect.getInputStream()));
				while ((inline = rd.readLine()) != null) {
					responseContent.append(inline);
				}
				rd.close();
			}
		}
        catch (MalformedURLException e) {
			e.printStackTrace();
		}
        catch (IOException e) {
			e.printStackTrace();
		}

        respString = responseContent.toString();
        jsonArr= new JSONArray(respString);
        
    	return jsonArr;
    } 

    public boolean postAlert(String alert) throws IOException{
        URL market_alert_um = new URL("https://api.marketalertum.com/Alert");
        HttpURLConnection connect = (HttpURLConnection)market_alert_um.openConnection(); 

        connect.setRequestMethod("POST");
        connect.setRequestProperty("Content-Type", "application/json");
        connect.setRequestProperty("Accept", "application/json");
        connect.setDoOutput(true);

        try(OutputStream os = connect.getOutputStream()) {
            byte[] input = alert.getBytes("utf-8");
            os.write(input, 0, input.length);			
        }

        try(BufferedReader br = new BufferedReader(
        new InputStreamReader(connect.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }
        System.out.println("Post resp:\t" + connect.getResponseCode() + "\n");

        if(connect.getResponseCode() == 201){ // boolean return used for testing
            return true;
        }
        return false;
    }   

    public boolean deleteAlerts() throws IOException{
        URL market_alert_um = new URL("https://api.marketalertum.com/Alert?userId=7f84a00a-eeac-47fa-b15c-ee7e7ff9378d");
        HttpURLConnection connect = (HttpURLConnection)market_alert_um.openConnection(); 

        connect.setRequestMethod("DELETE");
        connect.setRequestProperty("Content-Type", "application/json");
        connect.setRequestProperty("Accept", "application/json");
        connect.setDoOutput(true);

        System.out.println("Delete resp:\t" + connect.getResponseCode() + "\n");

        if(connect.getResponseCode() == 201){ // boolean return used for testing
            return true;
        }
        return false;
    } 

	public int getState(JSONArray data){
		int state = -1;

		for (int i = 0 ; i < data.length(); i++) {
			
			JSONObject clean = data.getJSONObject(i);	
			int systState = clean.getInt("eventLogType");
			
			state = systState;
			System.out.println("state:\t" + systState);
		}
		return state;
	}
    
    public boolean checkAlerts(JSONArray data){
    	boolean valid = false;

		for (int i = 0 ; i < data.length(); i++) {
			
			JSONObject clean = data.getJSONObject(i);	
			JSONObject systState = clean.getJSONObject("systemState");
			JSONArray alerts = systState.getJSONArray("alerts");
			
			System.out.println("alerts pulled:\t" + alerts);
			
			if(alerts.length() == 0){
				valid =  false;
			}
			else{
				JSONObject alertInfo = alerts.getJSONObject(0);
				
				int alertType = alertInfo.getInt("alertType");
				String heading = alertInfo.getString("description");
				String description = alertInfo.getString("description");
				String url = alertInfo.getString("url");
				String imageUrl= alertInfo.getString("imageURL");
				String postedBy = alertInfo.getString("postedBy");
				int priceInCents = alertInfo.getInt("priceInCents");
				String postDate = alertInfo.getString("postDate");
				
				if(alertType >= 7|| heading == "" || description == "" || url == "" || 
				imageUrl == "" || postedBy == "" || priceInCents == 0 || postDate == ""){
					valid =  false;
				}
				else{
					valid = true;
				}
			}
		}
		return valid;
    }
    
    public int checkAlertsAmount(JSONArray data){
    	int amount = 0;

		// if(checkAlerts(data)){
			for (int i = 0 ; i < data.length(); i++) {
			
				JSONObject clean = data.getJSONObject(i);	
				JSONObject systState = clean.getJSONObject("systemState");
				JSONArray alerts = systState.getJSONArray("alerts");
				
				System.out.println("alerts no:\t" + alerts);
				
				if(alerts.length() == 0){
					amount = 0;
				}else{
					amount += 1;
				}
			}
		// }
    	return amount;
    }
}
