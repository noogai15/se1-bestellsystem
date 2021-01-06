package system;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import application.Application_2;
import datamodel.Customer;
import datamodel.Order;
import datamodel.OrderItem;
import system.OrderProcessor;
import system.OutputProcessor;

public final class OutputProcessor implements Components.OutputProcessor {

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
	
	
	
	public void printInventory() {
	}

	
	public String fmtPrice(long price, String currency) {
		
			String fmtPrice = pad( fmtPrice( price, "", " " + currency ), 14, true );
			return fmtPrice;
		
	}
	
	
	

	
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
	
	
	
	public String splitName(Customer customer, String name) {
		if(name.contains(","))
		{
	String[] firstLastName = name.split(",");
	customer.firstName = firstLastName[1].trim();
	customer.lastName = firstLastName[0].trim();
		}
		return customer.firstName + customer.lastName;
	}

	
	public String singleName(Customer customer) {
		return customer.lastName + ", " + customer.firstName;
	}
}