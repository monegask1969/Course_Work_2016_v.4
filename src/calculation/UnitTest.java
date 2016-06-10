package calculation;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class UnitTest {
    @Test
    public void test1() {
        CW c = new CW();
        ArrayList<Point> x = new ArrayList<>();
        x.add(new Point(-2, -3));
        x.add(new Point(0, 1));
        x.add(new Point(2, -3));
        Assert.assertEquals(0,c.Interpoilation(1.0,x),0.1);
    }

    @Test
    public void test2(){
        CW c=new CW();
        ArrayList<Point> F= new ArrayList<>();
        ArrayList<Point> G= new ArrayList<>();
        G.add(new Point(-2,-3));
        G.add(new Point(0,1));
        G.add(new Point(2,-3));
        F.add(new Point(-3,0));
        F.add(new Point(3,0));
        Assert.assertEquals(-1,c.x_minimum_search(-3,3,0.001,F,G),0.1);
    }
}
