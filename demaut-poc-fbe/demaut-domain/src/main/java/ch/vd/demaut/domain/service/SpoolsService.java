package ch.vd.demaut.domain.service;

import org.joda.time.DateTime;

import ch.vd.demaut.domain.model.documents.UnsortedDocuments;
import ch.vd.demaut.domain.model.spools.SpoolList;

/**
 * Domain services for Spools
 *
 */
public interface SpoolsService {

	/**
	 * Generate a sorted list of spools out of a list of unsorted documents 
	 * @param documents Unsorted document
	 * @return the lost of spool structured as a {@link SpoolList}
	 */
	SpoolList generateSpoolList(UnsortedDocuments documents, DateTime creationTime);
	
}
