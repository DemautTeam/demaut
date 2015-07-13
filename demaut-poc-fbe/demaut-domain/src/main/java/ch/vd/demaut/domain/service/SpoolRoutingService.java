package ch.vd.demaut.domain.service;

import ch.vd.demaut.domain.model.DispatchCode;
import ch.vd.demaut.domain.model.EnvelopeSize;
import ch.vd.demaut.domain.model.documents.Document;
import ch.vd.demaut.domain.model.documents.DocumentsGroup;
import ch.vd.demaut.domain.model.spools.SpoolRouting;

/**
 * Domain Services for {@link SpoolRouting}
 *
 */
public interface SpoolRoutingService {

	/**
	 * Calculate the {@link SpoolRouting} for a given {@link Document}
	 * @param document The {@link Document} to calculate the {@link SpoolRouting}s
	 * @return The calculated {@link SpoolRouting}
	 */
	SpoolRouting calculateSpoolRooting(DispatchCode docDispatchCode);
	
	EnvelopeSize calculateEnvelopeSize(DocumentsGroup group);

	
}
