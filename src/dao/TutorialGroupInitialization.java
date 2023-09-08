package dao;

import adt.SortedArrayList;
import adt.SortedListInterface;
import entity.TutorialGroup;
import entity.Student;

/**
 *
 * @author TAN KAI JIAN
 */
public class TutorialGroupInitialization {

    public SortedListInterface<TutorialGroup> initializeTutorial() {

        SortedListInterface<TutorialGroup> pList = new SortedArrayList<>();

        // Create Student objects
        Student student1 = new Student("aaa", "ali", "Female", 1);
        Student student2 = new Student("aab", "bob", "Male", 2);
        Student student3 = new Student("aac", "cat", "Male", 1);
        
        // Create TutorialGroup objects and add students to them
        TutorialGroup group1 = new TutorialGroup("RSW1");
        group1.addStudent(student1);
        group1.addStudent(student2);
        group1.addStudent(student3);
        
        pList.add(group1);

        pList.add(new TutorialGroup("RDS1"));
        pList.add(new TutorialGroup("RIS1"));

        return pList;
    }

    public static void main(String[] args) {
        TutorialGroupInitialization p = new TutorialGroupInitialization();
        TutorialDao dao = new TutorialDao();
        SortedListInterface pList = p.initializeTutorial();

        dao.saveToFile(pList);
    }
}
