package entity;


/**
 *
 * @author TAN KAI JIAN
 */
import adt.HashMap;
import adt.MapInterface;
import java.io.Serializable;
import java.util.Objects;

public class TutorialGroup implements Serializable, Comparable<TutorialGroup> {

    private String groupCode;
    private MapInterface<String, Student> student;
    private int noOfStudent;

    public TutorialGroup() {
        this.noOfStudent = 0;
    }

    public TutorialGroup(String groupCode) {
        this.groupCode = groupCode;
        this.student = new HashMap<>();
        this.noOfStudent = 0;
    }

    public TutorialGroup(String groupCode, MapInterface<String, Student> student) {
        this.groupCode = groupCode;
        this.student = student;
        this.noOfStudent = student.size();
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public void setStudent(MapInterface<String, Student> student) {
        this.student = student;
        this.noOfStudent = student.size();
    }

    public int getNoOfStudent() {
        return noOfStudent;
    }

    public void addStudent(Student newStudent) {
        student.put(newStudent.getStudentID(), newStudent);
        noOfStudent++;
    }

    public void removeStudent(String studentID) {
        student.remove(studentID);
        noOfStudent--;
    }

    public Student getStudent(String studentID) {
        return student.get(studentID);
    }

    public MapInterface<String, Student> getStudent() {
        return student;
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
        final TutorialGroup other = (TutorialGroup) obj;
        return this.groupCode.equals(other.groupCode);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.groupCode);
        return hash;
    }

    @Override
    public int compareTo(TutorialGroup o) {
        if (this.groupCode.compareTo(o.groupCode) > 0) {
            return 1;
        } else if (this.groupCode.compareTo(o.groupCode) < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "TutorialGroup{" + "groupCode=" + groupCode + ", student=" + student + ", noOfStudent=" + noOfStudent + '}';
    }

}