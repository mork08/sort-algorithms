package sort_algorithms.graphics.array;

import sort_algorithms.animation.Easings;
import sort_algorithms.animation.tween.Tween;

import java.util.ArrayList;

public class ArrayRepresentation {
    double width;
    double height;
    double individualWidth;
    DataRepresentation[] dataRepresentation;

    public ArrayRepresentation(int[] array, double width, double height) {
        this.width = width;
        this.height = height;
        individualWidth = width/array.length;
        dataRepresentation = new DataRepresentation[array.length];
        int largest = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > largest) {
                largest = array[i];
            }
        }
        for (int i = 0; i < array.length; i++) {
            dataRepresentation[i] = new DataRepresentation(array[i], largest, individualWidth, height);
            dataRepresentation[i].setX(i * individualWidth);
        }
    }

    /** Startes the animation to swap the places between 2 DataRepresentations
     *
     */
    public void switchPlaces(int fromIndex, int toIndex) {
        dataRepresentation[fromIndex].moveToX(toIndex * individualWidth);
        dataRepresentation[toIndex].moveToX(fromIndex * individualWidth);
        DataRepresentation temp = dataRepresentation[fromIndex];
        dataRepresentation[fromIndex] = dataRepresentation[toIndex];
        dataRepresentation[toIndex] = temp;
    }
}
