/**
 * Generated from platform:/resource/EMFSolutionViatra/src/queries/ttc.vql
 */
package queries;

import SocialNetwork.Comment;
import SocialNetwork.Submission;
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
 *         pattern commented(submission: Submission, comment: Comment) {
 *         	Comment.commented(comment, submission);
 *         }
 * </pre></code>
 * 
 * @see Matcher
 * @see Match
 * 
 */
@SuppressWarnings("all")
public final class Commented extends BaseGeneratedEMFQuerySpecification<Commented.Matcher> {
  /**
   * Pattern-specific match representation of the queries.commented pattern,
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
    private Submission fSubmission;
    
    private Comment fComment;
    
    private static List<String> parameterNames = makeImmutableList("submission", "comment");
    
    private Match(final Submission pSubmission, final Comment pComment) {
      this.fSubmission = pSubmission;
      this.fComment = pComment;
    }
    
    @Override
    public Object get(final String parameterName) {
      if ("submission".equals(parameterName)) return this.fSubmission;
      if ("comment".equals(parameterName)) return this.fComment;
      return null;
    }
    
    public Submission getSubmission() {
      return this.fSubmission;
    }
    
    public Comment getComment() {
      return this.fComment;
    }
    
    @Override
    public boolean set(final String parameterName, final Object newValue) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      if ("submission".equals(parameterName) ) {
          this.fSubmission = (Submission) newValue;
          return true;
      }
      if ("comment".equals(parameterName) ) {
          this.fComment = (Comment) newValue;
          return true;
      }
      return false;
    }
    
    public void setSubmission(final Submission pSubmission) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fSubmission = pSubmission;
    }
    
    public void setComment(final Comment pComment) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fComment = pComment;
    }
    
    @Override
    public String patternName() {
      return "queries.commented";
    }
    
    @Override
    public List<String> parameterNames() {
      return Commented.Match.parameterNames;
    }
    
    @Override
    public Object[] toArray() {
      return new Object[]{fSubmission, fComment};
    }
    
    @Override
    public Commented.Match toImmutable() {
      return isMutable() ? newMatch(fSubmission, fComment) : this;
    }
    
    @Override
    public String prettyPrint() {
      StringBuilder result = new StringBuilder();
      result.append("\"submission\"=" + prettyPrintValue(fSubmission) + ", ");
      result.append("\"comment\"=" + prettyPrintValue(fComment));
      return result.toString();
    }
    
    @Override
    public int hashCode() {
      return Objects.hash(fSubmission, fComment);
    }
    
    @Override
    public boolean equals(final Object obj) {
      if (this == obj)
          return true;
      if (obj == null) {
          return false;
      }
      if ((obj instanceof Commented.Match)) {
          Commented.Match other = (Commented.Match) obj;
          return Objects.equals(fSubmission, other.fSubmission) && Objects.equals(fComment, other.fComment);
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
    public Commented specification() {
      return Commented.instance();
    }
    
    /**
     * Returns an empty, mutable match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @return the empty match.
     * 
     */
    public static Commented.Match newEmptyMatch() {
      return new Mutable(null, null);
    }
    
    /**
     * Returns a mutable (partial) match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @param pSubmission the fixed value of pattern parameter submission, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @return the new, mutable (partial) match object.
     * 
     */
    public static Commented.Match newMutableMatch(final Submission pSubmission, final Comment pComment) {
      return new Mutable(pSubmission, pComment);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pSubmission the fixed value of pattern parameter submission, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public static Commented.Match newMatch(final Submission pSubmission, final Comment pComment) {
      return new Immutable(pSubmission, pComment);
    }
    
    private static final class Mutable extends Commented.Match {
      Mutable(final Submission pSubmission, final Comment pComment) {
        super(pSubmission, pComment);
      }
      
      @Override
      public boolean isMutable() {
        return true;
      }
    }
    
    private static final class Immutable extends Commented.Match {
      Immutable(final Submission pSubmission, final Comment pComment) {
        super(pSubmission, pComment);
      }
      
      @Override
      public boolean isMutable() {
        return false;
      }
    }
  }
  
