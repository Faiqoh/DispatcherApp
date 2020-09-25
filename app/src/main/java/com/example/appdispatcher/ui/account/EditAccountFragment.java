package com.example.appdispatcher.ui.account;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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
import com.example.appdispatcher.util.server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;


public class EditAccountFragment extends Fragment {

    TextView tvname, tvemail, tvphone, tvaddress, tvidUser;
    ImageView ivuser;
    EditText etName, etEmail, etPhone, etAddress;
    Button btn_submit, btn_cancel;
    String id_user, name, phone, email, address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_account, container, false);
        tvname = view.findViewById(R.id.tvname2);
        tvemail = view.findViewById(R.id.tvemail2);
        tvphone = view.findViewById(R.id.tvphone2);
        tvaddress = view.findViewById(R.id.tvaddress2);
        ivuser = view.findViewById(R.id.ivuser);
        tvidUser = view.findViewById(R.id.tv_id_user);
        fillaccount();

        tvname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.activity_edit_name, null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                fillaccount2();

                btn_submit = mView.findViewById(R.id.btnSubmit);
                etName = mView.findViewById(R.id.eTextName);
                etEmail = mView.findViewById(R.id.eTextEmail);
                etPhone = mView.findViewById(R.id.eTextPhone);
                etAddress = mView.findViewById(R.id.eTextAddress);
                btn_cancel = mView.findViewById(R.id.btnCancel);
                Button btn_submit4 = mView.findViewById(R.id.btnSubmit4);
                Button btn_cancel4 = mView.findViewById(R.id.btnCancel4);
                Button btn_submit3 = mView.findViewById(R.id.btnSubmit3);
                Button btn_cancel3 = mView.findViewById(R.id.btnCancel3);
                Button btn_submit2 = mView.findViewById(R.id.btnSubmit2);
                Button btn_cancel2 = mView.findViewById(R.id.btnCancel2);

                etEmail.setVisibility(GONE);
                etPhone.setVisibility(GONE);
                etAddress.setVisibility(GONE);
                btn_cancel2.setVisibility(GONE);
                btn_cancel4.setVisibility(GONE);
                btn_cancel3.setVisibility(GONE);
                btn_submit2.setVisibility(GONE);
                btn_submit4.setVisibility(GONE);
                btn_submit3.setVisibility(GONE);

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_user = tvidUser.getText().toString().trim();
                        name = etName.getText().toString().trim();
                        if (etName.getText().toString().length() == 0) {
                            etName.setError("Name Should not be empty!");
                        } else {
                            profileupdate();
                            dialog.dismiss();
                        }
                    }

                });
            }
        });

        tvemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.activity_edit_name, null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                fillaccount2();

                btn_submit = mView.findViewById(R.id.btnSubmit);
                etName = mView.findViewById(R.id.eTextName);
                etEmail = mView.findViewById(R.id.eTextEmail);
                etPhone = mView.findViewById(R.id.eTextPhone);
                etAddress = mView.findViewById(R.id.eTextAddress);
                btn_cancel = mView.findViewById(R.id.btnCancel);
                Button btn_submit4 = mView.findViewById(R.id.btnSubmit4);
                Button btn_cancel4 = mView.findViewById(R.id.btnCancel4);
                Button btn_submit3 = mView.findViewById(R.id.btnSubmit3);
                Button btn_cancel3 = mView.findViewById(R.id.btnCancel3);
                Button btn_submit2 = mView.findViewById(R.id.btnSubmit2);
                Button btn_cancel2 = mView.findViewById(R.id.btnCancel2);

                etName.setVisibility(GONE);
                etPhone.setVisibility(GONE);
                etAddress.setVisibility(GONE);
                btn_cancel.setVisibility(GONE);
                btn_cancel4.setVisibility(GONE);
                btn_cancel3.setVisibility(GONE);
                btn_submit.setVisibility(GONE);
                btn_submit4.setVisibility(GONE);
                btn_submit3.setVisibility(GONE);

                btn_cancel2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btn_submit2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_user = tvidUser.getText().toString().trim();
                        email = etEmail.getText().toString().trim();
                        if (etEmail.getText().toString().length() == 0) {
                            etEmail.setError("Email Should not be empty!");
                        } else {
                            profileupdateemail();
                            dialog.dismiss();
                        }
                    }

                });
            }
        });

        tvphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.activity_edit_name, null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                fillaccount2();

                btn_submit = mView.findViewById(R.id.btnSubmit);
                etName = mView.findViewById(R.id.eTextName);
                etEmail = mView.findViewById(R.id.eTextEmail);
                etPhone = mView.findViewById(R.id.eTextPhone);
                etAddress = mView.findViewById(R.id.eTextAddress);
                btn_cancel = mView.findViewById(R.id.btnCancel);
                Button btn_submit4 = mView.findViewById(R.id.btnSubmit4);
                Button btn_cancel4 = mView.findViewById(R.id.btnCancel4);
                Button btn_submit3 = mView.findViewById(R.id.btnSubmit3);
                Button btn_cancel3 = mView.findViewById(R.id.btnCancel3);
                Button btn_submit2 = mView.findViewById(R.id.btnSubmit2);
                Button btn_cancel2 = mView.findViewById(R.id.btnCancel2);

                etName.setVisibility(GONE);
                etEmail.setVisibility(GONE);
                etAddress.setVisibility(GONE);

                btn_cancel.setVisibility(GONE);
                btn_cancel2.setVisibility(GONE);
                btn_cancel4.setVisibility(GONE);
                btn_submit.setVisibility(GONE);
                btn_submit2.setVisibility(GONE);
                btn_submit4.setVisibility(GONE);

                btn_cancel3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btn_submit3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_user = tvidUser.getText().toString().trim();
                        phone = etPhone.getText().toString().trim();
                        if (etPhone.getText().toString().length() == 0) {
                            etPhone.setError("Phone Should not be empty!");
                        } else {
                            profileupdatephone();
                            dialog.dismiss();
                        }
                    }

                });
            }
        });

        tvaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.activity_edit_name, null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                fillaccount2();

                etName = mView.findViewById(R.id.eTextName);
                etEmail = mView.findViewById(R.id.eTextEmail);
                etPhone = mView.findViewById(R.id.eTextPhone);
                etAddress = mView.findViewById(R.id.eTextAddress);
                btn_submit = mView.findViewById(R.id.btnSubmit);
                btn_cancel = mView.findViewById(R.id.btnCancel);
                Button btn_submit4 = mView.findViewById(R.id.btnSubmit4);
                Button btn_cancel4 = mView.findViewById(R.id.btnCancel4);
                Button btn_submit3 = mView.findViewById(R.id.btnSubmit3);
                Button btn_cancel3 = mView.findViewById(R.id.btnCancel3);
                Button btn_submit2 = mView.findViewById(R.id.btnSubmit2);
                Button btn_cancel2 = mView.findViewById(R.id.btnCancel2);

                etName.setVisibility(GONE);
                etPhone.setVisibility(GONE);
                etEmail.setVisibility(GONE);
                btn_cancel.setVisibility(GONE);
                btn_cancel2.setVisibility(GONE);
                btn_cancel3.setVisibility(GONE);
                btn_submit.setVisibility(GONE);
                btn_submit2.setVisibility(GONE);
                btn_submit3.setVisibility(GONE);


                btn_cancel4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btn_submit4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_user = tvidUser.getText().toString().trim();
                        address = etAddress.getText().toString().trim();
                        if (etAddress.getText().toString().length() == 0) {
                            etAddress.setError("Phone Should not be empty!");
                        } else {
                            profileupdateaddress();
                            dialog.dismiss();
                        }
                    }

                });
            }
        });
        return view;
    }

    private void profileupdateemail() {
        final JSONObject jobj = new JSONObject();
        try {
//            jobj.put("id_user", id_user);
            jobj.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, server.postProfileUpdate, jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jObj = response;
                Toast.makeText(getActivity(), "Successfully :)", Toast.LENGTH_LONG).show();
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Accept", "applicaion/json");
                SharedPreferences mSetting = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
                headers.put("Authorization", mSetting.getString("Token", "missing"));
                return headers;
            }
        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(strReq);
    }

    private void profileupdatephone() {
        final JSONObject jobj = new JSONObject();
        try {
            jobj.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, server.postProfileUpdate, jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jObj = response;
                Toast.makeText(getActivity(), "Successfully :)", Toast.LENGTH_LONG).show();
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Accept", "applicaion/json");
                SharedPreferences mSetting = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
                headers.put("Authorization", mSetting.getString("Token", "missing"));
                return headers;
            }
        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(strReq);
    }

    private void profileupdateaddress() {

        final JSONObject jobj = new JSONObject();
        try {
//            jobj.put("id_user", id_user);
            jobj.put("address", address);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, server.postProfileUpdate, jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jObj = response;
                Toast.makeText(getActivity(), "Successfully :)", Toast.LENGTH_LONG).show();
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Accept", "applicaion/json");
                SharedPreferences mSetting = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
                headers.put("Authorization", mSetting.getString("Token", "missing"));
                return headers;
            }
        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(strReq);
    }

    private void profileupdate() {
        final JSONObject jobj = new JSONObject();
        try {
//            jobj.put("id_user", id_user);
            jobj.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, server.postProfileUpdate, jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jObj = response;
                Toast.makeText(getActivity(), "Successfully :)", Toast.LENGTH_LONG).show();
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Accept", "applicaion/json");
                SharedPreferences mSetting = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
                headers.put("Authorization", mSetting.getString("Token", "missing"));
                return headers;
            }
        };

        strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(strReq);
    }

    private void fillaccount2() {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getuserwithToken, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jUser = response.getJSONObject("user");
                    etName.setText(jUser.getString("name"));
                    etAddress.setText(jUser.getString("address"));
                    etEmail.setText(jUser.getString("email"));
                    etPhone.setText(jUser.getString("phone"));

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
                SharedPreferences mSetting = getActivity().getSharedPreferences("Setting", MODE_PRIVATE);
                headers.put("Authorization", mSetting.getString("Token", "missing"));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(strReq);
    }

    private void fillaccount() {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getuserwithToken, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                /*nestedScrollView.setVisibility(View.VISIBLE);
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);*/
                try {
                    JSONObject jUser = response.getJSONObject("user");
                    Log.i("users", jUser.toString());

                    tvname.setText(jUser.getString("name"));
                    tvidUser.setText(jUser.getString("id"));
                    tvemail.setText(jUser.getString("email"));
                    tvphone.setText(jUser.getString("phone"));
                    tvaddress.setText(jUser.getString("address"));
                    Glide.with(getActivity()).load(jUser.getString("photo_image_url")).into(ivuser);

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
                SharedPreferences mSetting = getActivity().getSharedPreferences("Setting", MODE_PRIVATE);
                headers.put("Authorization", mSetting.getString("Token", "missing"));
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(strReq);
    }
}