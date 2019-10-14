package maze;

import javax.swing.*;
import java.awt.*;

/**
 * @author jieli
 * pack frame
 */
public class MyFrame extends JFrame {

    private int sceneWidth;
    private int sceneHeight;
    private MazeData mazeData;

    // initialize the frame
    public MyFrame(String title, int sceneWidth, int sceneHeight) {

        super(title);
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        // initialize the panel
        MyPanel panel = new MyPanel();

        // let panel's size cove the frame's size
        panel.setPreferredSize(new Dimension(this.sceneWidth, this.sceneHeight));

        // add the panel to frame
        this.setContentPane(panel);
        pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    // enter data to paint the maze
    public void render(MazeData mazeData){
        this.mazeData = mazeData;
        repaint();
    }


    // pack my own panel
    private class MyPanel extends JPanel{

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D graphics2D = (Graphics2D)g;

            // make the maze looks more smooth
            RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics2D.addRenderingHints(renderingHints);

            // calculate for the width and length of my small rectangle
            int w = sceneWidth/mazeData.M;
            int h = sceneHeight/mazeData.N;

            // check the situation of current point
            for(int i = 0; i < mazeData.M; i++){
                for(int j = 0; j < mazeData.N; j++){
                    // check if current point visited
                    if(mazeData.inMist[i][j]){
                        MyVisHelper.setColor(graphics2D, Color.BLACK);
                    }
                    // check if current point is wall
                    else if(mazeData.maze[i][j] == MazeData.WALL){
                        MyVisHelper.setColor(graphics2D, Color.LIGHT_GRAY);
                    }
                    // if all top conditions failed, current point is my road
                    else{
                        MyVisHelper.setColor(graphics2D, Color.WHITE);
                    }
                    // paint this point as a rectangle on correct axis
                    if(mazeData.path[i][j]){
                        MyVisHelper.setColor(graphics2D, Color.YELLOW);
                    }
                    MyVisHelper.fillRectangle(graphics2D, j * w, i * h, w, h);
                }
            }
        }
    }
}
