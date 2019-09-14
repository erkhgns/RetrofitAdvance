package com.erkhgns.retrofitadvance;


import com.erkhgns.retrofitadvance.Model.Comment;
import com.erkhgns.retrofitadvance.Model.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface IApi {


    /**
     * @Get annotation request
     * data in web service (API)
     *
     * String value (eg. post) is
     * the endpoint of API
     *
     * Call is the method uses to make an HTTP request.
     * It can be synchronous and asynchronous
     * @return
     */
    @GET("posts")
    Call<List<Post>> getAllPost();


    @Deprecated
    /**
     * request in API that filters data.
     *
     * @return data with the id of 2
     *
     * NOTE: Bad design because parameter was hard coded
     */
    @GET("posts/2/comments")
    Call<List<Comment>> getSpecificComments();


    /**
     * Uses @Path annotations
     *value inside the curly bracket will be substituted accdg to
     * the value we pass in the parameter.
     *
     * URL actual value post/1
     * Note: Good design because the parameter is not hardcoded
     * @param id
     * @return
     */
    @GET("posts/{id}")
    Call<Post> getSpecificPost(@Path("id") int id);


    /**
     * Its almost the same in @Path Annotations.
     * Retrofit added '?' every time a @Query is called.
     *
     * Note: String inside Query annotations should exactly in JSON Data.
     * Actual url posts/?id = 1 (1 is the value we passed)
     */
    @GET("posts")
    Call<List<Post>> getPostUsingQuery(@Query("userId") int userId);


    /**Sorts the query
     *
     * @param userId - data to be displayed
     * @param sort - sorting will base on this
     * @param order - either desc or asc
     * @return
     *
     * Note: I think not all API are applicable for this.
     * In this case, it is applicable in the API wch is TypiCode
     *
     * Actual URL /post?userId=1&_sort=id&_order=desc
     */
    @GET("posts")
    Call<List<Post>> getSortedPost(
            @Query("userId") int userId,
            @Query("_sort") String sort,
            @Query("_order") String order);


    /**
     * Almost the same as the function above.
     * The only difference is it passes 2 user id
     * so will return 2 user ID
     */
    @GET("posts")
    Call<List<Post>> getSortedPostWith2Id(
            @Query("userId") int userId,
            @Query("_sort") String sort,
            @Query("_order") String order);


    /**
     * Almost the same as the function above.
     * The only difference is it has a parameter of
     * list
     * @param userId
     * @param sort
     * @param order
     * @return
     */
    @GET("posts")
    Call<List<Post>> getSortedPostWithListParameter(
            @Query("userId") Integer [] userId,
            @Query("_sort") String sort,
            @Query("_order") String order);


    /**
     * Query map is the most flexible annotations for me
     * It has parameters of map of String
     * The first String is the name of the parameter
     * The second String is the value of the parameter
     *
     * @param parameters
     * @return
     */
    @GET("posts")
    Call<List<Post>> getPostUsingMap(@QueryMap Map<String, String> parameters);


    /**
     * @URL annotation does
     * it requires the actual url of the API
     *
     * This is advisable when the query or path is very complicated
     * @param url
     * @return
     */
    @GET
    Call<List<Post>> getPostUsingUrl(@Url String url);


    /**
     * @Post annotation - sends data to web
     *
     * @Body annotation -The object will be serialized using
     * the Retrofit instance Converter
     * and the result will be set directly as the request body (Object - JSON)
     * @param post
     * @return
     */
    @POST("posts")
    Call<Post> createPost(@Body Post post);
}
