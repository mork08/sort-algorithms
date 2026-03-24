package sort_algorithms.model.sorting;

public enum SortingType {
    SWITCH("%d und %d müssen getauscht werden"),
    NO_SWITCH("%d und %d brauchen keinen Tausch"),
    COMPARE("%d und %d werden verglichen"),
    FINISH("der Array ist sortiert");

    private String message;

    SortingType(String message) {
        this.message = message;
    }

    public String getMessage(int value1, int value2) {
        return String.format(this.message, value1, value2);
    }
}
