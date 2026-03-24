package sort_algorithms.model.sorting;


public record SortingStep(SortingType type, String message, SortingStepData data) {
    public void stepBack() {
        switch (type){
            case SortingType.SWITCH:
                this.switchPlaces(this.data.index2(), this.data.index1());
                break;

            default:
                break;
        }
    }

    public void stepForward() {
        switch (type) {
            case SortingType.SWITCH:
                this.switchPlaces(this.data.index1(), this.data.index2());
                break;

            default:
                break;
        }
    }

    private void switchPlaces(int index1, int index2){
        int value1 = this.data.array()[index1];
        int value2 = this.data.array()[index2];
        this.data.array()[index1] = value2;
        this.data.array()[index2] = value1;
    }

    public String message(int value1, int value2) {
        return this.message == null ? this.type.getMessage(value1, value2) : this.message.contains("%d") ? String.format(this.message, value1, value2) : this.message;
    }
}
