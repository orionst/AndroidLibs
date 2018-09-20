package ru.geekbrains.android3_1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends MvpAppCompatActivity implements MainView {
    @BindView(R.id.btn_one)
    Button buttonOne;
    @BindView(R.id.btn_two)
    Button buttonTwo;
    @BindView(R.id.btn_three)
    Button buttonThree;
    @BindView(R.id.edit_text_view)
    EditText editText;
    @BindView(R.id.resulted_text_view)
    TextView textView;

    @InjectPresenter
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @ProvidePresenter
    public MainPresenter provideMainPresenter() {
        presenter = new MainPresenter(AndroidSchedulers.mainThread());
        //TO SOMETHING WITH PRESENTER
        return presenter;
    }

    @OnClick(R.id.btn_one)
    public void onClickOne() {
        presenter.counterClickOne();
    }

    @OnClick(R.id.btn_two)
    public void onClickTwo() {
        presenter.counterClickTwo();
    }

    @OnClick(R.id.btn_three)
    public void onClickThree() {
        presenter.counterClickThree();
    }

    @OnTextChanged(R.id.edit_text_view)
    public void onTextChanged() {
        RxTextView.textChanges(editText)
                .map(charSequence -> new StringBuilder(charSequence).toString())
                .subscribe(s -> textView.setText(s));
    }

    @Override
    public void setButtonTextOne(Integer val) {
        buttonOne.setText(String.format(Locale.getDefault(), "%d", val));
    }

    @Override
    public void setButtonTextTwo(Integer val) {
        buttonTwo.setText(String.format(Locale.getDefault(), "%d", val));
    }

    @Override
    public void setButtonTextThree(Integer val) {
        buttonThree.setText(String.format(Locale.getDefault(), "%d", val));
    }

}
