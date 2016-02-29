package babylon.sniper.com.babylon.ui.adapters;

import android.app.Application;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import babylon.sniper.com.babylon.api.object_models.Post;
import babylon.sniper.com.babylon.api.repos.DataRepository;
import babylon.sniper.com.babylon.ui.interfaces.PostAdapterListener;
import babylon.sniper.com.babylon.ui.modules.PostModuleCollection;
import babylon.sniper.com.babylon.ui.presenters.PostsScreenModulesPresenter;
import babylon.sniper.com.babylon.ui.views.PostsAdapterView;


public class PostsAdapter extends RecyclerView.Adapter implements PostsAdapterView, PostDelegate.OnPostClickListener {

    private PostModuleCollection moduleCollection;
    private PostDelegate postDelegate;
    private PostsScreenModulesPresenter presenter;
    private PostAdapterListener itemClickListener;

    public PostsAdapter(Application application, PostAdapterListener itemClickListener) {
        // Create presenter and to get moduleCollection
        presenter = new PostsScreenModulesPresenter(this, new DataRepository(application));

        // Initialize delegates for different modules
        postDelegate = new PostDelegate(this);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;//We don't have more then one view types in this adapter
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return postDelegate.onCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Post module = moduleCollection.getPostModule(position);
        postDelegate.onBindViewHolder(holder, module, position);
    }

    @Override
    public int getItemCount() {
        if (moduleCollection == null) {
            return 0;
        }
        return moduleCollection.size();
    }

    //This data will come from PostsScreenModulesPresenter
    @Override
    public void setPostsModule(List<Post> allPosts) {
        if(moduleCollection == null){
            moduleCollection = new PostModuleCollection();
        }
        moduleCollection.setPostModuleCollection(allPosts);
        itemClickListener.onDataLoad();
        notifyDataSetChanged();
    }

    @Override
    public void onErrorOccurred(@StringRes int errorMessage) {
        if(itemClickListener != null){
            itemClickListener.onDataError(errorMessage);
        }
    }

    @Override
    public void onItemClick(int position) {
        //prevent index out of bound ex
        if(position >= 0 && position < moduleCollection.size()) {
            itemClickListener.onPostSelected(moduleCollection.getPostModule(position));
        }
    }
    public void activatePostListPresenter(){
        presenter.onAdapterCreated();
    }
}
