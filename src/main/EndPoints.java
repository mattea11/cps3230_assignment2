package main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class EndPoints {

	public List<String> createJsonObject(List<itemInfo> items, int itemType) throws JSONException, UnsupportedEncodingException{ //String title, String description, String url, String imageUrl, String price, String date
        int act_price = 0;
        JSONObject json = new JSONObject();
        List<String> alert = new ArrayList<>();

        try {// ensures that the json object created will keep the elements in the order they are added
        Field changeMap = json.getClass().getDeclaredField("map");
        changeMap.setAccessible(true);
        changeMap.set(json, new LinkedHashMap<>());
        changeMap.setAccessible(false);
        } catch (IllegalAccessException | NoSuchFieldException e) {
        System.out.println(e.getMessage());
        }

        for (int i = 0; i < items.size(); i++) { //create a jsonobject for every item passed in

            if(itemType < 1 || itemType > 6 ||  items.get(i).image == "" || items.get(i).title == "" || items.get(i).description == "" || items.get(i).url == "" || items.get(i).price == ""){
                throw new IllegalArgumentException("None of the item information should be null");
            }else{

                if(items.get(i).price.length() == 1){ // ensures the price passed is always 4 digits long 
                    act_price = Integer.parseInt(items.get(i).price) * 1000;
                } else if(items.get(i).price.length() == 2){
                    act_price = Integer.parseInt(items.get(i).price) * 100;
                } else if(items.get(i).price.length() == 3){
                    act_price = Integer.parseInt(items.get(i).price) * 10;
                } else{
                    act_price = Integer.parseInt(items.get(i).price);
                }

                //create the json object
                json.put("alertType", itemType);
                json.put("heading", items.get(i).title);
                json.put("description", items.get(i).description);
                json.put("url", items.get(i).url);
                json.put("imageUrl", items.get(i).image);
                json.put("postedBy", "7f84a00a-eeac-47fa-b15c-ee7e7ff9378d");
                json.put("priceInCents", act_price);

                alert.add(json.toString());
                System.out.println("Alert created:\t" + json.toString() + "\n\n");
            }
        }
        return alert; // return a list of all the alerts generated
    }

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
