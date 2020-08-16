package com.dev5151.educate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dev5151.educate.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class OnBoardAdapter extends SliderViewAdapter<OnBoardAdapter.ViewHolder> {
    private Context context;

    public OnBoardAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout, null);
        return new ViewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        switch (position) {
            case 0:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.ic_slider_img_1)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;

            case 1:
                Glide.with(viewHolder.itemView)
                    .load(R.drawable.ic_slider_img_2)
                    .fitCenter()
                    .into(viewHolder.imageViewBackground);

                break;
            case 2:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.ic_slider_img_3)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;


        }


    }

    @Override
    public int getCount() {
        return 3;
    }

    class ViewHolder extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            this.itemView = itemView;

        }
    }

    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        return drawableResourceId;
    }
}