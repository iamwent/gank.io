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
import com.iamwent.gank.ui.base.WebActivity;
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
    private static final int TYPE_FOOTER = 2;

    private LayoutInflater inflater;
    private List<Gank> ganks;

    DailyGankAdapter(Context ctx, List<Gank> ganks) {
        this.ganks = ganks;
        this.inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_IMAGE;
        }

        if (position == ganks.size()) {
            return TYPE_FOOTER;
        }

        return TYPE_CONTENT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {
            View view = inflater.inflate(R.layout.item_daily_content, parent, false);
            return new ContentViewHolder(view);
        } else if (viewType == TYPE_IMAGE) {
            View view = inflater.inflate(R.layout.item_daily_image, parent, false);
            return new ImageViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_daily_footer, parent, false);
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == ganks.size()) return;

        if (holder instanceof ContentViewHolder) {
            boolean showTitle = !ganks.get(position).type.equals(ganks.get(position - 1).type);
            ((ContentViewHolder) holder).bind(ganks.get(position), showTitle);
        } else if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).bind(ganks.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return ganks == null ? 0 : ganks.size() + 1;
    }

    void replace(List<Gank> ganks) {
        this.ganks = ganks;
        notifyDataSetChanged();
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView title;

        @BindView(R.id.tv_content)
        TextView content;

        @BindView(R.id.divider)
        View divider;

        private Gank gank;

        ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Gank newGank, boolean showTitle) {
            this.gank = newGank;

            title.setText(gank.type);
            title.setVisibility(showTitle ? View.VISIBLE : View.GONE);
            divider.setVisibility(showTitle ? View.VISIBLE : View.GONE);

            content.setText(SpannableUtil.formatContent(gank.desc, gank.who));
            content.setOnClickListener(v -> WebActivity.start(itemView.getContext(), gank.desc, gank.url));
        }
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView cover;
        @BindView(R.id.tv_date)
        TextView date;

        private Gank gank;

        ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Gank newGank) {
            this.gank = newGank;

            Glide.with(itemView.getContext())
                    .load(gank.url)
                    .placeholder(cover.getDrawable())
                    .into(cover);

            String dateStr = gank.publishedAt.substring(0, 10).replace('-', '/');
            date.setText(dateStr);

            cover.setOnClickListener(v -> ImageActivity.start(itemView.getContext(), gank.url));
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
