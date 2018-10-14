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
import queries.MutuallyLikedComment;

/**
 * A pattern-specific query specification that can instantiate Matcher in a type-safe way.
 * 
 * <p>Original source:
 *         <code><pre>
 *         pattern transitiveFriendLikers(comment: Comment, user1: User, user2: User) {
 *         	User.likes(user1, comment);
 *         	user1 == user2;
 *         } or {
 *         	find mutuallyLikedComment(comment, user1, friend);
 *         	find transitiveFriendLikers(comment, friend, user2);
 *         }
 * </pre></code>
 * 
 * @see Matcher
 * @see Match
 * 
 */
@SuppressWarnings("all")
public final class TransitiveFriendLikers extends BaseGeneratedEMFQuerySpecification<TransitiveFriendLikers.Matcher> {
  /**
   * Pattern-specific match representation of the queries.transitiveFriendLikers pattern,
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
    
    private User fUser1;
    
    private User fUser2;
    
    private static List<String> parameterNames = makeImmutableList("comment", "user1", "user2");
    
    private Match(final Comment pComment, final User pUser1, final User pUser2) {
      this.fComment = pComment;
      this.fUser1 = pUser1;
      this.fUser2 = pUser2;
    }
    
    @Override
    public Object get(final String parameterName) {
      if ("comment".equals(parameterName)) return this.fComment;
      if ("user1".equals(parameterName)) return this.fUser1;
      if ("user2".equals(parameterName)) return this.fUser2;
      return null;
    }
    
    public Comment getComment() {
      return this.fComment;
    }
    
    public User getUser1() {
      return this.fUser1;
    }
    
    public User getUser2() {
      return this.fUser2;
    }
    
    @Override
    public boolean set(final String parameterName, final Object newValue) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      if ("comment".equals(parameterName) ) {
          this.fComment = (Comment) newValue;
          return true;
      }
      if ("user1".equals(parameterName) ) {
          this.fUser1 = (User) newValue;
          return true;
      }
      if ("user2".equals(parameterName) ) {
          this.fUser2 = (User) newValue;
          return true;
      }
      return false;
    }
    
    public void setComment(final Comment pComment) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fComment = pComment;
    }
    
    public void setUser1(final User pUser1) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fUser1 = pUser1;
    }
    
    public void setUser2(final User pUser2) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fUser2 = pUser2;
    }
    
    @Override
    public String patternName() {
      return "queries.transitiveFriendLikers";
    }
    
    @Override
    public List<String> parameterNames() {
      return TransitiveFriendLikers.Match.parameterNames;
    }
    
    @Override
    public Object[] toArray() {
      return new Object[]{fComment, fUser1, fUser2};
    }
    
    @Override
    public TransitiveFriendLikers.Match toImmutable() {
      return isMutable() ? newMatch(fComment, fUser1, fUser2) : this;
    }
    
    @Override
    public String prettyPrint() {
      StringBuilder result = new StringBuilder();
      result.append("\"comment\"=" + prettyPrintValue(fComment) + ", ");
      result.append("\"user1\"=" + prettyPrintValue(fUser1) + ", ");
      result.append("\"user2\"=" + prettyPrintValue(fUser2));
      return result.toString();
    }
    
    @Override
    public int hashCode() {
      return Objects.hash(fComment, fUser1, fUser2);
    }
    
    @Override
    public boolean equals(final Object obj) {
      if (this == obj)
          return true;
      if (obj == null) {
          return false;
      }
      if ((obj instanceof TransitiveFriendLikers.Match)) {
          TransitiveFriendLikers.Match other = (TransitiveFriendLikers.Match) obj;
          return Objects.equals(fComment, other.fComment) && Objects.equals(fUser1, other.fUser1) && Objects.equals(fUser2, other.fUser2);
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
    public TransitiveFriendLikers specification() {
      return TransitiveFriendLikers.instance();
    }
    
    /**
     * Returns an empty, mutable match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @return the empty match.
     * 
     */
    public static TransitiveFriendLikers.Match newEmptyMatch() {
      return new Mutable(null, null, null);
    }
    
