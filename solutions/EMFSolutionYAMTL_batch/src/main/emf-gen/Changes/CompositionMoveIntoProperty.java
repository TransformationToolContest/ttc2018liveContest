/**
 */
package Changes;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composition Move Into Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link Changes.CompositionMoveIntoProperty#getNewValue <em>New Value</em>}</li>
 *   <li>{@link Changes.CompositionMoveIntoProperty#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link Changes.CompositionMoveIntoProperty#getOrigin <em>Origin</em>}</li>
 * </ul>
 *
 * @see Changes.ChangesPackage#getCompositionMoveIntoProperty()
 * @model
 * @generated
 */
public interface CompositionMoveIntoProperty extends CompositionChange {
	/**
	 * Returns the value of the '<em><b>New Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>New Value</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New Value</em>' reference.
	 * @see #setNewValue(EObject)
	 * @see Changes.ChangesPackage#getCompositionMoveIntoProperty_NewValue()
	 * @model required="true"
	 * @generated
	 */
	EObject getNewValue();

	/**
	 * Sets the value of the '{@link Changes.CompositionMoveIntoProperty#getNewValue <em>New Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New Value</em>' reference.
	 * @see #getNewValue()
	 * @generated
	 */
	void setNewValue(EObject value);

	/**
	 * Returns the value of the '<em><b>Old Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Old Value</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Old Value</em>' reference.
	 * @see #setOldValue(EObject)
	 * @see Changes.ChangesPackage#getCompositionMoveIntoProperty_OldValue()
	 * @model
	 * @generated
	 */
	EObject getOldValue();

	/**
	 * Sets the value of the '{@link Changes.CompositionMoveIntoProperty#getOldValue <em>Old Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Old Value</em>' reference.
	 * @see #getOldValue()
	 * @generated
	 */
	void setOldValue(EObject value);

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
	 * @see Changes.ChangesPackage#getCompositionMoveIntoProperty_Origin()
	 * @model containment="true"
	 * @generated
	 */
	ElementaryChange getOrigin();

	/**
	 * Sets the value of the '{@link Changes.CompositionMoveIntoProperty#getOrigin <em>Origin</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Origin</em>' containment reference.
	 * @see #getOrigin()
	 * @generated
	 */
	void setOrigin(ElementaryChange value);

} // CompositionMoveIntoProperty
