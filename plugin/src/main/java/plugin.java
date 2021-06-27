
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;


public class plugin extends AnAction{
        @Override
    public void actionPerformed(AnActionEvent event) {
        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        String selectedText = editor.getSelectionModel().getSelectedText();
        Project project = event.getRequiredData(CommonDataKeys.PROJECT);
        if (selectedText != null) {
            Frame frame = new Frame(event, editor,selectedText);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } else {
            Messages.showMessageDialog("Selection is empty, could you please select something?", "Tweet Action", Messages.getInformationIcon());
        }
    }

    // https://pastebin.com/
    @Override
    public boolean isDumbAware() {
        return false;
    }
}

