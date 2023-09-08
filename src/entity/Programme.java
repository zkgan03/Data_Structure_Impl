package entity;

import adt.SortedArrayList;
import adt.SortedListInterface;
import java.io.Serializable;

/**
 *
 * @author Gan Zhi Ken
 */
public class Programme implements Serializable, Comparable<Programme> {

    private String programmeCode;
    private String programmeName;
    private SortedListInterface<TutorialGroup> tutorialGroups;

    public Programme() {
        this("", "");
    }

    public Programme(String programmeCode) {
        this(programmeCode, "");
    }

    public Programme(String programmeCode, String programmeName) {
        this(programmeCode, programmeName, 0);
    }

    public Programme(String programmeCode, String programmeName, int numberOfTutorialGroups) {
        this.programmeCode = programmeCode;
        this.programmeName = programmeName;
        this.tutorialGroups = new SortedArrayList();
        addNoOfTutorialGroups(numberOfTutorialGroups);
    }

    public String getProgrammeCode() {
        return programmeCode;
    }

    public String getProgrammeName() {
        return programmeName;
    }

    public SortedListInterface<TutorialGroup> getTutorialGroups() {
        return tutorialGroups;
    }

    public void setProgrammeCode(String programmeCode) {
        this.programmeCode = programmeCode;
    }

    public void setProgrammeName(String programmeName) {
        this.programmeName = programmeName;
    }

    public void setTutorialGroups(SortedListInterface tutorialGroups) {
        this.tutorialGroups = tutorialGroups;
    }

    public int getNoOfTutorialGroups() {
        return tutorialGroups.getNumberOfEntries();
    }

    public void addNoOfTutorialGroups(int numberOfTutorialGroups) {
        int total = numberOfTutorialGroups + getNoOfTutorialGroups();
        for (int i = 1 + getNoOfTutorialGroups(); i <= total; i++) {
            String code = (programmeCode + i);
            tutorialGroups.add(new TutorialGroup(code));
        }
    }

    public void removeNoOfTutorialGroup(int numberOfTutorialGroups) {
        if (numberOfTutorialGroups > getNoOfTutorialGroups()) {
            return;
        }
        int currentSize = getNoOfTutorialGroups();

        for (int i = currentSize; i > (currentSize - numberOfTutorialGroups); i--) {
            String code = programmeCode + i;
            tutorialGroups.remove(new TutorialGroup(code));
        }
    }

    public void updateNoOfTutorialGroup(int numberOfTutorialGroups) {
        if (numberOfTutorialGroups < 0) {
            return;
        }

        int currentSize = getNoOfTutorialGroups();

        if (numberOfTutorialGroups < currentSize) {
            removeNoOfTutorialGroup(currentSize - numberOfTutorialGroups);
        } else {
            addNoOfTutorialGroups(numberOfTutorialGroups - currentSize);
        }
    }

    public TutorialGroup getTutorialGroupDetail(String grpCode) {
        return tutorialGroups.get(new TutorialGroup(grpCode));
    }

    @Override
    public String toString() {
        return "Programme Code :" + programmeCode + "\nProgramme Name :" + programmeName + "\nNumber of Tutorial Group :"
                + getNoOfTutorialGroups() + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Programme prog = (Programme) obj;

        return this.programmeCode.equals(prog.programmeCode);
    }

    @Override
    public int compareTo(Programme prog) {

        return this.programmeCode.compareTo(prog.programmeCode);

    }

}
