package control;

import adt.AVLTreeSet;
import adt.LimitedLinkedStack;
import adt.LinkedStack;
import adt.SetInterface;
import adt.SortedListInterface;
import adt.StackInterface;
import boundary.ProgrammeManagementUI;
import dao.ProgrammeDao;
import entity.Programme;
import entity.TutorialGroup;
import java.util.Iterator;
import utility.DateTimeUtility;
import utility.MessageUI;

/**
 *
 * @author Gan Zhi Ken
 */
public class ProgrammeManagement {

    private SetInterface<Programme> programme;
    private final ProgrammeManagementUI ui;
    private final ProgrammeDao pDao;
    private final StackInterface<String> changeHistory;
    private final StackInterface<SetInterface<Programme>> undoStack;
    private final StackInterface<SetInterface<Programme>> redoStack;

    public ProgrammeManagement() {
        pDao = new ProgrammeDao();
        programme = pDao.retrieveProgrammeFromFile();
        ui = new ProgrammeManagementUI();
        changeHistory = new LinkedStack();
        undoStack = new LimitedLinkedStack(5);
        redoStack = new LimitedLinkedStack(5);
    }

    public void runProgrammeManagement() {

        while (true) {
            int choice = ui.getMenuChoice();

            switch (choice) {
                case 1 -> {
                    addNewProgramme();
                }
                case 2 -> {
                    removeProgramme();
                }
                case 3 -> {
                    updateProgramme();
                }
                case 4 -> {
                    displayProgramme();
                }
                case 5 -> {
                    searchProgramme();
                }
                case 6 -> {
                    listTutorialGroups();
                }
                case 7 -> {
                    generateHistoryReport();
                }
                case 0 -> {
                    return;
                }
                default ->
                    MessageUI.displayInvalidChoiceMsg();
            }
            pDao.saveProgrammeToFile(programme);
        }
    }

    public void addNewProgramme() {
        Programme newProgramme = ui.getNewProgrammeDetail();

        SetInterface<Programme> oldProg = new AVLTreeSet();
        oldProg.addAll(programme);
        undoStack.push(oldProg);

        if (programme.add(newProgramme)) {
            MessageUI.displaySuccessfulMsg();
            changeHistory.push(DateTimeUtility.currentDateTime() + ", {Added Programme : " + newProgramme.getProgrammeCode() + "}");

        } else {
            MessageUI.displayFailOperationMsg();
            undoStack.pop();
        }

    }

    public void displayProgramme() {
        ui.listProgramme(programme);
    }

    public void searchProgramme() {

        ui.searchProgramHeader();

        String code = ui.getProgrammeCode();
        Programme p = programme.get(new Programme(code));

        if (p != null) {
            ui.listOneProgramme(p.toString());
        } else {
            MessageUI.displayNotFoundMsg();
        }
    }

    private void removeProgramme() {
        ui.removeProgramHeader();

        String code = ui.getProgrammeCode();

        SetInterface<Programme> oldProg = new AVLTreeSet();
        oldProg.addAll(programme);
        undoStack.push(oldProg);

        if (programme.remove(new Programme(code))) {
            MessageUI.displaySuccessfulMsg();
            changeHistory.push(DateTimeUtility.currentDateTime() + ", {Removed Programme : " + code + "}");

        } else {
            MessageUI.displayNotFoundMsg();
            undoStack.pop();
        }
    }

    private void listTutorialGroups() {
        ui.ListTutorialGroupsHeader();
        String code = ui.getProgrammeCode();
        Programme p = programme.get(new Programme(code));

        if (p == null) {
            MessageUI.displayNotFoundMsg();
        } else {
            ui.listTutorialGroups(p);
        }

    }

    private void updateProgramme() {
        ui.updateProgramHeader();

        String code = ui.getProgrammeCode();
        Programme p = programme.get(new Programme(code));

        if (p == null) {
            MessageUI.displayNotFoundMsg();
            return;
        }

        while (true) {
            ui.listOneProgramme(p.toString());
            int choice = ui.getUpdateMenuChoice();

            switch (choice) {
                case 1 -> {
                    changeProgrammeCode(p);
                }
                case 2 -> {
                    changeProgrammeName(p);
                }
                case 3 -> {
                    updateNumberOfGroups(p);
                }
                case 4 -> {
                    addTutorialGroups(p);
                }
                case 5 -> {
                    removeTutorialGroups(p);
                }
                case 0 -> {
                    return;
                }
                default ->
                    MessageUI.displayInvalidChoiceMsg();
            }
        }

    }

