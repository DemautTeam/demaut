package ch.vd.demaut.domain.service.impl;

import java.util.List;

import org.joda.time.DateTime;

import ch.vd.demaut.domain.model.documents.DocumentsGroup;
import ch.vd.demaut.domain.model.documents.UnsortedDocuments;
import ch.vd.demaut.domain.model.spools.SpoolList;
import ch.vd.demaut.domain.repository.DispatchCodesRepository;
import ch.vd.demaut.domain.service.SpoolRoutingService;
import ch.vd.demaut.domain.service.SpoolsService;

/**
 * Implementation of the {@link SpoolsService}
 *
 */
public class SpoolsServiceImpl implements SpoolsService {

	// ********************************************************* Injected Fields
	SpoolRoutingService spoolRoutingService;

	// ********************************************************* Constructor
	public SpoolsServiceImpl(DispatchCodesRepository dispatchCodesConfiguration) {
		super();
		spoolRoutingService = new SpoolRoutingServiceImpl(dispatchCodesConfiguration);
	}
	
	// ********************************************************* Business Methods
	@Override
	public SpoolList generateSpoolList(UnsortedDocuments documents, DateTime creationTime) {

		//0. We create the SpoolList
		SpoolList spoolList = new SpoolList(creationTime, spoolRoutingService);
		
		//1. We group documents into DocumentsGroup 
		List<DocumentsGroup> documentsGroups = documents.organizeDocumentsByGroup();
		
		//2. For each DocumentsGroup, we insert it into a spool
		spoolList.insertDocumentsGroupsInSpools(documentsGroups);
		
		//3. Return the generated SpoolList
		return spoolList;
		
	}

}
