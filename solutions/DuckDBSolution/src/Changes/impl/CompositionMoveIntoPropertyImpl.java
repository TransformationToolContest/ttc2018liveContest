/**
 */
package Changes.impl;

import Changes.ChangesPackage;
import Changes.CompositionMoveIntoProperty;
import Changes.ElementaryChange;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composition Move Into Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link Changes.impl.CompositionMoveIntoPropertyImpl#getNewValue <em>New Value</em>}</li>
 *   <li>{@link Changes.impl.CompositionMoveIntoPropertyImpl#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link Changes.impl.CompositionMoveIntoPropertyImpl#getOrigin <em>Origin</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompositionMoveIntoPropertyImpl extends CompositionChangeImpl implements CompositionMoveIntoProperty {
	/**
	 * The cached value of the '{@link #getNewValue() <em>New Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewValue()
	 * @generated
	 * @ordered
	 */
	protected EObject newValue;

	/**
	 * The cached value of the '{@link #getOldValue() <em>Old Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOldValue()
	 * @generated
	 * @ordered
	 */
	protected EObject oldValue;

	/**
	 * The cached value of the '{@link #getOrigin() <em>Origin</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrigin()
	 * @generated
	 * @ordered
	 */
	protected ElementaryChange origin;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompositionMoveIntoPropertyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChangesPackage.Literals.COMPOSITION_MOVE_INTO_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getNewValue() {
		if (newValue != null && newValue.eIsProxy()) {
			InternalEObject oldNewValue = (InternalEObject)newValue;
			newValue = eResolveProxy(oldNewValue);
			if (newValue != oldNewValue) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__NEW_VALUE, oldNewValue, newValue));
			}
		}
		return newValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetNewValue() {
		return newValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewValue(EObject newNewValue) {
		EObject oldNewValue = newValue;
		newValue = newNewValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__NEW_VALUE, oldNewValue, newValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getOldValue() {
		if (oldValue != null && oldValue.eIsProxy()) {
			InternalEObject oldOldValue = (InternalEObject)oldValue;
			oldValue = eResolveProxy(oldOldValue);
			if (oldValue != oldOldValue) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__OLD_VALUE, oldOldValue, oldValue));
			}
		}
		return oldValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetOldValue() {
		return oldValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOldValue(EObject newOldValue) {
		EObject oldOldValue = oldValue;
		oldValue = newOldValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__OLD_VALUE, oldOldValue, oldValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementaryChange getOrigin() {
		return origin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOrigin(ElementaryChange newOrigin, NotificationChain msgs) {
		ElementaryChange oldOrigin = origin;
		origin = newOrigin;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__ORIGIN, oldOrigin, newOrigin);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrigin(ElementaryChange newOrigin) {
		if (newOrigin != origin) {
			NotificationChain msgs = null;
			if (origin != null)
				msgs = ((InternalEObject)origin).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__ORIGIN, null, msgs);
			if (newOrigin != null)
				msgs = ((InternalEObject)newOrigin).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__ORIGIN, null, msgs);
			msgs = basicSetOrigin(newOrigin, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__ORIGIN, newOrigin, newOrigin));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__ORIGIN:
				return basicSetOrigin(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__NEW_VALUE:
				if (resolve) return getNewValue();
				return basicGetNewValue();
			case ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__OLD_VALUE:
				if (resolve) return getOldValue();
				return basicGetOldValue();
			case ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__ORIGIN:
				return getOrigin();
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
			case ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__NEW_VALUE:
				setNewValue((EObject)newValue);
				return;
			case ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__OLD_VALUE:
				setOldValue((EObject)newValue);
				return;
			case ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__ORIGIN:
				setOrigin((ElementaryChange)newValue);
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
			case ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__NEW_VALUE:
				setNewValue((EObject)null);
				return;
			case ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__OLD_VALUE:
				setOldValue((EObject)null);
				return;
			case ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__ORIGIN:
				setOrigin((ElementaryChange)null);
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
			case ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__NEW_VALUE:
				return newValue != null;
			case ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__OLD_VALUE:
				return oldValue != null;
			case ChangesPackage.COMPOSITION_MOVE_INTO_PROPERTY__ORIGIN:
				return origin != null;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public void apply() {
		// TODO Auto-generated method stub
		
	}

} //CompositionMoveIntoPropertyImpl
