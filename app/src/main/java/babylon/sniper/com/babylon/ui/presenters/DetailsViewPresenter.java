package babylon.sniper.com.babylon.ui.presenters;

import java.util.List;

import babylon.sniper.com.babylon.api.interfaces.IDetailsRepository;
import babylon.sniper.com.babylon.api.object_models.Comment;
import babylon.sniper.com.babylon.api.object_models.User;
import babylon.sniper.com.babylon.api.repos.DetailsRepository;
import babylon.sniper.com.babylon.ui.views.DetailsView;

public class DetailsViewPresenter extends BasePresenter<DetailsView> {


    private final DetailsView view;
    private DetailsRepository repository;

    public DetailsViewPresenter(DetailsView view, DetailsRepository repository) {
        this.view = view;
        this.repository = repository;
    }
    public void onDetailViewCreated() {
        repository.getUserDetails(new IDetailsRepository.Callback<User>(){

            @Override
            public void onDataUpdated(User user) {
                view.setUser(user);
            }
            @Override
            public void onError(Throwable throwable) {
            }
        });
        repository.getPostComments(new IDetailsRepository.Callback<List<Comment>>(){

            @Override
            public void onDataUpdated(List<Comment> allPostComments) {
                view.setPostCommentNumber(allPostComments);
            }
            @Override
            public void onError(Throwable throwable) {
            }
        });
    }

}
