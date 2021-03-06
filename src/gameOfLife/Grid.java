package gameOfLife;

import java.util.Random;

/**
 * Grid representing the data of board in Array
 *
 * @author Narawish Sangsiriwut
 */
public class Grid {
    final static int ALIVE = 1;
    final static int DEAD = 0;
    final int width;
    final int height;
    final boolean loop;
    int[][] grid;
    Random rand = new Random();
    int day = 0;

    /**
     * Initialize new board.
     */
    public Grid(int width, int height, boolean loop) {
        this.width = width;
        this.height = height;
        this.loop = loop;
        this.grid = new int[width][height];
    }

    /**
     * Print view of board in console.
     */
    public void printGrid() {
        StringBuilder line = new StringBuilder();
        System.out.println("---");
        for (int y = 0; y < height; y++) {
            line.delete(0, line.length());
            line.append("|");
            for (int x = 0; x < width; x++) {
                if (this.grid[x][y] == DEAD) {
                    line.append("*");
                } else {
                    line.append(".");
                }
            }
            line.append("|");
            System.out.println(line.toString());
        }
        System.out.println("---\n");
    }

    /**
     * Set cell to Alive
     */
    public void setAlive(int x, int y) {
        setState(x, y, ALIVE);
    }

    /**
     * Set cell to Dead
     */
    public void setDead(int x, int y) {
        setState(x, y, DEAD);
    }

    /**
     * Set the cell.
     */
    public void setState(int x, int y, int state) {
        if (x < this.width && y < this.height && x >= 0 && y >= 0)
            this.grid[x][y] = state;
    }

    /**
     * Count the surrounding cell(not loop).
     *
     * @return number of alive neighbor cells
     */
    public int countNeighbors(int x, int y) {
        int sum = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int col = (x + i);
                int row = (y + j);
                if (col >= 0 && col < width && row >= 0 && row < height)
                    sum += this.grid[col][row];
            }
        }
        sum -= this.grid[x][y];
        return sum;
    }

    /**
     * Count the surrounding cell(loop).
     *
     * @return number of alive neighbor cells
     */
    public int countNeighborsLoop(int x, int y) {
        int sum = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int col = (x + i + this.width) % this.width;
                int row = (y + j + this.height) % this.height;
                sum += this.grid[col][row];
            }
        }
        sum -= this.grid[x][y];
        return sum;
    }

    /**
     * Apply the rule on board then change cell on the board.
     */
    public void next() {
        int[][] next = new int[this.width][this.height];
        // Compute next based on grid
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                // Count live neighbors!
                int neighbors;
                if (this.loop)
                    neighbors = countNeighborsLoop(i, j);
                else
                    neighbors = countNeighbors(i, j);
                int state = this.grid[i][j];
                if (state == 0 && neighbors == 3) {
                    next[i][j] = ALIVE;
                } else if (state == 1 && (neighbors < 2 || neighbors > 3)) {
                    next[i][j] = DEAD;
                } else {
                    next[i][j] = state;
                }
            }
        }
        this.day++;
        this.grid = next;
    }

    /**
     * Random cell on board.
     */
    public void randomGrid() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.grid[i][j] = rand.nextInt(2);
            }
        }
        day = 0;
    }

    /**
     * Get all alive cell.
     *
     * @return number of alive cell
     */
    public int getAlive() {
        int alive = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                alive += this.grid[i][j];
            }
        }
        return alive;
    }

    /**
     * Clear all alive cell in grid.
     */
    public void clearGrid() {
        this.grid = new int[width][height];
        day = 0;
    }
}
