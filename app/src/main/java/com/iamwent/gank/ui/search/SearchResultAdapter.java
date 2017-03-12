package com.iamwent.gank.ui.search;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iamwent.gank.R;
import com.iamwent.gank.data.bean.Search;
import com.iamwent.gank.ui.base.WebActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamwent on 12/03/2017.
 *
 * @author iamwent
 * @since 12/03/2017
 */
class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private Context ctx;
    private List<Search> results;

    SearchResultAdapter(Context ctx, List<Search> results) {
        this.ctx = ctx;
        this.results = results;
    }

    private
    @LayoutRes
    int provideItemLayout() {
        return R.layout.item_search_result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(provideItemLayout(), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(results.get(position));
    }

    @Override
    public int getItemCount() {
        return results == null ? 0 : results.size();
    }

    public void replace(List<Search> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_info)
        TextView info;
        @BindView(R.id.tv_desc)
        TextView desc;

        private Search search;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Search search) {
            this.search = search;

            String who = search.who == null ? "None" : search.who;
            String time = search.publishedAt.substring(0, 10);
            info.setText(String.format("%s\t\t%s", who, time));

            desc.setText(search.desc);

            itemView.setOnClickListener(v -> {
                WebActivity.start(itemView.getContext(), search.desc, search.url);
            });
        }
    }
}
