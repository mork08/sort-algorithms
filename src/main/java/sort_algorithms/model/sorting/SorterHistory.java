package sort_algorithms.model.sorting;

import sort_algorithms.model.sorting.impl.SortingAlgorithmVisualizer;

import java.util.ArrayList;

public class SorterHistory {

    private final String name;
    private final int[] array;
    private int currentIndex = 0;
    private final ArrayList<SortingStep> list;

    public SorterHistory(String name, int[] array) {
        this.name = name;
        this.array = array;
        this.list = new ArrayList<>();
    }

    public void comparePlaces(int index1, int index2, String message){
        list.add(new SortingStep(SortingType.COMPARE, message, new SortingStepData(this.array, index1, index2)));
    }

    public void comparePlaces(int index1, int index2){
        this.comparePlaces(index1, index2, null);
    }

    public void switchPlaces(int index1, int index2, String message){
        list.add(new SortingStep(SortingType.SWITCH, message, new SortingStepData(this.array, index1, index2)));
    }

    public void switchPlaces(int index1, int index2){
        this.switchPlaces(index1, index2, null);
    }

    public void noSwitch(int index1, int index2, String message){
        list.add(new SortingStep(SortingType.NO_SWITCH, message, new SortingStepData(this.array, index1, index2)));
    }

    public void finish(String message){
        list.add(new SortingStep(SortingType.FINISH, message, new SortingStepData(this.array, 0, 0)));
    }

    public void stepForward(SortingAlgorithmVisualizer visualizer) {
        if (!visualizer.allowSkip()) return;
        if (currentIndex == list.size() - 1) return;
        list.get(currentIndex).stepForward();

        currentIndex++;
        visualizer.processStep();
    }

    public void stepBack(SortingAlgorithmVisualizer visualizer) {
        if (!visualizer.allowSkip()) return;
        if (currentIndex == 0) return;

        currentIndex--;
        list.get(currentIndex).stepBack();
        visualizer.processStep();
    }

    public void printArray(){
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public String getName() {
        return this.name;
    }

    public int getSteps() {
        return list.size();
    }

    public SortingStep getStep() {
        return this.list.get(this.currentIndex);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int[] getArray() {
        return array;
    }
}
