import java.io.Serializable;

public class Saved implements Serializable {
    private String account;
    private String password;

    public Saved(String account, String password) {
        this.account = account;
        this.password = password;
    }

    // getters and setters
    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
