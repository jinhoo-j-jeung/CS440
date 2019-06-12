import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CreateMaze {
    public static char[][] maze;

    public static void printMaze() {
        for(int i = 0; i < maze.length; i ++){
            for (int j = 0 ; j < maze[0].length;j++){
                if(j == maze[0].length-1){
                    System.out.println(maze[i][j]);
                }
                else{
                    System.out.print(maze[i][j]);
                }
            }
        }
    }

    public CreateMaze(String filename) {
        String fileName = filename;
        String line = null;
        int height = 0;
        int width = 0;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                height++;
                width = line.length();
            }
            fileReader.close();
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

        maze = new char[height][width];
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int h = 0;
            while ((line = bufferedReader.readLine()) != null) {
                for (int i = 0 ; i < width; i ++) {
                    maze[h][i] = line.charAt(i);
                }
                h++;
            }
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main (String[] args) {
        String name = args[0];
        CreateMaze m = new CreateMaze(name);
        printMaze();
    }
}