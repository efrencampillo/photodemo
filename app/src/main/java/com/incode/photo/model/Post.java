package com.incode.photo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Post
 * Base model of information , it supports not having info or
 * having extra params when parse from service
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("title")
    public String title;
    @JsonProperty("publishedAt")
    public String publishedAt;
    @JsonProperty("photo")
    public String photo;
    @JsonProperty("comment")
    public String comment;
}
