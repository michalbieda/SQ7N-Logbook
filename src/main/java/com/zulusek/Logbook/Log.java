package com.zulusek.Logbook;

import java.io.*;
import java.util.*;

public class Log {

    static final String CSV_FILENAME = "logbook.csv";
    private String logbookName;
    private List<QSO> log = new LinkedList<>();
    private Scanner scanner = new Scanner(System.in);

    public Log(String logbookName) {
        this.logbookName = logbookName;
        this.log = new LinkedList<QSO>();
    }

    public boolean loadLogbook() {
        System.out.println("Loading logbook db...");

        if (readLogbookFromCSVFile()) {
            for (int i = 0; i < 10; i++) {
                System.out.print(".");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
            System.out.println();
            System.out.println("Logbook loaded succesfully!");
            return true;
        } else {
            System.out.println("----> Failed to load logbook! <----");
            return false;
        }
    }

    public boolean saveLogbook() {
        if (saveLogbookToCSV()) {
            return true;
        }
        System.out.println("Error saving logbook!");
        return false;
    }

    private boolean saveLogbookToCSV() {
        try (PrintWriter save = new PrintWriter(new File(CSV_FILENAME))) {
            StringBuilder sb = new StringBuilder();
            sb.append("callSign,");
            sb.append("qsoDate,");
            sb.append("qsoTime,");
            sb.append("qsoFrequency,");
            sb.append("qsoMode,");
            sb.append("\n");

            for (int i = 0; i < log.size(); i++) {
                sb.append(log.get(i).getCallSign() + ",");
                sb.append(log.get(i).getQsoDate() + ",");
                sb.append(log.get(i).getQsoTime() + ",");
                sb.append(log.get(i).getQsoFrequency() + ",");
                sb.append(log.get(i).getQsoMode() + ",");
                sb.append("\n");
            }

            save.write(sb.toString());
            return true;

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    private boolean readLogbookFromCSVFile() {

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILENAME))) {

            br.readLine(); // removing CSV header by reading first line

            String line;
            // reading logbook entries
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                // todo: to fix hardcoded array fields - if import file changes with number of fields - code will produce error (missing fields or call to missing array field)
                this.log.add(new QSO(values[0], values[1], values[2], Double.parseDouble(values[3]), values[4]));
            }


        } catch (FileNotFoundException e) {
//            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean addLogbookEntry() {
        System.out.print("Enter callsign: ");
        String callSign = scanner.nextLine();
        System.out.print("Enter date: ");
        String date = scanner.nextLine();
        System.out.print("Enter time: ");
        String time = scanner.nextLine();
        System.out.print("Enter frequency: ");
        double frequency = 0.0;
        try {
            frequency = scanner.nextDouble();
            scanner.nextLine();
        } catch (InputMismatchException ioe) {
            System.out.println("You have inserted wrong format. Default " + frequency + " has been inserted!");
            scanner.nextLine();
        }

        System.out.print("Enter mode: ");
        String mode = scanner.nextLine();

        if (addLogbookEntry(callSign, date, time, frequency, mode) == true) {
            System.out.println("Success!!");
            saveLogbook();
            return true;
        } else {
            System.out.println("Something went wrong!!");
            return false;
        }
    }

    private boolean addLogbookEntry(String callSign, String date, String time, Double frequency, String mode) {
        this.log.add(new QSO(callSign, date, time, frequency, mode));
        return true;
    }

    public void findLogbookEntry() {
        System.out.println("Enter callsign to find: ");
        String callsign = scanner.nextLine().toUpperCase(Locale.ROOT);

        if (findLogbookEntries(callsign) != null) {
            for (int i = 0; i < findLogbookEntries(callsign).size(); i++) {
                System.out.println(findLogbookEntries(callsign).get(i));
            }
        } else {
            System.out.println(callsign + " not exist in logbook");
        }

    }

