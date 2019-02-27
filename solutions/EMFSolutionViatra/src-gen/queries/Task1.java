/**
 * Generated from platform:/resource/EMFSolutionViatra/src/queries/ttc.vql
 */
package queries;

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
import org.eclipse.viatra.query.runtime.matchers.psystem.IExpressionEvaluator;
import org.eclipse.viatra.query.runtime.matchers.psystem.IValueProvider;
import org.eclipse.viatra.query.runtime.matchers.psystem.PBody;
import org.eclipse.viatra.query.runtime.matchers.psystem.PVariable;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.Equality;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.ExportedParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.ExpressionEvaluation;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.PatternMatchCounter;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicdeferred.TypeFilterConstraint;
import org.eclipse.viatra.query.runtime.matchers.psystem.basicenumerables.TypeConstraint;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameterDirection;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PVisibility;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuple;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuples;
import org.eclipse.viatra.query.runtime.util.ViatraQueryLoggingUtil;
import queries.CommentOnPost;
import queries.LikesOnComments;

/**
 * A pattern-specific query specification that can instantiate Matcher in a type-safe way.
 * 
 * <p>Original source:
 *         <code><pre>
 *         // task 1
 *         pattern task1(post: Post, score: java Integer) {
 *         	Post(post);
 *         	commCount == count find commentOnPost(_comment, post);
 *         	likeCount == count find likesOnComments(post, _comment2, _user);
 *         	score == eval(10commCount + likeCount);
 *         }
 * </pre></code>
 * 
 * @see Matcher
 * @see Match
 * 
 */
@SuppressWarnings("all")
public final class Task1 extends BaseGeneratedEMFQuerySpecification<Task1.Matcher> {
  /**
   * Pattern-specific match representation of the queries.task1 pattern,
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
    
    private Integer fScore;
    
    private static List<String> parameterNames = makeImmutableList("post", "score");
    
    private Match(final Post pPost, final Integer pScore) {
      this.fPost = pPost;
      this.fScore = pScore;
    }
    
    @Override
    public Object get(final String parameterName) {
      if ("post".equals(parameterName)) return this.fPost;
      if ("score".equals(parameterName)) return this.fScore;
      return null;
    }
    
    public Post getPost() {
      return this.fPost;
    }
    
    public Integer getScore() {
      return this.fScore;
    }
    
    @Override
    public boolean set(final String parameterName, final Object newValue) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      if ("post".equals(parameterName) ) {
          this.fPost = (Post) newValue;
          return true;
      }
      if ("score".equals(parameterName) ) {
          this.fScore = (Integer) newValue;
          return true;
      }
      return false;
    }
    
    public void setPost(final Post pPost) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fPost = pPost;
    }
    
    public void setScore(final Integer pScore) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fScore = pScore;
    }
    
    @Override
    public String patternName() {
      return "queries.task1";
    }
    
    @Override
    public List<String> parameterNames() {
      return Task1.Match.parameterNames;
    }
    
    @Override
    public Object[] toArray() {
      return new Object[]{fPost, fScore};
    }
    
    @Override
    public Task1.Match toImmutable() {
      return isMutable() ? newMatch(fPost, fScore) : this;
    }
    
    @Override
    public String prettyPrint() {
      StringBuilder result = new StringBuilder();
      result.append("\"post\"=" + prettyPrintValue(fPost) + ", ");
      result.append("\"score\"=" + prettyPrintValue(fScore));
      return result.toString();
    }
    
    @Override
    public int hashCode() {
      return Objects.hash(fPost, fScore);
    }
    
    @Override
    public boolean equals(final Object obj) {
      if (this == obj)
          return true;
      if (obj == null) {
          return false;
      }
      if ((obj instanceof Task1.Match)) {
          Task1.Match other = (Task1.Match) obj;
          return Objects.equals(fPost, other.fPost) && Objects.equals(fScore, other.fScore);
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
    public Task1 specification() {
      return Task1.instance();
    }
    
    /**
     * Returns an empty, mutable match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @return the empty match.
     * 
     */
    public static Task1.Match newEmptyMatch() {
      return new Mutable(null, null);
    }
    
