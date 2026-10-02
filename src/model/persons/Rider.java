package model.persons;
import interfaces.IRider;

public class Rider extends Person implements IRider {
    private String email;
    private String homeCity;

    public Rider(int id, String name, String phoneNumber, String email, String homeCity) {
        super(id, name, phoneNumber);
        setEmail(email);
        setHomeCity(homeCity);
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getHomeCity() {
        return homeCity;
    }

    @Override
    public String toString() {
        return "";
    }

    // Compares this rider with another rider based on rider ID.
    @Override
    public int compareTo(IRider other) {
        return 0;
    }
}
