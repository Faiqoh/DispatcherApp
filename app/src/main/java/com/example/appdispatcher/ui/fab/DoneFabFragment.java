package com.example.appdispatcher.ui.fab;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.R;
import com.example.appdispatcher.VolleyMultipartRequest;
import com.example.appdispatcher.util.server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A fragment representing a list of Items.
 */
public class DoneFabFragment extends Fragment {

    public static final String ROOT_URL = "http://seoforworld.com/api/v1/file-upload.php";
    public static final int PICKFILE_RESULT_CODE = 1;
    public static final int REQUEST_PERMISSIONS = 100;
    EditText etsum, etroot, etcounter;
    TextView tvIdJob, textViewSelected;
    Button btn_upload;
    String summary, root, counter, id_job;
    ImageView imgIdProf;
    private Bitmap bitmap;
    private String filePath, filaName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_done_fab, container, false);

        Bundle extras = getActivity().getIntent().getExtras();
        String id_jobb = extras.getString("id_job");

        etsum = view.findViewById(R.id.eTextTask);
        etroot = view.findViewById(R.id.eTextTask2);
        etcounter = view.findViewById(R.id.eTextTask3);
        btn_upload = view.findViewById(R.id.btn_upload);
        tvIdJob = view.findViewById(R.id.tv_id_job);
        imgIdProf = view.findViewById(R.id.IdProf);
        textViewSelected = view.findViewById(R.id.textviewSelected);
        fillDetail(id_jobb);

        imgIdProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);
                    }
                } else {
                    Log.e("Else", "Else");
                    showFileChooser();
                }

            }

            private void showFileChooser() {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri picUri = data.getData();
            filePath = getPath(picUri);
            File file = new File(filePath);
            filaName = file.getName();
            Log.i("fileName", filaName);
            if (filePath != null) {
                try {
                    textViewSelected.setText("File Selected");
                    Log.i("filePath", String.valueOf(filePath));
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), picUri);
                    btn_upload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            summary = etsum.getText().toString().trim();
                            root = etroot.getText().toString().trim();
                            counter = etcounter.getText().toString().trim();
                            id_job = tvIdJob.getText().toString().trim();
                            submit(bitmap);
                        }
                    });

                    imgIdProf.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(
                        getActivity(), "no image selected",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

//    private void uploadBitmap(final Bitmap bitmap) {
//        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, ROOT_URL,
//                new Response.Listener<NetworkResponse>() {
//                    @Override
//                    public void onResponse(NetworkResponse response) {
//                        try {
//                            JSONObject obj = new JSONObject(new String(response.data));
//                            Toast.makeText(getActivity().getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//                        Log.e("GotError",""+error.getMessage());
//                    }
//                }) {
//
//
//            @Override
//            protected Map<String, DataPart> getByteData() {
//                Map<String, DataPart> params = new HashMap<>();
//                long imagename = System.currentTimeMillis();
//                params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
//                return params;
//            }
//        };
//
//        //adding the request to volley
//        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
//    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private String getPath(Uri picUri) {
        Cursor cursor = getActivity().getContentResolver().query(picUri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    private void fillDetail(String id_job) {
        Log.i("id_jobku", id_job);

        JsonObjectRequest StrReq = new JsonObjectRequest(Request.Method.GET, server.progreesjob_withToken + "?id_job=" + id_job, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Log.i("response bisa", String.valueOf(response));

                try {
                    JSONObject job = response.getJSONObject("job");

                    tvIdJob.setText(job.getString("id"));

                } catch (JSONException e) {
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
                // Barer di bawah ini akan di simpan local masing-masing device engineer

//                headers.put("Authorization", "Bearer 14a1105cf64a44f47dd6d53f6b3beb79b65c1e929a6ee94a5c7ad30528d02c3e");
                SharedPreferences mSetting = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
                headers.put("Authorization", mSetting.getString("Token", "missing"));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(StrReq);
    }

    private void submit(final Bitmap bitmap) {
        final JSONObject jobj = new JSONObject();
        try {
            jobj.put("id_job", id_job);
            jobj.put("job_summary", summary);
            jobj.put("job_rootcause", root);
            jobj.put("job_countermeasure", counter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, server.jobdone_withToken, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Log.i("response", response.toString());
                Toast.makeText(getActivity(), "Successfully :)", Toast.LENGTH_LONG).show();

                /*Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);*/
                getActivity().finish();
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        NetworkResponse response = error.networkResponse;
                        String errorMsg = "";
                        if (response != null && response.data != null) {
                            String errorString = new String(response.data);
                            Log.i("log error", errorString);
                        }
                        Toast.makeText(getActivity(), "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /**
             * Passing some request headers
             */
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_job", id_job);
                params.put("job_summary", summary);
                params.put("job_rootcause", root);
                params.put("job_countermeasure", counter);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("job_documentation", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "applicaion/json");
                SharedPreferences mSetting = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
                headers.put("Authorization", mSetting.getString("Token", "missing"));
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(volleyMultipartRequest);
    }
}