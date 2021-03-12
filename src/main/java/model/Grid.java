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

    private boolean isMovePossible(int x, int y, int orientation) {
        Cell c = getAdjCell(x, y, orientation);
        if (c==null) return false;
        int delta = cells[x][y].getHeight() - c.getHeight();
        return (c.getEntity()==null && delta <= 1 && delta >=-1 );
    }

    // renvoie une tableau d'entiers, chaque entier représente la cellule adjascente
    // qu'il faut prendre pour avancer dans le chemin
    // renvoie null si le chemin n'existe pas, ou s'il demande plus de maxlenght mouvements
    public byte[] getPath(int x1, int y1, int x2, int y2, int maxLength) {
        if (x1<0 || x2<0) return null;
        if (maxLength<=0) return null;
        byte[] b = new byte[1];
        for (int i = 0; i < 6; i++) {
            if (cells[x2][y2]==getAdjCell(x1,y1,i)) {
                if (isMovePossible(x1,y1,i)) {
                    b[0]=(byte)i;
                    return b;
                }
            }
        }
        return null;
    }

    //bouge l'entité d'une case, et update ses coordoonées internes
    public void move(Entity e, int direction) {
        int x=e.getX();
        int y=e.getY();
        if (!isMovePossible(x,y,direction) || e.getMp()<=0) return;
        getAdjCell(x,y,direction).setEntity(e);
        getCell(x,y).setEntity(null);
        e.updateCoords(direction);
        e.decreaseMp();

    }

    public boolean isWithinRange(int x1, int y1, int x2, int y2, int minRange, int maxRange) {
        double coordX1=getEffectiveX(x1);
        double coordY1=getEffectiveY(y1);
        double coordX2=getEffectiveX(x2);
        double coordY2=getEffectiveY(y2);
        double distance = Math.sqrt((coordX1-coordX2)*(coordX1-coordX2)+(coordY1-coordY2)*(coordY1-coordY2));
        return distance>=minRange && distance<=maxRange;
    }

    // renvoie les coordonnées carthésienne de la position de cells[x][y];
    private double getEffectiveX(int x) {
        return (x%2==0)? x:x+0.5;
    }

    private double getEffectiveY(int y) {
        return y*0.75;
    }

}