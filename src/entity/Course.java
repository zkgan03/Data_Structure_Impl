package entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ching Wei Hong
 */
public class Course implements Serializable {

    private String courseCode;
    private String courseName;
    private double creditHours;
    private String[] programmeCodes;
    private Date dateAdded;

    public Course(String courseCode, String courseName, double creditHours) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.creditHours = creditHours;
        this.programmeCodes = new String[0];
        this.dateAdded = null;
    }

    public Course(String courseCode, String courseName, double creditHours, String[] programmeCodes) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.creditHours = creditHours;
        this.programmeCodes = programmeCodes;
        this.dateAdded = null;
    }

    public double getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(double creditHours) {
        this.creditHours = creditHours;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String[] getProgrammeCodes() {
        return programmeCodes;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void updateCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getProgrammeCodesAsString() {
        StringBuilder sb = new StringBuilder();

        if (programmeCodes.length > 0) {
            for (int i = 0; i < programmeCodes.length - 1; i++) {
                sb.append(programmeCodes[i]).append(", ");
            }
            sb.append(programmeCodes[programmeCodes.length - 1]); // Append the last element without a comma
        }

        return sb.toString();
    }

    public void addProgrammeCode(String programmeCode) {
        // Create a new array with one more element and copy existing elements
        String[] newProgrammeCodes = new String[programmeCodes.length + 1];
        System.arraycopy(programmeCodes, 0, newProgrammeCodes, 0, programmeCodes.length);
        newProgrammeCodes[programmeCodes.length] = programmeCode;
        programmeCodes = newProgrammeCodes;
    }

    public void removeProgrammeCode(String programmeCode) {
        int indexToRemove = -1;
        for (int i = 0; i < programmeCodes.length; i++) {
            if (programmeCodes[i].equals(programmeCode)) {
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove != -1) {
            String[] newProgrammeCodes = new String[programmeCodes.length - 1];
            System.arraycopy(programmeCodes, 0, newProgrammeCodes, 0, indexToRemove);
            System.arraycopy(programmeCodes, indexToRemove + 1, newProgrammeCodes, indexToRemove, programmeCodes.length - indexToRemove - 1);
            programmeCodes = newProgrammeCodes;
        }
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;

        result = prime * result + ((courseCode == null) ? 0 : courseCode.hashCode());
        result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Course otherCourse = (Course) obj;

        if ((courseCode == null && otherCourse.courseCode != null)
                || (courseCode != null && !courseCode.equals(otherCourse.courseCode))) {
            return false;
        }

        if ((courseName == null && otherCourse.courseName != null)
                || (courseName != null && !courseName.equals(otherCourse.courseName))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Course Code: ").append(courseCode).append(System.lineSeparator());
        sb.append("Course Name: ").append(courseName).append(System.lineSeparator());
        sb.append("Credit Hours: ").append(creditHours).append(System.lineSeparator());
        sb.append("Programme include course: ").append(getProgrammeCodesAsString()).append(System.lineSeparator());

        return sb.toString();
    }

}
