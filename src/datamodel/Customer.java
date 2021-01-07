package datamodel;

import system.OutputProcessor;

public class Customer {
	private final String id;
	public String firstName;
	public String lastName;
	public String contact;
	OutputProcessor op;

	protected Customer(String id, String name, String contact) {
		this.id = id;
		setContact(contact);
		this.firstName = "";
		setLastName(name);
	}

	public String getId() {
		return id;

	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		if (firstName == null) {
			
			this.firstName = "";
		} else {
		
		this.firstName = firstName;
		}

	}

	public String getlastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		
		if (lastName == null) {
			
			this.lastName = "";
		} else {
		
		this.lastName = lastName;
		}
	}

	public String getName() {/*
		return this.firstName + this.lastName;*/
		return null;
	}

	public void setName(String name) {

		
		

	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
	if (contact == null) {
			
			this.contact = "";
		} else {
		
		this.contact = contact;
		}
	}

	

	public static void main(String[] args) {

	}

}
