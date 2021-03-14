package com.mickleentityltdnigeria.resturantapp.data.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Date;

@IgnoreExtraProperties
public class FeedBack implements Serializable {

    public String feedBackID;
    public String feedBackType;
    public String userEmail;
    public String userFullName;
    public String msgSubject;
    public String msgBody;
    public Date msgDate;
    public boolean isRead;
    public boolean isResolved;

    public FeedBack() {
    }

    public FeedBack(String feedBackID, String feedBackType, String userEmail, String userFullName, String msgSubject, String msgBody, Date msgDate, boolean isRead, boolean isResolved) {
        this.feedBackID = feedBackID;
        this.feedBackType = feedBackType;
        this.userEmail = userEmail;
        this.userFullName = userFullName;
        this.msgSubject = msgSubject;
        this.msgBody = msgBody;
        this.msgDate = msgDate;
        this.isRead = isRead;
        this.isResolved = isResolved;
    }

    @Exclude
    public String getFeedBackID() {
        return feedBackID;
    }

    @Exclude
    public void setFeedBackID(String feedBackID) {
        this.feedBackID = feedBackID;
    }

    @Exclude
    public String getFeedBackType() {
        return feedBackType;
    }

    @Exclude
    public void setFeedBackType(String feedBackType) {
        this.feedBackType = feedBackType;
    }

    @Exclude
    public String getUserEmail() {
        return userEmail;
    }

    @Exclude
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Exclude
    public String getUserFullName() {
        return userFullName;
    }

    @Exclude
    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    @Exclude
    public String getMsgSubject() {
        return msgSubject;
    }

    @Exclude
    public void setMsgSubject(String msgSubject) {
        this.msgSubject = msgSubject;
    }

    @Exclude
    public String getMsgBody() {
        return msgBody;
    }

    @Exclude
    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    @Exclude
    public Date getMsgDate() {
        return msgDate;
    }

    @Exclude
    public void setMsgDate(Date msgDate) {
        this.msgDate = msgDate;
    }

    @Exclude
    public boolean isRead() {
        return isRead;
    }

    @Exclude
    public void setRead(boolean read) {
        isRead = read;
    }

    @Exclude
    public boolean isResolved() {
        return isResolved;
    }

    @Exclude
    public void setResolved(boolean resolved) {
        isResolved = resolved;
    }

}
