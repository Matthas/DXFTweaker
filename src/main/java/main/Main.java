package main;

import fileHandlers.Settings;
import windows.MainMenu;

import javax.swing.*;

public class Main extends JFrame {
    static Settings settings;

    public static void main(String[] args) {
        settings = Settings.getInstance();
        MainMenu mainMenu = new MainMenu();
    }
}