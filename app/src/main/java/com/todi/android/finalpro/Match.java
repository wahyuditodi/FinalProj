package com.todi.android.finalpro;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Match extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View view;
    private RecyclerView recyclerView;
    private List<ModelLive> itemLive;
    private SwipeRefreshLayout swipe;
    private Adapterlist adapter;

    public static Match newInstance() {
        return new Match();
    }

    public Match() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_match_child, container, false);
        init();
        return view;
    }

    private void init() {
        getViews();
        swipping();
        setListener();
    }

    private void getViews() {
        recyclerView = view.findViewById(R.id.recycler_view);
        swipe = view.findViewById(R.id.swipe_refresh);
        itemLive = new ArrayList<>();
        adapter = new Adapterlist(itemLive, getActivity(), R.layout.item_match);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void swipping() {
        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                fetchData();
            }
        });
    }

    private void fetchData() {
        JsonObjectRequest reqMatch = new JsonObjectRequest(Request.Method.GET,
                "https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response != null) {
//                                JSONObject hasil = new JSONObject();
                                JSONArray hasile = response.getJSONArray("events");
                                for (int i = 0; i < hasile.length(); i++) {
                                    JSONObject obj = hasile.getJSONObject(i);
                                    ModelLive mod = new ModelLive();
                                    mod.setNama_home(obj.getString("strHomeTeam"));
                                    mod.setNama_away(obj.getString("strAwayTeam"));
                                    String hscore = obj.getString("intHomeScore");
                                    String ascore = obj.getString("intAwayScore");
                                    if (obj.isNull("intHomeScore")){
                                        mod.setHome_score("?");
                                    } else {
                                        mod.setHome_score(hscore);
                                    }
                                    if (obj.isNull("intAwayScore")){
                                        mod.setAway_score("?");
                                    } else {
                                        mod.setAway_score(ascore);
                                    }
//                                    mod.setHome_score(obj.getString("intHomeScore"));
//                                    mod.setAway_score(obj.getString("intAwayScore"));
                                    mod.setTanggal(obj.getString("strDate"));
                                    itemLive.add(mod);
                                }
                            }
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                        swipe.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipe.setRefreshing(false);
            }
        });
        Kontrol.getInstance().addToRequestQueue(reqMatch);
    }

    private void setListener() {
        recyclerView.addOnItemTouchListener(new TouchList(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
/*                ModelLive mod = adapter.getItemAtPosition(position);
                String namahome = mod.getNama_home();
                String namaaway = mod.getNama_away();
                String homescore = mod.getHome_score();
                String awayscore = mod.getAway_score();
                String tanggal = mod.getTanggal();
                Intent intent = new Intent(getActivity(), )*/

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onRefresh() {
        refetch();
    }

    private void refetch() {
        if (itemLive != null) {
            itemLive.clear();
        }
        fetchData();
        adapter.notifyDataSetChanged();
    }
}
