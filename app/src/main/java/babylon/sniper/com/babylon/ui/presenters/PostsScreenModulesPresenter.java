package babylon.sniper.com.babylon.ui.presenters;

import java.util.List;

import babylon.sniper.com.babylon.api.interfaces.IDataRepository;
import babylon.sniper.com.babylon.api.object_models.Comment;
import babylon.sniper.com.babylon.api.object_models.Post;
import babylon.sniper.com.babylon.api.object_models.User;
import babylon.sniper.com.babylon.api.repos.DataRepository;
import babylon.sniper.com.babylon.ui.views.PostsAdapterView;
import babylon.sniper.com.babylon.utils.ErrorUtil;

public class PostsScreenModulesPresenter {

    private PostsAdapterView view;
    private DataRepository repository;

    public PostsScreenModulesPresenter(PostsAdapterView view, DataRepository dataRepository) {
        this.view = view;
        this.repository = dataRepository;
    }

    public void onAdapterCreated() {
        repository.getPostScreenModules(new IDataRepository.Callback<Post>(){

            @Override
            public void onDataUpdated(Post data) {
            }
            @Override
            public void onListDataUpdated(List<Post> data) {
                view.setPostsModule(data);
            }
            @Override
            public void onError(Throwable throwable) {
                view.onErrorOccurred(ErrorUtil.getErrorCode(throwable));
            }
        });
        repository.getUsers(new IDataRepository.Callback<User>(){

            @Override
            public void onDataUpdated(User data) {
            }
            @Override
            public void onListDataUpdated(List<User> data) {

            }
            @Override
            public void onError(Throwable throwable) {
                view.onErrorOccurred(ErrorUtil.getErrorCode(throwable));
            }
        });
        repository.getComments(new IDataRepository.Callback<Comment>(){

            @Override
            public void onDataUpdated(Comment data) {
            }
            @Override
            public void onListDataUpdated(List<Comment> data) {

            }
            @Override
            public void onError(Throwable throwable) {
                view.onErrorOccurred(ErrorUtil.getErrorCode(throwable));
            }
        });
    }
}