    private void removeTutorialGroups(Programme p) {
        int oldNum = p.getNoOfTutorialGroups();
        int number = ui.getNumberOfRemovedTutorialGroup();

        if (number > oldNum) {
            MessageUI.displayFailOperationMsg();
            return;
        }

        SetInterface<Programme> oldProg = new AVLTreeSet();
        oldProg.addAll(programme);
        undoStack.push(oldProg);

        p.removeNoOfTutorialGroup(number);

        MessageUI.displaySuccessfulMsg();
        changeHistory.push(DateTimeUtility.currentDateTime() + ", Programme : " + p.getProgrammeCode()
                + " Number of Tutorial Group Updated, {From :" + oldNum + ", To :" + p.getNoOfTutorialGroups() + "}");

    }

    private void addTutorialGroups(Programme p) {
        int oldNum = p.getNoOfTutorialGroups();
        int number = ui.getNumberOfAddedTutorialGroup();

        SetInterface<Programme> oldProg = new AVLTreeSet();
        oldProg.addAll(programme);
        undoStack.push(oldProg);

        p.addNoOfTutorialGroups(number);

        MessageUI.displaySuccessfulMsg();
        changeHistory.push(DateTimeUtility.currentDateTime() + ", Programme :" + p.getProgrammeCode()
                + " Number of Tutorial Group Updated, {From :" + oldNum + ", To :" + p.getNoOfTutorialGroups() + "}");
    }

    private void updateNumberOfGroups(Programme p) {
        int number = ui.getNumberOfTutorialGroup();
        int oldNum = p.getNoOfTutorialGroups();

        if (number < 0) {
            MessageUI.displayFailOperationMsg();
            return;
        }

        SetInterface<Programme> oldProg = new AVLTreeSet();
        oldProg.addAll(programme);
        undoStack.push(oldProg);

        p.updateNoOfTutorialGroup(number);

        MessageUI.displaySuccessfulMsg();
        changeHistory.push(DateTimeUtility.currentDateTime() + ", Programme : " + p.getProgrammeCode()
                + " Number of Tutorial Group Updated, {From :" + oldNum + ", To :" + p.getNoOfTutorialGroups() + "}");

    }

    private void changeProgrammeName(Programme p) {
        String newName = ui.getProgrammeName();
        String oldName = p.getProgrammeName();

        SetInterface<Programme> oldProg = new AVLTreeSet();
        oldProg.addAll(programme);
        undoStack.push(oldProg);

        p.setProgrammeName(newName);

        MessageUI.displaySuccessfulMsg();
        changeHistory.push(DateTimeUtility.currentDateTime() + ", Programme name changed, {From :" + oldName + ", To :" + newName + "}");
    }

    private void changeProgrammeCode(Programme p) {
        String oldCode = p.getProgrammeCode();
        String newCode = ui.getProgrammeCode();

        //whether the code is exist
        if (programme.contains(new Programme(newCode))) {
            MessageUI.displayFailOperationMsg();
            return;
        }

        SetInterface<Programme> oldProg = new AVLTreeSet();
        oldProg.addAll(programme);
        undoStack.push(oldProg);

        programme.remove(p); // remove and add, to maintain the sorting
        p.setProgrammeCode(newCode);
        SortedListInterface<TutorialGroup> grps = p.getTutorialGroups();

        Iterator<TutorialGroup> iterator = grps.getIterator();
        int i = 1;
        while (iterator.hasNext()) {
            TutorialGroup g = iterator.next();
            g.setGroupCode(p.getProgrammeCode() + i);
            i++;
        }

        programme.add(p);
        MessageUI.displaySuccessfulMsg();
        changeHistory.push(DateTimeUtility.currentDateTime() + ", Programme code changed, {From :" + oldCode + ", To :" + newCode + "}");

    }

    private void generateHistoryReport() {

        while (true) {
            ui.changeHistoryReport(changeHistory);

            int choice = ui.getUndoRedoChoice();

            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    undo();
                }
                case 2 -> {
                    redo();
                }
                default -> {
                    MessageUI.displayInvalidChoiceMsg();
                }
            }
        }
    }

    private void undo() {
        if (undoStack.isEmpty()) {
            MessageUI.displayFailOperationMsg();
            return;
        }

        SetInterface<Programme> currentProg = new AVLTreeSet();
        currentProg.addAll(programme);
        redoStack.push(currentProg);

        programme = undoStack.pop();
        changeHistory.push(DateTimeUtility.currentDateTime() + ", Undo operation taken");
        MessageUI.displaySuccessfulMsg();
    }

    private void redo() {
        if (redoStack.isEmpty()) {
            MessageUI.displayFailOperationMsg();
            return;
        }

        SetInterface<Programme> currentProg = new AVLTreeSet();
        currentProg.addAll(programme);
        undoStack.push(currentProg);

        programme = redoStack.pop();
        changeHistory.push(DateTimeUtility.currentDateTime() + ", Redo operation taken");
        MessageUI.displaySuccessfulMsg();
    }

    public static void main(String[] args) {
        new ProgrammeManagement().runProgrammeManagement();
    }
}
