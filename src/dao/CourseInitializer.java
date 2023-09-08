package dao;

import adt.MapInterface;
import adt.HashMap;
import entity.Course;

/**
 *
 * @author Ching Wei Hong
 */
public class CourseInitializer {

//  Method to return a collection of with hard-coded entity values
    public MapInterface<String, Course> initializeCourse() {

        MapInterface<String, Course> cList = new HashMap<>();

        cList.put("BAMS1001", new Course("BAMS1001", "Software Engineering", 2));
        cList.put("ACSS1001", new Course("ACSS1001", "Maths", 2));
        cList.put("BAMS1002", new Course("BAMS1002", "DSA", 2));

        return cList;
    }

    public static void main(String[] args) {
        // To illustrate how to use the initializeProducts() method
        CourseInitializer p = new CourseInitializer();
        CourseDao dao = new CourseDao();
        MapInterface<String, Course> cList = p.initializeCourse();
        System.out.println(cList);

        dao.saveCourseToFile(cList);
        

    }
}