    /**
     * Returns a mutable (partial) match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pScore the fixed value of pattern parameter score, or null if not bound.
     * @return the new, mutable (partial) match object.
     * 
     */
    public static Task1.Match newMutableMatch(final Post pPost, final Integer pScore) {
      return new Mutable(pPost, pScore);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pScore the fixed value of pattern parameter score, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public static Task1.Match newMatch(final Post pPost, final Integer pScore) {
      return new Immutable(pPost, pScore);
    }
    
    private static final class Mutable extends Task1.Match {
      Mutable(final Post pPost, final Integer pScore) {
        super(pPost, pScore);
      }
      
      @Override
      public boolean isMutable() {
        return true;
      }
    }
    
    private static final class Immutable extends Task1.Match {
      Immutable(final Post pPost, final Integer pScore) {
        super(pPost, pScore);
      }
      
      @Override
      public boolean isMutable() {
        return false;
      }
    }
  }
  
  /**
   * Generated pattern matcher API of the queries.task1 pattern,
   * providing pattern-specific query methods.
   * 
   * <p>Use the pattern matcher on a given model via {@link #on(ViatraQueryEngine)},
   * e.g. in conjunction with {@link ViatraQueryEngine#on(QueryScope)}.
   * 
   * <p>Matches of the pattern will be represented as {@link Match}.
   * 
   * <p>Original source:
   * <code><pre>
   * // task 1
   * pattern task1(post: Post, score: java Integer) {
   * 	Post(post);
   * 	commCount == count find commentOnPost(_comment, post);
   * 	likeCount == count find likesOnComments(post, _comment2, _user);
   * 	score == eval(10commCount + likeCount);
   * }
   * </pre></code>
   * 
   * @see Match
   * @see Task1
   * 
   */
  public static class Matcher extends BaseMatcher<Task1.Match> {
    /**
     * Initializes the pattern matcher within an existing VIATRA Query engine.
     * If the pattern matcher is already constructed in the engine, only a light-weight reference is returned.
     * 
     * @param engine the existing VIATRA Query engine in which this matcher will be created.
     * @throws ViatraQueryRuntimeException if an error occurs during pattern matcher creation
     * 
     */
    public static Task1.Matcher on(final ViatraQueryEngine engine) {
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
    public static Task1.Matcher create() {
      return new Matcher();
    }
    
    private final static int POSITION_POST = 0;
    
    private final static int POSITION_SCORE = 1;
    
    private final static Logger LOGGER = ViatraQueryLoggingUtil.getLogger(Task1.Matcher.class);
    
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
     * @param pScore the fixed value of pattern parameter score, or null if not bound.
     * @return matches represented as a Match object.
     * 
     */
    public Collection<Task1.Match> getAllMatches(final Post pPost, final Integer pScore) {
      return rawStreamAllMatches(new Object[]{pPost, pScore}).collect(Collectors.toSet());
    }
    
    /**
     * Returns a stream of all matches of the pattern that conform to the given fixed values of some parameters.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pScore the fixed value of pattern parameter score, or null if not bound.
     * @return a stream of matches represented as a Match object.
     * 
     */
    public Stream<Task1.Match> streamAllMatches(final Post pPost, final Integer pScore) {
      return rawStreamAllMatches(new Object[]{pPost, pScore});
    }
    
    /**
     * Returns an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pScore the fixed value of pattern parameter score, or null if not bound.
     * @return a match represented as a Match object, or null if no match is found.
     * 
     */
    public Optional<Task1.Match> getOneArbitraryMatch(final Post pPost, final Integer pScore) {
      return rawGetOneArbitraryMatch(new Object[]{pPost, pScore});
    }
    
    /**
     * Indicates whether the given combination of specified pattern parameters constitute a valid pattern match,
     * under any possible substitution of the unspecified parameters (if any).
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pScore the fixed value of pattern parameter score, or null if not bound.
     * @return true if the input is a valid (partial) match of the pattern.
     * 
     */
    public boolean hasMatch(final Post pPost, final Integer pScore) {
      return rawHasMatch(new Object[]{pPost, pScore});
    }
    
    /**
     * Returns the number of all matches of the pattern that conform to the given fixed values of some parameters.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pScore the fixed value of pattern parameter score, or null if not bound.
     * @return the number of pattern matches found.
     * 
     */
    public int countMatches(final Post pPost, final Integer pScore) {
      return rawCountMatches(new Object[]{pPost, pScore});
    }
    
    /**
     * Executes the given processor on an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pScore the fixed value of pattern parameter score, or null if not bound.
     * @param processor the action that will process the selected match.
     * @return true if the pattern has at least one match with the given parameter values, false if the processor was not invoked
     * 
     */
    public boolean forOneArbitraryMatch(final Post pPost, final Integer pScore, final Consumer<? super Task1.Match> processor) {
      return rawForOneArbitraryMatch(new Object[]{pPost, pScore}, processor);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param pScore the fixed value of pattern parameter score, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public Task1.Match newMatch(final Post pPost, final Integer pScore) {
      return Task1.Match.newMatch(pPost, pScore);
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
    public Stream<Post> streamAllValuesOfpost(final Task1.Match partialMatch) {
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
    public Stream<Post> streamAllValuesOfpost(final Integer pScore) {
      return rawStreamAllValuesOfpost(new Object[]{null, pScore});
    }
    
    /**
     * Retrieve the set of values that occur in matches for post.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Post> getAllValuesOfpost(final Task1.Match partialMatch) {
      return rawStreamAllValuesOfpost(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for post.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Post> getAllValuesOfpost(final Integer pScore) {
      return rawStreamAllValuesOfpost(new Object[]{null, pScore}).collect(Collectors.toSet());
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
    public Stream<Integer> streamAllValuesOfscore(final Task1.Match partialMatch) {
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
    public Stream<Integer> streamAllValuesOfscore(final Post pPost) {
      return rawStreamAllValuesOfscore(new Object[]{pPost, null});
    }
    
    /**
     * Retrieve the set of values that occur in matches for score.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Integer> getAllValuesOfscore(final Task1.Match partialMatch) {
      return rawStreamAllValuesOfscore(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for score.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Integer> getAllValuesOfscore(final Post pPost) {
      return rawStreamAllValuesOfscore(new Object[]{pPost, null}).collect(Collectors.toSet());
    }
    
    @Override
    protected Task1.Match tupleToMatch(final Tuple t) {
      try {
          return Task1.Match.newMatch((Post) t.get(POSITION_POST), (Integer) t.get(POSITION_SCORE));
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in tuple not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected Task1.Match arrayToMatch(final Object[] match) {
      try {
          return Task1.Match.newMatch((Post) match[POSITION_POST], (Integer) match[POSITION_SCORE]);
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in array not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected Task1.Match arrayToMatchMutable(final Object[] match) {
      try {
          return Task1.Match.newMutableMatch((Post) match[POSITION_POST], (Integer) match[POSITION_SCORE]);
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
    public static IQuerySpecification<Task1.Matcher> querySpecification() {
      return Task1.instance();
    }
  }
  
  private Task1() {
    super(GeneratedPQuery.INSTANCE);
  }
  
  /**
   * @return the singleton instance of the query specification
   * @throws ViatraQueryRuntimeException if the pattern definition could not be loaded
   * 
   */
  public static Task1 instance() {
    try{
        return LazyHolder.INSTANCE;
    } catch (ExceptionInInitializerError err) {
        throw processInitializerError(err);
    }
  }
  
  @Override
  protected Task1.Matcher instantiate(final ViatraQueryEngine engine) {
    return Task1.Matcher.on(engine);
  }
  
  @Override
  public Task1.Matcher instantiate() {
    return Task1.Matcher.create();
  }
  
  @Override
  public Task1.Match newEmptyMatch() {
    return Task1.Match.newEmptyMatch();
  }
  
  @Override
  public Task1.Match newMatch(final Object... parameters) {
    return Task1.Match.newMatch((SocialNetwork.Post) parameters[0], (java.lang.Integer) parameters[1]);
  }
  
  /**
   * Inner class allowing the singleton instance of {@link JvmGenericType: queries.Task1 (visibility: PUBLIC, simpleName: Task1, identifier: queries.Task1, deprecated: <unset>) (abstract: false, static: false, final: true, packageName: queries) (interface: false, strictFloatingPoint: false, anonymous: false)} to be created 
   *     <b>not</b> at the class load time of the outer class, 
   *     but rather at the first call to {@link JvmGenericType: queries.Task1 (visibility: PUBLIC, simpleName: Task1, identifier: queries.Task1, deprecated: <unset>) (abstract: false, static: false, final: true, packageName: queries) (interface: false, strictFloatingPoint: false, anonymous: false)#instance()}.
   * 
   * <p> This workaround is required e.g. to support recursion.
   * 
   */
  private static class LazyHolder {
    private final static Task1 INSTANCE = new Task1();
    
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
    private final static Task1.GeneratedPQuery INSTANCE = new GeneratedPQuery();
    
    private final PParameter parameter_post = new PParameter("post", "SocialNetwork.Post", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("https://www.transformation-tool-contest.eu/2018/social_media", "Post")), PParameterDirection.INOUT);
    
    private final PParameter parameter_score = new PParameter("score", "java.lang.Integer", new JavaTransitiveInstancesKey(java.lang.Integer.class), PParameterDirection.INOUT);
    
    private final List<PParameter> parameters = Arrays.asList(parameter_post, parameter_score);
    
    private GeneratedPQuery() {
      super(PVisibility.PUBLIC);
    }
    
    @Override
    public String getFullyQualifiedName() {
      return "queries.task1";
    }
    
    @Override
    public List<String> getParameterNames() {
      return Arrays.asList("post","score");
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
          PVariable var_score = body.getOrCreateVariableByName("score");
          PVariable var_commCount = body.getOrCreateVariableByName("commCount");
          PVariable var__comment = body.getOrCreateVariableByName("_comment");
          PVariable var_likeCount = body.getOrCreateVariableByName("likeCount");
          PVariable var__comment2 = body.getOrCreateVariableByName("_comment2");
          PVariable var__user = body.getOrCreateVariableByName("_user");
          new TypeConstraint(body, Tuples.flatTupleOf(var_post), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Post")));
          new TypeFilterConstraint(body, Tuples.flatTupleOf(var_score), new JavaTransitiveInstancesKey(java.lang.Integer.class));
          body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
             new ExportedParameter(body, var_post, parameter_post),
             new ExportedParameter(body, var_score, parameter_score)
          ));
          // 	Post(post)
          new TypeConstraint(body, Tuples.flatTupleOf(var_post), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Post")));
          // 	commCount == count find commentOnPost(_comment, post)
          PVariable var__virtual_0_ = body.getOrCreateVariableByName(".virtual{0}");
          new PatternMatchCounter(body, Tuples.flatTupleOf(var__comment, var_post), CommentOnPost.instance().getInternalQueryRepresentation(), var__virtual_0_);
          new Equality(body, var_commCount, var__virtual_0_);
          // 	likeCount == count find likesOnComments(post, _comment2, _user)
          PVariable var__virtual_1_ = body.getOrCreateVariableByName(".virtual{1}");
          new PatternMatchCounter(body, Tuples.flatTupleOf(var_post, var__comment2, var__user), LikesOnComments.instance().getInternalQueryRepresentation(), var__virtual_1_);
          new Equality(body, var_likeCount, var__virtual_1_);
          // 	score == eval(10*commCount + likeCount)
          PVariable var__virtual_2_ = body.getOrCreateVariableByName(".virtual{2}");
          new ExpressionEvaluation(body, new IExpressionEvaluator() {
          
              @Override
              public String getShortDescription() {
                  return "Expression evaluation from pattern task1";
              }
              
              @Override
              public Iterable<String> getInputParameterNames() {
                  return Arrays.asList("commCount", "likeCount");}
          
              @Override
              public Object evaluateExpression(IValueProvider provider) throws Exception {
                  Integer commCount = (Integer) provider.getValue("commCount");
                  Integer likeCount = (Integer) provider.getValue("likeCount");
                  return evaluateExpression_1_1(commCount, likeCount);
              }
          },  var__virtual_2_ ); 
          new Equality(body, var_score, var__virtual_2_);
          bodies.add(body);
      }
      return bodies;
    }
  }
  
  private static int evaluateExpression_1_1(final Integer commCount, final Integer likeCount) {
    return ((10 * (commCount).intValue()) + (likeCount).intValue());
  }
}
