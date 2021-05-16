# Jeu de Stratégie 

## Présentation de l'équipe:
Ce jeu a été réalisé dans le cadre d'un projet universitaire encadré par l'université de Paris entre Janvier et Mai 2021 par:
*  GANGNEUX Paul
*  BAH Awa
*  PUSKAS Matyas
*  NASR Amira
*  ALLANO Yoann

## Utilisation : 

### Préparation

1.  Clonez le projet
```
git clone https://gaufre.informatique.univ-paris-diderot.fr/gangneux/jeu-de-strategie
```

2.  Entrez dans le répertoire du jeu
```
cd jeu-de-strategie
```

### Lancement
```
./gradlew run
```

## Comment jouer ?
Choisissez d'abord le mode du jeu ("multijoueur"/"jouer contre un robot"), puis un des terrains disponibles. Au début du jeu, vous avez le choix entre l'achat de 5 types d'entités, chacune avec des compétences différentes:
1.  CLERC (2€): 
* orion sacré : inflige 2 points de dégats magiques
* premiers soins : soigne 2 points de vie d'un allié
* purge : enlève tous les problèmes de statut d'un allié (poison, paralysie)

2.  DRUIDE (3€): 
* attaque de bâton : inflige 2 points de dégats physiques
* empoisonnement : empoisonne l'ennemi ciblé pendant 3 tours infligeant 1 point de dégat à chaque fois
* enracinement : paralyse l'ennemi à son prochain tour l'empêchant de bouger

3.  SORCIER (4€): 
* boule de feu : inflige 8 points de dégats magiques
* protection : rajoute 2 points d'armure à un allié

4.  CHEVALIER (3€): 
* coup décisif : inflige 8 points de dégats physiques
* auto soin : permet de s'auto-soigner de 2 points de vie

5.  SOLDAT (2€): 
* frappe courte : inflige 4 points de dégats physiques au corps à corps
* frappe longue : inflige 6 points de dégats physiques à distance

Sélectionnez l'entité de votre choix puis placez-la n'importe-où sur la grille en cliquant sur la cellule correspondante. Une fois que vous avez placé vos entités, cliquez sur le bouton "commencer". À chaque tour, chaque entité peut effectuer une attaque et avancer d'un certain nombre de cases, en fonction de son type. Vous passez à la prochaine entité avec le bouton "fin de tour". Une fois que vous avez joué avec toutes vos entités, votre tour est fini est c'est à l'adversaire de jouer. La partie se termine lorsque l'un des joueurs n'a plus d'entité. 

## Contrôles de la caméra
Pour se déplacer la caméra dans l'espace 3D :
* clique droit permet d'effectuer des translations
* clique molette permet d'effectuer des rotations
* molette permet de zoomer / dézoomer

### Prêt pour un combat acharné ?

## Autres fonctionalités
La partie fonctionnelle du projet constitue le multijoueur local. Les deux joueurs étant contrôlés depuis la même fenêtre de jeu. En effet, le bot ne peut pas faire d'action autre que passer son tour. On l'a laissé dans le projet car il est pratique pour faire des tests et comprendre les mécaniques de jeu. 

On peut aussi choisir entre 4 cartes differentes, chargées depuis des fichiers sérialisés.

La hauteur du terrain est prise en compte dans le calcul des chemins à parcourir par les unités, et forment des obstacles à leur champs de vision.

## Non inclus mais considéré
- La possibilité d'editer des niveaux, le bouton est la mais nous n'avons pas eu le temps de l'implémenter.

- Les differents type de terrains au sol (càd : eau, terre, herbe, pierre) qui auraient été choisi par le joueur en début de partie et auraient influencé les attaques des entités. Les terrains peuvent être modifiés en changeant directement le code mais pas autrement.

- Jouer le jeu en reseau, nous n'avons pas encore effectué de reseau en cours donc nous serions entré en territoire completement inconnu en plus de l'apprentissage de Javafx.

## Conclusion
Durant la réalisation de ce projet, nous avons utilisé javafx pour créer des interfaces graphiques semblables à celles vues au troisième semestre mais aussi pour réaliser le plateau du jeu en 3D, ce qui n'aurait pas été possible avec swing.
Nous avons pu employer tout ce que nous avons appris en java pendant les deux dernières années, le cours de Conduite de projet ayant été d'une importance particulière. En effet, il nous a permis de comprendre les bases du travail en groupe avec les systèmes de contrôle de versions (git) et l'utilité des outils de constructions (gradle).

Dans l'ensemble, il y avait une bonne coordination dans les rôles de chacun et une bonne entente au sein de l'équipe était présente.

## Déjà fan de jeu de Stratégie?
Vous avez des remarques? Des idées à nous transmettre ? Merci de nous écrire sur:
```
nasramira8@gmail.com>
```
## Enfin, un grand MERCI à tous ceux qui ont joué à notre version jeu de stratégie! Amusez-vous bien!
