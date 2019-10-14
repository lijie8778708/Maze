package maze;

/**
 * @author jieli
 * Create a class to store a maze's attibutes and methods
 */
public class MazeData {

    public static char ROAD = ' ';
    public static char WALL = '#';
    public static final int ENTER_X = 1;
    public static final int ENTER_Y = 0;
    public int Exit_X;
    public int Exit_Y;

    public char[][] maze;
    public boolean[][] isVisited;
    public boolean[][] inMist;
    public boolean[][] path;

    public int N;
    public int M;


    public MazeData(int N, int M){
        // a maze's length and width have to be odd
        if(N%2 == 0 || M %2 == 0){
            throw new IllegalArgumentException("Invalid parameter");
        }
        // define entry point and exit point
        this.N = N;
        this.M = M;
        this.Exit_X = N-2;
        this.Exit_Y = N-1;

        // initialize maze's size
        maze = new char[N][M];
        isVisited = new boolean[N][M];
        inMist = new boolean[N][M];
        path = new boolean[N][M];

        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                // if both x and y are odd, that means current point is road.
                if(i % 2 == 1 && j % 2 == 1){
                    maze[i][j] = MazeData.ROAD;
                }else{
                    maze[i][j] = MazeData.WALL;
                }
                // initialize all coordinate as unvisited
                isVisited[i][j] = false;
                inMist[i][j] = true;
                path[i][j] = false;
            }
        }

        maze[MazeData.ENTER_X][MazeData.ENTER_Y] = MazeData.ROAD;
        maze[this.Exit_X][this.Exit_Y] = MazeData.ROAD;

    }
    // verify if current point is in my maze
    public boolean inArea(int x, int y){
        return x>=0 && x<N && y>=0 & y<M;
    }

    // let current position become visible
    public void openMist(int x, int y){
        if(!inArea(x, y)){
            throw new ArrayIndexOutOfBoundsException("Out of Bound");
        }
        for(int i = x-1; i <= x+1; i++)
        {
            for(int j = y-1; j <= y+1; j++){
                inMist[i][j] = false;
            }
        }
    }
}
