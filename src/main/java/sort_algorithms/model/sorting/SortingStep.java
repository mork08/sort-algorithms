package sort_algorithms.model.sorting;

public class SortingStep {
    String type;
    int index1, index2;
    int[] array;
    public SortingStep(int[] array,String type, int index1, int index2){
        this.type = type;
        this.index1 = index1;
        this.index2 = index2;
        this.array = array;
    }
    public void stepBack(){
        switch(type){
            case "switch":
                switchPlaces(index2, index1);
                break;
        }
    }
    public void stepForward(){
        switch(type){
            case "switch":
                switchPlaces(index1, index2);
                break;
        }
    }
    private void switchPlaces(int index1, int index2){
        int value1 = array[index1];
        int value2 = array[index2];
        array[index1] = value2;
        array[index2] = value1;
    }
}
