package com.m2i.m2ibank.transactions;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.m2i.m2ibank.core.Compte;
import com.m2i.m2ibank.core.Transaction;
import com.m2i.m2ibank.core.TypeCompte;
import com.m2i.m2ibank.core.TypeTransaction;

public class ReleveDateFormatee {
    private final String clientsPath;
    private final String comptesPath;
    private final String transactionsPath;

    public ReleveDateFormatee(String clientsPath, String comptesPath, String transactionsPath) {
        this.clientsPath = clientsPath;
        this.comptesPath = comptesPath;
        this.transactionsPath = transactionsPath;
    }

    public void afficherRelevesFormates() {
        try {
            // Charger les clients
            Map<String, String> clients = Files.lines(Paths.get(clientsPath))
                .skip(1)
                .map(line -> line.split(","))
                .collect(Collectors.toMap(parts -> parts[0], parts -> parts[1]));

            // Charger les comptes
            List<Compte> comptes = Files.lines(Paths.get(comptesPath))
                .skip(1)
                .map(line -> {
                    String[] parts = line.split(",");
                    return new Compte(
                        parts[0],
                        parts[1],
                        TypeCompte.valueOf(parts[2]),
                        Double.parseDouble(parts[3]),
                        LocalDate.parse(parts[4])
                    );
                })
                .collect(Collectors.toList());

            // Charger les transactions
            List<Transaction> transactions = Files.lines(Paths.get(transactionsPath))
                .skip(1)
                .map(line -> {
                    String[] parts = line.split(",");
                    return new Transaction(
                        parts[0],
                        parts[1],
                        TypeTransaction.valueOf(parts[2]),
                        Double.parseDouble(parts[3]),
                        parts[4]
                    );
                })
                .collect(Collectors.toList());

            // Associer les transactions aux comptes
            Map<String, List<Transaction>> transactionsParCompte = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getAccountId));

            comptes.forEach(compte -> {
                List<Transaction> tx = transactionsParCompte.getOrDefault(compte.getId(), new ArrayList<>());
                tx.sort(Comparator.comparing(Transaction::getDate)); // tri par date
                compte.setTransactions(tx);
            });

            // Grouper les comptes par client
            Map<String, List<Compte>> comptesParClient = comptes.stream()
                .collect(Collectors.groupingBy(Compte::getClientId));

            // Affichage
            clients.forEach((clientId, name) -> {
                System.out.println("Client : " + name);
                List<Compte> comptesClient = comptesParClient.getOrDefault(clientId, new ArrayList<>());
                comptesClient.forEach(compte -> {
                    System.out.printf("Compte [%s] ouvert le %s%n",
                        compte.getId(), compte.getDateOuvertureFormatee());

                    List<Transaction> txs = compte.getTransactions();
                    txs.forEach(tx -> System.out.printf(
                        "  %s - %.2f € le %s%n",
                        tx.getType(), tx.getAmount(), tx.getDateFormatee()
                    ));
                });
                System.out.println();
            });

        } catch (IOException e) {
            System.err.println("Erreur lors de la génération des relevés : " + e.getMessage());
        }
    }
}

