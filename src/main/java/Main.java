import controller.MainController;
import model.Renamer;
import view.UserView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        Renamer renamer = new Renamer();
        MainController controller = new MainController(renamer);
        UserView userView = new UserView(controller);
        SwingUtilities.invokeLater(userView::init);

    }

}
