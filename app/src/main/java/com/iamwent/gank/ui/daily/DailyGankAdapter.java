package com.iamwent.gank.ui.daily;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iamwent.gank.R;
import com.iamwent.gank.data.bean.Gank;
import com.iamwent.gank.ui.image.ImageActivity;
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
class DailyGankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_IMAGE = 0;
    private static final int TYPE_CONTENT = 1;

    private LayoutInflater inflater;
    private List<Gank> ganks;
    private OnItemClickListener listener;

    DailyGankAdapter(Context ctx, List<Gank> ganks) {
        this.ganks = ganks;
        this.inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_IMAGE : TYPE_CONTENT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_IMAGE) {
            View view = inflater.inflate(R.layout.item_image, parent, false);
            return new ImageViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_content, parent, false);
            return new ContentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).bind(ganks.get(position));
            ((ImageViewHolder) holder).cover.setOnClickListener(v -> {
                if (listener != null) {
                    Gank gank = ganks.get(holder.getAdapterPosition());
                    ImageActivity.start(holder.itemView.getContext(), gank.url);
                }
            });
        } else if (holder instanceof ContentViewHolder) {
            boolean showTitle = !ganks.get(position).type.equals(ganks.get(position - 1).type);
            ((ContentViewHolder) holder).bind(ganks.get(position), showTitle);
            ((ContentViewHolder) holder).content.setOnClickListener(v -> {
                if (listener != null) {
                    Gank gank = ganks.get(holder.getAdapterPosition());
                    listener.onItemClick(gank.desc, gank.url);
                }
            });
        }
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

    static class ContentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView title;

        @BindView(R.id.tv_content)
        TextView content;

        ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Gank gank, boolean showTitle) {
            title.setText(gank.type);
            title.setVisibility(showTitle ? View.VISIBLE : View.GONE);

            content.setText(SpannableUtil.formatContent(gank.desc, gank.who));
        }
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView cover;
        @BindView(R.id.tv_date)
        TextView date;

        ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Gank gank) {
            Glide.with(itemView.getContext())
                    .load(gank.url)
                    .into(cover);

            String dateStr = gank.publishedAt.substring(0, 10).replace('-', '/');
            date.setText(dateStr);
        }
    }
}
