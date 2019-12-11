package domain.game;

import domain.storage.SavedGame;

public class GameInitializer {
    private static SavedGame loadedGame;
    private static MonopolyGame monopolyGame;

    public static void setLoadedGame(SavedGame savedGame){
        loadedGame= savedGame;
    }
    public static void initializeGame(){
        if(loadedGame == null){
            monopolyGame=  new MonopolyGame();
        }else{
            monopolyGame= loadedGame.getMonopolyGame();
            System.out.println("Loaded game");
            monopolyGame.initiliazeSerializedGame();
        }
    }
    public static MonopolyGame getMonopolyGame(){
        return monopolyGame;
    }
}
