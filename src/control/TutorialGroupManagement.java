package control;

import adt.Entry;
import dao.TutorialDao;
import adt.MapInterface;
import adt.HashMap;
import adt.SortedArrayList;
import adt.SortedListInterface;
import boundary.TutorialGroupManagementUI;
import entity.Student;
import entity.TutorialGroup;
import java.util.Iterator;
import utility.MessageUI;

/**
 *
 * @author TAN KAI JIAN
 */
public class TutorialGroupManagement {

    private SortedListInterface<TutorialGroup> tutorialGroup = new SortedArrayList<>();
    private TutorialGroupManagementUI tutorialGroupUI = new TutorialGroupManagementUI();
    private final TutorialDao tDao;

    public TutorialGroupManagement() {
        tDao = new TutorialDao();
        tutorialGroup = tDao.retrieveFromFile();
        tutorialGroupUI = new TutorialGroupManagementUI();
    }

    public void runTutorialGroupManagement() {
        while (true) {
            int choice = tutorialGroupUI.getMenuChoice();

            switch (choice) {
                case 1 -> {
                    addStudent();
                }
                case 2 -> {
                    removeStudent();
                }
                case 3 -> {
                    changeTutorialGroup();
                }
                case 4 -> {
                    findStudent();
                }
                case 5 -> {
                    listStudent();
                }
                case 6 -> {
                    filterGroup();
                }
                case 7 -> {
                    generateNumOfGenderReport();
                }
                case 0 -> {
                    MessageUI.displayExitMessage();
                    return;
                }
                default ->
                    MessageUI.displayInvalidChoiceMessage();
            }
            tDao.saveToFile(tutorialGroup);
        }
    }

    public void addStudent() {
        tutorialGroupUI.getAddStudHeader();
        String groupCode;
        String studID;

        groupCode = tutorialGroupUI.getGroupCode();

        do {
            //Student ID validation
            do {
                studID = tutorialGroupUI.getStudID();

                if (!isValidStudentID(studID)) {
                    MessageUI.displayInvalidStudID();
                }

            } while (!isValidStudentID(studID));

            // Check if the student ID already exists in any group
            if (studentExistsInOtherGroup(studID, groupCode)) {
                MessageUI.displayAddStudExistAnotherGrpMessage(studID);
            } else {
                // Check if the student ID already exists in the current group
                TutorialGroup currentGroup = getTutorialGroup(groupCode);
                if (currentGroup != null && currentGroup.getStudent().containsKey(studID)) {
                    MessageUI.displayAddStudExistCurrentGrpMessage(studID, groupCode);
                } else {
                    break;
                }
            }
        } while (true);

        String studName = tutorialGroupUI.getStudName();

        String studGender;
        do {
            studGender = tutorialGroupUI.getStudGender().toUpperCase(); // Convert to uppercase

            // Check if the studGender is valid
            if (studGender.equals("MALE") || studGender.equals("FEMALE")) {
                break;
            } else {
                MessageUI.displayAddStudInvalidGender();
            }
        } while (true);

        int yearOfStudy = tutorialGroupUI.getYearOfStudy();

        TutorialGroup group = getOrCreateTutorialGroup(groupCode);

        Student newStudent = new Student(studID, studName, studGender, yearOfStudy);

        group.addStudent(newStudent);

        MessageUI.displayStudAdded();
    }

    private boolean studentExistsInOtherGroup(String studentID, String currentGroupCode) {
        // Iterate through all tutorial groups to check if the student ID exists in any other group
        Iterator<TutorialGroup> iterator = tutorialGroup.getIterator();
        while (iterator.hasNext()) {
            TutorialGroup group = iterator.next();
            if (!group.getGroupCode().equals(currentGroupCode) && group.getStudent().containsKey(studentID)) {
                return true;
            }
        }
        return false;
    }

    private TutorialGroup getOrCreateTutorialGroup(String groupCode) {
        Iterator<TutorialGroup> iterator = tutorialGroup.getIterator();
        while (iterator.hasNext()) {
            TutorialGroup group = iterator.next();
            if (group.getGroupCode().equals(groupCode)) {
                return group;
            }
        }

        // If not found, create a new tutorial group
        TutorialGroup newGroup = new TutorialGroup(groupCode);
        tutorialGroup.add(newGroup);
        return newGroup;
    }

