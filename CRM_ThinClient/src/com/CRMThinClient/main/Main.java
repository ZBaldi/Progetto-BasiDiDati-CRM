package com.CRMThinClient.main;

import java.util.Scanner;

import com.CRMThinClient.controller.ApplicationController;

public class Main {
	
	private static final Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		ApplicationController applicationController = new ApplicationController();
        applicationController.start();
	}
	
	public static Scanner getScanner() {
		return scanner;
	}
	
}