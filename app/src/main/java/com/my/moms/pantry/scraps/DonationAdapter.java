//package com.my.moms.pantry;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import java.util.List;
//
//public class DonationAdapter extends ArrayAdapter<com.my.moms.pantry.DonationOption> {
//
//    public DonationAdapter(Context context, List<com.my.moms.pantry.DonationOption> donationOptions) {
//        super(context, 0, donationOptions);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder vh;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pantry_dialog, parent, false);
//            vh = new ViewHolder(convertView);
//            convertView.setTag(vh);
//        } else vh = (ViewHolder) convertView.getTag();
//
//        com.my.moms.pantry.DonationOption option = getItem(position);
//
//        vh.description.setText(option.description);
//        vh.amount.setText(option.amount);
//
//        return convertView;
//    }
//
//    private static final class ViewHolder {
//        TextView description;
//        TextView amount;
//
//        public ViewHolder(View v) {
//            description = (TextView) v.findViewById(R.id.item_name);
//            amount = (TextView) v.findViewById(R.id.item_quantity);
//        }
//    }
//}
