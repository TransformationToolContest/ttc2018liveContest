/**
 */
package SocialNetwork;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see SocialNetwork.SocialNetworkFactory
 * @model kind="package"
 * @generated
 */
public interface SocialNetworkPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "SocialNetwork";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "https://www.transformation-tool-contest.eu/2018/social_media";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "social";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SocialNetworkPackage eINSTANCE = SocialNetwork.impl.SocialNetworkPackageImpl.init();

	/**
	 * The meta object id for the '{@link SocialNetwork.impl.SubmissionImpl <em>Submission</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SocialNetwork.impl.SubmissionImpl
	 * @see SocialNetwork.impl.SocialNetworkPackageImpl#getSubmission()
	 * @generated
	 */
	int SUBMISSION = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBMISSION__ID = 0;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBMISSION__TIMESTAMP = 1;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBMISSION__CONTENT = 2;

	/**
	 * The feature id for the '<em><b>Submitter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBMISSION__SUBMITTER = 3;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBMISSION__COMMENTS = 4;

	/**
	 * The number of structural features of the '<em>Submission</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBMISSION_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Submission</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBMISSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link SocialNetwork.impl.PostImpl <em>Post</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SocialNetwork.impl.PostImpl
	 * @see SocialNetwork.impl.SocialNetworkPackageImpl#getPost()
	 * @generated
	 */
	int POST = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST__ID = SUBMISSION__ID;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST__TIMESTAMP = SUBMISSION__TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST__CONTENT = SUBMISSION__CONTENT;

	/**
	 * The feature id for the '<em><b>Submitter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST__SUBMITTER = SUBMISSION__SUBMITTER;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST__COMMENTS = SUBMISSION__COMMENTS;

	/**
	 * The number of structural features of the '<em>Post</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_FEATURE_COUNT = SUBMISSION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Post</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_OPERATION_COUNT = SUBMISSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link SocialNetwork.impl.CommentImpl <em>Comment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SocialNetwork.impl.CommentImpl
	 * @see SocialNetwork.impl.SocialNetworkPackageImpl#getComment()
	 * @generated
	 */
	int COMMENT = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__ID = SUBMISSION__ID;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__TIMESTAMP = SUBMISSION__TIMESTAMP;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__CONTENT = SUBMISSION__CONTENT;

	/**
	 * The feature id for the '<em><b>Submitter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__SUBMITTER = SUBMISSION__SUBMITTER;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__COMMENTS = SUBMISSION__COMMENTS;

	/**
	 * The feature id for the '<em><b>Commented</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__COMMENTED = SUBMISSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Liked By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__LIKED_BY = SUBMISSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Post</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__POST = SUBMISSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_FEATURE_COUNT = SUBMISSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_OPERATION_COUNT = SUBMISSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link SocialNetwork.impl.UserImpl <em>User</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SocialNetwork.impl.UserImpl
	 * @see SocialNetwork.impl.SocialNetworkPackageImpl#getUser()
	 * @generated
	 */
	int USER = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__NAME = 1;

	/**
	 * The feature id for the '<em><b>Submissions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__SUBMISSIONS = 2;

	/**
	 * The feature id for the '<em><b>Likes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__LIKES = 3;

	/**
	 * The feature id for the '<em><b>Friends</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__FRIENDS = 4;

	/**
	 * The number of structural features of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link SocialNetwork.impl.SocialNetworkRootImpl <em>Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SocialNetwork.impl.SocialNetworkRootImpl
	 * @see SocialNetwork.impl.SocialNetworkPackageImpl#getSocialNetworkRoot()
	 * @generated
	 */
	int SOCIAL_NETWORK_ROOT = 4;

	/**
	 * The feature id for the '<em><b>Posts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOCIAL_NETWORK_ROOT__POSTS = 0;

	/**
	 * The feature id for the '<em><b>Users</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOCIAL_NETWORK_ROOT__USERS = 1;

	/**
	 * The number of structural features of the '<em>Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOCIAL_NETWORK_ROOT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOCIAL_NETWORK_ROOT_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link SocialNetwork.Submission <em>Submission</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Submission</em>'.
	 * @see SocialNetwork.Submission
	 * @generated
	 */
	EClass getSubmission();

	/**
	 * Returns the meta object for the attribute '{@link SocialNetwork.Submission#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see SocialNetwork.Submission#getId()
	 * @see #getSubmission()
	 * @generated
	 */
	EAttribute getSubmission_Id();

	/**
	 * Returns the meta object for the attribute '{@link SocialNetwork.Submission#getTimestamp <em>Timestamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timestamp</em>'.
	 * @see SocialNetwork.Submission#getTimestamp()
	 * @see #getSubmission()
	 * @generated
	 */
	EAttribute getSubmission_Timestamp();

	/**
	 * Returns the meta object for the attribute '{@link SocialNetwork.Submission#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Content</em>'.
	 * @see SocialNetwork.Submission#getContent()
	 * @see #getSubmission()
	 * @generated
	 */
	EAttribute getSubmission_Content();

	/**
	 * Returns the meta object for the reference '{@link SocialNetwork.Submission#getSubmitter <em>Submitter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Submitter</em>'.
	 * @see SocialNetwork.Submission#getSubmitter()
	 * @see #getSubmission()
	 * @generated
	 */
	EReference getSubmission_Submitter();

	/**
	 * Returns the meta object for the containment reference list '{@link SocialNetwork.Submission#getComments <em>Comments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Comments</em>'.
	 * @see SocialNetwork.Submission#getComments()
	 * @see #getSubmission()
	 * @generated
	 */
	EReference getSubmission_Comments();

	/**
	 * Returns the meta object for class '{@link SocialNetwork.Post <em>Post</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Post</em>'.
	 * @see SocialNetwork.Post
	 * @generated
	 */
	EClass getPost();

	/**
	 * Returns the meta object for class '{@link SocialNetwork.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comment</em>'.
	 * @see SocialNetwork.Comment
	 * @generated
	 */
	EClass getComment();

	/**
	 * Returns the meta object for the container reference '{@link SocialNetwork.Comment#getCommented <em>Commented</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Commented</em>'.
	 * @see SocialNetwork.Comment#getCommented()
	 * @see #getComment()
	 * @generated
	 */
	EReference getComment_Commented();

	/**
	 * Returns the meta object for the reference list '{@link SocialNetwork.Comment#getLikedBy <em>Liked By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Liked By</em>'.
	 * @see SocialNetwork.Comment#getLikedBy()
	 * @see #getComment()
	 * @generated
	 */
	EReference getComment_LikedBy();

	/**
	 * Returns the meta object for the reference '{@link SocialNetwork.Comment#getPost <em>Post</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Post</em>'.
	 * @see SocialNetwork.Comment#getPost()
	 * @see #getComment()
	 * @generated
	 */
	EReference getComment_Post();

	/**
	 * Returns the meta object for class '{@link SocialNetwork.User <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User</em>'.
	 * @see SocialNetwork.User
	 * @generated
	 */
	EClass getUser();

	/**
	 * Returns the meta object for the attribute '{@link SocialNetwork.User#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see SocialNetwork.User#getId()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_Id();

	/**
	 * Returns the meta object for the attribute '{@link SocialNetwork.User#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see SocialNetwork.User#getName()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_Name();

	/**
	 * Returns the meta object for the reference list '{@link SocialNetwork.User#getSubmissions <em>Submissions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Submissions</em>'.
	 * @see SocialNetwork.User#getSubmissions()
	 * @see #getUser()
	 * @generated
	 */
	EReference getUser_Submissions();

	/**
	 * Returns the meta object for the reference list '{@link SocialNetwork.User#getLikes <em>Likes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Likes</em>'.
	 * @see SocialNetwork.User#getLikes()
	 * @see #getUser()
	 * @generated
	 */
	EReference getUser_Likes();

	/**
	 * Returns the meta object for the reference list '{@link SocialNetwork.User#getFriends <em>Friends</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Friends</em>'.
	 * @see SocialNetwork.User#getFriends()
	 * @see #getUser()
	 * @generated
	 */
	EReference getUser_Friends();

	/**
	 * Returns the meta object for class '{@link SocialNetwork.SocialNetworkRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Root</em>'.
	 * @see SocialNetwork.SocialNetworkRoot
	 * @generated
	 */
	EClass getSocialNetworkRoot();

	/**
	 * Returns the meta object for the containment reference list '{@link SocialNetwork.SocialNetworkRoot#getPosts <em>Posts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Posts</em>'.
	 * @see SocialNetwork.SocialNetworkRoot#getPosts()
	 * @see #getSocialNetworkRoot()
	 * @generated
	 */
	EReference getSocialNetworkRoot_Posts();

	/**
	 * Returns the meta object for the containment reference list '{@link SocialNetwork.SocialNetworkRoot#getUsers <em>Users</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Users</em>'.
	 * @see SocialNetwork.SocialNetworkRoot#getUsers()
	 * @see #getSocialNetworkRoot()
	 * @generated
	 */
	EReference getSocialNetworkRoot_Users();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SocialNetworkFactory getSocialNetworkFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link SocialNetwork.impl.SubmissionImpl <em>Submission</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SocialNetwork.impl.SubmissionImpl
		 * @see SocialNetwork.impl.SocialNetworkPackageImpl#getSubmission()
		 * @generated
		 */
		EClass SUBMISSION = eINSTANCE.getSubmission();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBMISSION__ID = eINSTANCE.getSubmission_Id();

		/**
		 * The meta object literal for the '<em><b>Timestamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBMISSION__TIMESTAMP = eINSTANCE.getSubmission_Timestamp();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBMISSION__CONTENT = eINSTANCE.getSubmission_Content();

		/**
		 * The meta object literal for the '<em><b>Submitter</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBMISSION__SUBMITTER = eINSTANCE.getSubmission_Submitter();

		/**
		 * The meta object literal for the '<em><b>Comments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBMISSION__COMMENTS = eINSTANCE.getSubmission_Comments();

		/**
		 * The meta object literal for the '{@link SocialNetwork.impl.PostImpl <em>Post</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SocialNetwork.impl.PostImpl
		 * @see SocialNetwork.impl.SocialNetworkPackageImpl#getPost()
		 * @generated
		 */
		EClass POST = eINSTANCE.getPost();

		/**
		 * The meta object literal for the '{@link SocialNetwork.impl.CommentImpl <em>Comment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SocialNetwork.impl.CommentImpl
		 * @see SocialNetwork.impl.SocialNetworkPackageImpl#getComment()
		 * @generated
		 */
		EClass COMMENT = eINSTANCE.getComment();

		/**
		 * The meta object literal for the '<em><b>Commented</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMENT__COMMENTED = eINSTANCE.getComment_Commented();

		/**
		 * The meta object literal for the '<em><b>Liked By</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMENT__LIKED_BY = eINSTANCE.getComment_LikedBy();

		/**
		 * The meta object literal for the '<em><b>Post</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMENT__POST = eINSTANCE.getComment_Post();

		/**
		 * The meta object literal for the '{@link SocialNetwork.impl.UserImpl <em>User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SocialNetwork.impl.UserImpl
		 * @see SocialNetwork.impl.SocialNetworkPackageImpl#getUser()
		 * @generated
		 */
		EClass USER = eINSTANCE.getUser();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__ID = eINSTANCE.getUser_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__NAME = eINSTANCE.getUser_Name();

		/**
		 * The meta object literal for the '<em><b>Submissions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER__SUBMISSIONS = eINSTANCE.getUser_Submissions();

		/**
		 * The meta object literal for the '<em><b>Likes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER__LIKES = eINSTANCE.getUser_Likes();

		/**
		 * The meta object literal for the '<em><b>Friends</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER__FRIENDS = eINSTANCE.getUser_Friends();

		/**
		 * The meta object literal for the '{@link SocialNetwork.impl.SocialNetworkRootImpl <em>Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SocialNetwork.impl.SocialNetworkRootImpl
		 * @see SocialNetwork.impl.SocialNetworkPackageImpl#getSocialNetworkRoot()
		 * @generated
		 */
		EClass SOCIAL_NETWORK_ROOT = eINSTANCE.getSocialNetworkRoot();

		/**
		 * The meta object literal for the '<em><b>Posts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOCIAL_NETWORK_ROOT__POSTS = eINSTANCE.getSocialNetworkRoot_Posts();

		/**
		 * The meta object literal for the '<em><b>Users</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOCIAL_NETWORK_ROOT__USERS = eINSTANCE.getSocialNetworkRoot_Users();

	}

} //SocialNetworkPackage
