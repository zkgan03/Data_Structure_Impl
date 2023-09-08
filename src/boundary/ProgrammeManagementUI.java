package boundary;

import adt.SetInterface;
import adt.SortedListInterface;
import adt.StackInterface;
import entity.Programme;
import entity.TutorialGroup;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author zhiken
 */
public class ProgrammeManagementUI {

    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        System.out.println("Programme Management Subsystem");
        System.out.println("------------------------------");
        System.out.println("1. Add a new Programme");
        System.out.println("2. Remove Programme");
        System.out.println("3. Update Programme");
        System.out.println("4. Display all Programme");
        System.out.println("5. Search a Programme");
        System.out.println("6. List Tutorial Groups of a Programme");
        System.out.println("7. Generate Edit History Report");
        System.out.println("0. Quit");

        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println("\n----------------------------------");

        return choice;
    }

    public Programme getNewProgrammeDetail() {
        System.out.println("\n----------------------------------");
        System.out.println("Add new Programme");
        System.out.println("-----------------");
        String id = getProgrammeCode();
        String name = getProgrammeName();
        int number = getNumberOfTutorialGroup();
        System.out.println("\n----------------------------------");

        return new Programme(id, name, number);
    }

    public void listProgramme(SetInterface<Programme> pList) {
        System.out.println("\nProgramme Details:");
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.printf("%-6s %-60s %-36s\n", "Code", "Name", "Number Of Tutorial Groups");
        System.out.println("----------------------------------------------------------------------------------------------");

        Iterator<Programme> iterator = pList.getIterator();
        while (iterator.hasNext()) {
            Programme p = iterator.next();
            System.out.printf("%-6s %-50s %35d\n", p.getProgrammeCode(), p.getProgrammeName(), p.getNoOfTutorialGroups());

        }

        System.out.println("----------------------------------------------------------------------------------------------\n\n");

    }

    public void listOneProgramme(String p) {
        System.out.println("\nProgramme Details:");
        System.out.println("------------------");
        System.out.println(p);
        System.out.println("\n----------------------------------");
    }

    public String getProgrammeCode() {

        System.out.print("Enter Programme Code :");
        String code = scanner.nextLine();

        return code;
    }

    public String getProgrammeName() {
        System.out.print("Enter Programme Name :");
        String name = scanner.nextLine();

        return name;
    }

    public int getNumberOfTutorialGroup() {
        System.out.print("Enter number of Tutorial Group : ");
        int number = scanner.nextInt();
        scanner.nextLine();

        return number;
    }

    public int getNumberOfRemovedTutorialGroup() {
        System.out.print("Enter number of Tutorial Group needed to be removed: ");
        int number = scanner.nextInt();
        scanner.nextLine();

        return number;
    }

    public int getNumberOfAddedTutorialGroup() {
        System.out.print("Enter number of Tutorial Group needed to be added: ");
        int number = scanner.nextInt();
        scanner.nextLine();

        return number;
    }

    public void searchProgramHeader() {
        System.out.println("Search a Program");
        System.out.println("----------------");
    }

    public void removeProgramHeader() {
        System.out.println("Remove a Program");
        System.out.println("----------------");
    }

    public void updateProgramHeader() {
        System.out.println("Update a Program");
        System.out.println("----------------");
    }

    public void ListTutorialGroupsHeader() {
        System.out.println("List Tutorial Groups of a programme");
        System.out.println("-----------------------------------");
    }

    public void listTutorialGroups(Programme p) {
        System.out.println("\nTutorials Groups");
        System.out.println("----------------");

        SortedListInterface tList = p.getTutorialGroups();

        Iterator<TutorialGroup> iterator = tList.getIterator();
        while (iterator.hasNext()) {
            TutorialGroup t = iterator.next();
            System.out.println("Group Code :" + t.getGroupCode() + ", Number of Students :" + t.getNoOfStudent());
        }

        System.out.println("\n----------------------------------");

    }

    public int getUpdateMenuChoice() {

        System.out.println("1. Update Programme Code");
        System.out.println("2. Update Programme Name");
        System.out.println("3. Update Number of Tutorial Groups");
        System.out.println("4. Add Tutorial Groups");
        System.out.println("5. Remove Tutorial Groups");
        System.out.println("0. Back");

        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println("\n----------------------------------");

        return choice;
    }

    public void changeHistoryReport(StackInterface<String> changeHistory) {
        System.out.println("Programme Edit History");
        System.out.println("----------------------");

        if (changeHistory.isEmpty()) {
            System.out.println("No changes has made...");

        } else {
            Iterator iterator = changeHistory.getIterator();

            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        }

        System.out.println("\n----------------------------------");
    }

    public int getUndoRedoChoice() {
        System.out.println("Do you want to Quit(0) / Undo(1) / Redo(2) ? ");
        System.out.print("Enter choice  : ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\n----------------------------------");

        return choice;

    }

}
