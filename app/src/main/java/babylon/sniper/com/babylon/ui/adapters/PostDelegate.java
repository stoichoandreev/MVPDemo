package babylon.sniper.com.babylon.ui.adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import babylon.sniper.com.babylon.R;
import babylon.sniper.com.babylon.api.object_models.Post;
import butterknife.Bind;
import butterknife.ButterKnife;


@UiThread
public class PostDelegate implements AdapterDelegate<Post>, View.OnClickListener {

    private OnPostClickListener postClickListener;
    public PostDelegate(OnPostClickListener itemClickListener){
        this.postClickListener = itemClickListener;
    }
    interface OnPostClickListener{
        void onItemClick(int position);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new PostViewHolder(inflater.inflate(R.layout.post_module_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @NonNull Post module , int position) {
        PostViewHolder postViewHolder = (PostViewHolder) holder;
        Uri uri = Uri.parse(module.userAvatar.get());
        postViewHolder.userAvatar.setImageURI(uri);
        postViewHolder.postTitle.setText(module.title);

        //We can do this with much better way using for ex. Guava
        postViewHolder.clickView.setTag(position);
        postViewHolder.clickView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int position;
        try {
            position = (Integer)v.getTag();
            if(postClickListener != null) {
                postClickListener.onItemClick(position);
            }
        }catch (ClassCastException ex){
            throw new RuntimeException("Clicked View doesn't have Tag");
        }
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.post_user_avatar) SimpleDraweeView userAvatar;
        @Bind(R.id.post_title) TextView postTitle;
        @Bind(R.id.click_view) View clickView;


        public PostViewHolder(@NonNull View postView) {
            super(postView);
            ButterKnife.bind(this,postView);
        }
    }
}
