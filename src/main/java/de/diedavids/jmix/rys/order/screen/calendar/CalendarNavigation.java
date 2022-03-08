package de.diedavids.jmix.rys.order.screen.calendar;

import java.time.LocalDate;

public interface CalendarNavigation {
    void navigate(CalendarNavigationMode navigationMode, LocalDate referenceDate);
}
