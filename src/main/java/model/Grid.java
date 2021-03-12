package model;
import model.entity.Entity;
import java.io.Serializable;
import java.util.LinkedList;

public class Grid implements Serializable {
    


	private static final long serialVersionUID = -9029502583635790499L;

	private final Cell[][] cells;

    private final int height;
    private final int width;

    private LinkedList<int[]> coordList;

    final double offset=0.8660254037844386; // = (racine 3) / 2

    public Grid(int h, int w) {
        height=h;
        width=w;
        cells=new Cell[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                cells[i][j]=new Cell(); 
            }
        }
        coordList = new LinkedList<>();
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

    // vérifie que les coordonées sont dans la grille
    private boolean isInBounds(int x, int y) {
        return (x>=0 && y>=0 && x<height && y<width);
    }

    private boolean isInBounds(int[] coords) {
        return (coords[0]>=0 && coords[1]>=0 && coords[0]<height && coords[1]<width);
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

    // meme chose que getAdjCell mais a la place d'envoyer une cellule elle envoie les
    // coordonnees de la cellule adjacente
    // 5   0
    //4  x  1
    // 3   2
    public int[] getAdjCellCoordinates(int h, int w, int direction) {
        boolean odd = h % 2 == 0;
        int[] result = new int[2];
        switch (direction) {
            case 0:
                if (odd) {
                    result[0] = h - 1;
                    result[1] = w;
                    return result;
                } else {
                    result[0] = h - 1;
                    result[1] = w + 1;
                    return result;
                }
            case 1:
                result[0] = h;
                result[1] = w + 1;
                return result;
            case 2:
                if (odd) {
                    result[0] = h + 1;
                    result[1] = w;
                    return result;
                } else {
                    result[0] = h + 1;
                    result[1] = w + 1;
                    return result;
                }
            case 3:
                if (odd) {
                    result[0] = h + 1;
                    result[1] = w - 1;
                    return result;
                } else {
                    result[0] = h + 1;
                    result[1] = w;
                    return result;
                }
            case 4:
                result[0] = h;
                result[1] = w - 1;
                return result;
            case 5:
                if (odd) {
                    result[0] = h - 1;
                    result[1] = w - 1;
                    return result;
                } else {
                    result[0] = h - 1;
                    result[1] = w;
                    return result;
                }

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

    public void selectCellsWithinRange(int x, int y, int minRange, int maxRange) {
        clearCoordList();
        for (int i = minRange; i <= maxRange; i++) {
            addCellsAtDistance(x,y,i);
        }
    }

    // ajoute le cercle de distance dist à coordList
    private void addCellsAtDistance(int x, int y, int dist) {
        int[] coord={x,y+dist};
        if (isInBounds(coord)) coordList.add(coord);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < dist; j++) {
                coord=getAdjCellCoordinates(coord[0],coord[1],(i+3)%6);
                // TODO : vérifier que la cible est directement visible (pas d'obstacle)
                if (isInBounds(coord)) {
                    coordList.add(coord);
                }
            }
        }
    }

    public LinkedList<int[]> getCoordList() {
        return coordList;
    }

    public void clearCoordList() {
        if (coordList==null) coordList = new LinkedList<>();
        else coordList.clear();
    }

    // renvoie les coordonnées carthésienne de la position de cells[x][y];
    // utile pour calculer des distances
    private double[] getEffectiveXY(int x, int y) {
        double[] result = new double[2];
        result[0]=x*offset;
        result[1]=(x%2==0)? y:y+0.5;
        return result;
    }

    // calcule la distance entre deux cellules
    // je sais pas comment ça marche mais je prie petit jésus
    // allègrement copié depuis https://www.redblobgames.com/grids/hexagons/#distances
    private int distance(int x1, int y1, int x2, int y2) {
        int a1 = y1 - (x1 - ((x1%2==0)?0:1))/2;
        int c1 = x1;
        int b1 = -a1-c1;

        int a2 = y2 - (x2 - ((x2%2==0)?0:1))/2;;
        int c2 = x2;
        int b2 = -a2-c2;

        return (Math.abs(a1-a2)+Math.abs(b1-b2)+Math.abs(c1-c2)) / 2;
    }

    // distance réelle entre deux cellules
    private double distanceR(int x1, int y1, int x2, int y2) {
        double[] c1=getEffectiveXY(x1,y1);
        double[] c2=getEffectiveXY(x2,y2);
        return Math.sqrt((c1[0]-c2[0])*(c1[0]-c2[0])+(c1[1]-c2[1])*(c1[1]-c2[1]));
    }

    public boolean isInCoordList(int x, int y) {
        for (int[] coord: coordList) {
            if (x==coord[0] && y==coord[1]) return true;
        }
        return false;
    }

}