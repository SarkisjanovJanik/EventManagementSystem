package sia.eventmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sia.eventmanagementsystem.entity.Event;
import sia.eventmanagementsystem.repo.EventRepo;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;

    public Event createEvent(Event event){
      return eventRepo.save(event);
    }

    public Event getEventById (Long eventId) {
        return eventRepo.findById(eventId).orElseThrow(
                () -> new EntityNotFoundException("Event isn't found by id " +eventId));
    }

    public List<Event> getAllEvents () {
        return eventRepo.findAll();
    }



    public void deleteAttendeesFromEvent(Long eventId, Set<Long> attendeeIds) {
        Event event = getEventById(eventId);

        event.getAttendees().removeIf(user -> attendeeIds.contains(user.getId()));
        eventRepo.save(event);
    }




    public Event updateEvent (Event event, Long eventId){
        Event existingEvent = eventRepo.findById(eventId).orElseThrow(
                () -> new EntityNotFoundException("Event isn't found by id " + eventId));
        if (event.getDescription() != null){
            existingEvent.setDescription(event.getDescription());
        }
        if (event.getName() != null){
            existingEvent.setName(event.getName());
        }
        if (event.getLocation() != null) {
            existingEvent.setLocation(event.getLocation());
        }
        if (event.getOrganizer() != null) {
            existingEvent.setOrganizer(event.getOrganizer());
        }
        if (event.getAttendees() != null) {
            existingEvent.setAttendees(event.getAttendees());
        }
        return eventRepo.save(existingEvent);
    }

    public void deleteEventById (Long eventId){
        if (!eventRepo.existsById(eventId)){
            throw new EntityNotFoundException("Event doesn't exists by id " + eventId);
        }
        eventRepo.deleteById(eventId);
    }
}
