package com.CRMThinClient.view;

import java.io.IOException;
import java.util.Scanner;

import com.CRMThinClient.main.Main;

public class OperatoreView {

	public static int showMenu() throws IOException {
        System.out.println("*********************************");
        System.out.println("*    CRM DASHBOARD    *");
        System.out.println("*********************************\n");
        System.out.println("*** What should I do for you? ***\n");
        System.out.println("1) Show notes associated with a customer");
        System.out.println("2) Write a Note associated with a customer");
        System.out.println("3) Insert accepted offer");
        System.out.println("4) Quit");


        Scanner input= Main.getScanner();
		int choice = 0;
		while (true) {
			 System.out.print("Please enter your choice: ");
			 choice = Integer.parseInt(input.nextLine());
			 if (choice >= 1 && choice <= 4) {
			     break;
			 }
			 System.out.println("Invalid option");
		}
		return choice;
    }
	
	public static void riepilogoNota(String s) {
		System.out.println("********************************");
		System.out.println("*     RIEPILOGO     *");
		System.out.println("********************************\n");
		System.out.println(s);
	}

	public static void riepilogoOffertaAccettata(String s) {
		System.out.println("***************************************************");
		System.out.println("*    RIEPILOGO INSERIMENTO OFFERTA ACCETTATA    *");
		System.out.println("***************************************************\n");
		System.out.println(s);
	}
}