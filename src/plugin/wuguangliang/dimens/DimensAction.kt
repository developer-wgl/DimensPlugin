package plugin.wuguangliang.dimens

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.ui.Messages
import org.apache.http.util.TextUtils
import plugin.wuguangliang.dimens.gui.DimenGui

class DimensAction : AnAction() {

    private val findDimenRex = Regex("<dimen\\s*name\\s*=\\s*\"([0-9a-zA-Z._]{1,60})\\\">([-0-9.]{1,7})(dp|sp)</dimen>")

    override fun actionPerformed(event: AnActionEvent) {
        val mEditor = event.getData(PlatformDataKeys.EDITOR) ?: return
        val model = mEditor.selectionModel
        val selectedText = model.selectedText
        if (TextUtils.isEmpty(selectedText)) {
            Messages.showMessageDialog("Please select the dimens and operate!", "Information", Messages.getInformationIcon())
            return
        }

        val dimensDialog = DimenGui()
        dimensDialog.multipleJTextField.text = (3 / 2.75).toString()
        dimensDialog.convertButton.addActionListener {
            if (TextUtils.isEmpty(dimensDialog.multipleJTextField.text)) {
                dimensDialog.msgLabel.text = "Please enter the conversion multiple!"
                return@addActionListener
            }
            val multiple: Double
            try {
                multiple = dimensDialog.multipleJTextField.text.toDouble()
            } catch (e: Exception) {
                dimensDialog.msgLabel.text = "Conversion multiples do not meet the rules!"
                return@addActionListener
            }
            try {
                val ss = StringBuilder()
                val m = findDimenRex.findAll(selectedText.toString())
                m.groupBy {
                    ss.appendln("<dimen name=\"${it.groupValues[1]}\">" +
                            "${String.format("%.2f", it.groupValues[2].toDouble()
                                    * multiple)}${it.groupValues[3]}</dimen>")
                }
                if (TextUtils.isEmpty(ss)) {
                    dimensDialog.msgLabel.text = "Please select the dimens and operate!"
                    return@addActionListener
                }
                print(ss.toString())
                dimensDialog.outText.text = ss.toString()
                dimensDialog.msgLabel.text = "Converted!"
                return@addActionListener
            } catch (e: Exception) {
                dimensDialog.msgLabel.text = "An unknown error has occurred and the log is as follows!"
                dimensDialog.outText.text = e.toString()
                e.printStackTrace()
            }
        }
        dimensDialog.pack()
        dimensDialog.isVisible = true
        if (!TextUtils.isEmpty(dimensDialog.multipleJTextField.text)) {
            dimensDialog.convertButton.doClick()
        }
    }
}