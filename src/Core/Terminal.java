package Core;


import javax.swing.*;
import javax.swing.text.*;
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
 * @version 1.0.2-beta
 */
public class Terminal {
    private final int height;
    private final int width;
    private final List<String> input = new ArrayList<>();
    private ScheduledExecutorService executorService;
    private ScheduledExecutorService timerExecutor;
    TerminalScheduleUpdate scheduleUpdate;
    int initialDelay = 0;
    int delay = 500;
    private String title = "Terminal";
    private JFrame frame;
    private JPanel MainPanel;
    private JPanel panel;
    private JTextPane textPane;
    private JScrollPane scroll;
    private boolean editable = true;
    private boolean resizeable = true;
    private boolean fullscreen = false;
    private Color backgroundColor = new Color(0, 0, 0);
    private Color fontColor = new Color(0, 225, 0);
    private int fontSize = 16;
    private Font font;
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
     * Sets the periodic execution(schedule) for the terminal
     * <p>
     *     This method executes by default 2 times in a second
     *     you can change the value of the delay to change how many times in a second to execute
     * </p>
     * Documentation on how use  watch the GitHub
     * @param scheduleUpdate adds new {@link TerminalScheduleUpdate}
     */
    public void setSchedule(TerminalScheduleUpdate scheduleUpdate) {
        this.scheduleUpdate = scheduleUpdate;
        start();
    }

    /**
     * Runs void method after a given delay
     *
     * @param method runnable void method
     * @param delay delay in milliseconds
     * */
    public void setTimer(Runnable method, long delay){
        this.timerExecutor.schedule(method, delay, TimeUnit.MILLISECONDS);
    }


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
     * Starts the update schedule
     * <p>
     *     This starts a loop that constantly executes the update method from {@link TerminalScheduleUpdate}
     * </p>
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
     * <p>
     *     You have to build your KeyListener before you add it
     * </p>
     *
     * @param listener adds {@link KeyListener}
     * @see KeyListener
     */
    public void addKeyListener(KeyListener listener) {
        textPane.addKeyListener(listener);
    }

    /**
     * Removes given {@link KeyListener}
     *
     * @param listener Removes listener
     */
    public void removeKeyListener(KeyListener listener) {
        textPane.removeKeyListener(listener);
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
     * With some limitations
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

    private void append(String text) throws BadLocationException
    {
        StyledDocument document = (StyledDocument) textPane.getDocument();
        document.insertString(document.getLength(), text, null);
    }

    private void append(String text, Color color) throws BadLocationException
    {
        StyledDocument document = textPane.getStyledDocument();
        Style style = document.addStyle("Color", null);
        StyleConstants.setForeground(style, color);
        document.insertString(document.getLength(), text, style);
    }

    /**
     * Sets or overrides all the text shown in the terminal
     *
     * @param text sets text
     */
    public void setText(String text) {
        textPane.setText(text);
    }

    /**
     * Outputs {@link String} to the terminal
     *
     * @param text output text
     */

    public void print(String text, int r, int g, int b) {
        try {
            append(text, new Color(r, g, b));
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    public void print(String text, Color color) {
        try {
            append(text, color);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    public void print(String text) {
        try {
            append(text);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Outputs {@link String} to the terminal and makes a new line
     */
    public void println() {
        try {
            append("\n");
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Outputs {@link String} to the terminal and makes a new line
     *
     * @param text output text
     */
    public void println(String text) {
        try {
            append(text + "\n");
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    public void println(String text, int r, int g, int b) {
        try {
            append(text + "\n", new Color(r, g, b));
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    public void println(String text, Color color) {
        try {
            append(text + "\n", color);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Clears the terminal by setting the text to nothing
     */
    public void clear() {
        textPane.setText(null);
    }

    /**
     * Reads the last line in the Terminal
     *
     * Limitations:
     * <p>
     *     Pasting text with more than one line is not going to read the lines on top
     *     Always reads the last line even if nothing is typed
     *     Stops the execution for the most of the things
     * </p>
     *
     * @return Last line in the Terminal
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
                        String[] text = textPane.getText().split("\\n");
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
        textPane.addKeyListener(readLine);
    }

    //=======================
    //     Build options
    //=======================


    /**
     * Sets the {@link Color} of the terminal background
     *
     * @param backgroundColor set the background {@link Color}
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBackgroundColor(int r, int g, int b){
        this.backgroundColor =  new Color(r, g, b);
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
     * Sets the font color to a new one
     * <p>
     *     this can be done by selecting the rgb values of the color you want
     * </p>
     * @param r red
     * @param g green
     * @param b blue
     * */
    public void setFontColor(int r, int g, int b){
        this.fontColor = new Color(r , g, b);
    }


    /**
     * Sets the font to a new one
     * <p>
     *     This is done by making a new {@link Font} object
     * </p>
     * @param font sets {@link Font}
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * Sets the font to a new one
     * <p>
     *     Sets the font by giving it the name of the font as well as the desired style and size
     * </p>
     * <p>
     *     The styles can be bold, italics and so on (default = 0)
     * </p>
     * @param name the name of the font
     * @param style the style of the font
     * @param size the font size
     */
    public void setFont(String name, int style, int size){
        this.font = new Font(name, style, size);
    }

    /**
     * Sets the font to a new one
     * <p>
     *     Sets the font by giving it the name of the font as well as the desired style
     * </p>
     * <p>
     *     The styles can be bold, italics and so on (default = 0)
     * </p>
     * @param name the name of the font
     * @param style the style of the font
     */
    public void setFont(String name, int style){
        this.font = new Font(name, style, fontSize);
    }

    /**
     * Sets the font to a new one
     * <p>
     *     Sets the font by giving it the name of the font
     * </p>
     * @param name the name of the font
     */
    public void setFont(String name){
        this.font = new Font(name, Font.PLAIN, fontSize);
    }

    /**
     * Gives the set font
     * @return font*/
    public Font getFont(){
        if(font==null){
            this.font = new Font("Segoe UI", Font.BOLD, fontSize);
        }
        return this.font;
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
        textPane.setBackground(backgroundColor);
        textPane.setForeground(fontColor);
        textPane.setFont(getFont());
        textPane.setEditable(editable);
    }

    /**
     * Builds the terminal by executing all the specifications
     * for the
     */
    public void run() {
        createUIComponents();
        panel = new JPanel(new BorderLayout());
        panel.add(textPane);
        scroll = new JScrollPane(panel);
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setContentPane(scroll);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(resizeable);
        if(fullscreen){
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        frame.setVisible(true);
    }
}
