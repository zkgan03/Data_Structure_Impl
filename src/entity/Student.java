package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author TAN KAI JIAN
 */
public class Student implements Serializable, Comparable<Student> {

    private String studentID;
    private String studentName;
    private String gender;
    private int yearOfStudy;

    public Student() {
    }

    public Student(String studentID, String studentName, String gender, int yearOfStudy) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.gender = gender;
        this.yearOfStudy = yearOfStudy;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Student other = (Student) obj;
        return this.studentID.equals(other.studentID);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.studentID);
        return hash;
    }

    @Override
    public int compareTo(Student o) {
        return this.studentID.compareTo(o.studentID);
    }

    @Override
    public String toString() {
        return "studentID=" + studentID + ", studentName=" + studentName + ", gender=" + gender + ", yearOfStudy=" + yearOfStudy;
    }
}