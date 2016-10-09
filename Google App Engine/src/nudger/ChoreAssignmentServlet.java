package nudger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings("serial")
public class ChoreAssignmentServlet extends HttpServlet{
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(req.getReader().readLine());
		
		String email = json.get("email").getAsString();
		String name = json.get("name").getAsString();
		
		try {
			User currentUser = User.getUser(email);
			Group currentGroup = Group.getGroup(name);
			
			assignChore(currentUser, currentGroup);
			
			resp.setContentType("application/json");
			resp.setStatus(200);
			resp.getWriter().write("{ \"success\": 1 }");
			
		} catch(EntityNotFoundException e) {
			resp.setStatus(400);
			resp.getWriter().write("{ \"success\": 0 }");
		}
		
	}
	
	// Returns true if chore assigned, false if not
	private boolean assignChore(User user, Group group){
		// No chores in group
		ArrayList<String> chores = group.getChores();
		if(chores == null || chores.size() == 0){
			return false;
		}
		
		Random r = new Random();
		int n = r.nextInt(chores.size());
		user.addChore(chores.get(n));
		group.removeChore(chores.get(n));
		return true;
	}
}
