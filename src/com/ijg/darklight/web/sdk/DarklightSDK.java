package com.ijg.darklight.web.sdk;

import java.util.HashMap;

import org.json.simple.JSONObject;

import com.ijg.darklightnova.web.api.APIRequest;
import com.ijg.darklightnova.web.api.DarklightAPI;

public class DarklightSDK {
	String APIProtocol;
	String APIServer;
	
	int API_SESSION_ID;
	String API_SESSION_KEY;
	
	private DarklightAPI api;
	
	JSONObject lastRequestResponse;
	
	/*
	 * Empty constructor
	 * requires calling setAPIProtocol() and setAPIServer() at
	 * a later time, followed by createAPI(), if the API is to be used.
	 */
	public DarklightSDK() {}
	
	// API constructor: creates API without having to explicitly call createAPI()
	public DarklightSDK(String APIProtocol, String APIServer, int APISessionID) {
		this.APIProtocol = APIProtocol;
		this.APIServer = APIServer;
		API_SESSION_ID = APISessionID;
		createAPI();
	}
	
	
	private boolean APIReady() {
		if (APIProtocol != null && APIServer != null && API_SESSION_ID > 0) {
			return true;
		}
		return false;
	}
	
	public boolean createAPI() {
		/*
		 * Instead of explicitly checking APIReady(),
		 * if createAPI() returns false then you know
		 * either the APIProtocol or APIServer are not
		 * defined (!APIReady())
		 */
		if (APIReady()) {
			api = new DarklightAPI(APIProtocol, APIServer);
			return true;
		}
		return false;
	}
	
	public DarklightAPI api() {
		if (api == null) {
			createAPI();
		}
		return api;
	}
	
	public String getAPIProtocol() {
		return APIProtocol;
	}
	public void setAPIProtocol(String APIProtocol) {
		this.APIProtocol = APIProtocol;
	}
	public String getAPIServer() {
		return APIServer;
	}
	public void setAPIServer(String APIServer) {
		this.APIServer = APIServer;
	}
	public int getAPISessionID() {
		return API_SESSION_ID;
	}
	public void setAPISessionID(int id) {
		API_SESSION_ID = id;
	}
	
	public String toJSONString(HashMap<?, ?> map) {
		// Easier than importing JSONObject elsewhere
		return JSONObject.toJSONString(map);
	}
	
	
	/*
	 * Easy API access
	 * ===============================
	 */
	public boolean apiAuth(String name) {
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("name", name);
		
		APIRequest authRequest = api().individualSessionRequest(API_SESSION_ID, DarklightAPI.AUTH_ENDPOINT, 
				parameters);
		authRequest.send();
		
		lastRequestResponse = authRequest.getResponse();
		
		int statusCode = (int) authRequest.get("status_code");
		if (statusCode == 200 || statusCode == 201) {
			API_SESSION_KEY = (String) authRequest.get("sessionkey");
			return true;
		}
		
		return false;
	}
	
	public boolean apiUpdate(int score, HashMap<String, String> desc) {
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("sessionkey", API_SESSION_KEY);
		parameters.put("score", "" + score);
		parameters.put("desc", toJSONString(desc));
		
		APIRequest updateRequest = api().individualSessionRequest(API_SESSION_ID, DarklightAPI.UPDATE_ENDPOINT, 
				parameters);
		updateRequest.send();
		
		lastRequestResponse = updateRequest.getResponse();
		
		int statusCode = (int) updateRequest.get("status_code");
		if (statusCode == 200 || statusCode == 201) {
			return true;
		}
		
		return false;
	}
}
