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
        for (int i = 0; i < obtainables; i++){
            int startingX = obtainLocationX.get(i);
            int startingY = obtainLocationY.get(i);
            //System.out.println("X: " + startingX);
            //System.out.println("Y: " + startingY);
            for (int w = 0; w < walls; w++){
                int direction = rng.nextInt(3);
                int nextX;
                int nextY;
                if (direction == 0){
                    nextY = startingY - 1; // goes up
                    if (nextY < 0){ // if going up makes you hit a border or goes over the edge
                        nextY = startingY + 2;
                        placeWall(startingX, nextY);
                    }
                } else if (direction == 1){
                    nextY = startingY + 1; // goes down
                    if (grid[startingX][nextY] == "BORDER"){ // if going down makes you hit a border or goes over the edge
                        nextY = startingY - 1;
                        placeWall(startingX, nextY);
                    }
                } else if (direction == 2){
                    nextX = startingX - 1; //goes left
                    if (nextX < 0){ // if going left makes you hit a border or goes over the edge
                        nextY = startingY + 2;
                        placeWall(nextX, startingY);
                    }
                } else if (direction == 3){
                    nextX = startingX + 1;// goes right
                    if (grid[nextX][startingY] == "BORDER"){ // if going right makes you hit a border or goes over the edge
                        nextY = startingY - 1;
                        placeWall(nextX, startingY);
                    }
                }
            }
        }
        // BIGGEST PROBLEM, MAKING PATH SYSTEM THAT WORKS. LOTS OF TRIAL AND ERROR INCOMING
            for (int i = 0; i < gridSize; i++){ // changes whats being printed for the y axis
            for (int t = 0; t < gridSize; t++){ // changes whats being printed for the x axis
                if (grid[i][t] == "null"){
                    grid[i][t] = "path"; //makes everything that is nothing, a wall
                }
            }
        }
        DrawGame();
    }
    public void placeWall(int x, int y){
        grid[x][y] = "wall";
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
