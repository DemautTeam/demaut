package ch.vd.demaut.domain.model.documents;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;

import ch.vd.demaut.commons.ObjectFunctionalKeyAware;
import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.domain.model.DispatchCode;
import ch.vd.demaut.domain.model.RegionCode;

/**
 * Documents grouped by common characteristics.
 * Documents in the same group cannot be split up by spools.
 */
@Entity
public class DocumentsGroup extends ObjectFunctionalKeyAware {

	// ********************************************************* Fields
	private DispatchCode dispatchCode;
	private RegionCode regionCode;
	private Address address;
	
	private List<Document> documents = new ArrayList<Document>();

	// ********************************************************* Constructor

	public DocumentsGroup(Document doc) {
		this.dispatchCode = doc.getDispatchCode();
		this.regionCode = doc.getRegionCode();
		this.address = doc.getAddress();
		addDocument(doc);
	}

	// ********************************************************* Business

	public void addDocument(Document doc) {
		if (!doesDocumentBelongsToGroup(doc)) {
			throw new IllegalArgumentException("Document is not compatible");
		}
		documents.add(doc);
	}

	public boolean doesDocumentBelongsToGroup(Document doc) {
		return getFunctionalKey().equals(new DocumentsGroupFK(doc));
	}

	// ********************************************************* Getters
	@Min(value=1)
	public List<Document> getDocuments() {
		return documents;
	}

	public DispatchCode getDispatchCode() {
		return dispatchCode;
	}

	public RegionCode getRegionCode() {
		return regionCode;
	}

	public Address getAddress() {
		return address;
	}

	// ********************************************************* Technical
	@Override
	public DocumentsGroupFK getFunctionalKey() {
		return new DocumentsGroupFK(this);
	}

}
