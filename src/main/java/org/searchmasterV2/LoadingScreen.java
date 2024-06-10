package org.searchmasterV2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.Timer;

public class LoadingScreen extends JFrame {

    public static void Load() {
        // Create the splash screen
        SplashScreen splash = new SplashScreen();
        splash.showSplashScreen();

        // Simulate some loading tasks
        try {
            Thread.sleep(2500); // Simulate loading time (3 seconds)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Close the splash screen and open the main application window
        splash.closeSplashScreen();
    }
}

class SplashScreen {
    private JFrame frame;
    private Timer timer;
    private float opacity = 0.0f;

    public SplashScreen() {
        frame = new JFrame();
        frame.setUndecorated(true);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Background panel with solid black color
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        frame.add(backgroundPanel, BorderLayout.CENTER);

        JLabel textLabel = new JLabel("");
        textLabel.setFont(new Font("Arial", Font.BOLD, 40));
        textLabel.setForeground(Color.BLACK);

        // Loading animation
        LoadingCircle loadingCircle = new LoadingCircle();

        // Adding components to the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        gbc.gridy = 1;
        backgroundPanel.add(textLabel, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        backgroundPanel.add(loadingCircle, gbc);

        // Set initial opacity
        frame.setOpacity(opacity);

        Timer fadeInTimer = null;

        Timer typingTimer = null;

        // Timer for fade-in effect
        Timer finalFadeInTimer = fadeInTimer;
        fadeInTimer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opacity < 1.0f) {
                    opacity += 0.1f; // Increase opacity more rapidly
                    frame.setOpacity(opacity);
                }

                if (opacity >= 1.0f) {
                    finalFadeInTimer.stop(); // Stop the timer once the opacity is 1.0
                }
            }
        });
        fadeInTimer.start();

        // Timer for text typing effect
        Timer finalTypingTimer = typingTimer;
        typingTimer = new Timer(40, new ActionListener() {
            String[] texts = {"R", "RM", "RMP", "RMP ", "RMP A", "RMP AN", "RMP ANA", "RMP ANAL", "RMP ANALY", "RMP ANALYS", "RMP ANALYSIS", "RMP ANALYSIS ", "RMP ANALYSIS T", "RMP ANALYSIS TO", "RMP ANALYSIS TOO", "RMP ANALYSIS TOOL"};
            int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < texts.length) {
                    textLabel.setText(texts[index]);
                    index++;
                } else {
                    finalTypingTimer.stop(); // Stop the timer once the text is fully displayed
                }
            }
        });
        typingTimer.start();

        // Start the loading animation
        loadingCircle.start();
    }

    public void showSplashScreen() {
        frame.setVisible(true);
    }

    public void closeSplashScreen() {
        frame.dispose();
    }
}

class LoadingCircle extends JPanel {
    private int angle = 0;
    private Timer timer;

    public LoadingCircle() {
        setPreferredSize(new Dimension(50, 50));
        setOpaque(false);

        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angle += 10;
                repaint();
            }
        });
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawArc(5, 5, 35, 35, angle, 270);

        g2d.dispose();
    }
}

class MainAppWindow {
    private JFrame frame;

    public MainAppWindow() {
        frame = new JFrame("SMV1.0 ALPHA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
    }

    public void showMainAppWindow() {
        frame.setVisible(true);
    }
}
