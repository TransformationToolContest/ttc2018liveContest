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
import org.eclipse.viatra.query.runtime.matchers.psystem.basicenumerables.TypeConstraint;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameter;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PParameterDirection;
import org.eclipse.viatra.query.runtime.matchers.psystem.queries.PVisibility;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuple;
import org.eclipse.viatra.query.runtime.matchers.tuple.Tuples;
import org.eclipse.viatra.query.runtime.util.ViatraQueryLoggingUtil;

/**
 * A pattern-specific query specification that can instantiate Matcher in a type-safe way.
 * 
 * <p>Original source:
 *         <code><pre>
 *         pattern likes(user: User, comment: Comment) {
 *         	User.likes(user, comment); 
 *         }
 * </pre></code>
 * 
 * @see Matcher
 * @see Match
 * 
 */
@SuppressWarnings("all")
public final class Likes extends BaseGeneratedEMFQuerySpecification<Likes.Matcher> {
  /**
   * Pattern-specific match representation of the queries.likes pattern,
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
    private User fUser;
    
    private Comment fComment;
    
    private static List<String> parameterNames = makeImmutableList("user", "comment");
    
    private Match(final User pUser, final Comment pComment) {
      this.fUser = pUser;
      this.fComment = pComment;
    }
    
    @Override
    public Object get(final String parameterName) {
      if ("user".equals(parameterName)) return this.fUser;
      if ("comment".equals(parameterName)) return this.fComment;
      return null;
    }
    
    public User getUser() {
      return this.fUser;
    }
    
    public Comment getComment() {
      return this.fComment;
    }
    
    @Override
    public boolean set(final String parameterName, final Object newValue) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      if ("user".equals(parameterName) ) {
          this.fUser = (User) newValue;
          return true;
      }
      if ("comment".equals(parameterName) ) {
          this.fComment = (Comment) newValue;
          return true;
      }
      return false;
    }
    
    public void setUser(final User pUser) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fUser = pUser;
    }
    
    public void setComment(final Comment pComment) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fComment = pComment;
    }
    
    @Override
    public String patternName() {
      return "queries.likes";
    }
    
    @Override
    public List<String> parameterNames() {
      return Likes.Match.parameterNames;
    }
    
    @Override
    public Object[] toArray() {
      return new Object[]{fUser, fComment};
    }
    
    @Override
    public Likes.Match toImmutable() {
      return isMutable() ? newMatch(fUser, fComment) : this;
    }
    
    @Override
    public String prettyPrint() {
      StringBuilder result = new StringBuilder();
      result.append("\"user\"=" + prettyPrintValue(fUser) + ", ");
      result.append("\"comment\"=" + prettyPrintValue(fComment));
      return result.toString();
    }
    
    @Override
    public int hashCode() {
      return Objects.hash(fUser, fComment);
    }
    
    @Override
    public boolean equals(final Object obj) {
      if (this == obj)
          return true;
      if (obj == null) {
          return false;
      }
      if ((obj instanceof Likes.Match)) {
          Likes.Match other = (Likes.Match) obj;
          return Objects.equals(fUser, other.fUser) && Objects.equals(fComment, other.fComment);
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
    public Likes specification() {
      return Likes.instance();
    }
    
    /**
     * Returns an empty, mutable match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @return the empty match.
     * 
     */
    public static Likes.Match newEmptyMatch() {
      return new Mutable(null, null);
    }
    
    /**
     * Returns a mutable (partial) match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @return the new, mutable (partial) match object.
     * 
     */
    public static Likes.Match newMutableMatch(final User pUser, final Comment pComment) {
      return new Mutable(pUser, pComment);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public static Likes.Match newMatch(final User pUser, final Comment pComment) {
      return new Immutable(pUser, pComment);
    }
    
    private static final class Mutable extends Likes.Match {
      Mutable(final User pUser, final Comment pComment) {
        super(pUser, pComment);
      }
      
      @Override
      public boolean isMutable() {
        return true;
      }
    }
    
    private static final class Immutable extends Likes.Match {
      Immutable(final User pUser, final Comment pComment) {
        super(pUser, pComment);
      }
      
      @Override
      public boolean isMutable() {
        return false;
      }
    }
  }
  
