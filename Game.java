/**
 * Creates terrain and gets me credits. I guess.
 * I have commented out some lines of code, this is to show progress of testing.
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
    int paths; // how many paths are required to make every area accessable
    int replaced = -1;
    int obtainables;
    // misc variables
    Scanner input = new Scanner(System.in); // variable to get keyboard inputs.
    String breaker = "↓--------------------↓"; // just to split up text to make it more readable
    Random rng = new Random(); // rng.nextInt(gridSize);
    public String grid[][] = new String[100][100]; // size is set to maximum size, however not all of it will be used
    ArrayList<Integer> obtainLocationX = new ArrayList<Integer>();
    ArrayList<Integer> obtainLocationY = new ArrayList<Integer>();
    ArrayList<Integer> obtainPathX = new ArrayList<Integer>();
    ArrayList<Integer> obtainPathY = new ArrayList<Integer>();    
    public Game(){
        obtainLocationX.clear(); //clears all prior x locations stored
        obtainLocationY.clear(); //clears all prior y locations stored
        obtainPathX.clear(); //clears all prior x locations stored
        obtainPathY.clear(); //clears all prior y locations stored
        InitializeBuffs();
        return;
    }
    public void InitializeBuffs(){
        // sets parameters for the map based on what the player wants.
        System.out.println("How many buffs do you want on the map? Recommendation: 1-2 Max: 10");
        try {
            buffs = 0;
            buffs = input.nextInt();
            if (buffs > 10){
                System.out.println("Plese enter a number less than 10");
                InitializeBuffs();
                return;
            } else {
                InitializeTreasure();
                return;
            }
        } catch (Exception e){
            System.out.println("Plese enter a numerical value");
            input.nextLine();
            InitializeBuffs();
            return;
        }
    }
    public void InitializeTreasure(){
        System.out.println("How many obtainable treasures do you want on the map? Recommendation: 2-4 Max: 10");
        try {
            treasure = input.nextInt();
            if (treasure > 10){
                System.out.println("Plese enter a number less than 10");
                InitializeTreasure();
                return;
            }
        } catch (Exception e){
            System.out.println("Plese enter a numerical value");
            input.nextLine();
            InitializeTreasure();
            return;
        }
        obtainables = buffs + treasure;
        gridSize = obtainables * 2;
        enemies = gridSize / 2;
        border = gridSize + 1;
        paths = 4;
        CreateMap();
        return;
    }
    public void CreateMap(){
        obtainLocationX.clear();
        obtainLocationY.clear();
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
        //System.out.println("Initial Set Of Paths");
        for (int i = 0; i < obtainables; i++){ // goes through the location of all obtainables
            int startingX = obtainLocationX.get(i); // x location of said obtainable
            int startingY = obtainLocationY.get(i); // y location of said obtainable
            AssignPath(startingX, startingY); // checks the immediate surroundings of each obtainable
        }

        //System.out.println("Paths After");
        for (int t = 0; t < 4; t++){
            for (int i = 0; i < obtainPathX.size(); i++){ // goes through the location of all paths
                int startingX = obtainPathX.get(i); // x location of said paths
                int startingY = obtainPathY.get(i); // y location of said paths
                ConstructPaths(startingX, startingY); // checks the immediate surroundings of each path
            }
        }
        //System.out.println("TESTING");
        for (int i = 0; i < obtainables; i++){ // goes through the location of all obtainables
            int startingX = obtainLocationX.get(i); // x location of said obtainables
            int startingY = obtainLocationY.get(i); // y location of said obtainables
            //System.out.println("Obtainable " + i + " X: " + startingX + " Y: " + startingY);
            boolean up = false;// true means there is nothing above
            boolean down = false; // true means there is nothing below
            boolean left = false; // true means there is nothing to the left
            boolean right = false; // true means there is nothing to the right
            if (startingX == gridSize || grid[(startingX + 1)][startingY] != "path" && grid[(startingX + 1)][startingY] != "buff" && grid[(startingX + 1)][startingY] != "treasure"){
                //System.out.println("Obtainable at X: " + startingX + " Y: " + startingY + " has nothing to its right, or is in the right most column");
                down = true;
            } else {
                //System.out.println("Obtainable at X: " + startingX + " Y: " + startingY + " has something to its right");
            }
                if (startingX == 0 || grid[(startingX - 1)][startingY] != "path" && grid[(startingX - 1)][startingY] != "buff" && grid[(startingX - 1)][startingY] != "treasure"){
                //System.out.println("Obtainable at X: " + startingX + " Y: " + startingY + " has nothing to its left, or is in the left most column");
                up = true;
            } else  {
                //System.out.println("Obtainable at X: " + startingX + " Y: " + startingY + " has something to its left");
            }
            if (startingY == gridSize || grid[startingX][startingY +  1] != "path" && grid[startingX][startingY +  1] != "buff" && grid[startingX][startingY +  1] != "treasure"){
                //System.out.println("Obtainable at X: " + startingX + " Y: " + startingY + " has  nothing below, or is in the bottom most row");
                right = true;
            } else  {
                //System.out.println("Obtainable at X: " + startingX + " Y: " + startingY + " has something below");
            }
            if (startingY == 0 || grid[startingX][startingY -  1] != "path" && grid[startingX][startingY -  1] != "buff" && grid[startingX][startingY -  1] != "treasure"){
                //System.out.println("Obtainable at X: " + startingX + " Y: " + startingY + " has nothing above, or is in the highest row");
                left = true;
            } else  {
                //System.out.println("Obtainable at X: " + startingX + " Y: " + startingY + " has something above");
            }
            if (up == true && down == true && left == true && right == true){
                System.out.println("Stray Obtainable at X: " + startingX + " Y: " + startingY);
                System.out.println("Re-placing Walls");
                obtainPathX.clear(); //clears all prior x locations stored
                obtainPathY.clear(); //clears all prior y locations stored
                //System.out.println(obtainPathX);
                //System.out.println(obtainPathY);
                replaced++;
                AssignPath(startingX, startingY);
                //System.out.println("X: " + startingX  + " Y: " + startingY);
                System.out.println(obtainPathX);
                System.out.println(obtainPathY);
                try { 
                    int wallX = obtainPathX.get(replaced);
                    int wallY = obtainPathY.get(replaced);
                    for (int l = 0; l<5; l++){
                        ConstructPaths(wallX, wallY);
                    }
                } catch (Exception e){
                    System.out.println(e);
                }
            }
        }
        //ConstructPaths(x,y);
        for (int i = 0; i < gridSize; i++){ // goes through entire x axis
            for (int t = 0; t < gridSize; t++){ // goes through entire y axis
                if (grid[i][t] == "null"){ // if there is nothing there
                    grid[i][t] = "wall"; // it becomes a wall
                }
            }   
        }
        //System.out.println(obtainPathX);
        //System.out.println(obtainPathY);
        DrawGame(); // displays the map
    }
    public void ConstructPaths(int x, int y){// will place pathss everywhere according to the ammount of paths needed.
        boolean up; // true means you can move, false means you cant
        boolean down; // true means you can move, false means you cant
        boolean left; // true means you can move, false means you cant
        boolean right; // true means you can move, false means you cant
        int checkingX = x + 1; //i realize slightly too late that this variable isnt exactly required.. 
        if (grid[checkingX][y] == "BORDER" || grid[checkingX][y] == "treasure" || grid[checkingX][y] == "buff" || grid[checkingX][y] == "path"){ // checking if moving right makes you hit the border
            right = false;
        } else {
            right = true;
        }
        checkingX = x - 1;
        if (checkingX <= -1  || grid[checkingX][y] == "treasure" || grid[checkingX][y] == "buff" || grid[checkingX][y] == "path"){ // checking if moving left makes you leave the grid
            left = false;
        } else {
            left = true;
        }
        int checkingY = y + 1;
         if (grid[x][checkingY] == "BORDER" || grid[x][checkingY] == "treasure" || grid[x][checkingY] == "buff" || grid[x][checkingY] == "path"){ // checking if moving down makes you hit the border
            down = false;
        } else {
            down = true;
        }
        checkingY = y - 1;
        if (checkingY <= -1 || grid[x][checkingY] == "treasure" || grid[x][checkingY] == "buff" || grid[x][checkingY] == "path"){ // checking if moving up makes you leave the grid
            up = false;
        } else {
            up = true;
        }
        DirectionRandomizer(up, down, left, right, x, y);
    }
    public void AssignPath(int x,int y){ // checks surroundings, and if branching out will hit any of them, and then tells the initial calculation to stay away from branching to that side
        boolean up; // true means you can move, false means you cant
        boolean down; // true means you can move, false means you cant
        boolean left; // true means you can move, false means you cant
        boolean right; // true means you can move, false means you cant
        int checkingX = x + 1;
        if (grid[checkingX][y] == "BORDER" || grid[checkingX][y] == "treasure" || grid[checkingX][y] == "buff" || grid[checkingX][y] == "path"){ // checking if moving right makes you hit the border
            right = false;
        } else {
            right = true;
        }
        checkingX = x - 1;
        if (checkingX <= -1  || grid[checkingX][y] == "treasure" || grid[checkingX][y] == "buff" || grid[checkingX][y] == "path"){ // checking if moving left makes you leave the grid
            left = false;
        } else {
            left = true;
        }
        int checkingY = y + 1;
         if (grid[x][checkingY] == "BORDER" || grid[x][checkingY] == "treasure" || grid[x][checkingY] == "buff" || grid[x][checkingY] == "path"){ // checking if moving down makes you hit the border
            down = false;
        } else {
            down = true;
        }
        checkingY = y - 1;
        if (checkingY <= -1 || grid[x][checkingY] == "treasure" || grid[x][checkingY] == "buff" || grid[x][checkingY] == "path"){ // checking if moving up makes you leave the grid
            up = false;
        } else {
            up = true;
        }
        /**
        System.out.println("Obtainable at X: " + x + " Y: " + y);
        System.out.println("UP: " + up);
        System.out.println("DOWN: " + down);
        System.out.println("LEFT: " + left);
        System.out.println("RIGHT: " + right);
        System.out.println("----------------------");
        **/
        DirectionRandomizer(up, down, left, right, x, y);
    }
    public void DirectionRandomizer(boolean up, boolean down, boolean left, boolean right, int x, int y){
        int direction = rng.nextInt(3);
        if (direction == 0){ // up
            //System.out.println("Going up");
            if (up == false){
                //System.out.println("Can't place path above");
                return;
            } else {
                PlacePath(x, (y-1));
                //System.out.println("Path placed" + x + (y-1));
            }
        } else if (direction == 1){ // down
            //System.out.println("Going down");
            if (down == false){
                //System.out.println("Can't place path below");
                return;
            } else {
                PlacePath(x, (y+1));
                //System.out.println("Path Placed" + x + (y+1));
            }
        } else if (direction == 2){ //  left
            //System.out.println("Going left");
            if (left == false){
                //System.out.println("Can't place path left");
                return;
            } else {
                PlacePath((x-1), y);
                //System.out.println("Path Placed" + (x-1) + y);
            }
        } else if (direction == 3){ // right
            //System.out.println("Going right");
            if (right == false){
                //System.out.println("Can't place path right");
                return;
            } else {
                PlacePath((x+1), y);
                //System.out.println("Path Placed" + (x+1) + y);
            }
        }
    }
    public void PlacePath(int x, int y){
        grid[x][y] = "path";
        obtainPathX.add(x);
        obtainPathY.add(y);
        return;
    }
    
    public void DrawGame(){
        //System.out.println("\f");
        for (int i = 0; i < border; i++){ // changes whats being printed for the y axis
            for (int t = 0; t < border; t++){ // changes whats being printed for the x axis
                if (grid[t][i] == "path"){
                    System.out.print("██"); // prints what should be where
                } else if (grid[t][i] == "treasure"){
                    System.out.print("▓▓"); // prints what should be where
                } else if (grid[t][i] == "buff"){
                    System.out.print("▒▒"); // prints what should be where
                } else if (grid[t][i] == "wall"){
                    System.out.print("░░"); // prints what should be where
                }
            }
            System.out.println(""); // next line
        }
        //System.out.println(obtainLocationX);
        //System.out.println(obtainLocationY);
    }
}
