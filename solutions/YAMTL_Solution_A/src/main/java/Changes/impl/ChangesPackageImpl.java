/**
 */
package Changes.impl;

import Changes.AssociationChange;
import Changes.AssociationCollectionDeletion;
import Changes.AssociationCollectionInsertion;
import Changes.AssociationCollectionReset;
import Changes.AssociationListDeletion;
import Changes.AssociationListInsertion;
import Changes.AssociationPropertyChange;
import Changes.AttributeChange;
import Changes.AttributeCollectionDeletion;
import Changes.AttributeCollectionInsertion;
import Changes.AttributeCollectionReset;
import Changes.AttributeListDeletion;
import Changes.AttributeListInsertion;
import Changes.AttributePropertyChange;
import Changes.ChangeTransaction;
import Changes.ChangesFactory;
import Changes.ChangesPackage;
import Changes.CompositionChange;
import Changes.CompositionCollectionDeletion;
import Changes.CompositionCollectionInsertion;
import Changes.CompositionCollectionReset;
import Changes.CompositionListDeletion;
import Changes.CompositionListInsertion;
import Changes.CompositionMoveIntoProperty;
import Changes.CompositionMoveToCollection;
import Changes.CompositionMoveToList;
import Changes.CompositionPropertyChange;
import Changes.ElementaryChange;
import Changes.ModelChange;
import Changes.ModelChangeSet;
import Changes.OperationArgument;
import Changes.OperationCall;
import Changes.ReferenceArgument;
import Changes.ValueArgument;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ChangesPackageImpl extends EPackageImpl implements ChangesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelChangeSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass elementaryChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass changeTransactionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositionChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass associationChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass associationCollectionDeletionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositionCollectionDeletionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeCollectionDeletionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass associationCollectionInsertionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositionCollectionInsertionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeCollectionInsertionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass associationCollectionResetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositionCollectionResetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeCollectionResetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass associationListDeletionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositionListDeletionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeListDeletionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass associationListInsertionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositionListInsertionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeListInsertionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributePropertyChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass associationPropertyChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositionPropertyChangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositionMoveIntoPropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositionMoveToListEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositionMoveToCollectionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationCallEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationArgumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueArgumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass referenceArgumentEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see Changes.ChangesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ChangesPackageImpl() {
		super(eNS_URI, ChangesFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link ChangesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ChangesPackage init() {
		if (isInited) return (ChangesPackage)EPackage.Registry.INSTANCE.getEPackage(ChangesPackage.eNS_URI);

		// Obtain or create and register package
		ChangesPackageImpl theChangesPackage = (ChangesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ChangesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ChangesPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theChangesPackage.createPackageContents();

		// Initialize created meta-data
		theChangesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theChangesPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ChangesPackage.eNS_URI, theChangesPackage);
		return theChangesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModelChangeSet() {
		return modelChangeSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModelChangeSet_Changes() {
		return (EReference)modelChangeSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModelChange() {
		return modelChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getElementaryChange() {
		return elementaryChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElementaryChange_AffectedElement() {
		return (EReference)elementaryChangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElementaryChange_Feature() {
		return (EReference)elementaryChangeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChangeTransaction() {
		return changeTransactionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChangeTransaction_SourceChange() {
		return (EReference)changeTransactionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChangeTransaction_NestedChanges() {
		return (EReference)changeTransactionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompositionChange() {
		return compositionChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAssociationChange() {
		return associationChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeChange() {
		return attributeChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAssociationCollectionDeletion() {
		return associationCollectionDeletionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssociationCollectionDeletion_DeletedElement() {
		return (EReference)associationCollectionDeletionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompositionCollectionDeletion() {
		return compositionCollectionDeletionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompositionCollectionDeletion_DeletedElement() {
		return (EReference)compositionCollectionDeletionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeCollectionDeletion() {
		return attributeCollectionDeletionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeCollectionDeletion_DeletedValue() {
		return (EAttribute)attributeCollectionDeletionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAssociationCollectionInsertion() {
		return associationCollectionInsertionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssociationCollectionInsertion_AddedElement() {
		return (EReference)associationCollectionInsertionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompositionCollectionInsertion() {
		return compositionCollectionInsertionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompositionCollectionInsertion_AddedElement() {
		return (EReference)compositionCollectionInsertionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeCollectionInsertion() {
		return attributeCollectionInsertionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeCollectionInsertion_AddedValue() {
		return (EAttribute)attributeCollectionInsertionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAssociationCollectionReset() {
		return associationCollectionResetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompositionCollectionReset() {
		return compositionCollectionResetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeCollectionReset() {
		return attributeCollectionResetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAssociationListDeletion() {
		return associationListDeletionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssociationListDeletion_DeletedElement() {
		return (EReference)associationListDeletionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAssociationListDeletion_Index() {
		return (EAttribute)associationListDeletionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompositionListDeletion() {
		return compositionListDeletionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompositionListDeletion_DeletedElement() {
		return (EReference)compositionListDeletionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompositionListDeletion_Index() {
		return (EAttribute)compositionListDeletionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeListDeletion() {
		return attributeListDeletionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeListDeletion_DeletedValue() {
		return (EAttribute)attributeListDeletionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeListDeletion_Index() {
		return (EAttribute)attributeListDeletionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAssociationListInsertion() {
		return associationListInsertionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssociationListInsertion_AddedElement() {
		return (EReference)associationListInsertionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAssociationListInsertion_Index() {
		return (EAttribute)associationListInsertionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompositionListInsertion() {
		return compositionListInsertionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompositionListInsertion_AddedElement() {
		return (EReference)compositionListInsertionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompositionListInsertion_Index() {
		return (EAttribute)compositionListInsertionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeListInsertion() {
		return attributeListInsertionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeListInsertion_AddedValue() {
		return (EAttribute)attributeListInsertionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeListInsertion_Index() {
		return (EAttribute)attributeListInsertionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributePropertyChange() {
		return attributePropertyChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributePropertyChange_NewValue() {
		return (EAttribute)attributePropertyChangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributePropertyChange_OldValue() {
		return (EAttribute)attributePropertyChangeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAssociationPropertyChange() {
		return associationPropertyChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssociationPropertyChange_NewValue() {
		return (EReference)associationPropertyChangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssociationPropertyChange_OldValue() {
		return (EReference)associationPropertyChangeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompositionPropertyChange() {
		return compositionPropertyChangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompositionPropertyChange_NewValue() {
		return (EReference)compositionPropertyChangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompositionPropertyChange_OldValue() {
		return (EReference)compositionPropertyChangeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompositionMoveIntoProperty() {
		return compositionMoveIntoPropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompositionMoveIntoProperty_NewValue() {
		return (EReference)compositionMoveIntoPropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompositionMoveIntoProperty_OldValue() {
		return (EReference)compositionMoveIntoPropertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompositionMoveIntoProperty_Origin() {
		return (EReference)compositionMoveIntoPropertyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompositionMoveToList() {
		return compositionMoveToListEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompositionMoveToList_Index() {
		return (EAttribute)compositionMoveToListEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompositionMoveToList_MovedElement() {
		return (EReference)compositionMoveToListEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompositionMoveToList_Origin() {
		return (EReference)compositionMoveToListEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompositionMoveToCollection() {
		return compositionMoveToCollectionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompositionMoveToCollection_MovedElement() {
		return (EReference)compositionMoveToCollectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompositionMoveToCollection_Origin() {
		return (EReference)compositionMoveToCollectionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperationCall() {
		return operationCallEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationCall_Operation() {
		return (EReference)operationCallEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationCall_TargetElement() {
		return (EReference)operationCallEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOperationCall_Arguments() {
		return (EReference)operationCallEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperationArgument() {
		return operationArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOperationArgument_Name() {
		return (EAttribute)operationArgumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValueArgument() {
		return valueArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getValueArgument_Value() {
		return (EAttribute)valueArgumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getReferenceArgument() {
		return referenceArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReferenceArgument_Value() {
		return (EReference)referenceArgumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangesFactory getChangesFactory() {
		return (ChangesFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		modelChangeSetEClass = createEClass(MODEL_CHANGE_SET);
		createEReference(modelChangeSetEClass, MODEL_CHANGE_SET__CHANGES);

		modelChangeEClass = createEClass(MODEL_CHANGE);

		elementaryChangeEClass = createEClass(ELEMENTARY_CHANGE);
		createEReference(elementaryChangeEClass, ELEMENTARY_CHANGE__AFFECTED_ELEMENT);
		createEReference(elementaryChangeEClass, ELEMENTARY_CHANGE__FEATURE);

		changeTransactionEClass = createEClass(CHANGE_TRANSACTION);
		createEReference(changeTransactionEClass, CHANGE_TRANSACTION__SOURCE_CHANGE);
		createEReference(changeTransactionEClass, CHANGE_TRANSACTION__NESTED_CHANGES);

		compositionChangeEClass = createEClass(COMPOSITION_CHANGE);

		associationChangeEClass = createEClass(ASSOCIATION_CHANGE);

		attributeChangeEClass = createEClass(ATTRIBUTE_CHANGE);

		associationCollectionDeletionEClass = createEClass(ASSOCIATION_COLLECTION_DELETION);
		createEReference(associationCollectionDeletionEClass, ASSOCIATION_COLLECTION_DELETION__DELETED_ELEMENT);

		compositionCollectionDeletionEClass = createEClass(COMPOSITION_COLLECTION_DELETION);
		createEReference(compositionCollectionDeletionEClass, COMPOSITION_COLLECTION_DELETION__DELETED_ELEMENT);

		attributeCollectionDeletionEClass = createEClass(ATTRIBUTE_COLLECTION_DELETION);
		createEAttribute(attributeCollectionDeletionEClass, ATTRIBUTE_COLLECTION_DELETION__DELETED_VALUE);

		associationCollectionInsertionEClass = createEClass(ASSOCIATION_COLLECTION_INSERTION);
		createEReference(associationCollectionInsertionEClass, ASSOCIATION_COLLECTION_INSERTION__ADDED_ELEMENT);

		compositionCollectionInsertionEClass = createEClass(COMPOSITION_COLLECTION_INSERTION);
		createEReference(compositionCollectionInsertionEClass, COMPOSITION_COLLECTION_INSERTION__ADDED_ELEMENT);

		attributeCollectionInsertionEClass = createEClass(ATTRIBUTE_COLLECTION_INSERTION);
		createEAttribute(attributeCollectionInsertionEClass, ATTRIBUTE_COLLECTION_INSERTION__ADDED_VALUE);

		associationCollectionResetEClass = createEClass(ASSOCIATION_COLLECTION_RESET);

		compositionCollectionResetEClass = createEClass(COMPOSITION_COLLECTION_RESET);

		attributeCollectionResetEClass = createEClass(ATTRIBUTE_COLLECTION_RESET);

		associationListDeletionEClass = createEClass(ASSOCIATION_LIST_DELETION);
		createEReference(associationListDeletionEClass, ASSOCIATION_LIST_DELETION__DELETED_ELEMENT);
		createEAttribute(associationListDeletionEClass, ASSOCIATION_LIST_DELETION__INDEX);

		compositionListDeletionEClass = createEClass(COMPOSITION_LIST_DELETION);
		createEReference(compositionListDeletionEClass, COMPOSITION_LIST_DELETION__DELETED_ELEMENT);
		createEAttribute(compositionListDeletionEClass, COMPOSITION_LIST_DELETION__INDEX);

		attributeListDeletionEClass = createEClass(ATTRIBUTE_LIST_DELETION);
		createEAttribute(attributeListDeletionEClass, ATTRIBUTE_LIST_DELETION__DELETED_VALUE);
		createEAttribute(attributeListDeletionEClass, ATTRIBUTE_LIST_DELETION__INDEX);

		associationListInsertionEClass = createEClass(ASSOCIATION_LIST_INSERTION);
		createEReference(associationListInsertionEClass, ASSOCIATION_LIST_INSERTION__ADDED_ELEMENT);
		createEAttribute(associationListInsertionEClass, ASSOCIATION_LIST_INSERTION__INDEX);

		compositionListInsertionEClass = createEClass(COMPOSITION_LIST_INSERTION);
		createEReference(compositionListInsertionEClass, COMPOSITION_LIST_INSERTION__ADDED_ELEMENT);
		createEAttribute(compositionListInsertionEClass, COMPOSITION_LIST_INSERTION__INDEX);

		attributeListInsertionEClass = createEClass(ATTRIBUTE_LIST_INSERTION);
		createEAttribute(attributeListInsertionEClass, ATTRIBUTE_LIST_INSERTION__ADDED_VALUE);
		createEAttribute(attributeListInsertionEClass, ATTRIBUTE_LIST_INSERTION__INDEX);

		attributePropertyChangeEClass = createEClass(ATTRIBUTE_PROPERTY_CHANGE);
		createEAttribute(attributePropertyChangeEClass, ATTRIBUTE_PROPERTY_CHANGE__NEW_VALUE);
		createEAttribute(attributePropertyChangeEClass, ATTRIBUTE_PROPERTY_CHANGE__OLD_VALUE);

		associationPropertyChangeEClass = createEClass(ASSOCIATION_PROPERTY_CHANGE);
		createEReference(associationPropertyChangeEClass, ASSOCIATION_PROPERTY_CHANGE__NEW_VALUE);
		createEReference(associationPropertyChangeEClass, ASSOCIATION_PROPERTY_CHANGE__OLD_VALUE);

		compositionPropertyChangeEClass = createEClass(COMPOSITION_PROPERTY_CHANGE);
		createEReference(compositionPropertyChangeEClass, COMPOSITION_PROPERTY_CHANGE__NEW_VALUE);
		createEReference(compositionPropertyChangeEClass, COMPOSITION_PROPERTY_CHANGE__OLD_VALUE);

		compositionMoveIntoPropertyEClass = createEClass(COMPOSITION_MOVE_INTO_PROPERTY);
		createEReference(compositionMoveIntoPropertyEClass, COMPOSITION_MOVE_INTO_PROPERTY__NEW_VALUE);
		createEReference(compositionMoveIntoPropertyEClass, COMPOSITION_MOVE_INTO_PROPERTY__OLD_VALUE);
		createEReference(compositionMoveIntoPropertyEClass, COMPOSITION_MOVE_INTO_PROPERTY__ORIGIN);

		compositionMoveToListEClass = createEClass(COMPOSITION_MOVE_TO_LIST);
		createEAttribute(compositionMoveToListEClass, COMPOSITION_MOVE_TO_LIST__INDEX);
		createEReference(compositionMoveToListEClass, COMPOSITION_MOVE_TO_LIST__MOVED_ELEMENT);
		createEReference(compositionMoveToListEClass, COMPOSITION_MOVE_TO_LIST__ORIGIN);

		compositionMoveToCollectionEClass = createEClass(COMPOSITION_MOVE_TO_COLLECTION);
		createEReference(compositionMoveToCollectionEClass, COMPOSITION_MOVE_TO_COLLECTION__MOVED_ELEMENT);
		createEReference(compositionMoveToCollectionEClass, COMPOSITION_MOVE_TO_COLLECTION__ORIGIN);

		operationCallEClass = createEClass(OPERATION_CALL);
		createEReference(operationCallEClass, OPERATION_CALL__OPERATION);
		createEReference(operationCallEClass, OPERATION_CALL__TARGET_ELEMENT);
		createEReference(operationCallEClass, OPERATION_CALL__ARGUMENTS);

		operationArgumentEClass = createEClass(OPERATION_ARGUMENT);
		createEAttribute(operationArgumentEClass, OPERATION_ARGUMENT__NAME);

		valueArgumentEClass = createEClass(VALUE_ARGUMENT);
		createEAttribute(valueArgumentEClass, VALUE_ARGUMENT__VALUE);

		referenceArgumentEClass = createEClass(REFERENCE_ARGUMENT);
		createEReference(referenceArgumentEClass, REFERENCE_ARGUMENT__VALUE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		elementaryChangeEClass.getESuperTypes().add(this.getModelChange());
		changeTransactionEClass.getESuperTypes().add(this.getModelChange());
		compositionChangeEClass.getESuperTypes().add(this.getElementaryChange());
		associationChangeEClass.getESuperTypes().add(this.getElementaryChange());
		attributeChangeEClass.getESuperTypes().add(this.getElementaryChange());
		associationCollectionDeletionEClass.getESuperTypes().add(this.getAssociationChange());
		compositionCollectionDeletionEClass.getESuperTypes().add(this.getCompositionChange());
		attributeCollectionDeletionEClass.getESuperTypes().add(this.getAttributeChange());
		associationCollectionInsertionEClass.getESuperTypes().add(this.getAssociationChange());
		compositionCollectionInsertionEClass.getESuperTypes().add(this.getCompositionChange());
		attributeCollectionInsertionEClass.getESuperTypes().add(this.getAttributeChange());
		associationCollectionResetEClass.getESuperTypes().add(this.getAssociationChange());
		compositionCollectionResetEClass.getESuperTypes().add(this.getCompositionChange());
		attributeCollectionResetEClass.getESuperTypes().add(this.getAttributeChange());
		associationListDeletionEClass.getESuperTypes().add(this.getAssociationChange());
		compositionListDeletionEClass.getESuperTypes().add(this.getCompositionChange());
		attributeListDeletionEClass.getESuperTypes().add(this.getAttributeChange());
		associationListInsertionEClass.getESuperTypes().add(this.getAssociationChange());
		compositionListInsertionEClass.getESuperTypes().add(this.getCompositionChange());
		attributeListInsertionEClass.getESuperTypes().add(this.getAttributeChange());
		attributePropertyChangeEClass.getESuperTypes().add(this.getAttributeChange());
		associationPropertyChangeEClass.getESuperTypes().add(this.getAssociationChange());
		compositionPropertyChangeEClass.getESuperTypes().add(this.getCompositionChange());
		compositionMoveIntoPropertyEClass.getESuperTypes().add(this.getCompositionChange());
		compositionMoveToListEClass.getESuperTypes().add(this.getCompositionChange());
		operationCallEClass.getESuperTypes().add(this.getModelChange());
		valueArgumentEClass.getESuperTypes().add(this.getOperationArgument());
		referenceArgumentEClass.getESuperTypes().add(this.getOperationArgument());

		// Initialize classes, features, and operations; add parameters
		initEClass(modelChangeSetEClass, ModelChangeSet.class, "ModelChangeSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getModelChangeSet_Changes(), this.getModelChange(), null, "changes", null, 0, -1, ModelChangeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(modelChangeEClass, ModelChange.class, "ModelChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(elementaryChangeEClass, ElementaryChange.class, "ElementaryChange", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getElementaryChange_AffectedElement(), ecorePackage.getEObject(), null, "affectedElement", null, 1, 1, ElementaryChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getElementaryChange_Feature(), ecorePackage.getEStructuralFeature(), null, "feature", null, 1, 1, ElementaryChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(changeTransactionEClass, ChangeTransaction.class, "ChangeTransaction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getChangeTransaction_SourceChange(), this.getModelChange(), null, "sourceChange", null, 1, 1, ChangeTransaction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeTransaction_NestedChanges(), this.getModelChange(), null, "nestedChanges", null, 0, -1, ChangeTransaction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compositionChangeEClass, CompositionChange.class, "CompositionChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(associationChangeEClass, AssociationChange.class, "AssociationChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(attributeChangeEClass, AttributeChange.class, "AttributeChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(associationCollectionDeletionEClass, AssociationCollectionDeletion.class, "AssociationCollectionDeletion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAssociationCollectionDeletion_DeletedElement(), ecorePackage.getEObject(), null, "deletedElement", null, 1, 1, AssociationCollectionDeletion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compositionCollectionDeletionEClass, CompositionCollectionDeletion.class, "CompositionCollectionDeletion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompositionCollectionDeletion_DeletedElement(), ecorePackage.getEObject(), null, "deletedElement", null, 0, 1, CompositionCollectionDeletion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeCollectionDeletionEClass, AttributeCollectionDeletion.class, "AttributeCollectionDeletion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAttributeCollectionDeletion_DeletedValue(), ecorePackage.getEString(), "deletedValue", null, 1, 1, AttributeCollectionDeletion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(associationCollectionInsertionEClass, AssociationCollectionInsertion.class, "AssociationCollectionInsertion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAssociationCollectionInsertion_AddedElement(), ecorePackage.getEObject(), null, "addedElement", null, 1, 1, AssociationCollectionInsertion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compositionCollectionInsertionEClass, CompositionCollectionInsertion.class, "CompositionCollectionInsertion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompositionCollectionInsertion_AddedElement(), ecorePackage.getEObject(), null, "addedElement", null, 1, 1, CompositionCollectionInsertion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeCollectionInsertionEClass, AttributeCollectionInsertion.class, "AttributeCollectionInsertion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAttributeCollectionInsertion_AddedValue(), ecorePackage.getEString(), "addedValue", null, 1, 1, AttributeCollectionInsertion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(associationCollectionResetEClass, AssociationCollectionReset.class, "AssociationCollectionReset", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(compositionCollectionResetEClass, CompositionCollectionReset.class, "CompositionCollectionReset", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(attributeCollectionResetEClass, AttributeCollectionReset.class, "AttributeCollectionReset", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(associationListDeletionEClass, AssociationListDeletion.class, "AssociationListDeletion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAssociationListDeletion_DeletedElement(), ecorePackage.getEObject(), null, "deletedElement", null, 0, 1, AssociationListDeletion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAssociationListDeletion_Index(), ecorePackage.getEInt(), "index", null, 1, 1, AssociationListDeletion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compositionListDeletionEClass, CompositionListDeletion.class, "CompositionListDeletion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompositionListDeletion_DeletedElement(), ecorePackage.getEObject(), null, "deletedElement", null, 0, 1, CompositionListDeletion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompositionListDeletion_Index(), ecorePackage.getEInt(), "index", null, 1, 1, CompositionListDeletion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeListDeletionEClass, AttributeListDeletion.class, "AttributeListDeletion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAttributeListDeletion_DeletedValue(), ecorePackage.getEString(), "deletedValue", null, 0, 1, AttributeListDeletion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributeListDeletion_Index(), ecorePackage.getEInt(), "index", null, 1, 1, AttributeListDeletion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(associationListInsertionEClass, AssociationListInsertion.class, "AssociationListInsertion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAssociationListInsertion_AddedElement(), ecorePackage.getEObject(), null, "addedElement", null, 1, 1, AssociationListInsertion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAssociationListInsertion_Index(), ecorePackage.getEInt(), "index", null, 1, 1, AssociationListInsertion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compositionListInsertionEClass, CompositionListInsertion.class, "CompositionListInsertion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompositionListInsertion_AddedElement(), ecorePackage.getEObject(), null, "addedElement", null, 1, 1, CompositionListInsertion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompositionListInsertion_Index(), ecorePackage.getEInt(), "index", null, 1, 1, CompositionListInsertion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeListInsertionEClass, AttributeListInsertion.class, "AttributeListInsertion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAttributeListInsertion_AddedValue(), ecorePackage.getEString(), "addedValue", null, 1, 1, AttributeListInsertion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributeListInsertion_Index(), ecorePackage.getEInt(), "index", null, 1, 1, AttributeListInsertion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributePropertyChangeEClass, AttributePropertyChange.class, "AttributePropertyChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAttributePropertyChange_NewValue(), ecorePackage.getEString(), "newValue", null, 0, 1, AttributePropertyChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributePropertyChange_OldValue(), ecorePackage.getEString(), "oldValue", null, 0, 1, AttributePropertyChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(associationPropertyChangeEClass, AssociationPropertyChange.class, "AssociationPropertyChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAssociationPropertyChange_NewValue(), ecorePackage.getEObject(), null, "newValue", null, 0, 1, AssociationPropertyChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAssociationPropertyChange_OldValue(), ecorePackage.getEObject(), null, "oldValue", null, 0, 1, AssociationPropertyChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compositionPropertyChangeEClass, CompositionPropertyChange.class, "CompositionPropertyChange", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompositionPropertyChange_NewValue(), ecorePackage.getEObject(), null, "newValue", null, 0, 1, CompositionPropertyChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompositionPropertyChange_OldValue(), ecorePackage.getEObject(), null, "oldValue", null, 0, 1, CompositionPropertyChange.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compositionMoveIntoPropertyEClass, CompositionMoveIntoProperty.class, "CompositionMoveIntoProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompositionMoveIntoProperty_NewValue(), ecorePackage.getEObject(), null, "newValue", null, 1, 1, CompositionMoveIntoProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompositionMoveIntoProperty_OldValue(), ecorePackage.getEObject(), null, "oldValue", null, 0, 1, CompositionMoveIntoProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompositionMoveIntoProperty_Origin(), this.getElementaryChange(), null, "origin", null, 0, 1, CompositionMoveIntoProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compositionMoveToListEClass, CompositionMoveToList.class, "CompositionMoveToList", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCompositionMoveToList_Index(), ecorePackage.getEInt(), "index", null, 1, 1, CompositionMoveToList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompositionMoveToList_MovedElement(), ecorePackage.getEObject(), null, "movedElement", null, 1, 1, CompositionMoveToList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompositionMoveToList_Origin(), this.getElementaryChange(), null, "origin", null, 1, 1, CompositionMoveToList.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compositionMoveToCollectionEClass, CompositionMoveToCollection.class, "CompositionMoveToCollection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompositionMoveToCollection_MovedElement(), ecorePackage.getEObject(), null, "movedElement", null, 1, 1, CompositionMoveToCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompositionMoveToCollection_Origin(), this.getElementaryChange(), null, "origin", null, 1, 1, CompositionMoveToCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(operationCallEClass, OperationCall.class, "OperationCall", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOperationCall_Operation(), ecorePackage.getEOperation(), null, "operation", null, 1, 1, OperationCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOperationCall_TargetElement(), ecorePackage.getEObject(), null, "targetElement", null, 0, 1, OperationCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOperationCall_Arguments(), this.getOperationArgument(), null, "arguments", null, 0, -1, OperationCall.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(operationArgumentEClass, OperationArgument.class, "OperationArgument", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOperationArgument_Name(), ecorePackage.getEString(), "name", null, 1, 1, OperationArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(valueArgumentEClass, ValueArgument.class, "ValueArgument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getValueArgument_Value(), ecorePackage.getEString(), "value", null, 1, 1, ValueArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(referenceArgumentEClass, ReferenceArgument.class, "ReferenceArgument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReferenceArgument_Value(), ecorePackage.getEObject(), null, "value", null, 1, 1, ReferenceArgument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //ChangesPackageImpl
