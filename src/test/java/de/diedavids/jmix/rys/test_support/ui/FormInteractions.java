package de.diedavids.jmix.rys.test_support.ui;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.util.OperationResult;
import org.jetbrains.annotations.Nullable;

public class FormInteractions {
    private final StandardEditor editor;

    public FormInteractions(StandardEditor editor) {
        this.editor = editor;
    }

    public static FormInteractions of(StandardEditor editor) {
        return new FormInteractions(editor);
    }

    @Nullable
    TextField<String> textField(String componentId) {
        return (TextField<String>) editor.getWindow().getComponent(componentId);
    }

    @Nullable
    Button button(String buttonId) {
        return (Button) editor.getWindow().getComponent(buttonId);
    }

    public void setFieldValue(String componentId, String value) {
        textField(componentId).setValue(value);
    }

    public OperationResult saveForm() {
        return editor.closeWithCommit();
    }
}
