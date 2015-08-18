package ch.vd.demaut.domain.model.documents;

import ch.vd.demaut.commons.fk.FunctionalKeyAbstract;
import ch.vd.demaut.domain.model.CustomerNumber;

/**
 * Functional Key of a {@link Customer}
 *
 */
public class CustomerFK extends FunctionalKeyAbstract<Customer> {

	// ********************************************************* Fields
	private CustomerNumber customerNumber;
	
	// ********************************************************* Constructor

	public CustomerFK(CustomerNumber customerNumber) {
		super();
		this.customerNumber = customerNumber;
	}
	
    // ********************************************************* Getters

	public CustomerNumber getCustomerNumber() {
		return customerNumber;
	}
	
}
