/**
 * Creates terrain and gets me credits. I guess.
 *
 * Taison Shea
 * 04/05/23 -> 
 */
import java.util.Scanner;
import java.util.*;
import java.util.ArrayList;
import java.util.Random; 
public class Game
{
    // world building variables;
    int buffs;
    int treasure;
    int gridSize;
    int enemies;
    int border;
    // misc variables
    Scanner input = new Scanner(System.in); // variable to get keyboard inputs.
    String breaker = "↓--------------------↓"; // just to split up text to make it more readable
    Random rng = new Random(); // rng.nextInt(gridSize);
    public String grid[][] = new String[100][100]; // size is set to maximum size, however not all of it will be used
    public Game(){
         InitializeGame();
    }
    public void InitializeGame(){
        // sets parameters for the map based on what the player wants.
        System.out.println("How many buffs do you want on the map? Recommendation: 1-2 Max: 10");
        buffs = input.nextInt();
        if (buffs > 10){
            System.out.println("Plese enter a number less than 10");
            InitializeGame();
            return;
        }
        System.out.println("How many obtainable treasures do you want on the map? Recommendation: 2-4 Max: 10");
        treasure = input.nextInt();
        if (treasure > 10){
            System.out.println("Plese enter a number less than 10");
            InitializeGame();
            return;
        }
        gridSize = (buffs + treasure) * 2;
        //System.out.println("Creating a " + gridSize + "x" + gridSize + " map with " + buffs + " buffs and " + treasure + " treasures");
        enemies = gridSize / 2;
        border = gridSize + 1;
        //System.out.println("And " + enemies + " enemies");
        // makes entire grid null
        CreateMap();
    }
    public void CreateMap(){
        for (int i = 0; i < gridSize; i++){ // changes whats being printed for the y axis
            for (int t = 0; t < gridSize; t++){ // changes whats being printed for the x axis
                grid[i][t] = "null";
            }
        }
        for (int l = 0; l < treasure; l++){ // creates the treasure variables
            int randomOne  = rng.nextInt(gridSize);
            int randomTwo  = rng.nextInt(gridSize);
            if (grid[randomOne][randomTwo] == "treasure"){
                //System.out.println("Duplicate Space, Restarting render");
                CreateMap();
                return;
            } else if (grid[randomOne][randomTwo] == "null"){
                grid[randomOne][randomTwo] = "treasure";
                //System.out.println("Treasure created at " + randomOne + " " + randomTwo);
            }
        }
        for (int l = 0; l < buffs; l++){ // creates the buffs variables
            int randomOne  = rng.nextInt(gridSize);
            int randomTwo  = rng.nextInt(gridSize);
            if (grid[randomOne][randomTwo] == "treasure" || grid[randomOne][randomTwo] == "buff"){
                //System.out.println("Duplicate Space, Restarting render");
                CreateMap();
                return;
            } else if (grid[randomOne][randomTwo] == "null"){
                grid[randomOne][randomTwo] = "buff";
                //System.out.println("Buff created at " + randomOne + " " + randomTwo);
            }
        }
        for (int i = 0; i < gridSize; i++){ // changes whats being printed for the y axis
            for (int t = 0; t < gridSize; t++){ // changes whats being printed for the x axis
                if (grid[i][t] == "null"){
                    grid[i][t] = "path"; //makes everything that is nothing, a path.
                }
            }
        }
        for (int i = 0; i < border; i++){
            grid[i][gridSize] = "BORDER";
            //System.out.println("Placing border at " + i + " : " + border);
        }
        for (int i = 0; i < border; i++){
            grid[gridSize][i] = "BORDER";
            //System.out.println("Placing border at " + i + " : " + border);
        }
        DrawGame();
    }
    public void DrawGame(){
        System.out.println("\f");
        for (int i = 0; i < border; i++){ // changes whats being printed for the y axis
            for (int t = 0; t < border; t++){ // changes whats being printed for the x axis
                if (grid[t][i] == "wall"){
                    System.out.print("██"); // prints what should be where
                } else if (grid[t][i] == "treasure"){
                    System.out.print("▓▓"); // prints what should be where
                } else if (grid[t][i] == "buff"){
                    System.out.print("░░"); // prints what should be where
                } else if (grid[t][i] == "enemy"){
                    System.out.print("░░"); // prints what should be where
                } else if (grid[t][i] == "path"){
                    System.out.print("▒▒"); // prints what should be where
                }
            }
            System.out.println(""); // next line
        }
    }
    public void CheckMove(){
        
    }
}
