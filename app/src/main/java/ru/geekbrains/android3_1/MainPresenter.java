package ru.geekbrains.android3_1;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private CounterModel model;
    private Scheduler observeScheduler;

    MainPresenter(Scheduler scheduler) {
        this.model = new CounterModel();
        this.observeScheduler = scheduler;
    }

    public void counterClickOne() {
        Observable.just(0)
                .observeOn(Schedulers.computation())
                .map(index -> model.calculate(index))
                .observeOn(observeScheduler)
                .subscribe(index -> getViewState().setButtonTextOne(index));
    }

    public void counterClickTwo() {
        Observable.just(1)
                .observeOn(Schedulers.computation())
                .map(index -> model.calculate(index))
                .observeOn(observeScheduler)
                .subscribe(index -> getViewState().setButtonTextTwo(index));
    }

    public void counterClickThree() {
        Observable.just(2)
                .observeOn(Schedulers.computation())
                .map(index -> model.calculate(index))
                .observeOn(observeScheduler)
                .subscribe(index -> getViewState().setButtonTextThree(index));
    }
}
