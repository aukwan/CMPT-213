package ca.cmpt213.as5.model;

public class GraphDataPoint {
    private long semesterCode;
    private long totalCoursesTaken;

    public GraphDataPoint() {}

    public long getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(long semesterCode) {
        this.semesterCode = semesterCode;
    }

    public long getTotalCoursesTaken() {
        return totalCoursesTaken;
    }

    public void setTotalCoursesTaken(long totalCoursesTaken) {
        this.totalCoursesTaken = totalCoursesTaken;
    }
}
