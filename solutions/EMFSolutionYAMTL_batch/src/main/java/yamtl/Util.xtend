package yamtl

import SocialNetwork.Submission
import java.util.ArrayList
import java.util.List

class Util {
	def static getBestThree(List<Pair<Submission,Integer>> weightedList, List<Submission> completionList) {
		val list = new ArrayList<Submission>(weightedList.map[key])
		if (list.size < 3) {
			val numberOfMissing = 3 - list.length
			completionList.sortInplace([c1,c2| - c1.timestamp.compareTo(c2.timestamp)])
			list.addAll(completionList.take(numberOfMissing))
		}
		list
	}
	
	// start with last
	def static void addIfIsThreeBest(List<Pair<Submission,Integer>> list, Submission submission, int score) {
		addIfIsThreeBest(list, submission, score, list.size-1)
	}
	
	def static private void addIfIsThreeBest(List<Pair<Submission,Integer>> list, Submission submission, int score, int index) {
		if (index == -1) {
			list.add(0, submission -> score)
			if (list.size > 3) 
				list.remove(list.last)
		} else {
			val element = list.get(index)
			if (element.key==submission) {
				list.remove(index)
				addIfIsThreeBest(list, submission, score, index-1)
			} else {
				if (score == element.value) {
					if (submission.timestamp.after(element.key.timestamp)) {
						// advance a position to check if there is another 
						// submission with the same value and smaller timestamp
						if ((index==0) || (index>=1 && list.get(index-1).value == score))
							addIfIsThreeBest(list, submission, score, index-1)
						else
							list.add(index, submission -> score)
							if (list.size > 3) 
								list.remove(list.last)
					} else {
						if (index<2) {
							list.add(index+1, submission -> score)
							if (list.size > 3) 
								list.remove(list.last)
						}
					}
				} else if (score > element.value) {
					// advance a position to check if there is another 
					// submission with a smaller value					
					addIfIsThreeBest(list, submission, score, index-1)
				} else {
					if (index<2) {
						list.add(index+1, submission -> score)
						if (list.size > 3) 
							list.remove(list.last)
					}
				}
				
			}
		}
	}

	def static sum(Iterable<Integer> list) {
		list.reduce[v1, v2 | v1+v2]
	}
}