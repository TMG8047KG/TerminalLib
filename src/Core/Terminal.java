package Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Builds an external terminal that is more intuitive and customizable
 * than the normal IDE terminal
 *
 * @author TMG8047KG
 * @version 1.0.1-beta
 */
public class Terminal {
    private final int height;
    private final int width;
    private final List<String> input = new ArrayList<>();
    private ScheduledExecutorService executorService;
    TerminalScheduleUpdate scheduleUpdate;
    int initialDelay = 0;
    int delay = 500;
    private String title = "Terminal";
    private JFrame frame;
    private JPanel MainPanel;
    private JTextArea textArea;
    private JScrollPane scroll;
    private boolean editable = true;
    private boolean resizeable = true;
    private boolean fullscreen = false;
    private Color backgroundColor = new Color(0, 0, 0);
    private Color fontColor = new Color(0, 220, 30);
    private int fontSize = 16;
    private Font font = new Font("Courier New", Font.BOLD, fontSize);
    private char[][] charMatrix;
    private String[][] stringMatrix;
    private int[][] intMatrix;

    //=======================
    //     Constructors
    //=======================

    /**
     * Declares the terminal
     * Default size 450x650 pixels
     */
    public Terminal() {
        this.height = 450;
        this.width = 650;
    }

    /**
     * Declares the terminal
     * And sets the title
     *
     * @param title Title
     */
    public Terminal(String title){
        this.title = title;
        this.height = 450;
        this.width = 650;
    }

    /**
     * Declares the Terminal
     * Defines the size of the Terminal
     *
     * @param height The height in pixels
     * @param width  The width in pixels
     */
    public Terminal(int height, int width) {
        this.height = height;
        this.width = width;
    }

    /**
     * Declares the terminal
     * Defines the size and the name of the Terminal
     *
     * @param title Title
     * @param height The height in pixels
     * @param width  The width in pixels
     */
    public Terminal(String title, int height, int width){
        this.title = title;
        this.height = height;
        this.width = width;
    }

    //=======================

    /**
     * Gets the window builder of the Terminal class on which
     * the terminal is created
     *
     * @return Returns the Terminal window builder
     */
    public JFrame getFrame(){
        return this.frame;
    }

    //=======================
    //    Update Method
    //=======================

    /**
     * Returns the set schedule
     *
     * @return Returns schedule
     */
    public TerminalScheduleUpdate getSchedule() {
        return scheduleUpdate;
    }

    /**
     * Sets the schedule (periodic execution) for the terminal
     * Schedules default execute times are on every second executes 2 times (2t/s)
     * This executes everything in {@link TerminalScheduleUpdate#Update()} indefinitely, until stopped
     * Starts the schedule on default
     *
     * @param scheduleUpdate adds new {@link TerminalScheduleUpdate}
     * @see "{@link #}"
     */
    public void setSchedule(TerminalScheduleUpdate scheduleUpdate) {
        this.scheduleUpdate = scheduleUpdate;
        start();
    }

    //TODO: add timer that executes methods in predefined time

    /**
     * Sets the delay between executions in the schedule (in milliseconds)
     *
     * @param delay delay in milliseconds
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }


    /**
     * Sets the initial delay for the schedule (in milliseconds)
     *
     * @param initialDelay delay in milliseconds
     */
    public void setInitialDelay(int initialDelay) {
        this.initialDelay = initialDelay;
    }


