/**
 */
package Changes.impl;

import Changes.AttributeCollectionInsertion;
import Changes.ChangesPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Collection Insertion</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link Changes.impl.AttributeCollectionInsertionImpl#getAddedValue <em>Added Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AttributeCollectionInsertionImpl extends AttributeChangeImpl implements AttributeCollectionInsertion {
	/**
	 * The default value of the '{@link #getAddedValue() <em>Added Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddedValue()
	 * @generated
	 * @ordered
	 */
	protected static final String ADDED_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAddedValue() <em>Added Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddedValue()
	 * @generated
	 * @ordered
	 */
	protected String addedValue = ADDED_VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeCollectionInsertionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChangesPackage.Literals.ATTRIBUTE_COLLECTION_INSERTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAddedValue() {
		return addedValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAddedValue(String newAddedValue) {
		String oldAddedValue = addedValue;
		addedValue = newAddedValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChangesPackage.ATTRIBUTE_COLLECTION_INSERTION__ADDED_VALUE, oldAddedValue, addedValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ChangesPackage.ATTRIBUTE_COLLECTION_INSERTION__ADDED_VALUE:
				return getAddedValue();
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
			case ChangesPackage.ATTRIBUTE_COLLECTION_INSERTION__ADDED_VALUE:
				setAddedValue((String)newValue);
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
			case ChangesPackage.ATTRIBUTE_COLLECTION_INSERTION__ADDED_VALUE:
				setAddedValue(ADDED_VALUE_EDEFAULT);
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
			case ChangesPackage.ATTRIBUTE_COLLECTION_INSERTION__ADDED_VALUE:
				return ADDED_VALUE_EDEFAULT == null ? addedValue != null : !ADDED_VALUE_EDEFAULT.equals(addedValue);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (addedValue: ");
		result.append(addedValue);
		result.append(')');
		return result.toString();
	}

	@Override
	public void apply() {

		EList<String> collection = (EList<String>)this.getAffectedElement().eGet(this.getFeature());
		collection.add(this.getAddedValue());
	}

} //AttributeCollectionInsertionImpl
