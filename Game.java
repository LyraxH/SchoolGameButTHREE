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
    int buffs;// how many buffs on the map
    int treasure; // how many treasure items on the map
    int gridSize; // how big the playable map is
    int enemies; // how many enemies should show up
    int border; // where the border stands (one larger than playable map)
    int walls; // how many walls and/or barriers should get in the players way
    int obtainables;
    // misc variables
    Scanner input = new Scanner(System.in); // variable to get keyboard inputs.
    String breaker = "↓--------------------↓"; // just to split up text to make it more readable
    Random rng = new Random(); // rng.nextInt(gridSize);
    public String grid[][] = new String[100][100]; // size is set to maximum size, however not all of it will be used
    ArrayList<Integer> obtainLocationX = new ArrayList<Integer>();
    ArrayList<Integer> obtainLocationY = new ArrayList<Integer>();
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
        obtainables = buffs + treasure;
        gridSize = obtainables * 2;
        //System.out.println("Creating a " + gridSize + "x" + gridSize + " map with " + buffs + " buffs and " + treasure + " treasures");
        enemies = gridSize / 2;
        border = gridSize + 1;
        walls = gridSize * 2;
        //System.out.println("And " + enemies + " enemies");
        // makes entire grid null
        CreateMap();
    }
    public void CreateMap(){
        for (int i = 0; i < gridSize; i++){ // changes whats being changed for the y
            for (int t = 0; t < gridSize; t++){ // changes whats being changed for the x axis
                grid[i][t] = "null";
            }
        }
        for (int l = 0; l < treasure; l++){ // creates the treasure variables
            int randomOne  = rng.nextInt(gridSize); // plots x value randomly
            int randomTwo  = rng.nextInt(gridSize); // plots y value randomly
            if (grid[randomOne][randomTwo] == "treasure"){
                //System.out.println("Duplicate Space, Restarting render");
                CreateMap();
                return;
            } else if (grid[randomOne][randomTwo] == "null"){
                grid[randomOne][randomTwo] = "treasure";
                //System.out.println("Treasure created at " + randomOne + " " + randomTwo);
                obtainLocationX.add(randomOne);
                obtainLocationY.add(randomTwo);
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
                obtainLocationX.add(randomOne);
                obtainLocationY.add(randomTwo);
            }
        }
        for (int i = 0; i < border; i++){ // spawns border along the x axis
            grid[i][gridSize] = "BORDER";
            //System.out.println("Placing border at " + i + " : " + border);
        }
        for (int i = 0; i < border; i++){ // spawns border on the y axis
            grid[gridSize][i] = "BORDER";
            //System.out.println("Placing border at " + i + " : " + border);
        }
        //this is the annoying part that creates paths that bridge off of the obtainables making them all acessable.
        // BIGGEST PROBLEM, MAKING PATH SYSTEM THAT WORKS. LOTS OF TRIAL AND ERROR INCOMING
        CheckBoundries();
        for (int i = 0; i < gridSize; i++){ // changes whats being printed for the y axis
            for (int t = 0; t < gridSize; t++){ // changes whats being printed for the x axis
                if (grid[i][t] == "null"){
                    grid[i][t] = "wall"; //makes everything that is nothing, a wall
                }
            }
        }
        DrawGame();
    }
    public void CheckBoundries(){ // checks surroundings, and if branching out will hit any of them, and then tells the initial calculation to stay away from branching to that side
        for (int i = 0; i < obtainables; i++){ // goes through the location of all obtainables
            int startingX = obtainLocationX.get(i); // x location
            int startingY = obtainLocationY.get(i); // y location
            boolean up; // true means you can move, false means you cant
            boolean down; // true means you can move, false means you cant
            boolean left; // true means you can move, false means you cant
            boolean right; // true means you can move, false means you cant
            int checkingX = startingX + 1;
            if (grid[checkingX][startingY] == "BORDER" || grid[checkingX][startingY] == "treasure" || grid[checkingX][startingY] == "buff" || grid[checkingX][startingY] == "path"){ // checking if moving right makes you hit the border
                right = false;
            } else {
                right = true;
            }
            checkingX = startingX - 1;
            if (checkingX < 0  || grid[checkingX][startingY] == "treasure" || grid[checkingX][startingY] == "buff" || grid[checkingX][startingY] == "path"){ // checking if moving left makes you leave the grid
                left = false;
            } else {
                left = true;
            }
            int checkingY = startingY + 1;
            if (grid[startingX][checkingY] == "BORDER" || grid[startingX][checkingY] == "treasure" || grid[startingX][checkingY] == "buff" || grid[startingX][checkingY] == "path"){ // checking if moving down makes you hit the border
                down = false;
            } else {
                down = true;
            }
            checkingY = startingY - 1;
            if (checkingY < 0 || grid[startingX][checkingY] == "treasure" || grid[startingX][checkingY] == "buff" || grid[startingX][checkingY] == "path"){ // checking if moving up makes you leave the grid
                up = false;
            } else {
                up = true;
            }
            System.out.println("Obtainable at X: " + startingX + " Y: " + startingY);
            System.out.println("UP: " + up);
            System.out.println("DOWN: " + down);
            System.out.println("LEFT: " + left);
            System.out.println("RIGHT: " + right);
            System.out.println("----------------------");
        }
    }
    public void PlaceWalls(){
        
    }
    public void PlaceWall(int x, int y){
        grid[x][y] = "path";
        return;
    }
    public void DrawGame(){
        //System.out.println("\f");
        for (int i = 0; i < border; i++){ // changes whats being printed for the y axis
            for (int t = 0; t < border; t++){ // changes whats being printed for the x axis
                if (grid[t][i] == "wall"){
                    System.out.print("██"); // prints what should be where
                } else if (grid[t][i] == "treasure"){
                    System.out.print("▓▓"); // prints what should be where
                } else if (grid[t][i] == "buff"){
                    System.out.print("░░"); // prints what should be where
                } else if (grid[t][i] == "BORDER"){
                    System.out.print("░░"); // prints what should be where
                } else if (grid[t][i] == "path"){
                    System.out.print("▒▒"); // prints what should be where
                }
            }
            System.out.println(""); // next line
        }
        //System.out.println(obtainLocationX);
        //System.out.println(obtainLocationY);
    }
    public void CheckMove(){
        
    }
}
