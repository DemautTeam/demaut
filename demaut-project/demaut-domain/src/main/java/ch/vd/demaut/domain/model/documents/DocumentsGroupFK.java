package ch.vd.demaut.domain.model.documents;

import ch.vd.demaut.commons.fk.FunctionalKeyAbstract;
import ch.vd.demaut.domain.model.DispatchCode;
import ch.vd.demaut.domain.model.RegionCode;

/**
 * Functional Key of a {@link DocumentsGroup}
 *
 */
public class DocumentsGroupFK extends FunctionalKeyAbstract<Document> {

	// ********************************************************* Fields
	private DispatchCode dispatchCode;
	private RegionCode regionCode; 
	private Address address;

	// ********************************************************* Constructor
	public DocumentsGroupFK(DocumentsGroup group) {
		super();
		this.dispatchCode = group.getDispatchCode();
		this.regionCode = group.getRegionCode();
		this.address = group.getAddress();
	} 
	
	public DocumentsGroupFK(Document doc) {
		super();
		this.dispatchCode = doc.getDispatchCode();
		this.regionCode = doc.getRegionCode();
		this.address = doc.getAddress();
	} 
	// ********************************************************* Getters
	
	public DispatchCode getDispatchCode() {
		return dispatchCode;
	}
	
	public RegionCode getRegionCode() {
		return regionCode;
	}
	
	public Address getAddress() {
		return address;
	}
}
