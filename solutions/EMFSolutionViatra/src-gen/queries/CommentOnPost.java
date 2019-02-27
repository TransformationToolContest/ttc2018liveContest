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

/**
 * A pattern-specific query specification that can instantiate Matcher in a type-safe way.
 * 
 * <p>Original source:
 *         <code><pre>
 *         pattern commentOnPost(comment: Comment, post: Post)
 *         // use this if Comment.post() is guaranteed to point to the root Post of the Submission tree 
 *         // Comment.post(comment, post);
 *         {
 *          	Post.comments(post, comment);
 *         } or {
 *         	find commentOnPost(parentComment, post);
 *         	Submission.comments(parentComment, comment);
 *         }
 * </pre></code>
 * 
 * @see Matcher
 * @see Match
 * 
 */
@SuppressWarnings("all")
public final class CommentOnPost extends BaseGeneratedEMFQuerySpecification<CommentOnPost.Matcher> {
  /**
   * Pattern-specific match representation of the queries.commentOnPost pattern,
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
    
    private Post fPost;
    
    private static List<String> parameterNames = makeImmutableList("comment", "post");
    
    private Match(final Comment pComment, final Post pPost) {
      this.fComment = pComment;
      this.fPost = pPost;
    }
    
    @Override
    public Object get(final String parameterName) {
      if ("comment".equals(parameterName)) return this.fComment;
      if ("post".equals(parameterName)) return this.fPost;
      return null;
    }
    
    public Comment getComment() {
      return this.fComment;
    }
    
    public Post getPost() {
      return this.fPost;
    }
    
    @Override
    public boolean set(final String parameterName, final Object newValue) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      if ("comment".equals(parameterName) ) {
          this.fComment = (Comment) newValue;
          return true;
      }
      if ("post".equals(parameterName) ) {
          this.fPost = (Post) newValue;
          return true;
      }
      return false;
    }
    
    public void setComment(final Comment pComment) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fComment = pComment;
    }
    
    public void setPost(final Post pPost) {
      if (!isMutable()) throw new java.lang.UnsupportedOperationException();
      this.fPost = pPost;
    }
    
    @Override
    public String patternName() {
      return "queries.commentOnPost";
    }
    
    @Override
    public List<String> parameterNames() {
      return CommentOnPost.Match.parameterNames;
    }
    
    @Override
    public Object[] toArray() {
      return new Object[]{fComment, fPost};
    }
    
    @Override
    public CommentOnPost.Match toImmutable() {
      return isMutable() ? newMatch(fComment, fPost) : this;
    }
    
    @Override
    public String prettyPrint() {
      StringBuilder result = new StringBuilder();
      result.append("\"comment\"=" + prettyPrintValue(fComment) + ", ");
      result.append("\"post\"=" + prettyPrintValue(fPost));
      return result.toString();
    }
    
    @Override
    public int hashCode() {
      return Objects.hash(fComment, fPost);
    }
    
    @Override
    public boolean equals(final Object obj) {
      if (this == obj)
          return true;
      if (obj == null) {
          return false;
      }
      if ((obj instanceof CommentOnPost.Match)) {
          CommentOnPost.Match other = (CommentOnPost.Match) obj;
          return Objects.equals(fComment, other.fComment) && Objects.equals(fPost, other.fPost);
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
    public CommentOnPost specification() {
      return CommentOnPost.instance();
    }
    
    /**
     * Returns an empty, mutable match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @return the empty match.
     * 
     */
    public static CommentOnPost.Match newEmptyMatch() {
      return new Mutable(null, null);
    }
    
    /**
     * Returns a mutable (partial) match.
     * Fields of the mutable match can be filled to create a partial match, usable as matcher input.
     * 
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @return the new, mutable (partial) match object.
     * 
     */
    public static CommentOnPost.Match newMutableMatch(final Comment pComment, final Post pPost) {
      return new Mutable(pComment, pPost);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public static CommentOnPost.Match newMatch(final Comment pComment, final Post pPost) {
      return new Immutable(pComment, pPost);
    }
    
    private static final class Mutable extends CommentOnPost.Match {
      Mutable(final Comment pComment, final Post pPost) {
        super(pComment, pPost);
      }
      
      @Override
      public boolean isMutable() {
        return true;
      }
    }
    
    private static final class Immutable extends CommentOnPost.Match {
      Immutable(final Comment pComment, final Post pPost) {
        super(pComment, pPost);
      }
      
      @Override
      public boolean isMutable() {
        return false;
      }
    }
  }
  
