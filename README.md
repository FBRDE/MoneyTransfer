 1.MoneyTransfer

    	C’est une application web de transfert d'argent.

 2.Fonctionnalités de l’application

	-S'enregistrer à l'aide d'un identifiant e-mail unique;
	-Se connecter à partir des comptes dans la base de données ;
	-Après la connexion, les utilisateurs peuvent ajouter des personnes à leurs listes à partir de leur adresse e-mail (si la personne existe déjà dans la base de données);
	-À partir du solde disponible, les utilisateurs peuvent effectuer des paiements à tout autre utilisateur enregistré sur l'application;
	-À chaque transaction, un prélèvement de 0,5 % est effectué pour monétiser l’application ;
	
 3.Prérequis

	-JDK 11
	-Maven 3.8.6
	-Mysql 8.0.28

 4.Installation

	-Pour installer JDK 11 :
	https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html
	-Pour installer Maven 3.8.6
	https://maven.apache.org/download.cgi
	-Pour installer Mysql
	https://www.mysql.com/fr/downloads/

5.Données

	Les scripts SQL de base de données sont stockés dans le fichier database.sql qui se trouve dans le dossier MoneyTransfer.
	Une fois la base de données créée, il faut aller dans le fichier application.properties qui se trouve dans le dossier
  MoneyTransfer/src/main/resources et modifier les valeurs suivantes:
	
	spring.datasource.url=jdbc:mysql://localhost:3306/moneytransfer?serverTimezone=UTC
	spring.datasource.username=
	spring.datasource.password=

 6.Installation de l’application

	Pour installer l’application il faut se positionner dans le dossier « MoneyTransfer » avec une invite de commande et lancer la commande : ‘mvn install’

 7.Exécution de l’application

	Pour exécuter l’application il faut se positionner dans le dossier « MoneyTransfer » avec une invite de commande et lancer la commande :‘java -jar target/MoneyTransfer-0.0.1-SNAPSHOT.jar’

 8.Accès à l'application

	http://localhost:8080

	Il faut s'enregistrer avec une adresse email et un mot de passe pour accéder à l'application en cliquant sur Sign up!

 9.Diagramme de classe

![Diagramme_de_classe](https://user-images.githubusercontent.com/100838760/205433159-a7252621-4cc5-4620-be69-c560f9a2599d.png)

 10.Modèle physique de données
 ![Modèle_physique](https://user-images.githubusercontent.com/100838760/205433177-53be7f59-ff5b-41b0-bfdc-b53127d92253.png)



