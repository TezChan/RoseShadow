package com.jaqen.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenp
 * @version 2017-07-26 13:08
 */

public class GameMainFragment extends Fragment {
    private RecyclerView rvGameList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game_main, container, false);

        rvGameList = (RecyclerView) rootView.findViewById(R.id.rvGameList);

        rvGameList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvGameList.setAdapter(new GameListAdapter(getActivity()));

        return rootView;
    }

    class GameViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgTheme;
        private TextView tvName;
        private Class gameClass;

        public GameViewHolder(View itemView) {
            super(itemView);

            imgTheme = (ImageView) itemView.findViewById(R.id.imgTheme);
            tvName = (TextView) itemView.findViewById(R.id.tvName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (gameClass != null){
                        startActivity(new Intent(getActivity(), gameClass));
                    }
                }
            });
        }

        public void setGameInfo(GameInfo gameInfo){
            imgTheme.setImageResource(gameInfo.getThemeImgId());
            tvName.setText(gameInfo.getName());

            gameClass = gameInfo.getGameClass();
        }
    }

    private class GameListAdapter extends RecyclerView.Adapter<GameViewHolder>{
        private List<GameInfo> games;
        private LayoutInflater inflater;

        public GameListAdapter(Context context){
            inflater = LayoutInflater.from(context);

            games = new ArrayList<>();

            games.add(new GameInfo(R.mipmap.default_game, "3D", DDDGameActivity.class));
            games.add(new GameInfo(R.mipmap.default_game, "俄罗斯方块", TetrisActivity.class));
        }

        @Override
        public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new GameViewHolder(inflater.inflate(R.layout.item_game_list, parent, false));
        }

        @Override
        public void onBindViewHolder(GameViewHolder holder, int position) {
            holder.setGameInfo(games.get(position));
        }

        @Override
        public int getItemCount() {
            return games.size();
        }
    }
}