  /**
   * Generated pattern matcher API of the queries.commentOnPost pattern,
   * providing pattern-specific query methods.
   * 
   * <p>Use the pattern matcher on a given model via {@link #on(ViatraQueryEngine)},
   * e.g. in conjunction with {@link ViatraQueryEngine#on(QueryScope)}.
   * 
   * <p>Matches of the pattern will be represented as {@link Match}.
   * 
   * <p>Original source:
   * <code><pre>
   * pattern commentOnPost(comment: Comment, post: Post)
   * // use this if Comment.post() is guaranteed to point to the root Post of the Submission tree 
   * // Comment.post(comment, post);
   * {
   *  	Post.comments(post, comment);
   * } or {
   * 	find commentOnPost(parentComment, post);
   * 	Submission.comments(parentComment, comment);
   * }
   * </pre></code>
   * 
   * @see Match
   * @see CommentOnPost
   * 
   */
  public static class Matcher extends BaseMatcher<CommentOnPost.Match> {
    /**
     * Initializes the pattern matcher within an existing VIATRA Query engine.
     * If the pattern matcher is already constructed in the engine, only a light-weight reference is returned.
     * 
     * @param engine the existing VIATRA Query engine in which this matcher will be created.
     * @throws ViatraQueryRuntimeException if an error occurs during pattern matcher creation
     * 
     */
    public static CommentOnPost.Matcher on(final ViatraQueryEngine engine) {
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
    public static CommentOnPost.Matcher create() {
      return new Matcher();
    }
    
    private final static int POSITION_COMMENT = 0;
    
    private final static int POSITION_POST = 1;
    
    private final static Logger LOGGER = ViatraQueryLoggingUtil.getLogger(CommentOnPost.Matcher.class);
    
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
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @return matches represented as a Match object.
     * 
     */
    public Collection<CommentOnPost.Match> getAllMatches(final Comment pComment, final Post pPost) {
      return rawStreamAllMatches(new Object[]{pComment, pPost}).collect(Collectors.toSet());
    }
    
    /**
     * Returns a stream of all matches of the pattern that conform to the given fixed values of some parameters.
     * </p>
     * <strong>NOTE</strong>: It is important not to modify the source model while the stream is being processed.
     * If the match set of the pattern changes during processing, the contents of the stream is <strong>undefined</strong>.
     * In such cases, either rely on {@link #getAllMatches()} or collect the results of the stream in end-user code.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @return a stream of matches represented as a Match object.
     * 
     */
    public Stream<CommentOnPost.Match> streamAllMatches(final Comment pComment, final Post pPost) {
      return rawStreamAllMatches(new Object[]{pComment, pPost});
    }
    
    /**
     * Returns an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @return a match represented as a Match object, or null if no match is found.
     * 
     */
    public Optional<CommentOnPost.Match> getOneArbitraryMatch(final Comment pComment, final Post pPost) {
      return rawGetOneArbitraryMatch(new Object[]{pComment, pPost});
    }
    
    /**
     * Indicates whether the given combination of specified pattern parameters constitute a valid pattern match,
     * under any possible substitution of the unspecified parameters (if any).
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @return true if the input is a valid (partial) match of the pattern.
     * 
     */
    public boolean hasMatch(final Comment pComment, final Post pPost) {
      return rawHasMatch(new Object[]{pComment, pPost});
    }
    
    /**
     * Returns the number of all matches of the pattern that conform to the given fixed values of some parameters.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @return the number of pattern matches found.
     * 
     */
    public int countMatches(final Comment pComment, final Post pPost) {
      return rawCountMatches(new Object[]{pComment, pPost});
    }
    
    /**
     * Executes the given processor on an arbitrarily chosen match of the pattern that conforms to the given fixed values of some parameters.
     * Neither determinism nor randomness of selection is guaranteed.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @param processor the action that will process the selected match.
     * @return true if the pattern has at least one match with the given parameter values, false if the processor was not invoked
     * 
     */
    public boolean forOneArbitraryMatch(final Comment pComment, final Post pPost, final Consumer<? super CommentOnPost.Match> processor) {
      return rawForOneArbitraryMatch(new Object[]{pComment, pPost}, processor);
    }
    
    /**
     * Returns a new (partial) match.
     * This can be used e.g. to call the matcher with a partial match.
     * <p>The returned match will be immutable. Use {@link #newEmptyMatch()} to obtain a mutable match object.
     * @param pComment the fixed value of pattern parameter comment, or null if not bound.
     * @param pPost the fixed value of pattern parameter post, or null if not bound.
     * @return the (partial) match object.
     * 
     */
    public CommentOnPost.Match newMatch(final Comment pComment, final Post pPost) {
      return CommentOnPost.Match.newMatch(pComment, pPost);
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
    public Stream<Comment> streamAllValuesOfcomment(final CommentOnPost.Match partialMatch) {
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
    public Stream<Comment> streamAllValuesOfcomment(final Post pPost) {
      return rawStreamAllValuesOfcomment(new Object[]{null, pPost});
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Comment> getAllValuesOfcomment(final CommentOnPost.Match partialMatch) {
      return rawStreamAllValuesOfcomment(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for comment.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Comment> getAllValuesOfcomment(final Post pPost) {
      return rawStreamAllValuesOfcomment(new Object[]{null, pPost}).collect(Collectors.toSet());
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
    public Stream<Post> streamAllValuesOfpost(final CommentOnPost.Match partialMatch) {
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
    public Stream<Post> streamAllValuesOfpost(final Comment pComment) {
      return rawStreamAllValuesOfpost(new Object[]{pComment, null});
    }
    
    /**
     * Retrieve the set of values that occur in matches for post.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Post> getAllValuesOfpost(final CommentOnPost.Match partialMatch) {
      return rawStreamAllValuesOfpost(partialMatch.toArray()).collect(Collectors.toSet());
    }
    
    /**
     * Retrieve the set of values that occur in matches for post.
     * @return the Set of all values or empty set if there are no matches
     * 
     */
    public Set<Post> getAllValuesOfpost(final Comment pComment) {
      return rawStreamAllValuesOfpost(new Object[]{pComment, null}).collect(Collectors.toSet());
    }
    
    @Override
    protected CommentOnPost.Match tupleToMatch(final Tuple t) {
      try {
          return CommentOnPost.Match.newMatch((Comment) t.get(POSITION_COMMENT), (Post) t.get(POSITION_POST));
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in tuple not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected CommentOnPost.Match arrayToMatch(final Object[] match) {
      try {
          return CommentOnPost.Match.newMatch((Comment) match[POSITION_COMMENT], (Post) match[POSITION_POST]);
      } catch(ClassCastException e) {
          LOGGER.error("Element(s) in array not properly typed!",e);
          return null;
      }
    }
    
    @Override
    protected CommentOnPost.Match arrayToMatchMutable(final Object[] match) {
      try {
          return CommentOnPost.Match.newMutableMatch((Comment) match[POSITION_COMMENT], (Post) match[POSITION_POST]);
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
    public static IQuerySpecification<CommentOnPost.Matcher> querySpecification() {
      return CommentOnPost.instance();
    }
  }
  
  private CommentOnPost() {
    super(GeneratedPQuery.INSTANCE);
  }
  
  /**
   * @return the singleton instance of the query specification
   * @throws ViatraQueryRuntimeException if the pattern definition could not be loaded
   * 
   */
  public static CommentOnPost instance() {
    try{
        return LazyHolder.INSTANCE;
    } catch (ExceptionInInitializerError err) {
        throw processInitializerError(err);
    }
  }
  
  @Override
  protected CommentOnPost.Matcher instantiate(final ViatraQueryEngine engine) {
    return CommentOnPost.Matcher.on(engine);
  }
  
  @Override
  public CommentOnPost.Matcher instantiate() {
    return CommentOnPost.Matcher.create();
  }
  
  @Override
  public CommentOnPost.Match newEmptyMatch() {
    return CommentOnPost.Match.newEmptyMatch();
  }
  
  @Override
  public CommentOnPost.Match newMatch(final Object... parameters) {
    return CommentOnPost.Match.newMatch((SocialNetwork.Comment) parameters[0], (SocialNetwork.Post) parameters[1]);
  }
  
  /**
   * Inner class allowing the singleton instance of {@link JvmGenericType: queries.CommentOnPost (visibility: PUBLIC, simpleName: CommentOnPost, identifier: queries.CommentOnPost, deprecated: <unset>) (abstract: false, static: false, final: true, packageName: queries) (interface: false, strictFloatingPoint: false, anonymous: false)} to be created 
   *     <b>not</b> at the class load time of the outer class, 
   *     but rather at the first call to {@link JvmGenericType: queries.CommentOnPost (visibility: PUBLIC, simpleName: CommentOnPost, identifier: queries.CommentOnPost, deprecated: <unset>) (abstract: false, static: false, final: true, packageName: queries) (interface: false, strictFloatingPoint: false, anonymous: false)#instance()}.
   * 
   * <p> This workaround is required e.g. to support recursion.
   * 
   */
  private static class LazyHolder {
    private final static CommentOnPost INSTANCE = new CommentOnPost();
    
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
    private final static CommentOnPost.GeneratedPQuery INSTANCE = new GeneratedPQuery();
    
    private final PParameter parameter_comment = new PParameter("comment", "SocialNetwork.Comment", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")), PParameterDirection.INOUT);
    
    private final PParameter parameter_post = new PParameter("post", "SocialNetwork.Post", new EClassTransitiveInstancesKey((EClass)getClassifierLiteralSafe("https://www.transformation-tool-contest.eu/2018/social_media", "Post")), PParameterDirection.INOUT);
    
    private final List<PParameter> parameters = Arrays.asList(parameter_comment, parameter_post);
    
    private GeneratedPQuery() {
      super(PVisibility.PUBLIC);
    }
    
    @Override
    public String getFullyQualifiedName() {
      return "queries.commentOnPost";
    }
    
    @Override
    public List<String> getParameterNames() {
      return Arrays.asList("comment","post");
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
          PVariable var_post = body.getOrCreateVariableByName("post");
          new TypeConstraint(body, Tuples.flatTupleOf(var_comment), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")));
          new TypeConstraint(body, Tuples.flatTupleOf(var_post), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Post")));
          body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
             new ExportedParameter(body, var_comment, parameter_comment),
             new ExportedParameter(body, var_post, parameter_post)
          ));
          //  	Post.comments(post, comment)
          new TypeConstraint(body, Tuples.flatTupleOf(var_post), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Post")));
          PVariable var__virtual_0_ = body.getOrCreateVariableByName(".virtual{0}");
          new TypeConstraint(body, Tuples.flatTupleOf(var_post, var__virtual_0_), new EStructuralFeatureInstancesKey(getFeatureLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Submission", "comments")));
          new TypeConstraint(body, Tuples.flatTupleOf(var__virtual_0_), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")));
          new Equality(body, var__virtual_0_, var_comment);
          bodies.add(body);
      }
      {
          PBody body = new PBody(this);
          PVariable var_comment = body.getOrCreateVariableByName("comment");
          PVariable var_post = body.getOrCreateVariableByName("post");
          PVariable var_parentComment = body.getOrCreateVariableByName("parentComment");
          new TypeConstraint(body, Tuples.flatTupleOf(var_comment), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")));
          new TypeConstraint(body, Tuples.flatTupleOf(var_post), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Post")));
          body.setSymbolicParameters(Arrays.<ExportedParameter>asList(
             new ExportedParameter(body, var_comment, parameter_comment),
             new ExportedParameter(body, var_post, parameter_post)
          ));
          // 	find commentOnPost(parentComment, post)
          new PositivePatternCall(body, Tuples.flatTupleOf(var_parentComment, var_post), this);
          // 	Submission.comments(parentComment, comment)
          new TypeConstraint(body, Tuples.flatTupleOf(var_parentComment), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Submission")));
          PVariable var__virtual_0_ = body.getOrCreateVariableByName(".virtual{0}");
          new TypeConstraint(body, Tuples.flatTupleOf(var_parentComment, var__virtual_0_), new EStructuralFeatureInstancesKey(getFeatureLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Submission", "comments")));
          new TypeConstraint(body, Tuples.flatTupleOf(var__virtual_0_), new EClassTransitiveInstancesKey((EClass)getClassifierLiteral("https://www.transformation-tool-contest.eu/2018/social_media", "Comment")));
          new Equality(body, var__virtual_0_, var_comment);
          bodies.add(body);
      }
      return bodies;
    }
  }
}
