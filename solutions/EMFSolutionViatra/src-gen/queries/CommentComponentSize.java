/**
 * Generated from platform:/resource/EMFSolutionViatra/src/queries/ttc.vql
 */
package queries;

import SocialNetwork.Comment;
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
import org.eclipse.viatra.query.runtime.matchers.context.common.JavaTransitiveInstancesKey;
import org.eclipse.viatra.query.runtime.matchers.psystem.PBody;
import org.eclipse.viatra.query.runtime.matchers.psystem.PVariable;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.Equality;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.ExportedParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.PatternMatchCounter;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.TypeFilterConstraint;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicenumerables.TypeConstraint;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameterDirection;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PVisibility;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuple;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuples;
import org.eclipse.viatra.query.runtime.util.ViatraQueryLoggingUtil;
import queries.TransitiveFriendLikers;

/**
 * A pattern-specific query specification that can instantiate Matcher in a type-safe way.
 * 
 * <p>Original source:
 *         <code><pre>
 *         pattern commentComponentSize(comment: Comment, user: User, size: java Integer) {
 *         	size == count find transitiveFriendLikers(comment, user, _otherUser);
 *         	User.likes(user, comment);
 *         }
 * </pre></code>
 * 
 * @see Matcher
 * @see Match
 * 
 */
@SuppressWarnings("all")
public final class CommentComponentSize extends BaseGeneratedEMFQuerySpecification<CommentComponentSize.Matcher> {
  /**
   * Pattern-specific match representation of the queries.commentComponentSize pattern,
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
    private Comment fComment;
    
    private User fUser;
    
    private Integer fSize;
    
    private static List<String> parameterNames = makeImmutableList("comment", "user", "size");
    
    private Match(final Comment pComment, final User pUser, final Integer pSize) {
      this.fComment = pComment;
      this.fUser = pUser;
      this.fSize = pSize;
    }
    
    @Override
    public Object get(final String parameterName) {
      if ("comment".equals(parameterName)) return this.fComment;
      if ("user".equals(parameterName)) return this.fUser;
      if ("size".equals(parameterName)) return this.fSize;
      return null;
    }
    
    public Comment getComment() {
      return this.fComment;
    }
    
    public User getUser() {
      return this.fUser;
    }
    
    public Integer getSize() {
      return this.fSize;
    }
    
    @Override
    public boolean set(final String parameterName, final Object newValue) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      if ("comment".equals(parameterName) ) {
          this.fComment = (Comment) newValue;
          return true;
      }
      if ("user".equals(parameterName) ) {
          this.fUser = (User) newValue;
          return true;
      }
      if ("size".equals(parameterName) ) {
          this.fSize = (Integer) newValue;
          return true;
      }
      return false;
    }
    
    public void setComment(final Comment pComment) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fComment = pComment;
    }
    
    public void setUser(final User pUser) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fUser = pUser;
    }
    
    public void setSize(final Integer pSize) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fSize = pSize;
    }
    
    @Override
    public String patternName() {
      return "queries.commentComponentSize";
    }
    
    @Override
    public List<String> parameterNames() {
      return CommentComponentSize.Match.parameterNames;
    }
    
    @Override
    public Object[] toArray() {
      return new Object[]{fComment, fUser, fSize};
    }
    
    @Override
    public CommentComponentSize.Match toImmutable() {
      return isMutable() ? newMatch(fComment, fUser, fSize) : this;
    }
    
    @Override
    public String prettyPrint() {
      StringBuilder result = new StringBuilder();
      result.append("\"comment\"=" + prettyPrintValue(fComment) + ", ");
      result.append("\"user\"=" + prettyPrintValue(fUser) + ", ");
      result.append("\"size\"=" + prettyPrintValue(fSize));
      return result.toString();
    }
    
    @Override
    public int hashCode() {
      return Objects.hash(fComment, fUser, fSize);
    }
    
    @Override
    public boolean equals(final Object obj) {
      if (this == obj)
          return true;
      if (obj == null) {
          return false;
      }
      if ((obj instanceof CommentComponentSize.Match)) {
          CommentComponentSize.Match other = (CommentComponentSize.Match) obj;
          return Objects.equals(fComment, other.fComment) && Objects.equals(fUser, other.fUser) && Objects.equals(fSize, other.fSize);
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
    public CommentComponentSize specification() {
      return CommentComponentSize.instance();
    }
    
    /**
     * Returns an empty, mutable match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @return the empty match.
     * 
     */
    public static CommentComponentSize.Match newEmptyMatch() {
      return new Mutable(null, null, null);
    }
    
