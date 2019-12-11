package domain.storage;

import domain.game.GameInitializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class StorageHandler {
    private static StorageHandler ourInstance = new StorageHandler();

    public static StorageHandler getInstance() {
        return ourInstance;
    }
    private SavedGame savedGame;

    private StorageHandler() {
        savedGame= null;
    }

    protected void saveGameToFile(String fileName) {
        File file = new File(fileName);
        SavedGame savedGame = new SavedGame(); // TODO: implement savedGame or something that generates it
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(savedGame);
            objectOut.close();
        } catch (Exception e) {
            System.out.println("File out error: " + e.toString());
            e.printStackTrace();
        }

    }

    protected void loadSavedGame(String fileName) {
        try {
            FileInputStream fileIn = new FileInputStream(new File(fileName));
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            savedGame = (SavedGame) objectIn.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        GameInitializer.setLoadedGame(savedGame);


    }
    protected SavedGame getSavedGame(){
        return savedGame;
    }
}
