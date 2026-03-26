package sort_algorithms.model.sorting;

import sort_algorithms.model.sorting.impl.SortingAlgorithmVisualizer;

import java.util.ArrayList;

public class SorterHistory {

    private final String name;
    private final int[] array;
    private int currentIndex = 0;
    private final ArrayList<SortingStep> list;

    private int comparisions;
    private int assignments;

    public SorterHistory(String name, int[] array) {
        this.name = name;
        this.array = array;
        this.list = new ArrayList<>();
        list.add(new SortingStep(SortingType.FINISH, "Starte die Simulation", new SortingStepData(this.array, 0, 0, 0, 0)));
    }

    public void comparePlaces(int index1, int index2, String message){
        list.add(new SortingStep(SortingType.COMPARE, message, new SortingStepData(this.array, index1, index2, this.getComparisions(), this.getAssignments())));
    }

    public void comparePlaces(int index1, int index2){
        this.comparePlaces(index1, index2, null);
    }

    public void switchPlaces(int index1, int index2, String message){
        list.add(new SortingStep(SortingType.SWITCH, message, new SortingStepData(this.array, index1, index2, this.getComparisions(), this.getAssignments())));
    }

    public void switchPlaces(int index1, int index2){
        this.switchPlaces(index1, index2, null);
    }

    public void noSwitch(int index1, int index2, String message){
        list.add(new SortingStep(SortingType.NO_SWITCH, message, new SortingStepData(this.array, index1, index2, this.getComparisions(), this.getAssignments())));
    }

    public void finish(String message){
        list.add(new SortingStep(SortingType.FINISH, message, new SortingStepData(this.array, 0, 0, this.getComparisions(), this.getAssignments())));
    }

    public int stepForward(SortingAlgorithmVisualizer visualizer) {
        if (!visualizer.allowSkip()) return 1;
        if (currentIndex == list.size() - 1) return 2;

        list.get(currentIndex).stepForward();
        currentIndex++;
        visualizer.processStep(SortingDirection.FORWARD);
        return 0;
    }

    public void stepBack(SortingAlgorithmVisualizer visualizer) {
        if (!visualizer.allowSkip()) return;
        if (currentIndex == 0) return;

        list.get(currentIndex - 1).stepBack();
        currentIndex--;
        visualizer.processStep(SortingDirection.BACKWARD);
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

    public ArrayList<SortingStep> getList() {
        return list;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int[] getArray() {
        return array;
    }

    public void comparision(int add) {
        this.comparisions += add;
    }

    public void assignment(int add) {
        this.assignments += add;
    }

    public int getAssignments() {
        return this.assignments;
    }

    public int getComparisions() {
        return this.comparisions;
    }
}
