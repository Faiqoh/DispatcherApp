package com.example.appdispatcher.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdispatcher.Adapter.JobCategoryAdapter;
import com.example.appdispatcher.Adapter.JobListAdapter;
import com.example.appdispatcher.Adapter.RecomenJobAdapter;
import com.example.appdispatcher.R;
import com.example.appdispatcher.ui.detail.ScrollingActivityDetail;
import com.example.appdispatcher.util.server;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment implements JobListAdapter.JListAdapter, JobCategoryAdapter.CListAdapter {

    public static final String ID_JOB = "id_job";
    public static final String ID_JOB2 = "id_job1";
    public static final String GET_ID_JOB = "get_id_job";
    private HomeViewModel homeViewModel;
    public List<HomeViewModel> mList = new ArrayList<>();
    JobListAdapter mAdapter;
    ArrayList<RecomendJobViewModel> rList = new ArrayList<>();
    RecomenJobAdapter rAdapter;
    public List<JobCategoryViewModel> cList = new ArrayList<>();
    JobCategoryAdapter cAdapter;
    TextView name, detailUser, tvSeeAllJob, tvSeeAllJobCategory, tvSeeAllJobList;
    ImageView imageViewOtof;
    ShimmerFrameLayout shimmerFrameLayout;
    RelativeLayout relativeLayoutHome;
    NestedScrollView nestedhome;
    BottomNavigationView navigation;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);*/
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        fillAccountUser();
        relativeLayoutHome = root.findViewById(R.id.headerhome);
        nestedhome = root.findViewById(R.id.nestedhome);
        shimmerFrameLayout = root.findViewById(R.id.shimmer_view_container);

        name = root.findViewById(R.id.text_name);
        detailUser = root.findViewById(R.id.text_detail);
        imageViewOtof = root.findViewById(R.id.imageViewlistjob);
        navigation = getActivity().findViewById(R.id.nav_view);

        nestedhome.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            boolean isNavigationHide = false;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY < oldScrollY) { // up
                    animateNavigation(false);
                }
                if (scrollY > oldScrollY) { // down
                    animateNavigation(true);
                }
            }

            private void animateNavigation(boolean hide) {
                if (isNavigationHide && hide || !isNavigationHide && !hide) return;
                isNavigationHide = hide;
                int moveY = hide ? (2 * navigation.getHeight()) : 0;
                navigation.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
            }
        });

        RecyclerView recyclerViewJobCategory = root.findViewById(R.id.recyclerViewJobCategory);
        LinearLayoutManager layoutManagerJobCategory = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewJobCategory.setLayoutManager(layoutManagerJobCategory);
        cAdapter = new JobCategoryAdapter(this, cList);
        recyclerViewJobCategory.setAdapter(cAdapter);
        fillDataJobCategory();

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewlistjob);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new JobListAdapter(this, mList);
        recyclerView.setAdapter(mAdapter);
//        fillData();
        fillDataListJob();

        RecyclerView recyclerView2 = root.findViewById(R.id.recyclerViewrecomendjob);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        recyclerView2.setLayoutManager(layoutManager2);
        rAdapter = new RecomenJobAdapter(rList);
        recyclerView2.setAdapter(rAdapter);
