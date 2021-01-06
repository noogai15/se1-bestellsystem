package datamodel;
import java.util.ArrayList;
import java.util.Date;

public class Order {
	private final long id;
	private final Date date;
	private Customer customer = null;
	private final ArrayList<OrderItem> items;


	
	protected Order (long id, Date date, Customer customer){
		this.id = id;
		this.date = date;
		this.customer = customer;
		this.items = new ArrayList<OrderItem>();
		
		
	}
	
	public long getId() {
		return id;
		
	}
	
	public Date getDate() {
		return date;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public ArrayList<OrderItem> getItems() {
		return items;

	}
	
	public int count(ArrayList<OrderItem> items) {
		int i;
		for(i = 0; i <= items.size(); i++) {
		}
		return i;
	}
	
	public Order addItem(OrderItem item) {
		items.add(item);
		return this;
	}
	
	public Order removeItem(OrderItem item) {
		items.remove(item);
		return this;
	}
	
	public Order clearItems(OrderItem item) {
		for (OrderItem item1 : items) {
			items.remove(item1);
		}
		return this;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	}

