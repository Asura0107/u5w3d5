package u5w3d5.u5w3d5.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import u5w3d5.u5w3d5.dao.EventsDAO;
import u5w3d5.u5w3d5.dao.UserDAO;
import u5w3d5.u5w3d5.entities.Events;
import u5w3d5.u5w3d5.entities.User;
import u5w3d5.u5w3d5.exception.BadRequestException;
import u5w3d5.u5w3d5.exception.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDAO usersDAO;
    @Autowired
    private EventsService eventsService;
    @Autowired
    private EventsDAO eventsDAO;


    public Page<User> getUsers(int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return usersDAO.findAll(pageable);
    }


    public User findById(UUID userId) {
        return usersDAO.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User findByIdAndUpdate(UUID userId, User updateUser) {
        User found = this.findById(userId);
        found.setSurname(updateUser.getSurname());
        found.setName(updateUser.getName());
        found.setEmail(updateUser.getEmail());
        found.setPassword(updateUser.getPassword());
        return usersDAO.save(found);
    }

    public void findByIdAndDelete(UUID userId) {
        User found = this.findById(userId);
        usersDAO.delete(found);
    }

    public User findByEmail(String email) {
        return usersDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Email " + email + " non trovata"));
    }

    public List<Events> getAllEvent(UUID userId){
        User user=this.findById(userId);
        return user.getEvents();
    }
    public void eleminateEvent(UUID userId,UUID eventId){
        User user=this.findById(userId);
        Optional<Events> events1= user.getEvents().stream().filter(u -> u.getId().equals(eventId)).findFirst();
        if (events1.isPresent()){
            Events removeEvent= events1.get();
            user.getEvents().remove(removeEvent);
            usersDAO.save(user);
        }else {
            throw new NotFoundException("non è stato trovato l'evento");
        }
    }


}
