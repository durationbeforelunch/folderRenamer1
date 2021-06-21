package observers;

public interface Observer {

    void updateProgressBarValue(int value);

    void updateProgressBarText(String text);

    void updateLogTextArea(String text);

}
