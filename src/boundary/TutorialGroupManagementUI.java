package boundary;

import java.util.Scanner;

/**
 *
 * @author TAN KAI JIAN
 */
public class TutorialGroupManagementUI {

    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        System.out.println("=================================================");
        System.out.println("       Tutorial Group Management Subsystem");
        System.out.println("=================================================");
        System.out.println("1. Add a student to a tutorial group");
        System.out.println("2. Remove a student from a tutorial group");
        System.out.println("3. Change the tutorial group for a student");
        System.out.println("4. Find a student in a tutorial group");
        System.out.println("5. List all students in a tutorial group");
        System.out.println("6. Filter tutorial groups based on no of student");
        System.out.println("7. Generate a Tutorial Group Gender Report");
        System.out.println("0. Quit");

        System.out.print("\nEnter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.print("\n");

        return choice;
    }

    public void getAddStudHeader() {
        System.out.println("-------------------------------------------------");
        System.out.println("        Add a student to a tutorial group");
        System.out.println("-------------------------------------------------");
    }

    public void getRemoveStudHeader() {
        System.out.println("-------------------------------------------------");
        System.out.println("     Remove a student from a tutorial group");
        System.out.println("-------------------------------------------------");
    }

    public void getChgTutoGrpHeader() {
        System.out.println("-------------------------------------------------");
        System.out.println("     Change the tutorial group for a student");
        System.out.println("-------------------------------------------------");
    }

    public void getFindStdHeader() {
        System.out.println("-------------------------------------------------");
        System.out.println("       Find a student in a tutorial group");
        System.out.println("-------------------------------------------------");
    }

    public void getListStdHeader() {
        System.out.println("-------------------------------------------------");
        System.out.println("      List all students in a tutorial group");
        System.out.println("-------------------------------------------------");
    }

    public void getFilterGrpHeader() {
        System.out.println("-------------------------------------------------");
        System.out.println("  Filter tutorial groups based on no of student");
        System.out.println("-------------------------------------------------");
    }

    public void getReportHeader() {
        System.out.println("======================================================");
        System.out.println("|            Tutorial Group Gender Report            |");
        System.out.println("======================================================");
        System.out.println("| Tutorial Group | Number of Student | Male | Female |");
        System.out.println("------------------------------------------------------");
    }

    public void getReportFooter() {
        System.out.println("------------------------------------------------------\n");
    }

    public String getStudName() {
        System.out.print("Enter student's name : ");
        String studName = scanner.nextLine();

        return studName;
    }

    public String getStudGender() {
        System.out.print("Enter student's gender(MALE/FEMALE): ");
        String studGender = scanner.nextLine();

        return studGender;
    }

    public int getYearOfStudy() {
        System.out.print("Enter student's year of study : ");
        int yearOfStudy = scanner.nextInt();
        scanner.nextLine();

        return yearOfStudy;
    }

    public String getGroupCode() {
        System.out.print("Enter student's tutorial group : ");
        String groupCode = scanner.nextLine();

        return groupCode;
    }

    public String getCurrentGroupCode() {
        System.out.print("Enter student's current tutorial group : ");
        String groupCode = scanner.nextLine();

        return groupCode;
    }

    public String getNewGroupCode() {
        System.out.print("Enter student's new tutorial group : ");
        String groupCode = scanner.nextLine();

        return groupCode;
    }

    public String getStudID() {
        System.out.print("Enter student's ID: ");
        String studentID = scanner.nextLine();

        return studentID;
    }

    public String getGroupCodeToListStud() {
        System.out.print("Enter tutorial group to get student list: ");
        String groupCode = scanner.nextLine();

        return groupCode;
    }

    public int getFilterChoice() {
        System.out.print("Filter tutorial groups with: ");
        System.out.print("\n1. More Than number of students");
        System.out.print("\n2. Less Than number of students\n");
        System.out.print("\nEnter choice: ");
        int filterChoice = scanner.nextInt();
        scanner.nextLine();

        return filterChoice;
    }

    public int getFilterNumOfStud() {
        System.out.print("Enter the number of students: ");
        int num = scanner.nextInt();
        scanner.nextLine();

        return num;
    }

    public void printStudFound(String groupCode, String studentID, String studentName, String gender, int yearOfStudy) {
        System.out.println("\nStudent found in group " + groupCode + ":");
        System.out.println("Student ID    : " + studentID);
        System.out.println("Student Name  : " + studentName);
        System.out.println("Student Gender: " + gender);
        System.out.println("Year of Study : " + yearOfStudy);
        System.out.println("\n");
    }

    public void printListStudGrpCode(String groupCode) {
        System.out.println("List of students in group " + groupCode + ":\n");
    }

    public void printListStudDetailsHeader() {
        System.out.println(" Student ID     Student Name    ");
        System.out.println(" ---------- --------------------");
    }
    
    public void printListStudDetails(String studentID, String studentName) {
        System.out.printf("  %-10s %-20s \n", studentID, studentName);
    }

    public void printGenderReport(String groupCode, int totalStudents, int maleStudents, int femaleStudents) {
        System.out.printf("| %-15s| %17d | %4d | %6d |\n", groupCode, totalStudents, maleStudents, femaleStudents);
    }

    public void printLine() {
        System.out.println("\n");
    }
}
