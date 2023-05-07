
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
    // misc variables
    Scanner input = new Scanner(System.in);
    String breaker = "↓--------------------↓";
    Random rng = new Random(); // rng.nextInt(gridSize);
    public String grid[][] = new String[100][100];
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
        System.out.println("Creating a " + gridSize + "x" + gridSize + " map with " + buffs + " buffs and " + treasure + " treasures");
        // makes entire grid null
        for (int i = 0; i < gridSize; i++){ // changes whats being printed for the y axis
            for (int t = 0; t < gridSize; t++){ // changes whats being printed for the x axis
                int random = rng.nextInt(2);
                System.out.println(random);
                if (random == 0){
                    grid[t][i] = "nothing";
                } else if (random == 1){
                    grid[t][i] = "something";
                }
            }
        }
        DrawGame();
    }
    public void CreateMap(){
        for (int i = 0; i < buffs; i++){ //
            
        }
    }
    public void DrawGame(){
        for (int i = 0; i < gridSize; i++){ // changes whats being printed for the y axis
            for (int t = 0; t < gridSize; t++){ // changes whats being printed for the x axis
                if (grid[t][i] == "nothing"){
                    System.out.print("██"); // prints what should be where
                    // ░░
                } else if (grid[t][i] == "something"){
                    System.out.print("░░");
                }
            }
            System.out.println(""); // next line
        }
    }
}
