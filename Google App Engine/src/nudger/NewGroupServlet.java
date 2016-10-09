package nudger;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.gson.JsonArray;
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
		String name = json.get("name").getAsString();
		
		ArrayList<String> members = new ArrayList<>();
		
		members.add(email);
		
		Group newGroup;
		
		try {
			newGroup = Group.getGroup(name);
			newGroup.addMember(email);
		} catch(EntityNotFoundException e) {
			newGroup = Group.createNewGroup(name, members);
		}
		
		User currentUser;
		
		resp.setContentType("application/json");
		
		try {
			currentUser = User.getUser(email);
			currentUser.addGroup(json.get("name").getAsString());
			
			//for each user, add them to the group too
			
			JsonObject result = new JsonObject();
			
			result.addProperty("success", 1);
			result.addProperty("name", name);
			
			ArrayList<String> people = newGroup.getMembers();
			JsonArray jArray = new JsonArray();
			for(String s : people) {
				jArray.add(s);
			}
			result.add("members", jArray);
			
			jArray = new JsonArray();
			ArrayList<String> chores = newGroup.getChores();
			if(chores == null) {
				chores = new ArrayList<String>();
			}
			for(String s : chores) {
				jArray.add(s);
			}
			result.add("chores", jArray);
			
			resp.setStatus(200);
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