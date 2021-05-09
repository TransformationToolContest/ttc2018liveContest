/**
 */
package SocialNetwork.impl;

import SocialNetwork.Comment;
import SocialNetwork.SocialNetworkPackage;
import SocialNetwork.Submission;
import SocialNetwork.User;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.BasicInternalEList;
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
	@Override
	public Submission getCommented() {
		if (eContainerFeatureID() != SocialNetworkPackage.COMMENT__COMMENTED) return null;
		return (Submission)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<User> getLikedBy() {
		if (likedBy == null) {
			likedBy = new BasicInternalEList<User>(User.class);
		}
		return likedBy;
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
			case SocialNetworkPackage.COMMENT__SUBMITTER:
				if (submitter != null)
					msgs = ((InternalEObject)submitter).eInverseRemove(this, SocialNetworkPackage.USER__SUBMISSIONS, User.class, msgs);
				return basicSetSubmitter((User)otherEnd, msgs);
			case SocialNetworkPackage.COMMENT__COMMENTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getComments()).basicAdd(otherEnd, msgs);
			case SocialNetworkPackage.COMMENT__COMMENTED:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, SocialNetworkPackage.COMMENT__COMMENTED, msgs);
			case SocialNetworkPackage.COMMENT__LIKED_BY:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getLikedBy()).basicAdd(otherEnd, msgs);
		}
		return eDynamicInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SocialNetworkPackage.COMMENT__SUBMITTER:
				return basicSetSubmitter(null, msgs);
			case SocialNetworkPackage.COMMENT__COMMENTS:
				return ((InternalEList<?>)getComments()).basicRemove(otherEnd, msgs);
			case SocialNetworkPackage.COMMENT__COMMENTED:
				return eBasicSetContainer(null, SocialNetworkPackage.COMMENT__COMMENTED, msgs);
			case SocialNetworkPackage.COMMENT__LIKED_BY:
				return ((InternalEList<?>)getLikedBy()).basicRemove(otherEnd, msgs);
		}
		return eDynamicInverseRemove(otherEnd, featureID, msgs);
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
		return eDynamicBasicRemoveFromContainer(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SocialNetworkPackage.COMMENT__ID:
				return getId();
			case SocialNetworkPackage.COMMENT__TIMESTAMP:
				return getTimestamp();
			case SocialNetworkPackage.COMMENT__CONTENT:
				return getContent();
			case SocialNetworkPackage.COMMENT__SUBMITTER:
				if (resolve) return getSubmitter();
				return basicGetSubmitter();
			case SocialNetworkPackage.COMMENT__COMMENTS:
				return getComments();
			case SocialNetworkPackage.COMMENT__COMMENTED:
				return getCommented();
			case SocialNetworkPackage.COMMENT__LIKED_BY:
				return getLikedBy();
		}
		return eDynamicGet(featureID, resolve, coreType);
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
			case SocialNetworkPackage.COMMENT__ID:
				setId((String)newValue);
				return;
			case SocialNetworkPackage.COMMENT__TIMESTAMP:
				setTimestamp((Date)newValue);
				return;
			case SocialNetworkPackage.COMMENT__CONTENT:
				setContent((String)newValue);
				return;
			case SocialNetworkPackage.COMMENT__SUBMITTER:
				setSubmitter((User)newValue);
				return;
			case SocialNetworkPackage.COMMENT__COMMENTS:
				getComments().clear();
				getComments().addAll((Collection<? extends Comment>)newValue);
				return;
			case SocialNetworkPackage.COMMENT__LIKED_BY:
				getLikedBy().clear();
				getLikedBy().addAll((Collection<? extends User>)newValue);
				return;
		}
		eDynamicSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SocialNetworkPackage.COMMENT__ID:
				setId(ID_EDEFAULT);
				return;
			case SocialNetworkPackage.COMMENT__TIMESTAMP:
				setTimestamp(TIMESTAMP_EDEFAULT);
				return;
			case SocialNetworkPackage.COMMENT__CONTENT:
				setContent(CONTENT_EDEFAULT);
				return;
			case SocialNetworkPackage.COMMENT__SUBMITTER:
				setSubmitter((User)null);
				return;
			case SocialNetworkPackage.COMMENT__COMMENTS:
				getComments().clear();
				return;
			case SocialNetworkPackage.COMMENT__LIKED_BY:
				getLikedBy().clear();
				return;
		}
		eDynamicUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SocialNetworkPackage.COMMENT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case SocialNetworkPackage.COMMENT__TIMESTAMP:
				return TIMESTAMP_EDEFAULT == null ? timestamp != null : !TIMESTAMP_EDEFAULT.equals(timestamp);
			case SocialNetworkPackage.COMMENT__CONTENT:
				return CONTENT_EDEFAULT == null ? content != null : !CONTENT_EDEFAULT.equals(content);
			case SocialNetworkPackage.COMMENT__SUBMITTER:
				return submitter != null;
			case SocialNetworkPackage.COMMENT__COMMENTS:
				return comments != null && !comments.isEmpty();
			case SocialNetworkPackage.COMMENT__COMMENTED:
				return getCommented() != null;
			case SocialNetworkPackage.COMMENT__LIKED_BY:
				return likedBy != null && !likedBy.isEmpty();
		}
		return eDynamicIsSet(featureID);
	}

} //CommentImpl
