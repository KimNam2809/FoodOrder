package com.man293.food_ordering_spoon.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.man293.food_ordering_spoon.views.activities.ProductActivity;
import com.man293.food_ordering_spoon.models.HomeProduct;
import com.man293.food_ordering_spoon.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeProductAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<HomeProduct> homeProducts;
    ViewHolder holder;

    public HomeProductAdapter(Context context, int layout, ArrayList<HomeProduct> homeProducts) {
        this.context = context;
        this.layout = layout;
        this.homeProducts = homeProducts;
    }

    @Override
    public int getCount() {
        return homeProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        ShapeableImageView img;
        TextView name, description, price;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.tv_home_single_product_name);
            holder.description = (TextView) convertView.findViewById(R.id.tv_home_single_product_desc);
            holder.price = (TextView) convertView.findViewById(R.id.tv_home_single_product_price);
            holder.img = (ShapeableImageView) convertView.findViewById(R.id.img_home_single_product);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HomeProduct homeProduct = homeProducts.get(position);
        holder.name.setText(homeProduct.getHomeProductName());
        holder.description.setText(homeProduct.getHomeProductDescription());
        holder.price.setText("$" + Double.toString(homeProduct.getHomeProductPrice()));

        String imgName = homeProduct.getHomeProductImg();
        String imgPath = context.getString(R.string.PUBLIC_IMAGES, imgName);
        String baseUrl = context.getString(R.string.BASE_URL);

        baseUrl = baseUrl.replaceAll("/$", ""); // Remove trailing slash from the base URL
        imgPath = imgPath.replaceAll("^/", ""); // Remove leading slash from the image path


        // Concatenate base URL with image path
        String fullImageUrl = baseUrl + "/" + imgPath;

        Picasso.get().load(fullImageUrl).into(holder.img);

        convertView.findViewById(R.id.layoutHomeProductItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("product", (Serializable) homeProduct);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
