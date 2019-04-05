/**
 */
package SocialNetwork;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>User</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link SocialNetwork.User#getId <em>Id</em>}</li>
 *   <li>{@link SocialNetwork.User#getName <em>Name</em>}</li>
 *   <li>{@link SocialNetwork.User#getSubmissions <em>Submissions</em>}</li>
 *   <li>{@link SocialNetwork.User#getLikes <em>Likes</em>}</li>
 *   <li>{@link SocialNetwork.User#getFriends <em>Friends</em>}</li>
 * </ul>
 *
 * @see SocialNetwork.SocialNetworkPackage#getUser()
 * @model
 * @generated
 */
public interface User extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see SocialNetwork.SocialNetworkPackage#getUser_Id()
	 * @model unique="false" id="true" required="true" ordered="false"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link SocialNetwork.User#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see SocialNetwork.SocialNetworkPackage#getUser_Name()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link SocialNetwork.User#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Submissions</b></em>' reference list.
	 * The list contents are of type {@link SocialNetwork.Submission}.
	 * It is bidirectional and its opposite is '{@link SocialNetwork.Submission#getSubmitter <em>Submitter</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Submissions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Submissions</em>' reference list.
	 * @see SocialNetwork.SocialNetworkPackage#getUser_Submissions()
	 * @see SocialNetwork.Submission#getSubmitter
	 * @model opposite="submitter" ordered="false"
	 * @generated
	 */
	EList<Submission> getSubmissions();

	/**
	 * Returns the value of the '<em><b>Likes</b></em>' reference list.
	 * The list contents are of type {@link SocialNetwork.Comment}.
	 * It is bidirectional and its opposite is '{@link SocialNetwork.Comment#getLikedBy <em>Liked By</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Likes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Likes</em>' reference list.
	 * @see SocialNetwork.SocialNetworkPackage#getUser_Likes()
	 * @see SocialNetwork.Comment#getLikedBy
	 * @model opposite="likedBy" ordered="false"
	 * @generated
	 */
	EList<Comment> getLikes();

	/**
	 * Returns the value of the '<em><b>Friends</b></em>' reference list.
	 * The list contents are of type {@link SocialNetwork.User}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Friends</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Friends</em>' reference list.
	 * @see SocialNetwork.SocialNetworkPackage#getUser_Friends()
	 * @model ordered="false"
	 * @generated
	 */
	EList<User> getFriends();

} // User
