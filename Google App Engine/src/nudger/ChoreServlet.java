package nudger;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings("serial")
public class ChoreServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(req.getReader().readLine());
		
		String email = json.get("email").getAsString();
		
		resp.setContentType("application/json");
		
		try {
			User currentUser = User.getUser(email);
			
			ArrayList<String> chores = currentUser.getChores();
			
			JsonObject result = new JsonObject();
			
			result.addProperty("success", 1);
			JsonArray jArray = new JsonArray();
			for(String s : chores) {
				jArray.add(s);
			}
			result.add("chores", jArray);
			resp.getWriter().write(result.toString());
			
		} catch(EntityNotFoundException e) {
			resp.setStatus(400);
			resp.getWriter().write("{ \"success\": 0 }");
		}
	}
}
