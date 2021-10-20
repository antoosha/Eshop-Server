package korolov.project.domain;

import org.springframework.context.annotation.Scope;
import java.util.Objects;

@Scope("prototype")
public class Client {
    private String name;
    private String surname;
    private final String username;
    private String email;

    public Client(String name, String surname, String username, String email) {
        this.name = name;
        this.surname = surname;
        this.username = Objects.requireNonNull(username);
        this.email = Objects.requireNonNull(email);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return username.equals(client.username) || email.equals(client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }

    @Override
    public String toString() {
        return "Client{" + name + " " +  surname + ", username='" + username + '\'' + ", email='" + email + '\'' + '}';
    }
}
