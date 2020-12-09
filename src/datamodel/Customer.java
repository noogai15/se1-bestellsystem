package datamodel;

import system.OutputProcessor;

public class Customer {
	final String id;
	public String firstName;
	public String lastName;
	public String contact;
	OutputProcessor op;

	protected Customer(String id, String name, String contact) {
		this.id = id;
		this.contact = contact;
		this.firstName = "";
		this.lastName = "";
	}

	public String getId() {
		return id;

	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;

	}

	public String getlastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;

	}

	public String getName() {/*
		return this.firstName + this.lastName;*/
		return null;
	}

	public void setName(String name) {
		/*if(name.contains(","))
				{
			String[] firstLastName = name.split(",");
			this.firstName = firstLastName[1].trim();
			this.lastName = firstLastName[0].trim();
		}else {
			String[] firstLastName = name.split(" ");
			this.lastName = firstLastName[1].trim();
			this.lastName = firstLastName[firstLastName.length - 1].trim();
			this.firstName = firstLastName[0].trim();
			int a = firstLastName.length - 1;
			for (int i = 1; i < a; i++) {
				this.firstName = this.firstName + " " + firstLastName[i];
			}

		}*/

	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;

	}

	public static void main(String[] args) {

	}

}
