package ttc2018.yamtl

import SocialNetwork.User
import java.util.List
import java.util.Map
import org.eclipse.xtend.lib.annotations.Accessors

class FriendComponentUtil_UF {
	@Accessors
	var WeightedQuickUnionUF uf;
	val Map<User,Integer> userIndex = newHashMap
	
	// computes connected components
	new(List<User> userList) {
		uf = new WeightedQuickUnionUF(userList.size)
		var i = 0
		for (u: userList) {
			userIndex.put(u,i++)
		}
		updateComponents(userList)
	} 
	
	def updateComponents(List<User> userList) {
		for (i_u1: 0..userList.size-1) {
			val u1 = userList.get(i_u1)
			for (u2: u1.friends) { 
				val i_u2 = userIndex.get(u2)
				if (i_u2 !== null) {
					uf.union(i_u1, i_u2)
				}
			}
		}
	}

	def addLikedBy(User u) {
		var i_u1 = userIndex.get(u)
		if (i_u1 === null) {
			i_u1 = uf.addElement()
			userIndex.put(u,i_u1)
		}
		for (friend: u.friends) {
			val i_u2 = userIndex.get(friend)
			if (i_u2 !== null) {
				uf.union(i_u1,i_u2)
			}
		}
	}
	
	def addFriendship(User u1, User u2) {
		val i_u1 = userIndex.get(u1)
		var i_u2 = userIndex.get(u2)
		if (i_u2 !== null) {
			uf.union(i_u1,i_u2)
		}
	}
	
	def score() {
		var int score = 0
		for (i: 0..uf.parentArray.length-1) {
			if (i==uf.parentArray.get(i)) {
				score += uf.sizeArray.get(i)*uf.sizeArray.get(i)
			}
		}
		score
	}	
}