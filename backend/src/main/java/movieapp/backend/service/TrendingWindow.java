package movieapp.backend.service;

public enum TrendingWindow {

    DAY, WEEK;

    /**
     * This method determines the actual trend window the parameter passed into the API request refers to
     * @param window is the parameter being checked as either "day" or "week"
     * @return TrendingWindow or an exception if the window was neither "day" nor "week"
     */
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