  /**
   * Generated pattern matcher API of the queries.likes pattern,
   * providing pattern-specific query methods.
   * 
   * <p>Use the pattern matcher on a given model via {@link #on(ViatraQueryEngine)},
   * e.g. in conjunction with {@link ViatraQueryEngine#on(QueryScope)}.
   * 
   * <p>Matches of the pattern will be represented as {@link Match}.
   * 
   * <p>Original source:
   * <code><pre>
   * pattern likes(user: User, comment: Comment) {
   * 	User.likes(user, comment); 
   * }
   * </pre></code>
   * 
   * @see Match
   * @see Likes
   * 
   */
  public static class Matcher extends BaseMatcher<Likes.Match> {
    /**
     * Initializes the pattern matcher within an existing VIATRA Query engine.
     * If the pattern matcher is already constructed in the engine, only a light-weight reference is returned.
     * 
     * @param engine the existing VIATRA Query engine in which this matcher will be created.
     * @throws ViatraQueryRuntimeException if an error occurs during pattern matcher creation
     * 
     */
    public static Likes.Matcher on(final ViatraQueryEngine engine) {
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
    public static Likes.Matcher create() {
      return new Matcher();
    }
    
    private final static int POSITION_USER = 0;
    
    private final static int POSITION_COMMENT = 1;
    
    private final static Logger LOGGER = ViatraQueryLoggingUtil.getLogger(Likes.Matcher.class);
    
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
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @return matches represented as a Match object.
     * 
     */
    public Collection<Likes.Match> getAllMatches(final User pUser, final Comment pComment) {
      return rawStreamAllMatches(new Object[]{pUser, pComment}).collect(Collectors.toSet());
    }
    
    /**
     * Returns a stream of all matches of the pattern that conform to the given fixed values of some parameters.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @return a stream of matches represented as a Match object.
     * 
     */
    public Stream<Likes.Match> streamAllMatches(final User pUser, final Comment pComment) {
      return rawStreamAllMatches(new Object[]{pUser, pComment});
    }
    
    /**
     * Returns an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @return a match represented as a Match object, or null if no match is found.
     * 
     */
    public Optional<Likes.Match> getOneArbitraryMatch(final User pUser, final Comment pComment) {
      return rawGetOneArbitraryMatch(new Object[]{pUser, pComment});
    }
    
    /**
     * Indicates whether the given combination of specified pattern parameters constitute a valid pattern match,
     * under any possible substitution of the unspecified parameters (if any).
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @return true if the input is a valid (partial) match of the pattern.
     * 
     */
    public boolean hasMatch(final User pUser, final Comment pComment) {
      return rawHasMatch(new Object[]{pUser, pComment});
    }
    
    /**
     * Returns the number of all matches of the pattern that conform to the given fixed values of some parameters.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @return the number of pattern matches found.
     * 
     */
    public int countMatches(final User pUser, final Comment pComment) {
      return rawCountMatches(new Object[]{pUser, pComment});
    }
    
    /**
     * Executes the given processor on an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param processor the action that will process the selected match.
     * @return true if the pattern has at least one match with the given parameter values, false if the processor was not invoked
     * 
     */
    public boolean forOneArbitraryMatch(final User pUser, final Comment pComment, final Consumer<? super Likes.Match> processor) {
      return rawForOneArbitraryMatch(new Object[]{pUser, pComment}, processor);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pUser the fixed value of pattern parameter user, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public Likes.Match newMatch(final User pUser, final Comment pComment) {
      return Likes.Match.newMatch(pUser, pComment);
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
    public Stream<User> streamAllValuesOfuser(final Likes.Match partialMatch) {
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
    public Stream<User> streamAllValuesOfuser(final Comment pComment) {
      return rawStreamAllValuesOfuser(new Object[]{null, pComment});
    }
    
    /**
     * Retrieve the set of values that occur in matches for user.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<User> getAllValuesOfuser(final Likes.Match partialMatch) {
      return rawStreamAllValuesOfuser(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for user.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<User> getAllValuesOfuser(final Comment pComment) {
      return rawStreamAllValuesOfuser(new Object[]{null, pComment}).collect(Collectors.toSet());
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
    public Stream<Comment> streamAllValuesOfcomment(final Likes.Match partialMatch) {
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
    public Stream<Comment> streamAllValuesOfcomment(final User pUser) {
      return rawStreamAllValuesOfcomment(new Object[]{pUser, null});
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Comment> getAllValuesOfcomment(final Likes.Match partialMatch) {
      return rawStreamAllValuesOfcomment(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Comment> getAllValuesOfcomment(final User pUser) {
      return rawStreamAllValuesOfcomment(new Object[]{pUser, null}).collect(Collectors.toSet());
    }
    
    @Override
    protected Likes.Match tupleToMatch(final Tuple t) {
      try {
          return Likes.Match.newMatch((User) t.get(POSITION_USER), (Comment) t.get(POSITION_COMMENT));
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in tuple not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected Likes.Match arrayToMatch(final Object[] match) {
      try {
          return Likes.Match.newMatch((User) match[POSITION_USER], (Comment) match[POSITION_COMMENT]);
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in array not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected Likes.Match arrayToMatchMutable(final Object[] match) {
      try {
          return Likes.Match.newMutableMatch((User) match[POSITION_USER], (Comment) match[POSITION_COMMENT]);
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
    public static IQuerySpecification<Likes.Matcher> querySpecification() {
      return Likes.instance();
    }
  }
  
  private Likes() {
    super(GeneratedPQuery.INSTANCE);
  }
  
  /**
   * @return the singleton instance of the query specification
   * @throws ViatraQueryRuntimeException if the pattern definition could not be loaded
   * 
   */
  public static Likes instance() {
    try{
        return LazyHolder.INSTANCE;
    } catch (ExceptionInInitializerError err) {
        throw processInitializerError(err);
    }
  }
  
  @Override
  protected Likes.Matcher instantiate(final ViatraQueryEngine engine) {
    return Likes.Matcher.on(engine);
  }
  
  @Override
  public Likes.Matcher instantiate() {
    return Likes.Matcher.create();
  }
  
  @Override
  public Likes.Match newEmptyMatch() {
    return Likes.Match.newEmptyMatch();
  }
  
  @Override
  public Likes.Match newMatch(final Object... parameters) {
    return Likes.Match.newMatch((SocialNetwork.User) parameters[0], (SocialNetwork.Comment) parameters[1]);
  }
  
  /**
   * Inner class allowing the singleton instance of {@link JvmGenericType: queries.Likes (visibility: PUBLIC, simpleName: Likes, identifier: queries.Likes, deprecated: <unset>) (abstract: false, static: false, final: true, packageName: queries) (interface: false, strictFloatingPoint: false, anonymous: false)} to be created 
   *     <b>not</b> at the class load time of the outer class, 
   *     but rather at the first call to {@link JvmGenericType: queries.Likes (visibility: PUBLIC, simpleName: Likes, identifier: queries.Likes, deprecated: <unset>) (abstract: false, static: false, final: true, packageName: queries) (interface: false, strictFloatingPoint: false, anonymous: false)#instance()}.
   * 
   * <p> This workaround is required e.g. to support recursion.
   * 
   */
  private static class LazyHolder {
    private final static Likes INSTANCE = new Likes();
    
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
    private final static Likes.GeneratedPQuery INSTANCE = new GeneratedPQuery();
    
    private final PParameter parameter_user = new PParameter("user", "SocialNetwork.User", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("https://www.transformation-tool-contest.eu/2018/social_media", "User")), PParameterDirection.INOUT);
    
    private final PParameter parameter_comment = new PParameter("comment", "SocialNetwork.Comment", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")), PParameterDirection.INOUT);
    
    private final List<PParameter> parameters = Arrays.asList(parameter_user, parameter_comment);
    
    private GeneratedPQuery() {
      super(PVisibility.PUBLIC);
    }
    
    @Override
    public String getFullyQualifiedName() {
      return "queries.likes";
    }
    
    @Override
    public List<String> getParameterNames() {
      return Arrays.asList("user","comment");
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
          PVariable var_user = body.getOrCreateVariableByName("user");
          PVariable var_comment = body.getOrCreateVariableByName("comment");
          new TypeConstraint(body, Tuples.flatTupleOf(var_user), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "User")));
          new TypeConstraint(body, Tuples.flatTupleOf(var_comment), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")));
          body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
             new ExportedParameter(body, var_user, parameter_user),
             new ExportedParameter(body, var_comment, parameter_comment)
          ));
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
