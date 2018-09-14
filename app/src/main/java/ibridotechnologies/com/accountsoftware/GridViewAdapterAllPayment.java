package ibridotechnologies.com.accountsoftware;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ibridotechnologies.com.accountsoftware.Model.AllBookDetails;

import static android.view.Gravity.LEFT;

/**
 * Created by sushil on 13/09/18.
 */

public class GridViewAdapterAllPayment extends BaseAdapter {
    //Context
    private Context context;
    AlertDialog.Builder builder;

    float total=0;
    AllBookDetails allBookDetails = new AllBookDetails();


    //Array List that would contain the urls and the titles for the images
    private ArrayList<String> _dates;
    private ArrayList<String> _paymentType;
    private ArrayList<String> _amount;
    private TextView tv;

    public GridViewAdapterAllPayment(Context context, ArrayList<String> _dates, ArrayList<String> _paymentType, ArrayList<String> _amount, TextView tv) {
        this.context = context;
        this._dates = _dates;
        this._paymentType = _paymentType;
        this._amount = _amount;
        this.tv = tv;
        builder = new AlertDialog.Builder(context);

        for (String num : _amount)
        {
            total += Float.parseFloat(num);
        }
        tv.setText(String.valueOf(total));
    }

    @Override
    public int getCount() {
        return _paymentType.size();
    }

    @Override
    public Object getItem(int i) {

        return _paymentType.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        // Start of Horizontal Linear Layout nested in Vertical Linear Layout
        LinearLayout li = new LinearLayout(context);
        li.setOrientation(LinearLayout.HORIZONTAL);
        li.setGravity(LEFT);

        final TextView txtDate = new TextView(context);
        String[] date = _dates.get(i).split("T");
        txtDate.setText(date[0]);
        txtDate.setGravity(LEFT);
        txtDate.setWidth(280);
        txtDate.setTypeface(txtDate.getTypeface(), Typeface.BOLD);

        final TextView txtPaymentType = new TextView(context);
        txtPaymentType.setText(_paymentType.get(i));
        txtPaymentType.setGravity(LEFT);
        txtPaymentType.setWidth(450);
        txtPaymentType.setTextColor(Color.parseColor("#000000"));
        txtPaymentType.setTypeface(txtPaymentType.getTypeface(), Typeface.BOLD);

        final TextView txtAmount = new TextView(context);
        txtAmount.setText(_amount.get(i));
        txtAmount.setGravity(LEFT);
        txtAmount.setTextColor(Color.parseColor("#000000"));

        li.addView(txtDate);
        li.addView(txtPaymentType);
        li.addView(txtAmount);

        return li;
    }
}
