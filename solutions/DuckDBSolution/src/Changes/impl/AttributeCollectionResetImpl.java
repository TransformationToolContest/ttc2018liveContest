/**
 */
package Changes.impl;

import Changes.AttributeCollectionReset;
import Changes.ChangesPackage;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Collection Reset</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class AttributeCollectionResetImpl extends AttributeChangeImpl implements AttributeCollectionReset {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeCollectionResetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChangesPackage.Literals.ATTRIBUTE_COLLECTION_RESET;
	}

	@Override
	public void apply() {

		EList<?> collection = (EList<?>)this.getAffectedElement().eGet(this.getFeature());
		collection.clear();
	}

} //AttributeCollectionResetImpl
