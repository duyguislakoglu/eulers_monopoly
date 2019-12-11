package domain.storage;

import domain.game.GameControler;
import domain.game.MonopolyGame;

import java.io.Serializable;

public class SavedGame implements Serializable {
    private MonopolyGame monopolyGame;
    public SavedGame(){
        monopolyGame = GameControler.monopolyGame;
    }
    public MonopolyGame getMonopolyGame(){
        return monopolyGame;
    }
}
