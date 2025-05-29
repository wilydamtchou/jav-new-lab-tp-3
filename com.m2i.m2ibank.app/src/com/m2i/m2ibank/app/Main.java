 package com.m2i.m2ibank.app;

import com.m2i.m2ibank.clients.AncienneteComptes;
import com.m2i.m2ibank.clients.CompteLoaderDate;
import com.m2i.m2ibank.transactions.ReleveDateFormatee;
import com.m2i.m2ibank.transactions.ReleveTransactionsFiltrees;

public class Main {
	public static String CLIENTS_FILE = "clients.csv";
	public static String ACCOUNTS_FILE = "accounts.csv";
	public static String TRANSACTIONS_FILE = "transactions.csv";
	
	public static void main(String[] args) {

		// Tâche 1 : Création de dates d’ouverture de comptes avec LocalDate
		CompteLoaderDate loader = new CompteLoaderDate(ACCOUNTS_FILE);
		loader.afficherComptesAvecDateOuverture();
		
		// Tâche 2 : Calcul de l’ancienneté d’un compte avec Period
		AncienneteComptes anciennete = new AncienneteComptes(ACCOUNTS_FILE);
		anciennete.afficherAncienneteDesComptes();
		
		// Tâche 3 : Formatage des dates avec DateTimeFormatter
		ReleveDateFormatee releve = new ReleveDateFormatee(CLIENTS_FILE, ACCOUNTS_FILE, TRANSACTIONS_FILE);
		releve.afficherRelevesFormates();
		
		// Tâche 4 : Génération d’un relevé de transactions datées
		ReleveTransactionsFiltrees releveFiltre = new ReleveTransactionsFiltrees(CLIENTS_FILE, ACCOUNTS_FILE, TRANSACTIONS_FILE);
		releveFiltre.afficherRelevesAvecFiltrage();
	}
}