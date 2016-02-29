package babylon.sniper.com.babylon.ui.views;


import java.util.List;

import babylon.sniper.com.babylon.api.object_models.Comment;
import babylon.sniper.com.babylon.api.object_models.User;

public interface DetailsView {

    void setPostCommentNumber(List<Comment> postComments);
    void setUser(User user);

}
