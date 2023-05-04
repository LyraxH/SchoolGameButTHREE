
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
    String grid[][];
    int buffs;
    int treasure;
    int gridSize;
    // misc variables
    Scanner input = new Scanner(System.in);
    String breaker = "↓--------------------↓";
    Random rng = new Random();
    public Game(){
         InitializeGame();
    }
    public void InitializeGame(){
        System.out.println("How many buffs do you want on the map? Recommendation: 1-2");
        buffs = input.nextInt();
        System.out.println("How many obtainable treasures do you want on the map? Recommendation: 2-4");
        treasure = input.nextInt();
        gridSize = (buffs + treasure) * 2;
        System.out.println("Creating a " + gridSize + "x" + gridSize + " map with " + buffs + " buffs and " + treasure + " treasures");
        // makes entire grid null
        for (int i = 0; i < gridSize; i++){ // changes y axis
            for (int t = 0; t < gridSize; t++){ // changes x axis
                grid[t][i] = "fuckignsomething";
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
                if (grid[t][i] == "typenull"){
                    System.out.print("typenull"); // prints what should be where
                }
            }
            System.out.println(""); // next line
        }
    }
}
