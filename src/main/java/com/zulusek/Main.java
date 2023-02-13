package com.zulusek;

import com.zulusek.Logbook.Log;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Log log = new Log("SQ7N");

        System.out.println("SQ7N Logbook");

        if (log.loadLogbook()) {
            programOptions();

            boolean quit = false;
            while (!quit) {
                Scanner scanner = new Scanner(System.in);
                int programOption = getProgramOption();

                switch (programOption) {
                    case 1:
                        log.printLogbookEntries();
                        break;
                    case 2:
                        log.addLogbookEntry();
                        break;
                    case 3:
                        log.findLogbookEntry();
                        break;
                    case 4:
                        log.deleteLogbookEntry();
                        break;
                    case 5:
                        log.updateLogbookEntry();
                        break;
                    case 9:
                        programOptions();
                        break;
                    case 0:
                        log.saveLogbook();
                        quit = true;
                        break;
                }
            }

        }


    }


    public static void programOptions() {
        System.out.println("Available options:");
        System.out.println("" +
                "1 - list Logbook QSO's from logbook\n" +
                "2 - add Logbook QSO\n" +
                "3 - find Logbook QSO\n" +
                "4 - delete Logbook QSO\n" +
                "5 - update Logbook QSO\n" +
                "9 - to print program options\n" +
                "0 - quit application\n");
    }

    private static int getProgramOption() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("::: Choose option number [9 - for available options]: ");
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Please enter a number using only digits 0 - 9");
            }
        }

    }
}
