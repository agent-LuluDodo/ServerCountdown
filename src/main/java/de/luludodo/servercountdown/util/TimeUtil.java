package de.luludodo.servercountdown.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class TimeUtil {
    public static String generateTime(long millisecond, JavaPlugin plugin){
        List<String> active = plugin.getConfig().getStringList("Active");

        long decade = millisecond/1000/60/60/24/365/10;
        boolean decadeDone = false;
        boolean useDecade = false;

        long year = millisecond/1000/60/60/24/365;
        boolean yearDone = false;
        boolean useYear = false;

        long week = millisecond/1000/60/60/7;
        boolean weekDone = false;
        boolean useWeek = false;

        long day = millisecond/1000/60/60/24;
        boolean dayDone = false;
        boolean useDay = false;

        long hour = millisecond/1000/60/60;
        boolean hourDone = false;
        boolean useHour = false;

        long minute = millisecond/1000/60;
        boolean minuteDone = false;
        boolean useMinute = false;

        long second = millisecond/1000;
        boolean secondDone = false;
        boolean useSecond = false;

        boolean millisecondDone = false;
        boolean useMillisecond = false;

        if (active.contains("millisecond")) {
            useMillisecond = true;
        }
        if (active.contains("second")) {
            useSecond = true;
            if (useMillisecond) {
                millisecond %= 1000;
                millisecondDone = true;
            }
        }
        if (active.contains("minute")) {
            useMinute = true;
            if (useMillisecond && !millisecondDone) {
                millisecond = millisecond % 1000 % 60;
                millisecondDone = true;
            }
            if (useSecond) {
                second %= 60;
                secondDone = true;
            }
        }
        if (active.contains("hour")) {
            useHour = true;
            if (useMillisecond && !millisecondDone) {
                millisecond = millisecond % 1000 % 60 % 60;
                millisecondDone = true;
            }
            if (useSecond && !secondDone) {
                second = second % 60 % 60;
                secondDone = true;
            }
            if (useMinute) {
                minute %= 60;
                minuteDone = true;
            }
        }
        if (active.contains("day")) {
            useDay = true;
            if (useMillisecond && !millisecondDone) {
                millisecond = millisecond % 1000 % 60 % 60 % 24;
                millisecondDone = true;
            }
            if (useSecond && !secondDone) {
                second = second % 60 % 60 % 24;
                secondDone = true;
            }
            if (useMinute && !minuteDone) {
                minute = minute % 60 % 24;
                minuteDone = true;
            }
            if (useHour) {
                hour %= 24;
                hourDone = true;
            }
        }
        if (active.contains("week")) {
            useWeek = true;
            if (useMillisecond && !millisecondDone) {
                millisecond = (millisecond % 1000 % 60 % 60 % 24 % 7);
                millisecondDone = true;
            }
            if (useSecond && !secondDone) {
                second = (second % 60 % 60 % 24 % 7);
                secondDone = true;
            }
            if (useMinute && !minuteDone) {
                minute = (minute % 60 % 24 % 7);
                minuteDone = true;
            }
            if (useHour && !hourDone) {
                hour = (hour % 24 % 7);
                hourDone = true;
            }
            if (useDay) {
                day %= 7;
                dayDone = true;
            }
        }
        if (active.contains("year")) {
            useYear = true;
            if (useMillisecond && !millisecondDone) {
                millisecond = millisecond % 1000 % 60 % 60 % 24 % 365;
                millisecondDone = true;
            }
            if (useSecond && !secondDone) {
                second = second % 60 % 60 % 24 % 365;
                secondDone = true;
            }
            if (useMinute && !minuteDone) {
                minute = minute % 60 % 24 % 365;
                minuteDone = true;
            }
            if (useHour && !hourDone) {
                hour = hour % 24 % 365;
                hourDone = true;
            }
            if (useDay && !dayDone) {
                day %= 365;
                dayDone = true;
            }
            if (useWeek) {
                week = (long)(week % 50.8571429);
                weekDone = true;
            }
        }
        if (active.contains("decade")) {
            useDecade = true;
            if (useMillisecond && !millisecondDone) {
                millisecond = millisecond % 1000 % 60 % 60 % 24 % 365 % 10;
                millisecondDone = true;
            }
            if (useSecond && !secondDone) {
                second = second % 60 % 60 % 24 % 365 % 10;
                secondDone = true;
            }
            if (useMinute && !minuteDone) {
                minute = minute % 60 % 24 % 365 % 10;
                minuteDone = true;
            }
            if (useHour && !hourDone) {
                hour = hour % 24 % 365 % 10;
                hourDone = true;
            }
            if (useDay && !dayDone) {
                day %= 365 % 10;
                dayDone = true;
            }
            if (useWeek && !weekDone) {
                week = (long)(week % 50.8571429 % 10);
                weekDone = true;
            }
            if (useYear) {
                year %= 10;
            }
        }

        String separator = plugin.getConfig().getString("Separator");
        String decadeString      = getSingularOrPlural(useDecade     , decade     , separator, plugin, "DecadeSuffix"     );
        String yearString        = getSingularOrPlural(useYear       , year       , separator, plugin, "YearSuffix"       );
        String weekString        = getSingularOrPlural(useWeek       , week       , separator, plugin, "WeekSuffix"       );
        String dayString         = getSingularOrPlural(useDay        , day        , separator, plugin, "DaySuffix"        );
        String hourString        = getSingularOrPlural(useHour       , hour       , separator, plugin, "HourSuffix"       );
        String minuteString      = getSingularOrPlural(useMinute     , minute     , separator, plugin, "MinuteSuffix"     );
        String secondString      = getSingularOrPlural(useSecond     , second     , separator, plugin, "SecondSuffix"     );
        String millisecondString = getSingularOrPlural(useMillisecond, millisecond, separator, plugin, "MillisecondSuffix");

        List<String> decadeLeft = plugin.getConfig().getStringList("DecadeLeft");
        List<String> yearLeft = plugin.getConfig().getStringList("YearLeft");
        List<String> weekLeft = plugin.getConfig().getStringList("WeekLeft");
        List<String> dayLeft = plugin.getConfig().getStringList("DayLeft");
        List<String> hourLeft = plugin.getConfig().getStringList("HourLeft");
        List<String> minuteLeft = plugin.getConfig().getStringList("MinuteLeft");
        List<String> secondLeft = plugin.getConfig().getStringList("SecondLeft");
        List<String> millisecondLeft = plugin.getConfig().getStringList("MillisecondLeft");

        if (!decadeString.isEmpty()) {
            return build(decadeLeft     , decadeString, yearString, weekString, dayString, hourString, minuteString, secondString, millisecondString);
        } else if (!yearString.isEmpty()) {
            return build(yearLeft       , decadeString, yearString, weekString, dayString, hourString, minuteString, secondString, millisecondString);
        } else if (!weekString.isEmpty()) {
            return build(weekLeft       , decadeString, yearString, weekString, dayString, hourString, minuteString, secondString, millisecondString);
        } else if (!dayString.isEmpty()) {
            return build(dayLeft        , decadeString, yearString, weekString, dayString, hourString, minuteString, secondString, millisecondString);
        } else if (!hourString.isEmpty()) {
            return build(hourLeft       , decadeString, yearString, weekString, dayString, hourString, minuteString, secondString, millisecondString);
        } else if (!minuteString.isEmpty()) {
            return build(minuteLeft     , decadeString, yearString, weekString, dayString, hourString, minuteString, secondString, millisecondString);
        } else if (!secondString.isEmpty()) {
            return build(secondLeft     , decadeString, yearString, weekString, dayString, hourString, minuteString, secondString, millisecondString);
        } else {
            return build(millisecondLeft, decadeString, yearString, weekString, dayString, hourString, minuteString, secondString, millisecondString);
        }
    }

    @NonNull
    private static String getSingularOrPlural(boolean use, long value, String separator, JavaPlugin plugin, String name) {
        if (!use || value == 0) return "";
        return value + plugin.getConfig().getStringList(name).get(value == 1? 0 : 1) + separator;
    }

    private static String build(List<String> left, String decadeString, String yearString, String weekString, String dayString, String hourString, String minuteString, String secondString, String millisecondString) {
        if (!left.contains("decade"))
            decadeString = "";
        if (!left.contains("year"))
            yearString = "";
        if (!left.contains("week"))
            weekString = "";
        if (!left.contains("day"))
            dayString = "";
        if (!left.contains("hour"))
            hourString = "";
        if (!left.contains("minute"))
            minuteString = "";
        if (!left.contains("second"))
            secondString = "";
        if (!left.contains("millisecond"))
            millisecondString = "";
        return (decadeString + yearString + weekString + dayString + hourString + minuteString + secondString + millisecondString).trim();
    }
}
