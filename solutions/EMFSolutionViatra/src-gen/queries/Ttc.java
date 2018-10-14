/**
 * Generated from platform:/resource/EMFSolutionViatra/src/queries/ttc.vql
 */
package queries;

import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.impl.BaseGeneratedPatternGroup;
import queries.CommentComponentSize;
import queries.Commented;
import queries.Likes;
import queries.MutuallyLikedComment;
import queries.Task1;
import queries.Task2;
import queries.TransitiveFriendLikers;
import queries.TransitivelyCommented;

/**
 * A pattern group formed of all public patterns defined in ttc.vql.
 * 
 * <p>Use the static instance as any {@link interface org.eclipse.viatra.query.runtime.api.IQueryGroup}, to conveniently prepare
 * a VIATRA Query engine for matching all patterns originally defined in file ttc.vql,
 * in order to achieve better performance than one-by-one on-demand matcher initialization.
 * 
 * <p> From package queries, the group contains the definition of the following patterns: <ul>
 * <li>task1</li>
 * <li>transitivelyCommented</li>
 * <li>commented</li>
 * <li>likes</li>
 * <li>task2</li>
 * <li>commentComponentSize</li>
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
    querySpecifications.add(TransitivelyCommented.instance());
    querySpecifications.add(Commented.instance());
    querySpecifications.add(Likes.instance());
    querySpecifications.add(Task2.instance());
    querySpecifications.add(CommentComponentSize.instance());
    querySpecifications.add(TransitiveFriendLikers.instance());
    querySpecifications.add(MutuallyLikedComment.instance());
  }
  
  public Task1 getTask1() {
    return Task1.instance();
  }
  
  public Task1.Matcher getTask1(final ViatraQueryEngine engine) {
    return Task1.Matcher.on(engine);
  }
  
  public TransitivelyCommented getTransitivelyCommented() {
    return TransitivelyCommented.instance();
  }
  
  public TransitivelyCommented.Matcher getTransitivelyCommented(final ViatraQueryEngine engine) {
    return TransitivelyCommented.Matcher.on(engine);
  }
  
  public Commented getCommented() {
    return Commented.instance();
  }
  
  public Commented.Matcher getCommented(final ViatraQueryEngine engine) {
    return Commented.Matcher.on(engine);
  }
  
  public Likes getLikes() {
    return Likes.instance();
  }
  
  public Likes.Matcher getLikes(final ViatraQueryEngine engine) {
    return Likes.Matcher.on(engine);
  }
  
  public Task2 getTask2() {
    return Task2.instance();
  }
  
  public Task2.Matcher getTask2(final ViatraQueryEngine engine) {
    return Task2.Matcher.on(engine);
  }
  
  public CommentComponentSize getCommentComponentSize() {
    return CommentComponentSize.instance();
  }
  
  public CommentComponentSize.Matcher getCommentComponentSize(final ViatraQueryEngine engine) {
    return CommentComponentSize.Matcher.on(engine);
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
