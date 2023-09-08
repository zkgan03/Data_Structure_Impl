/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.AVLTreeSet;
import adt.SetInterface;
import entity.Programme;

/**
 *
 * @author zhiken
 */
public class ProgrammeInit {

//  Method to return a collection of with hard-coded entity values
    public SetInterface<Programme> initializeProducts() {

        SetInterface<Programme> pList = new AVLTreeSet<>();

        pList.add(new Programme("RSW", "Bachelor Degree in Software Engineering", 4));
        pList.add(new Programme("RDS", "Bachelor Degree in Data Science", 3));
        pList.add(new Programme("DCS", "Diploma in Computer Science", 2));
        pList.add(new Programme("RIS", "Bachelor Degree in Information Security", 9));
        pList.add(new Programme("DIS", "Diloma in Information Technology", 1));
        pList.add(new Programme("DSW", "Diploma in Software Engineering", 5));

        return pList;
    }

    public static void main(String[] args) {
        // To illustrate how to use the initializeProducts() method
        ProgrammeInit p = new ProgrammeInit();
        ProgrammeDao dao = new ProgrammeDao();
        SetInterface pList = p.initializeProducts();

        dao.saveProgrammeToFile(pList);

    }
}
