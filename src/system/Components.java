package system;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;

import application.Application_1;
import application.Application_2;
import datamodel.Article;
import datamodel.Customer;
import datamodel.Order;
import datamodel.OrderItem;
import datamodel.RawDataFactory;
import system.DataFactory.IDGenerator;
import system.DataFactory.IDTYPE;


/**
 * Central location defining system component interfaces for:
 * \\
 *  - InventoryManager		; manage article inventory
 *  - OrderProcessor		; process orders
 * 	- DataFactory			; create business objects from the datamodel
 * 	- OutputProcessor		; produce system outputs and mapping functions,
 * 							; e.g. to map Customer names between single-string
 * 							; and split-string (first-/lastName) representations
 * 
 * @author svgr64
 *
 */
public interface Components {


	/**
	 * Interface of InventoryManager that maintains the current inventory, which
	 * contains Article objects with unitsInStore and unitPrice attributes.
	 *
	 */
	interface InventoryManager {

		/**
		 * Indicate whether inventory contains article with given id.
		 * 
		 * @param id article id
		 * @return true, if article with id is in inventory
		 */
		boolean containsArticle( String id );

		/**
		 * Return inventory as iterable of Articles (iterable to prevent list manipulation from outside)
		 * 
		 * @return article as iterable
		 */
		Iterable<Article> getInventory();

		/**
		 * Return article from inventory by its id. Returns Optional.
		 * 
		 * @param id article id
		 * @return article as Optional
		 */
		Optional<Article> get( String id );

		/**
		 * Return the number of articles in inventory.
		 * 
		 * @return number of OrderItems
		 */
		long count();

		/**
		 * Add Article to inventory, if article.id is not already present.
		 * 
		 * @param article article added, if article.id is not already present
		 * @return self-reference to allow method chaining
		 */
		InventoryManager add( Article article );

		/**
		 * Remove Article from inventory.
		 * 
		 * @param article article to remove
		 * @return self-reference to allow method chaining
		 */
		InventoryManager remove( Article article );

		/**
		 * Clear all articles from inventory.
		 * 
		 * @return self-reference to allow method chaining
		 */
		void clear();

	}


	/**
	 * Interface of the central order processing component.
	 *
	 */
	interface OrderProcessor {

		/**
		 * Validates order against available inventory. An order can only be
		 * accepted when all(!) its OrderItems can be fulfilled. If an order
		 * is accepted (and only then), inventory is reduced by ordered items.
		 * If order is not accepted, inventory remains unchanged.
		 * 
		 * @param order order to accept
		 * @return true it order is accepted
		 */
		boolean accept( Order order );

		/**
		 * Refined variation of accept( order ) method that allows to pass code
		 * via Java 8's Functional Interfaces that executes in cases the order
		 * is accepted or rejected and when specific order items are rejected.
		 * 
		 * @param order order to accept
		 * @param acceptCode Functional Interface invoked when order is accepted
		 * @param rejectCode Functional Interface invoked when order is rejected
		 * @param rejectedOrderItemCode invoked for each rejected order item
		 * @return
		 */
		boolean accept( Order order,
			Consumer<Order> acceptCode,		// Functional Interface invoked when order is accepted
			Consumer<Order> rejectCode,		// Functional Interface invoked when order is rejected
			Consumer<OrderItem> rejectedOrderItemCode	// invoked for each rejected order item
		);

		/**
		 * Calculate order value as sum of value of all order items.
		 * 
		 * @param order order to calculate
		 * @return value of order in cents (as long)
		 */
		long orderValue( Order order );

		/**
		 * Calculate the Value-Added Tax (VAT) included in a gross value.
		 * E.g. at a 19% VAT tax rate in Germany, a gross value of 49,84 EUR
		 * includes 7,96 EUR VAT.
		 * 
		 * @param grossValue value of which included VAT is calculated
		 * @return included VAT
		 */
		long vat( long grossValue );

