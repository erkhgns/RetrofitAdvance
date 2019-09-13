package com.erkhgns.retrofitadvance;


import com.erkhgns.retrofitadvance.Model.Comment;
import com.erkhgns.retrofitadvance.Model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
}
