package com.erkhgns.retrofitadvance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.erkhgns.retrofitadvance.Model.Comment;
import com.erkhgns.retrofitadvance.Model.Post;

import java.util.List;

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

        getSortedPost();
    }

    /**
     * Returns single object and filters dynamically
     */
    public void getSpecificPost(){

        //If retrofit doesn't find the data accdg to the parameter passes,
        // retrofit will display 'no data found'
        Call<Post> post = api.getSpecificPost(1);

        post.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
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


    public void getPostUsingQuery(){
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

    public void getSortedPost(){

        //will return a sorted data
        //sort String must be same to the actual json value
        // get the post of all user id 1 sorted by id in descending order
        Call<List<Post>> getAllPost = api.getSortedPost(1,"id","desc");


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



    }

