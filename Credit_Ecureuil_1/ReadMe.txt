**************************************************************************************************************
******																	 								******
******									Projet de AAW - Banque en ligne 								******
******																	 								******
**************************************************************************************************************

Dans le cadre du Master 2 Informatique de l'université de Poitiers.
Par Romain Charpentier et Etienne Wehowski.


--------------------------------------------------------------------------------------------------------------
-----											Installation											------
--------------------------------------------------------------------------------------------------------------

- Importer le projet sous Netbeans 8.2 avec Tomcat 9.0.12.

- Ajouter les jars aopalliance-1.0.jar, jandex-2.0.0.Final.jar et javax.jar (présents à la racine du projet)
	dans les librairies du projet (sous Netbeans : clique droit sur le projet -> Properties -> Libraries)

- Créer une base de données "JavaDB" (derby), le nom de la base : "CreditEcureuilDB", l'identifiant :
	"romain" et le mot de passe : "romain".

- Se connecter à la base de données (clique droit sur le nom et "Se connecter").

- Le projet peut être lancé à partir de Netbeans.


--------------------------------------------------------------------------------------------------------------
-----										Types d'utilisateurs										------
--------------------------------------------------------------------------------------------------------------
 
Il y a 3 types d'utilisateurs : classique, pro et admin. Les pro et admin sont des entity dérivant de
utilisateur classique (dit simplement utilisateur).

- Un utilisateur peut modifier son profil, effectuer des virements à partir de ses comptes, se créer de nouveaux
livrets ou de nouveaux comptes joints, consulter ses transactions, envoyer des messages, consulter sa 
messagerie.

- L'utilisateur pro peut s'inscrire avec un formulaire à part où un siret est demandé en plus de l'email et du
mot de passe. Ainsi, il est lié à une entreprise et peut accéder à "Ordre en Bourse" (dans le menu). Cette
fonctionnalité n'est pas encore implémentée. Il peut ajouter un nom à son entreprise (à partir du profil).

- Pour l'admin, voir le paragraphe ci-dessous.


--------------------------------------------------------------------------------------------------------------
-----											Mode admin												------
--------------------------------------------------------------------------------------------------------------

La création d'admin ("Conseiller") n'est pas accessible à partir du site internet. Il faut passer par des 
requêtes SQL qui peuvent venir d'une autre API pas encore développée. Cela évite des failles de sécurité 
(si les utilisateurs peuvent accéder au formulaire de création d'admin).

Il faut donc créer un UtilisateurEntity dans la table "Utilisateur" de type AdminEntity avec une ligne 
correspondante dans la table "Admin".

Il est également possible d'ajouter à un admin principal un compte bancaire qui correspondrait aux fonds de
la banque.

L'admin peut voir et supprimer les comptes de tous les utilisateurs (avec solde vide ou non). Il peut
également faire des virements depuis n'importe quel compte. Son nom est également différencié par l'ajout
de "- ADMIN -", ce qui permet aux utilisateurs de contacter un administrateur facilement.


--------------------------------------------------------------------------------------------------------------
-----										Types de comptes											------
--------------------------------------------------------------------------------------------------------------

Il existe 3 types de comptes : Compte, Livret et CompteJoint. Les livrets et comptes joints dérivents de
compte.

- Un compte ne peut pas être supprimé. Il est normalement unique pour chaque utilisateur. Il s'agit du compte
courant. Par défaut, 100 euros sont ajoutés à la création d'un compte.

- Le livret peut être crée par l'utilisateur mais il a un nom unique pour chaque utilisateur. Il est aussi
possible de le supprimer si le solde du livret est vide.

- Le compte joint est semblable à un livret sauf qu'il est partagé entre 2 à n personnes. La personne qui le
crée devient son proprietaire et est la seule personne à pouvoir le supprimer.

