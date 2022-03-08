package de.diedavids.jmix.rys.order.screen.calendar;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static de.diedavids.jmix.rys.order.screen.calendar.MonthFormatter.fullMonthYear;
import static de.diedavids.jmix.rys.order.screen.calendar.RelativeDates.beginningOfMonth;
import static de.diedavids.jmix.rys.order.screen.calendar.RelativeDates.endOfMonth;


public class MonthCalendarNavigation implements CalendarNavigation {

    private final Locale locale;
    private final CalendarScreenAdjustment screenAdjustment;

    public MonthCalendarNavigation(
            CalendarScreenAdjustment screenAdjustment,
            Locale locale
    ) {
        this.screenAdjustment = screenAdjustment;
        this.locale = locale;
    }

    @Override
    public void navigate(CalendarNavigationMode navigationMode, LocalDate referenceDate) {
        LocalDate newMonthDate = navigationMode.calculate(ChronoUnit.MONTHS, referenceDate);
        YearMonth newMonth = YearMonth.from(newMonthDate);

        screenAdjustment.adjust(
                beginningOfMonth(newMonth),
                endOfMonth(newMonth),
                newMonthDate,
                fullMonthYear(newMonth, locale)
        );

    }
}