    /**
     * Starts the schedule
     * The update method is single threaded and executes by default every half a second
     * unless changed with the {@link #setDelay(int)} then the update method
     * will start with the given delay time
     */
    public void start() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(scheduleUpdate::Update, initialDelay, delay, TimeUnit.MILLISECONDS);
    }


    /**
     * Stops the schedule from running
     */
    public void stop(){
        executorService.shutdown();
    }

    //=======================
    //     Key listener
    //=======================

    /**
     * Adds {@link KeyListener} to the Terminal
     * You have to build your KeyListener before you add it
     *
     * @param listener adds {@link KeyListener}
     * @see KeyListener
     */
    public void addKeyListener(KeyListener listener) {
        textArea.addKeyListener(listener);
    }

    /**
     * Removes given {@link KeyListener}
     *
     * @param listener Removes listener
     */
    public void removeKeyListener(KeyListener listener) {
        textArea.removeKeyListener(listener);
    }

    //=======================

    /**
     * Sets whether you can type in the console
     *
     * @param editable Enables/Disables editing
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    //=======================
    // Matrix implementation
    //=======================


    /**
     * Sets matrix that can be printed
     *
     * @param intMatrix set matrix
     * @see #printMatrix()
     */
    public void setMatrix(int[][] intMatrix) {
        this.intMatrix = intMatrix;
    }

    /**
     * Sets matrix that can be printed
     *
     * @param charMatrix set matrix
     * @see #printMatrix()
     */
    public void setMatrix(char[][] charMatrix) {
        this.charMatrix = charMatrix;
    }

    /**
     * Sets matrix that can be printed
     *
     * @param stringMatrix set matrix
     * @see #printMatrix()
     */
    public void setMatrix(String[][] stringMatrix) {
        this.stringMatrix = stringMatrix;
    }

    /**
     * Prints the set matrix
     *
     * @see "all methods for setting a matrix here:
     * - {@link #setMatrix(int[][])}
     * - {@link #setMatrix(char[][])}
     * - {@link #setMatrix(String[][])}"
     */
    public void printMatrix() {
        if (charMatrix != null) {
            for (char[] matrix : charMatrix) {
                for (int b = 0; b < charMatrix[0].length; b++) {
                    print(matrix[b] + " ");
                }
                println();
            }
        }
        if (stringMatrix != null) {
            for (String[] matrix : stringMatrix) {
                for (int b = 0; b < stringMatrix[0].length; b++) {
                    print(matrix[b] + " ");
                }
                println();
            }
        }
        if (intMatrix != null) {
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
     *
     * @return int matrix
     */
    public int[][] getIntMatrix() {
        return intMatrix;
    }

    /**
     * Gets the char matrix if set
     *
     * @return char matrix
     */
    public char[][] getCharMatrix() {
        return charMatrix;
    }

    /**
     * Gets the string matrix if set
     *
     * @return string matrix
     */
    public String[][] getStringMatrix() {
        return stringMatrix;
    }

    //=======================
    // Basic console methods
    //=======================

    /**
     * Sets or overrides all the text shown in the terminal
     *
     * @param text sets text
     */
    public void setText(String text) {
        textArea.setText(text);
    }

    /**
     * Outputs {@link String} to the terminal
     *
     * @param text output text
     */
    public void print(String text) {
        textArea.append(text);
    }

    /**
     * Outputs {@link String} to the terminal and makes a new line
     */
    public void println() {
        textArea.append("\n");
    }

    /**
     * Outputs {@link String} to the terminal and makes a new line
     *
     * @param text output text
     */
    public void println(String text) {
        textArea.append(text + "\n");
    }

    /**
     * Clears the terminal by setting the text to nothing
     */
    public void clear() {
        textArea.setText(null);
    }

    /**
     * Reads the last line in the Terminal
     *
     * Limitations:
     * Pasting text with more than one line is not going to read the lines on top
     * Always reads the last line even if nothing is typed
     * Stops the execution for the most of the things
     */
    public String readLine() {
        readEvent();
        try {
            synchronized (input) {
                while (input.isEmpty())
                    input.wait();

                return input.remove(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void readEvent() {
        KeyListener readLine = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    synchronized (input) {
                        String[] text = textArea.getText().split("\\n");
                        int lastLine = text.length - 1;
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
     *
     * @param backgroundColor set the background {@link Color}
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Changes the size of the font
     *
     * @param fontSize size
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * Sets the {@link Color} of the terminal font
     *
     * @param fontColor sets the font {@link Color}
     */
    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    /**
     * Changes the font that the terminal will use
     * by creating new {@link Font} object
     *
     * @param font sets {@link Font}
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * Sets whether the Terminal window is resizable by the user
     * On default is: true
     *
     * @param resizeable False to lock resizing
     */
    public void setResizeable(boolean resizeable){
        this.resizeable = resizeable;
    }

    /**
     * Checks to see if the window is resizable
     *
     * @return True if resizable, else false.
     */
    public boolean isResizeable(){
        return this.resizeable;
    }

    /**
     * Makes the Terminal window fullscreen
     *
     * @param fullscreen True for fullscreen
     */
    public void setFullscreen(boolean fullscreen){
        this.fullscreen = fullscreen;
    }

    /**
     * Checks is the Terminal window fullscreen
     *
     * @return False if not fullscreen
     */
    public boolean isFullscreen(){
        return this.fullscreen;
    }

    private void createUIComponents() {
        textArea.setBackground(backgroundColor);
        textArea.setForeground(fontColor);
        textArea.setFont(font);
        textArea.setEditable(editable);
    }

    /**
     * Builds the terminal by executing all the specifications
     * for the
     */
    public void run() {

        //TODO: Add scrollbar that shows up when needed

        createUIComponents();

        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setContentPane(textArea);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(resizeable);
        if(fullscreen){
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        frame.setVisible(true);
    }
}
