/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.util.Scanner;

/**
 *
 * @author zhiken
 */
public class MessageUI {

    private static Scanner scanner = new Scanner(System.in);

    public static void displayFailOperationMsg() {
        System.out.println("\n<< This operation was failed... >>\n");
    }

    public static void displayInvalidChoiceMessage() {
        System.out.println("\nInvalid choice.");
    }

    public static void displayInvalidChoiceMsg() {
        System.out.println("\n<< Invalid Choice! >>\n");
    }

    public static void displaySuccessfulMsg() {
        System.out.println("\n<< Successful >>\n");
    }

    public static void displayNotFoundMsg() {
        System.out.println("\n<< Not found >>\n");
    }

    public static void displayQuitMsg() {
        System.out.println("\n<< Quiting... >>\n");
    }

    public static void displayErrorMessage(String message) {
        System.out.println("Error: " + message);
    }

    public static void displaySuccessMessage(String message) {
        System.out.println("Success: " + message);
    }

    public static void pressEnterToContinue() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine(); // Wait for user input
    }

    public static void displayExitMessage() {
        System.out.println("\nExiting system\n");
    }

    public static void displayAddStudExistAnotherGrpMessage(String studID) {
        System.out.println("Student ID " + studID + " already exists in another group. Please enter a different student ID.");
    }

    public static void displayAddStudExistCurrentGrpMessage(String studID, String groupCode) {
        System.out.println("Student ID " + studID + " already exists in the current group " + groupCode + ". Please enter a different student ID.");
    }

    public static void displayAddStudInvalidGender() {
        System.out.println("Invalid input for gender. Please enter 'MALE' or 'FEMALE'.");
    }

    public static void displayStudAdded() {
        System.out.println("\nNew Student Added!\n");
    }

    public static void displayStudRemoved(String studentID, String groupCode) {
        System.out.println("Student with ID " + studentID + " removed from the group " + groupCode + "\n");
    }

    public static void displayRemoveStudNotFound(String studentID, String groupCode) {
        System.out.println("Student with ID " + studentID + " not found in the group " + groupCode + "\n");
    }

    public static void displayRemoveStudGrpNotFound(String groupCode) {
        System.out.println("Tutorial group with code " + groupCode + " not found.");
    }

    public static void displayChangeStudNotFound(String studentID, String currentGroupCode) {
        System.out.println("Student with ID " + studentID + " not found in the group " + currentGroupCode + ". Please enter a valid student ID and group code.");
    }

    public static void displayChangeStudGrpNotFound(String currentGroupCode) {
        System.out.println("Current tutorial group with code " + currentGroupCode + " not found. Please enter a valid group code.");
    }

    public static void displayStudChanged(String studentID, String currentGroupCode, String newGroupCode) {
        System.out.println("Student with ID " + studentID + " moved from group " + currentGroupCode + " to group " + newGroupCode);
    }

    public static void displayChangeStudNotFoundGrp(String studentID, String currentGroupCode) {
        System.out.println("Student with ID " + studentID + " not found in the group " + currentGroupCode);
    }

    public static void displayChangeStudCurrentGrpNotFound(String currentGroupCode) {
        System.out.println("Current tutorial group with code " + currentGroupCode + " not found.");
    }

    public static void displayFindStudNotFound(String studentID, String groupCode) {
        System.out.println("Student with ID " + studentID + " not found in the group " + groupCode);
    }

    public static void displayFindStudGrpNotFound(String groupCode) {
        System.out.println("Tutorial group with code " + groupCode + " not found.");
    }

    public static void displayListStudGrpNotFound(String groupCode) {
        System.out.println("Tutorial group with code " + groupCode + " not found.");
    }

    public static void displayInvalidStudID() {
        System.out.println("Invalid Student ID! Must have 3 character");
    }
}
