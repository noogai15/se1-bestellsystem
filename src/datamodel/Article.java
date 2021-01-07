package datamodel;

public class Article {
	private String id;
	private String description;
	private long unitPrice;
	private int unitsInStore;

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
		
		if (price < 0) {
			
		this.unitPrice = 0;
		
		}
		else {
		this.unitPrice = price;
		}
	}
	
	public int getUnitsInStore() {
		return unitsInStore;
	}
	
	public void setUnitsInStore(int l) {
		if (l < 0) {
			
			this.unitsInStore = 0;
			
			}
			else {
			this.unitsInStore = l;
			}
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
