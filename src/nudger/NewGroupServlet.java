package nudger;

import java.io.IOException;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings("serial")
public class NewGroupServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("hello world!");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(req.getReader().readLine());
		
		String email = json.get("email").getAsString();
		
		User currentUser;
		
		resp.setContentType("application/json");
		
		try {
			currentUser = User.getUser(email);
			currentUser.addGroup(json.get("name").getAsString());
			
			//for each user, add them to the group too
			
			JsonObject result = new JsonObject();
			
			result.addProperty("success", 1);
			
			
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