/**
 * Generated from platform:/resource/EMFSolutionViatra/src/queries/ttc.vql
 */
package queries;

import SocialNetwork.Comment;
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
 *         // task 2
 *         pattern task2(comment: Comment, score: java Integer) {
 *         	score == count find transitiveFriendLikers(comment, _user, _otherUser);
 *         }
 * </pre></code>
 * 
 * @see Matcher
 * @see Match
 * 
 */
@SuppressWarnings("all")
public final class Task2 extends BaseGeneratedEMFQuerySpecification<Task2.Matcher> {
  /**
   * Pattern-specific match representation of the queries.task2 pattern,
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
    
    private Integer fScore;
    
    private static List<String> parameterNames = makeImmutableList("comment", "score");
    
    private Match(final Comment pComment, final Integer pScore) {
      this.fComment = pComment;
      this.fScore = pScore;
    }
    
    @Override
    public Object get(final String parameterName) {
      if ("comment".equals(parameterName)) return this.fComment;
      if ("score".equals(parameterName)) return this.fScore;
      return null;
    }
    
    public Comment getComment() {
      return this.fComment;
    }
    
    public Integer getScore() {
      return this.fScore;
    }
    
    @Override
    public boolean set(final String parameterName, final Object newValue) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      if ("comment".equals(parameterName) ) {
          this.fComment = (Comment) newValue;
          return true;
      }
      if ("score".equals(parameterName) ) {
          this.fScore = (Integer) newValue;
          return true;
      }
      return false;
    }
    
    public void setComment(final Comment pComment) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fComment = pComment;
    }
    
    public void setScore(final Integer pScore) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fScore = pScore;
    }
    
    @Override
    public String patternName() {
      return "queries.task2";
    }
    
    @Override
    public List<String> parameterNames() {
      return Task2.Match.parameterNames;
    }
    
    @Override
    public Object[] toArray() {
      return new Object[]{fComment, fScore};
    }
    
    @Override
    public Task2.Match toImmutable() {
      return isMutable() ? newMatch(fComment, fScore) : this;
    }
    
    @Override
    public String prettyPrint() {
      StringBuilder result = new StringBuilder();
      result.append("\"comment\"=" + prettyPrintValue(fComment) + ", ");
      result.append("\"score\"=" + prettyPrintValue(fScore));
      return result.toString();
    }
    
    @Override
    public int hashCode() {
      return Objects.hash(fComment, fScore);
    }
    
    @Override
    public boolean equals(final Object obj) {
      if (this == obj)
          return true;
      if (obj == null) {
          return false;
      }
      if ((obj instanceof Task2.Match)) {
          Task2.Match other = (Task2.Match) obj;
          return Objects.equals(fComment, other.fComment) && Objects.equals(fScore, other.fScore);
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
    public Task2 specification() {
      return Task2.instance();
    }
    
    /**
     * Returns an empty, mutable match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @return the empty match.
     * 
     */
    public static Task2.Match newEmptyMatch() {
      return new Mutable(null, null);
    }
    
    /**
     * Returns a mutable (partial) match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pScore the fixed value of pattern parameter score, or null if not bound.
     * @return the new, mutable (partial) match object.
     * 
     */
    public static Task2.Match newMutableMatch(final Comment pComment, final Integer pScore) {
      return new Mutable(pComment, pScore);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pScore the fixed value of pattern parameter score, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public static Task2.Match newMatch(final Comment pComment, final Integer pScore) {
      return new Immutable(pComment, pScore);
    }
    
    private static final class Mutable extends Task2.Match {
      Mutable(final Comment pComment, final Integer pScore) {
        super(pComment, pScore);
      }
      
      @Override
      public boolean isMutable() {
        return true;
      }
    }
    
    private static final class Immutable extends Task2.Match {
      Immutable(final Comment pComment, final Integer pScore) {
        super(pComment, pScore);
      }
      
      @Override
      public boolean isMutable() {
        return false;
      }
    }
  }
  
