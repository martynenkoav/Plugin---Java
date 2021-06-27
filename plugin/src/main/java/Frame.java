import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Frame extends JFrame
{
    private static final long serialVersionUID = 1L;

    private JButton    button1;
    private JButton    button2;
    private JButton    button3;

    public Frame(AnActionEvent _event, Editor _editor, String _text) {
        super("Button bar");
        createGUI(_event, _editor, _text);
    }

    public void createGUI(AnActionEvent _event, Editor _editor, String _text) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        button1 = new JButton("Button 1");
        panel.add(button1);

        button2 = new JButton("Button 2");
        panel.add(button2);

        button3 = new JButton("Button 3");
        panel.add(button3);


        button1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Messages.showMessageDialog("choose another button", "advertisment", Messages.getInformationIcon());
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                button1.setActionCommand("Button1");

        }});
        button2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String encoded = "";
                try {
                    encoded = URLEncoder.encode(_text, StandardCharsets.UTF_8.toString());
                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
                String url = String.format("https://twitter.com/intent/tweet?text=%s", encoded);
                BrowserUtil.browse(url);
                button2.setActionCommand(url);
            }
        });
        button3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event) {

      //          final Editor editor = event.getData(CommonDataKeys.EDITOR);
                String selectedText = _editor.getSelectionModel().getSelectedText();
                Project project = _event.getRequiredData(CommonDataKeys.PROJECT);
                Document document = _editor.getDocument();
                Caret primaryCaret = _editor.getCaretModel().getPrimaryCaret();

                WriteCommandAction.runWriteCommandAction(project, () -> {
                    try {
                        document.replaceString(
                                primaryCaret.getSelectionStart(), primaryCaret.getSelectionEnd(),
                                ""
                        );
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                });
                primaryCaret.removeSelection();
            }
        }
        );


        getContentPane().add(panel);
        setPreferredSize(new Dimension(320, 200));
    }


}