//        fillData2();

        tvSeeAllJobCategory = root.findViewById(R.id.text_see_all);
        tvSeeAllJobList = root.findViewById(R.id.text_see_all2);
        tvSeeAllJobCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScrollingActivityDetail.class);
                intent.putExtra(GET_ID_JOB, "job_category");
                startActivity(intent);
            }
        });

        tvSeeAllJobList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SeeAllActivity.class);
                intent.putExtra(GET_ID_JOB, "job_list");
                startActivity(intent);
            }
        });

        final SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);

                Fragment frg = null;
                frg = getFragmentManager().findFragmentById(R.id.nav_host_fragment);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();

                mList.clear();

                cList.clear();

            }
        });
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
        item3.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_logout) {
//            SharedPreferences blockSession = getActivity().getSharedPreferences("blockSession", 0);
//            SharedPreferences.Editor blockEdit = blockSession.edit();
//            blockEdit.clear();
//            blockEdit.apply();
//            //finish();       /****<-----commented out this line---****/
//            Intent intent = new Intent(getActivity(), LoginActivity.class);
//            startActivity(intent);
//
//            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fillAccountUser() {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getUser_withToken, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                relativeLayoutHome.setVisibility(View.VISIBLE);
                nestedhome.setVisibility(View.VISIBLE);

                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                try {
                    JSONObject jUser = response.getJSONObject("users");

                    Log.i("users", jUser.toString());

                    name.setText("Hi " + jUser.getString("name"));
                    detailUser.setText(jUser.getString("email") + "\n\n" + jUser.getString("address"));

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
        requestQueue.add(strReq);
    }

    private void fillDataListJob() {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getJobListSumm, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response job list", response.toString());
                JSONObject jObj = response;
                try {
                    JSONArray jray = jObj.getJSONArray("job");

                    if (response.length() > 0) {
                        for (int i = 0; i < jray.length(); i++) {
                            JSONObject cat = jray.getJSONObject(i);

                            HomeViewModel itemCategory = new HomeViewModel();
                            if (cat.getString("job_status").equals("Open")) {
                                if (mList.size() < 5) {
                                    itemCategory.setJudul(cat.getJSONObject("category").getString("category_name"));
                                    itemCategory.setId_job(cat.getString("id"));
                                    itemCategory.setFoto(cat.getJSONObject("category").getString("category_image_url"));
                                    itemCategory.setCustomer(cat.getJSONObject("customer").getString("customer_name"));
                                    itemCategory.setLocation(cat.getJSONObject("location").getString("long_location"));
                                    itemCategory.setJob_name(cat.getString("job_name"));

                                    mList.add(itemCategory);
                                }
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
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
        requestQueue.add(strReq);
    }

    private void fillDataJobCategory() {

        final JSONObject jobj = new JSONObject();
        final String category_name = "null";
        try {
            jobj.put("category_name", category_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET, server.getJobCategory, jobj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", response.toString());
                JSONObject jObj = response;
                try {
                    JSONArray jray = jObj.getJSONArray("job_category");

                    if (response.length() > 0) {
                        Resources resources = getResources();
//
//                        TypedArray a = resources.obtainTypedArray(R.array.image_category);
//                        String[] arFotoku = new String[a.length()];

                        for (int i = 0; i < jray.length(); i++) {
                            JSONObject items = jray.getJSONObject(i);

                            JobCategoryViewModel itemCategory = new JobCategoryViewModel();

                            itemCategory.setFoto(items.getString("category_image_url"));
                            itemCategory.setJudul(items.getString("category_name"));
                            itemCategory.setId_category(items.getInt("id"));

                            cList.add(itemCategory);
                        }
                        cAdapter.notifyDataSetChanged();
                    }
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
        requestQueue.add(strReq);
    }

    @Override
    public void doClick(int pos) {
        Intent intent = new Intent(getContext(), ScrollingActivityDetail.class);
        intent.putExtra(ID_JOB, mAdapter.getItem(pos));
        intent.putExtra(GET_ID_JOB, "id_list");
        startActivity(intent);
    }

    public void doClickCategory(int pos) {
        Intent intent = new Intent(getActivity(), SeeAllActivity.class);
        intent.putExtra(ID_JOB2, cAdapter.getItem(pos));
        intent.putExtra(GET_ID_JOB, "id_category");
        startActivity(intent);
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

//    private void fillData2() {
//        Resources resources = getResources();
//        String[] arJudul2 = resources.getStringArray(R.array.titlejob);
//        TypedArray a = resources.obtainTypedArray(R.array.recomend_job);
//        Drawable[] arFoto2 = new Drawable[a.length()];
//        for (int i = 0; i < arFoto2.length; i++) {
//            arFoto2[i] = a.getDrawable(i);
//        }
//        a.recycle();
//
//        for (int i = 0; i < arJudul2.length; i++) {
//            rList.add(new RecomendJobViewModel(arJudul2[i], arFoto2[i]));
//        }
//        rAdapter.notifyDataSetChanged();
//    }

//    private void fillData() {
//        Resources resources = getResources();
//        String[] arJudul = resources.getStringArray(R.array.title);
//        TypedArray a = resources.obtainTypedArray(R.array.list_job);
//        Drawable[] arFoto = new Drawable[a.length()];
//        for (int i = 0; i < arFoto.length; i++) {
//            arFoto[i] = a.getDrawable(i);
//        }
//        a.recycle();
//
//        for (int i = 0; i < arJudul.length; i++) {
//            mList.add(new HomeViewModel(arJudul[i], arFoto[i]));
//        }
//        mAdapter.notifyDataSetChanged();
//    }
}
