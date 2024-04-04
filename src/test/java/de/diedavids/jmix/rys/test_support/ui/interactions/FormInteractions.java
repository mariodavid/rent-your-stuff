package de.diedavids.jmix.rys.test_support.ui.interactions;

import io.jmix.flowui.util.OperationResult;
import io.jmix.flowui.view.StandardDetailView;

public class FormInteractions {

    private final StandardDetailView<?> detailView;
    private final ComponentInteractions components;

    public FormInteractions(StandardDetailView<?> detailView) {
        this.detailView = detailView;
        this.components = ComponentInteractions.of(detailView);
    }

    public static FormInteractions of(StandardDetailView<?> detailView) {
        return new FormInteractions(detailView);
    }


    public OperationResult saveForm() {
        return detailView.closeWithSave();
    }

    public ComponentInteractions components() {
        return components;
    }

}
