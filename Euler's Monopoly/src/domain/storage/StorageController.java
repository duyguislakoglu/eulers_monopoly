package domain.storage;

import domain.network.NetworkController;
import domain.network.actions.Action;
import domain.network.actions.LoadedGameAction;

public class StorageController {
    private static StorageController ourInstance = new StorageController();

    public static StorageController getInstance() {
        return ourInstance;
    }

    private StorageController() {

    }

    public void saveGame(String fileName){
        StorageHandler.getInstance().saveGameToFile(fileName);
    }
    public void loadGame(String fileName){
        StorageHandler.getInstance().loadSavedGame(fileName);
        Action action= new LoadedGameAction(getSavedGame());
        NetworkController.getInstance().sendAction(action);
    }
    public SavedGame getSavedGame(){ return StorageHandler.getInstance().getSavedGame();}

}
