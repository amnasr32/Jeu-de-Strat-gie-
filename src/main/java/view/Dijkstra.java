package view;

import java.util.List;
import java.util.Map;
import java.lang.Math.*;

public class Dijkstra {
    private Map<Hexagon, List<Hexagon>> adjHexagons;

    public Dijkstra(Map<Hexagon, List<Hexagon>> adjHexagons){
        this.adjHexagons=adjHexagons;
    }

    public Hexagon plusprochePointFinal(Hexagon debut, Hexagon arrive){
        for (Hexagon adj : adjHexagons.get(debut)) {
            int distance2pointX = Math.abs(debut.x - arrive.x);
            int distance2pointY = Math.abs(debut.y - arrive.y);
            int pythagore=  (int)Math.sqrt(distance2pointX*distance2pointX + distance2pointY*distance2pointY);
        }
        return debut;
    }
}