  /**
   * Generated pattern matcher API of the queries.commented pattern,
   * providing pattern-specific query methods.
   * 
   * <p>Use the pattern matcher on a given model via {@link #on(ViatraQueryEngine)},
   * e.g. in conjunction with {@link ViatraQueryEngine#on(QueryScope)}.
   * 
   * <p>Matches of the pattern will be represented as {@link Match}.
   * 
   * <p>Original source:
   * <code><pre>
   * pattern commented(submission: Submission, comment: Comment) {
   * 	Comment.commented(comment, submission);
   * }
   * </pre></code>
   * 
   * @see Match
   * @see Commented
   * 
   */
  public static class Matcher extends BaseMatcher<Commented.Match> {
    /**
     * Initializes the pattern matcher within an existing VIATRA Query engine.
     * If the pattern matcher is already constructed in the engine, only a light-weight reference is returned.
     * 
     * @param engine the existing VIATRA Query engine in which this matcher will be created.
     * @throws ViatraQueryRuntimeException if an error occurs during pattern matcher creation
     * 
     */
    public static Commented.Matcher on(final ViatraQueryEngine engine) {
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
    public static Commented.Matcher create() {
      return new Matcher();
    }
    
    private final static int POSITION_SUBMISSION = 0;
    
    private final static int POSITION_COMMENT = 1;
    
    private final static Logger LOGGER = ViatraQueryLoggingUtil.getLogger(Commented.Matcher.class);
    
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
     * @param pSubmission the fixed value of pattern parameter submission, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @return matches represented as a Match object.
     * 
     */
    public Collection<Commented.Match> getAllMatches(final Submission pSubmission, final Comment pComment) {
      return rawStreamAllMatches(new Object[]{pSubmission, pComment}).collect(Collectors.toSet());
    }
    
    /**
     * Returns a stream of all matches of the pattern that conform to the given fixed values of some parameters.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     * @param pSubmission the fixed value of pattern parameter submission, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @return a stream of matches represented as a Match object.
     * 
     */
    public Stream<Commented.Match> streamAllMatches(final Submission pSubmission, final Comment pComment) {
      return rawStreamAllMatches(new Object[]{pSubmission, pComment});
    }
    
    /**
     * Returns an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pSubmission the fixed value of pattern parameter submission, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @return a match represented as a Match object, or null if no match is found.
     * 
     */
    public Optional<Commented.Match> getOneArbitraryMatch(final Submission pSubmission, final Comment pComment) {
      return rawGetOneArbitraryMatch(new Object[]{pSubmission, pComment});
    }
    
    /**
     * Indicates whether the given combination of specified pattern parameters constitute a valid pattern match,
     * under any possible substitution of the unspecified parameters (if any).
     * @param pSubmission the fixed value of pattern parameter submission, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @return true if the input is a valid (partial) match of the pattern.
     * 
     */
    public boolean hasMatch(final Submission pSubmission, final Comment pComment) {
      return rawHasMatch(new Object[]{pSubmission, pComment});
    }
    
    /**
     * Returns the number of all matches of the pattern that conform to the given fixed values of some parameters.
     * @param pSubmission the fixed value of pattern parameter submission, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @return the number of pattern matches found.
     * 
     */
    public int countMatches(final Submission pSubmission, final Comment pComment) {
      return rawCountMatches(new Object[]{pSubmission, pComment});
    }
    
    /**
     * Executes the given processor on an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pSubmission the fixed value of pattern parameter submission, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param processor the action that will process the selected match.
     * @return true if the pattern has at least one match with the given parameter values, false if the processor was not invoked
     * 
     */
    public boolean forOneArbitraryMatch(final Submission pSubmission, final Comment pComment, final Consumer<? super Commented.Match> processor) {
      return rawForOneArbitraryMatch(new Object[]{pSubmission, pComment}, processor);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pSubmission the fixed value of pattern parameter submission, or null if not bound.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public Commented.Match newMatch(final Submission pSubmission, final Comment pComment) {
      return Commented.Match.newMatch(pSubmission, pComment);
    }
    
    /**
     * Retrieve the set of values that occur in matches for submission.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    protected Stream<Submission> rawStreamAllValuesOfsubmission(final Object[] parameters) {
      return rawStreamAllValues(POSITION_SUBMISSION, parameters).map(Submission.class::cast);
    }
    
    /**
     * Retrieve the set of values that occur in matches for submission.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Submission> getAllValuesOfsubmission() {
      return rawStreamAllValuesOfsubmission(emptyArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for submission.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Stream<Submission> streamAllValuesOfsubmission() {
      return rawStreamAllValuesOfsubmission(emptyArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for submission.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<Submission> streamAllValuesOfsubmission(final Commented.Match partialMatch) {
      return rawStreamAllValuesOfsubmission(partialMatch.toArray());
    }
    
    /**
     * Retrieve the set of values that occur in matches for submission.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     *      
     * @return the Stream of all values or empty set if there are no matches
     * 
     */
    public Stream<Submission> streamAllValuesOfsubmission(final Comment pComment) {
      return rawStreamAllValuesOfsubmission(new Object[]{null, pComment});
    }
    
    /**
     * Retrieve the set of values that occur in matches for submission.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Submission> getAllValuesOfsubmission(final Commented.Match partialMatch) {
      return rawStreamAllValuesOfsubmission(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for submission.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Submission> getAllValuesOfsubmission(final Comment pComment) {
      return rawStreamAllValuesOfsubmission(new Object[]{null, pComment}).collect(Collectors.toSet());
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
    public Stream<Comment> streamAllValuesOfcomment(final Commented.Match partialMatch) {
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
    public Stream<Comment> streamAllValuesOfcomment(final Submission pSubmission) {
      return rawStreamAllValuesOfcomment(new Object[]{pSubmission, null});
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Comment> getAllValuesOfcomment(final Commented.Match partialMatch) {
      return rawStreamAllValuesOfcomment(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Comment> getAllValuesOfcomment(final Submission pSubmission) {
      return rawStreamAllValuesOfcomment(new Object[]{pSubmission, null}).collect(Collectors.toSet());
    }
    
    @Override
    protected Commented.Match tupleToMatch(final Tuple t) {
      try {
          return Commented.Match.newMatch((Submission) t.get(POSITION_SUBMISSION), (Comment) t.get(POSITION_COMMENT));
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in tuple not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected Commented.Match arrayToMatch(final Object[] match) {
      try {
          return Commented.Match.newMatch((Submission) match[POSITION_SUBMISSION], (Comment) match[POSITION_COMMENT]);
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in array not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected Commented.Match arrayToMatchMutable(final Object[] match) {
      try {
          return Commented.Match.newMutableMatch((Submission) match[POSITION_SUBMISSION], (Comment) match[POSITION_COMMENT]);
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
    public static IQuerySpecification<Commented.Matcher> querySpecification() {
      return Commented.instance();
    }
  }
  
  private Commented() {
    super(GeneratedPQuery.INSTANCE);
  }
  
  /**
   * @return the singleton instance of the query specification
   * @throws ViatraQueryRuntimeException if the pattern definition could not be loaded
   * 
   */
  public static Commented instance() {
    try{
        return LazyHolder.INSTANCE;
    } catch (ExceptionInInitializerError err) {
        throw processInitializerError(err);
    }
  }
  
  @Override
  protected Commented.Matcher instantiate(final ViatraQueryEngine engine) {
    return Commented.Matcher.on(engine);
  }
  
  @Override
  public Commented.Matcher instantiate() {
    return Commented.Matcher.create();
  }
  
  @Override
  public Commented.Match newEmptyMatch() {
    return Commented.Match.newEmptyMatch();
  }
  
  @Override
  public Commented.Match newMatch(final Object... parameters) {
    return Commented.Match.newMatch((SocialNetwork.Submission) parameters[0], (SocialNetwork.Comment) parameters[1]);
  }
  
  /**
   * Inner class allowing the singleton instance of {@link JvmGenericType: queries.Commented (visibility: PUBLIC, simpleName: Commented, identifier: queries.Commented, deprecated: <unset>) (abstract: false, static: false, final: true, packageName: queries) (interface: false, strictFloatingPoint: false, anonymous: false)} to be created 
   *     <b>not</b> at the class load time of the outer class, 
   *     but rather at the first call to {@link JvmGenericType: queries.Commented (visibility: PUBLIC, simpleName: Commented, identifier: queries.Commented, deprecated: <unset>) (abstract: false, static: false, final: true, packageName: queries) (interface: false, strictFloatingPoint: false, anonymous: false)#instance()}.
   * 
   * <p> This workaround is required e.g. to support recursion.
   * 
   */
  private static class LazyHolder {
    private final static Commented INSTANCE = new Commented();
    
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
    private final static Commented.GeneratedPQuery INSTANCE = new GeneratedPQuery();
    
    private final PParameter parameter_submission = new PParameter("submission", "SocialNetwork.Submission", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("https://www.transformation-tool-contest.eu/2018/social_media", "Submission")), PParameterDirection.INOUT);
    
    private final PParameter parameter_comment = new PParameter("comment", "SocialNetwork.Comment", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")), PParameterDirection.INOUT);
    
    private final List<PParameter> parameters = Arrays.asList(parameter_submission, parameter_comment);
    
    private GeneratedPQuery() {
      super(PVisibility.PUBLIC);
    }
    
    @Override
    public String getFullyQualifiedName() {
      return "queries.commented";
    }
    
    @Override
    public List<String> getParameterNames() {
      return Arrays.asList("submission","comment");
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
          PVariable var_submission = body.getOrCreateVariableByName("submission");
          PVariable var_comment = body.getOrCreateVariableByName("comment");
          new TypeConstraint(body, Tuples.flatTupleOf(var_submission), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Submission")));
          new TypeConstraint(body, Tuples.flatTupleOf(var_comment), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")));
          body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
             new ExportedParameter(body, var_submission, parameter_submission),
             new ExportedParameter(body, var_comment, parameter_comment)
          ));
          // 	Comment.commented(comment, submission)
          new TypeConstraint(body, Tuples.flatTupleOf(var_comment), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")));
          PVariable var__virtual_0_ = body.getOrCreateVariableByName(".virtual{0}");
          new TypeConstraint(body, Tuples.flatTupleOf(var_comment, var__virtual_0_), new EStructuralFeatureInstancesKey(getFeatureLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Comment", "commented")));
          new TypeConstraint(body, Tuples.flatTupleOf(var__virtual_0_), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Submission")));
          new Equality(body, var__virtual_0_, var_submission);
          bodies.add(body);
      }
      return bodies;
    }
  }
}
