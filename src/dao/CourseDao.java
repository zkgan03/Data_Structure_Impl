/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.HashMap;
import adt.MapInterface;
import entity.Course;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author CHING WEI HONG
 */
public class CourseDao {

    private String fileName = "course.dat";

    public void saveCourseToFile(MapInterface<String, Course> courseList) {
        File file = new File(fileName);
        try {
            ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
            ooStream.writeObject(courseList);
            ooStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found(Course)");
        } catch (IOException ex) {
            System.out.println("\nCannot save to file (Course)\n" + ex);
        }
    }

    public MapInterface<String, Course> retrieveCourseFromFile() {
        File file = new File(fileName);
        MapInterface<String, Course> courseList = new HashMap<>();
        try {
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            courseList = (MapInterface<String, Course>) (oiStream.readObject());
            oiStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nNo such file.(Course)");
        } catch (IOException ex) {
            System.err.println("Error saving to file: " + ex.getMessage());
            System.out.println("\nCannot read from file.(Course)\n" + ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found.(Course)");
        } finally {
            return courseList;
        }
    }
}
