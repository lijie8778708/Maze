package maze;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class MyVisHelper {
    public static void setStrokeWidth(Graphics2D graphics2D, int w){
        int strokeWidth = w;
        graphics2D.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }

    public static void strokeCircle(Graphics2D graphics2D, int x, int y, int r){
        Ellipse2D ellipse2D = new Ellipse2D.Double(x-r, y-r, 2*r, 2*r);
        graphics2D.draw(ellipse2D);
    }

    public static void fillCircle(Graphics2D graphics2D, int x, int y, int r){
        Ellipse2D ellipse2D = new Ellipse2D.Double(x-r, y-r, 2*r, 2*r);
        graphics2D.fill(ellipse2D);
    }

    public static void fillRectangle(Graphics2D graphics2D, int x, int y, int w, int h){
        Rectangle2D rectangle = new Rectangle2D.Double(x, y, w, h);
        graphics2D.fill(rectangle);
    }

    public static void setColor(Graphics2D graphics2D, Color color){
        graphics2D.setColor(color);
    }

    public static void pause(int t){
        try{
            Thread.sleep(t);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