    /**
     * Returns a mutable (partial) match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser1 the fixed value of pattern parameter user1, or null if not bound.
     * @param pUser2 the fixed value of pattern parameter user2, or null if not bound.
     * @return the new, mutable (partial) match object.
     * 
     */
    public static TransitiveFriendLikers.Match newMutableMatch(final Comment pComment, final User pUser1, final User pUser2) {
      return new Mutable(pComment, pUser1, pUser2);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser1 the fixed value of pattern parameter user1, or null if not bound.
     * @param pUser2 the fixed value of pattern parameter user2, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public static TransitiveFriendLikers.Match newMatch(final Comment pComment, final User pUser1, final User pUser2) {
      return new Immutable(pComment, pUser1, pUser2);
    }
    
    private static final class Mutable extends TransitiveFriendLikers.Match {
      Mutable(final Comment pComment, final User pUser1, final User pUser2) {
        super(pComment, pUser1, pUser2);
      }
      
      @Override
      public boolean isMutable() {
        return true;
      }
    }
    
    private static final class Immutable extends TransitiveFriendLikers.Match {
      Immutable(final Comment pComment, final User pUser1, final User pUser2) {
        super(pComment, pUser1, pUser2);
      }
      
      @Override
      public boolean isMutable() {
        return false;
      }
    }
  }
  
