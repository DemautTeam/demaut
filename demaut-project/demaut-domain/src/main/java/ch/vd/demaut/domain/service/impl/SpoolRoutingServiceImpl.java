package ch.vd.demaut.domain.service.impl;

import java.util.Collection;

import ch.vd.demaut.domain.model.DispatchCode;
import ch.vd.demaut.domain.model.EnvelopeSize;
import ch.vd.demaut.domain.model.documents.DocumentsGroup;
import ch.vd.demaut.domain.model.spools.SpoolRouting;
import ch.vd.demaut.domain.repository.DispatchCodesRepository;
import ch.vd.demaut.domain.service.SpoolRoutingService;

/**
 * Implementation of the {@link SpoolRoutingService}
 *
 */
public class SpoolRoutingServiceImpl implements SpoolRoutingService {

	// ********************************************************* Fields
	private DispatchCodesRepository dispatchCodesConfiguration;

	// ********************************************************* Constructor
	public SpoolRoutingServiceImpl(DispatchCodesRepository dispatchCodesConfiguration) {
		super();
		this.dispatchCodesConfiguration = dispatchCodesConfiguration;
	}

	// ********************************************************* Implemented
	// Services
	/**
	 * {@inheritDoc}
	 */
	@Override
	public SpoolRouting calculateSpoolRooting(DispatchCode docDispatchCode) {

		// Retrieve configured automatic dispatch codes
		Collection<DispatchCode> automaticDispatchCodes = dispatchCodesConfiguration
				.listAllAutomaticDispatchCodes();

		// Test whether the Document Dispatch Code is in the configured
		// automatic dispatch codes
		boolean isDocDispatchCodeAutomatic = automaticDispatchCodes
				.contains(docDispatchCode);

		// If not automatic then routing is Manual
		SpoolRouting result = isDocDispatchCodeAutomatic ? SpoolRouting.Automatic
				: SpoolRouting.Manual;

		return result;
	}

	@Override
	// TODO: Refactor when C4/C5 story to be implemented
	public EnvelopeSize calculateEnvelopeSize(DocumentsGroup group) {
		SpoolRouting routing = calculateSpoolRooting(group.getDispatchCode());
		if (routing == SpoolRouting.Manual) {
			return EnvelopeSize.X;
		} else {
			return EnvelopeSize.C4;
		}
	}
}