		/**
		 * Refined variation to calculate included VAT with different VAT tax
		 * rates auch as 1: 19%, 2: 7%.
		 * 
		 * @param grossValue value of which included VAT is calculated
		 * @param rateIndex VAT tax rate according as index: 1=19%, 2=7%
		 * @return included VAT
		 */
		long vat( long grossValue, int rateIndex );

	}


	/**
	 * Interface for creating business objects from the datamodel package.
	 * DataFactory is the only component in the system that can create
	 * datamodel Objects.
	 *
	 */
	interface DataFactory {

		/**
		 * Create new Customer object.
		 * 
		 * @param name single-String name that is split into first and last name
		 * @param contact customer's contact information
		 * @return new Customer object
		 */
		Customer createCustomer( String name, String contact );

		/**
		 * Create new Article object.
		 * 
		 * @param descr article description
		 * @param price article price
		 * @param units units of article in store
		 * @return new Article object
		 */
		Article createArticle( String descr, long price, int units );

		/**
		 * Create new Order object.
		 * 
		 * @param customer customer to whom the order is associated, must not be null
		 * @return new Order object
		 */
		Order createOrder( Customer customer );

		/**
		 * Create new OrderItem object.
		 * 
		 * @param descr description of ordered item, usually article description
		 * @param article article that is referred to in orderItem
		 * @param units units ordered
		 * @return new OrderItem object
		 */
		OrderItem createOrderItem( String descr, Article article, int units );

	}


	/**
	 * Interface that provides print, reporting and formatting methods
	 * for producing system output.
	 * 
	 */
	public interface OutputProcessor {


		/**
		 * Print orders to System.out in format (example):
		 * 
		 * #5234968294, Eric's Bestellung: 1x Kanne                           20,00 EUR
		 * #8592356245, Eric's Bestellung: 4x Teller, 8x Becher, 4x Tassen    49,84 EUR
		 * #3563561357, Anne's Bestellung: 1x Kanne aus Porzellan             20,00 EUR
		 * #6135735635, Nadine Ulla's Bestellung: 12x Teller blau/weiss Ker.. 77,88 EUR
		 * #4835735356, Timo's Bestellung: 1x Kaffeemaschine, 6x Tasse        47,93 EUR
		 * #6399437335, Sandra's Bestellung: 1x Teekocher, 4x Becher, 4x Te.. 51,91 EUR
		 * -------------                                    ------------- -------------
		 * Gesamtwert aller Bestellungen:                                    267,56 EUR

		 * |<----------------------------<printLineWidth>----------------------------->|
		 * 
		 * @param orders list of orders to print
		 * @param printVAT print included VAT at the end of each line item
		 * 
		 */
		void printOrders( List<Order> orders, boolean printVAT );


		String fmtPrice(long price, String prefix, String postfix);


		/**
		 * Print available inventory.
		 * 
		 */
		void printInventory();


		/**
		 * Format long-price in 1/100 (cents) to String using DecimalFormatter, add
		 * currency and pad to minimum width right-aligned.
		 * For example, 299, "EUR", 12 -> "    2,99 EUR"
		 * 
		 * @param price price as long in 1/100 (cents)
		 * @param currency currency as String, e.g. "EUR"
		 * @return price as String with currency and padded to minimum width
		 */
		String fmtPrice( long price, String currency );


		/**
		 * Format long-price in 1/100 (cents) to String using DecimalFormatter, add
		 * currency and pad to minimum width right-aligned.
		 * For example, 299, "EUR", 12 -> "    2,99 EUR"
		 * 
		 * @param price price as long in 1/100 (cents)
		 * @param currency currency as String, e.g. "EUR"
		 * @param width minimum width to which result is padded
		 * @return price as String with currency and padded to minimum width
		 */
		String fmtPrice( long price, String currency, int width );


		/**
		 * Format line to a left-aligned part followed by a right-aligned part padded to
		 * a minimum width.
		 * For example:
		 * 
		 * <left-aligned part>                          <>       <right-aligned part>
		 * 
		 * "#5234968294, Eric's Bestellung: 1x Kanne         20,00 EUR   (3,19 MwSt)"
		 * 
		 * |<-------------------------------<width>--------------------------------->|
		 * 
		 * @param leftStr left-aligned String
		 * @param rightStr right-aligned String
		 * @param width minimum width to which result is padded
		 * @return String with left- and right-aligned parts padded to minimum width
		 */
		StringBuffer fmtLine( String leftStr, String rightStr, int width );


