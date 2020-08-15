package com.aravind.wisdomleaf.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aravind.wisdomleaf.R;
import com.aravind.wisdomleaf.model.ApiResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {

    private List<ApiResponse> apiResponses;
    private Context context;
    AlertDialog.Builder builder;

    PhotosAdapter(List<ApiResponse> apiResponseList, Context context) {
        this.apiResponses = apiResponseList;
        this.context = context;
    }

    View view;

    @NonNull
    @Override
    public PhotosAdapter.PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.list_item, null);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        final ApiResponse apiResponse = apiResponses.get(position);
        holder.id.setText("Id:-  " + apiResponse.getId());
        holder.author.setText("Author:-  " + apiResponse.getAuthor());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(context);
                builder.setMessage(apiResponse.getAuthor())
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Author");
                alert.show();
            }
        });

        Picasso.get()
                .load(apiResponse.getDownload_url())
                .centerCrop()
                .fit()
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return apiResponses.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView author;
        ImageView image;

        PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_id);
            author = itemView.findViewById(R.id.tv_author);
            image = itemView.findViewById(R.id.img_disp);
        }
    }
}