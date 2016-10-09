package nudger;

import java.io.IOException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings("serial")
public class AuthServlet extends HttpServlet {
	
	private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(req.getReader().readLine());
		
		String email = json.get("email").getAsString();
		String password = json.get("password").getAsString();
		
		resp.setContentType("application/json");
		
		/*if(password.equals("Marisa")) {
			resp.getWriter().write("{ \"success\": 1 }");
		} else {
			resp.getWriter().write("{ \"success\": 0 }");
		}*/
		
		Entity currentUser;
		Key userKey = KeyFactory.createKey("User", email);
		
		try {
			currentUser = datastore.get(userKey);
			if(password.equals(currentUser.getProperty("password"))) {
				resp.setStatus(200);
				resp.getWriter().write("{ \"success\": 1, \"name\": \"" + 
						currentUser.getProperty("name") + "\", \"email\": \"" + 
						currentUser.getProperty("email") + "\" }");
			} else {
				resp.setStatus(400);
				resp.getWriter().write("{ \"success\": 0 }");
			}
		} catch (EntityNotFoundException e) {
			resp.setStatus(400);
			resp.getWriter().write("{ \"success\": 0 }");
		}
		
	}
}
