Atelier 3 : Modularisation du système bancaire

Objectifs pédagogiques

À l’issue de cet atelier, vous serez capables de :
•	Créer une architecture multi-modules Java.
•	Déclarer les dépendances entre modules avec requires, exports, opens.
•	Encapsuler les packages pour limiter la visibilité.
•	Tester des modules avec JShell.

Contexte métier

M2iBank poursuit la modernisation de son système d’information. Après avoir intégré les expressions lambda, les streams et l’API DateTime dans la gestion des comptes et des relevés, l’équipe technique souhaite désormais structurer l’application de manière modulaire. L’objectif est de séparer les responsabilités fonctionnelles (gestion des clients, des comptes, des transactions) dans des modules Java distincts, afin de :
•	Améliorer la maintenabilité du code,
•	Renforcer l'encapsulation des composants internes,
•	Faciliter les tests unitaires et interactifs (via JShell),
•	Et préparer l’intégration de nouveaux services (ex. : alertes, sécurité, reporting).
Vous êtes chargé de refactoriser l’application existante en créant une architecture modulaire basée sur Java 9+, tout en conservant les fonctionnalités développées dans les ateliers précédents.

Modules à créer

A.	com.m2i.m2ibank.core
Contient les entités métier communes :
•	Client
•	Compte
•	Transaction
•	Enums : TypeCompte, TypeTransaction

B.	com.m2i.m2ibank.clients
Contient la logique de chargement et affichage des clients :
•	ClientLoader
•	AnalyseComptes
•	TriComptes

C.	com.m2i.m2ibank.transactions
Contient la logique de gestion des transactions et relevés :
•	ReleveComptes
•	ReleveDateFormatee
•	ReleveTransactionsFiltrees

D.	Structure des fichiers module-info.java
•	com.m2i.m2ibank.core/module-info.java
•	com.m2i.m2ibank.clients/module-info.java
•	com.m2i.m2ibank.transactions/module-info.java

Tâches à réaliser

A.	Tâche 1 : Création des modules
•	Créer les dossiers de modules.
•	Déplacer les classes dans les bons modules.
•	Ajouter les module-info.java.

B.	Tâche 2 : Encapsulation
•	Exporter uniquement les packages nécessaires.
•	Utiliser opens si des bibliothèques ou outils utilisent la réflexion.

C.	Tâche 3 : Exécution
•	Créer une classe Main dans un module com.m2ibank.app (optionnel) pour tester l’ensemble.

D.	Tâche 4 : Test avec JShell
•	Lancer JShell avec --module-path pour tester des classes comme Client, Compte, etc.

Structure recommandée

JavaNewLabTp3/
├── com.m2i.m2ibank.core/
│   ├── module-info.java
│   └── com/m2i/m2ibank/core/...
├── com.m2i.m2ibank.clients/
│   ├── module-info.java
│   └── com/m2i/m2ibank/clients/...
├── com.m2i.m2ibank.transactions/
│   ├── module-info.java
│   └── com/m2i/m2ibank/transactions/...
├── com.m2i.m2ibank.app/
│   ├── module-info.java
│   └── com/m2i/m2ibank/app/Main.java
└── lib/ (bibliothèques externes si besoin)


