package domain.game;

public class CardFactory {

	public static Cards createCard(String type, int id, String name) {  
		
	      Cards card = null;
	      if (type == "chance")  
	            card = new ChanceCard(id, name);  
	         else if(type == "rollThree")  
	            card = new RollThree(id, name);  
	         else if(type == "community")
	            card = new CommunityCards(id, name);
	        return card;
	   }
	
}