    public void removeStudent() {
        tutorialGroupUI.getRemoveStudHeader();

        String studentID;

        //Student ID validation
        do {
            studentID = tutorialGroupUI.getStudID();

            if (!isValidStudentID(studentID)) {
                MessageUI.displayInvalidStudID();
            }

        } while (!isValidStudentID(studentID));

        String groupCode = tutorialGroupUI.getGroupCode();

        // Find the tutorial group
        TutorialGroup tutorialGroup = getTutorialGroup(groupCode);

        if (tutorialGroup != null) {
            // Check if the student with the specified ID exists in the tutorial group
            Student studentToRemove = tutorialGroup.getStudent(studentID);

            if (studentToRemove != null) {
                tutorialGroup.removeStudent(studentID);
                MessageUI.displayStudRemoved(studentID, groupCode);
            } else {
                MessageUI.displayRemoveStudNotFound(studentID, groupCode);
            }
        } else {
            MessageUI.displayRemoveStudGrpNotFound(groupCode);
        }
    }

    private TutorialGroup getTutorialGroup(String groupCode) {
        Iterator<TutorialGroup> iterator = tutorialGroup.getIterator();
        while (iterator.hasNext()) {
            TutorialGroup group = iterator.next();
            if (group.getGroupCode().equals(groupCode)) {
                return group;
            }
        }
        return null;
    }

    public void changeTutorialGroup() {
        tutorialGroupUI.getChgTutoGrpHeader();
        String studentID;
        String currentGroupCode;

        do {
            //Student ID validation
            do {
                studentID = tutorialGroupUI.getStudID();

                if (!isValidStudentID(studentID)) {
                    MessageUI.displayInvalidStudID();
                }

            } while (!isValidStudentID(studentID));

            currentGroupCode = tutorialGroupUI.getCurrentGroupCode();

            TutorialGroup currentTutorialGroup = getTutorialGroup(currentGroupCode);

            if (currentTutorialGroup != null) {
                // Find the student with the specified ID in the current group
                Student studentToChange = currentTutorialGroup.getStudent(studentID);

                if (studentToChange != null) {
                    break;
                } else {
                    MessageUI.displayChangeStudNotFound(studentID, currentGroupCode);
                }
            } else {
                MessageUI.displayChangeStudGrpNotFound(currentGroupCode);
            }
        } while (true);

        String newGroupCode = tutorialGroupUI.getNewGroupCode();

        TutorialGroup newTutorialGroup = getOrCreateTutorialGroup(newGroupCode);

        TutorialGroup currentTutorialGroup = getTutorialGroup(currentGroupCode);

        if (currentTutorialGroup != null) {
            Student studentToChange = currentTutorialGroup.getStudent(studentID);

            if (studentToChange != null) {
                currentTutorialGroup.removeStudent(studentID);
                newTutorialGroup.addStudent(studentToChange);
                MessageUI.displayStudChanged(studentID, currentGroupCode, newGroupCode);
            } else {
                MessageUI.displayChangeStudNotFoundGrp(studentID, currentGroupCode);
            }
        } else {
            MessageUI.displayChangeStudCurrentGrpNotFound(currentGroupCode);
        }
    }

    private void findStudent() {
        tutorialGroupUI.getFindStdHeader();
        String groupCode = tutorialGroupUI.getGroupCode();
        String studentID;

        //Student ID validation
        do {
            studentID = tutorialGroupUI.getStudID();

            if (!isValidStudentID(studentID)) {
                MessageUI.displayInvalidStudID();
            }
        } while (!isValidStudentID(studentID));

        TutorialGroup tutorialGroup = getTutorialGroup(groupCode);

        if (tutorialGroup != null) {
            // Get the custom HashMap of students in the tutorial group
            MapInterface<String, Student> studentsMap = tutorialGroup.getStudent();

            // Check if the student with the specified ID exists in the tutorial group
            if (studentsMap.containsValue(new Student(studentID, "", "", 0))) {
                Student student = studentsMap.get(studentID);
                tutorialGroupUI.printStudFound(groupCode, student.getStudentID(), student.getStudentName(), student.getGender(), student.getYearOfStudy());
            } else {
                MessageUI.displayFindStudNotFound(studentID, groupCode);
            }
        } else {
            MessageUI.displayFindStudGrpNotFound(groupCode);
        }
    }

