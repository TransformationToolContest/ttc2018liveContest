/**
 */
package Changes.impl;

import Changes.ChangesPackage;
import Changes.CompositionCollectionReset;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composition Collection Reset</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class CompositionCollectionResetImpl extends CompositionChangeImpl implements CompositionCollectionReset {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompositionCollectionResetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChangesPackage.Literals.COMPOSITION_COLLECTION_RESET;
	}

	@Override
	public void apply() {

		EList<?> collection = (EList<?>)this.getAffectedElement().eGet(this.getFeature());
		collection.clear();
	}

} //CompositionCollectionResetImpl
