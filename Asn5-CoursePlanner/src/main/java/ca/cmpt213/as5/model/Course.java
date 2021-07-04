package ca.cmpt213.as5.model;

import ca.cmpt213.as5.exceptions.IdNotFoundException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/*
 Stores course ID, numbers, and offerings.
 */

public class Course implements Comparable<Course>, Iterable<CourseOffering> {
    private long courseId;
    private String catalogNumber;
    private List<CourseOffering> offeringList = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();

    public Course(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public long getCourseId() {
        return courseId;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public List<CourseOffering> getOfferingList() {
        return offeringList;
    }

    public void addOffering(CourseOffering newOffering, Section newSection) {
        // Check for duplicates
        for (CourseOffering offering : offeringList) {
            if (newOffering.getLocation().equals(offering.getLocation()) &&
                    newOffering.getSemesterCode().equals(offering.getSemesterCode())) {
                // If found then add section to existing offering
                offering.addInstructors(newOffering.getInstructorsList());
                offering.addSection(newSection);
                return;
            }
        }
        newOffering.setCourseOfferingId(offeringList.size());
        newOffering.addSection(newSection);
        offeringList.add(newOffering);
        Collections.sort(offeringList);
    }

    public CourseOffering getOffering(int offeringId) throws IdNotFoundException {
        for (CourseOffering offering : offeringList) {
            if (offering.getCourseOfferingId() == offeringId) {
                return offering;
            }
        }
        throw new IdNotFoundException();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(CourseOffering offering, Section section) {
        for (Observer observer : observers) {
            observer.addEvent(offering, section);
        }
    }

    @Override
    public int compareTo(Course o) {
        return catalogNumber.compareTo(o.catalogNumber);
    }

    @Override
    public Iterator<CourseOffering> iterator() {
        return offeringList.iterator();
    }


}