		/**
		 * Split single-String name to first- and last name and set to the customer object,
		 * e.g. single-String "Eric Meyer" is split into "Eric" and "Meyer".
		 * 
		 * @param customer object for which first- and lastName are set
		 * @param name single-String name that is split into first- and last name
		 * @return returns single-String name extracted from customer object
		 */
		String splitName( Customer customer, String name );

		/**
		 * Returns single-String name obtained from first- and lastName attributes of
		 * Customer object, e.g. "Eric", "Meyer" returns single-String "Meyer, Eric".
		 * 
		 * @param customer object referred to
		 * @return name derived from first- and lastName attributes
		 */
		String singleName( Customer customer );

		
		
	}

}

final class OutputProcessor implements Components.OutputProcessor {

	private static OutputProcessor instance = null;
	private static InventoryManager inventoryManager;
	private OrderProcessor orderProcessor;
	private OutputProcessor outputProcessor;
	
	public OutputProcessor(InventoryManager inventoryManager2, Object orderProcessor2) {
		this.inventoryManager = new InventoryManager();
		this.orderProcessor = new OrderProcessor(inventoryManager);
	}
	
	 public static OutputProcessor getInstance() {
		    if( instance == null ) {
		instance = new OutputProcessor(inventoryManager, instance);
		    }
		    return instance;
		  }
	
	
	@Override
	  public void printOrders( List<Order> orders, boolean printVAT ) {
		{
			{
				
				StringBuffer sbAllOrders = new StringBuffer( "-------------" );
				StringBuffer sbLineItem = new StringBuffer();
				String fmtPrice = null;
				String multiOrder = null;
			
				String desc = null;
				int numUnit;
				long price = 0;
				long orderID = 0;
				long total = 0;
				Customer customer;
				
						
				
				//String cName = null;
				for(Order order : orders) {
					multiOrder = "";
					price = 0;
					customer = order.getCustomer();
					ArrayList <OrderItem> list = order.getItems();
					orderID = order.getId();
					//cName = order.getCustomer().getFirstName();
					for(OrderItem oi : list) {
						desc = oi.getDescription();
						numUnit = oi.getUnitsOrdered();
						price += oi.getArticle().getUnitPrice()*numUnit;
						fmtPrice = fmtPrice( price, "EUR", 14 );
						multiOrder = multiOrder + ", " + Integer.toString(numUnit) + "x " + desc;
						
						
					
					}
					sbAllOrders.append( "\n" );
					multiOrder = multiOrder.substring(1);
					total = total + price;
					sbLineItem = fmtLine( "#" + Long.toString(orderID) + ", " + customer.getlastName() + ", " + customer.getFirstName() + "'s Bestellung: " + multiOrder, fmtPrice, Application_2.printLineWidth );
					sbAllOrders.append( sbLineItem );
					
				}

				//sbLineItem = fmtLine( "Zweite Bestellung: einzelne Bestellpositionen (orderItems)", fmtPrice2, printLineWidth );

				// calculate total price
				String fmtPriceTotal = pad( fmtPrice( total, "" + " EUR" ), 14, true );

				// append final line with totals
				sbAllOrders.append( "\n" )
					.append( fmtLine( "-------------", "-------------", Application_2.printLineWidth ) )
					.append( "\n" )
					.append( fmtLine( "Gesamtwert aller Bestellungen:", fmtPriceTotal, Application_2.printLineWidth ) )
					.append( "\n" );
				
				 
				 ;
				 if(printVAT) 
				 {
					 sbAllOrders.append(fmtLine( "Im Gesamtbetrag enthaltene Mehrwertsteuer (19%):", fmtPrice(orderProcessor.vat(total), "" + " EUR" ), Application_2.printLineWidth));
				 }
			
				System.out.println( sbAllOrders.toString() );
				
				
			
				
			}
			
		}
	  }
	
	
	

