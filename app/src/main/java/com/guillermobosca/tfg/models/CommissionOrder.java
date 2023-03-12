package com.guillermobosca.tfg.models;

public class CommissionOrder {

    private String id;
    private String artist_id;
    private String artist_name;
    private String client_id;
    private String client_name;
    private String commission_type_id;
    private String[] images;
    private String[] tags;
    private String description;
    private String price;
    private String date_expected_end;
    private String date_start;
    private String date_finish;
    private boolean isFinished;
    private boolean isPaid;
    private boolean isAccepted;
    private boolean isRejected;
    private boolean isCancelled;
    private boolean isRefunded;
    private boolean isDisputed;
    private boolean isReviewed;
    private int revisions_total;
    private int revisions_made;

}
