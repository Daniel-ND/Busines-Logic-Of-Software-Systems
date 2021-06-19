package ru.itmo.blss1.data.dto;

public class DashboardDTO {
    public DashboardDTO(String name, int ownerId) {
        this.name = name;
        this.ownerId = ownerId;
    }
    public String name;
    public int ownerId;
}