	private String pad( String str, int width, boolean rightAligned ) {
		String fmtter = ( rightAligned? "%" : "%-" ) + width + "s";
		String padded = String.format( fmtter, str );
		return padded;
	}
	
	
	@Override
	public void printInventory() {
	}

	@Override
	public String fmtPrice(long price, String currency) {
		
			String fmtPrice = pad( fmtPrice( price, "", " " + currency ), 14, true );
			return fmtPrice;
		
	}
	
	
	

	@Override
	public String fmtPrice(long price, String currency, int width) {
		{
			String fmtPrice = pad( fmtPrice( price, "", " " + currency ), 14, true );
			return fmtPrice;
		}
	}
	
	
	public String fmtPrice( long price, String prefix, String postfix ) {
		StringBuffer fmtPriceSB = new StringBuffer();
		if( prefix != null ) {
			fmtPriceSB.append( prefix );
		}

		fmtPriceSB = fmtPrice( fmtPriceSB, price );

		if( postfix != null ) {
			fmtPriceSB.append( postfix );
		}
		return fmtPriceSB.toString();
	}
	
	
	private StringBuffer fmtPrice( StringBuffer sb, long price ) {
		if( sb == null ) {
			sb = new StringBuffer();
		}
		double dblPrice = ( (double)price ) / 100.0;			// convert cent to Euro
		DecimalFormat df = new DecimalFormat( "#,##0.00",
			new DecimalFormatSymbols( new Locale( "de" ) ) );	// rounds double to 2 digits

		String fmtPrice = df.format( dblPrice );				// convert result to String in DecimalFormat
		sb.append( fmtPrice );
		return sb;
	}

	@Override
	public StringBuffer fmtLine( String leftStr, String rightStr, int totalWidth ) {
		StringBuffer sb = new StringBuffer( leftStr );
		int shiftable = 0;		// leading spaces before first digit
		for( int i=1; rightStr.charAt( i ) == ' ' && i < rightStr.length(); i++ ) {
			shiftable++;
		}
		final int tab1 = totalWidth - rightStr.length() + 1;	// - ( seperator? 3 : 0 );
		int sbLen = sb.length();
		int excess = sbLen - tab1 + 1;
		int shift2 = excess - Math.max( 0, excess - shiftable );
		if( shift2 > 0 ) {
			rightStr = rightStr.substring( shift2, rightStr.length() );
			excess -= shift2;
		}
		if( excess > 0 ) {
			switch( excess ) {
			case 1:	sb.delete( sbLen - excess, sbLen ); break;
			case 2: sb.delete( sbLen - excess - 2 , sbLen ); sb.append( ".." ); break;
			default: sb.delete( sbLen - excess - 3, sbLen ); sb.append( "..." ); break;
			}
		}
		String strLineItem = String.format( "%-" + ( tab1 - 1 ) + "s%s", sb.toString(), rightStr );
		sb.setLength( 0 );
		sb.append( strLineItem );
		return sb;
	}
	
	
	@Override
	public String splitName(Customer customer, String name) {
		if(name.contains(","))
		{
	String[] firstLastName = name.split(",");
	customer.firstName = firstLastName[1].trim();
	customer.lastName = firstLastName[0].trim();
		}
		return customer.firstName + customer.lastName;
	}

	@Override
	public String singleName(Customer customer) {
		return customer.lastName + ", " + customer.firstName;
	}
}

final class InventoryManager implements Components.InventoryManager{
	
	private static InventoryManager instance = null;
	
	 public InventoryManager() {	 
	 }
	 
	 public static InventoryManager getInstance() {
		    if( instance == null ) {
		instance = new InventoryManager();
		    }
		    return instance;
		  }

	@Override
	public boolean containsArticle(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Article> getInventory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Article> get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public system.Components.InventoryManager add(Article article) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public system.Components.InventoryManager remove(Article article) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	
}

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
			
			return (long) (grossValue * (0.07 / 1.));
		}
		
		else return 0;
	
}

}