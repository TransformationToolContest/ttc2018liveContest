/**
 */
package Changes.impl;

import Changes.ChangesPackage;
import Changes.CompositionMoveToList;
import Changes.ElementaryChange;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composition Move To List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link Changes.impl.CompositionMoveToListImpl#getIndex <em>Index</em>}</li>
 *   <li>{@link Changes.impl.CompositionMoveToListImpl#getMovedElement <em>Moved Element</em>}</li>
 *   <li>{@link Changes.impl.CompositionMoveToListImpl#getOrigin <em>Origin</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompositionMoveToListImpl extends CompositionChangeImpl implements CompositionMoveToList {
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
	 * The cached value of the '{@link #getMovedElement() <em>Moved Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMovedElement()
	 * @generated
	 * @ordered
	 */
	protected EObject movedElement;

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
	protected CompositionMoveToListImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChangesPackage.Literals.COMPOSITION_MOVE_TO_LIST;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ChangesPackage.COMPOSITION_MOVE_TO_LIST__INDEX, oldIndex, index));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getMovedElement() {
		if (movedElement != null && movedElement.eIsProxy()) {
			InternalEObject oldMovedElement = (InternalEObject)movedElement;
			movedElement = eResolveProxy(oldMovedElement);
			if (movedElement != oldMovedElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChangesPackage.COMPOSITION_MOVE_TO_LIST__MOVED_ELEMENT, oldMovedElement, movedElement));
			}
		}
		return movedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetMovedElement() {
		return movedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMovedElement(EObject newMovedElement) {
		EObject oldMovedElement = movedElement;
		movedElement = newMovedElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChangesPackage.COMPOSITION_MOVE_TO_LIST__MOVED_ELEMENT, oldMovedElement, movedElement));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ChangesPackage.COMPOSITION_MOVE_TO_LIST__ORIGIN, oldOrigin, newOrigin);
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
				msgs = ((InternalEObject)origin).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ChangesPackage.COMPOSITION_MOVE_TO_LIST__ORIGIN, null, msgs);
			if (newOrigin != null)
				msgs = ((InternalEObject)newOrigin).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ChangesPackage.COMPOSITION_MOVE_TO_LIST__ORIGIN, null, msgs);
			msgs = basicSetOrigin(newOrigin, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChangesPackage.COMPOSITION_MOVE_TO_LIST__ORIGIN, newOrigin, newOrigin));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ChangesPackage.COMPOSITION_MOVE_TO_LIST__ORIGIN:
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
			case ChangesPackage.COMPOSITION_MOVE_TO_LIST__INDEX:
				return getIndex();
			case ChangesPackage.COMPOSITION_MOVE_TO_LIST__MOVED_ELEMENT:
				if (resolve) return getMovedElement();
				return basicGetMovedElement();
			case ChangesPackage.COMPOSITION_MOVE_TO_LIST__ORIGIN:
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
			case ChangesPackage.COMPOSITION_MOVE_TO_LIST__INDEX:
				setIndex((Integer)newValue);
				return;
			case ChangesPackage.COMPOSITION_MOVE_TO_LIST__MOVED_ELEMENT:
				setMovedElement((EObject)newValue);
				return;
			case ChangesPackage.COMPOSITION_MOVE_TO_LIST__ORIGIN:
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
			case ChangesPackage.COMPOSITION_MOVE_TO_LIST__INDEX:
				setIndex(INDEX_EDEFAULT);
				return;
			case ChangesPackage.COMPOSITION_MOVE_TO_LIST__MOVED_ELEMENT:
				setMovedElement((EObject)null);
				return;
			case ChangesPackage.COMPOSITION_MOVE_TO_LIST__ORIGIN:
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
			case ChangesPackage.COMPOSITION_MOVE_TO_LIST__INDEX:
				return index != INDEX_EDEFAULT;
			case ChangesPackage.COMPOSITION_MOVE_TO_LIST__MOVED_ELEMENT:
				return movedElement != null;
			case ChangesPackage.COMPOSITION_MOVE_TO_LIST__ORIGIN:
				return origin != null;
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
		result.append(" (index: ");
		result.append(index);
		result.append(')');
		return result.toString();
	}

	@Override
	public void apply() {
		// TODO Auto-generated method stub
		
	}

} //CompositionMoveToListImpl
