package ru.geekbrains.android3_1;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class CounterModel {

    private List<Integer> counters;

    CounterModel() {
        counters = new ArrayList<>();
        counters.add(0);
        counters.add(0);
        counters.add(0);
    }

    public Observable<Integer> calculate(int index) {
        return Observable.just(index)
                .observeOn(Schedulers.computation())
                .map(integer -> {
                    counters.set(index, counters.get(index) + 1);
                    return counters.get(index);
                });
    }
}