    private void listStudent() {
        tutorialGroupUI.getListStdHeader();
        String groupCode = tutorialGroupUI.getGroupCode();

        TutorialGroup tutorialGroup = getTutorialGroup(groupCode);

        tutorialGroupUI.printLine();

        if (tutorialGroup != null) {
            tutorialGroupUI.printListStudGrpCode(groupCode);

            // Get the custom HashMap of students in the tutorial group
            MapInterface<String, Student> studentsMap = tutorialGroup.getStudent();

            // Iterate through and display each student
            Iterator<Entry<String, Student>> studentIterator = studentsMap.getIterator();

            tutorialGroupUI.printListStudDetailsHeader();

            while (studentIterator.hasNext()) {
                Entry<String, Student> entry = studentIterator.next();
                String studentID = entry.getKey();
                Student student = entry.getValue();
                tutorialGroupUI.printListStudDetails(studentID, student.getStudentName());
            }
        } else {
            MessageUI.displayListStudGrpNotFound(groupCode);
        }
        tutorialGroupUI.printLine();
    }

    private void filterGroup() {
        tutorialGroupUI.getFilterGrpHeader();

        int filterChoice = tutorialGroupUI.getFilterChoice();

        int numOfStud = tutorialGroupUI.getFilterNumOfStud();

        // Determine the operator based on the user's choice
        String operator = (filterChoice == 1) ? "more than" : "less than";

        int matchingGroups = 0;

        Iterator<TutorialGroup> iterator = tutorialGroup.getIterator();
        while (iterator.hasNext()) {
            TutorialGroup group = iterator.next();

            int groupNoOfStudents = group.getNoOfStudent();

            if ((filterChoice == 1 && groupNoOfStudents > numOfStud) || (filterChoice == 2 && groupNoOfStudents < numOfStud)) {
                System.out.println("Tutorial Group Code: " + group.getGroupCode() + " - No. of Students: " + groupNoOfStudents);
                matchingGroups++;
            }
        }
        System.out.println("Total tutorial groups with " + operator + " " + numOfStud + " students: " + matchingGroups);
    }

    private void generateNumOfGenderReport() {
        tutorialGroupUI.getReportHeader();

        // Create a HashMap to store the counts of male and female students for each tutorial group
        MapInterface<String, MapInterface<String, Integer>> genderCounts = new HashMap<>();

        Iterator<TutorialGroup> iterator = tutorialGroup.getIterator();

        while (iterator.hasNext()) {
            TutorialGroup group = iterator.next();
            String groupCode = group.getGroupCode();

            // Get the custom HashMap of students in the tutorial group
            MapInterface<String, Student> studentsMap = group.getStudent();

            int maleCount = 0;
            int femaleCount = 0;

            // Get an iterator for the studentsMap
            Iterator<Entry<String, Student>> studentIterator = studentsMap.getIterator();

            while (studentIterator.hasNext()) {
                Entry<String, Student> entry = studentIterator.next();
                Student student = entry.getValue();

                String gender = student.getGender();
                if (gender.equalsIgnoreCase("MALE")) {
                    maleCount++;
                } else if (gender.equalsIgnoreCase("FEMALE")) {
                    femaleCount++;
                }
            }

            // Create a HashMap to store gender counts for the current tutorial group
            MapInterface<String, Integer> groupGenderCounts = new HashMap<>();
            groupGenderCounts.put("Male", maleCount);
            groupGenderCounts.put("Female", femaleCount);

            // Add the group's gender counts to the overall report
            genderCounts.put(groupCode, groupGenderCounts);
        }

        // Get an iterator for genderCounts
        Iterator<Entry<String, MapInterface<String, Integer>>> groupIterator = genderCounts.getIterator();

        while (groupIterator.hasNext()) {
            Entry<String, MapInterface<String, Integer>> entry = groupIterator.next();
            String groupCode = entry.getKey();
            MapInterface<String, Integer> groupGenderCounts = entry.getValue();

            int maleCount = groupGenderCounts.get("Male");
            int femaleCount = groupGenderCounts.get("Female");
            int totalStudents = maleCount + femaleCount;

            tutorialGroupUI.printGenderReport(groupCode, totalStudents, maleCount, femaleCount);
        }

        tutorialGroupUI.getReportFooter();
    }

    private boolean isValidStudentID(String studentID) {
        return studentID != null && studentID.matches("^[a-zA-Z]{3}$");
    }

    public static void main(String[] args) {
        new TutorialGroupManagement().runTutorialGroupManagement();
    }

}
