package com.scc.model;

public record AppointmentEntity(int id, String patientName, String doctor, double consultFee, String status) {
}