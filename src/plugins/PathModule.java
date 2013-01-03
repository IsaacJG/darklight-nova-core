package plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.ijg.darklight.core.Issue;
import com.ijg.darklight.core.ScoreModule;
import com.ijg.darklight.core.settings.ConfigParser;

public class PathModule extends ScoreModule {

	private Issue fakePath = new Issue("Malicious PATH",
			"Path variable no longer contains malicious and false paths.");

	private String[] badPaths;
	
	public PathModule() {
		loadSettings();
		issues.add(fakePath);
	}

	private boolean isPathClean() {
		try {
			Process p;
			p = Runtime.getRuntime().exec("cmd.exe /c echo %PATH%");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String path = "";
			String line = "";
			while ((line = br.readLine()) != null) {
				path += line;
			}
			
			for (String badPath : badPaths) {
				if (path.contains(badPath)) {
					return false;
				}
			}

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	protected void loadSettings() {
		JSONObject moduleSettings = (JSONObject) ConfigParser.getConfig().get("PathModule");
		JSONArray rawBadPaths = (JSONArray) moduleSettings.get("bad paths");
		badPaths = new String[rawBadPaths.size()];
		System.out.println("PathModule loaded the following bad paths:");
		for (int i = 0; i < badPaths.length; ++i) {
			badPaths[i] = (String) rawBadPaths.get(i);
			System.out.println(badPaths[i]);
		}
	}
	
	@Override
	public ArrayList<Issue> check() {
		if (isPathClean()) {
			add(fakePath);
		} else {
			remove(fakePath);
		}
		return issues;
	}

}
