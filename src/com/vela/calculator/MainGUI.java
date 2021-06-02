package com.vela.calculator;

import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAQuery;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;


/* WARNING */
/* IF NOT ESSENTIAL, DO NOT CHANGE THE CONTENT OF COM.WOLFRAM.ALPHA PACKAGE */
/* IT HAS BEEN MODIFIED BY VELA YANG TO BE USED MORE EASILY IN THIS PROJECT */

public class MainGUI implements Runnable {

    // Tag for whether should new Thread do
    private enum DoWhat {
        GET_KNOWLEDGE, CALCULATE
    }

    // Tag for using ONLINE mode or not
    private enum CalculatorMode {
        ALPHA, LOCAL
    }
    // Enum the action what should do when click the button
    private static DoWhat shouldDoWhat = DoWhat.GET_KNOWLEDGE;
    // if it is online
    private static CalculatorMode ifONLINE = CalculatorMode.ALPHA;

    // jTextArea for Calculator page
    protected static final JTextArea jTextArea = new JTextArea("  ", 5, 30);
    // for WolframAlpha page
    static final JTextArea jTextArea2 = new JTextArea("  ", 5, 30);
    // APPID of WolframAlpha API
    private static final String appid = "XWKV46-AAUVUJX388";
    // Contains button of calculator
    private static final JPanel buttonPanel1 = new JPanel();
    private static final JPanel buttonPanel2 = new JPanel();
    // CardLayout for different windows
    private static final CardLayout cardLayout = new CardLayout();
    private static final JButton calculateButton = new JButton("=");
    private static final JButton acquireButton = new JButton("Input and Acquire ANYTHING!!");
    // initialize the GUI components
    public static JFrame jFrame = new JFrame("Simple Calculator By Vela Yang");
    private static final Container container = jFrame.getContentPane();
    private static JLabel resultImage;
    // The panel contains first page
    private final JPanel topPanel1 = new JPanel();
    // The panel contains the second page
    private final JPanel topPanel2 = new JPanel();
    private final Color borderColor = new Color(224, 224, 224);
    // the result of calculator
    private double resultNumber;

    // Main function
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        // Set Layout of jFrame
        container.setLayout(cardLayout);
        // Set the gridLayout of JPanel
        GridLayout gridLayout = new GridLayout(4, 5, 0, 0);
        buttonPanel1.setLayout(gridLayout);
        buttonPanel2.setLayout(gridLayout);

        // Set the UI looks like
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

