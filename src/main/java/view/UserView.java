package view;

import controller.MainController;
import view.winFileDialog.api.src.main.java.jnafilechooser.api.WindowsFolderBrowser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class UserView {

    private final JFrame MAIN_WINDOW = new JFrame("Переименовыватель");
    private JLabel status;

    private String path = null;

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
        status = new JLabel();
        status.setBounds(10, 10, 580, 20);
        status.setText("Путь не выбран!");
        status.setForeground(Color.RED);
        MAIN_WINDOW.add(status);
    }

    private void drawDialogButton() {

        JButton dialogButton = new JButton("Выберите папку");
        dialogButton.setBorder(BorderFactory.createEtchedBorder());
        dialogButton.setBounds(230, 200, 125, 35);
        dialogButton.setFocusPainted(false);
        dialogButton.addActionListener(e -> {
            showFolderChooser();
            if (path == null) {
                status.setText("Выберете путь!");
                status.setForeground(Color.RED);
            } else {
                controller.setPath(path);
                status.setText("Путь: " + path);
                status.setForeground(Color.BLACK);
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
                String pattern;
                pattern = patternInputField.getText();
                controller.setPattern(pattern);
            }
        });

        MAIN_WINDOW.add(patternInputField);

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
        MAIN_WINDOW.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void showFolderChooser() {
        final WindowsFolderBrowser folderBrowser = new WindowsFolderBrowser();
        final File dir = folderBrowser.showDialog(MAIN_WINDOW);

        if (dir != null) {
            path = dir.getPath();
        }
    }

    public void init() {

        drawStateCheckBoxes();
        drawStatusLabel();
        drawDialogButton();
        drawRenameButton();
        drawPatternInputField();
        drawMainWindow();

    }

}
