package com.example.appdispatcher.ui.account;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appdispatcher.FabActivity;
import com.example.appdispatcher.LoginActivity;
import com.example.appdispatcher.R;
import com.example.appdispatcher.util.server;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class AccountFragment extends Fragment {

    BottomNavigationView navigation;
    public static final String DATE_FORMAT_5 = "dd MMMM yyyy";
    TextView tvname, tvemail, tvno, tvjobs, tvskill, tvfee, tvdate, tvaddress, tvdate_of_birth, tv_about;
    public static final String GET_ID_JOB = "get_id_job";
    ImageView ivuser;
    Button btn_logout, btn_edit;
    ShimmerFrameLayout shimmerFrameLayout;
    NestedScrollView nestedScrollView;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        navigation = getActivity().findViewById(R.id.nav_view);

        tvname = root.findViewById(R.id.text_name);
        tvemail = root.findViewById(R.id.text_email);
        tvno = root.findViewById(R.id.text_number);
        tvjobs = root.findViewById(R.id.textViewtotal);
        tvskill = root.findViewById(R.id.textViewskill2);
        tvfee = root.findViewById(R.id.textViewfee2);
        tvdate = root.findViewById(R.id.textViewdate2);
        tvaddress = root.findViewById(R.id.textViewaddress2);
        ivuser = root.findViewById(R.id.ivuser);
        btn_logout = root.findViewById(R.id.btn_logout);
        shimmerFrameLayout = root.findViewById(R.id.shimmer_view_container);
        nestedScrollView = root.findViewById(R.id.nestedaccount);
        btn_edit = root.findViewById(R.id.btn_edit);
        tvdate_of_birth = root.findViewById(R.id.text_date_of_birth);
        tv_about = root.findViewById(R.id.tv_about);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FabActivity.class);
                intent.putExtra(GET_ID_JOB, "edit_profile");
                startActivity(intent);
            }
        });

        tvjobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FabActivity.class);
                intent.putExtra(GET_ID_JOB, "detail_engineer");
                startActivity(intent);
            }
        });

        tv_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.layout_about, null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Setting", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.clear();
                        editor.remove("Token");
                        editor.apply();
                        Log.i(TAG, "Now log out and start the activity login");
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                });

                // Set the alert dialog no button click listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when No button clicked
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                positiveButton.setTextColor(Color.parseColor("#000000"));
                positiveButton.setBackgroundColor(Color.parseColor("#FFFFFF"));

                negativeButton.setTextColor(Color.parseColor("#000000"));
                negativeButton.setBackgroundColor(Color.parseColor("#FFFFFF"));

//                deleteCache(getContext());
//                if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
//                    ((ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE)).clearApplicationUserData();
//                }



                /*File dir = getContext().getCacheDir();
                if (dir != null && dir.isDirectory()){
                    deleteDir(dir);
                }*/
            }
        });

        fillaccount();

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.filter_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item3 = menu.findItem(R.id.item_filter);
        MenuItem item2 = menu.findItem(R.id.item_search);
        MenuItem item4 = menu.findItem(R.id.item_filter_date);
        item4.setVisible(false);
        item3.setVisible(false);
        item2.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_notif) {
            Intent intent = new Intent(getContext(), FabActivity.class);
            intent.putExtra(GET_ID_JOB, "notif");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    /*public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }*/

    private void fillaccount() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_5);
        final DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
//        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        final DateFormat date = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        final Locale localeID = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getuserwithToken, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                nestedScrollView.setVisibility(View.VISIBLE);
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                try {
                    JSONObject jUser = response.getJSONObject("user");
                    Log.i("users", jUser.toString());

                    tvname.setText(jUser.getString("name"));
                    tvemail.setText(jUser.getString("email"));
                    tvno.setText(jUser.getString("phone"));
                    tvaddress.setText(jUser.getString("address"));
                    Glide.with(getActivity()).load(jUser.getString("photo_image_url")).into(ivuser);
                    tvjobs.setText(jUser.getString("job_engineer_count") + " Jobs");
                    tvskill.setText(jUser.getString("category_engineer"));
                    tvfee.setText(formatRupiah.format((Double.parseDouble(jUser.getString("fee_engineer_count")))));
                    Date date_of_birth = date.parse(jUser.getString("date_of_birth"));
                    tvdate_of_birth.setText(dateFormat.format(date_of_birth));
                    Date join_date = inputFormat.parse(jUser.getString("date_of_join"));
                    tvdate.setText(dateFormat.format(join_date));

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
}
