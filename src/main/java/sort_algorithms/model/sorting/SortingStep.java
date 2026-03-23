package sort_algorithms.model.sorting;

import sort_algorithms.graphics.array.ArrayRepresentation;

public record SortingStep(int[] array, ArrayRepresentation arrayRepresentation, SortingType type, int index1, int index2) {

    public void stepBack(){
        switch(type){
            case SortingType.SWITCH:
                switchPlaces(index2, index1);
                break;
            default:
                break;
        }
    }

    public void stepForward(){
        switch(type){
            case SortingType.SWITCH:
                switchPlaces(index1, index2);
                break;
            default:
                break;
        }
    }

    private void switchPlaces(int index1, int index2){
        int value1 = array[index1];
        int value2 = array[index2];
        array[index1] = value2;
        array[index2] = value1;
        arrayRepresentation.switchPlaces(index1, index2);
    }
}
