package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/*
 Stores offering ID, location, instructors, year, semester code, term, and sections.
 */

public class CourseOffering implements Comparable<CourseOffering>, Iterable<Section> {
    private long courseOfferingId;
    private String location;
    private List<String> instructorsList;
    private String instructors;
    private int year;
    private String semesterCode;
    private String term;
    private List<Section> sections = new ArrayList<>();

    public CourseOffering(String location, List<String> instructorsList, String semesterCode) {
        this.location = location;
        this.instructorsList = instructorsList;
        this.semesterCode = semesterCode;
        this.instructors = "";
    }

    public long getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(long courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInstructors() {
        // Remove duplicate instructors
        instructorsList = instructorsList.stream().distinct().collect(Collectors.toList());
        instructors = String.join(", ", instructorsList);
        return instructors;
    }

    public void setInstructors(List<String> instructorsList) {
        this.instructorsList = instructorsList;
    }

    public int getYear() {
        String[] semesterCodeSplit = semesterCode.split("");
        int firstCodeNum = Integer.parseInt(semesterCodeSplit[0]);
        int secondCodeNum = Integer.parseInt(semesterCodeSplit[1]);
        int thirdCodeNum = Integer.parseInt(semesterCodeSplit[2]);
        this.year = 1900 + (100 * firstCodeNum) + (10 * secondCodeNum) + thirdCodeNum;
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(String semesterCode) {
        this.semesterCode = semesterCode;
    }

    public String getTerm() {
        String[] semesterCodeSplit = semesterCode.split("");
        if (semesterCodeSplit[3].equals("7")) {
            this.term = "Fall";
        } else if (semesterCodeSplit[3].equals("4")) {
            this.term = "Summer";
        } else if (semesterCodeSplit[3].equals("1")) {
            this.term = "Spring";
        }
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public List<String> getInstructorsList() {
        return instructorsList;
    }

    public void addSection(Section newSection) {
        // Check for duplicates
        for (Section section : sections) {
            if (newSection.getType().equals(section.getType())) {
                section.addEnrollmentCap(newSection.getEnrollmentCap());
                section.addEnrollmentTotal(newSection.getEnrollmentTotal());
                return;
            }
        }
        sections.add(newSection);
        Collections.sort(sections);
    }

    public void addInstructors(List<String> newInstructors) {
        for (String instructor : newInstructors) {
            if (instructorsList.contains(instructor)) {
                continue;
            }
            instructorsList.add(instructor);
        }
    }

    @Override
    public int compareTo(CourseOffering o) {
        return semesterCode.compareTo(o.semesterCode);
    }

    @Override
    public Iterator<Section> iterator() {
        return sections.iterator();
    }
}
