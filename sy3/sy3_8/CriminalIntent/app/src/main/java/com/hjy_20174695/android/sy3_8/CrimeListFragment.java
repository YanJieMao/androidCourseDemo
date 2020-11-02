package com.hjy_20174695.android.sy3_8;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    private class CrimeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Crime mCrime;
        private Button mRequirePolice;
        private TextView mTitleTextView;
        private TextView mDateTextView;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);


        }



        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }



        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(),
                    mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
        }



    }


    //定义一个新的requirePoliceCrimeHolder内部类一个CrimeHolder对应一行列表项，对应布局为 list_item_require_crime
    private class requirePoliceCrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Button mRequirePolice;
        private Crime mCrime;

        //只要收到一个crime，CrimeHolder就会刷新显示mTitleTextView和mDateTextView
        private void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }

        public requirePoliceCrimeHolder(LayoutInflater inflater,ViewGroup parent) {
            //首先实例化 list_item_require_crime 布局然后传给super（）即ViewHolder的构造方法*/
            super(inflater.inflate(R.layout.list_item_crime,parent,false));

            mTitleTextView = (TextView)itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView)itemView.findViewById(R.id.crime_date);
            mRequirePolice = (Button)itemView.findViewById(R.id.require_police);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),"Require Police!",Toast.LENGTH_SHORT).show();
        }
    }







    public class CrimeAdapter extends RecyclerView.Adapter {
        private List<Crime> mCrimes;
        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }
        //视图类别功能
        @Override
        public int getItemViewType(int position) {
            //真不报警
            if(mCrimes.get(position).ismRequiresPolice()) {
                return 1;
            }else{
                return 0;
            }
        }
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            //先创建LayoutInflater
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());

            //判断上面的i的值判断使用什么布局，即getItemViewType的返回值
            if (i == 1)
                return new requirePoliceCrimeHolder(layoutInflater, viewGroup);
            else
                return new CrimeHolder(layoutInflater, viewGroup);
        }

        //需要绑定数据（上下滑动屏幕）时，就会读取当前位置并绑定
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof CrimeHolder) {    //instance,如果左侧的对象是右侧类的实例，则返回true
                Crime crime = mCrimes.get(position);
                ((CrimeHolder)holder).bind(crime);
            }else if (holder instanceof requirePoliceCrimeHolder){
                Crime crime = mCrimes.get(position);
                ((requirePoliceCrimeHolder)holder).bind(crime);
            }
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
