package ca.cmpt213.as5.model;

import java.util.*;

/*
 Class to "watch" a course for sections being added to it. Observer for the specific course.
 */

public class Watcher implements Observer {
    private long id;
    private Department department;
    private Course course;
    private String catalogNumber;
    private List<String> events = new ArrayList<>();

    public Watcher(Department department, Course course) {
        this.department = department;
        this.course = course;
    }

    public long getId() {
        return id;
    }

    public Department getDepartment() {
        return department;
    }

    public Course getCourse() {
        return course;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setWatcherId(long id) {
        this.id = id;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    @Override
    public void addEvent(CourseOffering offering, Section section) {
        Date date = new Date();
        String currentDate = date.toString();
        String type = section.getType();
        String enrollmentTotal = Integer.toString(section.getEnrollmentTotal());
        String enrollmentCap = Integer.toString(section.getEnrollmentCap());
        String term = offering.getTerm();
        String year = Integer.toString(offering.getYear());

        String event = currentDate + ": Added section " + type + " with enrollment (" + enrollmentTotal
                + " / " + enrollmentCap + ") to offering " + term + " " + year;
        events.add(event);
    }
}
