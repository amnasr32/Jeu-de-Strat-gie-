package model;
import model.entity.Entity;
import model.entity.Knight;
import model.entity.Soldier;

import java.io.Serializable;
import java.util.LinkedList;

public class Game implements Serializable {

	private static final long serialVersionUID = 7373047453891668295L;
	private Grid grid;
    private Player[] players;
    private LinkedList<Entity> playableEntities; // liste de toutes les entités en jeu

    private Player currentPlayer=null; // le joueur dont c'est le tour
    private Entity currentEntity=null;

    private int gameState; // état de jeu : 0 : phase de selection des entités, 1 : phase de jeu
    private int entInd; // index de l'entité courante


    public Game(Grid grid, Player ... playerlist) {
        playableEntities = new LinkedList<>();
        this.grid=grid;

        players = new Player[playerlist.length];
        // copie playerList dans players
        System.arraycopy(playerlist, 0, players, 0, playerlist.length);
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
        grid.clearCoordList();
        entInd=(entInd+1)%playableEntities.size();
        currentEntity=playableEntities.get(entInd);
        currentPlayer=currentEntity.getPlayer();
        currentEntity.resetMp();
        for (Player p:players) {
            p.focusNextEntity(entInd, p==currentPlayer);
        }
    }

    // chaque joueur pose ses unités
    // TODO
    private void initPlayableEntities() {
        int h=grid.getHeight();
        int w=grid.getWidth();
        Entity e1 = new Soldier(players[0]);
        Entity e2 = new Soldier(players[1]);
        Entity e3 = new Knight(players[0]);
        Entity e4 = new Knight(players[1]);
        addEntityToGame(e1, 1,1);
        addEntityToGame(e2, h-2,w-2);
        addEntityToGame(e3, 4,3);
        addEntityToGame(e4, h-4,w-5);
    }

    // permet d'ajouter un entité au model et à la view de tous les joueurs
    private void addEntityToGame(Entity e, int x, int y) {
        e.updateCoords(x, y);
        grid.getCell(x,y).setEntity(e);
        playableEntities.add(e);
        for (Player p : players) {
            p.addEntityToView(e);
        }
    }

    
    // renvoie true si le jeu est fini
    // vérifie que seule une équipe ait encore des unités en jeu
    private boolean gameIsOver() {
        if (playableEntities.size()<=1) return true;
        Player p = playableEntities.get(0).getPlayer();
        for (int i = 1; i < playableEntities.size(); i++) {
            if (p != playableEntities.get(i).getPlayer()) return false;
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


    public void doAction(Player player, int action, int x, int y) {
        if (player!=currentPlayer) return;
        Cell c = grid.getCell(x,y);
        if (grid.isInCoordList(x,y) && currentEntity.doAction(action,c)) {
            // pour l'instant on update les points de vie de toutes les entités, ce n'est pas idéal
            for (int i = 0; i < playableEntities.size(); i++) {
                for (Player p : players) {
                    p.updateHpView(i, playableEntities.get(i).getHp());
                }
                removeIfDead(i);
            }

        }
        grid.clearCoordList();
        player.resetAction();
        if (gameIsOver()) {
            System.out.println("fin de la partie");
        }
    }

    public void selectAction(Player player, int actionNb) {
        if (player!=currentPlayer) return;
        int minRange=currentEntity.getAction(actionNb).getMinRange();
        int maxRange=currentEntity.getAction(actionNb).getMaxRange();
        grid.selectCellsWithinRange(currentEntity.getX(), currentEntity.getY(), minRange, maxRange);
        player.updateActionRangeView(grid.getCoordList());
    }

    public void cancelAction(Player player) {
        if (player!=currentPlayer) return;
        grid.clearCoordList();
    }

    // s'occupe de "tuer" l'entité playableEntities[i] si ses pv == 0
    private void removeIfDead(int i) {
        if (playableEntities.get(i).getHp()<=0) {
            removeEntity(i);
        }
    }

    private void removeEntity(int i) {
        grid.getCell(playableEntities.get(i).getX(),playableEntities.get(i).getY()).setEntity(null);
        playableEntities.remove(i);
        if (i<=entInd) entInd--; // on fait attention à ne pas changer l'ordre de jeu des entités
        for (Player p: players) {
            p.removeEntity(i);
        }
    }
}
