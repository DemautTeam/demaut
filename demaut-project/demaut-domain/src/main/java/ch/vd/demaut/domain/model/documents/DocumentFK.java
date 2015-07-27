package ch.vd.demaut.domain.model.documents;

import ch.vd.demaut.commons.FunctionalKeyAbstract;

/**
 * Functional key of a {@link Document}
 *
 */
public class DocumentFK extends FunctionalKeyAbstract<Document> {

	// ********************************************************* Fields
	private String id;

	// ********************************************************* Constructor
	public DocumentFK(String id) {
		super();
		this.id = id;
	} 
	
	public DocumentFK(Document doc) {
		this(doc.getId());
	}
	
	// ********************************************************* Getters
	public String getId() {
		return id;
	}
}
