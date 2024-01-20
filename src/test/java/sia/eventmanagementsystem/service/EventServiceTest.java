package sia.eventmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sia.eventmanagementsystem.entity.Event;
import sia.eventmanagementsystem.repo.EventRepo;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepo eventRepo;

    @InjectMocks
    private EventService eventService;

    @Test
    public void testCreateEvent() {
        Event event = new Event();
        when(eventRepo.save(event)).thenReturn(event);

        Event result = eventService.createEvent(event);

        assertNotNull(result);
    }

    @Test
    public void testGetEventById() {
        Long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);
        event.setName("Birthday Party");
        event.setDescription("A celebration of joy");

        when(eventRepo.findById(eventId)).thenReturn(Optional.of(event));

        Event result = eventService.getEventById(eventId);

        assertEquals(eventId, result.getId());
        assertEquals(event.getName(), result.getName());
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenGetEventById() {
        Long invalidEventId = -1L;

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> eventService.getEventById(invalidEventId));

        assertTrue(exception.getMessage().contains("Event isn't found by id " + invalidEventId));
    }

    @Test
    public void testGetAllEvents() {
        List<Event> events = Arrays.asList(new Event(), new Event());
        when(eventRepo.findAll()).thenReturn(events);

        List<Event> result = eventService.getAllEvents();

        assertEquals(events.size(), result.size());
    }

    @Test
    public void testUpdateEvent() {
        Long eventId = 1L;
        Event existingEvent = new Event();
        existingEvent.setId(eventId);
        existingEvent.setName("Old Name");
        Event updatedEvent = new Event();
        updatedEvent.setName("New Name");

        when(eventRepo.findById(eventId)).thenReturn(Optional.of(existingEvent));
        when(eventRepo.save(existingEvent)).thenReturn(existingEvent);


        Event result = eventService.updateEvent(updatedEvent, eventId);


        assertNotNull(result);
        assertEquals("New Name", existingEvent.getName());
    }

    @Test
    public void testUpdateEventThrowsEntityNotFoundException() {
        Long nonExistentEventId = -1L;
        Event updatedEvent = new Event();

        when(eventRepo.findById(nonExistentEventId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> eventService.updateEvent(updatedEvent, nonExistentEventId));

        assertTrue(exception.getMessage().contains("Event isn't found by id " + nonExistentEventId));

        verify(eventRepo, never()).save(any(Event.class));
    }

    @Test
    public void testDeleteEventById() {

            Long eventId = 1L;
            when(eventRepo.existsById(eventId)).thenReturn(true);

            eventService.deleteEventById(eventId);

            verify(eventRepo, times(1)).deleteById(eventId);
        }

    @Test
    public void testDeleteEventByIdThrowsEntityNotFoundException () {
        Long eventId = 1L;
        when(eventRepo.existsById(eventId)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class, () ->
                eventService.deleteEventById(eventId));
        String actualMessage = exception.getMessage();

        assertEquals("Event doesn't exists by id " + eventId, actualMessage);
    }
}




