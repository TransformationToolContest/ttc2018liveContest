/**
 */
package SocialNetwork.impl;

import SocialNetwork.Comment;
import SocialNetwork.SocialNetworkPackage;
import SocialNetwork.Submission;
import SocialNetwork.User;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>User</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link SocialNetwork.impl.UserImpl#getId <em>Id</em>}</li>
 *   <li>{@link SocialNetwork.impl.UserImpl#getName <em>Name</em>}</li>
 *   <li>{@link SocialNetwork.impl.UserImpl#getSubmissions <em>Submissions</em>}</li>
 *   <li>{@link SocialNetwork.impl.UserImpl#getLikes <em>Likes</em>}</li>
 *   <li>{@link SocialNetwork.impl.UserImpl#getFriends <em>Friends</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UserImpl extends MinimalEObjectImpl.Container implements User {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSubmissions() <em>Submissions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubmissions()
	 * @generated
	 * @ordered
	 */
	protected EList<Submission> submissions;

	/**
	 * The cached value of the '{@link #getLikes() <em>Likes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLikes()
	 * @generated
	 * @ordered
	 */
	protected EList<Comment> likes;

	/**
	 * The cached value of the '{@link #getFriends() <em>Friends</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFriends()
	 * @generated
	 * @ordered
	 */
	protected EList<User> friends;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UserImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SocialNetworkPackage.Literals.USER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SocialNetworkPackage.USER__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SocialNetworkPackage.USER__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Submission> getSubmissions() {
		if (submissions == null) {
			submissions = new EObjectWithInverseResolvingEList<Submission>(Submission.class, this, SocialNetworkPackage.USER__SUBMISSIONS, SocialNetworkPackage.SUBMISSION__SUBMITTER);
		}
		return submissions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Comment> getLikes() {
		if (likes == null) {
			likes = new EObjectWithInverseResolvingEList.ManyInverse<Comment>(Comment.class, this, SocialNetworkPackage.USER__LIKES, SocialNetworkPackage.COMMENT__LIKED_BY);
		}
		return likes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<User> getFriends() {
		if (friends == null) {
			friends = new EObjectResolvingEList<User>(User.class, this, SocialNetworkPackage.USER__FRIENDS);
		}
		return friends;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SocialNetworkPackage.USER__SUBMISSIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSubmissions()).basicAdd(otherEnd, msgs);
			case SocialNetworkPackage.USER__LIKES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getLikes()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SocialNetworkPackage.USER__SUBMISSIONS:
				return ((InternalEList<?>)getSubmissions()).basicRemove(otherEnd, msgs);
			case SocialNetworkPackage.USER__LIKES:
				return ((InternalEList<?>)getLikes()).basicRemove(otherEnd, msgs);
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
			case SocialNetworkPackage.USER__ID:
				return getId();
			case SocialNetworkPackage.USER__NAME:
				return getName();
			case SocialNetworkPackage.USER__SUBMISSIONS:
				return getSubmissions();
			case SocialNetworkPackage.USER__LIKES:
				return getLikes();
			case SocialNetworkPackage.USER__FRIENDS:
				return getFriends();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SocialNetworkPackage.USER__ID:
				setId((String)newValue);
				return;
			case SocialNetworkPackage.USER__NAME:
				setName((String)newValue);
				return;
			case SocialNetworkPackage.USER__SUBMISSIONS:
				getSubmissions().clear();
				getSubmissions().addAll((Collection<? extends Submission>)newValue);
				return;
			case SocialNetworkPackage.USER__LIKES:
				getLikes().clear();
				getLikes().addAll((Collection<? extends Comment>)newValue);
				return;
			case SocialNetworkPackage.USER__FRIENDS:
				getFriends().clear();
				getFriends().addAll((Collection<? extends User>)newValue);
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
			case SocialNetworkPackage.USER__ID:
				setId(ID_EDEFAULT);
				return;
			case SocialNetworkPackage.USER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SocialNetworkPackage.USER__SUBMISSIONS:
				getSubmissions().clear();
				return;
			case SocialNetworkPackage.USER__LIKES:
				getLikes().clear();
				return;
			case SocialNetworkPackage.USER__FRIENDS:
				getFriends().clear();
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
			case SocialNetworkPackage.USER__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case SocialNetworkPackage.USER__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SocialNetworkPackage.USER__SUBMISSIONS:
				return submissions != null && !submissions.isEmpty();
			case SocialNetworkPackage.USER__LIKES:
				return likes != null && !likes.isEmpty();
			case SocialNetworkPackage.USER__FRIENDS:
				return friends != null && !friends.isEmpty();
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
		result.append(" (id: ");
		result.append(id);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //UserImpl
