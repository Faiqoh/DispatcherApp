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
}
