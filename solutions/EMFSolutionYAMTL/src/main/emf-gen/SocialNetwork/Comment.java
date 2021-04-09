/**
 */
package SocialNetwork;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Comment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link SocialNetwork.Comment#getCommented <em>Commented</em>}</li>
 *   <li>{@link SocialNetwork.Comment#getLikedBy <em>Liked By</em>}</li>
 *   <li>{@link SocialNetwork.Comment#getPost <em>Post</em>}</li>
 * </ul>
 *
 * @see SocialNetwork.SocialNetworkPackage#getComment()
 * @model
 * @generated
 */
public interface Comment extends Submission {
	/**
	 * Returns the value of the '<em><b>Commented</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link SocialNetwork.Submission#getComments <em>Comments</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Commented</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commented</em>' container reference.
	 * @see SocialNetwork.SocialNetworkPackage#getComment_Commented()
	 * @see SocialNetwork.Submission#getComments
	 * @model opposite="comments" required="true" transient="false" changeable="false" ordered="false"
	 * @generated
	 */
	Submission getCommented();

	/**
	 * Returns the value of the '<em><b>Liked By</b></em>' reference list.
	 * The list contents are of type {@link SocialNetwork.User}.
	 * It is bidirectional and its opposite is '{@link SocialNetwork.User#getLikes <em>Likes</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Liked By</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Liked By</em>' reference list.
	 * @see SocialNetwork.SocialNetworkPackage#getComment_LikedBy()
	 * @see SocialNetwork.User#getLikes
	 * @model opposite="likes" ordered="false"
	 * @generated
	 */
	EList<User> getLikedBy();

	/**
	 * Returns the value of the '<em><b>Post</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Post</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Post</em>' reference.
	 * @see #setPost(Post)
	 * @see SocialNetwork.SocialNetworkPackage#getComment_Post()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Post getPost();

	/**
	 * Sets the value of the '{@link SocialNetwork.Comment#getPost <em>Post</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Post</em>' reference.
	 * @see #getPost()
	 * @generated
	 */
	void setPost(Post value);

} // Comment
