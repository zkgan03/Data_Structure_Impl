package dao;

import adt.SortedArrayList;
import adt.SortedListInterface;
import entity.TutorialGroup;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author TAN KAI JIAN
 */
public class TutorialDao {

    private String fileName = "tutorials.dat";

    public void saveToFile(SortedListInterface<TutorialGroup> tutorialList) {
        File file = new File(fileName);
        try {
            ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
            ooStream.writeObject(tutorialList);
            ooStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found" + ex);
        } catch (IOException ex) {
            System.out.println("\nCannot save to file" + ex);
        }
    }

    public SortedListInterface<TutorialGroup> retrieveFromFile() {
        
        File file = new File(fileName);
        SortedListInterface<TutorialGroup> tutorialList = new SortedArrayList<>();
        
        try {
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            tutorialList = (SortedArrayList<TutorialGroup>) (oiStream.readObject());
            oiStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nNo such file." + ex);
        } catch (IOException ex) {
            System.out.println("\nCannot read from file." + ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found." + ex);
        } finally {
            return tutorialList;
        }
    }
}
