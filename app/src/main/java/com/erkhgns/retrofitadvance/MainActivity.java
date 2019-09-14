package com.erkhgns.retrofitadvance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.erkhgns.retrofitadvance.Model.Comment;
import com.erkhgns.retrofitadvance.Model.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    Retrofit retrofit;
    IApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        // TODO: 9/12/19 Don't forget to Add Internet Permissions in Manifest.
        /**
         * Instance of retrofit.
         *
         * this is where you put the base URL of the API
         *
         * added converter factory (GSON)
         */
        retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        /**
         * Instance of API interface
         */
        api = retrofit.create(IApi.class);


        getPostUsingMap();
    }

    /**
     * Returns single object and filters dynamically
     */
    public void getSpecificPost() {

        //If retrofit doesn't find the data accdg to the parameter passes,
        // retrofit will display 'no data found'
        Call<Post> post = api.getSpecificPost(1);

        post.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textView.setText(response.message());
                    return;
                }
                Post post = response.body();
                String content = "";

                content += "User ID: " + post.getUserId() + "\n";
                content += "ID: " + post.getId() + "\n";
                content += "Title: " + post.getTitle() + "\n";
                content += "Message: " + post.getText() + "\n\n";

                textView.append(content);


            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    public void getComments() {
        Call<List<Comment>> getComments = api.getSpecificComments();


        getComments.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    textView.setText(response.message());
                    return;
                }

                List<Comment> listOfComments = response.body();
                for (Comment comment : listOfComments) {
                    String content = "";

                    content += "Post ID: " + comment.getPostId() + "\n";
                    content += "ID: " + comment.getId() + "\n";
                    content += "Name : " + comment.getName() + "\n";
                    content += "Email : " + comment.getEmail() + "\n";
                    content += "Text : " + comment.getText() + "\n\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    public void getAllPost() {
        Call<List<Post>> getAllPost = api.getAllPost();

        /**
         * Enqueue functions run the method into background
         *
         * execute functions run the method on the current thread
         *
         * It will have a callback to determine the status of request
         *
         */
        getAllPost.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textView.setText(response.message());
                }

                /**
                 * response.body returns the request (Content)
                 * on web API
                 */
                List<Post> listOfPost = response.body();

                for (Post post : listOfPost) {
                    String content = "";

                    content += "User ID: " + post.getUserId() + "\n";
                    content += "ID: " + post.getId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Message: " + post.getText() + "\n\n";

                    textView.append(content);

                }


            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }


    public void getPostUsingQuery() {
        //It will return list of post with the user id of 1
        Call<List<Post>> getAllPost = api.getPostUsingQuery(1);


        getAllPost.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textView.setText(response.message());
                }


                List<Post> listOfPost = response.body();

                for (Post post : listOfPost) {
                    String content = "";

                    content += "User ID: " + post.getUserId() + "\n";
                    content += "ID: " + post.getId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Message: " + post.getText() + "\n\n";

                    textView.append(content);

                }


            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    public void getSortedPost() {

        //will return a sorted data
        //sort String must be same to the actual json value
        // get the post of all user id 1 sorted by id in descending order
        Call<List<Post>> getAllPost = api.getSortedPost(1, "id", "desc");


        getAllPost.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textView.setText(response.message());
                }


                List<Post> listOfPost = response.body();

                for (Post post : listOfPost) {
                    String content = "";

                    content += "User ID: " + post.getUserId() + "\n";
                    content += "ID: " + post.getId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Message: " + post.getText() + "\n\n";

                    textView.append(content);

                }


            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }


    public void getSortedPostWithListParameter() {
        //You can have null values in sort and order parameter,
        //when that happens, the data will not be sorted
        Call<List<Post>> getAllPost = api.getSortedPostWithListParameter(new Integer[]{1, 2, 3}, "id", "desc");


        getAllPost.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textView.setText(response.message());
                }


                List<Post> listOfPost = response.body();

                for (Post post : listOfPost) {
                    String content = "";

                    content += "User ID: " + post.getUserId() + "\n";
                    content += "ID: " + post.getId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Message: " + post.getText() + "\n\n";

                    textView.append(content);

                }


            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }


    public void getPostUsingMap() {
         Map<String, String> map = new HashMap<>();

         /**
          *
          * Put the parameters in MAP
          * 1st String  - Parameter Name
          * 2nd String - value of the parameter
          *
          */
        map.put("userId", "1");
        map.put("_sort","id");
        map.put("_order","desc");


        Call<List<Post>> getAllPost = api.getPostUsingMap(map);


        getAllPost.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textView.setText(response.message());
                }


                List<Post> listOfPost = response.body();

                if(listOfPost!=null){
                    for (Post post : listOfPost) {
                        String content = "";

                        content += "User ID: " + post.getUserId() + "\n";
                        content += "ID: " + post.getId() + "\n";
                        content += "Title: " + post.getTitle() + "\n";
                        content += "Message: " + post.getText() + "\n\n";

                        textView.append(content);

                    }
                }



            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }


    public void getPostUsingActualUrl(){

        /**
         * It passes the actual end point of the API
         *
         * Note: You can also add the base url in the String.
         * Retrofit will automatically  substitute the base URL
         */
        //whole url passed
        //Call<List<Post>> getAllPost = api.getPostUsingUrl("http://jsonplaceholder.typicode.com/posts/1");
        Call<List<Post>> getAllPost = api.getPostUsingUrl("posts/1");


        getAllPost.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textView.setText(response.message());
                }


                List<Post> listOfPost = response.body();

                if(listOfPost!=null){
                    for (Post post : listOfPost) {
                        String content = "";

                        content += "User ID: " + post.getUserId() + "\n";
                        content += "ID: " + post.getId() + "\n";
                        content += "Title: " + post.getTitle() + "\n";
                        content += "Message: " + post.getText() + "\n\n";

                        textView.append(content);

                    }
                }



            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }


}

