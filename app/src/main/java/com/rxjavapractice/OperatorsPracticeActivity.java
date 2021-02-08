package com.rxjavapractice;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OperatorsPracticeActivity extends AppCompatActivity {

    private String Greetings = "Hello From RXJAVA";

    private String[] HelloArray = {"Hello A","Hello B","Hello C"};
    private Observable<String> observable;
    private TextView TextData;
    private String TAG = "MainActivity";

    //before transaction if he destroys the activity it prevents crashing of app and disposes the subscription

    private DisposableObserver<String> stringObserver;

    //Use Composite Observable for multiple observers and can dispose all in loop
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operators);

        TextData = (TextView)findViewById(R.id.TextData);

        //from array loops through array and emmits single item at a time
        observable = Observable.fromArray(HelloArray);



        compositeDisposable.add(
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(getObserver())
        );


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }


    private DisposableObserver getObserver(){

        stringObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String s) {

                Log.i(TAG,"onNext Invoked "+s);
                Toast.makeText(OperatorsPracticeActivity.this,""+s,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        return stringObserver;

    }
}
