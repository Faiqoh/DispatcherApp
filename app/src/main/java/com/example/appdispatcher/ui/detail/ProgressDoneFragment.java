package com.example.appdispatcher.ui.detail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appdispatcher.Adapter.ProgressTaskAdapter;
import com.example.appdispatcher.FabActivity;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.fab.RequestFabFragment;
import com.example.appdispatcher.util.server;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.appbar.AppBarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 */

public class ProgressDoneFragment extends Fragment implements ProgressTaskAdapter.PListAdapter, AdapterView.OnItemSelectedListener {

    private static ProgressDoneFragment instance = null;

    public List<ProgressDoneViewModel> pList = new ArrayList<>();
    public static final String DATE_FORMAT_5 = "dd MMMM yyyy";
    public static final String ID_JOB = "id_job";
    public static final String GET_ID_JOB = "get_id_job";

    ImageView cat_backend;
    TextView textViewjob, textJobdesc, textRequirement, textBuilding, textloc, textLevel, textDate, textPIc, tvidUser, tvidJob, textview_mail, textview_share, tv_status_request, tv_price, tv_url;
    EditText etTask;
    Button btn_submit, btn_download;
    ProgressTaskAdapter pAdapter;
    String id_user, id_jobb, detail_activity;
    ProgressBar progressBar, progressBarSubmit;
    CardView cvdesc, cvspec, cardView1;
    RelativeLayout relativelayoutprogress;
    ShimmerFrameLayout shimmerFrameLayout;
    Boolean isOpen = false;
    FloatingActionsMenu floatingActionsMenu;
    NestedScrollView NesteddetailTask;
    AppBarLayout appBarLayout;
    Spinner spinner;

    FloatingActionButton request, approval, done, job_progress, support, show;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_progress_done, container, false);

        Bundle extras = getActivity().getIntent().getExtras();
        String getJob = extras.getString("get_id_job");

        cat_backend = root.findViewById(R.id.cat_backend);
        textJobdesc = root.findViewById(R.id.job_desc_detail);
        textViewjob = root.findViewById(R.id.text_view_job);
        textRequirement = root.findViewById(R.id.requirement_detail);
        tvidJob = root.findViewById(R.id.tv_idjob);
        tvidUser = root.findViewById(R.id.tv_id_user);
        progressBar = root.findViewById(R.id.progressBarDone);
        progressBarSubmit = root.findViewById(R.id.progressBarSubmit);
        shimmerFrameLayout = root.findViewById(R.id.shimmer_view_container);
        cvdesc = root.findViewById(R.id.cvdesc);
        cvspec = root.findViewById(R.id.spec);
        relativelayoutprogress = root.findViewById(R.id.relativelayoutprogress);
        cardView1 = root.findViewById(R.id.cardview1);
        floatingActionsMenu = getActivity().findViewById(R.id.fab_menu);
        NesteddetailTask = getActivity().findViewById(R.id.Nested_detail_task);
        appBarLayout = getActivity().findViewById(R.id.app_bar);
        tv_status_request = root.findViewById(R.id.tv_status_request);
        textBuilding = root.findViewById(R.id.building);
        textloc = root.findViewById(R.id.location);
        textLevel = root.findViewById(R.id.level);
        textDate = root.findViewById(R.id.date_job);
        textPIc = root.findViewById(R.id.pic_job);
        tv_price = root.findViewById(R.id.tv_price);
        tv_url = root.findViewById(R.id.tv_url);

        final RecyclerView recyclerView = root.findViewById(R.id.recyclerViewprogresstask);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        pAdapter = new ProgressTaskAdapter(pList);
        recyclerView.setAdapter(pAdapter);

        request = getActivity().findViewById(R.id.request);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FabActivity.class);
                intent.putExtra(ID_JOB, tvidJob.getText().toString());
                intent.putExtra(GET_ID_JOB, "id_job_request");
                getActivity().startActivityForResult(intent, 1);
                floatingActionsMenu.collapse();
            }
        });

        approval = getActivity().findViewById(R.id.approval);
        approval.setVisibility(View.GONE);
        approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FabActivity.class);
                intent.putExtra(ID_JOB, tvidJob.getText().toString());
                intent.putExtra(GET_ID_JOB, "id_job_request_done");
                getActivity().startActivityForResult(intent, 1);
                floatingActionsMenu.collapse();
            }
        });

        show = getActivity().findViewById(R.id.show);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = tv_url.getText().toString().trim();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        job_progress = getActivity().findViewById(R.id.progress);
        job_progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(getContext(), FabActivity.class);
                intent.putExtra(ID_JOB, tvidJob.getText().toString());
                intent.putExtra(GET_ID_JOB, "id_progress_job");
                getActivity().startActivityForResult(intent, 1);
                floatingActionsMenu.collapse();
