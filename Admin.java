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
