package yamtl


import SocialNetwork.User
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.xtend.lib.annotations.Accessors

class FriendComponentUtil {
	// to know which users liked the comment
	Set<User> userSet
	// maps a user to its component
	Map<User,Set<User>> userToComponent = newHashMap
	// set of resulting components
	@Accessors
	Set<Set<User>> components = newHashSet
	
	// computes connected components
	new(List<User> userList) {
		// to know which users liked the comment
		userSet = userList.toSet
		
		for (u : userList) {
			var comp = userToComponent.get(u)
			if (comp === null) { // not visited
				comp = newHashSet(u)
				components.add(comp)
				userToComponent.put(u, comp)
				u.computeComponent(comp)
			}
		}
	} 
	
	// explores friends recursively until all the relevant ones are visited
	def private void computeComponent(User u, Set<User> comp) {
//		// keep those users who liked the comment
		for (f: u.friends) {
			if (userSet.contains(f)) {
				val isAdded = comp.add(f)
				if (isAdded) { // not visited
					userToComponent.put(f, comp)
					f.computeComponent(comp)
				}
			}
		}
	}	
}