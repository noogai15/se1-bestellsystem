package application;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import datamodel.Article;
import datamodel.Customer;
import datamodel.Order;
import datamodel.OrderItem;
import system.ComponentFactory;
import system.Components;


public final class Application_2 {
	
	public final static int printLineWidth = 95;


	  public static void main( String[] args ) {
		  		  
	    System.out.println( "SE1-Bestellsystem" );
	    ComponentFactory componentFactory = ComponentFactory.getInstance();
	    Components.OutputProcessor outputProcessor =  componentFactory.getOutputProcessor();
	    Components.DataFactory dataFactory = componentFactory.getDataFactory();
	    Components.OrderProcessor orderProcessor =componentFactory.getOrderProcessor();
	    
		
		Customer cEric = dataFactory.createCustomer( "Schulz-Mueller, Eric", "eric2346@gmail.com" );
		Customer cAnne = dataFactory.createCustomer( "Anne, Meyer", "+4917223524" );
		Customer cNadine = dataFactory.createCustomer( "Ulla Blumenfeld, Nadine", "+4915292454" );

		final Article aTasse = dataFactory.createArticle("Tasse", 299, 2000);
		final Article aBecher = dataFactory.createArticle( "Becher", 149, 8400 );
		final Article aKanne = dataFactory.createArticle( "Kanne", 2000, 2400 );
		final Article aTeller = dataFactory.createArticle( "Teller", 649, 7000 );
	    
	    
	    
		// Eric's 1st order
		//Order o5234 = new Order( 5234968294L, new Date(), cEric );
	    Order o5234 = dataFactory.createOrder(cEric);
		OrderItem oi1 = dataFactory.createOrderItem(aKanne.getDescription(), aKanne, 1);	// 1x Kanne
		
		o5234.addItem( oi1 );	// add OrderItem to Order 5234968294L

		// Eric's 2nd order
		Order o8592 = dataFactory.createOrder(cEric);
		o8592.addItem(	// add three OrderItems to Order 8592356245L
			dataFactory.createOrderItem( aTeller.getDescription(), aTeller, 4 )		// 4x Teller
		).addItem(
				dataFactory.createOrderItem( aBecher.getDescription(), aBecher, 8 )		// 8x Becher
		).addItem(
				dataFactory.createOrderItem( "passende Tassen", aTasse, 4 )				// 4x passende Tassen
		);

		// Anne's order
		Order o3563 =  dataFactory.createOrder( cAnne );
		o3563.addItem(
				dataFactory.createOrderItem( aKanne.getDescription() + " aus Porzellan", aKanne, 1 )
		);

		// Nadine's order
		Order o6135 = dataFactory.createOrder( cNadine );
		o6135.addItem(													// 12x Teller
				dataFactory.createOrderItem( aTeller.getDescription() + " blau/weiss Keramik", aTeller, 12 )
		);



		//Neue Kunden
		Customer cTimo = dataFactory.createCustomer( "Werner, Timo", "tw@gmail.com");
		
		Customer cSandra = dataFactory.createCustomer( "Müller, Sandra", "samue62@gmx.de");
		//Neue Artikel
		Article aKMaschine = dataFactory.createArticle( "Kaffeemaschine", 2999, 500); 
		Article aTeeKocher = dataFactory.createArticle( "Teekocher", 1999, 2000);
		//Timo's order
		Order o2857 = dataFactory.createOrder( cTimo);
		o2857.addItem(
				dataFactory.createOrderItem (aKMaschine.getDescription(), aKMaschine, 1))
		.addItem(
				dataFactory.createOrderItem (aTasse.getDescription(), aTasse, 6));
		//Sandra's order
		Order o5182 = dataFactory.createOrder( cSandra);
		o5182.addItem(
				dataFactory.createOrderItem (aTeeKocher.getDescription(), aTeeKocher, 1))
		.addItem(
				dataFactory.createOrderItem (aBecher.getDescription(), aBecher, 4))
		.addItem(
				dataFactory.createOrderItem (aTeller.getDescription(), aTeller, 4));
	    
	    
	    List<Order> orders = new ArrayList<Order>( List.of(o5234, o8592, o3563, o6135, o2857, o5182) );
	    outputProcessor.printOrders( orders, true ); // Ausgabe aller Bestellungen
	  }
	}
