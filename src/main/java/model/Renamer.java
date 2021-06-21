package model;

import lombok.Setter;
import model.data.File;
import model.data.Folder;
import observers.Observed;
import observers.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Renamer implements Observed {

    private Observer observer;

    @Setter
    private String path;
    private Pattern pattern;

    private boolean matchFound;

    public void init(boolean includeSubFolders, boolean includeSubFolderFiles) {

        if (pattern == null) {
            observer.updateLogTextArea("Не введенный шаблон!");
            observer.updateProgressBarText("Не введенный шаблон!");
        } else if (path == null) {
            observer.updateLogTextArea("Не выбран путь!");
            observer.updateProgressBarText("Не выбран путь!");
        } else {

            Folder folder = new Folder(path);

            observer.updateProgressBarValue(10);
            observer.updateLogTextArea("Переименовывание\n");

            renameFiles(folder);
            observer.updateProgressBarValue(33);

            if (includeSubFolderFiles) {
                observer.updateLogTextArea("Переименовывание файлов подпапок\n");
                for (Folder folders : folder.getDIRECTORIES()) {
                    renameFiles(new Folder(folders.getFolder().getPath()));
                }
                observer.updateProgressBarValue(66);

            }

            if (includeSubFolders) {
                observer.updateLogTextArea("Переименовывание подпапок\n");
                renameDir(folder);
            }

            observer.updateProgressBarValue(100);
            observer.updateProgressBarText("Готово!");
            observer.updateLogTextArea("""
                    Готово!
                    ===================================================================================
                    """);

        }

    }

    private String rename(String name) {

        StringBuilder stringBuilder = new StringBuilder(name);
        matchFound = false;

        Matcher matcher = pattern.matcher(stringBuilder);

        while (matcher.find()) {
            stringBuilder.delete(matcher.start(), matcher.end());
            matcher = pattern.matcher(stringBuilder);
            matchFound = true;
        }

        return stringBuilder.toString();

    }

    private void renameFiles(Folder f) {

        String renamedFileName;
        boolean success;

        for (File file : f.getFILES()) {

            renamedFileName = rename(file.getName());
            success = file.renameTo(renamedFileName);

            if (matchFound) {
                if (success) {
                    observer.updateLogTextArea("Файл - " + file.getName() + " : " + renamedFileName);
                } else {
                    observer.updateLogTextArea("Не удалось переименовать файл - " + file.getName());
                }
            } else {
                observer.updateLogTextArea("Файл - " + file.getName() + " : Без изменений");
            }

        }

    }

    private void renameDir(Folder f) {

        String renamedDir;
        boolean success;

        for (Folder folder : f.getDIRECTORIES()) {

            renamedDir = rename(folder.getName());
            success = folder.renameTo(renamedDir);

            if (matchFound) {
                if (success) {
                    observer.updateLogTextArea("Папка - " + folder.getName() + " : " + renamedDir);
                } else {
                    observer.updateLogTextArea("Не удалось переименовать директорию - " + folder.getName());
                }
            } else {
                observer.updateLogTextArea("Папка - " + folder.getName() + " : Без изменений");
            }

        }

    }

    public void setPattern(String pattern) {
        if (pattern != null) {
            try {
                this.pattern = Pattern.compile(pattern);
                observer.updateLogTextArea("Шаблон - " + pattern);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addObserver(Observer observer) {
        this.observer = observer;
    }
}
