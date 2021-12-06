# neo-willis-tower-watson


Le projet web de laa platforme projet. Il s'interface aussi bien à l'interface avec l'application mobile ARTISAN'.
Cette plateforme embarque les actions de:
* [ADMIN]() à savoir:
- L'activation de compte prestataire de service.
- Le management des annonces
- Le management des secteurs d'activités
- La gestion des Clients

* [PRESTATAIRE DE SERVICE - ARTISAN]():
- Les annonces
- Le management de son profil
- Le management de ses formations, experiences et réferences
- Permet aux prestataires de services de créer un compte 


# ARCHITECTURE DE CODE

Pour ce projet, nous utilisons l'architecture hexagonale autrement clean architecture. 
(https://netsharpdev.com/2019/12/04/clean-architecture/)

Le projet est composé de 4 couches principales:
* [La couche Application](): Elle contient toutes fonctionnalités directement à application à savoir:
  * Les configurations
  * les controllers 
  * les Rest Controllers (Les Api)
* [La couche Data](): est la couche d'accès aux données persistence. Elle est composée de : 
  * Repositories : qui herite de JpaRepository
* [La couche Domain](): Elle repose sur les entités et les usescases( workers) de l'application.
* [La couche Infrastructure](): Elle contient sur les librairies et les services externes à l'application.

# LIBRAIRIES UTILISÉES

[Swaagger2]()  : pour la documentation dess API.

[Mail]() (org.springframework.boot:spring-boot-starter-mail) : Configuration et envoi de mail.

[Jackson Module]() (com.fasterxml.jackson.module:jackson-module-kotlin) : Pour la serialisation et deserialisation des données au format JSON.