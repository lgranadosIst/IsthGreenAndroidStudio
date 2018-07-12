package com.isthmusit.isthgreen.isthgreenapp.entity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isthmusit.isthgreen.isthgreenapp.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> posts;
    private int layout;
    private PostAdapter.OnItemClickListener itemClickListener;

    public PostAdapter(List<Post> posts, int layout, PostAdapter.OnItemClickListener listener){
        this.posts = posts;
        this.layout = layout;
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        PostAdapter.ViewHolder vh = new PostAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(posts.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textPostId;
        private TextView textPostName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textPostId = itemView.findViewById(R.id.textPostId);
            this.textPostName = itemView.findViewById(R.id.textPostName);
        }

        public void bind(final Post post, final PostAdapter.OnItemClickListener plistener){
            this.textPostId.setText("Post ID: " + post.getId().toString());
            this.textPostName.setText("Name: " + post.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    plistener.onItemClick(post.getId(),getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Long id, int position);
    }
}
