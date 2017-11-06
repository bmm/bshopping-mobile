package org.lupum.bshopping;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


class MultiSelectionAdapter extends BaseAdapter {
    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<Product> mList;

    public MultiSelectionAdapter(Context context, List<Product> list) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mList = new ArrayList<>();
        mList = list;
    }

    public ArrayList<Product> getCheckedItems() {
        ArrayList<Product> mTempAarry = new ArrayList<>();

        for (Product p : mList) {
            if (p.isSelected()) {
                mTempAarry.add(p);
            }
        }

        return mTempAarry;
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
            convertView = mInflater.inflate(R.layout.row, parent, false);
        }

        Product product = mList.get(position);

        final TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
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

                // Update selected value
                AsyncDatabase db = new AsyncDatabase(mContext);
                db.updateProduct(product, null);
            }
        });

        tvTitle.setOnLongClickListener((MainActivity)mContext);
        tvTitle.setOnTouchListener(new OnSwipeTouchListener(mContext) {
            public void onSwipeRight() {
                new AlertDialog.Builder(mContext)
                        .setTitle(mContext.getString(R.string.confirmation))
                        .setMessage(mContext.getString(R.string.confirm_delete) + " " + tvTitle.getText() + "?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                int position = (Integer)tvTitle.getTag();
                                Product p = mList.get(position);
                                AsyncDatabase db = new AsyncDatabase(mContext);
                                db.deleteProduct(p, (MainActivity)mContext);
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        return convertView;
    }
}