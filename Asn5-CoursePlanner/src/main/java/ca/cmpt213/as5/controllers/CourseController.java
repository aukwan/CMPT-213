package ca.cmpt213.as5.controllers;

import ca.cmpt213.as5.exceptions.IdNotFoundException;
import ca.cmpt213.as5.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/*
 Rest API
 */

@RestController
public class CourseController {

    private CSVReader CSVFile = new CSVReader();
    private List<Department> departments = CSVFile.getDepartments();
    private List<Watcher> watchers = new ArrayList<>();

    public CourseController() throws FileNotFoundException {
    }

    // General
    @GetMapping("/api/about")
    public About getAboutMessage() {
        About info = new About("Course Planner App", "Austin Kwan");
        return info;
    }

    @GetMapping("/api/dump-model")
    public void dumpModel() {
        CSVFile.printModel();
    }

    // Access Departments, Course, Offerings, and Sections
    @GetMapping("/api/departments")
    public List<Department> getAllDepartments() {
        return departments;
    }

    @GetMapping("/api/departments/{dId}/courses")
    public List<Course> getCourses(@PathVariable("dId") int deptId) throws IdNotFoundException {
        if (deptId > departments.size()) {
            throw new IdNotFoundException();
        } else {
            return CSVFile.getDepartment(deptId).getCourses();
        }
    }

    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings")
    public List<CourseOffering> getOfferings(@PathVariable int deptId,
                                                   @PathVariable int courseId) throws IdNotFoundException {
        if (deptId > departments.size()) {
            throw new IdNotFoundException();
        } else if (courseId > departments.get(deptId).getCourses().size()) {
            throw new IdNotFoundException();
        } else {
            return CSVFile.getDepartment(deptId).getCourse(courseId).getOfferingList();
        }
    }

    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings/{offeringId}")
    public List<Section> getSections(@PathVariable int deptId,
                                                   @PathVariable int courseId,
                                                   @PathVariable int offeringId) throws IdNotFoundException {
        if (deptId > departments.size()) {
            throw new IdNotFoundException();
        } else if (courseId > CSVFile.getDepartment(deptId).getCourses().size()) {
            throw new IdNotFoundException();
        } else if (offeringId > CSVFile.getDepartment(deptId).getCourse(courseId)
                .getOfferingList().size()) {
            throw new IdNotFoundException();
        } else {
            return CSVFile.getDepartment(deptId).getCourse(courseId).getOffering(offeringId).getSections();
        }
    }


    @GetMapping("/api/stats/students-per-semester/")
    public List<GraphDataPoint> getDataPointList(@RequestBody int deptId) throws IdNotFoundException {
        if (deptId > departments.size()) {
            throw new IdNotFoundException();
        } else {
            List<GraphDataPoint> dataPointList = new ArrayList<>();

            return dataPointList;
        }
    }


    // Add New Offering/Section
    @PostMapping("/api/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewOffering(@RequestBody OfferingData offeringData) {
        Department newDepartment = new Department(offeringData.getSubjectName());
        Course newCourse = new Course(offeringData.getCatalogNumber());
        List<String> instructorsList = new ArrayList<>();
        instructorsList.add(offeringData.getInstructor());
        CourseOffering newOffering = new CourseOffering(offeringData.getLocation(), instructorsList,
                                        offeringData.getSemester());
        Section newSection = new Section(offeringData.getComponent(), offeringData.getEnrollmentCap(),
                                        offeringData.getEnrollmentTotal());
        for (Watcher watcher : watchers) {
            if (watcher.getDepartment().getName().equals(newDepartment.getName())) {
                if (watcher.getCourse().getCatalogNumber().equals(newCourse.getCatalogNumber())) {
                    watcher.getCourse().notifyObservers(newOffering, newSection);
                }
            }
        }
        CSVFile.addDepartment(newDepartment, newCourse, newOffering, newSection);
    }


    // Course Change Watchers
    @GetMapping("/api/watchers")
    public List<Watcher> getAllWatchers() {
        return watchers;
    }

    @PostMapping("/api/watchers")
    @ResponseStatus(HttpStatus.CREATED)
    public void createWatcher(@RequestBody WatcherData watcherData) throws IdNotFoundException {
        if (watcherData.getDeptId() > departments.size()) {
            throw new IdNotFoundException();
        } else if (watcherData.getCourseId() > CSVFile.getDepartment(watcherData.getDeptId()).getCourses().size()) {
            throw new IdNotFoundException();
        } else {
            Department department = CSVFile.getDepartment(watcherData.getDeptId());
            Course course = CSVFile.getDepartment(watcherData.getDeptId()).getCourse(watcherData.getCourseId());
            Watcher watcher = new Watcher(department, course);
            watcher.getCourse().addObserver(watcher);
            watcher.setWatcherId(watchers.size());
            watchers.add(watcher);
        }
    }

    @GetMapping("/api/watchers/{id}")
    public Watcher getWatcher(@PathVariable("id") int watcherId) throws IdNotFoundException {
        if (watcherId > watchers.size()) {
            throw new IdNotFoundException();
        } else {
            for (Watcher watcher : watchers) {
                if (watcher.getId() == watcherId) {
                    return watcher;
                }
            }
            throw new IdNotFoundException();
        }
    }

    @DeleteMapping("/api/watchers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeWatcher(@PathVariable("id") int watcherId) throws IdNotFoundException {
        if (watcherId > watchers.size()) {
            throw new IdNotFoundException();
        } else {
            for (Watcher watcher : watchers) {
                if (watcher.getId() == watcherId) {
                    watcher.getCourse().removeObserver(watcher);
                    watchers.remove(watcher);
                }
            }
            throw new IdNotFoundException();
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(IdNotFoundException.class)
    public void IdNotFoundExceptionHandler() {}
}
