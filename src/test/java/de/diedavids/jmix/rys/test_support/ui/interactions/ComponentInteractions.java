package de.diedavids.jmix.rys.test_support.ui.interactions;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextArea;
import de.diedavids.jmix.rys.product.PriceUnit;
import io.jmix.core.metamodel.datatype.EnumClass;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.codeeditor.CodeEditor;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.combobox.JmixComboBox;
import io.jmix.flowui.component.datepicker.TypedDatePicker;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.main.JmixListMenu;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.component.tabsheet.JmixTabSheet;
import io.jmix.flowui.component.textfield.JmixPasswordField;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.view.View;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComponentInteractions {

    private final Component component;

    private ComponentInteractions(Component component) {
        this.component = component;
    }

    public static ComponentInteractions of(View<?> detailView) {
        return new ComponentInteractions(detailView.getContent());
    }

    public static ComponentInteractions of(Component component) {
        return new ComponentInteractions(component);
    }

    public TypedTextField<String> textField(String componentId) {
        return (TypedTextField<String>) getComponentNN(componentId);
    }

    public TextArea textAreaField(String componentId) {
        return (TextArea) getComponentNN(componentId);
    }

    public NativeLabel labelField(String componentId) {
        return (NativeLabel) getComponentNN(componentId);
    }

    public TypedTextField<Number> numberField(String componentId) {
        return (TypedTextField<Number>) getComponentNN(componentId);
    }
    public TypedTextField<BigDecimal> bigDecimalField(String componentId) {
        return (TypedTextField<BigDecimal>) getComponentNN(componentId);
    }

    public TypedTextField<Long> longField(String componentId) {
        return (TypedTextField<Long>) getComponentNN(componentId);
    }

    public ComboBox<EnumClass<String>> stringEnumComboBoxField(String componentId) {
        return (ComboBox<EnumClass<String>>) getComponentNN(componentId);
    }

    public ComboBox<EnumClass<Integer>> integerEnumComboBoxField(String componentId) {
        return (ComboBox<EnumClass<Integer>>) getComponentNN(componentId);
    }

    public <T> EntityComboBox<T> entityComboBoxField(String componentId) {
        return (EntityComboBox<T>) getComponentNN(componentId);
    }

    public DatePicker datePicker(String componentId) {
        return (DatePicker) getComponentNN(componentId);
    }

    public void setDatePickerValue(String componentId, LocalDate value) {
        datePicker(componentId).setValue(value);
    }

    public DateTimePicker dateTimePicker(String componentId) {
        return (DateTimePicker) getComponentNN(componentId);
    }

    private Component getComponentNN(String componentId) {
        return UiComponentUtils.findComponent(component, componentId).orElseThrow();
    }


    public Button button(String buttonId) {
        return (Button) getComponentNN(buttonId);
    }

    public Checkbox checkbox(String checkboxId) {
        return (Checkbox) getComponentNN(checkboxId);
    }

    public NativeLabel label(String componentId) {
        return (NativeLabel) getComponentNN(componentId);
    }

    public <T> DataGrid<T> dataGrid(String componentId) {
        return (DataGrid<T>) getComponentNN(componentId);
    }


    public void setEnumFieldStringValue(String componentId, EnumClass<String> value) {
        stringEnumComboBoxField(componentId).setValue(value);
    }

    public void setEnumFieldIntegerValue(String componentId, EnumClass<Integer> value) {
        integerEnumComboBoxField(componentId).setValue(value);
    }


    public <T> Stream<T> getEntityComboBoxValues(String componentId) {
        EntityComboBox<T> field = entityComboBoxField(componentId);
        return field.getGenericDataView().getItems();
    }

    public <T> void setEntityComboBoxFieldValue(String componentId, T entity) {
        entityComboBoxField(componentId).setValueFromClient(entity);
    }


    public <T> void setComboBoxFieldValue(String componentId, T entity) {
        comboBoxField(componentId).setValue(entity);
    }

    public boolean isVisible(String componentId) {
        return UiComponentUtils.isComponentVisible(getComponentNN(componentId));
    }

    public boolean isEnabled(String componentId) {
        return UiComponentUtils.isComponentEnabled(getComponentNN(componentId));
    }

    public void click(String componentId) {
        button(componentId).click();
    }

    public <T> JmixComboBox<T> comboBoxField(String componentId) {
        return (JmixComboBox<T>) getComponentNN(componentId);
    }

    public <T> List<T> getComboBoxValues(String componentId) {
        ComboBox<T> field = comboBoxField(componentId);
        return field.getListDataView().getItems().collect(Collectors.toList());
    }

    public <T> void setComboBoxValues(String componentId, EnumClass<String> value) {
        ComboBox<T> field = comboBoxField(componentId);
        field.setValue((T) value);
    }

    public String getSpanValue(String componentId) {
        return span(componentId).getText();
    }

    public Span span(String componentId) {
        return (Span) getComponentNN(componentId);
    }

    public <T> MultiSelectComboBox<T> multiSelectComboBox(String componentId) {
        return (MultiSelectComboBox<T>) getComponentNN(componentId);
    }

    public <T> void setMultiSelectComboBoxValues(String componentId, List<T> values) {
        MultiSelectComboBox<T> field = multiSelectComboBox(componentId);
        field.setValue(values);
    }

    public <T> RadioButtonGroup<T> radioButtonGroup(String componentId) {
        return (RadioButtonGroup<T>) getComponentNN(componentId);
    }

    public <T> void setRadioButtonGroupValue(String componentId, T value) {
        RadioButtonGroup<T> field = radioButtonGroup(componentId);
        field.setValue(value);
    }

    public void setTextFieldValue(String componentId, String value) {
        textField(componentId).setValue(value);
    }

    public JmixTabSheet tabSheet(String componentId) {
        return (JmixTabSheet) getComponentNN(componentId);
    }

    public void selectTab(String tabSheetId, String tabId) {
        JmixTabSheet tabSheet = tabSheet(tabSheetId);
        Tab tab = tabSheet.getChildren()
                .filter(it -> it instanceof Tab)
                .filter(it -> it.getId().isPresent() && it.getId().get().equals(tabId))
                .findFirst()
                .map(it -> (Tab) it)
                .orElseThrow();
        tabSheet.setSelectedTab(tab);
    }

    public <T> void setDateFieldValue(String componentId, LocalDate date) {
        TypedDatePicker dateField = dateField(componentId);
        dateField.setValue(date);
    }

    public <T> TypedDatePicker dateField(String componentId) {
        return (TypedDatePicker) getComponentNN(componentId);
    }


    public <T> JmixSelect<T> selectField(String componentId) {
        return (JmixSelect<T>) getComponentNN(componentId);
    }

    public CodeEditor codeEditor(String componentId) {
        return (CodeEditor) getComponentNN(componentId);
    }

    public JmixListMenu listMenu(String menuId) {
        return (JmixListMenu) getComponentNN(menuId);
    }

    private JmixPasswordField passwordField(String componentId) {
        return (JmixPasswordField) getComponentNN(componentId);
    }
    public void clickOnMenuItem(String menuId, String menuItemId) {
        var menu = listMenu(menuId);
        var item = menu.getMenuItem(menuItemId);
        item.getClickHandler().accept(item);
    }

    public void setPasswordFieldValue(String componentId, String value) {
        passwordField(componentId).setValue(value);
    }

}
