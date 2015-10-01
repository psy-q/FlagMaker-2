package flagmaker;

import javafx.fxml.FXML;
import javax.swing.JOptionPane;
import javafx.scene.control.TextField;

public class MainWindowController
{
    @FXML private TextField txtRatioHeight;

    @FXML
    public void SetRatio()
    {
	JOptionPane.showMessageDialog(null, txtRatioHeight.getText(), "FlagMaker", JOptionPane.INFORMATION_MESSAGE);
    }
}
