package com.m2i.m2ibank.core;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class Compte {
    private String id;
    private String clientId;
    private TypeCompte type;
    private double balance;
    private LocalDate dateOuverture;
    private List<Transaction> transactions;

    public Compte(String id, String clientId, TypeCompte type, double balance, LocalDate dateOuverture) {
        this.id = id;
        this.clientId = clientId;
        this.type = type;
        this.balance = balance;
        this.dateOuverture = dateOuverture;
    }

    // Getters
    public String getId() { return id; }
    public String getClientId() { return clientId; }
    public TypeCompte getType() { return type; }
    public double getBalance() { return balance; }
    public LocalDate getDateOuverture() { return dateOuverture; }

    public List<Transaction> getTransactions() { return transactions; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }

    @Override
    public String toString() {
        return String.format("Compte{id='%s', type='%s', balance=%.2f, dateOuverture=%s}",
                id, type, balance, dateOuverture);
    }
    
    // Tâche 2 : Calcul de l’ancienneté d’un compte avec Period
    public Period getAnciennete() {
    	return Period.between(dateOuverture, LocalDate.now());
    }
    
    // Tâche 3 : Formatage des dates avec DateTimeFormatter
	public String getDateOuvertureFormatee() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH);
		return dateOuverture.format(formatter);
	}
}