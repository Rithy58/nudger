package nudger;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings("serial")
public class GroupServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JsonObject json = new JsonObject();
		
		ArrayList<String> array = new ArrayList<String>();
		
		array.add("test");
		array.add("more test");
		
		JsonArray jsonArray = new JsonArray();
		
		for(String s : array) {
			jsonArray.add(s);
		}
		
		json.add("array", jsonArray);
		
		resp.setContentType("text/plain");
		resp.getWriter().println(json);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(req.getReader().readLine());
		
		String email = json.get("email").getAsString();
		
		User currentUser;
		
		resp.setContentType("application/json");
		
		try {
			currentUser = User.getUser(email);
			ArrayList<String> userGroups = currentUser.getGroups();
			ArrayList<String> userPendingGroups = currentUser.getPendingGroups();
			
			if(userPendingGroups == null) {
				userPendingGroups = new ArrayList<String>();
			}
			
			JsonObject result = new JsonObject();
			
			result.addProperty("success", 1);
			
			result.addProperty("email", email);
			
			JsonArray jArray = new JsonArray();
			for(String s : userGroups) {
				jArray.add(s);
			}
			result.add("joined", jArray);
			
			jArray = new JsonArray();
			for(String s : userPendingGroups) {
				jArray.add(s);
			}
			result.add("pending", jArray);
			
			resp.getWriter().write(result.toString());
			
			/*resp.getWriter().write("{ \"success\": 1, \"email\"" +
					email + "\"Joined:\" " + userGroups + 
					"\"Pending:\" " + userPendingGroups);*/
			
		} catch (EntityNotFoundException e) {
			resp.setStatus(400);
			resp.getWriter().write("{ \"success\": 0 }");
		}
		
	}
}