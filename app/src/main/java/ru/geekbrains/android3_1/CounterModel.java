package ru.geekbrains.android3_1;

import java.util.ArrayList;
import java.util.List;

public class CounterModel {

    private List<Integer> counters;

    CounterModel() {
        counters = new ArrayList<>();
        counters.add(0);
        counters.add(0);
        counters.add(0);
    }

    public Integer calculate(int index) {
        counters.set(index, counters.get(index) + 1);
        return counters.get(index);
    }
}
