/**
 */
package SocialNetwork.impl;

import SocialNetwork.Comment;
import SocialNetwork.Post;
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

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Comment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link SocialNetwork.impl.CommentImpl#getCommented <em>Commented</em>}</li>
 *   <li>{@link SocialNetwork.impl.CommentImpl#getLikedBy <em>Liked By</em>}</li>
 *   <li>{@link SocialNetwork.impl.CommentImpl#getPost <em>Post</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CommentImpl extends SubmissionImpl implements Comment {
	/**
	 * The cached value of the '{@link #getLikedBy() <em>Liked By</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLikedBy()
	 * @generated
	 * @ordered
	 */
	protected EList<User> likedBy;

	/**
	 * The cached value of the '{@link #getPost() <em>Post</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPost()
	 * @generated
	 * @ordered
	 */
	protected Post post;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CommentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SocialNetworkPackage.Literals.COMMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Submission getCommented() {
		if (eContainerFeatureID() != SocialNetworkPackage.COMMENT__COMMENTED) return null;
		return (Submission)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<User> getLikedBy() {
		if (likedBy == null) {
			likedBy = new EObjectWithInverseResolvingEList.ManyInverse<User>(User.class, this, SocialNetworkPackage.COMMENT__LIKED_BY, SocialNetworkPackage.USER__LIKES);
		}
		return likedBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Post getPost() {
		if (post != null && post.eIsProxy()) {
			InternalEObject oldPost = (InternalEObject)post;
			post = (Post)eResolveProxy(oldPost);
			if (post != oldPost) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SocialNetworkPackage.COMMENT__POST, oldPost, post));
			}
		}
		return post;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Post basicGetPost() {
		return post;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPost(Post newPost) {
		Post oldPost = post;
		post = newPost;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SocialNetworkPackage.COMMENT__POST, oldPost, post));
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
			case SocialNetworkPackage.COMMENT__COMMENTED:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, SocialNetworkPackage.COMMENT__COMMENTED, msgs);
			case SocialNetworkPackage.COMMENT__LIKED_BY:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getLikedBy()).basicAdd(otherEnd, msgs);
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
			case SocialNetworkPackage.COMMENT__COMMENTED:
				return eBasicSetContainer(null, SocialNetworkPackage.COMMENT__COMMENTED, msgs);
			case SocialNetworkPackage.COMMENT__LIKED_BY:
				return ((InternalEList<?>)getLikedBy()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case SocialNetworkPackage.COMMENT__COMMENTED:
				return eInternalContainer().eInverseRemove(this, SocialNetworkPackage.SUBMISSION__COMMENTS, Submission.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SocialNetworkPackage.COMMENT__COMMENTED:
				return getCommented();
			case SocialNetworkPackage.COMMENT__LIKED_BY:
				return getLikedBy();
			case SocialNetworkPackage.COMMENT__POST:
				if (resolve) return getPost();
				return basicGetPost();
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
			case SocialNetworkPackage.COMMENT__LIKED_BY:
				getLikedBy().clear();
				getLikedBy().addAll((Collection<? extends User>)newValue);
				return;
			case SocialNetworkPackage.COMMENT__POST:
				setPost((Post)newValue);
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
			case SocialNetworkPackage.COMMENT__LIKED_BY:
				getLikedBy().clear();
				return;
			case SocialNetworkPackage.COMMENT__POST:
				setPost((Post)null);
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
			case SocialNetworkPackage.COMMENT__COMMENTED:
				return getCommented() != null;
			case SocialNetworkPackage.COMMENT__LIKED_BY:
				return likedBy != null && !likedBy.isEmpty();
			case SocialNetworkPackage.COMMENT__POST:
				return post != null;
		}
		return super.eIsSet(featureID);
	}

} //CommentImpl
