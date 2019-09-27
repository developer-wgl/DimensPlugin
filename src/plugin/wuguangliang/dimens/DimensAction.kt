package plugin.wuguangliang.dimens

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.ui.Messages
import org.apache.http.util.TextUtils


class DimensAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val mEditor = event.getData(PlatformDataKeys.EDITOR) ?: return
        val model = mEditor.selectionModel
        val selectedText = model.selectedText
        if (TextUtils.isEmpty(selectedText)) {
            Messages.showMessageDialog("请选中dimens后操作！", "Information", Messages.getInformationIcon())
            return
        }

        val rex = Regex("<dimen\\s*name\\s*=\\s*\"([0-9a-zA-Z._]*)\\\">([-0-9.]*)(dp|sp)</dimen>")
        val m = rex.findAll(selectedText.toString())
        val ss = StringBuilder()
        m.groupBy {
            //            ss.appendln("group ${it.groupValues[0]} : ${it.groupValues[1]}  :  ${it.groupValues[2]}")
            ss.appendln("<dimen name=\"${it.groupValues[1]}\">" +
                    "${String.format("%.2f", it.groupValues[2].toDouble() * 3 / 2.75)}${it.groupValues[3]}</dimen>")
        }
        if (TextUtils.isEmpty(ss)) {
            Messages.showMessageDialog("请选中dimens后操作！", "Information", Messages.getInformationIcon())
            return
        }
        print(ss.toString())
        val dimensDialog = DimenGui(ss.toString())
        dimensDialog.pack()
        dimensDialog.isVisible = true
    }
}