package datamodel;

public class OrderItem {
	String description;
	final Article article;
	int unitsOrdered;
	
	protected OrderItem(String descr, Article article, int units) {
		setDescription(descr);
		this.article = article;
		setUnitsOrdered(units);
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String descr) {

		if (descr == null) {
		this.description = "";
		} else {
			
			this.description = descr;
		}
	}
	
	public Article getArticle() {
		return article;
	}
	
	public int getUnitsOrdered() {
		return unitsOrdered;
	}
	
	public void setUnitsOrdered(int number) {
		if (number < 0) {
			
			this.unitsOrdered = 0;
			
			}
			else {
			this.unitsOrdered = number;
			}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
