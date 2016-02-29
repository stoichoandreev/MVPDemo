package babylon.sniper.com.babylon.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * @param <T> the type of adapters data source
 */
public interface AdapterDelegate<T> {

    /**
     * Creates the  {@link RecyclerView.ViewHolder}
     *
     * @param parent The ViewGroup parent of the given data source
     * @return The new instantiated {@link RecyclerView.ViewHolder}
     */
    RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent);

    /**
     * Called to bind the {@link RecyclerView.ViewHolder} to the given module data item
     *
     * @param holder The {@link RecyclerView.ViewHolder} to bind
     * @param module The module object that contains the data to be bound
     */
    void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @NonNull T module, int position);
}