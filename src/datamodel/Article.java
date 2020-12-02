package datamodel;

public class Article {
	String id;
	String description;
	long unitPrice;
	int unitsInStore;

	protected Article(String id, String descr, long price, int units) {
		this.id = id;
		this.description = descr;
		this.unitPrice = price;
		this.unitsInStore = units;
	}
	
	public String getId() {
		return id;
		
	}
	
	
	
	public String getDescription() {
		return description;
	
	}
	
	public void setDescription(String descr) {
		this.description = descr;
		
	}
	
	public long getUnitPrice() {
		return unitPrice;
	}
	
	public void setUnitPrice(long price) {
		this.unitPrice = price;
	}
	
	public int getUnitsInStore() {
		return unitsInStore;
	}
	
	public void setUnitsInStore(int number) {
		this.unitsInStore = number;
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
