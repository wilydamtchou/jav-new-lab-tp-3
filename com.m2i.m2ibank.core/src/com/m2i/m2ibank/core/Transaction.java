package com.m2i.m2ibank.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Transaction {
    private String id;
    private String accountId;
    private TypeTransaction type;
    private double amount;
    private LocalDateTime date;
    
    public Transaction(String id, String accountId, TypeTransaction type, double amount, String dateStr) {
        this.id = id;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    // Getters
    public String getId() { return id; }
    public String getAccountId() { return accountId; }
    public TypeTransaction getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDateTime getDate() { return date; }

    // Tâche 3 : Formatage des dates avec DateTimeFormatter
    public String getDateFormatee() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH);
        return date.format(formatter);
    }
}
