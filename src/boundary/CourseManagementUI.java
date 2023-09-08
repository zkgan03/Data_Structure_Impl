package boundary;

import entity.Course;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Ching Wei Hong
 */
public class CourseManagementUI {

    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        int choice = -1;  // Initialize choice to an invalid value

        System.out.println("\nCourse Management Subsystem");
        System.out.println("============================");
        System.out.println("1. Add a new course");
        System.out.println("2. Remove a course");
        System.out.println("3. Find course");
        System.out.println("4. Amend course details");
        System.out.println("5. List all courses");
        System.out.println("6. Add programme to a course");
        System.out.println("7. Remove a programme from a course");
        System.out.println("8. Generate relevant reports");
        System.out.println("0. Quit");
        System.out.println("============================");

        do {
            System.out.print("Enter choice: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline character
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();  // Consume the invalid input
            }
        } while (choice < 0 || choice > 9);  // Repeat until a valid choice is entered

        return choice;
    }

    public Course addNewCourse() {
        System.out.println("\nAdd New Course");
        System.out.println("=====================");

        String courseCode = courseCodeUI();
        if (courseCode == null) {
            return null; // User canceled input
        }

        String courseName = courseNameUI();
        if (courseName == null) {
            return null; // User canceled input
        }

        Double creditHours = creditHoursUI();
        if (creditHours == -1.0) {
            return null; // User canceled input
        }

        System.out.println("======================");
        return new Course(courseCode, courseName, creditHours);
    }

    public String removeCourseUI() {
        System.out.println("\nRemove Course");
        System.out.println("=====================");

        String courseCode = courseCodeUI();
        if (courseCode == null) {
            return null; // User canceled input
        }

        System.out.println("=====================");
        return courseCode;
    }

// Find course
    public String findCourseUI() {
        System.out.println("\nSearch Courses");
        System.out.println("=====================");
        String courseCode = courseCodeUI();
        System.out.println("=====================");
        return courseCode;
    }

    // Amend course details
    public void amendCourseDetails() {
        System.out.println("\nUpdate Course Details");
        System.out.println("==============================");
    }

    // Add programme to a course
    public void addProgrammeToCourseUI() {
        System.out.println("\nAdd Programme to Course");
        System.out.println("==============================");

    }

    // Remove a programme from a course
    public void removeProgrammeFromCourseUI() {
        System.out.println("\nRemove Programme From Course");
        System.out.println("==============================");
    }

  public String courseCodeUI() {
    String courseCode;
    do {
        System.out.print("Enter course code (press 'x' to cancel): ");
        courseCode = scanner.nextLine().trim().toUpperCase();
        if (courseCode.equalsIgnoreCase("x")) {
            return null; // User canceled input
        }
        if (courseCode.isEmpty()) {
            System.out.println("Course code cannot be empty. Please try again or press 'x' to cancel.");
        } else if (!Character.isLetter(courseCode.charAt(0))) {
            System.out.println("Course code must start with a letter. Please try again or press 'x' to cancel.");
        }
    } while (courseCode.isEmpty() || !Character.isLetter(courseCode.charAt(0)));
    return courseCode;
}

    public String courseNameUI() {
        String courseName;
        do {
            System.out.print("Enter new course name (press 'x' to cancel): ");
            courseName = scanner.nextLine().trim();
            if (courseName.equalsIgnoreCase("x")) {
                return null; // User canceled input
            }
            if (courseName.isEmpty()) {
                System.out.println("Course name cannot be empty. Please try again or press 'x' to cancel.");
            } else {
                // Split the input into words
                String[] words = courseName.split("\\s+");
                StringBuilder capitalizedCourseName = new StringBuilder();
                boolean isFirstWord = true;

                for (String word : words) {
                    if (!isFirstWord) {
                        capitalizedCourseName.append(" "); // Add space between words
                    }
                    // Capitalize the first letter and append the rest in lowercase
                    capitalizedCourseName.append(Character.toUpperCase(word.charAt(0)))
                            .append(word.substring(1).toLowerCase());
                    isFirstWord = false;
                }
                courseName = capitalizedCourseName.toString();
            }
        } while (courseName.isEmpty());
        return courseName;
    }

    public double creditHoursUI() {
        double creditHours = -1.0; // Initialize with a negative value to indicate invalid input
        do {
            System.out.print("Enter new credit hours (press 'x' to cancel): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("x")) {
                return -1.0; // User canceled input
            }

            try {
                creditHours = Double.parseDouble(input);
                if (creditHours < 0) {
                    System.out.println("Credit hours cannot be negative. Please try again or press 'x' to cancel.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number or press 'x' to cancel.");
            }
        } while (creditHours < 0);

        return creditHours;
    }

    public String programmeCodeUI() {
        String programmeCode;
        do {
            System.out.print("Enter programme code (press 'x' to cancel): ");
            programmeCode = scanner.nextLine().trim().toUpperCase();
            if (programmeCode.equalsIgnoreCase("x")) {
                return null; // User canceled input
            }
            if (programmeCode.isEmpty()) {
                System.out.println("Program code cannot be empty. Please try again or press 'x' to cancel.");
            }
        } while (programmeCode.isEmpty());
        return programmeCode;
    }

    public boolean confirmAction(String message) {
        String input;
        boolean validInput = false;

        do {
            System.out.print(message + " (y/n): ");
            input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("n")) {
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        } while (!validInput);

        return input.equals("y");
    }

}
