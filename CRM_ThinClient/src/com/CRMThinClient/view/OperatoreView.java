package com.CRMThinClient.view;

import java.io.IOException;
import java.util.Scanner;

import com.CRMThinClient.main.Main;

public class OperatoreView {

	public static int showMenu() throws IOException {
        System.out.println("*********************************");
        System.out.println("*    CRM DASHBOARD    *");
        System.out.println("*********************************\n");
        System.out.println("*** Cosa vuoi fare ***\n");
        System.out.println("1) Mostra le note associate ad un cliente");
        System.out.println("2) Mostra clienti");
        System.out.println("3) Mostra offerte valide");
        System.out.println("4) Scrivi una nota");
        System.out.println("5) Salva offerta accettata");
        System.out.println("6) Esci");


        Scanner input= Main.getScanner();
		int choice = 0;
		while (true) {
			 System.out.print("Perfavore inserisci la tua scelta: ");
			 choice = Integer.parseInt(input.nextLine());
			 if (choice >= 1 && choice <= 6) {
			     break;
			 }
			 System.out.println("Opzione invalida");
		}
		return choice;
    }
	
	public static void riepilogo(String s) {
		System.out.println("********************************");
		System.out.println("*     RIEPILOGO     *");
		System.out.println("********************************\n");
		System.out.println(s);
		System.out.println("");
	}

}