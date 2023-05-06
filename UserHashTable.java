import java.io.*;
import java.util.LinkedList;

public class UserHashTable implements Serializable {
    private LinkedList<User>[] table;
    private int size;
    private int capacity;
    private double loadFactor;

    /** Constructor */
    public UserHashTable(int capacity, double loadFactor) {
        this.capacity = capacity;
        this.loadFactor = loadFactor;
        size = 0;
        table = new LinkedList[capacity];
    }

    /** Default constructor with default capacity and load factor */
    public UserHashTable() {
        this(20, 0.75);
    }

    /** Get the hash code for a given username */
    private int getHashCode(String username) {
        int hash = 0;

        for (int i = 0; i < username.length(); i++)
            hash = (31 * hash + username.charAt(i));

        return Math.abs(hash) % capacity;
    }

    /** Add a user to the hash table */
    public void addUser(User user) {
        if ((double) size / capacity >= loadFactor) {
            resizeTable();
        }
        int index = getHashCode(user.getUsername());
        if (table[index] == null) {
            table[index] = new LinkedList<User>();
        }
        table[index].add(user);
        size++;
    }

    /** Remove a user from the hash table */
    public void removeUser(User user) {
        int index = getHashCode(user.getUsername());
        if (table[index] != null) {
            table[index].remove(user);
            size--;
        }
    }

    /** Check if username exists in the hash table */
    public boolean containsUsername(String username) {
        int index = getHashCode(username);
        if (table[index] != null) {
            for (User user : table[index]) {
                if (user.getUsername().equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }


    /** Check if a user exists in the hash table */
    public boolean containsUser(User user) {
        int index = getHashCode(user.getUsername());
        if (table[index] != null) {
            for (User u : table[index]) {
                if (u.getUsername().equals(user.getUsername())) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Get a user from the hash table */
    public User getUser(String username) {
        int index = getHashCode(username);
        if (table[index] != null) {
            for (User user : table[index]) {
                if (user.getUsername().equals(username)) {
                    return user;
                }
            }
        }
        return null;
    }

    /** Verify a user by their username and password */
    public boolean verifyUser(String username, String password) {
        if (containsUsername(username)) {
            User user = getUser(username);
            if (user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }


    /** Checks if hash table is empty */
    public boolean isEmpty() {
        for (LinkedList<User> list : table) {
            if (list != null && !list.isEmpty()) {
                return false;
            }
        }
        return true;
    }


    /** Resize the hash table */
    private void resizeTable() {
        capacity *= 2;
        LinkedList<User>[] oldTable = table;
        table = new LinkedList[capacity];
        size = 0;
        for (LinkedList<User> list : oldTable) {
            if (list != null) {
                for (User user : list) {
                    addUser(user);
                }
            }
        }
    }

    /** Save hash table to a file */
    public void saveToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(filename)))) {

            // Write the hash table object to the file
            out.writeObject(this);

            // Flush the output stream to ensure all data is written to the file
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}