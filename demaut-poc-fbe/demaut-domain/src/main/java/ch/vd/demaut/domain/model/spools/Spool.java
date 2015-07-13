package ch.vd.demaut.domain.model.spools;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

import ch.vd.demaut.commons.ObjectFunctionalKeyAware;
import ch.vd.demaut.commons.annotations.Entity;
import ch.vd.demaut.domain.model.DispatchCode;
import ch.vd.demaut.domain.model.EnvelopeSize;
import ch.vd.demaut.domain.model.RegionCode;
import ch.vd.demaut.domain.model.documents.Document;
import ch.vd.demaut.domain.model.documents.DocumentsGroup;

/**
 * A spool to be displayed in the PrintShop screen. <br>
 * One spool represents a bunch of pages with a start and end pages (aka
 * banners). This pages are gathered in the same spool because they should be
 * tretated the same way by Application Support persons
 *
 */
@Entity
public class Spool extends ObjectFunctionalKeyAware  {

	// ********************************************************* Fields
	private DispatchCode dispatchCode;
	private RegionCode regionCode;
	private EnvelopeSize size;
	private Integer increment;

	private DateTime creationDateTime;

	private List<DocumentsGroup> documentsGroups = new ArrayList<DocumentsGroup>();

	// ********************************************************* Constructor

	Spool(DispatchCode dispatchCode, RegionCode regionCode, EnvelopeSize size,
			Integer increment, DateTime creationDateTime) {
		this.dispatchCode = dispatchCode;
		this.regionCode = regionCode;
		this.size = size;
		this.increment = increment;
		this.creationDateTime = creationDateTime;
	}

	// ********************************************************* Business Method

	/**
	 * TODO : Write the description of the method <br>
	 * Add a DocumentsGroup... 
	 * @param group
	 */
	public void addDocumentsGroup(DocumentsGroup group) {
		if (!doesUnbreakableGroupBelongToSpool(group)) {
			throw new IllegalArgumentException(
					"The group does not belong to this spool " + getFunctionalKey());
		}
		documentsGroups.add(group);
	}

	public boolean doesUnbreakableGroupBelongToSpool(DocumentsGroup group) {
		if (!group.getDispatchCode().equals(getDispatchCode())) {
			return false;
		}
		if (!group.getRegionCode().equals(getRegionCode())) {
			return false;
		}
		return true;
	}

	public List<Document> retrieveSortedDocuments() {
		List<Document> docs = new ArrayList<Document>();
		for (DocumentsGroup group : documentsGroups) {
			docs.addAll(group.getDocuments());
		}
		return docs;
	}

	public String generateSpoolName(String heading) {
		return SpoolNameGenerator.generateSpoolName(this, heading);
	}

	// ********************************************************* Getters

	@NotNull
	@Valid
	public DispatchCode getDispatchCode() {
		return dispatchCode;
	}

	@Min(value = 1)
	public Integer getIncrement() {
		return increment;
	}

	@NotNull
	@Valid
	public RegionCode getRegionCode() {
		return regionCode;
	}

	@NotNull
	public EnvelopeSize getSize() {
		return size;
	}

	@NotNull
	public DateTime getCreationDateTime() {
		return creationDateTime;
	}
	// ********************************************************* Technical methods
	
	@Override
	public SpoolFK getFunctionalKey() {
		return new SpoolFK(this);
	}

	// ********************************************************* Private methods

	

	// ********************************************************* Builder
	/**
	 * 
	 * Inner class Builder of a ${Spool} instance
	 */
	public static class SpoolBuilder {

		private DateTime creationDateTime;
		private DispatchCode dispatchCode;
		private RegionCode regionCode;
		private EnvelopeSize size;
		private Integer increment;

		public SpoolBuilder() {
		}

		public SpoolBuilder creationDateTime(DateTime dateTime) {
			this.creationDateTime = dateTime;
			return this;
		}

		public SpoolBuilder dispatchCode(DispatchCode code) {
			this.dispatchCode = code;
			return this;
		}

		public SpoolBuilder regionCode(RegionCode code) {
			this.regionCode = code;
			return this;
		}

		public SpoolBuilder size(EnvelopeSize size) {
			this.size = size;
			return this;
		}

		public SpoolBuilder increment(Integer increment) {
			this.increment = increment;
			return this;
		}

		public Spool build() {
			return new Spool(dispatchCode, regionCode, size, increment,
					creationDateTime);
		}

	}

}
