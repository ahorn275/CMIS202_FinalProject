// **********************************************************************************
// Title: Data Load Thread
// Author: Autumn Horn
// Course Section: CMIS202-ONL1 (Seidel) Spring 2023
// File: DataLoadThread.java
// Description: Creates a thread for loading in and storing a UserHashTable and a
//      WineTree from their respective files using ObjectInputStreams
// **********************************************************************************
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DataLoadThread extends Thread {
    private UserHashTable users;
    private WineTree wines;

    /** Accessors */
    public UserHashTable getUsers() {
        return users;
    }

    public WineTree getWines() {
        return wines;
    }

    @Override
    public void run() {
        users = loadUsersFromFile("files/users.dat");
        wines = loadWinesFromFile("files/wines.dat");
    }

    /** Load in hash table from save file */
    public static UserHashTable loadUsersFromFile(String filename) {
        UserHashTable loadedTable = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {

            loadedTable = (UserHashTable) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loadedTable;
    }

    /** Load in wine tree from save file */
    public static WineTree loadWinesFromFile(String filename) {
        WineTree loadedTree = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {

            loadedTree = (WineTree) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return loadedTree;
    }
}