//                String[] progress_job = { "Waiting", "On Progress", "Migrate", "Troubleshoot", "Monitor"};
//                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
//                View mView = getLayoutInflater().inflate(R.layout.activity_request, null);
//                mBuilder.setView(mView);
//                final AlertDialog dialog = mBuilder.create();
//                dialog.show();
//                floatingActionsMenu.collapse();
//                btn_submit = mView.findViewById(R.id.btnSubmit);
//                etTask = mView.findViewById(R.id.eTextTask);
//                spinner = mView.findViewById(R.id.spinner_progress);
//
//                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mView.getContext(), R.array.progress_job,
//                        android.R.layout.simple_spinner_item);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//                spinner.setAdapter(adapter);
//
//                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//                {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
//                    {
//                        String selected = parentView.getItemAtPosition(position).toString();
//                        Context context = parentView.getContext();
//                        CharSequence text = selected;
//                        int duration = Toast.LENGTH_SHORT;
//
//                        Toast toast = Toast.makeText(context, text, duration);
//                        toast.show();
//
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parentView) {
//                        // your code here
//                    }
//                });
//
//                btn_submit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        id_user = tvidUser.getText().toString().trim();
//                        id_jobb = tvidJob.getText().toString().trim();
//                        detail_activity = etTask.getText().toString().trim();
//                        if (etTask.getText().toString().length() == 0) {
//                            etTask.setError("Task Should not be empty!");
//                        } else {
//                            progressjob();
//                            dialog.dismiss();
//                        }
//                    }
//
//                });

            }

        });

        if (getJob.equals("id_job_progress")) {
            OnProgressViewModel detail = (OnProgressViewModel) getActivity().getIntent().getSerializableExtra(OnProgressFragment.ID_JOB);
            String id_job = detail.getId_job();
            fillDetail(id_job);
        } else {
            DoneViewModel detail = (DoneViewModel) getActivity().getIntent().getSerializableExtra(DoneFragment.ID_JOB);
            String id_job = detail.getId_job();
            fillDetail(id_job);
        }

        done = getActivity().findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent intent = new Intent(getContext(), FabActivity.class);
            intent.putExtra(ID_JOB, tvidJob.getText().toString());
            intent.putExtra(GET_ID_JOB, "id_job_done");
            getActivity().startActivityForResult(intent, 1);
            floatingActionsMenu.collapse();
            }
        });

        support = getActivity().findViewById(R.id.support);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent intent = new Intent(getContext(), FabActivity.class);
            intent.putExtra(ID_JOB, tvidJob.getText().toString());
            intent.putExtra(GET_ID_JOB, "id_job_support");
            getActivity().startActivityForResult(intent, 1);
            floatingActionsMenu.collapse();
            }
        });

        return root;
    }

    public void fillDetail(String id_job) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_5);
        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        final Locale localeID = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        progressBar.getIndeterminateDrawable().setColorFilter(getActivity().getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest StrReq = new JsonObjectRequest(Request.Method.GET, server.progreesjob_withToken + "?id_job=" + id_job, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                floatingActionsMenu.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);

                cvdesc.setVisibility(View.VISIBLE);
                cvspec.setVisibility(View.VISIBLE);
                relativelayoutprogress.setVisibility(View.VISIBLE);
                cardView1.setVisibility(View.VISIBLE);

                try {
                    JSONObject job = response.getJSONObject("job");
                    JSONObject jObj = response;

                    JSONObject category = job.getJSONObject("category");
                    JSONObject history_request = job.getJSONObject("latest_job_request");

                    Date date_start = inputFormat.parse(job.getString("date_start"));
                    Date date_end = inputFormat.parse(job.getString("date_end"));

                    textViewjob.setText(job.getString("job_name"));
                    textJobdesc.setText(job.getString("job_description"));
                    textRequirement.setText(job.getString("job_requrment"));
                    tvidJob.setText(job.getString("id"));
                    textBuilding.setText(job.getJSONObject("customer").getString("customer_name"));
                    textloc.setText(job.getJSONObject("location").getString("location_name"));
                    textLevel.setText(job.getJSONObject("level").getString("level_name"));
                    textDate.setText(dateFormat.format(date_start) + " - " + dateFormat.format(date_end));
                    textPIc.setText(job.getJSONObject("pic").getString("pic_name") + "(" + job.getJSONObject("pic").getString("pic_phone") + ")");
                    Glide.with(getActivity()).load(category.getString("category_image_url")).into(cat_backend);
                    tv_status_request.setText(history_request.getString("status_item"));
                    tv_price.setText(formatRupiah.format((Double.parseDouble(job.getString("job_price")))));
                    tv_url.setText(job.getString("letter_of_assignment"));

                    Log.d("cek status", tv_status_request.getText().toString().trim());

                    if (tv_status_request.getText().toString().trim().length() == 0){
                        request.setVisibility(View.VISIBLE);
                    } else if (tv_status_request.getText().toString().trim().equals("Done")){
                        approval.setVisibility(View.VISIBLE);
                        request.setVisibility(View.GONE);
                    } else if (tv_status_request.getText().toString().trim().equals("Requested")){
                        approval.setVisibility(View.GONE);
                        request.setVisibility(View.GONE);
                    } else if (tv_status_request.getText().toString().trim().equals("Success")){
                        approval.setVisibility(View.GONE);
                        request.setVisibility(View.VISIBLE);
                    }


                    JSONArray jray = jObj.getJSONArray("progress");
                    pList.clear();
                    if (response.length() > 0) {
                        int no = 1;
                        String tempDetail_activity = "";
                        for (int i = 0; i < jray.length(); i++) {
                            JSONObject task = jray.getJSONObject(i);
                            if (task.getInt("id_activity") == 5) {

                                ProgressDoneViewModel progress = new ProgressDoneViewModel();
                                Date date_submit = inputFormat.parse(task.getString("date_time"));
                                progress.setDate(dateFormat.format(date_submit));
                                floatingActionsMenu.collapse();

//                                Log.i("tes detail activity1", String.valueOf(task));

                                if (!pList.contains(progress)) {
                                    tempDetail_activity = task.getString("detail_activity") + "\n";
//                                    Log.i("tes detail activity1", "if");
                                    progress.setDetail_activity(tempDetail_activity);
                                    progress.setDay("Day " + no++);
                                    pList.add(progress);
                                } else {
                                        tempDetail_activity += task.getString("detail_activity") + "\n";
//                                        Log.i("tes detail activity3", "else");
                                        pList.get(pList.size() - 1).setDetail_activity(tempDetail_activity);
                                }

                            } else if (task.getInt("id_activity") == 6) {
                                ProgressDoneViewModel progress = new ProgressDoneViewModel();

                                Date date_submit = inputFormat.parse(task.getString("date_time"));
                                progress.setDetail_activity(task.getString("detail_activity"));
                                progress.setDate(dateFormat.format(date_submit));
                                floatingActionsMenu.setVisibility(View.GONE);

//                                if (!pList.contains(progress)) {
//                                    progress.setDay("Day " + no++);
//                                    pList.add(progress);
//                                }

                                if (!pList.contains(progress)) {
                                    tempDetail_activity = task.getString("detail_activity") + "\n";
//                                    Log.i("tes detail activity1", "if");
                                    progress.setDetail_activity(tempDetail_activity);
                                    progress.setDay("Day " + no++);
                                    pList.add(progress);
                                } else {
                                    tempDetail_activity += task.getString("detail_activity") + "\n";
//                                        Log.i("tes detail activity3", "else");
                                    pList.get(pList.size() - 1).setDetail_activity(tempDetail_activity);
                                }

                            }
                            pAdapter.notifyDataSetChanged();
                        }
                    }

                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Accept", "applicaion/json");
                SharedPreferences mSetting = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
                headers.put("Authorization", mSetting.getString("Token", "missing"));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(StrReq);
    }

//    private void progressjob() {
//        progressBarSubmit.getIndeterminateDrawable().setColorFilter(getActivity().getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
//        progressBarSubmit.setVisibility(View.VISIBLE);
//        floatingActionsMenu.setVisibility(View.GONE);
//        final JSONObject jobj = new JSONObject();
//        try {
//            jobj.put("id_job", id_jobb);
//            jobj.put("detail_activity", detail_activity);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, server.postJobUpdate_withToken, jobj, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                progressBarSubmit.setVisibility(View.GONE);
//                JSONObject jObj = response;
//                Toast.makeText(getActivity(), "Successfully :)", Toast.LENGTH_LONG).show();
//                Fragment frg = null;
//                frg = getFragmentManager().findFragmentById(R.id.pending_fragment);
//                final FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.detach(frg);
//                ft.attach(frg);
//                ft.commit();
//
////                getActivity().finish();
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                        NetworkResponse response = error.networkResponse;
//                        String errorMsg = "";
//                        if (response != null && response.data != null) {
//                            String errorString = new String(response.data);
//                            Log.i("log error", errorString);
//                        }
//                        Toast.makeText(getActivity(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//
//            /**
//             * Passing some request headers
//             */
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                //headers.put("Content-Type", "application/json");
//                headers.put("Accept", "applicaion/json");
//                SharedPreferences mSetting = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
//                headers.put("Authorization", mSetting.getString("Token", "missing"));
//                return headers;
//            }
//        };
//
//        strReq.setRetryPolicy(new DefaultRetryPolicy(
//                0,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(strReq);
//    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void doClick(int pos) {
        Intent intent = new Intent(getContext(), FabActivity.class);
        intent.putExtra(ID_JOB, pAdapter.getItem(pos));
        intent.putExtra(GET_ID_JOB, "date_progress");
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
