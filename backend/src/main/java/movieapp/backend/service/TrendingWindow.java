package movieapp.backend.service;

public enum TrendingWindow {

    DAY, WEEK;

    public static TrendingWindow determineParamWindow(String window) {
        if (window.equalsIgnoreCase("day")) {
            return DAY;
        } else if (window.equalsIgnoreCase("week")) {
            return WEEK;
        }
        throw new IllegalArgumentException("The provided window must either be 'day' or 'week'");
    }

    public String returnWindowType() {
        return this == DAY ? "day" : "week";
    }

}
