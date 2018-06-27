/**
 */
package SocialNetwork;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Submission</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link SocialNetwork.Submission#getId <em>Id</em>}</li>
 *   <li>{@link SocialNetwork.Submission#getTimestamp <em>Timestamp</em>}</li>
 *   <li>{@link SocialNetwork.Submission#getContent <em>Content</em>}</li>
 *   <li>{@link SocialNetwork.Submission#getSubmitter <em>Submitter</em>}</li>
 *   <li>{@link SocialNetwork.Submission#getComments <em>Comments</em>}</li>
 * </ul>
 *
 * @see SocialNetwork.SocialNetworkPackage#getSubmission()
 * @model abstract="true"
 * @generated
 */
public interface Submission extends EObject {
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
	 * @see SocialNetwork.SocialNetworkPackage#getSubmission_Id()
	 * @model unique="false" id="true" required="true" ordered="false"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link SocialNetwork.Submission#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timestamp</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timestamp</em>' attribute.
	 * @see #setTimestamp(Date)
	 * @see SocialNetwork.SocialNetworkPackage#getSubmission_Timestamp()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	Date getTimestamp();

	/**
	 * Sets the value of the '{@link SocialNetwork.Submission#getTimestamp <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timestamp</em>' attribute.
	 * @see #getTimestamp()
	 * @generated
	 */
	void setTimestamp(Date value);

	/**
	 * Returns the value of the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Content</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content</em>' attribute.
	 * @see #setContent(String)
	 * @see SocialNetwork.SocialNetworkPackage#getSubmission_Content()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	String getContent();

	/**
	 * Sets the value of the '{@link SocialNetwork.Submission#getContent <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Content</em>' attribute.
	 * @see #getContent()
	 * @generated
	 */
	void setContent(String value);

	/**
	 * Returns the value of the '<em><b>Submitter</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link SocialNetwork.User#getSubmissions <em>Submissions</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Submitter</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Submitter</em>' reference.
	 * @see #setSubmitter(User)
	 * @see SocialNetwork.SocialNetworkPackage#getSubmission_Submitter()
	 * @see SocialNetwork.User#getSubmissions
	 * @model opposite="submissions" required="true" ordered="false"
	 * @generated
	 */
	User getSubmitter();

	/**
	 * Sets the value of the '{@link SocialNetwork.Submission#getSubmitter <em>Submitter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Submitter</em>' reference.
	 * @see #getSubmitter()
	 * @generated
	 */
	void setSubmitter(User value);

	/**
	 * Returns the value of the '<em><b>Comments</b></em>' containment reference list.
	 * The list contents are of type {@link SocialNetwork.Comment}.
	 * It is bidirectional and its opposite is '{@link SocialNetwork.Comment#getCommented <em>Commented</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comments</em>' containment reference list.
	 * @see SocialNetwork.SocialNetworkPackage#getSubmission_Comments()
	 * @see SocialNetwork.Comment#getCommented
	 * @model opposite="commented" containment="true" ordered="false"
	 * @generated
	 */
	EList<Comment> getComments();

} // Submission
