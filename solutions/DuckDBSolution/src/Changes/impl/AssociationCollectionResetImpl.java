/**
 */
package Changes.impl;

import Changes.AssociationCollectionReset;
import Changes.ChangesPackage;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Association Collection Reset</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class AssociationCollectionResetImpl extends AssociationChangeImpl implements AssociationCollectionReset {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AssociationCollectionResetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChangesPackage.Literals.ASSOCIATION_COLLECTION_RESET;
	}

	@Override
	public void apply() {
		EList<?> collection = (EList<?>)this.getAffectedElement().eGet(this.getFeature());
		collection.clear();
	}

} //AssociationCollectionResetImpl
