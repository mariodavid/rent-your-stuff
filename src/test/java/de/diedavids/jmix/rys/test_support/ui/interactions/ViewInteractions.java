package de.diedavids.jmix.rys.test_support.ui.interactions;

import com.vaadin.flow.router.QueryParameters;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.testassist.UiTestUtils;
import io.jmix.flowui.view.View;



public class ViewInteractions {

    private final ViewNavigators viewNavigators;

    public ViewInteractions(
            ViewNavigators viewNavigators
    ) {
        this.viewNavigators = viewNavigators;
    }

    public static ViewInteractions of(ViewNavigators viewNavigators) {
        return new ViewInteractions(viewNavigators);
    }


    public <T> T findCurrentView(Class<T> viewClass) {
        View<?> currentView = UiTestUtils.getCurrentView();

        if (currentView == null) {
            return null;
        }

        if (viewClass.isAssignableFrom(currentView.getClass())) {
            return (T) currentView;
        }

        return null;
    }

    public <V extends View<?>> V open(Class<V> viewClass) {
        viewNavigators.view(viewClass).navigate();
        return findCurrentView(viewClass);
    }

    public <T> boolean isOpen(Class<T> viewClass) {
        return findCurrentView(viewClass) != null;
    }

    public <V extends View<?>, E> V openDetailForEdit(Class<V> detailViewClass, Class<E> entityClass, E entity) {
        viewNavigators.detailView(entityClass)
                .withViewClass(detailViewClass)
                .editEntity(entity)
                .navigate();
        return findCurrentView(detailViewClass);
    }

    public <V extends View<?>, E> V openDetailForNew(Class<V> detailViewClass, Class<E> entityClass) {
        viewNavigators.detailView(entityClass)
                .withViewClass(detailViewClass)
                .newEntity()
                .navigate();
        return findCurrentView(detailViewClass);
    }

    public <V extends View<?>, E> V openListView(Class<V> listViewClass, Class<E> entityClass) {
        viewNavigators.listView(entityClass)
                .withViewClass(listViewClass)
                .navigate();
        return findCurrentView(listViewClass);
    }

    public <V extends View<?>> V openStandardView(Class<V> standardViewClass) {
        return openStandardView(standardViewClass, QueryParameters.empty());
    }

    public <V extends View<?>> V openStandardView(Class<V> standardViewClass, QueryParameters queryParameters) {
        viewNavigators.view(standardViewClass)
                .withQueryParameters(queryParameters)
                .navigate();
        return findCurrentView(standardViewClass);
    }
}
