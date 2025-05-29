package com.m2i.m2ibank.clients;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.m2i.m2ibank.core.Compte;
import com.m2i.m2ibank.core.TypeCompte;

public class CompteLoaderDate {
    private final String filePath;

    public CompteLoaderDate(String filePath) {
        this.filePath = filePath;
    }

    public void afficherComptesAvecDateOuverture() {
        try {
            List<Compte> comptes = Files.lines(Paths.get(filePath))
                .skip(1)
                .map(line -> {
                    String[] parts = line.split(",");
                    String id = parts[0];
                    String clientId = parts[1];
                    TypeCompte type = TypeCompte.valueOf(parts[2]);
                    double balance = Double.parseDouble(parts[3]);
                    LocalDate dateOuverture = LocalDate.parse(parts[4]);
                    return new Compte(id, clientId, type, balance, dateOuverture);
                })
                .collect(Collectors.toList());

            comptes.forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des comptes : " + e.getMessage());
        }
    }
}

