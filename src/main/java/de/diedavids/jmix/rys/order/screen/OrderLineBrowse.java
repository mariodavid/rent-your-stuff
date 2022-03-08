package de.diedavids.jmix.rys.order.screen;

import com.vaadin.v7.shared.ui.calendar.CalendarState;
import de.diedavids.jmix.rys.order.OrderLine;
import de.diedavids.jmix.rys.order.screen.calendar.CalendarMode;
import de.diedavids.jmix.rys.order.screen.calendar.CalendarNavigationMode;
import de.diedavids.jmix.rys.order.screen.calendar.CalendarNavigators;
import de.diedavids.jmix.rys.order.screen.calendar.CalendarScreenAdjustment;
import de.diedavids.jmix.rys.product.Product;
import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.core.TimeSource;
import io.jmix.core.metamodel.datatype.DatatypeFormatter;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.*;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import de.diedavids.jmix.rys.order.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static de.diedavids.jmix.rys.order.screen.calendar.CalendarNavigationMode.*;
import static de.diedavids.jmix.rys.order.screen.calendar.RelativeDates.startOfWeek;

@UiController("rys_OrderLine.browse")
@UiDescriptor("order-line-browse.xml")
public class OrderLineBrowse extends StandardLookup<OrderLine> {

    @Autowired
    protected Calendar<LocalDateTime> calendar;
    @Autowired
    protected ScreenBuilders screenBuilders;
    @Autowired
    protected CollectionContainer<OrderLineEdit> orderLinesDc;
    @Autowired
    protected CollectionLoader<OrderLine> orderLinesDl;
    @Autowired
    protected DataContext dataContext;
//    @Autowired
//    protected CheckBoxGroup<OrderType> typeMultiFilter;
    @Autowired
    protected RadioButtonGroup<CalendarMode> calendarMode;
    @Autowired
    protected TimeSource timeSource;
    @Autowired
    protected Label<String> calendarTitle;
    @Autowired
    protected CalendarNavigators calendarNavigators;
    @Autowired
    protected DatePicker<LocalDate> calendarNavigator;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    protected DatatypeFormatter datatypeFormatter;
    @Autowired
    protected Notifications notifications;
    @Autowired
    protected MessageBundle messageBundle;
    @Autowired
    protected Messages messages;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private EntityComboBox<Product> productField;

    @Subscribe
    protected void onInit(InitEvent event) {
        initTypeFilter();
        initSortCalendarEventsInMonthlyView();
    }

    private void initTypeFilter() {
//        typeMultiFilter.setOptionsEnum(OrderType.class);
//        typeMultiFilter.setValue(EnumSet.allOf(OrderType.class));
//        typeMultiFilter.setOptionIconProvider(o -> OrderTypeIcon.valueOf(o.getIcon()).source());
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        current(CalendarMode.WEEK);
    }

    @SuppressWarnings("deprecation")
    private void initSortCalendarEventsInMonthlyView() {
        calendar.unwrap(com.vaadin.v7.ui.Calendar.class)
                .setEventSortOrder(CalendarState.EventSortOrder.START_DATE_DESC);
    }


    @Subscribe("calendar")
    protected void onCalendarCalendarDayClick(Calendar.CalendarDateClickEvent<LocalDateTime> event) {
        atDate(
                CalendarMode.DAY,
                event.getDate().toLocalDate()
        );
    }

    @Subscribe("calendar")
    protected void onCalendarCalendarWeekClick(Calendar.CalendarWeekClickEvent<LocalDateTime> event) {
        atDate(
                CalendarMode.WEEK,
                startOfWeek(
                        event.getYear(),
                        event.getWeek(),
                        currentAuthentication.getLocale()
                )
        );
    }

    @Subscribe("navigatorPrevious")
    protected void onNavigatorPreviousClick(Button.ClickEvent event) {
        previous(calendarMode.getValue());
    }

    @Subscribe("navigatorNext")
    protected void onNavigatorNextClick(Button.ClickEvent event) {
        next(calendarMode.getValue());
    }

    @Subscribe("navigatorCurrent")
    protected void onNavigatorCurrentClick(Button.ClickEvent event) {
        current(calendarMode.getValue());
    }

    @Subscribe("calendarNavigator")
    protected void onCalendarRangePickerValueChange(HasValue.ValueChangeEvent<LocalDate> event) {
        if (event.isUserOriginated()) {
            atDate(calendarMode.getValue(), event.getValue());
        }
    }

    private void current(CalendarMode calendarMode) {
        change(calendarMode, AT_DATE, timeSource.now().toLocalDate());
    }

    private void atDate(CalendarMode calendarMode, LocalDate date) {
        change(calendarMode, AT_DATE, date);
    }

