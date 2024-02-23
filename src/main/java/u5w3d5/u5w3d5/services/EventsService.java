package u5w3d5.u5w3d5.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import u5w3d5.u5w3d5.dao.EventsDAO;
import u5w3d5.u5w3d5.dto.EventDTO;
import u5w3d5.u5w3d5.entities.Events;
import u5w3d5.u5w3d5.entities.User;
import u5w3d5.u5w3d5.exception.BadRequestException;
import u5w3d5.u5w3d5.exception.NotFoundException;

import java.util.UUID;
@Service
public class EventsService {
    @Autowired
    private EventsDAO eventsDAO;


    public Page<Events> getEvents(int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return eventsDAO.findAll(pageable);
    }


    public Events findById(UUID eventId) {
        return eventsDAO.findById(eventId).orElseThrow(() -> new NotFoundException(eventId));
    }

    public Events findByIdAndUpdate(UUID EventsId, Events updateEvents) {
        Events found = this.findById(EventsId);

        return eventsDAO.save(found);
    }
    public Events save(EventDTO newevent) {
        return eventsDAO.save(
                new Events(newevent.title(),newevent.description(),newevent.date(),newevent.place(), newevent.maxposti())
        );
    }

    public void findByIdAndDelete(UUID EventsId) {
        Events found = this.findById(EventsId);
        eventsDAO.delete(found);
    }


}
