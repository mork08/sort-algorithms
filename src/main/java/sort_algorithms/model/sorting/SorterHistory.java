package sort_algorithms.model.sorting;

import sort_algorithms.graphics.array.ArrayRepresentation;

import java.util.ArrayList;

public class SorterHistory {
    int[] array;
    int currentIndex = 0;
    ArrayList<SortingStep> list = new ArrayList<>();
    ArrayRepresentation arrayRepresentation;
    public SorterHistory(int[] array, ArrayRepresentation arrayRepresentation) {
        this.array = array;
        this.arrayRepresentation = arrayRepresentation;
        //System.arraycopy(array, 0, this.array, 0, array.length);
    }
    public void switchPlaces(int index1, int index2){
        list.add(new SortingStep(array, arrayRepresentation, "switch", index1, index2));
    }
    public void stepForward(){
        if (currentIndex == list.size()) return;
        list.get(currentIndex).stepForward();

        currentIndex++;
    }
    public void stepBack(){
        if (currentIndex == 0) return;
        currentIndex--;
        list.get(currentIndex).stepBack();
    }
    public void stepTo(int index){
        while (currentIndex != index){
            if (currentIndex > index){
                stepBack();
            }else{
                stepForward();
            }
        }
    }
    public void printSorting(){
        while(currentIndex < list.size()){
            printArray();
            stepForward();
        }
    }
    public void printArray(){
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
    public int getSteps(){
        return list.size();
    }
    public int getCurrentIndex(){
        return currentIndex;
    }
    public int[] getArray(){
        return array;
    }
}
