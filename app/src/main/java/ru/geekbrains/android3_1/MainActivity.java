package ru.geekbrains.android3_1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.geekbrains.android3_1.utils.ImageConverter;
import ru.geekbrains.android3_1.utils.RealPathUtils;
import rx.functions.Action1;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    private static final int REQUEST_CODE_GET_IMAGE = 1;
    private static final int PERMISSION_REQUEST_CODE = 10;

    ImageConverter converter;

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
    @BindView(R.id.btn_open_convert_img)
    Button buttonConvertImage;
    @BindView(R.id.btn_stop_convert_img)
    Button buttonStopConvertImage;

    @InjectPresenter
    MainPresenter presenter;

    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        converter = new ImageConverter(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath());
        RxTextView.textChanges(editText).subscribe((Action1<? super CharSequence>) charSequence -> presenter.changedText(charSequence.toString()));

        checkPermission();

    }

    private void checkPermission() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int readPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            int writePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (writePermission != PackageManager.PERMISSION_GRANTED ||
                    readPermission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE
                );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        buttonConvertImage.setEnabled(false);
    }

    @ProvidePresenter
    public MainPresenter provideMainPresenter() {
        presenter = new MainPresenter(AndroidSchedulers.mainThread(), converter);
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

    @OnClick(R.id.btn_open_convert_img)
    public void onClickConvertImage() {
        Intent intent = new Intent();
        intent.setType("image/jpeg");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_GET_IMAGE);
    }

    @OnClick(R.id.btn_stop_convert_img)
    public void onClickStopConvertImage() {
        presenter.breakConverting();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_GET_IMAGE) {

                String filepath = RealPathUtils.getRealPathFromURI_API19(this, data.getData());
                presenter.sendConvertedDataForImage(converter.convertImageToArray(filepath));
            }
        }
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

    @Override
    public void setTextViewText(String s) {
        textView.setText(s);
    }

}
