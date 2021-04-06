package model;
import model.entity.Entity;
import model.entity.Knight;
import model.entity.Soldier;

import java.io.Serializable;
import java.util.LinkedList;



public class Game implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7373047453891668295L;
	private Grid grid;
    private Player[] players;
    private LinkedList<Entity> playableEntities; // liste de toutes les entités en jeu
    private int[] entTeam; // nombre d'entités pour chaque équipe actuellement en jeu
	int nb=0;

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
 // un bouton dit si le joueur a fini de posé ses entité    une fois que les joueurs ont cliqué
    void start() {
       firstRound();      
       gameState=1;
    	
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

 // vérifie qu'un joueur ait le droit de jouer, càd que c'est son tour
    // et que gamestate == 1
    private boolean canPlay(Player p) {
        return (currentPlayer==p && gameState==1);
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
    //savoir quelle entity lz joueur essaye d'ajouter
 // permet d'ajouter un entité au model et à la view de tous les joueurs
    private void addEntityToGame(Entity e, int x, int y) {
        if (grid.getCell(x,y).getEntity()!=null) return; //yeet
        e.updateCoords(x, y);
        grid.getCell(x,y).setEntity(e);
        playableEntities.add(e);
        for (Player p : players) {
            p.addEntityToView(e);
        }
    }

    //à faire : changer le nom de la fonction
    //on suppose qu'on a que des soldier
    //chaque joueur pose ses entités
    public void TryToaddEntitytoGame(int x, int y, int playerNb,int entity_type) {
    	
    	if(nb>=4) return;
    	int h=grid.getHeight();
        int w=grid.getWidth();
        if (entity_type==0) { //entity_type c'est pour indiquer quel type d'entier à ajouter (par exemple 0 pour soldier)
        	Entity e = new Soldier(players[0]);
        	addEntityToGame(e, x,y); 
        	nb++;

        }
        else {
        	Entity e1 = new Knight(players[0]);
        	addEntityToGame(e1, x,y); 
        	nb++;
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
    protected void move(Player player, byte[] path) {
        if ((!canPlay(player)) || path==null) return;
        for (byte dir: path) {
            grid.move(currentEntity, dir);
            for (Player p:players) {
                p.moveEntityInView(dir);
            }
        }

    }
   
    // renvoie le chemin menant de la position de l'entité en cours et les coords x y
    // renvoie null si le chemin n'exsite pas
    protected byte[] makePath(int x, int y) {
        return grid.getPath(currentEntity.getX(), currentEntity.getY(), x, y, currentEntity.getMp());
    }


    public void doAction(Player player, int action, int x, int y) {
        if (!canPlay(player)) return;
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
            gameState=2;
            for (Player p : players) {
                boolean hasWon = playableEntities.get(0).getPlayer()==p;
                p.endGame(hasWon);
            }
        }
    }

    public void selectAction(Player player, int actionNb) {
        if (!canPlay(player)) return;
        int minRange=currentEntity.getAction(actionNb).getMinRange();
        int maxRange=currentEntity.getAction(actionNb).getMaxRange();
        grid.selectCellsWithinRange(currentEntity.getX(), currentEntity.getY(), minRange, maxRange);
        player.updateActionRangeView(grid.getCoordList());
    }

    public void cancelAction(Player player) {
        if (!canPlay(player)) return;
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
