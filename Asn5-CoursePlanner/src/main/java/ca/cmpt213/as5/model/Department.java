package ca.cmpt213.as5.model;

import ca.cmpt213.as5.exceptions.IdNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/*
 Stores department ID, name, and the list of course in the specific department.
 */

public class Department implements Comparable<Department>, Iterable<Course> {
    private long deptId;
    private String name;
    private List<Course> courses = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public long getDeptId() {
        return deptId;
    }

    public String getName() {
        return name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Course getCourse(int courseId) throws IdNotFoundException {
        for (Course course : courses) {
            if (course.getCourseId() == courseId) {
                return course;
            }
        }
        throw new IdNotFoundException();
    }

    public void addCourse(Course newCourse, CourseOffering newOffering, Section newSection) {
        // Check for duplicates
        for (Course course : courses) {
            if (newCourse.getCatalogNumber().equals(course.getCatalogNumber())) {
                // If found then add offering to existing course
                course.addOffering(newOffering, newSection);
                return;
            }
        }
        newCourse.setCourseId(courses.size());
        newCourse.addOffering(newOffering, newSection);
        courses.add(newCourse);
        Collections.sort(courses);
    }

    @Override
    public int compareTo(Department o) {
        return name.compareTo(o.name);
    }

    @Override
    public Iterator<Course> iterator() {
        return courses.iterator();
    }
}
