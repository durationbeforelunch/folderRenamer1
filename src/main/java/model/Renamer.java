package model;

import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Renamer {

    @Setter
    private String path;

    private Pattern pattern;

    public void init(boolean includeSubFolders, boolean includeSubFolderFiles) {

        Folder folder = new Folder(path);

        renameFiles(folder);

        if (includeSubFolderFiles) {
            for (Folder folders : folder.getDIRECTORIES()) {
                renameFiles(new Folder(folders.getFolder().getPath()));
            }
        }

        if (includeSubFolders) {
            renameDir(folder);
        }

    }

    private String rename(String name) {

        StringBuilder stringBuilder = new StringBuilder(name);

        Matcher matcher = pattern.matcher(stringBuilder);

        while (matcher.find()) {
            stringBuilder.delete(matcher.start(), matcher.end());
            matcher = pattern.matcher(stringBuilder);
        }

        return stringBuilder.toString();

    }

    private void renameFiles(Folder folder) {

        String renamedFileName;
        boolean success;

        for (File file : folder.getFILES()) {

            renamedFileName = rename(file.getName());
            success = file.renameTo(renamedFileName);

            if (success) {
              System.out.println("Файл - " + file.getName() + " -> " + renamedFileName);
          } else {
              System.out.println("Не удалось переименовать файл - " + file.getName());
          }

        }

    }

    private void renameDir(Folder f) {

        String renamedDir;
        boolean success;

        for (Folder folder : f.getDIRECTORIES()) {

            renamedDir = rename(folder.getName());
            success = folder.renameTo(renamedDir);

            if (success) {
                System.out.println("Папка - " + folder.getName() + " -> " + renamedDir);
            } else {
                System.out.println("Не удалось переименовать директорию - " + folder.getName());
            }

        }

    }

    public void setPattern(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

}
