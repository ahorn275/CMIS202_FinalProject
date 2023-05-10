// **********************************************************************************
// Title: Admin class
// Author: Autumn Horn
// Course Section: CMIS202-ONL1 (Seidel) Spring 2023
// File: Admin.java
// Description: Creates an Admin which extends from the User class (and so is
//        Serializable) and has a pin
// **********************************************************************************
public class Admin extends User {
    private String pin;

    /** Constructor sets admin to true */
    public Admin(String name, String username, String password, String pin) {
        super(name, username, password);
        this.pin = pin;
    }

    /** Accessor */
    public String getPin() {
        return pin;
    }

    /** Mutator */
    public void setPin(String pin) {
        this.pin = pin;
    }
}
