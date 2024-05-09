# # MediLabo-Solution

Outil déstiné aux médecin, pour aide à gèrer les patients et detecter le risque diabète de type 2.  
Application basée sur une architecture microservices.  
Pour exécuter l'application avec docker, suivez les étapes ci-dessous.

## Prérequis

- Avant de commencer, assurez-vous d'avoir installé Docker et Docker Compose sur votre machine. Si ce n'est pas le cas, je vous recommande d'installer Docker desktop
- Il faut aussi une base de données Mysql et mongoDB

## Clonage du Projet

Utilisez la commande suivante pour cloner le projet sur votre machine :  
git clone https://github.com/ZinebELHALLAOUI/MediLaboSolutionsProjet9.git

## Configuration des Ports
Assurez-vous que les ports nécessaires sur votre machine sont disponibles. Les services Docker utilisent les ports suivants :

9000: webAPP -> interface utilisateur

8080: gateway -> redirige les url vers les différents microservices

8081: patient-service -> gère les opération du CRUD sur les patients

8082: doctor-note-service -> gère les opération du CRUD sur les notes

8083: risk-diabetes-service -> permet l'évaluation du niveau de risque des patients à partir du contenu des notes écrites par le médecin

3306: mysql-db -> persiste les données utiles à patient-service

27017: mongodb -> persiste les données utiles à doctor-note-service

## Démarrage de l'Application

Utilisez la commande suivante sur la racine du projet pour démarrer l'application en mode sur des containers Docker:  
`docker-compose up -d`

## Connexion à l'application
Après le démarrage des dockers, vous pouvez accéder à l'application via l'interface utilisateur à l'adresse http://localhost:8080.  
Utilisez les informations de connexion par défaut pour vous connecter :

Nom d'utilisateur: `zineb`

Mot de passe: `123456`

## Recommandations d'amélioration Green du projet
Dans un souci de réduction de l'impact écologique de ce projet, voici quelques pistes d'améliorations Green Code:

1) Évaluer l'utilité des dépendances, qui peuvent parfois générer du code inutilisé (ex : Lombok). Supprimer ou réduire l'utilisation de ces dépendances peu alléger l'empreinte du projet.
2) Optimiser les algos, par exemple sur le front nous pouvons récuperer les risks en une seule requête 
3) Opter pour le cache API: cacher les requêtes les plus coûteuses par exemple getAllPatients ou getNotes, par contre il faut penser à rafraîchir le cache après chaque insertion