package com.scc.dao;

import com.scc.model.AppointmentEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDao {

    public boolean addAppointment(String patientName, String doctor, double consultFee, String status) {
        String sql = "INSERT INTO appointments (patient_name, doctor, consult_fee, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patientName);
            ps.setString(2, doctor);
            ps.setDouble(3, consultFee);
            ps.setString(4, status);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Insert failed: " + e.getMessage());
            return false;
        }
    }

    public List<AppointmentEntity> getAllAppointments() {
        List<AppointmentEntity> list = new ArrayList<>();
        String sql = "SELECT id, patient_name, doctor, consult_fee, status FROM appointments ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new AppointmentEntity(
                        rs.getInt("id"),
                        rs.getString("patient_name"),
                        rs.getString("doctor"),
                        rs.getDouble("consult_fee"),
                        rs.getString("status")));
            }
        } catch (SQLException e) {
            System.out.println("Fetch failed: " + e.getMessage());
        }
        return list;
    }
}