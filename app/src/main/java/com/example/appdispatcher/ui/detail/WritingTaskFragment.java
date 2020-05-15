package com.example.appdispatcher.ui.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.Adapter.ProgressTaskAdapter;
import com.example.appdispatcher.R;
import com.example.appdispatcher.util.server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WritingTaskFragment extends Fragment {

    public List<DetailProgressViewModel> pList = new ArrayList<>();
    ImageView cat_backend;
    TextView textViewjob, textJobdesc, textRequirement;
    EditText etTask;
    Button btn_note, btn_submit;
    ProgressTaskAdapter pAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.writing_task_layout, container, false);

        OnProgressViewModel detail = (OnProgressViewModel) getActivity().getIntent().getSerializableExtra(OnProgressFragment.ID_JOB);

        cat_backend = root.findViewById(R.id.cat_backend);
        textJobdesc = root.findViewById(R.id.job_desc_detail);
        textViewjob = root.findViewById(R.id.text_view_job);
        textRequirement = root.findViewById(R.id.requirement_detail);
        etTask = root.findViewById(R.id.eTextTask);
        btn_submit = root.findViewById(R.id.btnSubmit);
        btn_note = root.findViewById(R.id.btnAddNote);

        btn_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTask.setVisibility(View.VISIBLE);
                btn_submit.setVisibility(View.VISIBLE);
            }
        });

        if (detail != null) {
            String id_job = detail.getId_job();
            fillDetail(id_job);
        }

        return root;
    }

    private void fillDetail(String id_job) {

        JsonObjectRequest StrReq = new JsonObjectRequest(Request.Method.GET, server.getJobOpen + "/?id_job=" + id_job, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject job = response.getJSONObject("job");

                    JSONObject category = job.getJSONObject("category");

                    Log.i("job", category.toString());

                    textViewjob.setText(category.getString("category_name"));
                    textJobdesc.setText(job.getString("job_description"));
                    textRequirement.setText(job.getString("job_requrment"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(StrReq);

//        JsonObjectRequest StrReq2 = new JsonObjectRequest(Request.Method.GET, server.getJobProgress + "/?id_job=" + id_job, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.i("response job list", response.toString());
//                JSONObject jObj = response;
//                try {
//                    JSONArray jray = jObj.getJSONArray("job");
//
//                    if (response.length() > 0) {
//                        Resources resources = getResources();
//
//                        for (int i = 0; i < jray.length(); i++) {
//                            JSONObject cat = jray.getJSONObject(i);
//
//                            DetailProgressViewModel itemCategory = new OnProgressViewModel();
//                            itemCategory.setJudul(cat.getJSONObject("category").getString("category_name"));
//                            itemCategory.setId_job(cat.getString("id"));
//                            itemCategory.setFoto(cat.getJSONObject("category").getString("category_image_url"));
//                            itemCategory.setCustomer(cat.getJSONObject("customer").getString("customer_name"));
//                            itemCategory.setLocation(cat.getJSONObject("location").getString("long_location"));
//
//                            pList.add(itemCategory);
//                        }
//                        pAdapter.notifyDataSetChanged();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());
//        requestQueue2.add(StrReq2);
    }
}
