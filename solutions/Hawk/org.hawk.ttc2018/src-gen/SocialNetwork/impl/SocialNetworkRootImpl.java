/**
 */
package SocialNetwork.impl;

import SocialNetwork.Post;
import SocialNetwork.SocialNetworkPackage;
import SocialNetwork.SocialNetworkRoot;
import SocialNetwork.User;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link SocialNetwork.impl.SocialNetworkRootImpl#getPosts <em>Posts</em>}</li>
 *   <li>{@link SocialNetwork.impl.SocialNetworkRootImpl#getUsers <em>Users</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SocialNetworkRootImpl extends MinimalEObjectImpl.Container implements SocialNetworkRoot {
	/**
	 * The cached value of the '{@link #getPosts() <em>Posts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPosts()
	 * @generated
	 * @ordered
	 */
	protected EList<Post> posts;

	/**
	 * The cached value of the '{@link #getUsers() <em>Users</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsers()
	 * @generated
	 * @ordered
	 */
	protected EList<User> users;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SocialNetworkRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SocialNetworkPackage.Literals.SOCIAL_NETWORK_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Post> getPosts() {
		if (posts == null) {
			posts = new EObjectContainmentEList<Post>(Post.class, this, SocialNetworkPackage.SOCIAL_NETWORK_ROOT__POSTS);
		}
		return posts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<User> getUsers() {
		if (users == null) {
			users = new EObjectContainmentEList<User>(User.class, this, SocialNetworkPackage.SOCIAL_NETWORK_ROOT__USERS);
		}
		return users;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SocialNetworkPackage.SOCIAL_NETWORK_ROOT__POSTS:
				return ((InternalEList<?>)getPosts()).basicRemove(otherEnd, msgs);
			case SocialNetworkPackage.SOCIAL_NETWORK_ROOT__USERS:
				return ((InternalEList<?>)getUsers()).basicRemove(otherEnd, msgs);
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
			case SocialNetworkPackage.SOCIAL_NETWORK_ROOT__POSTS:
				return getPosts();
			case SocialNetworkPackage.SOCIAL_NETWORK_ROOT__USERS:
				return getUsers();
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
			case SocialNetworkPackage.SOCIAL_NETWORK_ROOT__POSTS:
				getPosts().clear();
				getPosts().addAll((Collection<? extends Post>)newValue);
				return;
			case SocialNetworkPackage.SOCIAL_NETWORK_ROOT__USERS:
				getUsers().clear();
				getUsers().addAll((Collection<? extends User>)newValue);
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
			case SocialNetworkPackage.SOCIAL_NETWORK_ROOT__POSTS:
				getPosts().clear();
				return;
			case SocialNetworkPackage.SOCIAL_NETWORK_ROOT__USERS:
				getUsers().clear();
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
			case SocialNetworkPackage.SOCIAL_NETWORK_ROOT__POSTS:
				return posts != null && !posts.isEmpty();
			case SocialNetworkPackage.SOCIAL_NETWORK_ROOT__USERS:
				return users != null && !users.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SocialNetworkRootImpl
