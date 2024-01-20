package sia.eventmanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sia.eventmanagementsystem.entity.Event;
import sia.eventmanagementsystem.entity.User;
import sia.eventmanagementsystem.service.UserService;
import sia.eventmanagementsystem.service.EventService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final UserService userService;

    @GetMapping("/list")
    public String listEvents(Model model) {
        List<Event> events = eventService.getAllEvents();
        List<User> allUsers = userService.getAllUsers();

        model.addAttribute("events", events);
        model.addAttribute("allUsers", allUsers);

        return "event/list";
    }

    @GetMapping("/{eventId}")
    public String getEventDetails(@PathVariable Long eventId, Model model) {
        Event event = eventService.getEventById(eventId);
        model.addAttribute("event", event);
        return "event/details";
    }

    @GetMapping("/create")
    public String showCreateEventForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("users", userService.getAllUsers());
        return "event/create";
    }

    @PostMapping("/create")
    public String createEvent(@ModelAttribute Event event) {
        eventService.createEvent(event);
        return "redirect:/events/list";
    }


    @GetMapping("/{eventId}/edit")
    public String showEditEventForm(@PathVariable Long eventId, Model model) {
        Event event = eventService.getEventById(eventId);
        model.addAttribute("event", event);
        model.addAttribute("users", userService.getAllUsers());
        return "event/edit";
    }

    @PostMapping("/{eventId}/edit")
    public String updateEvent(@ModelAttribute Event updatedEvent, @PathVariable Long eventId) {
            eventService.updateEvent(updatedEvent, eventId);
            return "redirect:/events/" + eventId;


    }

    @GetMapping("/{eventId}/delete")
    public String deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEventById(eventId);
        return "redirect:/events/list";
    }


    @GetMapping("/{eventId}/addAttendees")
    public String showAddAttendeesForm(@PathVariable Long eventId, Model model) {
        Event event = eventService.getEventById(eventId);
        List<User> allUsers = userService.getAllUsers();

        model.addAttribute("event", event);
        model.addAttribute("allUsers", allUsers);

        return "event/addAttendees";
    }

    @PostMapping("/{eventId}/addAttendees")
    public String addAttendeesToEvent(@PathVariable Long eventId, @RequestParam("attendeeIds") Set<Long> attendeeIds) {
        Event event = eventService.getEventById(eventId);
        Set<User> attendees = attendeeIds.stream()
                .map(userService::getUserById)
                .collect(Collectors.toSet());

        event.getAttendees().addAll(attendees);
        eventService.createEvent(event);

        return "redirect:/events/" + eventId;
    }
    @GetMapping("/{eventId}/deleteAttendees")
    public String showDeleteAttendeesForm(@PathVariable Long eventId, Model model) {
        Event event = eventService.getEventById(eventId);
        List<User> attendees = userService.getAllUsers();

        model.addAttribute("event", event);
        model.addAttribute("attendees", attendees);

        return "event/deleteAttendees";
    }

    @PostMapping("/{eventId}/deleteAttendees")
    public String deleteAttendeesFromEvent(@PathVariable Long eventId, @RequestParam Set<Long> attendeeIds) {
        eventService.deleteAttendeesFromEvent(eventId, attendeeIds);
        return "redirect:/events/" + eventId;
    }

}
