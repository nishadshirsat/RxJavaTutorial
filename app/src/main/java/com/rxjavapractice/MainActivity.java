package com.rxjavapractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private String Greetings = "Hello From RXJAVA";
    private Observable<String> observable;
//    private Observer<String> stringObserver;
    private TextView TextData;
    private String TAG = "MainActivity";

    //before transaction if he destroys the activity it prevents crashing of app and disposes the subscription
//    private Disposable disposable;

    private DisposableObserver<String> stringObserver;
    private DisposableObserver<String> stringObserver2;

    //Use Composite Observable for multiple observers and can dispose all in loop
    private CompositeDisposable compositeDisposable = new  CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextData = (TextView)findViewById(R.id.TextData);
        observable = Observable.just(Greetings);

        //Schedulers
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());


        compositeDisposable.add(
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(stringObserver)
        );

//        stringObserver = new Observer<String>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//                Log.i(TAG,"onSubscribe Invoked");
//                disposable = d;
//            }
//
//            @Override
//            public void onNext(@NonNull String s) {
//                Log.i(TAG,"onNext Invoked");
//                TextData.setText(s);
//
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                Log.i(TAG,"onError Invoked");
//
//            }
//
//            @Override
//            public void onComplete() {
//                Log.i(TAG,"onComplete Invoked");
//
//
//            }
//        };

        stringObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String s) {


                Log.i(TAG,"onNext Invoked");
                Toast.makeText(MainActivity.this,"First Observer",Toast.LENGTH_SHORT);


            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };



        compositeDisposable.add(
                observable.subscribeWith(stringObserver2)
        );

        stringObserver2 = new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String s) {

                TextData.setText(s);

                Toast.makeText(MainActivity.this,"Second Observer",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
//        observable.subscribe(stringObserver);
//        observable.subscribe(stringObserver2);

        //add in composite disposable
//        compositeDisposable.add(stringObserver);
//        compositeDisposable.add(stringObserver2);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        disposable.dispose();

//        stringObserver.dispose();
//        stringObserver2.dispose();

        compositeDisposable.clear();
    }
}
