package model.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class File implements RenameInterface {

    private java.io.File file;
    private String name;
    private String extension;

    @Override
    public boolean renameTo(String name) {
        return file.renameTo(new java.io.File(file.getParent() + "\\" + name + "." + extension));
    }

}
