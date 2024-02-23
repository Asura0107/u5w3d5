package u5w3d5.u5w3d5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import u5w3d5.u5w3d5.dto.EventDTO;
import u5w3d5.u5w3d5.entities.Events;
import u5w3d5.u5w3d5.entities.User;
import u5w3d5.u5w3d5.services.EventsService;
import u5w3d5.u5w3d5.services.EventsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    private EventsService eventsService;

    @GetMapping
    public Page<Events> getAllEventss(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String orderBy
    ) {
        return this.eventsService.getEvents(page, size, orderBy);
    }


    @GetMapping("/{id}")
    public Events findById(@PathVariable UUID id) {
        return this.eventsService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.CREATED)
    public Events saveEvent(@RequestBody EventDTO eventDTO){
        return this.eventsService.save(eventDTO);
    }

    @PostMapping("/me/add/event")
    @ResponseStatus(HttpStatus.CREATED)
    public Events aggiungiEvento(@RequestParam UUID eventId, @AuthenticationPrincipal User currentAuthenticatedUser){
        this.eventsService.reserve(eventId,currentAuthenticatedUser);
        return this.eventsService.findById(eventId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Events findByIdAndUpdate(@PathVariable UUID id, @RequestBody Events updatedEvents) {

        return this.eventsService.findByIdAndUpdate(id, updatedEvents);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID id) {
        this.eventsService.findByIdAndDelete(id);
    }

}
