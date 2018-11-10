package com.bluetoothdevicemanager.presenter.base;

import android.content.Context;
import com.bluetoothdevicemanager.view.base.BaseView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<T extends BaseView> {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private T view;

    public interface View {
        Context context();
    }

    public void detachView(){this.view = null;}

    public void attachView(T view){this.view = view;}

    public T getView() {
        return view;
    }

    public void terminate() {
        dispose();
    }

    protected void addDisposableObserver(Disposable disposable) {
        this.compositeDisposable.add(disposable);
    }

    private void dispose() {
        if (!this.compositeDisposable.isDisposed()) {
            this.compositeDisposable.dispose();
        }
    }
}
