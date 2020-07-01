package com.example.appdispatcher.util;

public class server {
    public static final String getJobCategory = "https://development.sinergy-dev.xyz:2096/dashboard/getJobCategory";
    public static final String getJobList = "https://development.sinergy-dev.xyz:2096/dashboard/getJobList";
    public static final String getUser = "https://development.sinergy-dev.xyz:2096/dashboard/getDashboard";
    public static final String getJobListSummary = "https://development.sinergy-dev.xyz:2096/dashboard/getJobListSumary/?id_job=1";
    public static final String getJobListSumm = "https://development.sinergy-dev.xyz:2096/dashboard/getJobListAndSumary";
    public static final String getAllCategory = "https://development.sinergy-dev.xyz:2096/dashboard/getJobCategoryAll";

    //Halaman detail
    public static final String getJobOpen = "https://development.sinergy-dev.xyz:2096/job/getJobOpen";

    //Job progress
    public static final String getJobProgress = "https://development.sinergy-dev.xyz:2096/job/getJobProgress";

    //job aplly
    public static final String applyjob = "https://development.sinergy-dev.xyz:2096/job/postJobApply";
    public static final String getJobStatus = "https://development.sinergy-dev.xyz:2096/job/getJobByCategory";

    //job start
    public static final String startjob = "https://development.sinergy-dev.xyz:2096/job/postJobStart";

    //progress job
    public static final String progreesjob = "https://development.sinergy-dev.xyz:2096/job/postJobUpdate";

    //job done
    public static final String jobdone = "https://development.sinergy-dev.xyz:2096/job/postJobFinish";

    public static final String getpayment = "https://development.sinergy-dev.xyz:2096/payment/getJobPayment";

    public static final String getdetailpayment = "https://development.sinergy-dev.xyz:2096/payment/getJobPaymentDetail";

    // Link API With Token
    public static final String postSaveToken = "https://development.sinergy-dev.xyz:2096/api/api_token";

    public static final String getUser_withToken = "https://development.sinergy-dev.xyz:2096/api/dashboard/getDashboard";
    public static final String login_url = "https://development.sinergy-dev.xyz:2096/api/api_login";
    public static final String getJob_withToken = "https://sinergy-dev.xyz:2096/api/job/getJobByCategory";
    public static final String getpayment_withToken = "https://sinergy-dev.xyz:2096/api/payment/getJobPayment";
    public static final String JobStart_WithToken = "https://sinergy-dev.xyz:2096/api/job/postJobStart";
    public static final String JobApply_withToken = "https://sinergy-dev.xyz:2096/api/job/postJobApply";
    public static final String progreesjob_withToken = "https://sinergy-dev.xyz:2096/api/job/getJobProgress";
    public static final String postJobUpdate_withToken = "https://sinergy-dev.xyz:2096/api/job/postJobUpdate";
    public static final String request_item_withToken = "https://sinergy-dev.xyz:2096/api/job/postJobRequestItem";
    public static final String jobdone_withToken = "https://sinergy-dev.xyz:2096/api/job/postJobFinish";
    public static final String getdetailpayment_withToken = "https://sinergy-dev.xyz:2096/api/payment/getJobPaymentDetail";
    public static final String getuserwithToken = "https://sinergy-dev.xyz:2096/api/users/getProfileDetail";

}
