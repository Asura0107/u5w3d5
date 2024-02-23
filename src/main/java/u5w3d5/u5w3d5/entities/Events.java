package u5w3d5.u5w3d5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Events {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String description;
    private LocalDate date;
    private String place;
    private int maxposti;
    @ManyToMany
    @JoinTable(
            name = "user_events",
            joinColumns = @JoinColumn(name = "events_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;


    public Events(String title, String description, LocalDate date, String place, int maxposti) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.place = place;
        this.maxposti = maxposti;
        this.users=new ArrayList<>();
    }
    public void addUser(User user){
        this.users.add(user);
    }

    @Override
    public String toString() {
        return "Events{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", place='" + place + '\'' +
                ", maxposti=" + maxposti +
                ", users=" + users +
                '}';
    }
}