    /**
     * Returns a mutable (partial) match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @param pSize the fixed value of pattern parameter size, or null if not bound.
     * @return the new, mutable (partial) match object.
     * 
     */
    public static CommentComponentSize.Match newMutableMatch(final Comment pComment, final User pUser, final Integer pSize) {
      return new Mutable(pComment, pUser, pSize);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @param pSize the fixed value of pattern parameter size, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public static CommentComponentSize.Match newMatch(final Comment pComment, final User pUser, final Integer pSize) {
      return new Immutable(pComment, pUser, pSize);
    }
    
    private static final class Mutable extends CommentComponentSize.Match {
      Mutable(final Comment pComment, final User pUser, final Integer pSize) {
        super(pComment, pUser, pSize);
      }
      
      @Override
      public boolean isMutable() {
        return true;
      }
    }
    
    private static final class Immutable extends CommentComponentSize.Match {
      Immutable(final Comment pComment, final User pUser, final Integer pSize) {
        super(pComment, pUser, pSize);
      }
      
      @Override
      public boolean isMutable() {
        return false;
      }
    }
  }
  
  /**
   * Generated pattern matcher API of the queries.commentComponentSize pattern,
   * providing pattern-specific query methods.
   * 
   * <p>Use the pattern matcher on a given model via {@link #on(ViatraQueryEngine)},
   * e.g. in conjunction with {@link ViatraQueryEngine#on(QueryScope)}.
   * 
   * <p>Matches of the pattern will be represented as {@link Match}.
   * 
   * <p>Original source:
   * <code><pre>
   * pattern commentComponentSize(comment: Comment, user: User, size: java Integer) {
   * 	size == count find transitiveFriendLikers(comment, user, _otherUser);
   * 	User.likes(user, comment);
   * }
   * </pre></code>
   * 
   * @see Match
   * @see CommentComponentSize
   * 
   */
  public static class Matcher extends BaseMatcher<CommentComponentSize.Match> {
    /**
     * Initializes the pattern matcher within an existing VIATRA Query engine.
     * If the pattern matcher is already constructed in the engine, only a light-weight reference is returned.
     * 
     * @param engine the existing VIATRA Query engine in which this matcher will be created.
     * @throws ViatraQueryRuntimeException if an error occurs during pattern matcher creation
     * 
     */
    public static CommentComponentSize.Matcher on(final ViatraQueryEngine engine) {
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
    public static CommentComponentSize.Matcher create() {
      return new Matcher();
    }
    
    private final static int POSITION_COMMENT = 0;
    
    private final static int POSITION_USER = 1;
    
    private final static int POSITION_SIZE = 2;
    
    private final static Logger LOGGER = ViatraQueryLoggingUtil.getLogger(CommentComponentSize.Matcher.class);
    
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
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @param pSize the fixed value of pattern parameter size, or null if not bound.
     * @return matches represented as a Match object.
     * 
     */
    public Collection<CommentComponentSize.Match> getAllMatches(final Comment pComment, final User pUser, final Integer pSize) {
      return rawStreamAllMatches(new Object[]{pComment, pUser, pSize}).collect(Collectors.toSet());
    }
    
    /**
     * Returns a stream of all matches of the pattern that conform to the given fixed values of some parameters.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @param pSize the fixed value of pattern parameter size, or null if not bound.
     * @return a stream of matches represented as a Match object.
     * 
     */
    public Stream<CommentComponentSize.Match> streamAllMatches(final Comment pComment, final User pUser, final Integer pSize) {
      return rawStreamAllMatches(new Object[]{pComment, pUser, pSize});
    }
    
    /**
     * Returns an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @param pSize the fixed value of pattern parameter size, or null if not bound.
     * @return a match represented as a Match object, or null if no match is found.
     * 
     */
    public Optional<CommentComponentSize.Match> getOneArbitraryMatch(final Comment pComment, final User pUser, final Integer pSize) {
      return rawGetOneArbitraryMatch(new Object[]{pComment, pUser, pSize});
    }
    
    /**
     * Indicates whether the given combination of specified pattern parameters constitute a valid pattern match,
     * under any possible substitution of the unspecified parameters (if any).
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @param pSize the fixed value of pattern parameter size, or null if not bound.
     * @return true if the input is a valid (partial) match of the pattern.
     * 
     */
    public boolean hasMatch(final Comment pComment, final User pUser, final Integer pSize) {
      return rawHasMatch(new Object[]{pComment, pUser, pSize});
    }
    
    /**
     * Returns the number of all matches of the pattern that conform to the given fixed values of some parameters.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @param pSize the fixed value of pattern parameter size, or null if not bound.
     * @return the number of pattern matches found.
     * 
     */
    public int countMatches(final Comment pComment, final User pUser, final Integer pSize) {
      return rawCountMatches(new Object[]{pComment, pUser, pSize});
    }
    
    /**
     * Executes the given processor on an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @param pSize the fixed value of pattern parameter size, or null if not bound.
     * @param processor the action that will process the selected match.
     * @return true if the pattern has at least one match with the given parameter values, false if the processor was not invoked
     * 
     */
    public boolean forOneArbitraryMatch(final Comment pComment, final User pUser, final Integer pSize, final Consumer<? super CommentComponentSize.Match> processor) {
      return rawForOneArbitraryMatch(new Object[]{pComment, pUser, pSize}, processor);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @param pSize the fixed value of pattern parameter size, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public CommentComponentSize.Match newMatch(final Comment pComment, final User pUser, final Integer pSize) {
      return CommentComponentSize.Match.newMatch(pComment, pUser, pSize);
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
    public Stream<Comment> streamAllValuesOfcomment(final CommentComponentSize.Match partialMatch) {
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
    public Stream<Comment> streamAllValuesOfcomment(final User pUser, final Integer pSize) {
      return rawStreamAllValuesOfcomment(new Object[]{null, pUser, pSize});
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Comment> getAllValuesOfcomment(final CommentComponentSize.Match partialMatch) {
      return rawStreamAllValuesOfcomment(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Comment> getAllValuesOfcomment(final User pUser, final Integer pSize) {
      return rawStreamAllValuesOfcomment(new Object[]{null, pUser, pSize}).collect(Collectors.toSet());
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
    public Stream<User> streamAllValuesOfuser(final CommentComponentSize.Match partialMatch) {
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
    public Stream<User> streamAllValuesOfuser(final Comment pComment, final Integer pSize) {
      return rawStreamAllValuesOfuser(new Object[]{pComment, null, pSize});
    }
    
    /**
     * Retrieve the set of values that occur in matches for user.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<User> getAllValuesOfuser(final CommentComponentSize.Match partialMatch) {
      return rawStreamAllValuesOfuser(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for user.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<User> getAllValuesOfuser(final Comment pComment, final Integer pSize) {
      return rawStreamAllValuesOfuser(new Object[]{pComment, null, pSize}).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for size.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    protected Stream<Integer> rawStreamAllValuesOfsize(final Object[] parameters) {
      return rawStreamAllValues(POSITION_SIZE, parameters).map(Integer.class::cast);
    }
    
    /**
     * Retrieve the set of values that occur in matches for size.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Integer> getAllValuesOfsize() {
      return rawStreamAllValuesOfsize(emptyArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for size.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Stream<Integer> streamAllValuesOfsize() {
      return rawStreamAllValuesOfsize(emptyArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for size.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<Integer> streamAllValuesOfsize(final CommentComponentSize.Match partialMatch) {
      return rawStreamAllValuesOfsize(partialMatch.toArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for size.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<Integer> streamAllValuesOfsize(final Comment pComment, final User pUser) {
      return rawStreamAllValuesOfsize(new Object[]{pComment, pUser, null});
    }
    
    /**
     * Retrieve the set of values that occur in matches for size.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Integer> getAllValuesOfsize(final CommentComponentSize.Match partialMatch) {
      return rawStreamAllValuesOfsize(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for size.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Integer> getAllValuesOfsize(final Comment pComment, final User pUser) {
      return rawStreamAllValuesOfsize(new Object[]{pComment, pUser, null}).collect(Collectors.toSet());
    }
    
    @Override
    protected CommentComponentSize.Match tupleToMatch(final Tuple t) {
      try {
          return CommentComponentSize.Match.newMatch((Comment) t.get(POSITION_COMMENT), (User) t.get(POSITION_USER), (Integer) t.get(POSITION_SIZE));
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in tuple not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected CommentComponentSize.Match arrayToMatch(final Object[] match) {
      try {
          return CommentComponentSize.Match.newMatch((Comment) match[POSITION_COMMENT], (User) match[POSITION_USER], (Integer) match[POSITION_SIZE]);
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in array not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected CommentComponentSize.Match arrayToMatchMutable(final Object[] match) {
      try {
          return CommentComponentSize.Match.newMutableMatch((Comment) match[POSITION_COMMENT], (User) match[POSITION_USER], (Integer) match[POSITION_SIZE]);
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
    public static IQuerySpecification<CommentComponentSize.Matcher> querySpecification() {
      return CommentComponentSize.instance();
    }
  }
  
  private CommentComponentSize() {
    super(GeneratedPQuery.INSTANCE);
  }
  
  /**
   * @return the singleton instance of the query specification
   * @throws ViatraQueryRuntimeException if the pattern definition could not be loaded
   * 
   */
  public static CommentComponentSize instance() {
    try{
        return LazyHolder.INSTANCE;
    } catch (ExceptionInInitializerError err) {
        throw processInitializerError(err);
    }
  }
  
  @Override
  protected CommentComponentSize.Matcher instantiate(final ViatraQueryEngine engine) {
    return CommentComponentSize.Matcher.on(engine);
  }
  
  @Override
  public CommentComponentSize.Matcher instantiate() {
    return CommentComponentSize.Matcher.create();
  }
  
  @Override
  public CommentComponentSize.Match newEmptyMatch() {
    return CommentComponentSize.Match.newEmptyMatch();
  }
  
  @Override
  public CommentComponentSize.Match newMatch(final Object... parameters) {
    return CommentComponentSize.Match.newMatch((SocialNetwork.Comment) parameters[0], (SocialNetwork.User) parameters[1], (java.lang.Integer) parameters[2]);
  }
  
  /**
   * Inner class allowing the singleton instance of {@link JvmGenericType: queries.CommentComponentSize (visibility: PUBLIC, simpleName: CommentComponentSize, identifier: queries.CommentComponentSize, deprecated: <unset>) (abstract: false, static: false, final: true, packageName: queries) (interface: false, strictFloatingPoint: false, anonymous: false)} to be created 
   *     <b>not</b> at the class load time of the outer class, 
   *     but rather at the first call to {@link JvmGenericType: queries.CommentComponentSize (visibility: PUBLIC, simpleName: CommentComponentSize, identifier: queries.CommentComponentSize, deprecated: <unset>) (abstract: false, static: false, final: true, packageName: queries) (interface: false, strictFloatingPoint: false, anonymous: false)#instance()}.
   * 
   * <p> This workaround is required e.g. to support recursion.
   * 
   */
  private static class LazyHolder {
    private final static CommentComponentSize INSTANCE = new CommentComponentSize();
    
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
    private final static CommentComponentSize.GeneratedPQuery INSTANCE = new GeneratedPQuery();
    
    private final PParameter parameter_comment = new PParameter("comment", "SocialNetwork.Comment", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")), PParameterDirection.INOUT);
    
    private final PParameter parameter_user = new PParameter("user", "SocialNetwork.User", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("https://www.transformation-tool-contest.eu/2018/social_media", "User")), PParameterDirection.INOUT);
    
    private final PParameter parameter_size = new PParameter("size", "java.lang.Integer", new JavaTransitiveInstancesKey(java.lang.Integer.class), PParameterDirection.INOUT);
    
    private final List<PParameter> parameters = Arrays.asList(parameter_comment, parameter_user, parameter_size);
    
    private GeneratedPQuery() {
      super(PVisibility.PUBLIC);
    }
    
    @Override
    public String getFullyQualifiedName() {
      return "queries.commentComponentSize";
    }
    
    @Override
    public List<String> getParameterNames() {
      return Arrays.asList("comment","user","size");
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
          PVariable var_comment = body.getOrCreateVariableByName("comment");
          PVariable var_user = body.getOrCreateVariableByName("user");
          PVariable var_size = body.getOrCreateVariableByName("size");
          PVariable var__otherUser = body.getOrCreateVariableByName("_otherUser");
          new TypeConstraint(body, Tuples.flatTupleOf(var_comment), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")));
          new TypeConstraint(body, Tuples.flatTupleOf(var_user), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "User")));
          new TypeFilterConstraint(body, Tuples.flatTupleOf(var_size), new JavaTransitiveInstancesKey(java.lang.Integer.class));
          body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
             new ExportedParameter(body, var_comment, parameter_comment),
             new ExportedParameter(body, var_user, parameter_user),
             new ExportedParameter(body, var_size, parameter_size)
          ));
          // 	size == count find transitiveFriendLikers(comment, user, _otherUser)
          PVariable var__virtual_0_ = body.getOrCreateVariableByName(".virtual{0}");
          new PatternMatchCounter(body, Tuples.flatTupleOf(var_comment, var_user, var__otherUser), TransitiveFriendLikers.instance().getInternalQueryRepresentation(), var__virtual_0_);
          new Equality(body, var_size, var__virtual_0_);
          // 	User.likes(user, comment)
          new TypeConstraint(body, Tuples.flatTupleOf(var_user), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "User")));
          PVariable var__virtual_1_ = body.getOrCreateVariableByName(".virtual{1}");
          new TypeConstraint(body, Tuples.flatTupleOf(var_user, var__virtual_1_), new EStructuralFeatureInstancesKey(getFeatureLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "User", "likes")));
          new TypeConstraint(body, Tuples.flatTupleOf(var__virtual_1_), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")));
          new Equality(body, var__virtual_1_, var_comment);
          bodies.add(body);
      }
      return bodies;
    }
  }
}
