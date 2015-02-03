package com.powwau.setilite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * 20150203. Initial version created by jorge.
 */
public class SignalDataAdapter extends ArrayAdapter<SignalData> {
    Context mContext;
    List<SignalData> mSignalData;

    public SignalDataAdapter(Context context, List<SignalData> signalData) {
        super(context, R.layout.list_item_entry, signalData);
        mContext = context;
        mSignalData = signalData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        rowView = reuseOrGenerateRowView(convertView, parent);
        displayContentInView(position, rowView);

        return rowView;
    }

    private View reuseOrGenerateRowView(View convertView, ViewGroup parent) {
        View rowView;
        if (convertView != null) {
            rowView = convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item_entry, parent, false);
        }
        return rowView;
    }

    private void displayContentInView(int position, View rowView) {
        if (rowView != null) {
            TextView textViewStar = (TextView)rowView.findViewById(R.id.text_view_star_name);
            textViewStar.setText(mSignalData.get(position).getStarName());
            TextView textViewProbability =(TextView)rowView.findViewById(R.id.text_view_probability);
            textViewProbability.setText(mSignalData.get(position).getProbabilityString());
        }
    }
}
