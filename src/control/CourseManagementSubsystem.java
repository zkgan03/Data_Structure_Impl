package control;

/**
 *
 * @author Ching Wei Hong
 */
import adt.HashMap;
import adt.MapInterface;
import adt.SetInterface;
import adt.Entry;
import boundary.CourseManagementUI;
import dao.CourseDao;
import dao.ProgrammeDao;
import java.util.Iterator;
import utility.MessageUI;
import entity.Course;
import entity.Programme;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CourseManagementSubsystem {

    private final CourseManagementUI ui;
    private SetInterface<Programme> programme;
    private final CourseDao cDao;
    private final ProgrammeDao pDao;
    private MapInterface<String, Course> courses = new HashMap<>();

    public CourseManagementSubsystem() {
        cDao = new CourseDao();
        pDao = new ProgrammeDao();
        if (cDao.retrieveCourseFromFile() != null) {
            courses = (MapInterface<String, Course>) cDao.retrieveCourseFromFile();
        }
        if (pDao.retrieveProgrammeFromFile() != null) {
            programme = pDao.retrieveProgrammeFromFile();
        }
        ui = new CourseManagementUI();
    }

    public void runCourseManagement() {
        int choice = 0;

        do {
            choice = ui.getMenuChoice();

            switch (choice) {
                case 0:
                    break;
                case 1:
                    addCourse();
                    MessageUI.pressEnterToContinue();
                    break;
                case 2:
                    removeCourse();
                    MessageUI.pressEnterToContinue();
                    break;
                case 3:
                    findCourseByCode(null);
                    MessageUI.pressEnterToContinue();
                    break;
                case 4:
                    amendCourseDetails();
                    MessageUI.pressEnterToContinue();
                    break;
                case 5:
                    listAllCourses();
                    MessageUI.pressEnterToContinue();
                    break;
                case 6:
                    addProgrammeToCourse();
                    MessageUI.pressEnterToContinue();
                    break;
                case 7:
                    removeProgrammeFromCourse();
                    MessageUI.pressEnterToContinue();
                    break;
                case 8:
                    displayCourseLog();
                    MessageUI.pressEnterToContinue();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    public void addCourse() {
        Course newCourse = ui.addNewCourse();
        if (newCourse != null) {
            if (!courses.containsKey(newCourse.getCourseCode())) {
                if (newCourse != null) {
                    if (ui.confirmAction("Are you sure you want to add this course?")) {
                        newCourse.setDateAdded(new Date());
                        courses.put(newCourse.getCourseCode(), newCourse);
                        cDao.saveCourseToFile(courses);
                        MessageUI.displaySuccessMessage("Course Added!");
                    } else {
                        MessageUI.displaySuccessMessage("Course Not Added.");
                    }
                } else {
                    MessageUI.displayErrorMessage("Failed add operation!");
                }
            } else {
                MessageUI.displayErrorMessage("Course code already exists!");
            }
        } else {
            MessageUI.displayErrorMessage("Action Cancelled!");
        }
    }

    public void removeCourse() {
        String courseCode = ui.removeCourseUI();

        if (courseCode != null) {
            Course courseToRemove = findCourseByCode(courseCode);
            if (courseToRemove != null) {
                if (ui.confirmAction("Are you sure you want to remove this course?")) {
                    courses.remove(courseCode);
                    cDao.saveCourseToFile(courses);
                    MessageUI.displaySuccessMessage("Course Removed!");
                } else {
                    MessageUI.displaySuccessMessage("Course Not Removed.");
                }
            }
        } else {
            MessageUI.displayErrorMessage("Action Cancelled!");
        }
    }

    public Course findCourseByCode(String courseCode) {
        if (courseCode == null) {
            courseCode = ui.findCourseUI();
            if (courseCode != null) {
                if (courses.containsKey(courseCode)) {
                    Course course = courses.get(courseCode);
                    System.out.println(course.toString());
                    return course;
                } else {
                    MessageUI.displayErrorMessage("Course Not Found!");
                }
            } else {
                MessageUI.displayErrorMessage("Action Cancelled!");
                return null;
            }
        }
        return courses.get(courseCode);
    }

    public void amendCourseDetails() {
        ui.amendCourseDetails();
        String courseCode = ui.courseCodeUI();
        String newCourseName = ui.courseNameUI();
        if (courseCode != null && newCourseName != null) {
            Course course = courses.get(courseCode);
            if (course != null) {
                course.updateCourseName(newCourseName);
                cDao.saveCourseToFile(courses);
                MessageUI.displaySuccessMessage("Course's Details Updated!");
            } else {
                MessageUI.displayErrorMessage("Update Failed!");
            }
        }else{
            MessageUI.displayErrorMessage("Action Cancelled!");
        }
    }

    public void listAllCourses() {
        Iterator<Entry<String, Course>> iterator = courses.getIterator();
        int totalCourse = 0;
        System.out.println("\n\n=====================================================================================");
        System.out.println("Course Code      Course Name                  Credit Hours           ProgrammeCode");
        System.out.println("=====================================================================================");
        while (iterator.hasNext()) {
            Entry<String, Course> courseEntry = iterator.next();
            String courseCode = courseEntry.getKey();
            Course course = courseEntry.getValue();

            System.out.printf("%-16s %-30s %-20.2f %s", courseCode, course.getCourseName(), course.getCreditHours(), course.getProgrammeCodesAsString());
            System.out.println("");
            totalCourse++;

        }
        System.out.println("Total Number of Course: " + totalCourse);

    }

    public void addProgrammeToCourse() {

        ui.addProgrammeToCourseUI();
        listAllProgrammeCode();
        String courseCode = ui.courseCodeUI();
        String programmeCode = ui.programmeCodeUI();
        System.out.println("==============================");

        if (courseCode != null && programmeCode != null) { // Add a null check here
            Course course = courses.get(courseCode);

            Programme programme = findProgrammeByCode(programmeCode);

            if (course != null && programme != null) {
                course.addProgrammeCode(programmeCode); // Add the program code to the SortedArrayList
                cDao.saveCourseToFile(courses);
                MessageUI.displaySuccessMessage("Programme added to course.");
            } else {
                MessageUI.displayErrorMessage("Invalid course or programme code.");
            }
        } else {
            MessageUI.displayErrorMessage("Action Cancelled");
        }
    }

    public void removeProgrammeFromCourse() {
        ui.removeProgrammeFromCourseUI();
        String courseCode = ui.courseCodeUI();
        String programmeCode = ui.programmeCodeUI();
        System.out.println("==============================");

        if (courseCode != null) { // Add a null check here
            Course course = courses.get(courseCode);

            if (course != null) {
                // Check if the course contains the specified program code
                if (course.getProgrammeCodesAsString().contains(programmeCode)) {
                    course.removeProgrammeCode(programmeCode); // Remove the program code from the course
                    cDao.saveCourseToFile(courses);
                    MessageUI.displaySuccessMessage("Programme removed from course.");
                } else {
                    MessageUI.displayErrorMessage("Programme not found in the course.");
                }
            } else {
                MessageUI.displayErrorMessage("Course not found.");
            }
        } else {
            MessageUI.displayErrorMessage("Action Cancelled");
        }
    }

    private Programme findProgrammeByCode(String programmeCode) {
        Iterator<Programme> iterator = programme.getIterator();

        while (iterator.hasNext()) {
            Programme prog = iterator.next();
            if (prog.getProgrammeCode().equals(programmeCode)) {
                return prog;
            }
        }

        return null;
    }

    public void listAllProgrammeCode() {
        Iterator<Programme> iterator = programme.getIterator();
        int totalProgrammes = 0;

        System.out.println("Programme Codes:");
        while (iterator.hasNext()) {
            Programme prog = iterator.next();
            if (totalProgrammes != 0) {
                System.out.print(", ");
            }
            System.out.print("" + prog.getProgrammeCode());
            totalProgrammes++;
        }

        System.out.println("\nTotal Number of Programmes: " + totalProgrammes);
    }

    public void displayCourseLog() {
        System.out.println("\n\n\nCourse Logs:");
        System.out.println("==============================================================");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        Iterator<Entry<String, Course>> courseIterator = courses.getIterator();

        while (courseIterator.hasNext()) {
            Entry<String, Course> courseEntry = courseIterator.next();
            Course course = courseEntry.getValue();

            if (course.getDateAdded() != null) {
                System.out.println("Course Added:");
                System.out.println("Course Code: " + course.getCourseCode());
                System.out.println("Course Name: " + course.getCourseName());
                System.out.println("Date Added: " + dateFormat.format(course.getDateAdded()));
                System.out.println("==============================================================");
            }
        }

        System.out.println("End of Course Logs");
    }

    public static void main(String[] args) {
        CourseManagementSubsystem cSystem = new CourseManagementSubsystem();
        cSystem.runCourseManagement();
    }
}
