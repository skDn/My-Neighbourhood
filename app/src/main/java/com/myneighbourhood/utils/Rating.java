package com.myneighbourhood.utils;

/**
 * Created by kirchoni on 26/02/16.
 */
public class Rating {

    private User user;
    private int ratingAsRequester;
    private int ratingAsApplicant;
    private int endorsedBy;

    public Rating (User user, int ratingAsRequester, int ratingAsApplicant, int endorsedBy){
        this.user = user;
        this.ratingAsRequester = ratingAsRequester;
        this.ratingAsApplicant = ratingAsApplicant;
        this.endorsedBy = endorsedBy;
    }

    public User getUser() {
        return user;
    }

    public int getRatingAsRequester() {
        return ratingAsRequester;
    }

    public void setRatingAsRequester(int ratingAsRequester) {
        this.ratingAsRequester = ratingAsRequester;
    }

    public int getRatingAsApplicant() {
        return ratingAsApplicant;
    }

    public void setRatingAsApplicant(int ratingAsApplicant) {
        this.ratingAsApplicant = ratingAsApplicant;
    }

    public int getEndorsedBy() {
        return endorsedBy;
    }

    public void setEndorsedBy(int endorsedBy) {
        this.endorsedBy = endorsedBy;
    }
}
