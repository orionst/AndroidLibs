package ru.geekbrains.android3_1;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView
{
    void setButtonTextOne(Integer calculate);
    void setButtonTextTwo(Integer calculate);
    void setButtonTextThree(Integer calculate);

    void setTextViewText(String s);

}
