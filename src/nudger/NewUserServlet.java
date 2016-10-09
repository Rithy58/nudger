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
public class NewUserServlet extends HttpServlet {
	
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
		String name = json.get("name").getAsString();
		
		Key userKey = KeyFactory.createKey("User", email);
		
		try {
			datastore.get(userKey);
			
			resp.setContentType("application/json");
			resp.setStatus(400);
			resp.getWriter().write("{ \"success\": 0 }");
			
		} catch(EntityNotFoundException e) {
			Entity newUser = new Entity("User", email);
			
			newUser.setProperty("email", email);
			newUser.setProperty("password", password);
			newUser.setProperty("name", name);
			
			datastore.put(newUser);
			
			resp.setContentType("application/json");
			resp.setStatus(200);
			resp.getWriter().write("{ \"success\": 1, \"name\": \"" + 
					newUser.getProperty("name") + "\", \"email\": \"" + 
					newUser.getProperty("email") + "\" }");
		}
	}
}