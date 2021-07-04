package ca.cmpt213.as5.model;

/*
 Class to hold fields from web UI when a new watcher is created.
 */

public class WatcherData {
    private int deptId;
    private int courseId;

    public WatcherData(int deptId, int courseId) {
        this.deptId = deptId;
        this.courseId = courseId;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
