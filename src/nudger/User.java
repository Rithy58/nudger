package nudger;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class User {
	
	private Entity user;
	
	public static User createNewUser(String name, String email, String password) {
		Entity newUserEntity = new Entity("User", email);
		newUserEntity.setProperty("name", name);
		newUserEntity.setProperty("email", email);
		newUserEntity.setProperty("password", password);
		newUserEntity.setProperty("chores", new ArrayList<String>());
		newUserEntity.setProperty("groups", new ArrayList<String>());
		newUserEntity.setProperty("pendingGroups", new ArrayList<String>());
		return new User(newUserEntity);
	}
	
	public static User getUser(String email) throws EntityNotFoundException {
		Key userKey = KeyFactory.createKey("User", email);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		return new User(datastore.get(userKey));
	}

	public User(Entity e) {
		user = e;
	}
	
	public String getName() {
		return (String) user.getProperty("name");
	}
	
	public String getEmail() {
		return (String) user.getProperty("email");
	}
	
	public String getPassword() {
		return (String) user.getProperty("password");
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getChores() {
		return (ArrayList<String>) user.getProperty("chores");
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getGroups() {
		return (ArrayList<String>) user.getProperty("groups");
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getPendingGroups() {
		return (ArrayList<String>) user.getProperty("pendingGroups");
	}
	
	public void setName(String name) {
		user.setProperty("name", name);
	}
	
	public void setEmail(String email) {
		user.setProperty("email", email);
	}
	
	public void setPassword(String password) {
		user.setProperty("password", password);
	}
	
	public void addChore(String chore) {
		ArrayList<String> newChores = this.getChores();
		newChores.add(chore);
		user.setProperty("chores", newChores);
	}
	
	public void addGroup(String group) {
		ArrayList<String> newGroups = this.getGroups();
		newGroups.add(group);
		user.setProperty("groups", newGroups);
	}
	
	public void addPendingGroup(String pendingGroup) {
		ArrayList<String> newPendingGroup = this.getPendingGroups();
		newPendingGroup.add(pendingGroup);
		user.setProperty("pendingGroups", newPendingGroup);
	}
}
