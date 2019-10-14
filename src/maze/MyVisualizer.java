package maze;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author jieli
 * Generate Maze
 */

public class MyVisualizer {

    // set default delay time
    public static final int DELAY = 1;
    public static int blockSide = 8;
    // 2D array for counter search
    public int[][] d = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    private MazeData data;
    private MyFrame myFrame;

    /*
        Initialize the window
     */
    public MyVisualizer(int mazeWidth, int mazeHeight, String title){

        // initialize the maze
        data = new MazeData(mazeWidth, mazeHeight);

        // initialize maze's length and width
        int sceneWidth = data.N * blockSide;
        int sceneHeight = data.M * blockSide;

        EventQueue.invokeLater(()->{
            // initialize the frame
          myFrame = new MyFrame(title, sceneWidth, sceneHeight);
          myFrame.addKeyListener(new MyListener());
          // start to generate thr maze
          new Thread(()->{
              startGenerate();
          }).start();
        });
    }

    public void startGenerate(){

        // paint the maze
        setData(-1, -1);

        // start to generate maze from enter point
        generateMaze(MazeData.ENTER_X, MazeData.ENTER_Y+1);

        setData(-1, -1);
    }

    public void startSearch(){
        setData(-1, -1, false);

        searchMaze(MazeData.ENTER_X, MazeData.ENTER_Y);

        setData(-1, -1, false);
    }

    // repaint the window
    public void setData(int x, int y){
        if(data.inArea(x, y)){
            data.maze[x][y] = MazeData.ROAD;
        }
        myFrame.render(data);
        MyVisHelper.pause(MyVisualizer.DELAY);
    }

    // repaint the window
    public void setData(int x, int y, boolean isPath){
        if(data.inArea(x, y)){
            data.path[x][y] = isPath;
        }
        myFrame.render(data);
        MyVisHelper.pause(MyVisualizer.DELAY);
    }

    // use backtrack mindset to search a correct path
    public boolean searchMaze(int x, int y){

        if(!data.inArea(x, y)){
            throw new IllegalArgumentException("Out of Bound");
        }

        // end this method once the path reach to end point
        if(x == data.Exit_X && y == data.Exit_Y){
            return true;
        }

        data.isVisited[x][y] = true;
        setData(x, y, true);

        for(int i = 0; i < 4; i++){
            int newX = x + d[i][0];
            int newY = y + d[i][1];

            if(data.inArea(newX, newY)
                    && !data.isVisited[newX][newY] &&
                        data.maze[newX][newY] == MazeData.ROAD)
                if(searchMaze(newX, newY)){
                    return true;
                }
        }
        setData(x, y, false);
        return false;
    }

    // generate a randomly maze
    public void generateMaze(int x, int y){

        // check if the point is inside of the maze
        if(!data.inArea(x, y)){
            throw new IllegalArgumentException("Out of bound");
        }

        // initialize a random queue
        RandomQueue<Position> queue = new RandomQueue<>();

        // initialize the start point
        Position position = new Position(MazeData.ENTER_X, MazeData.ENTER_Y+1);

        queue.add(position);
        data.isVisited[position.getX()][position.getY()] = true;
        data.openMist(position.getX(), position.getY());

        // start randomly depth first search and breath first search
        while(!queue.isEmpty()){

            position = queue.remove();

            for(int i = 0; i < 4; i++){
                // counter search for valid point
                int newX = position.getX() + d[i][0] * 2;
                int newY = position.getY() + d[i][1] * 2;

                if(data.inArea(newX, newY) && !data.isVisited[newX][newY]){
                    queue.add(new Position(newX, newY));
                    setData(position.getX() + d[i][0], position.getY() + d[i][1]);
                    // marked current point ad visited
                    data.isVisited[newX][newY] = true;
                    data.openMist(newX, newY);
                }
            }
        }
    } 
    private class MyListener extends KeyAdapter {

        // start to search a correct path once one the space is pressed
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyChar() == ' '){
                for(int i = 0; i < data.isVisited.length; i++){
                    for(int j = 0; j < data.isVisited[0].length; j++){
                        data.isVisited[i][j] = false;
                    }
                }

                new Thread(()->{
                    startSearch();
                }).start();
            }
        }

    }
}
