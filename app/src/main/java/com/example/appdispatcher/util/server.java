package com.example.appdispatcher.util;

public class server {
    //    public static final String url = "https://eod-api.sinergy.co.id";
    public static final String url = "https://development-api.sifoma.id";
    //    public static final String url = "https://eod-api.sifoma.id";
    public static final String getJobCategory = url + "/dashboard/getJobCategory";
    public static final String getJobList = "https://development.sinergy-dev.xyz:2096/dashboard/getJobList";
    public static final String getUser = "https://development.sinergy-dev.xyz:2096/dashboard/getDashboard";
    public static final String getJobListSummary = "https://development.sinergy-dev.xyz:2096/dashboard/getJobListSumary/?id_job=1";
    public static final String getJobListSumm = url + "/dashboard/getJobListAndSumary";
    public static final String getAllCategory = url + "/dashboard/getJobCategoryAll";

    //Halaman detail
    public static final String getJobOpen = url + "/job/getJobOpen";

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
    public static final String postSaveToken = url + "/api/api_token";

    public static final String getUser_withToken = url + "/api/dashboard/getDashboard";
    public static final String login_url = url + "/api/api_login";
    public static final String getJob_withToken = url + "/api/job/getJobByCategory";
    public static final String getpayment_withToken = url + "/api/payment/getJobPayment";
    public static final String JobStart_WithToken = url + "/api/job/postJobStart";
    public static final String JobApply_withToken = url + "/api/job/postJobApply";
    public static final String progreesjob_withToken = url + "/api/job/getJobProgress";
    public static final String postJobUpdate_withToken = url + "/api/job/postJobUpdate";
    public static final String request_item_withToken = url + "/api/job/postJobRequestItem";
    public static final String jobdone_withToken = url + "/api/job/postJobFinish";
    public static final String getdetailpayment_withToken = url + "/api/payment/getJobPaymentDetail";
    public static final String getuserwithToken = url + "/api/users/getProfileDetail";
    public static final String postgetsupport_withtoken = url + "/api/job/postJobRequestSupport";
    public static final String getsupport_withtoken = url + "/api/job/getJobSupport";
    public static final String getdetailsupport_withtoken = url + "/api/job/getJobSupportEach";
    public static final String postProfileUpdate = url + "/api/users/postProfileUpdate";
}
