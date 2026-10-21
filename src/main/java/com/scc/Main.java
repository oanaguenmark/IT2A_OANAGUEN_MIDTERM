package com.scc;

import com.scc.dao.AppointmentDao;
import com.scc.dao.DatabaseConnection;
import com.scc.ui.AppointmentMenu;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.initializeDatabase();
        new AppointmentMenu(new AppointmentDao()).start();
    }
}