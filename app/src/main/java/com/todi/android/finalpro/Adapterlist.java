package com.todi.android.finalpro;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class Adapterlist extends RecyclerView.Adapter<Adapterlist.ViewHolder> implements Filterable {

    private List<ModelLive> itemlive;
    private ValueFilter valueFilter;
    private List<ModelLive> ListFilter;
    private int layout;

    public Adapterlist(List<ModelLive> itemlive, Activity activity, int layout) {
        super();
        this.layout = layout;
        this.itemlive = itemlive;
        ListFilter = itemlive;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelLive infolive = itemlive.get(position);
        holder.namahome.setText(infolive.getNama_home());
        holder.namaaway.setText(infolive.getNama_away());
        holder.scorehome.setText(infolive.getHome_score());
        holder.scoreaway.setText(infolive.getAway_score());
        holder.tanggal.setText(infolive.getTanggal());

    }

    public ModelLive getItemAtPosition(int position) {
        return itemlive.get(position);
    }

    @Override
    public int getItemCount() {
        return itemlive.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView namahome, namaaway, scoreaway, scorehome, tanggal;

        ViewHolder(View itemView) {
            super(itemView);
            namahome = itemView.findViewById(R.id.home_team);
            namaaway = itemView.findViewById(R.id.away_team);
            scorehome = itemView.findViewById(R.id.home_score);
            scoreaway = itemView.findViewById(R.id.away_score);
            tanggal = itemView.findViewById(R.id.date_match);
        }
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }

        return valueFilter;
    }

    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() >0){
            List<ModelLive> filterlist = new ArrayList<>();
            for (int i = 0; i < ListFilter.size(); i++) {
                if (((ListFilter.get(i).getNama_home().toUpperCase())
                        .contains(constraint.toString().toUpperCase()))
                        || ((ListFilter.get(i).getNama_away().toUpperCase())
                        .contains(constraint.toString().toUpperCase()))
                        || ((ListFilter.get(i).getHome_score().toUpperCase())
                        .contains(constraint.toString().toUpperCase()))
                        || ((ListFilter.get(i).getAway_score().toUpperCase())
                        .contains(constraint.toString().toUpperCase()))
                        || ((ListFilter.get(i).getTanggal().toUpperCase())
                        .contains(constraint.toString().toUpperCase()))) {
                    ModelLive member = new ModelLive();
                    member.setNama_home(ListFilter.get(i).getNama_home());
                    member.setNama_away(ListFilter.get(i).getNama_away());
                    member.setHome_score(ListFilter.get(i).getHome_score());
                    member.setAway_score(ListFilter.get(i).getAway_score());
                    member.setTanggal(ListFilter.get(i).getTanggal());
                    filterlist.add(member);
                }
            }
            results.count = filterlist.size();
            results.values = filterlist;
        } else

        {
            results.count = ListFilter.size();
            results.values = ListFilter;
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence constraint,
                                  FilterResults results) {
        itemlive = (List<ModelLive>) results.values;
        notifyDataSetChanged();
    }
}
}
