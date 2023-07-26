package me.myster.cap.tool;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class LatexFormat extends DialogWrapper {
    private final Editor editor;
    private final SelectionModel selectionModel;
    private JPanel contentPane;
    private JButton catchCharButton;
    private JButton overLineButton;
    private JButton squareRootButton;
    private JButton underLineButton;
    private JTextField formattedText;
    private JButton degreeButton;
    private JButton rightarrowButton;
    private JButton timesButton;
    private JButton divButton;
    private JButton triangleButton;
    private JButton similarButton;
    private JButton congruenceButton;
    private JButton piButton;
    private JButton angleButton;
    private JButton perpendicularButton;
    private JButton arcButton;
    private JButton greaterThanOrEqualButton;
    private JButton lessThanOrEqualButton;
    private JButton primeButton;
    private JButton absoluteValueButton;
    private JButton notEqualButton;
    private JButton selectAllButton;

    public LatexFormat(Editor editor) {
        super(true);
        this.editor = editor;
        this.selectionModel = editor.getSelectionModel();
        setTitle("Latex Format");
        setModal(true);

        for (Component component : ((JPanel) contentPane.getComponent(0)).getComponents())
            if (!component.equals(formattedText)) component.setFocusable(false);

        catchCharButton.addActionListener(e -> onCommonButton("$", "$"));
        underLineButton.addActionListener(e -> onCommonButton("\\\\underline{", "}"));
        overLineButton.addActionListener(e -> onCommonButton("\\\\overline{", "}"));
        squareRootButton.addActionListener(e -> onCommonButton("\\\\sqrt{", "}"));
        degreeButton.addActionListener(e -> onCommonButton("", "^\\\\circ "));
        rightarrowButton.addActionListener(e -> onCommonButton("", "\\\\Rightarrow "));
        timesButton.addActionListener(e -> onCommonButton("", "\\\\times "));
        divButton.addActionListener(e -> onCommonButton("", "\\\\div "));
        triangleButton.addActionListener(e -> onCommonButton("\\\\triangle ", ""));
        similarButton.addActionListener(e -> onCommonButton("", "\\\\sim "));
        congruenceButton.addActionListener(e -> onCommonButton("", "\\\\cong "));
        piButton.addActionListener(e -> onCommonButton("", "\\\\pi "));
        angleButton.addActionListener(e -> onCommonButton("\\\\angle ", ""));
        perpendicularButton.addActionListener(e -> onCommonButton("", "\\\\perp "));
        arcButton.addActionListener(e -> onCommonButton("\\\\overset{\\\\frown}{", "}"));
        greaterThanOrEqualButton.addActionListener(e -> onCommonButton("", "\\\\geq "));
        lessThanOrEqualButton.addActionListener(e -> onCommonButton("", "\\\\leq "));
        primeButton.addActionListener(e -> onCommonButton("", "\\\\prime "));
        absoluteValueButton.addActionListener(e -> onCommonButton("\\\\lvert ", " \\\\rvert "));
        notEqualButton.addActionListener(e -> onCommonButton("", "\\\\neq"));
        selectAllButton.addActionListener(e -> formattedText.selectAll());

        formattedText.setText(selectionModel.getSelectedText());
        formattedText.selectAll();

        init();
    }

    @Override
    protected JComponent createCenterPanel() {
        return contentPane;
    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return formattedText;
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();
        WriteCommandAction.runWriteCommandAction(editor.getProject(), () -> {
            editor.getDocument().replaceString(selectionModel.getSelectionStart(), selectionModel.getSelectionEnd(), formattedText.getText());
        });
    }

    private void onCommonButton(String prefix, String suffix) {
        int currentSelectionStart = formattedText.getSelectionStart();
        int currentSelectionEnd = formattedText.getSelectionEnd();
        formattedText.replaceSelection(prefix + (formattedText.getSelectedText() != null ? formattedText.getSelectedText() : "") + suffix);
        formattedText.select(currentSelectionStart, currentSelectionEnd + prefix.length() + suffix.length());
    }
}
