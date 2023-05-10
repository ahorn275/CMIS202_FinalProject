// **********************************************************************************
// Title: Data Save Thread
// Author: Autumn Horn
// Course Section: CMIS202-ONL1 (Seidel) Spring 2023
// File: DataSaveThread.java
// Description: Creates a Thread for saving a UserHashTable and a WineTree into their
//     respective files using ObjectOutputStreams
// **********************************************************************************
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class DataSaveThread extends Thread {
    private UserHashTable users;
    private WineTree wines;

    public DataSaveThread(UserHashTable users, WineTree wines) {
        this.users = users;
        this.wines = wines;
    }

    /** Write user hash table and wine tree to files using object output streams */
    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("files/users.dat"))) {
            out.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("files/wines.dat"))) {
            out.writeObject(wines);
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
