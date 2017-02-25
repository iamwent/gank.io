package com.iamwent.gank.ui.category;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iamwent.gank.R;
import com.iamwent.gank.data.bean.Gank;
import com.iamwent.gank.util.SpannableUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */
class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context ctx;
    private List<Gank> ganks;
    private boolean isBeauty;
    private OnItemClickListener listener;

    CategoryAdapter(Context ctx, List<Gank> ganks, boolean isBeauty) {
        this.ctx = ctx;
        this.ganks = ganks;
        this.isBeauty = isBeauty;
    }

    private @LayoutRes int  provideItemLayout() {
        return isBeauty ? R.layout.item_beauty : R.layout.item_gank;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(provideItemLayout(), parent, false);
        return isBeauty ? new BeautyViewHolder(view) : new GankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(ganks.get(position));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                Gank gank = ganks.get(holder.getAdapterPosition());
                listener.onItemClick(gank.desc, gank.url);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ganks == null ? 0 : ganks.size();
    }

    void replace(List<Gank> ganks) {
        this.ganks = ganks;
        notifyDataSetChanged();
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    interface OnItemClickListener {
        void onItemClick(String title, String url);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Gank gank) {
        }
    }

    static class BeautyViewHolder extends ViewHolder {

        @BindView(R.id.iv_beauty)
        ImageView beauty;

        BeautyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        void bind(Gank gank) {
            Glide.with(itemView.getContext())
                    .load(gank.url)
                    .into(beauty);
        }
    }

    static class GankViewHolder extends ViewHolder {

        @BindView(R.id.tv_gank)
        TextView content;

        GankViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        void bind(Gank gank) {
            content.setText(SpannableUtil.formatContent(gank.desc, gank.who));
        }
    }
}
