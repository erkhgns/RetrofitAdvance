package com.erkhgns.retrofitadvance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.erkhgns.retrofitadvance.Model.Comment;
import com.erkhgns.retrofitadvance.Model.Post;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    Retrofit retrofit;
    IApi api;

    HttpLoggingInterceptor loggingInterceptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);


        loggingInterceptor = new HttpLoggingInterceptor();

        //setting up the level of the logs we are able to see.
        //body is the most common one
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        /**
         * To see the actual HTTP request we made on server, use OKHTTP-logging interceptor.
         * For Logging of HTTP request.
         *
         * the instance of this library will be added on retrofit as Client.
         *
         *
         * Headers contains meta data of each response made on HTTP request.
         *    to add headers, put Annotations '@Headers' on API interface of retrofit.
         *    Or it can also pass it on the parameter using  Header annotation
         *
         * To add default header in each retrofit instance, add interceptor in OKHTTPClient. (First Interceptor)
         */
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                        //In this method, we can get our current HTTP request from Chain variable.

                        Request originalRequest = chain.request();

                        //We can't make changes on originalRequest so we copy it and create a new one


                        //adding default header on each retrofit client
                        //No spaces are allowed in header
                        Request newRequest = originalRequest.newBuilder()
                                .addHeader("CurrentApi","JSONPlace")
                                .build();



                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();


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
                .client(okHttpClient)
                .build();

        /**
         * Instance of API interface
         */
        api = retrofit.create(IApi.class);


       deletePost();
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
        map.put("_sort", "id");
        map.put("_order", "desc");


        Call<List<Post>> getAllPost = api.getPostUsingMap(map);


        getAllPost.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textView.setText(response.message());
                }


                List<Post> listOfPost = response.body();

                if (listOfPost != null) {
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


    public void getPostUsingActualUrl() {

        /**
         * It passes the actual end point of the API
         *
         * Note: You can also add the base url in the String.
         * Retrofit will automatically  substitute the base URL
         *
         * Retrofit will ignore the values if fields are null
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

                if (listOfPost != null) {
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


    public void createPost() {
        Post post = new Post(1, "Erick", "Sample message");

        /**
         * Sending data to API
         */
        Call<Post> createPost = api.createPost(post);

        createPost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textView.setText(response.message());
                    return;
                }

                Post post = response.body();
                String content = "";

                /**
                 * response.code returns the
                 * http code status of the request
                 */
                content += "Code :" + response.code() + "\n";
                content += "userId :" + post.getUserId() + "\n";
                content += "Id :" + post.getId() + "\n";
                content += "Title :" + post.getTitle() + "\n";
                content += "Message :" + post.getText() + "\n\n";

                textView.setText(content);


            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });


    }


    public void createPostUsingFormUrlEncoded() {

        /**
         * Inserting data to web service using Form URL encoded
         */
        Call<Post> createPost = api.createPostUsingFormUrl(1, "Tittleeeee", "Messageeee");

        createPost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textView.setText(response.message());
                    return;
                }

                Post post = response.body();
                String content = "";

                /**
                 * response.code returns the
                 * http code status of the request
                 */
                content += "Code :" + response.code() + "\n";
                content += "userId :" + post.getUserId() + "\n";
                content += "Id :" + post.getId() + "\n";
                content += "Title :" + post.getTitle() + "\n";
                content += "Message :" + post.getText() + "\n\n";

                textView.setText(content);


            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }


    public void createPostUsingFormMap() {
        Map<String, String> map = new HashMap<>();

        /**
         * Notice if other fields in json dont send in API, it will return a null value
         */
        map.put("userId", "99");
        map.put("title", "heyy");
        Call<Post> createPost = api.createPostUsingFormUrlMap(map);

        createPost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textView.setText(response.message());
                    return;
                }

                Post post = response.body();
                String content = "";

                /**
                 * response.code returns the
                 * http code status of the request
                 */
                content += "Code :" + response.code() + "\n";
                content += "userId :" + post.getUserId() + "\n";
                content += "Id :" + post.getId() + "\n";
                content += "Title :" + post.getTitle() + "\n";
                content += "Message :" + post.getText() + "\n\n";

                textView.setText(content);


            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void updatePostUsingPut() {

        /**
         * Add Dynamic headers using MAP
         */
        Map<String, String> headers = new HashMap<>();
        headers.put("header1","1");
        headers.put("header2","2");
        /**
         * If we send null value to the API using @PUT annotation
         * It will update the API as null because it replaces one whole object (row)
         */
        Post post = new Post(12, null, "Sample text");

        Call<Post> updatePost = api.putPost(headers, 5, post);


        updatePost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textView.setText(response.message());
                    return;
                }

                Post post = response.body();
                String content = "";

                /**
                 * response.code returns the
                 * http code status of the request
                 */
                content += "Code :" + response.code() + "\n";
                content += "userId :" + post.getUserId() + "\n";
                content += "Id :" + post.getId() + "\n";
                content += "Title :" + post.getTitle() + "\n";
                content += "Message :" + post.getText() + "\n\n";

                textView.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());

            }
        });
    }


    private void updatePostUsingPatch() {


        /**
         * If we send null value to the API using @PATCH annotation
         * It will ignore null value and will
         * only update data we send that is not null
         */
        /**
         * If we want to force update fields as null using @Patch annotation,
         * we have to create the instance of retrofit like this
         *
         *
         * Create instance of Gson Class that serializes nulls
         * and pass it on the creation of instance of retrofit
         *
         * Gson gson = new GsonBuilder().serializeNulls().create();
         *
         * retrofit = new Retrofit.Builder()
         *                 .baseUrl("http://jsonplaceholder.typicode.com/")
         *                 .addConverterFactory(GsonConverterFactory.create(gson))
         *                 .build();
         */
        Post post = new Post(12, null, "Sample text");

        Call<Post> updatePost = api.patchPost("samplee",5, post);


        updatePost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textView.setText(response.message());
                    return;
                }

                Post post = response.body();
                String content = "";

                /**
                 * response.code returns the
                 * http code status of the request
                 */
                content += "Code :" + response.code() + "\n";
                content += "userId :" + post.getUserId() + "\n";
                content += "Id :" + post.getId() + "\n";
                content += "Title :" + post.getTitle() + "\n";
                content += "Message :" + post.getText() + "\n\n";

                textView.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());

            }
        });
    }

    /**
     * Request to delete data in API
     */
    private void deletePost() {

        Call<Void> deletePost = api.deletePost(5);

        deletePost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textView.setText("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textView.setText(t.getMessage());

            }
        });
    }

}

