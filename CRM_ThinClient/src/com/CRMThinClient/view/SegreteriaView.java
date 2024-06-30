package com.CRMThinClient.view;

import java.io.IOException;
import java.util.Scanner;

import com.CRMThinClient.main.Main;

public class SegreteriaView {
	
	public static int showMenu() throws IOException {
        System.out.println("*********************************");
        System.out.println("*    CRM DASHBOARD    *");
        System.out.println("*********************************\n");
        System.out.println("*** Cosa vuoi fare? ***\n");
        System.out.println("1) Inserisci nuova offerta");
        System.out.println("2) Effettua il report dei clienti");
        System.out.println("3) Registra nuovo cliente");
        System.out.println("4) Elimina un'offerta");
        System.out.println("5) Chiudi");


        Scanner input= Main.getScanner();
		int choice = 0;
		while (true) {
			 System.out.print("Perfavore inserisci la tua scelta: ");
			 choice = Integer.parseInt(input.nextLine());
			 if (choice >= 1 && choice <= 5) {
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