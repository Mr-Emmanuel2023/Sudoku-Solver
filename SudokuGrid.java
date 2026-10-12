import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Thread.State;
import java.time.LocalTime;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

public class SudokuGrid extends JFrame implements ActionListener {

    public final int numCells = 9;

    private JPanel Board;
    private JButton[][] Grid;
//    private JButton[][] Num;
    private JButton[] newNum;
    private JButton Reset;
    private JButton Button;
    private JPanel Numbers;
    private JPanel ControlCentre;
    private int currentNumberInt;
    private JLabel currentNumberSelected;
    private JLabel timerLabel;
    private static SudokuSolver sudokuSolver;
    private LocalTime timeDifference;
    private Thread timerThread;

    public void redrawGrid() {
        int[][] grid = SudokuSolver.getGrid();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != 0) {
                    Grid[i][j].setText(grid[i][j] + "");
                } else {
                    Grid[i][j].setText("");
                }
            }
        }

    }

    public SudokuGrid() {
        Color gry = new Color(220, 220, 220);
        this.setSize(800, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        ImageIcon icon = new ImageIcon("M:/Sudoku Icon.png");
        this.setIconImage(icon.getImage());



        Numbers = new JPanel(new GridLayout(2, 5));

        newNum = new JButton[10];

        for (int i = 0; i < 10; i++) {
            final int currentNumber = i;

            if (i == 0) {
                newNum[i] = new JButton("");
            } else {
                newNum[i] = new JButton(i + "");
            }
            Numbers.add(newNum[i]);

            newNum[i].setBackground(gry);
            newNum[i].setFocusable(false);

            newNum[i].addActionListener(actionevent -> {
                currentNumberInt = currentNumber;
                currentNumberSelected.setText("Current Number: " + currentNumber);
            });

        }

        ControlCentre = new JPanel();
        ControlCentre.setLayout(new BoxLayout(ControlCentre, BoxLayout.Y_AXIS));
//        Button = new JButton("               Solve               ");
        Button = new JButton("Solve");
        Button.setMaximumSize(new Dimension(200, 50));
        Button.setBackground(Color.WHITE);
        Button.setFocusable(false);
        Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        Button.addActionListener(actionevent -> {
//            different thread so rest of code operates
//            new Thread(()-> {
//                LocalTime startTime = LocalTime.now();
//
//                while(true) {
//                    LocalTime endTime = LocalTime.now();
//
//                    timeDifference = endTime.minusSeconds(startTime.getSecond());
//
//                    timerLabel.setText(timeDifference.getSecond()  + "");
//                }
//
//            }).start();

            SudokuSolver.setGrid(this);

            if (timerThread != null) {
                if (timerThread.getState() != State.TERMINATED) {
                    timerThread.interrupt();
                }
            }

            timerThread = new Thread(() -> {
                try {
                    Thread.sleep(4000);

                    if (SudokuSolver.solved == false) {

                        throw new SudokuException("Grid not solveable");

                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (SudokuException e) {
                    // TODO Auto-generated catch block
                    SudokuSolver.breakNow = true;

                    for (int x = 0; x < numCells; x++) {
                        for (int y = 0; y < numCells; y++) {
                            Grid[x][y].setText("");
//                            this.redrawGrid();
                        }

                    }
//                    this.redrawGrid();

                    JOptionPane.showMessageDialog(null, "Unsolvable method inputted", "Error",
                            JOptionPane.ERROR_MESSAGE);

                    SudokuSolver.setGrid(new int[9][9]);

                    this.redrawGrid();

//                    System.out.println("AAAA");

//                    this.dispose();
//                    new SudokuGrid();
                    e.printStackTrace();
                }
//                this.dispose();
//                new SudokuGrid();
//                e.printStackTrace();
            });

            try {

                if (this.IsGridFull()) {
                    throw new SudokuException("Grid is full");
                }

                if (this.IsGridEmpty()) {
                    throw new SudokuException("Grid is empty");
                }


            } catch (SudokuException e) {

                String reason = e.getMessage();
                JOptionPane.showMessageDialog(null, reason, "Error", JOptionPane.ERROR_MESSAGE);


                return;
            }

            timerThread.start();


            int[][] solvedGrid = SudokuSolver.solve();

            this.redrawGrid();


            System.out.println("Thread started");

        });
//        public class Solvable extends Exception{
//            PupilException(String ex)
//            {
//                super(ex);
//            }
//        }

        Reset = new JButton("RESET");
        Reset.setBackground(Color.WHITE);
        Reset.setFocusable(false);
        Reset.setMaximumSize(new Dimension(200, 50));
        Reset.setAlignmentX(CENTER_ALIGNMENT);
        Reset.addActionListener(actionevent -> {
            for (int x = 0; x < numCells; x++) {
                for (int y = 0; y < numCells; y++) {
                    SudokuSolver.setGrid(new int[9][9]);
                    this.redrawGrid();
                }
            }

        });

        ControlCentre.add(Reset);
        ControlCentre.add(Button);

        currentNumberSelected = new JLabel("Current Number: 0");
        currentNumberSelected.setAlignmentX(Component.CENTER_ALIGNMENT);


        ControlCentre.add(currentNumberSelected);

//        timerLabel = new JLabel("0.00");
//        ControlCentre.add(timerLabel);

        Board = new JPanel(new GridLayout(9, 9));
//        Board.setSize(new Dimension(500, 500));
        Grid = new JButton[9][9];

        for (int x = 0; x < numCells; x++) {
            for (int y = 0; y < numCells; y++) {
                Grid[x][y] = new JButton("");
                Board.add(Grid[x][y]);
                Grid[x][y].setBackground(Color.WHITE);

                final int row = x;
                final int col = y;

                Grid[x][y].setFocusable(false);



                Grid[x][y].addActionListener(actionevent -> {
                    if (currentNumberInt != 0) {
                        Grid[row][col].setText(currentNumberInt + "");
                    } else {
                        Grid[row][col].setText("");

                    }

                });

            }
        }


        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, ControlCentre, Numbers);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, Board, split);
        splitPane.setDividerLocation(500);
        splitPane.setEnabled(false);

        split.setDividerLocation(350);
        split.setEnabled(false);

        this.add(splitPane);

        this.setVisible(true);
    }

//    public void setTimerLabelText(String text) {
//        timerLabel.setText(text);
//    }

    public int[][] getGrid() {

        int[][] output = new int[numCells][numCells];

        for (int i = 0; i < numCells; i++) {
            for (int j = 0; j < numCells; j++) {
                String gridCellString = Grid[i][j].getText();

                if (gridCellString.equals("")) {
                    output[i][j] = 0;
                } else {
                    output[i][j] = Integer.parseInt(gridCellString);
                }

            }
        }

        return output;
    }

    public boolean IsGridFull() {
        int[][] grid = getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    return false;
                }

            }
        }
        return true;
    }

    public boolean IsGridEmpty() {
        int[][] grid = getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != 0) {
                    return false;
                }

            }
        }
        return true;
    }

//    public void Solvable(int[][] solvedGrid ) throws Exception {
//        if (timeDifference.toSecondOfDay() - 3 < LocalTime.now().toSecondOfDay() ) {
//            throw new Exception("Not solvable");
//
//        }
//
//    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                SudokuGrid.sudokuSolver = new SudokuSolver(new SudokuGrid());
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }
}
