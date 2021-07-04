package ca.cmpt213.as5.model;

import ca.cmpt213.as5.exceptions.IdNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*
 Parses given CSV files' data into model.
 */

public class CSVReader {
    // Data files
    private static final String INPUT_FILE_PATH = "data/course_data_2018.csv";
    private static final String SAMPLE_FILE_PATH = "data/small_data.csv";

    private List<Department> departments = new ArrayList<>();
    private static final int SEMESTER_INDEX = 0;
    private static final int SUBJECT_INDEX = 1;
    private static final int CATALOG_NUMBER_INDEX = 2;
    private static final int LOCATION_INDEX = 3;
    private static final int ENROLLMENT_CAPACITY_INDEX = 4;
    private static final int ENROLLMENT_TOTAL_INDEX = 5;
    private static final int INSTRUCTORS_INDEX = 6;

    public CSVReader() throws FileNotFoundException {
        this.readCSVFile();
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public Department getDepartment(int deptId) throws IdNotFoundException {
        for (Department department : departments) {
            if (department.getDeptId() == deptId) {
                return department;
            }
        }
        throw new IdNotFoundException();
    }

    public void readCSVFile() throws FileNotFoundException {
        File file = new File(INPUT_FILE_PATH);
        //File file = new File(SAMPLE_FILE_PATH);
        Scanner scan = new Scanner(file);
        // Skip the first line of column names
        scan.nextLine();
        while (scan.hasNextLine()) {
            String[] line = scan.nextLine().split(",");
            // Parse and trim extra spaces
            String deptName = line[SUBJECT_INDEX].trim();
            String courseName = line[CATALOG_NUMBER_INDEX].trim();
            String location = line[LOCATION_INDEX].trim();
            List<String> instructors = new ArrayList<>();
            for (int i = INSTRUCTORS_INDEX; i < line.length - 1; i++) {
                // Check for <null>. Trim extra spaces and single quotation marks.
                if (!line[i].trim().equals("<null>")) {
                    String instructor = line[i].replaceAll("\"", "");
                    instructors.add(instructor.trim());
                } else {
                    continue;
                }
            }
            String semesterCode = line[SEMESTER_INDEX].trim();
            String component = line[line.length - 1].trim();
            int enrollmentCapacity = Integer.parseInt(line[ENROLLMENT_CAPACITY_INDEX].trim());
            int enrollmentTotal = Integer.parseInt(line[ENROLLMENT_TOTAL_INDEX].trim());

            Department department = new Department(deptName);
            Course course = new Course(courseName);
            CourseOffering offering = new CourseOffering(location, instructors, semesterCode);
            Section section = new Section(component, enrollmentCapacity, enrollmentTotal );
            addDepartment(department, course, offering, section);
        }
        scan.close();
    }

    // Dump model
    public void printModel() {
        for (Department department : departments) {
            for (Course course : department.getCourses()) {
                System.out.println(department.getName() + " " + course.getCatalogNumber());
                for (CourseOffering offering : course.getOfferingList()) {
                    System.out.println("\t" + offering.getSemesterCode() + " in "
                            + offering.getLocation() + " by " + offering.getInstructors());
                    for (Section section : offering.getSections()) {
                        System.out.println("\t\t" + "Type=" + section.getType() + ", Enrollment="
                                + section.getEnrollmentTotal() + "/" + section.getEnrollmentCap());
                    }
                }
            }
        }
    }

    public void addDepartment(Department newDepartment, Course newCourse,
                              CourseOffering newOffering, Section newSection) {
        // Check for duplicates
        for (Department department : departments) {
            if (newDepartment.getName().equals(department.getName())) {
                // If found then add course to existing department
                department.addCourse(newCourse, newOffering, newSection);
                return;
            }
        }
        newDepartment.setDeptId(departments.size());
        newDepartment.addCourse(newCourse, newOffering, newSection);
        departments.add(newDepartment);
        Collections.sort(departments);
    }
}
