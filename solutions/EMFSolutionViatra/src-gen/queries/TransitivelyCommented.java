/**
 * Generated from platform:/resource/EMFSolutionViatra/src/queries/ttc.vql
 */
package queries;

import SocialNetwork.Comment;
import SocialNetwork.Post;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import org.eclipse.viatra.query.runtime.api.IQuerySpecification;
import org.eclipse.viatra.query.runtime.api.ViatraQueryEngine;
import org.eclipse.viatra.query.runtime.api.impl.BaseGeneratedEMFPQuery;
import org.eclipse.viatra.query.runtime.api.impl.BaseGeneratedEMFQuerySpecification;
import org.eclipse.viatra.query.runtime.api.impl.BaseMatcher;
import org.eclipse.viatra.query.runtime.api.impl.BasePatternMatch;
import org.eclipse.viatra.query.runtime.emf.types.EClassTransitiveInstancesKey;
import org.eclipse.viatra.query.runtime.matchers.backend.QueryEvaluationHint;
import org.eclipse.viatra.query.runtime.matchers.context.common.JavaTransitiveInstancesKey;
import org.eclipse.viatra.query.runtime.matchers.psystem.PBody;
import org.eclipse.viatra.query.runtime.matchers.psystem.PVariable;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.Equality;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.ExportedParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.PatternMatchCounter;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.TypeFilterConstraint;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicenumerables.BinaryTransitiveClosure;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicenumerables.TypeConstraint;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameterDirection;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PVisibility;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuple;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuples;
import org.eclipse.viatra.query.runtime.util.ViatraQueryLoggingUtil;
import queries.Commented;
import queries.Likes;

/**
 * A pattern-specific query specification that can instantiate Matcher in a type-safe way.
 * 
 * <p>Original source:
 *         <code><pre>
 *         pattern transitivelyCommented(post: Post, comment: Comment, commentLikeCount: java Integer) {
 *         	find commented+(post, comment);
 *         	commentLikeCount == count find likes(_user, comment);
 *         }
 * </pre></code>
 * 
 * @see Matcher
 * @see Match
 * 
 */
@SuppressWarnings("all")
public final class TransitivelyCommented extends BaseGeneratedEMFQuerySpecification<TransitivelyCommented.Matcher> {
  /**
   * Pattern-specific match representation of the queries.transitivelyCommented pattern,
   * to be used in conjunction with {@link Matcher}.
   * 
   * <p>Class fields correspond to parameters of the pattern. Fields with value null are considered unassigned.
   * Each instance is a (possibly partial) substitution of pattern parameters,
   * usable to represent a match of the pattern in the result of a query,
   * or to specify the bound (fixed) input parameters when issuing a query.
   * 
   * @see Matcher
   * 
   */
  public static abstract class Match extends BasePatternMatch {
    private Post fPost;
    
    private Comment fComment;
    
    private Integer fCommentLikeCount;
    
    private static List<String> parameterNames = makeImmutableList("post", "comment", "commentLikeCount");
    
    private Match(final Post pPost, final Comment pComment, final Integer pCommentLikeCount) {
      this.fPost = pPost;
      this.fComment = pComment;
      this.fCommentLikeCount = pCommentLikeCount;
    }
    
    @Override
    public Object get(final String parameterName) {
      if ("post".equals(parameterName)) return this.fPost;
      if ("comment".equals(parameterName)) return this.fComment;
      if ("commentLikeCount".equals(parameterName)) return this.fCommentLikeCount;
      return null;
    }
    
    public Post getPost() {
      return this.fPost;
    }
    
    public Comment getComment() {
      return this.fComment;
    }
    
    public Integer getCommentLikeCount() {
      return this.fCommentLikeCount;
    }
    
    @Override
    public boolean set(final String parameterName, final Object newValue) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      if ("post".equals(parameterName) ) {
          this.fPost = (Post) newValue;
          return true;
      }
      if ("comment".equals(parameterName) ) {
          this.fComment = (Comment) newValue;
          return true;
      }
      if ("commentLikeCount".equals(parameterName) ) {
          this.fCommentLikeCount = (Integer) newValue;
          return true;
      }
      return false;
    }
    
