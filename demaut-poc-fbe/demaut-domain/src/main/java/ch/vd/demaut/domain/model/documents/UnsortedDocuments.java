package ch.vd.demaut.domain.model.documents;

import java.util.ArrayList;
import java.util.List;

import ch.vd.demaut.commons.FunctionalKeyEqualsPredicate;

/**
 * Collection of unsorted {@link Document} 
 *
 */
public class UnsortedDocuments {
	
	// ********************************************************* Fields
	private List<Document> documents = new ArrayList<Document>();

	// ********************************************************* Constructor

	// ********************************************************* Business Methods
	
	public void addDocument(Document doc) {
		documents.add(doc);
	}
	
	public List<DocumentsGroup> organizeDocumentsByGroup() {
		List<DocumentsGroup> documentsGroups = new ArrayList<DocumentsGroup>();
		for (Document doc : documents) {
			organizeDocByGroup(doc, documentsGroups);
		}
		return documentsGroups;
	}

	// ********************************************************* Getters
	public List<Document> getDocuments() {
		return documents;
	}
	
	
	// ********************************************************* Private methods
	private void organizeDocByGroup(Document doc, List<DocumentsGroup> documentsGroups) {
		
		DocumentsGroup matchingDocGroup = findMatchingDocGroup(doc, documentsGroups);

		if (matchingDocGroup == null) {
			//We need to create and insert a group
			documentsGroups.add(new DocumentsGroup(doc));
		} else {
			matchingDocGroup.addDocument(doc);
		}
	}

	private DocumentsGroup findMatchingDocGroup(Document doc, List<DocumentsGroup> documentsGroups) {
		org.apache.commons.collections.Predicate predicate = new FunctionalKeyEqualsPredicate(
				new DocumentsGroupFK(doc));
		
		DocumentsGroup docGroup = (DocumentsGroup) org.apache.commons.collections.CollectionUtils.find(documentsGroups, predicate);
		return docGroup;
	}

}
