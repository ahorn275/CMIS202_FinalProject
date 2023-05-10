// **********************************************************************************
// Title: User Class
// Author: Autumn Horn
// Course Section: CMIS202-ONL1 (Seidel) Spring 2023
// File: User.java
// Description: Creates a Serializable User object with a name, username, password,
//     and WineList of favorite wines
// **********************************************************************************
import java.io.Serializable;

public class User implements Serializable {
    private String name = "";
    private String username = "";
    private String password = "";
    private WineList favoriteWines = new WineList();

    /** Constructor */
    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    /** Accessors */
    public String getName() {
        return name;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public WineList getFavoriteWines() {
        return favoriteWines;
    }

    /** Mutators */
    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /** Add a wine to favorite wine array list */
    public void addFavoriteWine(Wine wine) {
        favoriteWines.add(wine);
    }

    /** Remove a wine from favorite wine array list */
    public void unFavoriteWine(Wine wine) {
        favoriteWines.remove(wine);
    }
}