  /**
   * Generated pattern matcher API of the queries.task2 pattern,
   * providing pattern-specific query methods.
   * 
   * <p>Use the pattern matcher on a given model via {@link #on(ViatraQueryEngine)},
   * e.g. in conjunction with {@link ViatraQueryEngine#on(QueryScope)}.
   * 
   * <p>Matches of the pattern will be represented as {@link Match}.
   * 
   * <p>Original source:
   * <code><pre>
   * // task 2
   * pattern task2(comment: Comment, score: java Integer) {
   * 	score == count find transitiveFriendLikers(comment, _user, _otherUser);
   * }
   * </pre></code>
   * 
   * @see Match
   * @see Task2
   * 
   */
  public static class Matcher extends BaseMatcher<Task2.Match> {
    /**
     * Initializes the pattern matcher within an existing VIATRA Query engine.
     * If the pattern matcher is already constructed in the engine, only a light-weight reference is returned.
     * 
     * @param engine the existing VIATRA Query engine in which this matcher will be created.
     * @throws ViatraQueryRuntimeException if an error occurs during pattern matcher creation
     * 
     */
    public static Task2.Matcher on(final ViatraQueryEngine engine) {
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
    public static Task2.Matcher create() {
      return new Matcher();
    }
    
    private final static int POSITION_COMMENT = 0;
    
    private final static int POSITION_SCORE = 1;
    
    private final static Logger LOGGER = ViatraQueryLoggingUtil.getLogger(Task2.Matcher.class);
    
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
     * @param pScore the fixed value of pattern parameter score, or null if not bound.
     * @return matches represented as a Match object.
     * 
     */
    public Collection<Task2.Match> getAllMatches(final Comment pComment, final Integer pScore) {
      return rawStreamAllMatches(new Object[]{pComment, pScore}).collect(Collectors.toSet());
    }
    
    /**
     * Returns a stream of all matches of the pattern that conform to the given fixed values of some parameters.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pScore the fixed value of pattern parameter score, or null if not bound.
     * @return a stream of matches represented as a Match object.
     * 
     */
    public Stream<Task2.Match> streamAllMatches(final Comment pComment, final Integer pScore) {
      return rawStreamAllMatches(new Object[]{pComment, pScore});
    }
    
    /**
     * Returns an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pScore the fixed value of pattern parameter score, or null if not bound.
     * @return a match represented as a Match object, or null if no match is found.
     * 
     */
    public Optional<Task2.Match> getOneArbitraryMatch(final Comment pComment, final Integer pScore) {
      return rawGetOneArbitraryMatch(new Object[]{pComment, pScore});
    }
    
    /**
     * Indicates whether the given combination of specified pattern parameters constitute a valid pattern match,
     * under any possible substitution of the unspecified parameters (if any).
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pScore the fixed value of pattern parameter score, or null if not bound.
     * @return true if the input is a valid (partial) match of the pattern.
     * 
     */
    public boolean hasMatch(final Comment pComment, final Integer pScore) {
      return rawHasMatch(new Object[]{pComment, pScore});
    }
    
    /**
     * Returns the number of all matches of the pattern that conform to the given fixed values of some parameters.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pScore the fixed value of pattern parameter score, or null if not bound.
     * @return the number of pattern matches found.
     * 
     */
    public int countMatches(final Comment pComment, final Integer pScore) {
      return rawCountMatches(new Object[]{pComment, pScore});
    }
    
    /**
     * Executes the given processor on an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pScore the fixed value of pattern parameter score, or null if not bound.
     * @param processor the action that will process the selected match.
     * @return true if the pattern has at least one match with the given parameter values, false if the processor was not invoked
     * 
     */
    public boolean forOneArbitraryMatch(final Comment pComment, final Integer pScore, final Consumer<? super Task2.Match> processor) {
      return rawForOneArbitraryMatch(new Object[]{pComment, pScore}, processor);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pScore the fixed value of pattern parameter score, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public Task2.Match newMatch(final Comment pComment, final Integer pScore) {
      return Task2.Match.newMatch(pComment, pScore);
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
    public Stream<Comment> streamAllValuesOfcomment(final Task2.Match partialMatch) {
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
    public Stream<Comment> streamAllValuesOfcomment(final Integer pScore) {
      return rawStreamAllValuesOfcomment(new Object[]{null, pScore});
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Comment> getAllValuesOfcomment(final Task2.Match partialMatch) {
      return rawStreamAllValuesOfcomment(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Comment> getAllValuesOfcomment(final Integer pScore) {
      return rawStreamAllValuesOfcomment(new Object[]{null, pScore}).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for score.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    protected Stream<Integer> rawStreamAllValuesOfscore(final Object[] parameters) {
      return rawStreamAllValues(POSITION_SCORE, parameters).map(Integer.class::cast);
    }
    
    /**
     * Retrieve the set of values that occur in matches for score.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Integer> getAllValuesOfscore() {
      return rawStreamAllValuesOfscore(emptyArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for score.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Stream<Integer> streamAllValuesOfscore() {
      return rawStreamAllValuesOfscore(emptyArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for score.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<Integer> streamAllValuesOfscore(final Task2.Match partialMatch) {
      return rawStreamAllValuesOfscore(partialMatch.toArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for score.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<Integer> streamAllValuesOfscore(final Comment pComment) {
      return rawStreamAllValuesOfscore(new Object[]{pComment, null});
    }
    
    /**
     * Retrieve the set of values that occur in matches for score.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Integer> getAllValuesOfscore(final Task2.Match partialMatch) {
      return rawStreamAllValuesOfscore(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for score.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Integer> getAllValuesOfscore(final Comment pComment) {
      return rawStreamAllValuesOfscore(new Object[]{pComment, null}).collect(Collectors.toSet());
    }
    
    @Override
    protected Task2.Match tupleToMatch(final Tuple t) {
      try {
          return Task2.Match.newMatch((Comment) t.get(POSITION_COMMENT), (Integer) t.get(POSITION_SCORE));
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in tuple not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected Task2.Match arrayToMatch(final Object[] match) {
      try {
          return Task2.Match.newMatch((Comment) match[POSITION_COMMENT], (Integer) match[POSITION_SCORE]);
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in array not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected Task2.Match arrayToMatchMutable(final Object[] match) {
      try {
          return Task2.Match.newMutableMatch((Comment) match[POSITION_COMMENT], (Integer) match[POSITION_SCORE]);
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
    public static IQuerySpecification<Task2.Matcher> querySpecification() {
      return Task2.instance();
    }
  }
  
  private Task2() {
    super(GeneratedPQuery.INSTANCE);
  }
  
  /**
   * @return the singleton instance of the query specification
   * @throws ViatraQueryRuntimeException if the pattern definition could not be loaded
   * 
   */
  public static Task2 instance() {
    try{
        return LazyHolder.INSTANCE;
    } catch (ExceptionInInitializerError err) {
        throw processInitializerError(err);
    }
  }
  
  @Override
  protected Task2.Matcher instantiate(final ViatraQueryEngine engine) {
    return Task2.Matcher.on(engine);
  }
  
  @Override
  public Task2.Matcher instantiate() {
    return Task2.Matcher.create();
  }
  
  @Override
  public Task2.Match newEmptyMatch() {
    return Task2.Match.newEmptyMatch();
  }
  
  @Override
  public Task2.Match newMatch(final Object... parameters) {
    return Task2.Match.newMatch((SocialNetwork.Comment) parameters[0], (java.lang.Integer) parameters[1]);
  }
  
  /**
   * Inner class allowing the singleton instance of {@link JvmGenericType: queries.Task2 (visibility: PUBLIC, simpleName: Task2, identifier: queries.Task2, deprecated: <unset>) (abstract: false, static: false, final: true, packageName: queries) (interface: false, strictFloatingPoint: false, anonymous: false)} to be created 
   *     <b>not</b> at the class load time of the outer class, 
   *     but rather at the first call to {@link JvmGenericType: queries.Task2 (visibility: PUBLIC, simpleName: Task2, identifier: queries.Task2, deprecated: <unset>) (abstract: false, static: false, final: true, packageName: queries) (interface: false, strictFloatingPoint: false, anonymous: false)#instance()}.
   * 
   * <p> This workaround is required e.g. to support recursion.
   * 
   */
  private static class LazyHolder {
    private final static Task2 INSTANCE = new Task2();
    
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
    private final static Task2.GeneratedPQuery INSTANCE = new GeneratedPQuery();
    
    private final PParameter parameter_comment = new PParameter("comment", "SocialNetwork.Comment", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")), PParameterDirection.INOUT);
    
    private final PParameter parameter_score = new PParameter("score", "java.lang.Integer", new JavaTransitiveInstancesKey(java.lang.Integer.class), PParameterDirection.INOUT);
    
    private final List<PParameter> parameters = Arrays.asList(parameter_comment, parameter_score);
    
    private GeneratedPQuery() {
      super(PVisibility.PUBLIC);
    }
    
    @Override
    public String getFullyQualifiedName() {
      return "queries.task2";
    }
    
    @Override
    public List<String> getParameterNames() {
      return Arrays.asList("comment","score");
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
          PVariable var_score = body.getOrCreateVariableByName("score");
          PVariable var__user = body.getOrCreateVariableByName("_user");
          PVariable var__otherUser = body.getOrCreateVariableByName("_otherUser");
          new TypeConstraint(body, Tuples.flatTupleOf(var_comment), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")));
          new TypeFilterConstraint(body, Tuples.flatTupleOf(var_score), new JavaTransitiveInstancesKey(java.lang.Integer.class));
          body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
             new ExportedParameter(body, var_comment, parameter_comment),
             new ExportedParameter(body, var_score, parameter_score)
          ));
          // 	score == count find transitiveFriendLikers(comment, _user, _otherUser)
          PVariable var__virtual_0_ = body.getOrCreateVariableByName(".virtual{0}");
          new PatternMatchCounter(body, Tuples.flatTupleOf(var_comment, var__user, var__otherUser), TransitiveFriendLikers.instance().getInternalQueryRepresentation(), var__virtual_0_);
          new Equality(body, var_score, var__virtual_0_);
          bodies.add(body);
      }
      return bodies;
    }
  }
}
