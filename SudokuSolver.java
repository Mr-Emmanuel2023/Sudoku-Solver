import java.util.Arrays;

import javax.swing.JOptionPane;

public class SudokuSolver {

    public static boolean breakNow = false;
    public static boolean solved = false;
    private static SudokuGrid sudokuGrid;
    private static int[][] grid;

    public static int[][] getGrid() {
        return grid;
    }

    SudokuSolver(SudokuGrid sudokuGrid) {
        SudokuSolver.sudokuGrid = sudokuGrid;


        grid = sudokuGrid.getGrid();
//        System.out.println((grid));

//        Cell cellExample = new Cell(12, 15);
//
//        int x = cellExample.i();
//        cellExample.j();
//


    }

//    public boolean getSolved() {
//        return this.solved;
//    }

    public static int[][] solve() {
        displayBoard();

        if (solver()) {

            if(breakNow) {
                breakNow = false;

                return new int[9][9];
            }


            System.out.println("A");
            displayBoard();
        }


        solved = true;

        return grid;
    }



    public static void setGrid(SudokuGrid sudokuGrid) {
        grid = sudokuGrid.getGrid();


    }

    public static void setGrid(int[][] grid) {
        SudokuSolver.grid = grid;
    }

    public static int[] emptyCellsExist() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) {
                    return new int[] { i, j };
                }
            }
        }
        return null;
    }
    // display board
    public static void displayBoard() {
        for (int[] row : grid) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }

    public static boolean validNumberCheck(int num, int i, int j) {
        // Checking vertically by iterating through each coloumn
        for (int row = 0; row < 9; row++) {
            if (grid[row][j] == num) {
                return false;
            }
        }
        // Checking horizontally by iterating through each column
        for (int col = 0; col < 9; col++) {
            if (grid[i][col] == num) {
                return false;
            }
        }
        // check each box (3x3 )
        int gridRow = (i / 3) * 3;
        int gridCol = (j / 3) * 3;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (grid[gridRow + x][gridCol + y] == num) {
                    return false;
                }
            }
        }
        // if all test above arent false, return true meaning the number is valid
        return true;
    }


    public static boolean solver() {
        int[] cell = emptyCellsExist();
        if (cell == null) {
            return true;
        }
        int i = cell[0], j = cell[1];
        for (int num = 1; num <= 9; num++) {
            if (validNumberCheck(num, i, j)) {
                grid[i][j] = num;
//                backtracking method which uses recursion

                if(breakNow) {
                    return false;
                }

                if (solver()) {
                    return true;
                }
                //changes previous cell back to 0 if all possible combinations arent valid (backtracking)
                grid[i][j] = 0;
            }
        }
        return false;
    }
}
