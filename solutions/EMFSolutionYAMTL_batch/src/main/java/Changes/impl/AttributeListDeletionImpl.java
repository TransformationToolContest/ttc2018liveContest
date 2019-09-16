/**
 */
package Changes.impl;

import Changes.AttributeListDeletion;
import Changes.ChangesPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute List Deletion</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link Changes.impl.AttributeListDeletionImpl#getDeletedValue <em>Deleted Value</em>}</li>
 *   <li>{@link Changes.impl.AttributeListDeletionImpl#getIndex <em>Index</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AttributeListDeletionImpl extends AttributeChangeImpl implements AttributeListDeletion {
	/**
	 * The default value of the '{@link #getDeletedValue() <em>Deleted Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeletedValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DELETED_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDeletedValue() <em>Deleted Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeletedValue()
	 * @generated
	 * @ordered
	 */
	protected String deletedValue = DELETED_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getIndex() <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int INDEX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getIndex() <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected int index = INDEX_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeListDeletionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChangesPackage.Literals.ATTRIBUTE_LIST_DELETION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDeletedValue() {
		return deletedValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeletedValue(String newDeletedValue) {
		String oldDeletedValue = deletedValue;
		deletedValue = newDeletedValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChangesPackage.ATTRIBUTE_LIST_DELETION__DELETED_VALUE, oldDeletedValue, deletedValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndex(int newIndex) {
		int oldIndex = index;
		index = newIndex;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChangesPackage.ATTRIBUTE_LIST_DELETION__INDEX, oldIndex, index));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ChangesPackage.ATTRIBUTE_LIST_DELETION__DELETED_VALUE:
				return getDeletedValue();
			case ChangesPackage.ATTRIBUTE_LIST_DELETION__INDEX:
				return getIndex();
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
			case ChangesPackage.ATTRIBUTE_LIST_DELETION__DELETED_VALUE:
				setDeletedValue((String)newValue);
				return;
			case ChangesPackage.ATTRIBUTE_LIST_DELETION__INDEX:
				setIndex((Integer)newValue);
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
			case ChangesPackage.ATTRIBUTE_LIST_DELETION__DELETED_VALUE:
				setDeletedValue(DELETED_VALUE_EDEFAULT);
				return;
			case ChangesPackage.ATTRIBUTE_LIST_DELETION__INDEX:
				setIndex(INDEX_EDEFAULT);
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
			case ChangesPackage.ATTRIBUTE_LIST_DELETION__DELETED_VALUE:
				return DELETED_VALUE_EDEFAULT == null ? deletedValue != null : !DELETED_VALUE_EDEFAULT.equals(deletedValue);
			case ChangesPackage.ATTRIBUTE_LIST_DELETION__INDEX:
				return index != INDEX_EDEFAULT;
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
		result.append(" (deletedValue: ");
		result.append(deletedValue);
		result.append(", index: ");
		result.append(index);
		result.append(')');
		return result.toString();
	}

	@Override
	public void apply() {

		EList<?> collection = (EList<?>)this.getAffectedElement().eGet(this.getFeature());
		collection.remove(this.getIndex());
	}

} //AttributeListDeletionImpl
