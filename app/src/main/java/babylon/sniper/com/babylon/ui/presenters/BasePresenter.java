package babylon.sniper.com.babylon.ui.presenters;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

public class BasePresenter<V> {
    private WeakReference<V> view;

    public void bindView(@NonNull V view) {
        this.view = new WeakReference<>(view);
    }

    public void unbindView() {
        this.view = null;
    }
}