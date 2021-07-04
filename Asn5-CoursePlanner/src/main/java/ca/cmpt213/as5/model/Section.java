package ca.cmpt213.as5.model;

/*
 Stores section type, enrollment capacity, enrollment total.
 */

public class Section implements Comparable<Section> {
    private String type;
    private int enrollmentCap;
    private int enrollmentTotal;

    public Section(String type, int enrollmentCap, int enrollmentTotal) {
        this.type = type;
        this.enrollmentCap = enrollmentCap;
        this.enrollmentTotal = enrollmentTotal;

    }

    public String getType() {
        return type;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentCap(int enrollmentCap) {
        this.enrollmentCap = enrollmentCap;
    }

    public void setEnrollmentTotal(int enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    public void addEnrollmentCap(int newEnrollmentCap) {
        this.enrollmentCap = this.enrollmentCap + newEnrollmentCap;
    }

    public void addEnrollmentTotal(int newEnrollmentTotal) {
        this.enrollmentTotal = this.enrollmentTotal + newEnrollmentTotal;
    }

    @Override
    public int compareTo(Section o) {
        return type.compareTo(o.type);
    }
}
