package controller;

import model.Renamer;

public class MainController {

    private final Renamer renamer;

    public MainController(Renamer renamer) {
        this.renamer = renamer;
    }

    public void rename(boolean includeSubFolderNames, boolean includeSubFolderFileNames) {
        renamer.init(includeSubFolderNames, includeSubFolderFileNames);
    }

    public void setPath(String path) {
        renamer.setPath(path);
        System.out.println(path);
    }

    public void setPattern(String pattern) {
        renamer.setPattern(pattern);
        System.out.println(pattern);
    }


}
