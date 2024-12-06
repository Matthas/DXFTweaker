package DXFRead.DXFElements.Coords;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

class Coordinate {
    double x;
    double y;
    public Coordinate(double x, double y){
        this.x = x;
        this.y = y;
    }
}

public class Coords {
    private int j;
    private Map<Integer, Coordinate> mCoords = new LinkedHashMap<>();

    public void addCoords(double x, double y) {
        //double x = Double.parseDouble(X);
        //double y = Double.parseDouble(Y);
        //x = Math.round(x * 1000.0) / 1000.0;
        //y = Math.round(y * 1000.0) / 1000.0;
        Coordinate coord = new Coordinate(x, y);
        mCoords.put(j, coord);
        j++;
        //System.out.println(coord.x + " " + coord.y);
    }

    public void addCoords(Coordinate nCoord) {
        mCoords.put(j, nCoord);
        j++;
    }

    public void reIndexElements() {
        Map<Integer, Coordinate> tempMap = new LinkedHashMap<>();
        int newIndex = 0;
        for (Coordinate coord : mCoords.values()) {
            tempMap.put(newIndex++, coord);
        }
        mCoords.clear();
        mCoords.putAll(tempMap);
    }


    public Integer size() {
        return mCoords.size();
    }

    public Coordinate getThatCoord(int i) {
        return mCoords.get(i);
    }

    public Coordinate getNCoord(int i) {
        return new Coordinate(mCoords.get(i).x, mCoords.get(i).y);
    }

    public double getNCoordX(int i) {
        return ((int) (mCoords.get(i).x * 1000)) / 1000.0;
        //return mCoords.get(i).x;
    }

    public double getNCoordY(int i) {
        //return (mCoords.get(i).y);
        return ((int) (mCoords.get(i).y * 1000)) / 1000.0;
    }
    public double getrawNCoordX(int i) {
        return mCoords.get(i).x;
    }

    public double getrawNCoordY(int i) {
        return (mCoords.get(i).y);
    }

    public void changeCoords(int i, Coordinate coordinate) {
        mCoords.put(i, new Coordinate(mCoords.get(i).x + coordinate.x, mCoords.get(i).y + coordinate.y));
    }

    public void replaceCoords(int i, Coordinate coordinate) {
        mCoords.replace(i, new Coordinate(coordinate.x, coordinate.y));
    }

    public void replaceCoordsXY(int i, double x, double y) {
        mCoords.replace(i, new Coordinate(x, y));
    }

    public void removeCoords(Integer i) {
        mCoords.remove(i);
        j -= 1;
    }

    public void removeZeroCoords() {
        Iterator<Map.Entry<Integer, Coordinate>> iterator = mCoords.entrySet().iterator();
        if (mCoords.size() != 1) {
            while (iterator.hasNext()) {
                Map.Entry<Integer, Coordinate> entry = iterator.next();
                if (entry.getValue().x == 0.0 && entry.getValue().y == 0.0) {
                    iterator.remove(); // Remove the entry with (x, y) = (0, 0)
                }
            }
        }
        j = mCoords.size();
    }

    //used to remove duplicated vortexes - because planner did not pay attention when drawing
    public String trimAndReturnDuplicates() {
        String tempstring = "";
        if (mCoords.size() > 1) {
            for (int i = 0; i < mCoords.size() - 1; i++) {
                double x1 = mCoords.get(i).x;
                double y1 = mCoords.get(i).y;
                double x2 = mCoords.get(i + 1).x;
                double y2 = mCoords.get(i + 1).y;

                if (x1 == x2 && y1 == y2) {
                    tempstring = tempstring + " ," + String.valueOf(i); //store number of duplicated vertex
                    Coordinate zerocoord = new Coordinate(0, 0);
                    mCoords.put(i, zerocoord); //set coordinates to 0 for duplicate vertex
                }
                removeZeroCoords();
            }
        }
        j = mCoords.size();
        return "Vertexes: " + tempstring + "are duplicates";
    }

    public void RemoveDeplicates() {
        if (mCoords.size() <= 1) {
            return;
        }
        for (int i = 0; i < mCoords.size() - 1; i++) {
            double x1 = mCoords.get(i).x;
            double y1 = mCoords.get(i).y;
            double x2 = mCoords.get(i + 1).x;
            double y2 = mCoords.get(i + 1).y;

            if (x1 == x2 && y1 == y2) {
                mCoords.remove(i);
            }
            reIndexElements();
        }
    }
}