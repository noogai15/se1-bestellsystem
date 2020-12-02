package application;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import datamodel.Article;
import datamodel.Customer;
import datamodel.Order;
import datamodel.OrderItem;


/**
 * 
 * Main class to launch the application.
 * 
 * @author svgr64
 *
 */


public class Application_1 {

	public final static int printLineWidth = 84;
	private final static Customer cEric = new Customer( "C86516", "Eric Schulz-Mueller", "eric2346@gmail.com" );
	private final static Customer cAnne = new Customer( "C64327", "Meyer, Anne", "+4917223524" );
	private final static Customer cNadine = new Customer( "C12396", "Nadine Ulla Blumenfeld", "+4915292454" );

	private final static Article aTasse = new Article( "SKU-458362", "Tasse", 299, 2000 );
	private final static Article aBecher = new Article( "SKU-693856", "Becher", 149, 8400 );
	private final static Article aKanne = new Article( "SKU-518957", "Kanne", 2000, 2400 );
	private final static Article aTeller = new Article( "SKU-638035", "Teller", 649, 7000 );


	/**
	 * main() to launch application.
	 * 
	 * @param args command line arguments
	 */
	
	
	
	
	public static void main( String[] args ) {

		System.out.println( "SE1-Bestellsystem" );

		Application_1 application = new Application_1();



		// Eric's 1st order
		Order o5234 = new Order( 5234968294L, new Date(), cEric );
		OrderItem oi1 = new OrderItem( aKanne.getDescription(), aKanne, 1 );	// 1x Kanne
		o5234.addItem( oi1 );	// add OrderItem to Order 5234968294L

		// Eric's 2nd order
		Order o8592 = new Order( 8592356245L, new Date(), cEric );
		o8592.addItem(	// add three OrderItems to Order 8592356245L
			new OrderItem( aTeller.getDescription(), aTeller, 4 )		// 4x Teller
		).addItem(
			new OrderItem( aBecher.getDescription(), aBecher, 8 )		// 8x Becher
		).addItem(
			new OrderItem( "passende Tassen", aTasse, 4 )				// 4x passende Tassen
		);

		// Anne's order
		Order o3563 = new Order( 3563561357L, new Date(), cAnne );
		o3563.addItem(
			new OrderItem( aKanne.getDescription() + " aus Porzellan", aKanne, 1 )
		);

		// Nadine's order
		Order o6135 = new Order( 6135735635L, new Date(), cNadine );
		o6135.addItem(													// 12x Teller
			new OrderItem( aTeller.getDescription() + " blau/weiss Keramik", aTeller, 12 )
		);



		//Neue Kunden
		Customer cTimo = new Customer("C13126", "Timo Werner", "tw@gmail.com");
		Customer cSandra = new Customer("C28842", "Sandra Müller", "samue62@gmx.de");
		//Neue Artikel
		Article aKMaschine = new Article("SKU-746283", "Kaffeemaschine", 2999, 500); 
		Article aTeeKocher = new Article("SKU-265736", "Teekocher", 1999, 2000);
		//Timo's order
		Order o2857 = new Order(2658274837L, new Date(), cTimo);
		o2857.addItem(
				new OrderItem(aKMaschine.getDescription(), aKMaschine, 1))
		.addItem(
				new OrderItem(aTasse.getDescription(), aTasse, 6));
		//Sandra's order
		Order o5182 = new Order(8362857382L, new Date(), cSandra);
		o5182.addItem(
				new OrderItem(aTeeKocher.getDescription(), aTeeKocher, 1))
		.addItem(
				new OrderItem(aBecher.getDescription(), aBecher, 4))
		.addItem(
				new OrderItem(aTeller.getDescription(), aTeller, 4));
		
		List<Order> orders = new ArrayList<Order>( List.of( o5234, o8592, o3563, o6135, o2857, o5182 ) );
		
		application.printOrders( orders );		// Ausgabe aller Bestellungen
		
	}

	


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
	 * 
	 */
	private void printOrders( List<Order> orders ) {
		
		StringBuffer sbAllOrders = new StringBuffer( "-------------" );
		StringBuffer sbLineItem = new StringBuffer();
		String fmtPrice = null;
		String multiOrder = null;
	
		String desc = null;
		int numUnit = 0;
		long price = 0;
		long orderID = 0;
		long total = 0;
		String cName = null;
		for(Order order : orders) {
			multiOrder = "";
			price = 0;
			ArrayList <OrderItem> list = order.getItems();
			orderID = order.getId();
			cName = order.getCustomer().getFirstName();
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
			sbLineItem = fmtLine( "#" + Long.toString(orderID) + ", " + cName + "'s Bestellung: " + multiOrder, fmtPrice, printLineWidth );
			sbAllOrders.append( sbLineItem );
			
		}






		
		
		
		
		/*
		 * Insert code to print orders with all order items:
		 */

		// convert price (long: 2345 in cent) to String of length 14, right-aligned -> "     23,45 EUR"



		// format line item with left-aligned part1 and right-aligned-part2 of total length printLineWidth
		// and append to sbLineItem StringBuffer

		

		

		// append sbLineItem to sbAllOrders StringBuffer where all orders are collected


		// format another line for second order
		String fmtPrice2 = fmtPrice( 67890, "EUR", 14 );
		//sbLineItem = fmtLine( "Zweite Bestellung: einzelne Bestellpositionen (orderItems)", fmtPrice2, printLineWidth );

		// calculate total price
		String fmtPriceTotal = pad( fmtPrice( total, "", " EUR" ), 14, true );

		// append final line with totals
		sbAllOrders.append( "\n" )
			.append( fmtLine( "-------------", "-------------", printLineWidth ) )
			.append( "\n" )
			.append( fmtLine( "Gesamtwert aller Bestellungen:", fmtPriceTotal, printLineWidth ) );

		// print sbAllOrders StringBuffer with all output to System.out
		 
		 ;
		
		
		System.out.println( sbAllOrders.toString() );
	
	
	}


