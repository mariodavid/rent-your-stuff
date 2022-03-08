package de.diedavids.jmix.rys.order.screen.calendar;


import io.jmix.core.metamodel.datatype.DatatypeFormatter;
import io.jmix.core.security.CurrentAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("petclinic_CalendarNavigators")
public class CalendarNavigators {

    @Autowired
    private CurrentAuthentication currentAuthentication;

    public CalendarNavigation forMode(
            CalendarScreenAdjustment screenAdjustment,
            DatatypeFormatter datatypeFormatter,
            CalendarMode mode
    ) {
        switch (mode) {
            case DAY: return new DayCalendarNavigation(screenAdjustment, datatypeFormatter);
            case WEEK: return new WeekCalendarNavigation(screenAdjustment, currentAuthentication.getLocale());
            case MONTH: return new MonthCalendarNavigation(screenAdjustment, currentAuthentication.getLocale());
        }

        throw new IllegalStateException("Calendar Mode has to be set");
    }

}
