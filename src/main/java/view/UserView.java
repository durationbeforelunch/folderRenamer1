package view;

import controller.MainController;
import view.winFileDialog.api.src.main.java.jnafilechooser.api.WindowsFolderBrowser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class UserView implements IView {

    private final JFrame MAIN_WINDOW = new JFrame("Переименовыватель");
    private JLabel pathStatus;
    private JProgressBar progressBar;
    private JTextArea logTextArea;

    private String path = null;
    private String pattern = null;

    private boolean includeSubFolderNames;
    private boolean includeSubFolderFileNames;
    private boolean renameButtonEnabled;

    private final MainController controller;

    public UserView(MainController controller) {
        this.controller = controller;
    }

    private void drawStateCheckBoxes() {
        JCheckBox includeSubFolderNamesCheckBox = new JCheckBox("Включить имена папок");
        JCheckBox includeSubFolderFileNamesCheckBox = new JCheckBox("Включить файлы подпапок");
        includeSubFolderNamesCheckBox.setBounds(360,194 , 200, 25);
        includeSubFolderFileNamesCheckBox.setBounds(360,215 , 200, 25);

        includeSubFolderNamesCheckBox.addItemListener(e ->  includeSubFolderNames = e.getStateChange() == ItemEvent.SELECTED);
        includeSubFolderFileNamesCheckBox.addItemListener(e -> includeSubFolderFileNames = e.getStateChange() == ItemEvent.SELECTED);

        MAIN_WINDOW.add(includeSubFolderNamesCheckBox);
        MAIN_WINDOW.add(includeSubFolderFileNamesCheckBox);
    }

    private void drawStatusLabel() {
        pathStatus = new JLabel();
        pathStatus.setBounds(10, 10, 580, 20);
        pathStatus.setText("Путь не выбран!");
        pathStatus.setForeground(Color.RED);

        MAIN_WINDOW.add(pathStatus);
    }

    private void drawDialogButton() {

        JButton dialogButton = new JButton("Выберите папку");
        dialogButton.setBorder(BorderFactory.createEtchedBorder());
        dialogButton.setBounds(230, 200, 125, 35);
        dialogButton.setFocusPainted(false);
        dialogButton.addActionListener(e -> {
            showFolderChooser();
            if (path == null) {
                pathStatus.setText("Выберете путь!");
                pathStatus.setForeground(Color.RED);
            } else {
                controller.setPath(path);
                updateLogTextArea("Путь - " + path);
                pathStatus.setText("Путь: " + path);
                pathStatus.setForeground(Color.BLACK);
                renameButtonEnabled = true;
            }
        });

        MAIN_WINDOW.add(dialogButton);

    }

    private void drawRenameButton() {

        JButton renameButton = new JButton("Замена");
        renameButton.setBorder(BorderFactory.createEtchedBorder());
        renameButton.setBounds(230, 300, 125, 35);
        renameButton.setFocusPainted(false);

        renameButton.addActionListener(l -> {
            if (renameButtonEnabled) {
                controller.setPattern(pattern);
                controller.rename(includeSubFolderNames, includeSubFolderFileNames);
            }
        });

        MAIN_WINDOW.add(renameButton);

    }

    private void drawPatternInputField() {

        JTextField patternInputField = new JTextField();
        patternInputField.setBounds(170,250,250, 35);
        patternInputField.setBorder(BorderFactory.createEtchedBorder());
        patternInputField.setText("Шаблон поиска");
        patternInputField.setFont(new Font("Serif", Font.PLAIN, 18));

        patternInputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                pattern = patternInputField.getText();
            }
        });

        MAIN_WINDOW.add(patternInputField);

    }

    private void showFolderChooser() {
        final WindowsFolderBrowser folderBrowser = new WindowsFolderBrowser();
        final File dir = folderBrowser.showDialog(MAIN_WINDOW);

        if (dir != null) {
            path = dir.getPath();
        }
    }

    private void drawProgressBar() {

        progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(-1, 536, 600,25);
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        progressBar.setBorder(null);

        MAIN_WINDOW.add(progressBar);

    }

    private void drawLogTextArea() {

        logTextArea = new JTextArea();

        JScrollPane scrollPane = new JScrollPane(logTextArea);
        scrollPane.setBounds(-1, 348, 600, 190);

        logTextArea.setFocusable(false);

        MAIN_WINDOW.add(scrollPane);

    }

    private void drawMainWindow() {

        short size = 600;
        Dimension dimension = new Dimension(size,size);

        MAIN_WINDOW.setSize(dimension);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        MAIN_WINDOW.setResizable(false);
        MAIN_WINDOW.setBackground(Color.GRAY);
        MAIN_WINDOW.setLayout(null);
        MAIN_WINDOW.setVisible(true);

        JOptionPane.showMessageDialog(MAIN_WINDOW, "<html>Для использования программы, " +
                "<b>Вам</b> нужно уметь пользоваться <font color=red>RegEX</font></html>",
                "Предупреждение", JOptionPane.WARNING_MESSAGE);

        MAIN_WINDOW.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    @Override
    public void init() {

        drawProgressBar();
        drawStateCheckBoxes();
        drawStatusLabel();
        drawDialogButton();
        drawRenameButton();
        drawPatternInputField();
        drawLogTextArea();
        drawMainWindow();

    }

    @Override
    public void updateProgressBarText(String text) {
        progressBar.setString(text);
    }

    @Override
    public void updateProgressBarValue(int value) {
        progressBar.setValue(value);
    }

    @Override
    public void updateLogTextArea(String text) {
        logTextArea.append(text + "\n");
    }
}
