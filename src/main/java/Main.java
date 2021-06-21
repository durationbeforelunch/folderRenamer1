import controller.MainController;
import model.Renamer;
import observers.ObserverManager;
import view.IView;
import view.UserView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        Renamer renamer = new Renamer();
        MainController controller = new MainController(renamer);
        IView userView = new UserView(controller);
        ObserverManager manager = new ObserverManager(renamer);
        manager.addObserver(userView);
        SwingUtilities.invokeLater(userView::init);

    }

}
