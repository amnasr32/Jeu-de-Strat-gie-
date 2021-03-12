package model;
import model.entity.Entity;
import model.entity.Soldier;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {

    private Grid grid;
    private Player[] players;
    private LinkedList<Entity> playableEntities; // liste de toutes les entités en jeu
    private int[] entTeam; // nombre d'entités pour chaque équipe actuellement en jeu

    private Player currentPlayer=null; // le joueur dont c'est le tour
    private Entity currentEntity=null;

    private int gameState; // état de jeu : 0 : phase de selection des entités, 1 : phase de jeu
    private int entInd; // index de l'entité courante


    public Game(Grid grid, Player player1, Player player2) {
        //TODO : permettre l'initialisation pour un nombre quelconque de joueurs
        playableEntities = new LinkedList<>();
        this.grid=grid;
        players = new Player[2];
        players[0]=player1;
        players[1]=player2;
        entTeam=new int[2];
    }

    protected Grid getGrid() {
        return grid;
    }

    void start() {
        initPlayableEntities();
        firstRound();
    }

    // premier tour de jeu
    // current player et current entity sont correctement initialisée
    private void firstRound() {
        entInd=0;
        currentEntity=playableEntities.get(entInd);
        currentPlayer=currentEntity.getPlayer();
        for (Player p:players) {
            p.focusFirstEntity(entInd, p==currentPlayer);
        }
    }

    // permet de passer au tour de l'entité suivante
    protected void nextRound(Player pp) {
        if (currentPlayer!=pp) return; // seul le joueur courant peut effectuer l'action
        entInd=(entInd+1)%playableEntities.size();
        currentEntity=playableEntities.get(entInd);
        currentPlayer=currentEntity.getPlayer();
        for (Player p:players) {
            p.focusNextEntity(entInd, p==currentPlayer);
        }
    }

    // chaque joueur pose ses unités
    // TODO
    private void initPlayableEntities() {
        int h=grid.getHeight();
        int w=grid.getWidth();
        Entity e1 = new Soldier(1,1,players[0]);
        Entity e2 = new Soldier(h-2,w-2,players[1]);
        addEntityToGame(e1, 1,1,0);
        addEntityToGame(e2, h-2,w-2,1);
    }

    // permet d'ajouter un entité au model et à la view de tous les joueurs
    private void addEntityToGame(Entity e, int x, int y, int playerNb) {
        e.updateCoords(x, y);
        grid.getCell(x,y).setEntity(e);
        entTeam[playerNb]++;
        playableEntities.add(e);
        for (Player p : players) {
            p.addEntityToView(e);
        }
    }

    // renvoie true si le jeu est fini
    // vérifie que seule une équipe ait encore des unités en jeu
    private boolean gameIsOver() {
        boolean b=false;
        for (int i:entTeam) {
            if (b && i>0) return false;
            if (i>0) b=true;
        }
        return true;
    }

    // bouge l'entité e en suivant le chemin donné en paramètre
    // met à jour la vue de tous les joueurs
    protected void move(Player p, byte[] path) {
        if (p!=currentPlayer || path==null) return;
        for (byte dir: path) {
            grid.move(currentEntity, dir);
            for (Player pp:players) {
                pp.moveEntityInView(dir);
            }
        }

    }

    // renvoie le chemin menant de la position de l'entité en cours et les coords x y
    // renvoie null si le chemin n'exsite pas
    protected byte[] makePath(int x, int y) {
        return grid.getPath(currentEntity.getX(), currentEntity.getY(), x, y, currentEntity.getMp());
    }

}
