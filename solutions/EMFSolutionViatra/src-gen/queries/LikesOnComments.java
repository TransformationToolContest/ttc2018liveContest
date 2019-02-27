/**
 * Generated from platform:/resource/EMFSolutionViatra/src/queries/ttc.vql
 */
package queries;

import SocialNetwork.Comment;
import SocialNetwork.Post;
import SocialNetwork.User;
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
import org.eclipse.viatra.query.runtime.emf.types.EStructuralFeatureInstancesKey;
import org.eclipse.viatra.query.runtime.matchers.backend.QueryEvaluationHint;
import org.eclipse.viatra.query.runtime.matchers.psystem.PBody;
import org.eclipse.viatra.query.runtime.matchers.psystem.PVariable;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.Equality;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.ExportedParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicenumerables.PositivePatternCall;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicenumerables.TypeConstraint;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameterDirection;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PVisibility;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuple;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuples;
import org.eclipse.viatra.query.runtime.util.ViatraQueryLoggingUtil;
import queries.CommentOnPost;

/**
 * A pattern-specific query specification that can instantiate Matcher in a type-safe way.
 * 
 * <p>Original source:
 *         <code><pre>
 *         pattern likesOnComments(post: Post, comment: Comment, user: User) {
 *         	find commentOnPost(comment, post);
 *         	User.likes(user, comment);
 *         }
 * </pre></code>
 * 
 * @see Matcher
 * @see Match
 * 
 */
@SuppressWarnings("all")
public final class LikesOnComments extends BaseGeneratedEMFQuerySpecification<LikesOnComments.Matcher> {
  /**
   * Pattern-specific match representation of the queries.likesOnComments pattern,
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
    
    private User fUser;
    
    private static List<String> parameterNames = makeImmutableList("post", "comment", "user");
    
    private Match(final Post pPost, final Comment pComment, final User pUser) {
      this.fPost = pPost;
      this.fComment = pComment;
      this.fUser = pUser;
    }
    
    @Override
    public Object get(final String parameterName) {
      if ("post".equals(parameterName)) return this.fPost;
      if ("comment".equals(parameterName)) return this.fComment;
      if ("user".equals(parameterName)) return this.fUser;
      return null;
    }
    
    public Post getPost() {
      return this.fPost;
    }
    
    public Comment getComment() {
      return this.fComment;
    }
    
    public User getUser() {
      return this.fUser;
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
      if ("user".equals(parameterName) ) {
          this.fUser = (User) newValue;
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
    
    public void setUser(final User pUser) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fUser = pUser;
    }
    
    @Override
    public String patternName() {
      return "queries.likesOnComments";
    }
    
    @Override
    public List<String> parameterNames() {
      return LikesOnComments.Match.parameterNames;
    }
    
    @Override
    public Object[] toArray() {
      return new Object[]{fPost, fComment, fUser};
    }
    
    @Override
    public LikesOnComments.Match toImmutable() {
      return isMutable() ? newMatch(fPost, fComment, fUser) : this;
    }
    
    @Override
    public String prettyPrint() {
      StringBuilder result = new StringBuilder();
      result.append("\"post\"=" + prettyPrintValue(fPost) + ", ");
      result.append("\"comment\"=" + prettyPrintValue(fComment) + ", ");
      result.append("\"user\"=" + prettyPrintValue(fUser));
      return result.toString();
    }
    
    @Override
    public int hashCode() {
      return Objects.hash(fPost, fComment, fUser);
    }
    
    @Override
    public boolean equals(final Object obj) {
      if (this == obj)
          return true;
      if (obj == null) {
          return false;
      }
      if ((obj instanceof LikesOnComments.Match)) {
          LikesOnComments.Match other = (LikesOnComments.Match) obj;
          return Objects.equals(fPost, other.fPost) && Objects.equals(fComment, other.fComment) && Objects.equals(fUser, other.fUser);
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
    public LikesOnComments specification() {
      return LikesOnComments.instance();
    }
    
    /**
     * Returns an empty, mutable match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @return the empty match.
     * 
     */
    public static LikesOnComments.Match newEmptyMatch() {
      return new Mutable(null, null, null);
    }
    
