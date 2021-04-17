/**
 */
package Changes.impl;

import Changes.AssociationCollectionInsertion;
import Changes.ChangesPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Association Collection Insertion</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link Changes.impl.AssociationCollectionInsertionImpl#getAddedElement <em>Added Element</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AssociationCollectionInsertionImpl extends AssociationChangeImpl implements AssociationCollectionInsertion {
	/**
	 * The cached value of the '{@link #getAddedElement() <em>Added Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddedElement()
	 * @generated
	 * @ordered
	 */
	protected EObject addedElement;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AssociationCollectionInsertionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChangesPackage.Literals.ASSOCIATION_COLLECTION_INSERTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getAddedElement() {
		if (addedElement != null && addedElement.eIsProxy()) {
			InternalEObject oldAddedElement = (InternalEObject)addedElement;
			addedElement = eResolveProxy(oldAddedElement);
			if (addedElement != oldAddedElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChangesPackage.ASSOCIATION_COLLECTION_INSERTION__ADDED_ELEMENT, oldAddedElement, addedElement));
			}
		}
		return addedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetAddedElement() {
		return addedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAddedElement(EObject newAddedElement) {
		EObject oldAddedElement = addedElement;
		addedElement = newAddedElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChangesPackage.ASSOCIATION_COLLECTION_INSERTION__ADDED_ELEMENT, oldAddedElement, addedElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ChangesPackage.ASSOCIATION_COLLECTION_INSERTION__ADDED_ELEMENT:
				if (resolve) return getAddedElement();
				return basicGetAddedElement();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ChangesPackage.ASSOCIATION_COLLECTION_INSERTION__ADDED_ELEMENT:
				setAddedElement((EObject)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ChangesPackage.ASSOCIATION_COLLECTION_INSERTION__ADDED_ELEMENT:
				setAddedElement((EObject)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ChangesPackage.ASSOCIATION_COLLECTION_INSERTION__ADDED_ELEMENT:
				return addedElement != null;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public void apply() {
		EList<EObject> collection = (EList<EObject>)this.getAffectedElement().eGet(this.getFeature());
		collection.add(this.getAddedElement());
	}

} //AssociationCollectionInsertionImpl
