package ru.geekbrains.android3_1;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.android3_1.utils.ImageConverter;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private CounterModel model;
    private Scheduler observeScheduler;
    private ImageConverter imageConverter;

    private Disposable convertingDispose;

    MainPresenter(Scheduler scheduler, ImageConverter imageConverter) {
        this.model = new CounterModel();
        this.observeScheduler = scheduler;
        this.imageConverter = imageConverter;
    }

    public void counterClickOne() {
        model.calculate(0)
                .observeOn(observeScheduler)
                .subscribe(index -> getViewState().setButtonTextOne(index));
    }

    public void counterClickTwo() {
        model.calculate(0)
                .observeOn(observeScheduler)
                .subscribe(index -> getViewState().setButtonTextTwo(index));
    }

    public void counterClickThree() {
        model.calculate(0)
                .observeOn(observeScheduler)
                .subscribe(index -> getViewState().setButtonTextThree(index));
    }

    public void changedText(String s) {
        getViewState().setTextViewText(s);
    }

    public void sendConvertedDataForImage(byte[] dataArr) {
        Single.just(dataArr)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doOnSubscribe(disposable -> convertingDispose = disposable)
                .subscribe(dataArr1 -> imageConverter.createImageFromData(dataArr1, "/pngfile.png"));
    }

    public void breakConverting() {
        convertingDispose.dispose();
    }
}
