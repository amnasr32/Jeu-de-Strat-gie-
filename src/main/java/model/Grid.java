package model;
import model.entity.Entity;

public class Grid {
    private final Cell[][] cells;

    private final int height;
    private final int width;

    public Grid(int h, int w) {
        height=h;
        width=w;
        cells=new Cell[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                cells[i][j]=new Cell(); 
            }
        }
    }

    public Cell getCell(int h, int w) {
        return cells[h][w];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    /* renvoie la cellule adjascente aux coordonnées entrées, en fonction de l'entier direction, ou null si celà ferait sortir de la grille
       direction prends une valeur entre 1 et 6. représentation vusielle de la cellule renvoyée :
         5   0
        4  o  1
         3   2
    */
    public Cell getAdjCell(int h, int w, int direction) {
        boolean odd = h%2==0;
        switch (direction) {
            case 0:
                if (h<=0 || !odd && w>=width-1) return null;
                if (odd) return cells[h-1][w];
                else return cells[h-1][w+1];
            case 1:
                if (w>=width-1) return null;
                return cells[h][w+1];
            case 2:
                if (h>=height-1 || !odd && w>=width-1) return null;
                if (odd) return cells[h+1][w];
                else return cells[h+1][w+1];
            case 3:
                if (h>=height-1 || odd && w<=0) return null;
                if (odd) return cells[h+1][w-1];
                else return cells[h+1][w];
            case 4:
                if (w<=0) return null;
                return cells[h][w-1];
            case 5:
                if (h<=0 || odd && w<=0) return null;
                if (odd) return cells[h-1][w-1];
                else return cells[h-1][w];

            default:
                break;
        }
        return null;
    }

    // renvoie une tableau d'entiers, chaque entier représente la cellule adjascente
    // qu'il faut prendre pour avancer dans le chemin
    // renvoie null si le chemin n'existe pas, ou s'il demande plus de maxlenght mouvements
    public byte[] getPath(int x1, int y1, int x2, int y2, int maxLength) {
        return null;
    }

    //bouge l'entité d'une case, et update ses coordoonées internes
    //on suppose que le mouvement est possible
    public void move(Entity e, int direction) {
        int x=e.getX();
        int y=e.getY();
        getAdjCell(x,y,direction).setEntity(e);
        getCell(x,y).setEntity(null);
        e.updateCoords(direction);
    }

}