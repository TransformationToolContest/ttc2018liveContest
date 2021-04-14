/**
 */
package SocialNetwork;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link SocialNetwork.SocialNetworkRoot#getPosts <em>Posts</em>}</li>
 *   <li>{@link SocialNetwork.SocialNetworkRoot#getUsers <em>Users</em>}</li>
 * </ul>
 *
 * @see SocialNetwork.SocialNetworkPackage#getSocialNetworkRoot()
 * @model
 * @generated
 */
public interface SocialNetworkRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Posts</b></em>' containment reference list.
	 * The list contents are of type {@link SocialNetwork.Post}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Posts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Posts</em>' containment reference list.
	 * @see SocialNetwork.SocialNetworkPackage#getSocialNetworkRoot_Posts()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Post> getPosts();

	/**
	 * Returns the value of the '<em><b>Users</b></em>' containment reference list.
	 * The list contents are of type {@link SocialNetwork.User}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Users</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Users</em>' containment reference list.
	 * @see SocialNetwork.SocialNetworkPackage#getSocialNetworkRoot_Users()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<User> getUsers();

} // SocialNetworkRoot