    private void next(CalendarMode calendarMode) {
        change(calendarMode, NEXT, calendarNavigator.getValue());
    }

    private void previous(CalendarMode calendarMode) {
        change(calendarMode, PREVIOUS, calendarNavigator.getValue());
    }

    private void change(CalendarMode calendarMode, CalendarNavigationMode navigationMode, LocalDate referenceDate) {
        this.calendarMode.setValue(calendarMode);

        calendarNavigators
                .forMode(
                        CalendarScreenAdjustment.of(calendar, calendarNavigator, calendarTitle),
                        datatypeFormatter,
                        calendarMode
                )
                .navigate(navigationMode, referenceDate);

        loadEvents();
    }


    @Subscribe("calendarMode")
    protected void onCalendarRangeValueChange(HasValue.ValueChangeEvent event) {
        if (event.isUserOriginated()) {
            atDate((CalendarMode) event.getValue(), calendarNavigator.getValue());
        }
    }

    private void loadEvents() {
        orderLinesDl.setParameter("startsAt", calendar.getStartDate());
        orderLinesDl.setParameter("endsAt", calendar.getEndDate());
        orderLinesDl.setParameter("product", productField.getValue());
        orderLinesDl.load();
    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Calendar Order Event Click
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Subscribe("calendar")
    protected void onCalendarCalendarDayClick(Calendar.CalendarDayClickEvent<LocalDateTime> event) {
        screenBuilders.lookup(Product.class, this)
                .withOpenMode(OpenMode.DIALOG)
                .withSelectHandler(products -> {
                    Screen orderEditor = screenBuilders.editor(Order.class, this)
                            .newEntity()
                            .withInitializer(order -> {
                                OrderLine orderLine = dataContext.create(OrderLine.class);
                                orderLine.setStockItem(products.stream().findFirst().get().getStockItems().get(0));
                                orderLine.setStartsAt(event.getDate());
                                orderLine.setEndsAt(event.getDate().plusHours(1));
                                orderLine.setOrder(order);
                                order.setOrderLines(List.of(orderLine));


                            })
                            .withOpenMode(OpenMode.DIALOG)
                            .build();

                    orderEditor.addAfterCloseListener(afterCloseEvent -> {
                        if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)) {
                            getScreenData().loadAll();
                        }
                    });

                    orderEditor.show();
                })
                .show();

    }

    @Subscribe("calendar")
    protected void onCalendarCalendarEventClick(Calendar.CalendarEventClickEvent<LocalDateTime> event) {

        Screen orderEditor = screenBuilders.editor(Order.class, this)
                .editEntity(((OrderLine) event.getEntity()).getOrder())
                .withOpenMode(OpenMode.DIALOG)
                .build();

        orderEditor.addAfterCloseListener(afterCloseEvent -> {
            if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)) {
                getScreenData().loadAll();
            }
        });

        orderEditor.show();
    }

    @Subscribe("productField")
    public void onProductFieldValueChange(HasValue.ValueChangeEvent<Product> event) {
        loadEvents();
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Filter for Order Types
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

//    @Subscribe("typeMultiFilter")
//    protected void onTypeMultiFilterValueChange(HasValue.ValueChangeEvent event) {
//
//        if (event.getValue() == null) {
//            ordersCalendarDl.removeParameter("type");
//        } else if (CollectionUtils.isEmpty((Set<OrderType>) event.getValue())) {
//            ordersCalendarDl.setParameter("type", Collections.singleton(""));
//        } else {
//            ordersCalendarDl.setParameter("type", event.getValue());
//        }
//        loadEvents();
//    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Order Changes through Calendar Event Adjustments
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

//    @Subscribe("calendar")
//    protected void onCalendarCalendarEventResize(Calendar.CalendarEventResizeEvent<LocalDateTime> event) {
//        updateOrder(event.getEntity(), event.getNewStart(), event.getNewEnd());
//    }
//
//    @Subscribe("calendar")
//    protected void onCalendarCalendarEventMove(Calendar.CalendarEventMoveEvent<LocalDateTime> event) {
//        updateOrder(event.getEntity(), event.getNewStart(), event.getNewEnd());
//    }
//
//    private void updateOrder(Object entity, LocalDateTime newStart, LocalDateTime newEnd) {
//        Order order = (Order) entity;
//        order.setOrderStart(newStart);
//        order.setOrderEnd(newEnd);
//        dataContext.commit();
//        notifications.create(Notifications.NotificationType.TRAY)
//                .withCaption(
//                        messageBundle.formatMessage(
//                                "orderUpdated",
//                                messages.getMessage(order.getType()),
//                                order.getPetName()
//                        )
//                )
//                .show();
//    }
}