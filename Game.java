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
    int gridSize = 0; // how big the playable map is
    int border; // where the border stands (one larger than playable map)
    int paths; // how many paths are required to make every area accessable
    int replaced = -1;
    int obtainables;
    //int testing = 0; // testing variable
    //int checkingSS; // also testing variable
    // misc variables
    Scanner input = new Scanner(System.in); // variable to get keyboard inputs.
    String breaker = "↓--------------------↓"; // just to split up text to make it more readable
    Random rng = new Random(); // rng.nextInt(gridSize);
    public String grid[][] = new String[100][100]; // size is set to maximum size, however not all of it will be used
    ArrayList<Integer> obtainLocationX = new ArrayList<Integer>();
    ArrayList<Integer> obtainLocationY = new ArrayList<Integer>();
    ArrayList<Integer> obtainPathX = new ArrayList<Integer>();
    ArrayList<Integer> obtainPathY = new ArrayList<Integer>();
    public ArrayList<String> saveFile = new ArrayList<String>();
    public Game(){
        obtainLocationX.clear(); //clears all prior x locations stored
        obtainLocationY.clear(); //clears all prior y locations stored
        obtainPathX.clear(); //clears all prior x locations stored
        obtainPathY.clear(); //clears all prior y locations stored
        Introduction(0);
        //testing++;
        //System.out.println(testing);
        return;
    }
    public void Introduction(int returning){
        int choice;
        System.out.println("\f");
        if (returning == 1){
            System.out.println("Please set the map parameters through: (2) Change Parameters");
        } else if (returning == 2){
            System.out.println("Map parameters Saved");
        } else if (returning == 5){
            System.out.println("Please enter a number according to the list");
        }
        System.out.println("<======Map Builder======>");
        System.out.println("Worse than the other guys, don't get your hopes up");
        System.out.println("<===What do you want to do?===>");
        System.out.println("(1) Create Map");
        System.out.println("(2) Change Parameters");
        System.out.println("(3) Exit");
        try {
            choice = input.nextInt();
            if (choice == 1){
                if (gridSize == 0){
                    Introduction(1);
                } else {
                    CreateMap();
                }
            } else if (choice == 2){
                InitializeBuffs(0);
            } else if (choice == 3){
                System.exit(0);
            } else {
                input.nextLine();
                Introduction(5);
            }
        } catch (Exception e){
            input.nextLine();
            Introduction(5);
        }
    }
    public void InitializeBuffs(int returning){
        System.out.println("\f");
        //testing++;
        //System.out.println(testing);
        // sets parameters for the map based on what the player wants.
        /**
        if (checkingSS  == 69){
            System.out.println("Buffs coming from surrounding Check");
        } else {
            System.out.println("buff from somewhere else");
        }
        **/
        System.out.println("How many buffs do you want on the map? Max: 10");
        if (returning == 1){
            System.out.println("Plese enter a number less than 10 and greater than 0");
            input.nextLine();
        } else if (returning == 2){
            System.out.println("Plese enter a numerical value");
            input.nextLine();
        }
        try {
            buffs = 0;
            buffs = input.nextInt();
            if (buffs > 10 || buffs < 1){
                InitializeBuffs(1);
                return;
            } else {
                InitializeTreasure(0);
                return;
            }
        } catch (Exception e){
            InitializeBuffs(2);
            return;
        }
    }
    public void InitializeTreasure(int returning){
        System.out.println("How many obtainable treasures do you want on the map? Max: 10");
        if (returning == 1){
            System.out.println("Plese enter a number less than 10 and greater than 0");
            input.nextLine();
        } else if (returning == 2){
            System.out.println("Plese enter a numerical value");
            input.nextLine();
        }
        try {
            treasure = input.nextInt();
            if (treasure > 10|| treasure < 1){
                InitializeTreasure(1);
                return;
            }
        } catch (Exception e){
            InitializeTreasure(2);
            return;
        }
        obtainables = buffs + treasure;
        gridSize = obtainables * 2;
        border = gridSize + 1;
        paths = 4;
        Introduction(2);
        return;
    }
    public void CreateMap(){
        //testing++;
        //System.out.println(testing);
        //System.out.println("Create Grid + Add Buffs");
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
        CreateBorders();
    }
    public void CreateBorders(){
        //testing++;
        //System.out.println(testing);
        //System.out.println("Create Borders");
        for (int i = 0; i < border; i++){ // spawns border along the x axis
            grid[i][gridSize] = "BORDER";
            //System.out.println("Placing border at " + i + " : " + border);
        }
        for (int i = 0; i < border; i++){ // spawns border on the y axis
            grid[gridSize][i] = "BORDER";
            //System.out.println("Placing border at " + i + " : " + border);
        }
        SpawnLand();
    }
    public void SpawnLand(){ // just saying you will need to use your imagination a bit to see a map in all this LOL.
        //testing++;
        //System.out.println(testing);
        //System.out.println("Initial Set Of Paths");
        //this is the annoying part that creates paths that bridge off of the obtainables making them all acessable.
        // BIGGEST PROBLEM, MAKING PATH SYSTEM THAT WORKS. LOTS OF TRIAL AND ERROR INCOMING
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
                //System.out.println(obtainPathX);
                //System.out.println(obtainPathY);
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
        for (int i = 0; i < border; i++){ // changes whats being printed for the y axis
            for (int t = 0; t < border; t++){ // changes whats being printed for the x axis
                if (grid[i][t] == "null"){
                    grid[i][t] = "wall";
                }
            }
        }
        //ConstructPaths(x,y);
        FillHoles();
    }
    public void FillHoles(){
        //testing++;
        //System.out.println(testing);
        //System.out.println("Fill Holes");
        try {
            for (int i = 1; i < (border-1); i++){ // changes whats being printed for the y axis
                for (int t = 1; t < (border-1); t++){ // changes whats being printed for the x axis
                    if (grid[i][t] == "wall"){
                        CheckSurroundings(i,t);
                    }
                }
            }
        } catch (Exception e){
            System.out.println(e + " BROKEN");
        }
        DrawGame(0);
    }
    public void CheckSurroundings(int x, int y){
        /**
        if (grid[x][y] == "wall"){
            System.out.println("Checking wall X: " + x + " Y: " + y);
        } else if (grid[x][y] == "path"){
            System.out.println("Checking path X: " + x + " Y: " + y);
        }
        **/
        int checkingX;
        int checkingY;
        boolean up; // true means you can move, false means you cant
        boolean left; // true means you can move, false means you cant
        boolean down; // true means you can move, false means you cant
        boolean right; // true means you can move, false means you cant
        checkingY = y + 1;
        if (grid[x][checkingY] == "path" || grid[x][checkingY]== "buff" || grid[x][checkingY]== "treasure"){ // checking if moving down makes you hit the border
            down = true;
            //System.out.println("checked  Down, something below, continuing search.");
            checkingY = y - 1;
            if (grid[x][checkingY] == "path" || grid[x][checkingY]== "buff" || grid[x][checkingY]== "treasure"){
                up = true;
                //System.out.println("checked up, something above, continuing search.");
                checkingX = x + 1;
                if (grid[checkingX][y] == "path" || grid[checkingX][y]== "buff" || grid[checkingX][y]== "treasure"){
                    right = true;
                    //System.out.println("Checked right, something right, continuting search");
                    checkingX = x - 1;
                    if (grid[checkingX][y]== "path" || grid[checkingX][y]== "buff" || grid[checkingX][y]== "treasure"){
                        left = true;
                        //System.out.println("Checked left, something left, fully surroudned, replacing");
                        grid[x][y] = "path";
                        return;
                    } else {
                        //System.out.println("Checked left, nothing left");
                        return;
                    }
                } else {
                    //System.out.println("Checked right, nothing right");
                    return;
                }
            } else {
                //System.out.println("Checked Above, nothing above");
                return;
            }
        } else {
            down = false;
            //System.out.println("Checked Down, nothing below");
            return;
        }
    }
    public void ConstructPaths(int x, int y){// will place pathss everywhere according to the ammount of paths needed.
        //testing++;
        //System.out.println(testing);
        //System.out.println("Construct Path");
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
        //testing++;
        //System.out.println(testing);
        //System.out.println("Assign Path");
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
        //testing++;
        //System.out.println(testing);
        //System.out.println("DirectionRandomizer");
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
        //testing++;
        //System.out.println(testing);
        //System.out.println("placed wall X " + x + " Y: " + y); 
        grid[x][y] = "path";
        obtainPathX.add(x);
        obtainPathY.add(y);
        return;
    }
    public void DrawGame(int returning){ // these "images" are actually unicode characters. and I don't need any special licenceing or anything to use them. Avoiding intellectual property
        int choice;
        System.out.println("\f");
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
        if (returning == 5){
            System.out.println("Please enter a number according to the list");
        }
        System.out.println("What do you want to do now?");
        System.out.println(" (1) Return to Main Menu (this will reset your map)");
        System.out.println(" (2) Quit");
        input.nextLine();
        try {
            choice = input.nextInt();
            if (choice == 1){ // return to main menu
                obtainLocationX.clear();
                obtainLocationY.clear();
                obtainPathX.clear();
                obtainPathY.clear();
                for (int i = 0; i < border; i++){ // changes whats being printed for the y axis
                    for (int t = 0; t < border; t++){ // changes whats being printed for the x axis
                        grid[i][t] = null;
                    }
                }
                input.nextLine();
                Introduction(0);
                return;
            } else if (choice == 2){ // exit
                input.nextLine();
                System.exit(0);
            } else {
                input.nextLine();
                DrawGame(5);
            }
        } catch (Exception e){
            input.nextLine();
            DrawGame(5);
        }
    }
}
