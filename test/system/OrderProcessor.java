package system;

import java.util.function.Consumer;

import datamodel.Order;
import datamodel.OrderItem;
import system.InventoryManager;



final class OrderProcessor implements Components.OrderProcessor{
	
	public static OrderProcessor instance = null;
	public InventoryManager inventoryManager;
	
	public OrderProcessor() {
		this.inventoryManager = new InventoryManager();
	}
	
	 public static OrderProcessor getInstance() {
		    if( instance == null ) {
		instance = new OrderProcessor();
		    }
		    return instance;
		  }

	public OrderProcessor(InventoryManager inventoryManager) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean accept(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean accept(Order order, Consumer<Order> acceptCode, Consumer<Order> rejectCode,
			Consumer<OrderItem> rejectedOrderItemCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long orderValue(Order order) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long vat(long grossValue) {
		
		return vat(grossValue, 1);
	}

	@Override
	public long vat(long grossValue, int rateIndex) {

		if(rateIndex == 1) {
			
			return (long) (grossValue * (0.19 / 1.19));
		}
		
		else if(rateIndex == 2) {
			
			return (long) (grossValue * (0.07 / 1.07));
		}
		
		else return 0;
	
}

}
