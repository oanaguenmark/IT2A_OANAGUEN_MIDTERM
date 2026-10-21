package com.scc.ui;

import com.scc.dao.AppointmentDao;
import com.scc.model.AppointmentEntity;

import java.util.List;
import java.util.Scanner;

public class AppointmentMenu {

    private final AppointmentDao dao;
    private final Scanner scanner = new Scanner(System.in);

    public AppointmentMenu(AppointmentDao dao) {
        this.dao = dao;
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Enter choice: ");
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> addAppointment();
                case "2" -> viewAppointments();
                case "3" -> {
                    System.out.println("\nExiting system... Thank you!");
                    running = false;
                }
                default -> System.out.println("\nInvalid choice. Please enter 1, 2, or 3.\n");
            }
        }
        scanner.close();
    }

    private void printMenu() {
        String line = "=".repeat(52);
        System.out.println(line);
        System.out.println("        CLINIC PATIENT APPOINTMENT REGISTRY");
        System.out.println(line);
        System.out.println("[1] Add New Appointment");
        System.out.println("[2] View Scheduled Appointments");
        System.out.println("[3] Exit System");
        System.out.println(line);
    }

    private void addAppointment() {
        System.out.println("\n--- BOOK APPOINTMENT ---");
        String name = readNonEmpty("Enter Patient Name: ");
        String doctor = readNonEmpty("Enter Doctor Assigned: ");
        double fee = readFee("Enter Consultation Fee: ");
        String status = readStatus("Enter Status (SCHEDULED / COMPLETED): ");

        if (dao.addAppointment(name, doctor, fee, status)) {
            System.out.println("\nSUCCESS: Appointment successfully booked!\n");
        } else {
            System.out.println("\nERROR: Could not save the appointment.\n");
        }
    }

    private void viewAppointments() {
        List<AppointmentEntity> list = dao.getAllAppointments();
        String banner = "=".repeat(80);
        String border = "+----+--------------------+--------------------+--------------+----------------+";

        System.out.println();
        System.out.println(banner);
        System.out.println("                          CLINIC APPOINTMENT SCHEDULE");
        System.out.println(banner);

        if (list.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            System.out.println(border);
            System.out.printf("| %-2s | %-18s | %-18s | %-12s | %-14s |%n",
                    "ID", "PATIENT NAME", "DOCTOR", "CONSULT FEE", "STATUS");
            System.out.println(border);
            for (AppointmentEntity a : list) {
                System.out.printf("| %-2d | %-18s | %-18s | %-12s | %-14s |%n",
                        a.id(), a.patientName(), a.doctor(),
                        String.format("%.2f", a.consultFee()), a.status());
            }
            System.out.println(border);
            System.out.println("Total Records Displayed: " + list.size());
        }
        System.out.println();
    }

    private String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be empty. Try again.");
        }
    }

    private double readFee(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double fee = Double.parseDouble(scanner.nextLine().trim());
                if (fee >= 0) {
                    return fee;
                }
                System.out.println("Fee cannot be negative. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Enter a number such as 800.00.");
            }
        }
    }

    private String readStatus(String prompt) {
        while (true) {
            System.out.print(prompt);
            String status = scanner.nextLine().trim().toUpperCase();
            if (status.equals("SCHEDULED") || status.equals("COMPLETED")) {
                return status;
            }
            System.out.println("Status must be SCHEDULED or COMPLETED.");
        }
    }
}