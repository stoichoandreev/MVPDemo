package babylon.sniper.com.babylon.ui.modules;

import java.util.ArrayList;
import java.util.List;

import babylon.sniper.com.babylon.api.object_models.Post;

public class PostModuleCollection {

    public int size() {
        return postModuleCollection.size();
    }

    //We can use Module Type if we have more then one View types in our Adapter
    public enum ModuleType {
        POST;
        public static ModuleType get(int ordinal) {
            return values()[ordinal];
        }
    }

    private final List<Post> postModuleCollection;


    public PostModuleCollection() {
        postModuleCollection = new ArrayList<>();
    }

    public void addPostModule(Post postModule) {
        this.postModuleCollection.add(postModule);
    }

    public Post getPostModule(int position) {
        return postModuleCollection.get(position);
    }
    public void setPostModuleCollection(List<Post> allPosts){
        postModuleCollection.clear();
        postModuleCollection.addAll(allPosts);
    }

}
