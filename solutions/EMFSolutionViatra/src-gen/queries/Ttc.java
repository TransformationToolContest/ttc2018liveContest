/**
 * Generated from platform:/resource/EMFSolutionViatra/src/queries/ttc.vql
 */
package queries;

import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.impl.BaseGeneratedPatternGroup;
import queries.CommentOnPost;
import queries.LikesOnComments;
import queries.MutuallyLikedComment;
import queries.Task1;
import queries.Task2;
import queries.TransitiveFriendLikers;

/**
 * A pattern group formed of all public patterns defined in ttc.vql.
 * 
 * <p>Use the static instance as any {@link interface org.eclipse.viatra.query.runtime.api.IQueryGroup}, to conveniently prepare
 * a VIATRA Query engine for matching all patterns originally defined in file ttc.vql,
 * in order to achieve better performance than one-by-one on-demand matcher initialization.
 * 
 * <p> From package queries, the group contains the definition of the following patterns: <ul>
 * <li>task1</li>
 * <li>likesOnComments</li>
 * <li>commentOnPost</li>
 * <li>task2</li>
 * <li>transitiveFriendLikers</li>
 * <li>mutuallyLikedComment</li>
 * </ul>
 * 
 * @see IQueryGroup
 * 
 */
@SuppressWarnings("all")
public final class Ttc extends BaseGeneratedPatternGroup {
  /**
   * Access the pattern group.
   * 
   * @return the singleton instance of the group
   * @throws ViatraQueryRuntimeException if there was an error loading the generated code of pattern specifications
   * 
   */
  public static Ttc instance() {
    if (INSTANCE == null) {
        INSTANCE = new Ttc();
    }
    return INSTANCE;
  }
  
  private static Ttc INSTANCE;
  
  private Ttc() {
    querySpecifications.add(Task1.instance());
    querySpecifications.add(LikesOnComments.instance());
    querySpecifications.add(CommentOnPost.instance());
    querySpecifications.add(Task2.instance());
    querySpecifications.add(TransitiveFriendLikers.instance());
    querySpecifications.add(MutuallyLikedComment.instance());
  }
  
  public Task1 getTask1() {
    return Task1.instance();
  }
  
  public Task1.Matcher getTask1(final ViatraQueryEngine engine) {
    return Task1.Matcher.on(engine);
  }
  
  public LikesOnComments getLikesOnComments() {
    return LikesOnComments.instance();
  }
  
  public LikesOnComments.Matcher getLikesOnComments(final ViatraQueryEngine engine) {
    return LikesOnComments.Matcher.on(engine);
  }
  
  public CommentOnPost getCommentOnPost() {
    return CommentOnPost.instance();
  }
  
  public CommentOnPost.Matcher getCommentOnPost(final ViatraQueryEngine engine) {
    return CommentOnPost.Matcher.on(engine);
  }
  
  public Task2 getTask2() {
    return Task2.instance();
  }
  
  public Task2.Matcher getTask2(final ViatraQueryEngine engine) {
    return Task2.Matcher.on(engine);
  }
  
  public TransitiveFriendLikers getTransitiveFriendLikers() {
    return TransitiveFriendLikers.instance();
  }
  
  public TransitiveFriendLikers.Matcher getTransitiveFriendLikers(final ViatraQueryEngine engine) {
    return TransitiveFriendLikers.Matcher.on(engine);
  }
  
  public MutuallyLikedComment getMutuallyLikedComment() {
    return MutuallyLikedComment.instance();
  }
  
  public MutuallyLikedComment.Matcher getMutuallyLikedComment(final ViatraQueryEngine engine) {
    return MutuallyLikedComment.Matcher.on(engine);
  }
}