	/*
	 * Private methods.
	 */

	/**
	 * Format long-price in 1/100 (cents) to String using DecimalFormatter.
	 * Append currency. For example, 299, "EUR" -> "2,99 EUR"
	 * 
	 * @param price price as long in 1/100 (cents)
	 * @param currency currency as String, e.g. "EUR"
	 * @return price as String with currency and padded to minimum width
	 */
	private String fmtPrice( long price, String currency ) {
		String fmtPrice = pad( fmtPrice( price, "", " " + currency ), 14, true );
		return fmtPrice;
	}


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
	private String fmtPrice( long price, String currency, int width ) {
		String fmtPrice = pad( fmtPrice( price, "", " " + currency ), 14, true );
		return fmtPrice;
	}


	/**
	 * Format long-price in 1/100 (cents) to String using DecimalFormatter and
	 * prepend prefix and append postfix String.
	 * For example, 299, "(", ")" -> "(2,99)"
	 * 
	 * @param price price as long in 1/100 (cents)
	 * @param prefix String to prepend before price
	 * @param postfix String to append after price
	 * @return price as String
	 */
	public static String fmtPrice( long price, String prefix, String postfix ) {
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


	/**
	 * Format long-price in 1/100 (cents) to String using DecimalFormatter.
	 * For example, 299 -> "2,99"
	 * 
	 * @param sb StringBuffer to which price is added
	 * @param price price as long in 1/100 (cents)
	 * @return StringBuffer with added price
	 */
	private static StringBuffer fmtPrice( StringBuffer sb, long price ) {
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


	/**
	 * Pad string to minimum width, either right-aligned or left-aligned
	 * 
	 * @param str String to pad
	 * @param width minimum width to which result is padded
	 * @param rightAligned flag to chose left- or right-alignment
	 * @return padded String
	 */
	private String pad( String str, int width, boolean rightAligned ) {
		String fmtter = ( rightAligned? "%" : "%-" ) + width + "s";
		String padded = String.format( fmtter, str );
		return padded;
	}


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
	 * @param totalWidth minimum width to which result is padded
	 * @return String with left- and right-aligned parts padded to minimum width
	 */
	private StringBuffer fmtLine( String leftStr, String rightStr, int totalWidth ) {
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
	
}*/
