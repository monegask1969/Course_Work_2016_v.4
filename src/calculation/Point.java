package calculation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="Point")
@XmlType(propOrder = {"x","y"})
public class Point {

    private double x;

    private double y;
    public Point(){}
    public Point(double x,double y){
        this.x=x;
        this.y=y;
    }
    @XmlElement
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }
    @XmlElement
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
