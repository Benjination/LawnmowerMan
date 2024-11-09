package LawnmowerMan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame
{
    private JPanel mainPanel;
    private JPanel sidePanel;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem exitMenuItem;
    private JTextField lengthField;
    private JTextField widthField;
    private JButton startButton;
    private JButton stopButton;
    private JButton resetButton;
    private JPanel lawnPanel;
    private Lawn lawn;
    private boolean isRunning = false;
    private Timer timer;
    private Mower mower;

    public GUI(int width, int height) {
        lawn = new Lawn(width, height);
        initializeUI();
        setVisible(true);
        this.mower = new Mower(this);
    }

    private void initializeUI() {
        setTitle("LawnMowerMan");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());
        createMenuBar();
        createSidePanel();

        lawnPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawLawn(g);
            }
        };
        lawnPanel.setPreferredSize(new Dimension(600, 400)); // Adjust as needed
        mainPanel.add(lawnPanel, BorderLayout.CENTER);
        mainPanel.add(sidePanel, BorderLayout.WEST);

        setContentPane(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        exitMenuItem = new JMenuItem("Exit");
        //exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private void createSidePanel() {
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        lengthField = new JTextField(3);
        widthField = new JTextField(3);
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        resetButton = new JButton("Update");
        Dimension maxSize = new Dimension(Integer.MAX_VALUE, lengthField.getPreferredSize().height);
        lengthField.setMaximumSize(maxSize);
        widthField.setMaximumSize(maxSize);
        JPanel lengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lengthPanel.add(new JLabel("Length (ft):"));
        lengthPanel.add(lengthField);
        JPanel widthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        widthPanel.add(new JLabel("Width (ft):"));
        widthPanel.add(widthField);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(resetButton);
        sidePanel.add(lengthPanel);
        sidePanel.add(widthPanel);
        sidePanel.add(Box.createVerticalStrut(10));  
        sidePanel.add(buttonPanel);
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning) {
                    isRunning = true;
                    startButton.setText("Resume");
                    if (lawn == null) {
                        int length = Integer.parseInt(lengthField.getText());
                        int width = Integer.parseInt(widthField.getText());
                        lawn = new Lawn(length, width);
                    }
                    mower.startMowing(lawn.width, lawn.height);
                } else {
                    mower.resume();
                }
            }
        });

    
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning) {
                    isRunning = false;
                    mower.pause();
                    startButton.setText("Resume");
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isRunning = false;
                mower.reset();
                int length = Integer.parseInt(lengthField.getText());
                int width = Integer.parseInt(widthField.getText());
                lawn = new Lawn(length, width);
                startButton.setText("Start");
                repaint();
            }
        });
    }

    private void resumeMowing() {
        if (isRunning) {
            mower.resume();
        }
    }


    public void setLawnSize(int width, int height) {
        lawn = new Lawn(width, height);
        repaint();
    }

    private void drawLawn(Graphics g) {
        if (lawn == null || lawn.squares == null || lawn.squares.isEmpty()) {
            return;
        }
    
        int canvasWidth = getWidth();
        int canvasHeight = getHeight();
    
        // Calculate the size of each square to fit the canvas
        int padding = 55;
        int squareSize = Math.min((canvasWidth - 4* padding) / lawn.width, (canvasHeight - 3* padding) / lawn.height);
    
        // Recalculate the square size to ensure all squares fit
        squareSize = Math.min(squareSize, Math.min(canvasWidth / lawn.width, canvasHeight / lawn.height));
    
        // Calculate the starting point to center the lawn
        int startX = (canvasWidth - (lawn.width * squareSize)) / 2;
        int startY = (canvasHeight - (lawn.height * squareSize)) / 2;
    
        for (Square square : lawn.squares) {
            // Fill the square
            g.setColor(square.getColor());
            g.fillRect(startX + square.x * squareSize, startY + square.y * squareSize, squareSize, squareSize);
            
            // Draw the border
            g.setColor(Color.BLACK);
            g.drawRect(startX + square.x * squareSize, startY + square.y * squareSize, squareSize, squareSize);
        }
    }

    public void Update(Lawn newLawn) {
        this.lawn = newLawn;
        lawnPanel.repaint();
    }

    public void updateSquare(Square square) {
        // Find the square in the lawn and update it
        for (Square s : lawn.squares) {
            if (s.x == square.x && s.y == square.y) {
                s.setColor(square.getColor());
                break;
            }
        }
        lawnPanel.repaint();
    }

}