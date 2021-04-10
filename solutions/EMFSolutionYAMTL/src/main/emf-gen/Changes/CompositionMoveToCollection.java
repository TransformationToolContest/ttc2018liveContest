/**
 */
package Changes;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composition Move To Collection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link Changes.CompositionMoveToCollection#getMovedElement <em>Moved Element</em>}</li>
 *   <li>{@link Changes.CompositionMoveToCollection#getOrigin <em>Origin</em>}</li>
 * </ul>
 *
 * @see Changes.ChangesPackage#getCompositionMoveToCollection()
 * @model
 * @generated
 */
public interface CompositionMoveToCollection extends EObject {
	/**
	 * Returns the value of the '<em><b>Moved Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Moved Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Moved Element</em>' reference.
	 * @see #setMovedElement(EObject)
	 * @see Changes.ChangesPackage#getCompositionMoveToCollection_MovedElement()
	 * @model required="true"
	 * @generated
	 */
	EObject getMovedElement();

	/**
	 * Sets the value of the '{@link Changes.CompositionMoveToCollection#getMovedElement <em>Moved Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Moved Element</em>' reference.
	 * @see #getMovedElement()
	 * @generated
	 */
	void setMovedElement(EObject value);

	/**
	 * Returns the value of the '<em><b>Origin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Origin</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Origin</em>' containment reference.
	 * @see #setOrigin(ElementaryChange)
	 * @see Changes.ChangesPackage#getCompositionMoveToCollection_Origin()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ElementaryChange getOrigin();

	/**
	 * Sets the value of the '{@link Changes.CompositionMoveToCollection#getOrigin <em>Origin</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Origin</em>' containment reference.
	 * @see #getOrigin()
	 * @generated
	 */
	void setOrigin(ElementaryChange value);

} // CompositionMoveToCollection
