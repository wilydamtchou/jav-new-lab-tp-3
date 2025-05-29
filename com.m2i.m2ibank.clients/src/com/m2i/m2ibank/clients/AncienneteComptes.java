package com.m2i.m2ibank.clients;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import com.m2i.m2ibank.core.Compte;
import com.m2i.m2ibank.core.TypeCompte;

public class AncienneteComptes {
    private final String comptesPath;

    public AncienneteComptes(String comptesPath) {
        this.comptesPath = comptesPath;
    }

    public void afficherAncienneteDesComptes() {
        try {
            List<Compte> comptes = Files.lines(Paths.get(comptesPath))
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

            comptes.forEach(compte -> {
                Period anciennete = compte.getAnciennete();
                System.out.printf(
                    "Compte [%s] ouvert le %s — Ancienneté : %d ans, %d mois, %d jours%n",
                    compte.getId(),
                    compte.getDateOuverture(),
                    anciennete.getYears(),
                    anciennete.getMonths(),
                    anciennete.getDays()
                );
            });

        } catch (IOException e) {
            System.err.println("Erreur lors du calcul de l'ancienneté : " + e.getMessage());
        }
    }
}

