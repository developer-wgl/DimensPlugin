package plugin.wuguangliang.dimens.gui;

import com.intellij.ui.components.JBTextArea;
import plugin.wuguangliang.dimens.utils.BrowserUtil;
import plugin.wuguangliang.dimens.utils.TestUtil;

import javax.swing.*;

public class DimenGui extends JDialog {
    private JPanel jp;
    private JBTextArea outText;
    private JTextField multipleJTextField;
    private JButton convert_button;
    private JLabel msg;
    private JButton helpButton;

    public DimenGui() {
        setContentPane(jp);
        helpButton.addActionListener(e -> {
            try {
                BrowserUtil.openByDefault("https://github.com/developer-wgl/DimensPlugin");
            } catch (Exception ex) {
                ex.printStackTrace();
                msg.setText("Open the browser exception, please copy it manually: https://github.com/developer-wgl/DimensPlugin");
            }
        });
    }

    public JBTextArea getOutText() {
        return outText;
    }

    public JTextField getMultipleJTextField() {
        return multipleJTextField;
    }

    public JButton getConvertButton() {
        return convert_button;
    }

    public JLabel getMsgLabel() {
        return msg;
    }

    public static void main(String[] args) {
        DimenGui jDialog = new DimenGui();
        jDialog.getConvertButton().addActionListener(e -> jDialog.getOutText().setText(TestUtil.testData));
        jDialog.pack();
        jDialog.setVisible(true);
    }
}
