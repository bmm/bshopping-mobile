package org.lupum.bshopping;

/**
 * Created by bmm on 24/08/16.
 */
import java.util.ArrayList;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class MultiSelectionAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<Product> mList;

    public MultiSelectionAdapter(Context context, ArrayList<Product> list) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mList = new ArrayList<>();
        this.mList = list;
    }

    public ArrayList<Product> getCheckedItems() {
        ArrayList<Product> mTempArry = new ArrayList<Product>();

        for (Product p : mList) {
            if (p.isSelected()) {
                mTempArry.add(p);
            }
        }

        return mTempArry;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row, null);
        }

        Product product = mList.get(position);

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        tvTitle.setTag(position);
        tvTitle.setText(product.getName());

        if (product.isSelected()) {
            tvTitle.setBackgroundResource(R.color.selectedBg);
        } else {
            tvTitle.setBackgroundResource(R.color.unSelectedBg);
        }

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer position = (Integer)view.getTag();
                Product product = mList.get(position);
                product.setSelected(!product.isSelected());

                if (product.isSelected()) {
                    view.setBackgroundResource(R.color.selectedBg);
                } else {
                    view.setBackgroundResource(R.color.unSelectedBg);
                }
            }
        });

        return convertView;
    }
}