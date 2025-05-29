package com.m2i.m2ibank.transactions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.m2i.m2ibank.core.Compte;
import com.m2i.m2ibank.core.Transaction;
import com.m2i.m2ibank.core.TypeCompte;
import com.m2i.m2ibank.core.TypeTransaction;

public class ReleveTransactionsFiltrees {
    private final String clientsPath;
    private final String comptesPath;
    private final String transactionsPath;

    public ReleveTransactionsFiltrees(String clientsPath, String comptesPath, String transactionsPath) {
        this.clientsPath = clientsPath;
        this.comptesPath = comptesPath;
        this.transactionsPath = transactionsPath;
    }

    public void afficherRelevesAvecFiltrage() {
        try {
            Map<String, String> clients = Files.lines(Paths.get(clientsPath))
                .skip(1)
                .map(line -> line.split(","))
                .collect(Collectors.toMap(parts -> parts[0], parts -> parts[1]));

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

            Map<String, List<Transaction>> transactionsParCompte = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getAccountId));

            comptes.forEach(compte -> {
                List<Transaction> tx = transactionsParCompte.getOrDefault(compte.getId(), new ArrayList<>());
                tx.sort(Comparator.comparing(Transaction::getDate));
                compte.setTransactions(tx);
            });

            Map<String, List<Compte>> comptesParClient = comptes.stream()
                .collect(Collectors.groupingBy(Compte::getClientId));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH);
            LocalDateTime unMoisAvant = LocalDateTime.now().minusMonths(1);

            clients.forEach((clientId, name) -> {
                System.out.println("Client : " + name);
                List<Compte> comptesClient = comptesParClient.getOrDefault(clientId, new ArrayList<>());
                comptesClient.forEach(compte -> {
                    System.out.printf("Compte [%s] ouvert le %s%n",
                        compte.getId(), compte.getDateOuverture().format(formatter));

                    List<Transaction> txs = compte.getTransactions().stream()
                        .filter(tx -> tx.getDate().isAfter(unMoisAvant)) // Filtrage dernier mois
                        .collect(Collectors.toList());

                    txs.forEach(tx -> System.out.printf(
                        "  %s - %.2f € le %s%n",
                        tx.getType(), tx.getAmount(), tx.getDate().format(formatter)
                    ));
                });
                System.out.println();
            });

        } catch (IOException e) {
            System.err.println("Erreur lors de la génération des relevés filtrés : " + e.getMessage());
        }
    }
}

