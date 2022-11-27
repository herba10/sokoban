package walking;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;
public class WalkingLevels {
    static ConsoleColors colors = new ConsoleColors();
    static char PLAYER = 'P';
    static char CUBE = 'C';
    static char TARGET = 'T';
    static char WALL = 'W';
    static char FLOOR = 'F';
    static int playerX, playerY;
    static char direction;
    static Tile[][] levelTiles;
    static Level level;
    static final int LEVELS_AMOUNT = 5;
    static int actualLevel = 0;
    static ArrayList<String> levels = new ArrayList<>();
    static int targetsAmount, cubesAtTargets;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        AnsiConsole.systemInstall();
        System.out.println("JAVA SOKOBAN");
        for (int l = 0; l < LEVELS_AMOUNT; l++) {
            levels.add("levels/level" + l + ".txt");
        }
        menu();
    }

    public static void menu() {
        while (true) {
            try {
                cubesAtTargets = 0;
                System.out.println("enter level number, 0-" + (LEVELS_AMOUNT - 1));
                var in = input.next();
                actualLevel = Integer.parseInt(in);
                if (actualLevel >= 0 && actualLevel < LEVELS_AMOUNT) {
                    System.out.println("you chose: " + actualLevel);
                    startLevel();
                } else {
                    System.out.println("walking.Level " + actualLevel + " not available");
                }
            } catch (InputMismatchException|NumberFormatException e) {
                System.out.println("Invalid level number");
            }
        }
    }

    public static void startLevel() {
        level = new Level();
        level.getLevelInfo(levels.get(actualLevel));
        playerX = level.playerX;
        playerY = level.playerY;
        levelTiles = level.levelTiles;
        targetsAmount = level.targetsAmount;
        System.out.println("level" + actualLevel + " info:");
        System.out.println(level.getLevelInfo());
        draw(level);
    }

    public static void restartLevel() {
        System.out.println("RESTARTED");
        cubesAtTargets = 0;
        level = new Level();
        level.getLevelInfo(levels.get(actualLevel));
        playerX = level.playerX;
        playerY = level.playerY;
        levelTiles = level.levelTiles;
        targetsAmount = level.targetsAmount;
        draw(level);
    }

    public static void draw(Level level) {
        ansi().eraseScreen();
        for (int h = 0; h < level.height; h++) {
            for (int w = 0; w < level.width; w++) {
                switch (levelTiles[w][h].type) {
                    case 'P' -> System.out.print(ansi().bg(BLUE).a("  ").reset());
                    case 'F' -> System.out.print(ansi().bg(WHITE).a("  ").reset());
                    case 'T' -> System.out.print(ansi().bg(GREEN).a("  ").reset());
                    case 'C' -> System.out.print(ansi().bg(RED).a("  ").reset());
                    case 'W' -> System.out.print(ansi().bg(BLACK).a("  ").reset());
                }
            }
            System.out.println();
        }
        move();
    }

    public static void move() {
        System.out.println("enter your direction (w/a/s/d)");
        System.out.println("enter r to restart, m to go to menu");
            direction = input.next().toLowerCase().charAt(0);
            levelTiles[playerX][playerY].type = FLOOR;

            for (int h = 0; h < level.height; h++) {
                for (int w = 0; w < level.width; w++) {
                    if (levelTiles[w][h].type != CUBE && levelTiles[w][h].target) {
                        levelTiles[w][h].type = TARGET;
                    }
                }
            }

            switch (direction) {
                case 'r' -> restartLevel();
                case 'm' -> {
                    return;
                }
                case 'w' -> movePlayer(0, -1);
                case 's' -> movePlayer(0, 1);
                case 'a' -> movePlayer(-1, 0);
                case 'd' -> movePlayer(1, 0);
            }

            levelTiles[playerX][playerY].type = PLAYER;
            checkCubesPosition();
    }

    public static void checkCubesPosition() {
        for (int h = 0; h < level.height; h++) {
            for (int w = 0; w < level.width; w++) {
                if (levelTiles[w][h].type == CUBE && levelTiles[w][h].target) {
                    cubesAtTargets++;
                } else if (levelTiles[w][h].type == CUBE && !levelTiles[w][h].target) {
                    cubesAtTargets--;
                }
            }
        }
        if (cubesAtTargets < 0) {
            cubesAtTargets = 0;
        } else if (cubesAtTargets == targetsAmount) {
            System.out.println("you won this level!");
            menu();
        }
        draw(level);
    }

    public static void movePlayer(int x, int y) {
        if (levelTiles[playerX + x][playerY + y].type == CUBE && levelTiles[playerX + 2 * x][playerY + 2 * y].type != WALL) {
            levelTiles[playerX + 2 * x][playerY + 2 * y].type = CUBE;
            playerX += x;
            playerY += y;
        } else if (levelTiles[playerX + x][playerY + y].type != WALL && levelTiles[playerX + x][playerY + y].type != CUBE) {
            playerX += x;
            playerY += y;
        }
    }
}