package de.diedavids.jmix.rys.test_support.ui;

import io.jmix.core.metamodel.datatype.impl.EnumClass;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.ComboBox;
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
    TextField<Number> numberField(String componentId) {
        return (TextField<Number>) editor.getWindow().getComponent(componentId);
    }

    ComboBox<EnumClass<String>> comboBoxField(String componentId) {
        return (ComboBox<EnumClass<String>>) editor.getWindow().getComponent(componentId);
    }

    @Nullable
    Button button(String buttonId) {
        return (Button) editor.getWindow().getComponent(buttonId);
    }

    public void setTextFieldValue(String componentId, String value) {
        textField(componentId).setValue(value);
    }
    public void setNumberFieldValue(String componentId, Number value) {
        numberField(componentId).setValue(value);
    }

    public OperationResult saveForm() {
        return editor.closeWithCommit();
    }

    public void setEnumFieldValue(String componentId, EnumClass<String> value) {
        comboBoxField(componentId).setValue(value);
    }
}
