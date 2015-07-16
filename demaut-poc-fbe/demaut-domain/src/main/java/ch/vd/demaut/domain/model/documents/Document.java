package ch.vd.demaut.domain.model.documents;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import ch.vd.demaut.commons.ObjectFunctionalKeyAware;
import ch.vd.demaut.commons.annotations.Aggregate;
import ch.vd.demaut.domain.model.CustomerNumber;
import ch.vd.demaut.domain.model.DispatchCode;
import ch.vd.demaut.domain.model.Pages;
import ch.vd.demaut.domain.model.RegionCode;
import ch.vd.demaut.domain.model.Society;

/**
 * Represents metadata of a PDF document to be printed
 *
 */
@Aggregate
public class Document extends ObjectFunctionalKeyAware {

	// ********************************************************* Fields
	private String id;
	private DocumentCode documentCode;
	private DispatchCode dispatchCode;
	private RegionCode regionCode;
	private Address address;
	private Customer customer;
	private Society society;
	private Pages pages;

	// ********************************************************* Constructor
	Document(String id, DocumentCode documentCode, DispatchCode dispatchCode,
			RegionCode regionCode, Address address, Customer customer,
			Society society, Pages pages) {
		super();
		this.id = id;
		this.documentCode = documentCode;
		this.dispatchCode = dispatchCode;
		this.regionCode = regionCode;
		this.address = address;
		this.customer = customer;
		this.society = society;
		this.pages = pages;
	}

	// ********************************************************* Business Methods

	// ********************************************************* Getters

	/**
	 * 
	 * @return Unique identifier of this {@link Document}
	 */
	@NotNull
	@NotBlank
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @return the {@link DocumentCode} associated with this. 
	 * 
	 */
	@NotNull
	@Valid
	public DocumentCode getDocumentCode() {
		return documentCode;
	}

	/**
	 * 
	 * @return The dispatch code of this {@link Document}
	 */
	@NotNull
	@Valid
	public DispatchCode getDispatchCode() {
		return dispatchCode;
	}

	/**
	 * 
	 * @return The region code of this {@link Document}
	 */
	@NotNull
	public RegionCode getRegionCode() {
		return regionCode;
	}

	@NotNull
	@Valid
	public Address getAddress() {
		return address;
	}

	@NotNull
	@Valid
	public Customer getCustomer() {
		return customer;
	}

	@NotNull
	@Valid
	public Society getSociety() {
		return society;
	}

	/**
	 * 
	 * @return {@link Pages} of this {@link Document}
	 */
	@NotNull
	@Valid
	public Pages getPages() {
		return pages;
	}

	// ********************************************************* Technical

	@Override
	public DocumentFK getFunctionalKey() {
		return new DocumentFK(getId());
	}

	/**
	 * 
	 * Inner class Builder of a Document instance
	 */
	public static class DocumentBuilder {
		
		private String id;
		private DocumentCode documentCode;
		private DispatchCode dispatchCode;
		private RegionCode regionCode;
		private Address address;
		private Customer customer;
		private Society society;
		private Pages pages;
		
		public DocumentBuilder() {
			this.regionCode = new RegionCode(""); //Default value for region code if not provided
		}
		
		public DocumentBuilder id(String id) {
			this.id = id;
			return this;
		}
		
		public DocumentBuilder documentCode(String code) {
			this.documentCode = new DocumentCode(code);
			return this;
		}
		
		public DocumentBuilder dispatchCode(String code) {
			this.dispatchCode = new DispatchCode(code);   
			return this;
		}
		
		public DocumentBuilder regionCode(String code) {
			this.regionCode = new RegionCode(code);
			return this;
		}
		
		public DocumentBuilder address(List<String> lines) {
			this.address = new Address(lines);
			return this;
		}
		
		public DocumentBuilder customer(String custNumber, String shortName, String accountNumber) {
			this.customer = new Customer(new CustomerNumber(custNumber), shortName, new AccountNumber(accountNumber));
			return this;
		}
		
		public DocumentBuilder society(String societyNumber) {
			this.society = new Society(societyNumber);
			return this;
		}
		
		public DocumentBuilder pages(int numPages) {
			this.pages = new Pages(numPages);
			return this;
		}
		
		public Document build() {
			return new Document(id, documentCode, dispatchCode, regionCode, address, customer, society, pages);
		}
		
	}

}
