package model;
import java.util.LinkedList;

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
        4  x  1
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
        boolean odd = h%2==0;
        int [] result = new int [2];
        switch (direction) {
            case 0:
                if (h<=0 || !odd && w>=width-1) return null;
                if (odd) {
                    // return cells[h-1][w];
                    result[0] = h-1;    result[1] = w;      return result;
                }
                else{
                    // return cells[h-1][w+1];
                    result[0] = h-1;    result[1] = w+1;    return result;
                }
            case 1:
                if (w>=width-1) return null;
                    // return cells[h][w+1];
                    result[0] = h;      result[1] = w+1;    return result;
            case 2:
                if (h>=height-1 || !odd && w>=width-1) return null;
                if (odd) {
                    // return cells[h+1][w];
                    result[0] = h+1;    result[1] = w;      return result;
                }
                else {
                    // return cells[h+1][w+1];
                    result[0] = h+1;    result[1] = w+1;    return result;
                }
            case 3:
                if (h>=height-1 || odd && w<=0) return null;
                if (odd) {
                    // return cells[h+1][w-1];
                    result[0] = h+1;    result[1] = w-1;    return result;
                }
                else {
                    // return cells[h+1][w];
                    result[0] = h+1;    result[1] = w;      return result;
                }
            case 4:
                if (w<=0) return null;
                    // return cells[h][w-1];
                    result[0] = h;      result[1] = w-1;    return result;
            case 5:
                if (h<=0 || odd && w<=0) return null;
                if (odd) {
                    // return cells[h-1][w-1];
                    result[0] = h-1;    result[1] = w-1;    return result;
                }
                else {
                    // return cells[h-1][w];
                    result[0] = h-1;    result[1] = w;      return result;
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

    double heuristicDistance(int x1, int y1, int x2, int y2){
        int distX = Math.abs(x1-x2);
        int distY = Math.abs(y1-y2);
        return Math.sqrt(distX*distX+distY*distY);
    }

    byte meilleureDirection(int x1, int y1, int x2, int y2){
        byte meilleureDirection;
        double distanceMinimale;

        meilleureDirection = -1;
        distanceMinimale = Double.MAX_VALUE;

        for(byte i = 0; i < 6; i++){
            int [] adjCellCoordinates = getAdjCellCoordinates(x1, x2, i);
            if(heuristicDistance(adjCellCoordinates[0], adjCellCoordinates[1], x2, y2) < distanceMinimale && 
            isMovePossible(adjCellCoordinates[0], adjCellCoordinates[1], i)){
                meilleureDirection = i;
                distanceMinimale = heuristicDistance(adjCellCoordinates[0], adjCellCoordinates[1], x2, y2);
            }
        }
        return meilleureDirection;
    }

    byte retournerDirection(byte direction){
        int result = direction-3;
        if(result < 0){
            result = 6 + result;
        }
        return (byte)Math.abs(result);
    }

    boolean existeOuvert(boolean open[][]){
        for(int i = 0; i < open.length; i++){
            for(int j = 0; j < open[i].length; j++){
                if(open[i][j] == true)
                    return true;
            }
        }
        return false;
    }

    // Algorithme A*
    // renvoie une tableau d'entiers, chaque entier représente la cellule adjascente
    // qu'il faut prendre pour avancer dans le chemin
    // renvoie null si le chemin n'existe pas, ou s'il demande plus de maxlength mouvements
    public byte[] getPath(int x1, int y1, int x2, int y2, int maxLength) {
        if (x1<0 || x2<0) return null;
        if (maxLength<=0) return null;

        //distance estimée au point final
        double heuristic[][] = new double[this.height][this.width]; 
        //distance au point d'origine
        int distance[][] = new int[this.height][this.width]; 
        // direction qu'il faut prendre pour arriver à la cellule de laquelle on vient
        byte origin[][] = new byte[this.height][this.width]; 
        // cellules qu'on a pas encore empruntées mais pour lesquelles on a déjà colculé les valeurs
        boolean open[][] = new boolean [this.height][this.width]; 
        // cellules par lesquelles on est déjà passés
        boolean closed[][] = new boolean [this.height][this.width];

        // Initialisation du tableau indiquant la direction de laquelle on est venus pour arriver à chaque cellule
        for(int i = 0; i < distance.length; i++){
            for(int j = 0; j < distance[i].length; j++){
                origin[i][j] = -1;
            }
        }

        // Initialisation du tableau des distances au point de départ
        for(int i = 0; i < distance.length; i++){
            for(int j = 0; j < distance[i].length; j++){
                distance[i][j] = Integer.MAX_VALUE;
            }
        }
        distance[x1][y1] = 0;

        // Initialisation des valeurs estimées au point final
        for(int i = 0; i < heuristic.length; i++){
            for(int j = 0; j < heuristic[i].length; j++){
                heuristic[i][j] = heuristicDistance(i, j, x2, y2);
                // System.out.print(heuristic[i][j] + " ");
            }
            // System.out.println();
        }

        open[x1][y1] = true;

        int xCourant = x1;
        int yCourant = y1;

        while(existeOuvert(open) && (xCourant !=x2 || yCourant != y2)){

            // On sélectionne la cellule qui a la plus petite valeur distance+heuristic
            // En cas d'égalité, on prend celle qui a la plus petite valeur heuristique
            double min = Double.MAX_VALUE;
            double minHeuristic = Double.MAX_VALUE;
            for(int i = 0; i < this.height; i++){
                for(int j = 0; j < this.width; j++){
                    if(open[i][j] && ( (distance[i][j] + heuristic[i][j] < min) || (distance[i][j] + heuristic[i][j] == min && minHeuristic < heuristic[i][j]) ) ){
                        xCourant = i;
                        yCourant = j;
                        min = distance[i][j] + heuristic[i][j];
                        minHeuristic = heuristic[i][j];
                    }
                }
            }

            // System.out.println("On a sélectionné la meilleure cellule " + xCourant + " " + yCourant);

            open[xCourant][yCourant] = false;
            closed[xCourant][yCourant] = true;

            // On calcule les distances pour les cellules voisines à celle sélectionnée
            if(xCourant !=x2 || yCourant != y2){
                for(byte i = 0; i < 6; i++){

                    if(getAdjCellCoordinates(xCourant, yCourant, i) != null){
                        int xAdjCell = getAdjCellCoordinates(xCourant, yCourant, i)[0];
                        int yAdjCell = getAdjCellCoordinates(xCourant, yCourant, i)[1];

                        if(isMovePossible(xCourant, yCourant, i) && !closed[xAdjCell][yAdjCell]){
                            
                            // On vérifie si il existe un raccourci à chaque cellule voisine en passant par la cellule sélectionnée
                            if((distance[xCourant][yCourant] + 1 < distance[xAdjCell][yAdjCell] ) || !open[xAdjCell][yAdjCell]){
                                distance[xAdjCell][yAdjCell] = distance[xCourant][yCourant] + 1;
                                origin[xAdjCell][yAdjCell] = retournerDirection(i);
                                if(!open[xAdjCell][yAdjCell]){
                                    open[xAdjCell][yAdjCell] = true;
                                }
                            }
                        }
                    }
                    
                }
            }
            
            
        }

        // Affichage de la cellule à laquelle l'algorithme s'est arrêté (pour faire des tests)
        // System.out.println(xCourant + " " + yCourant);

        // Affichage du tableau des distances + les valeurs heuristiques (pour faire des tests)
        /*for(int i = 0; i < distance.length; i++){
            for(int j = 0; j < distance[i].length; j++){
                if(distance[i][j] == Integer.MAX_VALUE){
                    System.out.print("x  ");
                }
                else{
                    int result = distance[i][j] + (int)heuristic[i][j];
                    if(result < 10){
                        System.out.print(result + "  ");
                    }
                    else{
                        System.out.print(result + " ");
                    }
                }
                
            }
            System.out.println();
        }*/

        // On crée une liste contenant toutes les directions menant du point d'arrivée au point de départ
        LinkedList<Byte> path = new LinkedList<Byte>();

        while(xCourant != x1 || yCourant != y1){
            path.add(origin[xCourant][yCourant]);
            int parentCoordinates[] = getAdjCellCoordinates(xCourant, yCourant, origin[xCourant][yCourant]);
            xCourant = parentCoordinates[0];
            yCourant = parentCoordinates[1];
        }

        // Nous avons les directions pour aller du point d'arrivée au point de départ mais on a besoin des directions dans l'autre sens
        // On met les éléments de la liste dans un tableau et on retourne chaque direction 
        // On ne vérifie pas si le chemin est plus long que la valeur maximale pour le moment pour pouvoir faire des tests
        if(path.size() > maxLength){
            return null;
        }
        else{
            byte[] b = new byte[path.size()];
            for(int i = 0; i < path.size(); i++){
                 b[i] = retournerDirection(path.get(i));
            }
            return b;
        }
        
        //return null;


        // Vielle version pour des chemins de longueur 1
        /*byte[] b = new byte[path.size()];
        for(int i = 0; i < path.size(); i++){
            b[i] = path.get(i);
        }

        byte[] b = new byte[1];
        for (int i = 0; i < 6; i++) {
            if (cells[x2][y2]==getAdjCell(x1,y1,i)) {
                if (isMovePossible(x1,y1,i)) {
                    //System.out.println(getAdjCellCoordinates(x1,y1,i)[0]);
                    //System.out.println(getAdjCellCoordinates(x1,y1,i)[1]);
                    b[0]=(byte)i;
                    return b;
                }
            }
        }
        return null;*/
    }

    boolean isFull( boolean processed[][]){
        //teste s'il reste encore des sommets à visiter
        for(int i = 0; i < processed.length; i++){
            for(int j= 0; j < processed[i].length; j++){
                if(processed[i][j] == false)
                    return false;
            }
        }

        return true;
    }

    int[] minDistance(int distance[][], boolean processed[][]){
        int min = Integer.MAX_VALUE;
        int[] result = new int[2];
        result[0] = -1;
        result[1] = -1;

        for(int i = 0; i < distance.length; i++){
            for(int j = 0; j < distance[i].length; j++){
                if(distance[i][j] < min && !processed[i][j]){
                    min = distance[i][j];
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        
        // System.out.println("Distance minimale minDistance: " + result[0] + ", " + result[1]);

        return result;
    }

    // Algorithme de Dijkstra
    /*public byte[] getPath(int x1, int y1, int x2, int y2, int maxLength) {
        if (x1<0 || x2<0) return null;
        if (maxLength<=0) return null;

        //distance au point d'origine
        int distance[][] = new int[this.height][this.width]; 
        // direction qu'il faut prendre pour arriver à la cellule de laquelle on vient
        byte origin[][] = new byte[this.height][this.width]; 
        // cellules pour lesquelles on a colculé les valeurs
        boolean processed[][] = new boolean[this.height][this.width]; 

        // Initialisation du tableau indiquant la direction de laquelle on est venus pour arriver à chaque cellule
        for(int i = 0; i < distance.length; i++){
            for(int j = 0; j < distance[i].length; j++){
                origin[i][j] = -1;
            }
        }

        // Initialisation du tableau des distances au point de départ
        for(int i = 0; i < distance.length; i++){
            for(int j = 0; j < distance[i].length; j++){
                distance[i][j] = Integer.MAX_VALUE;
            }
        }
        distance[x1][y1] = 0;

        // Initialisation du tableau des cellules pour lesquelles on a calculé les valeurs
        for(int i = 0; i < processed.length; i++){
            for(int j = 0; j < processed[i].length; j++){
                processed[i][j] = false;
            }
        }

        int[] celluleCourante = {x1, y1};

        while(!isFull(processed)){
            // On choisit la cellule qui a la plus petite distance du point de départ
            celluleCourante = minDistance(distance, processed);

            // On vérifie si on a fini de parcourir le tableau
            if(celluleCourante[0] == -1 || celluleCourante[1] == -1 || distance[celluleCourante[0]][celluleCourante[1]] == Integer.MAX_VALUE){
                break;
            }

            // On marque le cellule qu'on vient de choisir comme évaluée
            processed[celluleCourante[0]][celluleCourante[1]] = true;

            // On calcule les distances des cellules voisines à la cellule courante
            for(byte i = 0; i < 6; i++){
                if(getAdjCellCoordinates(celluleCourante[0], celluleCourante[1], i) != null && isMovePossible(celluleCourante[0], celluleCourante[1], i)){
                    int xAdjCell = getAdjCellCoordinates(celluleCourante[0], celluleCourante[1], i)[0];
                    int yAdjCell = getAdjCellCoordinates(celluleCourante[0], celluleCourante[1], i)[1];

                    if(isMovePossible(celluleCourante[0], celluleCourante[1], i) && !processed[xAdjCell][yAdjCell]){
                        
                        // On vérifie si il existe un raccourci à chaque cellule voisine en passant par la cellule sélectionnée
                        if((distance[celluleCourante[0]][celluleCourante[1]] + 1 < distance[xAdjCell][yAdjCell] ) ){
                            //System.out.println("J'actualise le chemin");
                            distance[xAdjCell][yAdjCell] = distance[celluleCourante[0]][celluleCourante[1]] + 1;
                            origin[xAdjCell][yAdjCell] = retournerDirection(i);
                        }
                    }
                }
                
            }

        }

        celluleCourante[0] = x2;
        celluleCourante[1] = y2;

        // On crée une liste contenant toutes les directions menant du point d'arrivée au point de départ
        LinkedList<Byte> path = new LinkedList<Byte>();

        // On retourne à la cellule de départ depuis la cellule d'arrivée en ajoutant la direction qu'on prend à chaque fois
        while(celluleCourante[0] != x1 || celluleCourante[1] != y1){
            path.add(origin[celluleCourante[0]][celluleCourante[1]]);
            int parentCoordinates[] = getAdjCellCoordinates(celluleCourante[0], celluleCourante[1], origin[celluleCourante[0]][celluleCourante[1]]);
            celluleCourante[0] = parentCoordinates[0];
            celluleCourante[1] = parentCoordinates[1];
        }


        // Nous avons les directions pour aller du point d'arrivée au point de départ mais on a besoin des directions dans l'autre sens
        // On met les éléments de la liste dans un tableau et on retourne chaque direction 
        if(path.size() > maxLength){
            return null;
        }
        else{
            byte[] b = new byte[path.size()];
            for(int i = 0; i < path.size(); i++){
                b[i] = retournerDirection(path.get(i));
            }
            return b;
        }
    }*/

    // Bouge l'entité d'une case, et update ses coordoonées internes
    public void move(Entity e, int direction) {
        int x=e.getX();
        int y=e.getY();
        if (!isMovePossible(x,y,direction)) return;
        getAdjCell(x,y,direction).setEntity(e);
        getCell(x,y).setEntity(null);
        e.updateCoords(direction);
        e.decreaseMp();

    }

}