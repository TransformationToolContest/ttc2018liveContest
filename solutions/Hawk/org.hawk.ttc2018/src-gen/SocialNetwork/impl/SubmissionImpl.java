/**
 */
package SocialNetwork.impl;

import SocialNetwork.Comment;
import SocialNetwork.SocialNetworkPackage;
import SocialNetwork.Submission;
import SocialNetwork.User;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Submission</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link SocialNetwork.impl.SubmissionImpl#getId <em>Id</em>}</li>
 *   <li>{@link SocialNetwork.impl.SubmissionImpl#getTimestamp <em>Timestamp</em>}</li>
 *   <li>{@link SocialNetwork.impl.SubmissionImpl#getContent <em>Content</em>}</li>
 *   <li>{@link SocialNetwork.impl.SubmissionImpl#getSubmitter <em>Submitter</em>}</li>
 *   <li>{@link SocialNetwork.impl.SubmissionImpl#getComments <em>Comments</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SubmissionImpl extends MinimalEObjectImpl.Container implements Submission {
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
	 * The default value of the '{@link #getTimestamp() <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimestamp()
	 * @generated
	 * @ordered
	 */
	protected static final Date TIMESTAMP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTimestamp() <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimestamp()
	 * @generated
	 * @ordered
	 */
	protected Date timestamp = TIMESTAMP_EDEFAULT;

	/**
	 * The default value of the '{@link #getContent() <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContent() <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected String content = CONTENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSubmitter() <em>Submitter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubmitter()
	 * @generated
	 * @ordered
	 */
	protected User submitter;

	/**
	 * The cached value of the '{@link #getComments() <em>Comments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComments()
	 * @generated
	 * @ordered
	 */
	protected EList<Comment> comments;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubmissionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SocialNetworkPackage.Literals.SUBMISSION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, SocialNetworkPackage.SUBMISSION__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimestamp(Date newTimestamp) {
		Date oldTimestamp = timestamp;
		timestamp = newTimestamp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SocialNetworkPackage.SUBMISSION__TIMESTAMP, oldTimestamp, timestamp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getContent() {
		return content;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContent(String newContent) {
		String oldContent = content;
		content = newContent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SocialNetworkPackage.SUBMISSION__CONTENT, oldContent, content));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public User getSubmitter() {
		if (submitter != null && submitter.eIsProxy()) {
			InternalEObject oldSubmitter = (InternalEObject)submitter;
			submitter = (User)eResolveProxy(oldSubmitter);
			if (submitter != oldSubmitter) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SocialNetworkPackage.SUBMISSION__SUBMITTER, oldSubmitter, submitter));
			}
		}
		return submitter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public User basicGetSubmitter() {
		return submitter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubmitter(User newSubmitter, NotificationChain msgs) {
		User oldSubmitter = submitter;
		submitter = newSubmitter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SocialNetworkPackage.SUBMISSION__SUBMITTER, oldSubmitter, newSubmitter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubmitter(User newSubmitter) {
		if (newSubmitter != submitter) {
			NotificationChain msgs = null;
			if (submitter != null)
				msgs = ((InternalEObject)submitter).eInverseRemove(this, SocialNetworkPackage.USER__SUBMISSIONS, User.class, msgs);
			if (newSubmitter != null)
				msgs = ((InternalEObject)newSubmitter).eInverseAdd(this, SocialNetworkPackage.USER__SUBMISSIONS, User.class, msgs);
			msgs = basicSetSubmitter(newSubmitter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SocialNetworkPackage.SUBMISSION__SUBMITTER, newSubmitter, newSubmitter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Comment> getComments() {
		if (comments == null) {
			comments = new EObjectContainmentWithInverseEList<Comment>(Comment.class, this, SocialNetworkPackage.SUBMISSION__COMMENTS, SocialNetworkPackage.COMMENT__COMMENTED);
		}
		return comments;
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
			case SocialNetworkPackage.SUBMISSION__SUBMITTER:
				if (submitter != null)
					msgs = ((InternalEObject)submitter).eInverseRemove(this, SocialNetworkPackage.USER__SUBMISSIONS, User.class, msgs);
				return basicSetSubmitter((User)otherEnd, msgs);
			case SocialNetworkPackage.SUBMISSION__COMMENTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getComments()).basicAdd(otherEnd, msgs);
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
			case SocialNetworkPackage.SUBMISSION__SUBMITTER:
				return basicSetSubmitter(null, msgs);
			case SocialNetworkPackage.SUBMISSION__COMMENTS:
				return ((InternalEList<?>)getComments()).basicRemove(otherEnd, msgs);
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
			case SocialNetworkPackage.SUBMISSION__ID:
				return getId();
			case SocialNetworkPackage.SUBMISSION__TIMESTAMP:
				return getTimestamp();
			case SocialNetworkPackage.SUBMISSION__CONTENT:
				return getContent();
			case SocialNetworkPackage.SUBMISSION__SUBMITTER:
				if (resolve) return getSubmitter();
				return basicGetSubmitter();
			case SocialNetworkPackage.SUBMISSION__COMMENTS:
				return getComments();
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
			case SocialNetworkPackage.SUBMISSION__ID:
				setId((String)newValue);
				return;
			case SocialNetworkPackage.SUBMISSION__TIMESTAMP:
				setTimestamp((Date)newValue);
				return;
			case SocialNetworkPackage.SUBMISSION__CONTENT:
				setContent((String)newValue);
				return;
			case SocialNetworkPackage.SUBMISSION__SUBMITTER:
				setSubmitter((User)newValue);
				return;
			case SocialNetworkPackage.SUBMISSION__COMMENTS:
				getComments().clear();
				getComments().addAll((Collection<? extends Comment>)newValue);
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
			case SocialNetworkPackage.SUBMISSION__ID:
				setId(ID_EDEFAULT);
				return;
			case SocialNetworkPackage.SUBMISSION__TIMESTAMP:
				setTimestamp(TIMESTAMP_EDEFAULT);
				return;
			case SocialNetworkPackage.SUBMISSION__CONTENT:
				setContent(CONTENT_EDEFAULT);
				return;
			case SocialNetworkPackage.SUBMISSION__SUBMITTER:
				setSubmitter((User)null);
				return;
			case SocialNetworkPackage.SUBMISSION__COMMENTS:
				getComments().clear();
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
			case SocialNetworkPackage.SUBMISSION__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case SocialNetworkPackage.SUBMISSION__TIMESTAMP:
				return TIMESTAMP_EDEFAULT == null ? timestamp != null : !TIMESTAMP_EDEFAULT.equals(timestamp);
			case SocialNetworkPackage.SUBMISSION__CONTENT:
				return CONTENT_EDEFAULT == null ? content != null : !CONTENT_EDEFAULT.equals(content);
			case SocialNetworkPackage.SUBMISSION__SUBMITTER:
				return submitter != null;
			case SocialNetworkPackage.SUBMISSION__COMMENTS:
				return comments != null && !comments.isEmpty();
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
		result.append(", timestamp: ");
		result.append(timestamp);
		result.append(", content: ");
		result.append(content);
		result.append(')');
		return result.toString();
	}

} //SubmissionImpl
