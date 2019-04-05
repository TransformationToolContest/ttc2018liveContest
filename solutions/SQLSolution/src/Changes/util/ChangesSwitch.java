/**
 */
package Changes.util;

import Changes.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see Changes.ChangesPackage
 * @generated
 */
public class ChangesSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ChangesPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangesSwitch() {
		if (modelPackage == null) {
			modelPackage = ChangesPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case ChangesPackage.MODEL_CHANGE_SET: {
				ModelChangeSet modelChangeSet = (ModelChangeSet)theEObject;
				T result = caseModelChangeSet(modelChangeSet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.MODEL_CHANGE: {
				ModelChange modelChange = (ModelChange)theEObject;
				T result = caseModelChange(modelChange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.ELEMENTARY_CHANGE: {
				ElementaryChange elementaryChange = (ElementaryChange)theEObject;
				T result = caseElementaryChange(elementaryChange);
				if (result == null) result = caseModelChange(elementaryChange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.CHANGE_TRANSACTION: {
				ChangeTransaction changeTransaction = (ChangeTransaction)theEObject;
				T result = caseChangeTransaction(changeTransaction);
				if (result == null) result = caseModelChange(changeTransaction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.COMPOSITION_CHANGE: {
				CompositionChange compositionChange = (CompositionChange)theEObject;
				T result = caseCompositionChange(compositionChange);
				if (result == null) result = caseElementaryChange(compositionChange);
				if (result == null) result = caseModelChange(compositionChange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.ASSOCIATION_CHANGE: {
				AssociationChange associationChange = (AssociationChange)theEObject;
				T result = caseAssociationChange(associationChange);
				if (result == null) result = caseElementaryChange(associationChange);
				if (result == null) result = caseModelChange(associationChange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.ATTRIBUTE_CHANGE: {
				AttributeChange attributeChange = (AttributeChange)theEObject;
				T result = caseAttributeChange(attributeChange);
				if (result == null) result = caseElementaryChange(attributeChange);
				if (result == null) result = caseModelChange(attributeChange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.ASSOCIATION_COLLECTION_DELETION: {
				AssociationCollectionDeletion associationCollectionDeletion = (AssociationCollectionDeletion)theEObject;
				T result = caseAssociationCollectionDeletion(associationCollectionDeletion);
				if (result == null) result = caseAssociationChange(associationCollectionDeletion);
				if (result == null) result = caseElementaryChange(associationCollectionDeletion);
				if (result == null) result = caseModelChange(associationCollectionDeletion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.COMPOSITION_COLLECTION_DELETION: {
				CompositionCollectionDeletion compositionCollectionDeletion = (CompositionCollectionDeletion)theEObject;
				T result = caseCompositionCollectionDeletion(compositionCollectionDeletion);
				if (result == null) result = caseCompositionChange(compositionCollectionDeletion);
				if (result == null) result = caseElementaryChange(compositionCollectionDeletion);
				if (result == null) result = caseModelChange(compositionCollectionDeletion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.ATTRIBUTE_COLLECTION_DELETION: {
				AttributeCollectionDeletion attributeCollectionDeletion = (AttributeCollectionDeletion)theEObject;
				T result = caseAttributeCollectionDeletion(attributeCollectionDeletion);
				if (result == null) result = caseAttributeChange(attributeCollectionDeletion);
				if (result == null) result = caseElementaryChange(attributeCollectionDeletion);
				if (result == null) result = caseModelChange(attributeCollectionDeletion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.ASSOCIATION_COLLECTION_INSERTION: {
				AssociationCollectionInsertion associationCollectionInsertion = (AssociationCollectionInsertion)theEObject;
				T result = caseAssociationCollectionInsertion(associationCollectionInsertion);
				if (result == null) result = caseAssociationChange(associationCollectionInsertion);
				if (result == null) result = caseElementaryChange(associationCollectionInsertion);
				if (result == null) result = caseModelChange(associationCollectionInsertion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.COMPOSITION_COLLECTION_INSERTION: {
				CompositionCollectionInsertion compositionCollectionInsertion = (CompositionCollectionInsertion)theEObject;
				T result = caseCompositionCollectionInsertion(compositionCollectionInsertion);
				if (result == null) result = caseCompositionChange(compositionCollectionInsertion);
				if (result == null) result = caseElementaryChange(compositionCollectionInsertion);
				if (result == null) result = caseModelChange(compositionCollectionInsertion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.ATTRIBUTE_COLLECTION_INSERTION: {
				AttributeCollectionInsertion attributeCollectionInsertion = (AttributeCollectionInsertion)theEObject;
				T result = caseAttributeCollectionInsertion(attributeCollectionInsertion);
				if (result == null) result = caseAttributeChange(attributeCollectionInsertion);
				if (result == null) result = caseElementaryChange(attributeCollectionInsertion);
				if (result == null) result = caseModelChange(attributeCollectionInsertion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.ASSOCIATION_COLLECTION_RESET: {
				AssociationCollectionReset associationCollectionReset = (AssociationCollectionReset)theEObject;
				T result = caseAssociationCollectionReset(associationCollectionReset);
				if (result == null) result = caseAssociationChange(associationCollectionReset);
				if (result == null) result = caseElementaryChange(associationCollectionReset);
				if (result == null) result = caseModelChange(associationCollectionReset);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.COMPOSITION_COLLECTION_RESET: {
				CompositionCollectionReset compositionCollectionReset = (CompositionCollectionReset)theEObject;
				T result = caseCompositionCollectionReset(compositionCollectionReset);
				if (result == null) result = caseCompositionChange(compositionCollectionReset);
				if (result == null) result = caseElementaryChange(compositionCollectionReset);
				if (result == null) result = caseModelChange(compositionCollectionReset);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.ATTRIBUTE_COLLECTION_RESET: {
				AttributeCollectionReset attributeCollectionReset = (AttributeCollectionReset)theEObject;
				T result = caseAttributeCollectionReset(attributeCollectionReset);
				if (result == null) result = caseAttributeChange(attributeCollectionReset);
				if (result == null) result = caseElementaryChange(attributeCollectionReset);
				if (result == null) result = caseModelChange(attributeCollectionReset);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.ASSOCIATION_LIST_DELETION: {
				AssociationListDeletion associationListDeletion = (AssociationListDeletion)theEObject;
				T result = caseAssociationListDeletion(associationListDeletion);
				if (result == null) result = caseAssociationChange(associationListDeletion);
				if (result == null) result = caseElementaryChange(associationListDeletion);
				if (result == null) result = caseModelChange(associationListDeletion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.COMPOSITION_LIST_DELETION: {
				CompositionListDeletion compositionListDeletion = (CompositionListDeletion)theEObject;
				T result = caseCompositionListDeletion(compositionListDeletion);
				if (result == null) result = caseCompositionChange(compositionListDeletion);
				if (result == null) result = caseElementaryChange(compositionListDeletion);
				if (result == null) result = caseModelChange(compositionListDeletion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.ATTRIBUTE_LIST_DELETION: {
				AttributeListDeletion attributeListDeletion = (AttributeListDeletion)theEObject;
				T result = caseAttributeListDeletion(attributeListDeletion);
				if (result == null) result = caseAttributeChange(attributeListDeletion);
				if (result == null) result = caseElementaryChange(attributeListDeletion);
				if (result == null) result = caseModelChange(attributeListDeletion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.ASSOCIATION_LIST_INSERTION: {
				AssociationListInsertion associationListInsertion = (AssociationListInsertion)theEObject;
				T result = caseAssociationListInsertion(associationListInsertion);
				if (result == null) result = caseAssociationChange(associationListInsertion);
				if (result == null) result = caseElementaryChange(associationListInsertion);
				if (result == null) result = caseModelChange(associationListInsertion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.COMPOSITION_LIST_INSERTION: {
				CompositionListInsertion compositionListInsertion = (CompositionListInsertion)theEObject;
				T result = caseCompositionListInsertion(compositionListInsertion);
				if (result == null) result = caseCompositionChange(compositionListInsertion);
				if (result == null) result = caseElementaryChange(compositionListInsertion);
				if (result == null) result = caseModelChange(compositionListInsertion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.ATTRIBUTE_LIST_INSERTION: {
				AttributeListInsertion attributeListInsertion = (AttributeListInsertion)theEObject;
				T result = caseAttributeListInsertion(attributeListInsertion);
				if (result == null) result = caseAttributeChange(attributeListInsertion);
				if (result == null) result = caseElementaryChange(attributeListInsertion);
				if (result == null) result = caseModelChange(attributeListInsertion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.ATTRIBUTE_PROPERTY_CHANGE: {
				AttributePropertyChange attributePropertyChange = (AttributePropertyChange)theEObject;
				T result = caseAttributePropertyChange(attributePropertyChange);
				if (result == null) result = caseAttributeChange(attributePropertyChange);
				if (result == null) result = caseElementaryChange(attributePropertyChange);
				if (result == null) result = caseModelChange(attributePropertyChange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.ASSOCIATION_PROPERTY_CHANGE: {
				AssociationPropertyChange associationPropertyChange = (AssociationPropertyChange)theEObject;
				T result = caseAssociationPropertyChange(associationPropertyChange);
				if (result == null) result = caseAssociationChange(associationPropertyChange);
				if (result == null) result = caseElementaryChange(associationPropertyChange);
				if (result == null) result = caseModelChange(associationPropertyChange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.COMPOSITION_PROPERTY_CHANGE: {
				CompositionPropertyChange compositionPropertyChange = (CompositionPropertyChange)theEObject;
				T result = caseCompositionPropertyChange(compositionPropertyChange);
				if (result == null) result = caseCompositionChange(compositionPropertyChange);
				if (result == null) result = caseElementaryChange(compositionPropertyChange);
				if (result == null) result = caseModelChange(compositionPropertyChange);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY: {
				CompositionMoveIntoProperty compositionMoveIntoProperty = (CompositionMoveIntoProperty)theEObject;
				T result = caseCompositionMoveIntoProperty(compositionMoveIntoProperty);
				if (result == null) result = caseCompositionChange(compositionMoveIntoProperty);
				if (result == null) result = caseElementaryChange(compositionMoveIntoProperty);
				if (result == null) result = caseModelChange(compositionMoveIntoProperty);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.COMPOSITION_MOVE_TO_LIST: {
				CompositionMoveToList compositionMoveToList = (CompositionMoveToList)theEObject;
				T result = caseCompositionMoveToList(compositionMoveToList);
				if (result == null) result = caseCompositionChange(compositionMoveToList);
				if (result == null) result = caseElementaryChange(compositionMoveToList);
				if (result == null) result = caseModelChange(compositionMoveToList);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.COMPOSITION_MOVE_TO_COLLECTION: {
				CompositionMoveToCollection compositionMoveToCollection = (CompositionMoveToCollection)theEObject;
				T result = caseCompositionMoveToCollection(compositionMoveToCollection);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.OPERATION_CALL: {
				OperationCall operationCall = (OperationCall)theEObject;
				T result = caseOperationCall(operationCall);
				if (result == null) result = caseModelChange(operationCall);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.OPERATION_ARGUMENT: {
				OperationArgument operationArgument = (OperationArgument)theEObject;
				T result = caseOperationArgument(operationArgument);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.VALUE_ARGUMENT: {
				ValueArgument valueArgument = (ValueArgument)theEObject;
				T result = caseValueArgument(valueArgument);
				if (result == null) result = caseOperationArgument(valueArgument);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ChangesPackage.REFERENCE_ARGUMENT: {
				ReferenceArgument referenceArgument = (ReferenceArgument)theEObject;
				T result = caseReferenceArgument(referenceArgument);
				if (result == null) result = caseOperationArgument(referenceArgument);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Model Change Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model Change Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModelChangeSet(ModelChangeSet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Model Change</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model Change</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModelChange(ModelChange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Elementary Change</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Elementary Change</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseElementaryChange(ElementaryChange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Change Transaction</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Change Transaction</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChangeTransaction(ChangeTransaction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composition Change</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composition Change</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompositionChange(CompositionChange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Association Change</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Association Change</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssociationChange(AssociationChange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Change</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Change</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeChange(AttributeChange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Association Collection Deletion</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Association Collection Deletion</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssociationCollectionDeletion(AssociationCollectionDeletion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composition Collection Deletion</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composition Collection Deletion</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompositionCollectionDeletion(CompositionCollectionDeletion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Collection Deletion</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Collection Deletion</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeCollectionDeletion(AttributeCollectionDeletion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Association Collection Insertion</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Association Collection Insertion</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssociationCollectionInsertion(AssociationCollectionInsertion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composition Collection Insertion</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composition Collection Insertion</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompositionCollectionInsertion(CompositionCollectionInsertion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Collection Insertion</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Collection Insertion</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeCollectionInsertion(AttributeCollectionInsertion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Association Collection Reset</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Association Collection Reset</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssociationCollectionReset(AssociationCollectionReset object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composition Collection Reset</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composition Collection Reset</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompositionCollectionReset(CompositionCollectionReset object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Collection Reset</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Collection Reset</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeCollectionReset(AttributeCollectionReset object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Association List Deletion</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Association List Deletion</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssociationListDeletion(AssociationListDeletion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composition List Deletion</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composition List Deletion</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompositionListDeletion(CompositionListDeletion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute List Deletion</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute List Deletion</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeListDeletion(AttributeListDeletion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Association List Insertion</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Association List Insertion</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssociationListInsertion(AssociationListInsertion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composition List Insertion</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composition List Insertion</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompositionListInsertion(CompositionListInsertion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute List Insertion</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute List Insertion</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeListInsertion(AttributeListInsertion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Property Change</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Property Change</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributePropertyChange(AttributePropertyChange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Association Property Change</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Association Property Change</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssociationPropertyChange(AssociationPropertyChange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composition Property Change</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composition Property Change</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompositionPropertyChange(CompositionPropertyChange object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composition Move Into Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composition Move Into Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompositionMoveIntoProperty(CompositionMoveIntoProperty object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composition Move To List</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composition Move To List</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompositionMoveToList(CompositionMoveToList object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composition Move To Collection</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composition Move To Collection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompositionMoveToCollection(CompositionMoveToCollection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Operation Call</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Operation Call</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOperationCall(OperationCall object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Operation Argument</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Operation Argument</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOperationArgument(OperationArgument object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Value Argument</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Value Argument</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValueArgument(ValueArgument object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Reference Argument</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Reference Argument</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReferenceArgument(ReferenceArgument object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //ChangesSwitch
