package model;

/**
 * The type Coordinates.
 */
public class Coordinates {
    private double x; //Значение поля должно быть больше -527
    private double y;

    /**
     * Instantiates a new Coordinates.
     *
     * @param x the x
     * @param y the y
     */
    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * Instantiates a new Coordinates.
     */
    public Coordinates() {
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(Double y) {
        this.y = y;
    }
    public static Coordinates fromStringToCoordinates(String line){
        return new Coordinates(Double.parseDouble(line.split(";")[0]), Double.parseDouble(line.split(";")[1]));
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}