    /**
     * Returns a mutable (partial) match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @return the new, mutable (partial) match object.
     * 
     */
    public static LikesOnComments.Match newMutableMatch(final Post pPost, final Comment pComment, final User pUser) {
      return new Mutable(pPost, pComment, pUser);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public static LikesOnComments.Match newMatch(final Post pPost, final Comment pComment, final User pUser) {
      return new Immutable(pPost, pComment, pUser);
    }
    
    private static final class Mutable extends LikesOnComments.Match {
      Mutable(final Post pPost, final Comment pComment, final User pUser) {
        super(pPost, pComment, pUser);
      }
      
      @Override
      public boolean isMutable() {
        return true;
      }
    }
    
    private static final class Immutable extends LikesOnComments.Match {
      Immutable(final Post pPost, final Comment pComment, final User pUser) {
        super(pPost, pComment, pUser);
      }
      
      @Override
      public boolean isMutable() {
        return false;
      }
    }
  }
  
  /**
   * Generated pattern matcher API of the queries.likesOnComments pattern,
   * providing pattern-specific query methods.
   * 
   * <p>Use the pattern matcher on a given model via {@link #on(ViatraQueryEngine)},
   * e.g. in conjunction with {@link ViatraQueryEngine#on(QueryScope)}.
   * 
   * <p>Matches of the pattern will be represented as {@link Match}.
   * 
   * <p>Original source:
   * <code><pre>
   * pattern likesOnComments(post: Post, comment: Comment, user: User) {
   * 	find commentOnPost(comment, post);
   * 	User.likes(user, comment);
   * }
   * </pre></code>
   * 
   * @see Match
   * @see LikesOnComments
   * 
   */
  public static class Matcher extends BaseMatcher<LikesOnComments.Match> {
    /**
     * Initializes the pattern matcher within an existing VIATRA Query engine.
     * If the pattern matcher is already constructed in the engine, only a light-weight reference is returned.
     * 
     * @param engine the existing VIATRA Query engine in which this matcher will be created.
     * @throws ViatraQueryRuntimeException if an error occurs during pattern matcher creation
     * 
     */
    public static LikesOnComments.Matcher on(final ViatraQueryEngine engine) {
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
    public static LikesOnComments.Matcher create() {
      return new Matcher();
    }
    
    private final static int POSITION_POST = 0;
    
    private final static int POSITION_COMMENT = 1;
    
    private final static int POSITION_USER = 2;
    
    private final static Logger LOGGER = ViatraQueryLoggingUtil.getLogger(LikesOnComments.Matcher.class);
    
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
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @return matches represented as a Match object.
     * 
     */
    public Collection<LikesOnComments.Match> getAllMatches(final Post pPost, final Comment pComment, final User pUser) {
      return rawStreamAllMatches(new Object[]{pPost, pComment, pUser}).collect(Collectors.toSet());
    }
    
    /**
     * Returns a stream of all matches of the pattern that conform to the given fixed values of some parameters.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @return a stream of matches represented as a Match object.
     * 
     */
    public Stream<LikesOnComments.Match> streamAllMatches(final Post pPost, final Comment pComment, final User pUser) {
      return rawStreamAllMatches(new Object[]{pPost, pComment, pUser});
    }
    
    /**
     * Returns an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @return a match represented as a Match object, or null if no match is found.
     * 
     */
    public Optional<LikesOnComments.Match> getOneArbitraryMatch(final Post pPost, final Comment pComment, final User pUser) {
      return rawGetOneArbitraryMatch(new Object[]{pPost, pComment, pUser});
    }
    
    /**
     * Indicates whether the given combination of specified pattern parameters constitute a valid pattern match,
     * under any possible substitution of the unspecified parameters (if any).
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @return true if the input is a valid (partial) match of the pattern.
     * 
     */
    public boolean hasMatch(final Post pPost, final Comment pComment, final User pUser) {
      return rawHasMatch(new Object[]{pPost, pComment, pUser});
    }
    
    /**
     * Returns the number of all matches of the pattern that conform to the given fixed values of some parameters.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @return the number of pattern matches found.
     * 
     */
    public int countMatches(final Post pPost, final Comment pComment, final User pUser) {
      return rawCountMatches(new Object[]{pPost, pComment, pUser});
    }
    
    /**
     * Executes the given processor on an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @param processor the action that will process the selected match.
     * @return true if the pattern has at least one match with the given parameter values, false if the processor was not invoked
     * 
     */
    public boolean forOneArbitraryMatch(final Post pPost, final Comment pComment, final User pUser, final Consumer<? super LikesOnComments.Match> processor) {
      return rawForOneArbitraryMatch(new Object[]{pPost, pComment, pUser}, processor);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public LikesOnComments.Match newMatch(final Post pPost, final Comment pComment, final User pUser) {
      return LikesOnComments.Match.newMatch(pPost, pComment, pUser);
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
    public Stream<Post> streamAllValuesOfpost(final LikesOnComments.Match partialMatch) {
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
    public Stream<Post> streamAllValuesOfpost(final Comment pComment, final User pUser) {
      return rawStreamAllValuesOfpost(new Object[]{null, pComment, pUser});
    }
    
    /**
     * Retrieve the set of values that occur in matches for post.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Post> getAllValuesOfpost(final LikesOnComments.Match partialMatch) {
      return rawStreamAllValuesOfpost(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for post.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Post> getAllValuesOfpost(final Comment pComment, final User pUser) {
      return rawStreamAllValuesOfpost(new Object[]{null, pComment, pUser}).collect(Collectors.toSet());
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
    public Stream<Comment> streamAllValuesOfcomment(final LikesOnComments.Match partialMatch) {
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
    public Stream<Comment> streamAllValuesOfcomment(final Post pPost, final User pUser) {
      return rawStreamAllValuesOfcomment(new Object[]{pPost, null, pUser});
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Comment> getAllValuesOfcomment(final LikesOnComments.Match partialMatch) {
      return rawStreamAllValuesOfcomment(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Comment> getAllValuesOfcomment(final Post pPost, final User pUser) {
      return rawStreamAllValuesOfcomment(new Object[]{pPost, null, pUser}).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for user.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    protected Stream<User> rawStreamAllValuesOfuser(final Object[] parameters) {
      return rawStreamAllValues(POSITION_USER, parameters).map(User.class::cast);
    }
    
    /**
     * Retrieve the set of values that occur in matches for user.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<User> getAllValuesOfuser() {
      return rawStreamAllValuesOfuser(emptyArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for user.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Stream<User> streamAllValuesOfuser() {
      return rawStreamAllValuesOfuser(emptyArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for user.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<User> streamAllValuesOfuser(final LikesOnComments.Match partialMatch) {
      return rawStreamAllValuesOfuser(partialMatch.toArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for user.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<User> streamAllValuesOfuser(final Post pPost, final Comment pComment) {
      return rawStreamAllValuesOfuser(new Object[]{pPost, pComment, null});
    }
    
    /**
     * Retrieve the set of values that occur in matches for user.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<User> getAllValuesOfuser(final LikesOnComments.Match partialMatch) {
      return rawStreamAllValuesOfuser(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for user.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<User> getAllValuesOfuser(final Post pPost, final Comment pComment) {
      return rawStreamAllValuesOfuser(new Object[]{pPost, pComment, null}).collect(Collectors.toSet());
    }
    
    @Override
    protected LikesOnComments.Match tupleToMatch(final Tuple t) {
      try {
          return LikesOnComments.Match.newMatch((Post) t.get(POSITION_POST), (Comment) t.get(POSITION_COMMENT), (User) t.get(POSITION_USER));
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in tuple not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected LikesOnComments.Match arrayToMatch(final Object[] match) {
      try {
          return LikesOnComments.Match.newMatch((Post) match[POSITION_POST], (Comment) match[POSITION_COMMENT], (User) match[POSITION_USER]);
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in array not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected LikesOnComments.Match arrayToMatchMutable(final Object[] match) {
      try {
          return LikesOnComments.Match.newMutableMatch((Post) match[POSITION_POST], (Comment) match[POSITION_COMMENT], (User) match[POSITION_USER]);
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
    public static IQuerySpecification<LikesOnComments.Matcher> querySpecification() {
      return LikesOnComments.instance();
    }
  }
  
  private LikesOnComments() {
    super(GeneratedPQuery.INSTANCE);
  }
  
  /**
   * @return the singleton instance of the query specification
   * @throws ViatraQueryRuntimeException if the pattern definition could not be loaded
   * 
   */
  public static LikesOnComments instance() {
    try{
        return LazyHolder.INSTANCE;
    } catch (ExceptionInInitializerError err) {
        throw processInitializerError(err);
    }
  }
  
  @Override
  protected LikesOnComments.Matcher instantiate(final ViatraQueryEngine engine) {
    return LikesOnComments.Matcher.on(engine);
  }
  
  @Override
  public LikesOnComments.Matcher instantiate() {
    return LikesOnComments.Matcher.create();
  }
  
  @Override
  public LikesOnComments.Match newEmptyMatch() {
    return LikesOnComments.Match.newEmptyMatch();
  }
  
  @Override
  public LikesOnComments.Match newMatch(final Object... parameters) {
    return LikesOnComments.Match.newMatch((SocialNetwork.Post) parameters[0], (SocialNetwork.Comment) parameters[1], (SocialNetwork.User) parameters[2]);
  }
  
  /**
   * Inner class allowing the singleton instance of {@link JvmGenericType: queries.LikesOnComments (visibility: PUBLIC, simpleName: LikesOnComments, identifier: queries.LikesOnComments, deprecated: <unset>) (abstract: false, static: false, final: true, packageName: queries) (interface: false, strictFloatingPoint: false, anonymous: false)} to be created 
   *     <b>not</b> at the class load time of the outer class, 
   *     but rather at the first call to {@link JvmGenericType: queries.LikesOnComments (visibility: PUBLIC, simpleName: LikesOnComments, identifier: queries.LikesOnComments, deprecated: <unset>) (abstract: false, static: false, final: true, packageName: queries) (interface: false, strictFloatingPoint: false, anonymous: false)#instance()}.
   * 
   * <p> This workaround is required e.g. to support recursion.
   * 
   */
  private static class LazyHolder {
    private final static LikesOnComments INSTANCE = new LikesOnComments();
    
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
    private final static LikesOnComments.GeneratedPQuery INSTANCE = new GeneratedPQuery();
    
    private final PParameter parameter_post = new PParameter("post", "SocialNetwork.Post", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("https://www.transformation-tool-contest.eu/2018/social_media", "Post")), PParameterDirection.INOUT);
    
    private final PParameter parameter_comment = new PParameter("comment", "SocialNetwork.Comment", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")), PParameterDirection.INOUT);
    
    private final PParameter parameter_user = new PParameter("user", "SocialNetwork.User", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("https://www.transformation-tool-contest.eu/2018/social_media", "User")), PParameterDirection.INOUT);
    
    private final List<PParameter> parameters = Arrays.asList(parameter_post, parameter_comment, parameter_user);
    
    private GeneratedPQuery() {
      super(PVisibility.PUBLIC);
    }
    
    @Override
    public String getFullyQualifiedName() {
      return "queries.likesOnComments";
    }
    
    @Override
    public List<String> getParameterNames() {
      return Arrays.asList("post","comment","user");
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
          PVariable var_user = body.getOrCreateVariableByName("user");
          new TypeConstraint(body, Tuples.flatTupleOf(var_post), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Post")));
          new TypeConstraint(body, Tuples.flatTupleOf(var_comment), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")));
          new TypeConstraint(body, Tuples.flatTupleOf(var_user), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "User")));
          body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
             new ExportedParameter(body, var_post, parameter_post),
             new ExportedParameter(body, var_comment, parameter_comment),
             new ExportedParameter(body, var_user, parameter_user)
          ));
          // 	find commentOnPost(comment, post)
          new PositivePatternCall(body, Tuples.flatTupleOf(var_comment, var_post), CommentOnPost.instance().getInternalQueryRepresentation());
          // 	User.likes(user, comment)
          new TypeConstraint(body, Tuples.flatTupleOf(var_user), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "User")));
          PVariable var__virtual_0_ = body.getOrCreateVariableByName(".virtual{0}");
          new TypeConstraint(body, Tuples.flatTupleOf(var_user, var__virtual_0_), new EStructuralFeatureInstancesKey(getFeatureLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "User", "likes")));
          new TypeConstraint(body, Tuples.flatTupleOf(var__virtual_0_), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")));
          new Equality(body, var__virtual_0_, var_comment);
          bodies.add(body);
      }
      return bodies;
    }
  }
}