    public void setPost(final Post pPost) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fPost = pPost;
    }
    
    public void setComment(final Comment pComment) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fComment = pComment;
    }
    
    public void setCommentLikeCount(final Integer pCommentLikeCount) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fCommentLikeCount = pCommentLikeCount;
    }
    
    @Override
    public String patternName() {
      return "queries.transitivelyCommented";
    }
    
    @Override
    public List<String> parameterNames() {
      return TransitivelyCommented.Match.parameterNames;
    }
    
    @Override
    public Object[] toArray() {
      return new Object[]{fPost, fComment, fCommentLikeCount};
    }
    
    @Override
    public TransitivelyCommented.Match toImmutable() {
      return isMutable() ? newMatch(fPost, fComment, fCommentLikeCount) : this;
    }
    
    @Override
    public String prettyPrint() {
      StringBuilder result = new StringBuilder();
      result.append("\"post\"=" + prettyPrintValue(fPost) + ", ");
      result.append("\"comment\"=" + prettyPrintValue(fComment) + ", ");
      result.append("\"commentLikeCount\"=" + prettyPrintValue(fCommentLikeCount));
      return result.toString();
    }
    
    @Override
    public int hashCode() {
      return Objects.hash(fPost, fComment, fCommentLikeCount);
    }
    
    @Override
    public boolean equals(final Object obj) {
      if (this == obj)
          return true;
      if (obj == null) {
          return false;
      }
      if ((obj instanceof TransitivelyCommented.Match)) {
          TransitivelyCommented.Match other = (TransitivelyCommented.Match) obj;
          return Objects.equals(fPost, other.fPost) && Objects.equals(fComment, other.fComment) && Objects.equals(fCommentLikeCount, other.fCommentLikeCount);
      } else {
          // this should be infrequent
          if (!(obj instanceof IPatternMatch)) {
              return false;
          }
          IPatternMatch otherSig  = (IPatternMatch) obj;
          return Objects.equals(specification(), otherSig.specification()) && Arrays.deepEquals(toArray(), otherSig.toArray());
      }
    }
    
    @Override
    public TransitivelyCommented specification() {
      return TransitivelyCommented.instance();
    }
    
    /**
     * Returns an empty, mutable match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @return the empty match.
     * 
     */
    public static TransitivelyCommented.Match newEmptyMatch() {
      return new Mutable(null, null, null);
    }
    
    /**
     * Returns a mutable (partial) match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pCommentLikeCount the fixed value of pattern parameter commentLikeCount, or null if not bound.
     * @return the new, mutable (partial) match object.
     * 
     */
    public static TransitivelyCommented.Match newMutableMatch(final Post pPost, final Comment pComment, final Integer pCommentLikeCount) {
      return new Mutable(pPost, pComment, pCommentLikeCount);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pCommentLikeCount the fixed value of pattern parameter commentLikeCount, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public static TransitivelyCommented.Match newMatch(final Post pPost, final Comment pComment, final Integer pCommentLikeCount) {
      return new Immutable(pPost, pComment, pCommentLikeCount);
    }
    
    private static final class Mutable extends TransitivelyCommented.Match {
      Mutable(final Post pPost, final Comment pComment, final Integer pCommentLikeCount) {
        super(pPost, pComment, pCommentLikeCount);
      }
      
      @Override
      public boolean isMutable() {
        return true;
      }
    }
    
    private static final class Immutable extends TransitivelyCommented.Match {
      Immutable(final Post pPost, final Comment pComment, final Integer pCommentLikeCount) {
        super(pPost, pComment, pCommentLikeCount);
      }
      
      @Override
      public boolean isMutable() {
        return false;
      }
    }
  }
  
  /**
   * Generated pattern matcher API of the queries.transitivelyCommented pattern,
   * providing pattern-specific query methods.
   * 
   * <p>Use the pattern matcher on a given model via {@link #on(ViatraQueryEngine)},
   * e.g. in conjunction with {@link ViatraQueryEngine#on(QueryScope)}.
   * 
   * <p>Matches of the pattern will be represented as {@link Match}.
   * 
   * <p>Original source:
   * <code><pre>
   * pattern transitivelyCommented(post: Post, comment: Comment, commentLikeCount: java Integer) {
   * 	find commented+(post, comment);
   * 	commentLikeCount == count find likes(_user, comment);
   * }
   * </pre></code>
   * 
   * @see Match
   * @see TransitivelyCommented
   * 
   */
  public static class Matcher extends BaseMatcher<TransitivelyCommented.Match> {
    /**
     * Initializes the pattern matcher within an existing VIATRA Query engine.
     * If the pattern matcher is already constructed in the engine, only a light-weight reference is returned.
     * 
     * @param engine the existing VIATRA Query engine in which this matcher will be created.
     * @throws ViatraQueryRuntimeException if an error occurs during pattern matcher creation
     * 
     */
    public static TransitivelyCommented.Matcher on(final ViatraQueryEngine engine) {
      // check if matcher already exists
      Matcher matcher = engine.getExistingMatcher(querySpecification());
      if (matcher == null) {
          matcher = (Matcher)engine.getMatcher(querySpecification());
      }
      return matcher;
    }
    
    /**
     * @throws ViatraQueryRuntimeException if an error occurs during pattern matcher creation
     * @return an initialized matcher
     * @noreference This method is for internal matcher initialization by the framework, do not call it manually.
     * 
     */
    public static TransitivelyCommented.Matcher create() {
      return new Matcher();
    }
    
    private final static int POSITION_POST = 0;
    
    private final static int POSITION_COMMENT = 1;
    
    private final static int POSITION_COMMENTLIKECOUNT = 2;
    
    private final static Logger LOGGER = ViatraQueryLoggingUtil.getLogger(TransitivelyCommented.Matcher.class);
    
    /**
     * Initializes the pattern matcher within an existing VIATRA Query engine.
     * If the pattern matcher is already constructed in the engine, only a light-weight reference is returned.
     * 
     * @param engine the existing VIATRA Query engine in which this matcher will be created.
     * @throws ViatraQueryRuntimeException if an error occurs during pattern matcher creation
     * 
     */
    private Matcher() {
      super(querySpecification());
    }
    
    /**
     * Returns the set of all matches of the pattern that conform to the given fixed values of some parameters.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pCommentLikeCount the fixed value of pattern parameter commentLikeCount, or null if not bound.
     * @return matches represented as a Match object.
     * 
     */
    public Collection<TransitivelyCommented.Match> getAllMatches(final Post pPost, final Comment pComment, final Integer pCommentLikeCount) {
      return rawStreamAllMatches(new Object[]{pPost, pComment, pCommentLikeCount}).collect(Collectors.toSet());
    }
    
    /**
     * Returns a stream of all matches of the pattern that conform to the given fixed values of some parameters.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pCommentLikeCount the fixed value of pattern parameter commentLikeCount, or null if not bound.
     * @return a stream of matches represented as a Match object.
     * 
     */
    public Stream<TransitivelyCommented.Match> streamAllMatches(final Post pPost, final Comment pComment, final Integer pCommentLikeCount) {
      return rawStreamAllMatches(new Object[]{pPost, pComment, pCommentLikeCount});
    }
    
    /**
     * Returns an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pCommentLikeCount the fixed value of pattern parameter commentLikeCount, or null if not bound.
     * @return a match represented as a Match object, or null if no match is found.
     * 
     */
    public Optional<TransitivelyCommented.Match> getOneArbitraryMatch(final Post pPost, final Comment pComment, final Integer pCommentLikeCount) {
      return rawGetOneArbitraryMatch(new Object[]{pPost, pComment, pCommentLikeCount});
    }
    
    /**
     * Indicates whether the given combination of specified pattern parameters constitute a valid pattern match,
     * under any possible substitution of the unspecified parameters (if any).
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pCommentLikeCount the fixed value of pattern parameter commentLikeCount, or null if not bound.
     * @return true if the input is a valid (partial) match of the pattern.
     * 
     */
    public boolean hasMatch(final Post pPost, final Comment pComment, final Integer pCommentLikeCount) {
      return rawHasMatch(new Object[]{pPost, pComment, pCommentLikeCount});
    }
    
    /**
     * Returns the number of all matches of the pattern that conform to the given fixed values of some parameters.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pCommentLikeCount the fixed value of pattern parameter commentLikeCount, or null if not bound.
     * @return the number of pattern matches found.
     * 
     */
    public int countMatches(final Post pPost, final Comment pComment, final Integer pCommentLikeCount) {
      return rawCountMatches(new Object[]{pPost, pComment, pCommentLikeCount});
    }
    
    /**
     * Executes the given processor on an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pCommentLikeCount the fixed value of pattern parameter commentLikeCount, or null if not bound.
     * @param processor the action that will process the selected match.
     * @return true if the pattern has at least one match with the given parameter values, false if the processor was not invoked
     * 
     */
    public boolean forOneArbitraryMatch(final Post pPost, final Comment pComment, final Integer pCommentLikeCount, final Consumer<? super TransitivelyCommented.Match> processor) {
      return rawForOneArbitraryMatch(new Object[]{pPost, pComment, pCommentLikeCount}, processor);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pCommentLikeCount the fixed value of pattern parameter commentLikeCount, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public TransitivelyCommented.Match newMatch(final Post pPost, final Comment pComment, final Integer pCommentLikeCount) {
      return TransitivelyCommented.Match.newMatch(pPost, pComment, pCommentLikeCount);
    }
    
    /**
     * Retrieve the set of values that occur in matches for post.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    protected Stream<Post> rawStreamAllValuesOfpost(final Object[] parameters) {
      return rawStreamAllValues(POSITION_POST, parameters).map(Post.class::cast);
    }
    
    /**
     * Retrieve the set of values that occur in matches for post.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Post> getAllValuesOfpost() {
      return rawStreamAllValuesOfpost(emptyArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for post.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Stream<Post> streamAllValuesOfpost() {
      return rawStreamAllValuesOfpost(emptyArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for post.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<Post> streamAllValuesOfpost(final TransitivelyCommented.Match partialMatch) {
      return rawStreamAllValuesOfpost(partialMatch.toArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for post.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<Post> streamAllValuesOfpost(final Comment pComment, final Integer pCommentLikeCount) {
      return rawStreamAllValuesOfpost(new Object[]{null, pComment, pCommentLikeCount});
    }
    
    /**
     * Retrieve the set of values that occur in matches for post.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Post> getAllValuesOfpost(final TransitivelyCommented.Match partialMatch) {
      return rawStreamAllValuesOfpost(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for post.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Post> getAllValuesOfpost(final Comment pComment, final Integer pCommentLikeCount) {
      return rawStreamAllValuesOfpost(new Object[]{null, pComment, pCommentLikeCount}).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    protected Stream<Comment> rawStreamAllValuesOfcomment(final Object[] parameters) {
      return rawStreamAllValues(POSITION_COMMENT, parameters).map(Comment.class::cast);
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Comment> getAllValuesOfcomment() {
      return rawStreamAllValuesOfcomment(emptyArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Stream<Comment> streamAllValuesOfcomment() {
      return rawStreamAllValuesOfcomment(emptyArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<Comment> streamAllValuesOfcomment(final TransitivelyCommented.Match partialMatch) {
      return rawStreamAllValuesOfcomment(partialMatch.toArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<Comment> streamAllValuesOfcomment(final Post pPost, final Integer pCommentLikeCount) {
      return rawStreamAllValuesOfcomment(new Object[]{pPost, null, pCommentLikeCount});
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Comment> getAllValuesOfcomment(final TransitivelyCommented.Match partialMatch) {
      return rawStreamAllValuesOfcomment(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Comment> getAllValuesOfcomment(final Post pPost, final Integer pCommentLikeCount) {
      return rawStreamAllValuesOfcomment(new Object[]{pPost, null, pCommentLikeCount}).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for commentLikeCount.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    protected Stream<Integer> rawStreamAllValuesOfcommentLikeCount(final Object[] parameters) {
      return rawStreamAllValues(POSITION_COMMENTLIKECOUNT, parameters).map(Integer.class::cast);
    }
    
    /**
     * Retrieve the set of values that occur in matches for commentLikeCount.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Integer> getAllValuesOfcommentLikeCount() {
      return rawStreamAllValuesOfcommentLikeCount(emptyArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for commentLikeCount.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Stream<Integer> streamAllValuesOfcommentLikeCount() {
      return rawStreamAllValuesOfcommentLikeCount(emptyArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for commentLikeCount.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<Integer> streamAllValuesOfcommentLikeCount(final TransitivelyCommented.Match partialMatch) {
      return rawStreamAllValuesOfcommentLikeCount(partialMatch.toArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for commentLikeCount.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<Integer> streamAllValuesOfcommentLikeCount(final Post pPost, final Comment pComment) {
      return rawStreamAllValuesOfcommentLikeCount(new Object[]{pPost, pComment, null});
    }
    
    /**
     * Retrieve the set of values that occur in matches for commentLikeCount.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Integer> getAllValuesOfcommentLikeCount(final TransitivelyCommented.Match partialMatch) {
      return rawStreamAllValuesOfcommentLikeCount(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for commentLikeCount.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Integer> getAllValuesOfcommentLikeCount(final Post pPost, final Comment pComment) {
      return rawStreamAllValuesOfcommentLikeCount(new Object[]{pPost, pComment, null}).collect(Collectors.toSet());
    }
    
    @Override
    protected TransitivelyCommented.Match tupleToMatch(final Tuple t) {
      try {
          return TransitivelyCommented.Match.newMatch((Post) t.get(POSITION_POST), (Comment) t.get(POSITION_COMMENT), (Integer) t.get(POSITION_COMMENTLIKECOUNT));
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in tuple not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected TransitivelyCommented.Match arrayToMatch(final Object[] match) {
      try {
          return TransitivelyCommented.Match.newMatch((Post) match[POSITION_POST], (Comment) match[POSITION_COMMENT], (Integer) match[POSITION_COMMENTLIKECOUNT]);
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in array not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected TransitivelyCommented.Match arrayToMatchMutable(final Object[] match) {
      try {
          return TransitivelyCommented.Match.newMutableMatch((Post) match[POSITION_POST], (Comment) match[POSITION_COMMENT], (Integer) match[POSITION_COMMENTLIKECOUNT]);
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in array not properly typed!",e);
          return null;
      }
    }
    
    /**
     * @return the singleton instance of the query specification of this pattern
     * @throws ViatraQueryRuntimeException if the pattern definition could not be loaded
     * 
     */
    public static IQuerySpecification<TransitivelyCommented.Matcher> querySpecification() {
      return TransitivelyCommented.instance();
    }
  }
  
  private TransitivelyCommented() {
    super(GeneratedPQuery.INSTANCE);
  }
  
  /**
   * @return the singleton instance of the query specification
   * @throws ViatraQueryRuntimeException if the pattern definition could not be loaded
   * 
   */
  public static TransitivelyCommented instance() {
    try{
        return LazyHolder.INSTANCE;
    } catch (ExceptionInInitializerError err) {
        throw processInitializerError(err);
    }
  }
  
  @Override
  protected TransitivelyCommented.Matcher instantiate(final ViatraQueryEngine engine) {
    return TransitivelyCommented.Matcher.on(engine);
  }
  
  @Override
  public TransitivelyCommented.Matcher instantiate() {
    return TransitivelyCommented.Matcher.create();
  }
  
  @Override
  public TransitivelyCommented.Match newEmptyMatch() {
    return TransitivelyCommented.Match.newEmptyMatch();
  }
  
  @Override
  public TransitivelyCommented.Match newMatch(final Object... parameters) {
    return TransitivelyCommented.Match.newMatch((SocialNetwork.Post) parameters[0], (SocialNetwork.Comment) parameters[1], (java.lang.Integer) parameters[2]);
  }
  
  /**
   * Inner class allowing the singleton instance of {@link JvmGenericType: queries.TransitivelyCommented (visibility: PUBLIC, simpleName: TransitivelyCommented, identifier: queries.TransitivelyCommented, deprecated: <unset>) (abstract: false, static: false, final: true, packageName: queries) (interface: false, strictFloatingPoint: false, anonymous: false)} to be created 
   *     <b>not</b> at the class load time of the outer class, 
   *     but rather at the first call to {@link JvmGenericType: queries.TransitivelyCommented (visibility: PUBLIC, simpleName: TransitivelyCommented, identifier: queries.TransitivelyCommented, deprecated: <unset>) (abstract: false, static: false, final: true, packageName: queries) (interface: false, strictFloatingPoint: false, anonymous: false)#instance()}.
   * 
   * <p> This workaround is required e.g. to support recursion.
   * 
   */
  private static class LazyHolder {
    private final static TransitivelyCommented INSTANCE = new TransitivelyCommented();
    
    /**
     * Statically initializes the query specification <b>after</b> the field {@link #INSTANCE} is assigned.
     * This initialization order is required to support indirect recursion.
     * 
     * <p> The static initializer is defined using a helper field to work around limitations of the code generator.
     * 
     */
    private final static Object STATIC_INITIALIZER = ensureInitialized();
    
    public static Object ensureInitialized() {
      INSTANCE.ensureInitializedInternal();
      return null;
    }
  }
  
  private static class GeneratedPQuery extends BaseGeneratedEMFPQuery {
    private final static TransitivelyCommented.GeneratedPQuery INSTANCE = new GeneratedPQuery();
    
    private final PParameter parameter_post = new PParameter("post", "SocialNetwork.Post", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("https://www.transformation-tool-contest.eu/2018/social_media", "Post")), PParameterDirection.INOUT);
    
    private final PParameter parameter_comment = new PParameter("comment", "SocialNetwork.Comment", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")), PParameterDirection.INOUT);
    
    private final PParameter parameter_commentLikeCount = new PParameter("commentLikeCount", "java.lang.Integer", new JavaTransitiveInstancesKey(java.lang.Integer.class), PParameterDirection.INOUT);
    
    private final List<PParameter> parameters = Arrays.asList(parameter_post, parameter_comment, parameter_commentLikeCount);
    
    private GeneratedPQuery() {
      super(PVisibility.PUBLIC);
    }
    
    @Override
    public String getFullyQualifiedName() {
      return "queries.transitivelyCommented";
    }
    
    @Override
    public List<String> getParameterNames() {
      return Arrays.asList("post","comment","commentLikeCount");
    }
    
    @Override
    public List<PParameter> getParameters() {
      return parameters;
    }
    
    @Override
    public Set<PBody> doGetContainedBodies() {
      setEvaluationHints(new QueryEvaluationHint(null, QueryEvaluationHint.BackendRequirement.UNSPECIFIED));
      Set<PBody> bodies = new LinkedHashSet<>();
      {
          PBody body = new PBody(this);
          PVariable var_post = body.getOrCreateVariableByName("post");
          PVariable var_comment = body.getOrCreateVariableByName("comment");
          PVariable var_commentLikeCount = body.getOrCreateVariableByName("commentLikeCount");
          PVariable var__user = body.getOrCreateVariableByName("_user");
          new TypeConstraint(body, Tuples.flatTupleOf(var_post), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Post")));
          new TypeConstraint(body, Tuples.flatTupleOf(var_comment), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")));
          new TypeFilterConstraint(body, Tuples.flatTupleOf(var_commentLikeCount), new JavaTransitiveInstancesKey(java.lang.Integer.class));
          body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
             new ExportedParameter(body, var_post, parameter_post),
             new ExportedParameter(body, var_comment, parameter_comment),
             new ExportedParameter(body, var_commentLikeCount, parameter_commentLikeCount)
          ));
          // 	find commented+(post, comment)
          new BinaryTransitiveClosure(body, Tuples.flatTupleOf(var_post, var_comment), Commented.instance().getInternalQueryRepresentation());
          // 	commentLikeCount == count find likes(_user, comment)
          PVariable var__virtual_0_ = body.getOrCreateVariableByName(".virtual{0}");
          new PatternMatchCounter(body, Tuples.flatTupleOf(var__user, var_comment), Likes.instance().getInternalQueryRepresentation(), var__virtual_0_);
          new Equality(body, var_commentLikeCount, var__virtual_0_);
          bodies.add(body);
      }
      return bodies;
    }
  }
}