  /**
   * Generated pattern matcher API of the queries.transitiveFriendLikers pattern,
   * providing pattern-specific query methods.
   * 
   * <p>Use the pattern matcher on a given model via {@link #on(ViatraQueryEngine)},
   * e.g. in conjunction with {@link ViatraQueryEngine#on(QueryScope)}.
   * 
   * <p>Matches of the pattern will be represented as {@link Match}.
   * 
   * <p>Original source:
   * <code><pre>
   * pattern transitiveFriendLikers(comment: Comment, user1: User, user2: User) {
   * 	User.likes(user1, comment);
   * 	user1 == user2;
   * } or {
   * 	find mutuallyLikedComment(comment, user1, friend);
   * 	find transitiveFriendLikers(comment, friend, user2);
   * }
   * </pre></code>
   * 
   * @see Match
   * @see TransitiveFriendLikers
   * 
   */
  public static class Matcher extends BaseMatcher<TransitiveFriendLikers.Match> {
    /**
     * Initializes the pattern matcher within an existing VIATRA Query engine.
     * If the pattern matcher is already constructed in the engine, only a light-weight reference is returned.
     * 
     * @param engine the existing VIATRA Query engine in which this matcher will be created.
     * @throws ViatraQueryRuntimeException if an error occurs during pattern matcher creation
     * 
     */
    public static TransitiveFriendLikers.Matcher on(final ViatraQueryEngine engine) {
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
    public static TransitiveFriendLikers.Matcher create() {
      return new Matcher();
    }
    
    private final static int POSITION_COMMENT = 0;
    
    private final static int POSITION_USER1 = 1;
    
    private final static int POSITION_USER2 = 2;
    
    private final static Logger LOGGER = ViatraQueryLoggingUtil.getLogger(TransitiveFriendLikers.Matcher.class);
    
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
     * @param pUser1 the fixed value of pattern parameter user1, or null if not bound.
     * @param pUser2 the fixed value of pattern parameter user2, or null if not bound.
     * @return matches represented as a Match object.
     * 
     */
    public Collection<TransitiveFriendLikers.Match> getAllMatches(final Comment pComment, final User pUser1, final User pUser2) {
      return rawStreamAllMatches(new Object[]{pComment, pUser1, pUser2}).collect(Collectors.toSet());
    }
    
    /**
     * Returns a stream of all matches of the pattern that conform to the given fixed values of some parameters.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser1 the fixed value of pattern parameter user1, or null if not bound.
     * @param pUser2 the fixed value of pattern parameter user2, or null if not bound.
     * @return a stream of matches represented as a Match object.
     * 
     */
    public Stream<TransitiveFriendLikers.Match> streamAllMatches(final Comment pComment, final User pUser1, final User pUser2) {
      return rawStreamAllMatches(new Object[]{pComment, pUser1, pUser2});
    }
    
    /**
     * Returns an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser1 the fixed value of pattern parameter user1, or null if not bound.
     * @param pUser2 the fixed value of pattern parameter user2, or null if not bound.
     * @return a match represented as a Match object, or null if no match is found.
     * 
     */
    public Optional<TransitiveFriendLikers.Match> getOneArbitraryMatch(final Comment pComment, final User pUser1, final User pUser2) {
      return rawGetOneArbitraryMatch(new Object[]{pComment, pUser1, pUser2});
    }
    
    /**
     * Indicates whether the given combination of specified pattern parameters constitute a valid pattern match,
     * under any possible substitution of the unspecified parameters (if any).
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser1 the fixed value of pattern parameter user1, or null if not bound.
     * @param pUser2 the fixed value of pattern parameter user2, or null if not bound.
     * @return true if the input is a valid (partial) match of the pattern.
     * 
     */
    public boolean hasMatch(final Comment pComment, final User pUser1, final User pUser2) {
      return rawHasMatch(new Object[]{pComment, pUser1, pUser2});
    }
    
    /**
     * Returns the number of all matches of the pattern that conform to the given fixed values of some parameters.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser1 the fixed value of pattern parameter user1, or null if not bound.
     * @param pUser2 the fixed value of pattern parameter user2, or null if not bound.
     * @return the number of pattern matches found.
     * 
     */
    public int countMatches(final Comment pComment, final User pUser1, final User pUser2) {
      return rawCountMatches(new Object[]{pComment, pUser1, pUser2});
    }
    
    /**
     * Executes the given processor on an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser1 the fixed value of pattern parameter user1, or null if not bound.
     * @param pUser2 the fixed value of pattern parameter user2, or null if not bound.
     * @param processor the action that will process the selected match.
     * @return true if the pattern has at least one match with the given parameter values, false if the processor was not invoked
     * 
     */
    public boolean forOneArbitraryMatch(final Comment pComment, final User pUser1, final User pUser2, final Consumer<? super TransitiveFriendLikers.Match> processor) {
      return rawForOneArbitraryMatch(new Object[]{pComment, pUser1, pUser2}, processor);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pUser1 the fixed value of pattern parameter user1, or null if not bound.
     * @param pUser2 the fixed value of pattern parameter user2, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public TransitiveFriendLikers.Match newMatch(final Comment pComment, final User pUser1, final User pUser2) {
      return TransitiveFriendLikers.Match.newMatch(pComment, pUser1, pUser2);
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
    public Stream<Comment> streamAllValuesOfcomment(final TransitiveFriendLikers.Match partialMatch) {
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
    public Stream<Comment> streamAllValuesOfcomment(final User pUser1, final User pUser2) {
      return rawStreamAllValuesOfcomment(new Object[]{null, pUser1, pUser2});
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Comment> getAllValuesOfcomment(final TransitiveFriendLikers.Match partialMatch) {
      return rawStreamAllValuesOfcomment(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Comment> getAllValuesOfcomment(final User pUser1, final User pUser2) {
      return rawStreamAllValuesOfcomment(new Object[]{null, pUser1, pUser2}).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for user1.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    protected Stream<User> rawStreamAllValuesOfuser1(final Object[] parameters) {
      return rawStreamAllValues(POSITION_USER1, parameters).map(User.class::cast);
    }
    
    /**
     * Retrieve the set of values that occur in matches for user1.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<User> getAllValuesOfuser1() {
      return rawStreamAllValuesOfuser1(emptyArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for user1.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Stream<User> streamAllValuesOfuser1() {
      return rawStreamAllValuesOfuser1(emptyArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for user1.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<User> streamAllValuesOfuser1(final TransitiveFriendLikers.Match partialMatch) {
      return rawStreamAllValuesOfuser1(partialMatch.toArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for user1.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<User> streamAllValuesOfuser1(final Comment pComment, final User pUser2) {
      return rawStreamAllValuesOfuser1(new Object[]{pComment, null, pUser2});
    }
    
    /**
     * Retrieve the set of values that occur in matches for user1.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<User> getAllValuesOfuser1(final TransitiveFriendLikers.Match partialMatch) {
      return rawStreamAllValuesOfuser1(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for user1.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<User> getAllValuesOfuser1(final Comment pComment, final User pUser2) {
      return rawStreamAllValuesOfuser1(new Object[]{pComment, null, pUser2}).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for user2.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    protected Stream<User> rawStreamAllValuesOfuser2(final Object[] parameters) {
      return rawStreamAllValues(POSITION_USER2, parameters).map(User.class::cast);
    }
    
    /**
     * Retrieve the set of values that occur in matches for user2.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<User> getAllValuesOfuser2() {
      return rawStreamAllValuesOfuser2(emptyArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for user2.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Stream<User> streamAllValuesOfuser2() {
      return rawStreamAllValuesOfuser2(emptyArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for user2.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<User> streamAllValuesOfuser2(final TransitiveFriendLikers.Match partialMatch) {
      return rawStreamAllValuesOfuser2(partialMatch.toArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for user2.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<User> streamAllValuesOfuser2(final Comment pComment, final User pUser1) {
      return rawStreamAllValuesOfuser2(new Object[]{pComment, pUser1, null});
    }
    
    /**
     * Retrieve the set of values that occur in matches for user2.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<User> getAllValuesOfuser2(final TransitiveFriendLikers.Match partialMatch) {
      return rawStreamAllValuesOfuser2(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for user2.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<User> getAllValuesOfuser2(final Comment pComment, final User pUser1) {
      return rawStreamAllValuesOfuser2(new Object[]{pComment, pUser1, null}).collect(Collectors.toSet());
    }
    
    @Override
    protected TransitiveFriendLikers.Match tupleToMatch(final Tuple t) {
      try {
          return TransitiveFriendLikers.Match.newMatch((Comment) t.get(POSITION_COMMENT), (User) t.get(POSITION_USER1), (User) t.get(POSITION_USER2));
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in tuple not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected TransitiveFriendLikers.Match arrayToMatch(final Object[] match) {
      try {
          return TransitiveFriendLikers.Match.newMatch((Comment) match[POSITION_COMMENT], (User) match[POSITION_USER1], (User) match[POSITION_USER2]);
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in array not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected TransitiveFriendLikers.Match arrayToMatchMutable(final Object[] match) {
      try {
          return TransitiveFriendLikers.Match.newMutableMatch((Comment) match[POSITION_COMMENT], (User) match[POSITION_USER1], (User) match[POSITION_USER2]);
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
    public static IQuerySpecification<TransitiveFriendLikers.Matcher> querySpecification() {
      return TransitiveFriendLikers.instance();
    }
  }
  
  private TransitiveFriendLikers() {
    super(GeneratedPQuery.INSTANCE);
  }
  
  /**
   * @return the singleton instance of the query specification
   * @throws ViatraQueryRuntimeException if the pattern definition could not be loaded
   * 
   */
  public static TransitiveFriendLikers instance() {
    try{
        return LazyHolder.INSTANCE;
    } catch (ExceptionInInitializerError err) {
        throw processInitializerError(err);
    }
  }
  
  @Override
  protected TransitiveFriendLikers.Matcher instantiate(final ViatraQueryEngine engine) {
    return TransitiveFriendLikers.Matcher.on(engine);
  }
  
  @Override
  public TransitiveFriendLikers.Matcher instantiate() {
    return TransitiveFriendLikers.Matcher.create();
  }
  
  @Override
  public TransitiveFriendLikers.Match newEmptyMatch() {
    return TransitiveFriendLikers.Match.newEmptyMatch();
  }
  
  @Override
  public TransitiveFriendLikers.Match newMatch(final Object... parameters) {
    return TransitiveFriendLikers.Match.newMatch((SocialNetwork.Comment) parameters[0], (SocialNetwork.User) parameters[1], (SocialNetwork.User) parameters[2]);
  }
  
  /**
   * Inner class allowing the singleton instance of {@link JvmGenericType: queries.TransitiveFriendLikers (visibility: PUBLIC, simpleName: TransitiveFriendLikers, identifier: queries.TransitiveFriendLikers, deprecated: <unset>) (abstract: false, static: false, final: true, packageName: queries) (interface: false, strictFloatingPoint: false, anonymous: false)} to be created 
   *     <b>not</b> at the class load time of the outer class, 
   *     but rather at the first call to {@link JvmGenericType: queries.TransitiveFriendLikers (visibility: PUBLIC, simpleName: TransitiveFriendLikers, identifier: queries.TransitiveFriendLikers, deprecated: <unset>) (abstract: false, static: false, final: true, packageName: queries) (interface: false, strictFloatingPoint: false, anonymous: false)#instance()}.
   * 
   * <p> This workaround is required e.g. to support recursion.
   * 
   */
  private static class LazyHolder {
    private final static TransitiveFriendLikers INSTANCE = new TransitiveFriendLikers();
    
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
    private final static TransitiveFriendLikers.GeneratedPQuery INSTANCE = new GeneratedPQuery();
    
    private final PParameter parameter_comment = new PParameter("comment", "SocialNetwork.Comment", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")), PParameterDirection.INOUT);
    
    private final PParameter parameter_user1 = new PParameter("user1", "SocialNetwork.User", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("https://www.transformation-tool-contest.eu/2018/social_media", "User")), PParameterDirection.INOUT);
    
    private final PParameter parameter_user2 = new PParameter("user2", "SocialNetwork.User", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("https://www.transformation-tool-contest.eu/2018/social_media", "User")), PParameterDirection.INOUT);
    
    private final List<PParameter> parameters = Arrays.asList(parameter_comment, parameter_user1, parameter_user2);
    
    private GeneratedPQuery() {
      super(PVisibility.PUBLIC);
    }
    
    @Override
    public String getFullyQualifiedName() {
      return "queries.transitiveFriendLikers";
    }
    
    @Override
    public List<String> getParameterNames() {
      return Arrays.asList("comment","user1","user2");
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
          PVariable var_user1 = body.getOrCreateVariableByName("user1");
          PVariable var_user2 = body.getOrCreateVariableByName("user2");
          new TypeConstraint(body, Tuples.flatTupleOf(var_comment), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")));
          new TypeConstraint(body, Tuples.flatTupleOf(var_user1), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "User")));
          new TypeConstraint(body, Tuples.flatTupleOf(var_user2), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "User")));
          body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
             new ExportedParameter(body, var_comment, parameter_comment),
             new ExportedParameter(body, var_user1, parameter_user1),
             new ExportedParameter(body, var_user2, parameter_user2)
          ));
          // 	User.likes(user1, comment)
          new TypeConstraint(body, Tuples.flatTupleOf(var_user1), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "User")));
          PVariable var__virtual_0_ = body.getOrCreateVariableByName(".virtual{0}");
          new TypeConstraint(body, Tuples.flatTupleOf(var_user1, var__virtual_0_), new EStructuralFeatureInstancesKey(getFeatureLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "User", "likes")));
          new TypeConstraint(body, Tuples.flatTupleOf(var__virtual_0_), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")));
          new Equality(body, var__virtual_0_, var_comment);
          // 	user1 == user2
          new Equality(body, var_user1, var_user2);
          bodies.add(body);
      }
      {
          PBody body = new PBody(this);
          PVariable var_comment = body.getOrCreateVariableByName("comment");
          PVariable var_user1 = body.getOrCreateVariableByName("user1");
          PVariable var_user2 = body.getOrCreateVariableByName("user2");
          PVariable var_friend = body.getOrCreateVariableByName("friend");
          new TypeConstraint(body, Tuples.flatTupleOf(var_comment), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")));
          new TypeConstraint(body, Tuples.flatTupleOf(var_user1), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "User")));
          new TypeConstraint(body, Tuples.flatTupleOf(var_user2), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "User")));
          body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
             new ExportedParameter(body, var_comment, parameter_comment),
             new ExportedParameter(body, var_user1, parameter_user1),
             new ExportedParameter(body, var_user2, parameter_user2)
          ));
          // 	find mutuallyLikedComment(comment, user1, friend)
          new PositivePatternCall(body, Tuples.flatTupleOf(var_comment, var_user1, var_friend), MutuallyLikedComment.instance().getInternalQueryRepresentation());
          // 	find transitiveFriendLikers(comment, friend, user2)
          new PositivePatternCall(body, Tuples.flatTupleOf(var_comment, var_friend, var_user2), this);
          bodies.add(body);
      }
      return bodies;
    }
  }
}
