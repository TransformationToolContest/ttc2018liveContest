package yamtl;

import SocialNetwork.User
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import yamtl.utils.GraphComponents

public class FriendsComponents extends GraphComponents {

    @Accessors
	var private List<User> userList;

    new(List<User> list) {
    	super(list.size)
    	userList = list
    }
   
   	def static public computeComponents(List<User> list) {
   		val FriendsComponents fc = new FriendsComponents(list)
   		
   		for (var i=0; i<list.size; i++) {
   			if (i+1<list.size) {
   				for (var j=i+1; j<list.size; j++) {
   					if (fc.connected(i,j)) {
   						fc.union(i,j)
   					} 			
   				}
   			}
   		}
   		fc
   	}

	def public getSquaredComponentSizes() {
		this.parent.groupBy[it].values.map[size * size]
	}

    override public boolean connected(int p, int q) {
		userList.get(p).friends.contains(userList.get(q))
		||
		userList.get(q).friends.contains(userList.get(p))
    }
}