        // Initialize
        new MainGUI().addListenerForAcquireAndCalculateButton();
        new MainGUI().setjTextAreas();
        new MainGUI().setWolframAlphaPage();
        new MainGUI().setContainer();
        new MainGUI().addMenuBar();
        cardLayout.last(container);
        jFrame.setIconImage(new ImageIcon(Objects.requireNonNull(MainGUI.class.getClassLoader().getResource("icon.png"))).getImage());
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(400, 600);
    }

    private void addListenerForAcquireAndCalculateButton() {
        // Equal
        // Calculate
        calculateButton.addActionListener(e -> {
            shouldDoWhat = DoWhat.CALCULATE;
            new Thread(new MainGUI()).start();
        });
        // Processing the input content
        acquireButton.addActionListener(e -> {
            shouldDoWhat = DoWhat.GET_KNOWLEDGE;
            new Thread(new MainGUI()).start();
        });
    }

    // Set MenuBar of JFrame
    private void addMenuBar() {

        // JMenuBar
        final JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenu = new JMenu("Change");
        jMenu.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        JLabel modeLabel = new JLabel(" 科学");
        modeLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));


        // MenuItem 1
        JMenuItem calculatorMenu = new JMenuItem("Calculator");
        calculatorMenu.addActionListener(e -> {
            modeLabel.setText(" 科学");
            jTextArea.setText(" ");
            cardLayout.last(container);
            jFrame.setSize(400, 600);
        });
        calculatorMenu.setBackground(Color.white);
        calculatorMenu.setFont(new Font("Segoe UI", Font.PLAIN, 20));

        // MenuItem 2
        JMenuItem wolframAlphaMenu = new JMenuItem("Click Here!");
        wolframAlphaMenu.setBackground(Color.white);
        wolframAlphaMenu.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        wolframAlphaMenu.addActionListener(e -> {
            modeLabel.setText("  Alpha");
            jTextArea2.setText(" ");
            cardLayout.first(container);
            jFrame.setSize(650, 840);
        });

        jMenu.add(calculatorMenu);
        jMenu.add(wolframAlphaMenu);
        jMenuBar.add(jMenu);
        jMenuBar.add(modeLabel);
        jMenuBar.setBackground(Color.white);

        // Set size of MenuBar
        jMenuBar.setPreferredSize(new Dimension(200, 40));
        jFrame.setJMenuBar(jMenuBar);
    }

    // The panel contains buttonPanel
    private void setContainer() {

        // Set the gridLayout of JFrame
        topPanel1.setLayout(new GridLayout(3, 1, 0, 0));

        topPanel1.add(jTextArea);

        // Add functional buttons
        new MainGUI().addButtons();

        // Set the font of buttons
        for (int i = 0; i < buttonPanel1.getComponentCount(); i++) {
            buttonPanel1.getComponent(i).setFont(new Font("Segoe UI", Font.PLAIN, 20));
            buttonPanel1.getComponent(i).setBackground(new Color(244, 244, 244));
            buttonPanel2.getComponent(i).setFont(new Font("Segoe UI", Font.PLAIN, 20));
            if ((0 < i && i < 4) || (5 < i && i < 9) || (10 < i && i < 14) || (15 < i && i < 19))
                buttonPanel2.getComponent(i).setBackground(Color.white);
            else buttonPanel2.getComponent(i).setBackground(new Color(244, 244, 244));
            if (i == 19) buttonPanel2.getComponent(i).setBackground(new Color(75, 210, 172));
        }

        // Add the jPanels
        topPanel1.add(buttonPanel1);
        topPanel1.add(buttonPanel2);
        container.add(topPanel1);
        // Set the frame size
        jFrame.setSize(650, 840);

    }

    private void setWolframAlphaPage() {
        // Set the font of jTextArea
        jTextArea2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 30));
        jTextArea2.setSelectedTextColor(Color.black);

        // Reset the size of Frame
        jFrame.setSize(650, 840);
        // Set topPanel2 with GridBagLayout
        topPanel2.setLayout(new GridBagLayout());

        // Set Acquire Button

        acquireButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        acquireButton.setBackground(Color.white);

        GridBagConstraints gbc = new GridBagConstraints();

        // Add jTextArea to GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 100;
        gbc.gridheight = 200;
        gbc.weightx = 10;
        gbc.weighty = 10;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.ipadx = 100;
        gbc.ipady = 100;
        topPanel2.add(jTextArea2, gbc);

        // Add jTextArea to GridBagLayout
        gbc.gridy = 200;
        gbc.gridwidth = 100;
        gbc.gridheight = 400;
        gbc.ipadx = 650;
        gbc.ipady = 600;
        JScrollPane jScrollPane = new MainGUI().getScrollPane();

        // Set velocity of MouseWheel rolls
        jScrollPane.getVerticalScrollBar().setUnitIncrement(35);
        jScrollPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '\n') {

                    // Ignore the default result which appends a '\n' to jTextArea2
                    e.consume();
                    acquireButton.doClick();
                }
            }
        });
        topPanel2.add(jScrollPane, gbc);

        // Add jTextArea to GridBagLayout
        gbc.gridy = 600;
        gbc.gridwidth = 100;
        gbc.gridheight = 200;
        gbc.ipadx = 600;
        gbc.ipady = 30;
        topPanel2.add(acquireButton, gbc);
        container.add(topPanel2);
    }

    // Return a JScrollPane by given image
    private JScrollPane getScrollPane() {
        resultImage = new JLabel();
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.white);
        imagePanel.add(resultImage);
        imagePanel.setPreferredSize(new Dimension(600, 2500));
        return new JScrollPane(imagePanel);
    }

    private String getResultFromWolframAlpha() throws IOException {
        String input = jTextArea.getText();
        WAEngine engine = new WAEngine();

        // These properties will be set in all the WAQuery objects created from this WAEngine.
        engine.setAppID(appid);
        //engine.addFormat("plaintext");
        // Create the query.
        WAQuery query = engine.createQuery();
        // Set properties of the query.
        query.setInput(input);

        // Get result
        String result = "default";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(engine.toURL(query, "result")).openStream()));
        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null) result = inputLine;
        if (result.equals("default")) throw new VoidResultException();

        // If can't get result from server, then throw the Exception
        return result;
    }

    private void setjTextAreas() {
        // Make jTextArea change lines automatically
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea2.setLineWrap(true);
        jTextArea2.setWrapStyleWord(true);
        // Set the font of jTextArea
        jTextArea.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 30));
        jTextArea.setSelectedTextColor(Color.black);

        // Add Listener for jTextAreas
        jTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '\n') {

                    // Ignore the default result which appends a '\n' to jTextArea
                    e.consume();
                    calculateButton.doClick();
                }
                if (e.getKeyChar() == '*') {

                    // Ignore the default result which appends a '\n' to jTextArea2
                    jTextArea.insert("×", jTextArea.getCaret().getDot());
                    jTextArea.setEditable(false);
                }
                if (e.getKeyChar() == '/') {

                    // Ignore the default result which appends a '\n' to jTextArea2
                    e.consume();
                    jTextArea.insert("÷", jTextArea.getCaret().getDot());
                    jTextArea.setEditable(false);
                }
                if (e.getKeyChar() != '/' && e.getKeyChar() != '*') jTextArea.setEditable(true);
            }
        });
        jTextArea2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    // Ignore the default result which appends a '\n' to jTextArea2
                    e.consume();
                    acquireButton.doClick();
                }
            }
        });
    }

    // Finish the run function of Runnable Interface
    // Only acquiceButton and calculateButton call this RUN METHOD(keyListener use JButton.doClick() to call this)
    // In this Calculator Program, RUN METHOD will process the Input and give the output to the user when it is being called
    @Override
    public void run() {

        switch (shouldDoWhat) {

            // Actions for calculaor Page
            case CALCULATE:
                // If is Online , get result from Alpha
                if (ifONLINE == CalculatorMode.ALPHA) {

                    String result = "default";
                    try {
                        result = new MainGUI().getResultFromWolframAlpha();
                        jTextArea.append(" = " + result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // If can't get result from WolframAlpha, then inform users of change the input
                    if (result.equals("default"))
                        try {
                            throw new VoidResultException();
                        } catch (VoidResultException e) {
                            e.printStackTrace();
                        }

                } else {
                    // get the input content
                    char[] input = jTextArea.getText().toCharArray();

                    // Split the input content to a easily processed one
                    StringBuffer[] segmentOfInput = new StringBuffer[100];
                    for (int i = 0; i < segmentOfInput.length; i++) {
                        segmentOfInput[i] = new StringBuffer();
                    }

                    // Split the input content to a easily processed one
                    // after this for sentence, the input content such as :
                    // 1.5+2×3+9÷3 will be stored in the given StringBuffer collection like the follows
                    // "1.5" "+" "2" "×" "3" "+" "9" "÷" "3"
                    int inBracket = 0;
                    for (int i = 0, j = 0; i < input.length; i++) {
                        if (Character.isDigit(input[i]) || input[i] == '.' || input[i] == '!' || input[i] == 'π' || input[i] == 'e' || inBracket != 0) {
                            if (i >= 1 && !Character.isDigit(input[i - 1]) && input[i - 1] != '(' && input[i - 1] != '.' && input[i] != '!' && inBracket == 0)
                                j++;
                            segmentOfInput[j].append(input[i]);
                        }
                        // store the operator
                        else {
                            if (i >= 1 && !Character.isLetter(input[i - 1]) && input[i] != ')') j++;
                            else if (i >= 1 && (Character.isDigit(input[i - 1]) || input[i - 1] == 'π' || (input[i - 1] == 'e' && input[i] != 'x'))
                                    && input[i - 1] != ')' && input[i] != ')')
                                j++;
                            segmentOfInput[j].append(input[i]);
                        }
                        if (input[i] == '(') inBracket++;
                        if (input[i] == ')') inBracket--;
                    }

                    // 如果括号数量不等，抛出异常
                    if (inBracket != 0) try {
                        throw new NoCorrespondingBracketException();
                    } catch (NoCorrespondingBracketException e) {
                        e.printStackTrace();
                    }

                    try {
                        // get result
                        resultNumber = Equal.calculateResult(segmentOfInput);
                        // if resultNumber would be more likely to an integer number, then print the integer
                        long intResultNumber = (long) resultNumber;
                        if (Math.abs(resultNumber - intResultNumber) <= 1e-7) {
                            jTextArea.setText(jTextArea.getText() + " = " + intResultNumber);
                        } else jTextArea.setText(jTextArea.getText() + " = " + String.format("%.8f", resultNumber));
                        // If result is too large that can not present, then throw InfinityException
                    } catch (InfinityException infinityException) {
                        infinityException.printStackTrace();
                    }
                }
                break;

            // Actions for wolframAlpha Page
            case GET_KNOWLEDGE: {
                String inputString = jTextArea2.getText();
                jTextArea2.append("\n Good things deserve waiting...");

                // Get correct API URL from WolframAlpha API
                WAEngine engine = new WAEngine();
                engine.setAppID(appid);
                WAQuery query = engine.createQuery();
                query.setInput(inputString);
                try {
                    // Get Image from API
                    URL url = new URL(engine.toURL(query, "simple"));
                    BufferedImage image1 = ImageIO.read(url);
                    resultImage.setIcon(new ImageIcon(image1));
                } catch (IOException malformedURLException) {
                    jTextArea2.setText("\nCan't get result, try to rearrange the INPUT content");
                    malformedURLException.printStackTrace();
                }
            }
        }
    }

    // Add buttons and set the function of buttons
    // Intotal, 8lines for buttons
    private void addButtons() {

        // Line 1
        {
            // Resize the window
            JButton modeButton = new JButton("Alpha");
            modeButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            modeButton.addActionListener(e -> {
                ifONLINE = modeButton.getText().equals("Local") ? CalculatorMode.ALPHA : CalculatorMode.LOCAL;
                modeButton.setText(modeButton.getText().equals("Local") ? "Alpha" : "Local");
            });
            buttonPanel1.add(modeButton);

            // Button presents Grecian alphabet π which is approximately to 3.1415926536 on jTextArea
            JButton piButton = new JButton("π");
            piButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            piButton.addActionListener(e -> jTextArea.insert("π", jTextArea.getCaret().getDot()));
            buttonPanel1.add(piButton);

            // Button presents natural exponent e which is approximately to 2.7182818285 on jTextArea
            JButton eButton = new JButton("e");
            eButton.addActionListener(e -> jTextArea.insert("e", jTextArea.getCaret().getDot()));
            buttonPanel1.add(eButton);
            eButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));

            // Button for Clearing the content of jTextArea
            // More precisely, make the jTextArea to the initial status which has the String content "  "
            JButton clearButton = new JButton("C");
            clearButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            clearButton.addActionListener(e -> jTextArea.setText("  "));
            buttonPanel1.add(clearButton);

            // Button for Delete a character of jTextArea
            // Unfinished at now
            JButton delButton = new JButton("Del");
            delButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            delButton.addActionListener(e -> {
                StringBuilder tempString = new StringBuilder(jTextArea.getText());
                tempString.deleteCharAt(jTextArea.getCaret().getDot() - 1);
                jTextArea.setText(tempString.toString());
            });

            buttonPanel1.add(delButton);

        }

        // Line 2
        {
            // sin function
            JButton sinButton = new JButton("sin");
            sinButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            sinButton.addActionListener(e -> jTextArea.insert("sin(", jTextArea.getCaret().getDot()));
            buttonPanel1.add(sinButton);

            // cos function
            JButton cosButton = new JButton("cos");
            cosButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            cosButton.addActionListener(e -> jTextArea.insert("cos(", jTextArea.getCaret().getDot()));
            buttonPanel1.add(cosButton);

            // tan function
            JButton tanButton = new JButton("tan");
            tanButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            tanButton.addActionListener(e -> jTextArea.insert("tan(", jTextArea.getCaret().getDot()));
            buttonPanel1.add(tanButton);

            // arcsin function
            JButton asinButton = new JButton("asin");
            asinButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            asinButton.addActionListener(e -> jTextArea.insert("arcsin(", jTextArea.getCaret().getDot()));
            buttonPanel1.add(asinButton);

            // arctan function
            JButton atanButton = new JButton("atan");
            atanButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            atanButton.addActionListener(e -> jTextArea.insert("arctan(", jTextArea.getCaret().getDot()));
            buttonPanel1.add(atanButton);

        }

        // Line 3
        {

            // Integer
            JButton intButton = new JButton("∫");
            intButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            intButton.addActionListener(e -> jTextArea.insert("∫", jTextArea.getCaret().getDot()));
            buttonPanel1.add(intButton);

            // Differential
            JButton diffButton = new JButton("y'");
            diffButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            diffButton.addActionListener(e -> jTextArea.insert("'", jTextArea.getCaret().getDot()));
            buttonPanel1.add(diffButton);

            // Button presents ^2 on jTextArea
            JButton squareButton = new JButton("<html>X<sup>2</sup>");
            squareButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            squareButton.addActionListener(e -> jTextArea.insert("^2", jTextArea.getCaret().getDot()));
            buttonPanel1.add(squareButton);


            // Exp()
            JButton expButton = new JButton("exp");
            expButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            expButton.addActionListener(e -> jTextArea.insert("exp(", jTextArea.getCaret().getDot()));
            buttonPanel1.add(expButton);

            // Mod
            JButton modButton = new JButton("mod");
            modButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            modButton.addActionListener(e -> jTextArea.insert("mod", jTextArea.getCaret().getDot()));
            buttonPanel1.add(modButton);
        }

        // Line 4
        {
            // Square root
            JButton squareRootButton = new JButton("<html>√<sup>—</sup>");
            squareRootButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            squareRootButton.addActionListener(e -> jTextArea.insert("sqrt(", jTextArea.getCaret().getDot()));
            buttonPanel1.add(squareRootButton);

            // Left bracket
            JButton leftBracketButton = new JButton("(");
            leftBracketButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            leftBracketButton.addActionListener(e -> jTextArea.insert("(", jTextArea.getCaret().getDot()));
            buttonPanel1.add(leftBracketButton);

            // Right bracket
            JButton rightBracketButton = new JButton(")");
            rightBracketButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            rightBracketButton.addActionListener(e -> jTextArea.insert(")", jTextArea.getCaret().getDot()));
            buttonPanel1.add(rightBracketButton);

            // Factorial
            JButton factorialButton = new JButton("n!");
            factorialButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            factorialButton.addActionListener(e -> jTextArea.insert("!", jTextArea.getCaret().getDot()));
            buttonPanel1.add(factorialButton);

            // Divide
            JButton divideButton = new JButton("÷");
            divideButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            divideButton.addActionListener(e -> jTextArea.insert("÷", jTextArea.getCaret().getDot()));
            buttonPanel1.add(divideButton);
        }

        // Line 5
        {
            // Power
            JButton exponentialXAndYButton = new JButton("<html>X<sup>y</sup>");
            exponentialXAndYButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            exponentialXAndYButton.addActionListener(e -> jTextArea.insert("^", jTextArea.getCaret().getDot()));
            buttonPanel2.add(exponentialXAndYButton);

            // Seven
            JButton sevenButton = new JButton("7");
            sevenButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            sevenButton.addActionListener(e -> jTextArea.insert("7", jTextArea.getCaret().getDot()));
            buttonPanel2.add(sevenButton);

            // Eight
            JButton eightButton = new JButton("8");
            eightButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            eightButton.addActionListener(e -> jTextArea.insert("8", jTextArea.getCaret().getDot()));
            buttonPanel2.add(eightButton);

            // Nine
            JButton nineButton = new JButton("9");
            nineButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            nineButton.addActionListener(e -> jTextArea.insert("9", jTextArea.getCaret().getDot()));
            buttonPanel2.add(nineButton);

            // Multiply
            JButton multiplyButton = new JButton("×");
            multiplyButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            multiplyButton.addActionListener(e -> jTextArea.insert("×", jTextArea.getCaret().getDot()));
            buttonPanel2.add(multiplyButton);
        }

        // Line 6
        {
            // Power of ten
            JButton exponentialOfTenButton = new JButton("<html>10<sup>x</sup>");
            exponentialOfTenButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            exponentialOfTenButton.addActionListener(e -> jTextArea.insert("10^(", jTextArea.getCaret().getDot()));
            buttonPanel2.add(exponentialOfTenButton);

            // Four
            JButton fourButton = new JButton("4");
            fourButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            fourButton.addActionListener(e -> jTextArea.insert("4", jTextArea.getCaret().getDot()));
            buttonPanel2.add(fourButton);

            // Five
            JButton fiveButton = new JButton("5");
            fiveButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            fiveButton.addActionListener(e -> jTextArea.insert("5", jTextArea.getCaret().getDot()));
            buttonPanel2.add(fiveButton);

            // Six
            JButton sixButton = new JButton("6");
            sixButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            sixButton.addActionListener(e -> jTextArea.insert("6", jTextArea.getCaret().getDot()));
            buttonPanel2.add(sixButton);

            // Subtract
            JButton subtractButton = new JButton("-");
            subtractButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            subtractButton.addActionListener(e -> jTextArea.insert("-", jTextArea.getCaret().getDot()));
            buttonPanel2.add(subtractButton);
        }

        // Line 7
        {
            // Logarithm
            JButton logarithmOfTenButton = new JButton("<html>log<sub>10</sub>");
            logarithmOfTenButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            logarithmOfTenButton.addActionListener(e -> jTextArea.insert("log(", jTextArea.getCaret().getDot()));
            buttonPanel2.add(logarithmOfTenButton);

            // One
            JButton oneButton = new JButton("1");
            oneButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            oneButton.addActionListener(e -> jTextArea.insert("1", jTextArea.getCaret().getDot()));
            buttonPanel2.add(oneButton);

            // Two
            JButton twoButton = new JButton("2");
            twoButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            twoButton.addActionListener(e -> jTextArea.insert("2", jTextArea.getCaret().getDot()));
            buttonPanel2.add(twoButton);

            // Three
            JButton threeButton = new JButton("3");
            threeButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            threeButton.addActionListener(e -> jTextArea.insert("3", jTextArea.getCaret().getDot()));
            buttonPanel2.add(threeButton);

            // Add
            JButton addButton = new JButton("+");
            addButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            addButton.addActionListener(e -> jTextArea.insert("+", jTextArea.getCaret().getDot()));
            buttonPanel2.add(addButton);
        }

        // Line 8
        {
            // Logarithm of e
            JButton logarithmOfEButton = new JButton("ln");
            logarithmOfEButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            logarithmOfEButton.addActionListener(e -> jTextArea.insert("ln(", jTextArea.getCaret().getDot()));
            buttonPanel2.add(logarithmOfEButton);

            // Change the signal
            JButton positiveButton = new JButton("+/-");
            positiveButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            positiveButton.addActionListener(e -> jTextArea.setText("  " + (resultNumber = resultNumber == 0 ? 0 : 0 - resultNumber)));
            buttonPanel2.add(positiveButton);

            // Zero
            JButton zeroButton = new JButton("0");
            zeroButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            zeroButton.addActionListener(e -> jTextArea.insert("0", jTextArea.getCaret().getDot()));
            buttonPanel2.add(zeroButton);

            // Decimal dot
            JButton dotButton = new JButton(".");
            dotButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            dotButton.addActionListener(e -> jTextArea.insert(".", jTextArea.getCaret().getDot()));
            buttonPanel2.add(dotButton);


            calculateButton.setBorder(new BasicBorders.ButtonBorder(borderColor, borderColor, borderColor, borderColor));
            buttonPanel2.add(calculateButton);
        }
    }

}
