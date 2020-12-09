package system;

import java.util.Optional;

import datamodel.Article;
import system.InventoryManager;

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
