package de.diedavids.jmix.rys.test_support.ui.interactions;


import com.vaadin.flow.component.button.Button;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.view.View;

import java.util.List;
import java.util.stream.Stream;

public class DataGridInteractions<E> {

    private final DataGrid<E> dataGrid;
    private final View view;

    public <V extends View> DataGridInteractions(V view, DataGrid dataGrid) {
        this.view = view;
        this.dataGrid = dataGrid;
    }

    public static <E, V extends View> DataGridInteractions<E> of(V view, String componentId) {
        return new DataGridInteractions(
                view,
                (DataGrid) UiComponentUtils.getComponent(view, componentId)
        );
    }

    Button button(String buttonId) {
        return (Button) UiComponentUtils.getComponent(view, buttonId);
    }

    public E firstItem() {
        return dataGrid.getItems().getItems().stream().findFirst().orElseThrow();
    }

    public void selectFirst() {
        dataGrid.select(firstItem());
    }

    public void edit(E entity) {
        dataGrid.select(entity);
        click("editBtn");
    }

    public void click(String buttonId) {
        button(buttonId).click();
    }

    public void create(String buttonId) {
        click(buttonId);
    }

    public Stream<E> items() {
        return dataGrid.getItems().getItems().stream();
    }

    public void select(E entity) {
        dataGrid.select(entity);
    }

    public void select(List<E> entities) {
        dataGrid.select(entities);
    }

    public boolean enabled(String buttonId) {
        return button(buttonId).isEnabled();
    }

    public DataGrid<E> delegate() {
        return dataGrid;
    }

    public void selectAndClick(E entity, String buttonId) {
        select(entity);
        click(buttonId);
    }
}
