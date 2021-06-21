package model.data;

import com.google.common.io.Files;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
public class Folder implements RenameInterface {

    @Getter
    private java.io.File folder;
    @Getter
    private String name;

    @Getter
    private final ArrayList<File> FILES =  new ArrayList<>();
    @Getter
    private final ArrayList<Folder> DIRECTORIES = new ArrayList<>();

    public Folder(String path) throws NullPointerException {

        folder = new java.io.File(path);
        name = folder.getName();

        java.io.File[] files = folder.listFiles();
        if (files != null) {

            for (java.io.File file : files) {

                if (!file.isDirectory()) {
                    String fileName = file.getName();
                    //noinspection UnstableApiUsage
                    this.FILES.add(new File(file, Files.getNameWithoutExtension(fileName), Files.getFileExtension(fileName)));
                } else {
                    DIRECTORIES.add(new Folder(file, file.getName()));
                }

            }

        } else {
            throw new NullPointerException("Path doesn't contain reference");
        }

    }

    private Folder(java.io.File folder, String folderName) {
        this.folder = folder;
        this.name = folderName;
    }

    @Override
    public boolean renameTo(String name) {
        return folder.renameTo(new java.io.File(folder.getParent() + "\\" + name));
    }
}
