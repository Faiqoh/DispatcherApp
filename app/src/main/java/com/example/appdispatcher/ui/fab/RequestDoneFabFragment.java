package com.example.appdispatcher.ui.fab;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.appdispatcher.R;
import com.example.appdispatcher.VolleyMultipartRequest;
import com.example.appdispatcher.ui.detail.ProgressDoneViewModel;
import com.example.appdispatcher.util.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class RequestDoneFabFragment extends Fragment {

    TextView tvname, tvnominal, tvreason, tv_id_job, textViewSelected;
    ImageView imgupload, imgIdProf;
    EditText etNote;
    private Bitmap bitmap;
    private String filePath, filaName;
    private ProgressDialog pd;
    public static final int PICKFILE_RESULT_CODE = 1;
    public static final int REQUEST_PERMISSIONS = 100;
    Button btn_upload;
    String id_job, note;
    public static final String ID_JOB = "id_job";
    public static final String GET_ID_JOB = "get_id_job";
    ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_request_done_fab, container, false);
        tvname = view.findViewById(R.id.tvNama2);
        tvnominal = view.findViewById(R.id.tvNominal2);
        tvreason = view.findViewById(R.id.tvAlasan2);
        imgupload = view.findViewById(R.id.imgUpload);
        tv_id_job = view.findViewById(R.id.tv_id_job);
        etNote = view.findViewById(R.id.etNote);
        imgIdProf = view.findViewById(R.id.IdProf);
        textViewSelected = view.findViewById(R.id.textviewSelected);
        pd = new ProgressDialog(getActivity(), R.style.MyTheme);
        btn_upload = view.findViewById(R.id.btn_upload);
        scrollView = view.findViewById(R.id.scrollingreqdone);

        Bundle extras = getActivity().getIntent().getExtras();
        String id_jobb = extras.getString("id_job");

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
                chooseFile.setType("image/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note = etNote.getText().toString().trim();
                id_job = tv_id_job.getText().toString().trim();
                if (filePath == null) {
                    Toast.makeText(getActivity(), "Image Item Should not be empty!", Toast.LENGTH_SHORT).show();
                } else if (filePath != null) {
                    File file = new File(filePath);
                    if (file.length() > 10000000) {
                        Toast.makeText(getActivity(), "Your image more than 10mb, please upload less than 10mb!", Toast.LENGTH_SHORT).show();
                    } else {
                        submit(bitmap);
                    }
                } else {
                    submit(bitmap);
                }
            }

        });

        return view;
    }

    public void fillDetail(String id_jobb) {
        final Locale localeID = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        JsonObjectRequest StrReq = new JsonObjectRequest(Request.Method.GET, server.progreesjob_withToken + "?id_job=" + id_jobb, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject job = response.getJSONObject("job");
                    JSONObject history_request = job.getJSONObject("latest_job_request");

                    tvname.setText(history_request.getString("name_item"));
                    tvreason.setText(history_request.getString("function_item"));
                    tvnominal.setText(formatRupiah.format((Double.parseDouble(history_request.getString("price_item")))));
                    Glide.with(getActivity()).load(history_request.getString("documentation_item_url")).into(imgupload);
                    tv_id_job.setText(job.getString("id"));

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
                SharedPreferences mSetting = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
                headers.put("Authorization", mSetting.getString("Token", "missing"));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(StrReq);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri picUri = data.getData();
            filePath = getPath(picUri);
            File file = new File(filePath);
            filaName = file.getName();
            Log.i("file length", String.valueOf(Integer.parseInt(String.valueOf(file.length()))));
            if (filePath != null) {
                try {
                    textViewSelected.setText("File Selected");
                    Log.i("filePath", String.valueOf(filePath));
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), picUri);
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

    private void submit(final Bitmap bitmap) {
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();

        scrollView.setVisibility(View.GONE);

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, server.postUpdateRequest, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Log.i("response", response.toString());
                pd.dismiss();
                Intent intent = getActivity().getIntent();
                intent.putExtra(ID_JOB, id_job);
                intent.putExtra(GET_ID_JOB, "id_job_progress");
//                Log.i("id_job", id_job);
                getActivity().setResult(1, intent);
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
                if (etNote.getText().toString().trim().length() == 0) {
                    params.put("note_success", "-");
                } else {
                    params.put("note_success", note);
                }
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("documentation_success", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
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
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(volleyMultipartRequest);
    }

    @Override
    public void onDestroy() {
        if (pd != null && pd.isShowing()){
            pd.dismiss();
        }
        super.onDestroy();
    }
}