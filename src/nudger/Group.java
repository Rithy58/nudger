package nudger;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class Group {
	
	private Entity group;
	
	public Group(Entity g) {
		group = g;
	}

	public static Group createNewGroup(String name, ArrayList<String> members) {
		Entity newGroupEntity = new Entity("Group", name);
		newGroupEntity.setProperty("name", name);
		newGroupEntity.setProperty("members", new ArrayList<String>());
		newGroupEntity.setProperty("chores", new ArrayList<String>());
		return new Group(newGroupEntity);
	}
	
	public static Group getGroup(String name) throws EntityNotFoundException {
		Key groupKey = KeyFactory.createKey("Group", name);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		return new Group(datastore.get(groupKey));
	}
	
	public String getName() {
		return (String) group.getProperty("name");
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getMembers() {
		return (ArrayList<String>) group.getProperty("members");
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getChores() {
		return (ArrayList<String>) group.getProperty("chores");
	}
}
