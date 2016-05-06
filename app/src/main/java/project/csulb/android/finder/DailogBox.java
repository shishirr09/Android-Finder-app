package project.csulb.android.finder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Shishir on 5/5/2016.
 */
public class DailogBox extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Error");
        builder.setCancelable(true);
        builder.setMessage("No location available for given address").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Select some other location", Toast.LENGTH_SHORT).show();
            }
        });
        return builder.create();
        //return super.onCreateDialog(savedInstanceState);
    }
}