    /**  Returns list of matching QSO's that contains of given callsign.  */
    private List<QSO> findLogbookEntries(String callsign) {
        List<QSO> matchingQSOs = new ArrayList<>();

        for (int i = 0; i < log.size(); i++) {
            QSO check = log.get(i);
            if (callsign.equals(check.getCallSign())) {
                matchingQSOs.add(log.get(i));
            }
        }

        if (matchingQSOs.size() > 0) {
            for (int i = 0; i < matchingQSOs.size(); i++) {
            }
            return matchingQSOs;
        } else {
            return null;
        }
    }

    public int findLogbookEntry(String callsign) {
        for (int i = 0; i < log.size(); i++) {
            QSO existingQSO = log.get(i);
            if (callsign.equals(existingQSO.getCallSign())) {
                return i;
            }
        }
        return -1;
    }

    public void showQsoDetails(String callsign) {
        int searchResult = findLogbookEntry(callsign);
        if (searchResult == -1) {
            System.out.println("Callsign " + callsign + " has not been found in logbook.");
        } else {
            System.out.println("Logbook.QSO Details:\n    " + log.get(searchResult).toString());
        }
    }

    public void printLogbookEntries() {

        System.out.println("Logbook consist of " + log.size() + " entries.");

        if (log.size() < 1) {
            return;
        }
        if (log.size() == 1) {
            System.out.println(log.get(log.size() - 1).toString());
            return;
        }

        System.out.println("How to sort logbook?: [1] - Ascending | [2] - descending");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                for (int i = 0; i < log.size(); i++) {
                    System.out.println(log.get(i).toString());
                }
                break;

            case 2: {
                for (int i = log.size() - 1; i >= 0; i--) {
                    System.out.println(log.get(i).toString());
                }
                break;
            }

        }
    }

    public boolean deleteLogbookEntry() {
        System.out.println("--= DELETING LOGBOOK ENTRY =--");
        System.out.println("Enter callsign to remove: ");
        String callsign = scanner.nextLine().toUpperCase(Locale.ROOT);
        System.out.println("... looking for " + callsign);
        if (findLogbookEntry(callsign) > -1) {
            System.out.println("Found in log: ");
            showQsoDetails(callsign);
            System.out.println("Are you sure to delete? [ Y / N ]");
            String opt = scanner.nextLine().toUpperCase();

            switch (opt) {
                case "Y":
                    while (deleteLogbookEntry(callsign)) {
                        System.out.println("Deleted successfully");
                        return true;
                    }
                    break;
                case "N": {
                    System.out.println("Deleting canceled");
                    return false;
                }
            }

        }


        System.out.println("Deleting false! Callsign you have enetered does not exists in logbook.");
        return false;
    }

    private boolean deleteLogbookEntry(String callsign) {
        if (findLogbookEntry(callsign) != -1) {
            log.remove(log.get(findLogbookEntry(callsign)));
            saveLogbook();
            return true;
        }
        return false;
    }


    public boolean updateLogbookEntry() {
        System.out.println("--= Updating Logbook.QSO =-- \n Enter callsign to update: ");
        String callsign = scanner.nextLine();

        if (findLogbookEntry(callsign) != -1) {
            System.out.println("Enter new callsign: ");
            String newCallsign = scanner.nextLine();
            System.out.println("Enter new date: ");
            String newDate = scanner.nextLine();
            System.out.println("Enter new time: ");
            String newTime = scanner.nextLine();
            System.out.println("Enter new frequency: ");
            double newFrequency = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Enter new mode: ");
            String newMode = scanner.nextLine();

            int position = findLogbookEntry(callsign);
            if (position != -1) {
                log.get(position).setCallSign(newCallsign);
                log.get(position).setQsoDate(newDate);
                log.get(position).setQsoTime(newTime);
                log.get(position).setQsoFrequency(newFrequency);
                log.get(position).setQsoMode(newMode);
            }
            System.out.println(log.get(position).toString());
            saveLogbook();
            return true;
        }
        System.out.println("Callsign not exists in logbook!");
        return false;
    }
}
