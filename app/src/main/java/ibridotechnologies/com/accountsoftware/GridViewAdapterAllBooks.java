package ibridotechnologies.com.accountsoftware;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ibridotechnologies.com.accountsoftware.Model.AllBookDetails;

import static android.view.Gravity.CENTER;
import static android.view.Gravity.LEFT;
import static android.view.Gravity.START;

/**
 * Created by sushil on 13/09/18.
 */

public class GridViewAdapterAllBooks extends BaseAdapter {
    //Context
    private Context context;
    AlertDialog.Builder builder;

    float total=0;
    AllBookDetails allBookDetails = new AllBookDetails();


    //Array List that would contain the urls and the titles for the images
    private ArrayList<String> _bookTitle;
    private ArrayList<String> _pagesNo;
    private ArrayList<String> _amount;
    private TextView tv;

    public GridViewAdapterAllBooks(Context context, ArrayList<String> _bookTitle, ArrayList<String> _pagesNo, ArrayList<String> _amount, TextView tv) {
        this.context = context;
        this._bookTitle = _bookTitle;
        this._pagesNo = _pagesNo;
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
        return _pagesNo.size();
    }

    @Override
    public Object getItem(int i) {

        return _pagesNo.get(i);
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

        final TextView txtBookTitle = new TextView(context);
        txtBookTitle.setText(_bookTitle.get(i));
        txtBookTitle.setGravity(LEFT);
        txtBookTitle.setWidth(350);
        txtBookTitle.setPaintFlags(txtBookTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtBookTitle.setTypeface(txtBookTitle.getTypeface(), Typeface.BOLD);

        final TextView txtSno = new TextView(context);
        txtSno.setText(String.valueOf(i + 1 + " ."));
        txtSno.setGravity(LEFT);
        txtSno.setWidth(110);
        txtSno.setTextColor(Color.parseColor("#000000"));
        txtSno.setTypeface(txtSno.getTypeface(), Typeface.BOLD);

        final TextView txtPagesNo = new TextView(context);
        txtPagesNo.setText(_pagesNo.get(i));
        txtPagesNo.setGravity(LEFT);
        txtPagesNo.setWidth(250);
        txtPagesNo.setTextColor(Color.parseColor("#000000"));

        final TextView txtAmount = new TextView(context);
        txtAmount.setText(_amount.get(i));
        txtAmount.setGravity(LEFT);
        txtAmount.setTextColor(Color.parseColor("#000000"));

        li.addView(txtSno);
        li.addView(txtBookTitle);
        li.addView(txtPagesNo);
        li.addView(txtAmount);

        return li;
    }
}
