package ttc2018;

import Changes.ModelChangeSet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

public class SolutionQ2 extends Solution {

	public SolutionQ2(String DataPath) throws IOException, InterruptedException {
		super(DataPath);

		Connection conn = getDbConnection();
		Query.Q2_CF_INIT.prepareStatement(conn);
		Query.Q2_CF_MAINTAIN.prepareStatement(conn);
		Query.Q2_CFC_INIT.prepareStatement(conn);
		Query.Q2_CFC_MAINTAIN.prepareStatement(conn);
		Query.Q2_RETRIEVE.prepareStatement(conn);
		Query.Q2_INFO_COUNT_COMMENT_FRIENDS_D.prepareStatement(conn);
		Query.Q2_INFO_COUNT_LIKES_D.prepareStatement(conn);
	}

	@Override
	public String Initial() {
		runVoidQuery(Query.Q2_CF_INIT);
		runVoidQuery(Query.Q2_CFC_INIT);
		String result = runReadQuery(Query.Q2_RETRIEVE);

		return result;
	}

	@Override
	public String Update(ModelChangeSet changes) {
		beforeUpdate(changes);

		runVoidQuery(Query.Q2_CF_MAINTAIN);
		runVoidQuery(Query.Q2_CFC_MAINTAIN);
		String result = runReadQuery(Query.Q2_RETRIEVE);

		afterUpdate();

		return result;
	}

	@Override
	public String Update(File changes) {
		long stopwatch = System.nanoTime();
		beforeUpdate(changes);

		long s1 = (System.nanoTime()-stopwatch)/1000;
		stopwatch=System.nanoTime();

		runVoidQuery(Query.Q2_CF_MAINTAIN);

		long s2 = (System.nanoTime()-stopwatch)/1000;

		int cntLikesDiff = runSingleIntReadQuery(Query.Q2_INFO_COUNT_LIKES_D);
		int cntCommentFriendsDiff = runSingleIntReadQuery(Query.Q2_INFO_COUNT_COMMENT_FRIENDS_D);
		if (LiveContestDriver.ShowDebugInfo) {
			System.err.println(
					String.format("Num of diff records: Likes_d: %d, Comment_friends_d: %d"
							, cntLikesDiff, cntCommentFriendsDiff
					)
			);
		}

		stopwatch=System.nanoTime();

		//System.exit(0);

		// if the diff set for both likes and comment_friends are empty, the closure of the comment friends graph
		// does not change, so we skip running its maintenance query
		if (cntCommentFriendsDiff > 0 || cntLikesDiff > 0) {
			runVoidQuery(Query.Q2_CFC_MAINTAIN);
		}

		long s3 = (System.nanoTime()-stopwatch)/1000;
		stopwatch=System.nanoTime();

		String result = runReadQuery(Query.Q2_RETRIEVE);

		long s4 = (System.nanoTime()-stopwatch)/1000;
		stopwatch=System.nanoTime();


		afterUpdate();


		long s5 = (System.nanoTime()-stopwatch)/1000;
		stopwatch=System.nanoTime();

		if (LiveContestDriver.ShowDebugInfo) {
			System.err.println(String.format("Q2 update: %d %d %d %d %d", s1, s2, s3, s4, s5));
		}

		return result;
	}
}
