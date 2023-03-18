package Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author TMG8047KG
 * @version 1.0.0
 * */
public class Terminal {
    private JPanel MainPanel;
    private JTextArea textArea;
    private JScrollPane scroll;
    private final int height;
    private final int width;
    private boolean editable;
    private Color backgroundColor = new Color(0, 0,0);
    private Color fontColor = new Color(0, 220, 30);
    private Font font = new Font("Courier New", Font.BOLD,16);
    private final List<String> input = new ArrayList<>();
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    //TODO: Add option for multiple schedules
    TerminalScheduleUpdate scheduleUpdate;
    int initialDelay = 0;
    int delay = 500;
    private char[][] charMatrix;
    private String[][] stringMatrix;
    private int[][] intMatrix;

    //=======================
    //     Constructor
    //=======================

    /**
     * Defines the size of the console
     * @param height
     * The height in pixels
     * @param width
     * The width in pixels*/
    public Terminal(int height, int width){
        this.height = height;
        this.width = width;
    }

    //=======================
    //    Update Method
    //=======================

    /**
     * Sets the schedule (periodic execution) for the terminal
     * Schedules default execute times are on every second executes 2 times (2t/s)
     * This executes everything in {@link TerminalScheduleUpdate#Update()} indefinitely, until stopped
     * @param scheduleUpdate
     * adds new {@link TerminalScheduleUpdate}
     * @see "{@link #}"
     * */
    public void setSchedule(TerminalScheduleUpdate scheduleUpdate){
        this.scheduleUpdate = scheduleUpdate;
    }


    /**
     * Returns the set schedule
     * @return Returns schedule
     * */
    public TerminalScheduleUpdate getSchedule(){
        return scheduleUpdate;
    }


    /**
     * Sets the delay between executions in the schedule (in milliseconds)
     * @param delay
     * delay in milliseconds
     * */
    public void setDelay(int delay){
        this.delay = delay;
    }


    /**
     * Sets the initial delay for the schedule (in milliseconds)
     * @param initialDelay
     * delay in milliseconds
     * */
    public void setInitialDelay(int initialDelay){
        this.initialDelay = initialDelay;
    }


    /**
     * Starts the schedule
     * Uses on one CPU thread
     * */
    public void start(){
        executor.scheduleWithFixedDelay(scheduleUpdate::Update, initialDelay, delay, TimeUnit.MILLISECONDS);
    }


    /**
     * Stops the schedule
     * */
    public void stop(){
        executor.shutdown();
    }

    //=======================
    //     Key listener
    //=======================

    /**
     * Adds {@link KeyListener} to the Terminal
     * You have to build your KeyListener before you add it
     * @param listener
     * adds {@link KeyListener}
     * @see KeyListener
     * */
    public void addKeyListener(KeyListener listener){
        textArea.addKeyListener(listener);
    }

    /**
     * Removes given {@link KeyListener}
     * @param listener
     * Removes listener
     * */
    public void removeKeyListener(KeyListener listener){
        textArea.removeKeyListener(listener);
    }

    //=======================

    /**
     * Sets whether you can type in the console
     * @param editable
     * Enables/Disables editing
     * */
    public void setEditable(boolean editable){
        this.editable = editable;
    }

    //=======================
    // Matrix implementation
    //=======================


    /**
     * Sets matrix that can be printed
     * @param intMatrix
     * set matrix
     * @see #printMatrix()
     * */
    public void setMatix(int[][] intMatrix){
        this.intMatrix = intMatrix;
    }

    /**
     * Sets matrix that can be printed
     * @param charMatrix
     * set matrix
     * @see #printMatrix()
     * */
    public void setMatix(char[][] charMatrix){
        this.charMatrix = charMatrix;
    }

    /**
     * Sets matrix that can be printed
     * @param stringMatrix
     * set matrix
     * @see #printMatrix()
     * */
    public void setMatix(String[][] stringMatrix){
        this.stringMatrix = stringMatrix;
    }

    /**
     * Prints the set matrix
     * @see "all methods for setting a matrix here:
     * - {@link #setMatix(int[][])}
     * - {@link #setMatix(char[][])}
     * - {@link #setMatix(String[][])}"
     * */
    public void printMatrix(){
        if(charMatrix != null){
            for (char[] matrix : charMatrix) {
                for (int b = 0; b < charMatrix[0].length; b++) {
                    print(matrix[b] + " ");
                }
                println();
            }
        }
        if(stringMatrix != null){
            for (String[] matrix : stringMatrix) {
                for (int b = 0; b < stringMatrix[0].length; b++) {
                    print(matrix[b] + " ");
                }
                println();
            }
        }
        if(intMatrix != null){
            for (int[] matrix : intMatrix) {
                for (int b = 0; b < intMatrix[0].length; b++) {
                    print(matrix[b] + " ");
                }
                println();
            }
        }
    }

    /**
     * Gets the int matrix if set
     * @return int matrix
     * */
    public int[][] getIntMatrix(){
        return intMatrix;
    }

    /**
     * Gets the char matrix if set
     * @return char matrix
     * */
    public char[][] getCharMatrix(){
        return charMatrix;
    }

    /**
     * Gets the string matrix if set
     * @return string matrix
     * */
    public String[][] getStringMatrix(){
        return stringMatrix;
    }

    //=======================
    // Basic console methods
    //=======================

    /**
     * Sets or overrides all the text shown in the terminal
     * @param text
     * sets text
     * */
    public void setText(String text){
        textArea.setText(text);
    }

    /**
     * Outputs {@link String} to the terminal
     * @param text
     * output text
     * */
    public void print(String text){
        textArea.append(text);
    }

    /**
     * Outputs {@link String} to the terminal and makes a new line
     * */
    public void println(){
        textArea.append("\n");
    }

    /**
     * Outputs {@link String} to the terminal and makes a new line
     * @param text
     * output text
     * */
    public void println(String text){
        textArea.append(text + "\n");
    }

    /**
     * Clears the terminal by setting the text to nothing
     * */
    public void clear(){
        textArea.setText(null);
    }

    /**
     * Reads the last line in the terminal
     * */
    public String readLine() {
        readEvent();
        try {
            synchronized (input){
                while (input.isEmpty())
                    input.wait();

                return input.remove(0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private void readEvent(){
        KeyListener readLine = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    synchronized (input){
                        String[] text = textArea.getText().split("\\n");
                        int lastLine = text.length-1;
                        //TODO: make it not to get the last line if nothing is typed
                        input.add(text[lastLine]);
                        input.notify();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
        textArea.addKeyListener(readLine);
    }

    /**
     * Sets the {@link Color} of the terminal background
     * @param backgroundColor
     * set the background {@link Color}
     * */
    public void setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
    }

    /**
     * Sets the {@link Color} of the terminal font
     * @param fontColor
     * sets the font {@link Color}
     * */
    public void setFontColor(Color fontColor){
        this.fontColor = fontColor;
    }

    /**
     * Sets the terminal {@link Font}
     * @param font
     * sets {@link Font}*/
    public void setFont(Font font){
        this.font = font;
    }

    private void createUIComponents() {
        textArea.setBackground(backgroundColor);
        textArea.setForeground(fontColor);
        textArea.setFont(font);
        textArea.setEditable(editable);
    }

    /**
     * Builds the terminal
     * */
    public void build(){
        createUIComponents();

        JFrame frame = new JFrame("Console");
        frame.setSize(width, height);
        //TODO: add scroll
        frame.setContentPane(textArea);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
