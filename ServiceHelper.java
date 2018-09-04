package sthlm.malmo.christofferwiregren.gogogreen;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServiceHelper {
    private DatabaseReference mDatabaseRef;
    private Context context;


    public ServiceHelper() {}

    public ServiceHelper(Context context) {
        this.context = context;
    }



    public void writeItems(Vegetable vegetables) {

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Food").child(vegetables.getID());
        mDatabaseRef.setValue(vegetables).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(context.getApplicationContext(), R.string.success_add_dial, Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(context.getApplicationContext(),R.string.failedconnect, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void UpdateItems(Vegetable vegetables) {

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Food").child(vegetables.getID());

        Map<String, Object> postValues = new HashMap<String,Object>();
        postValues.put("id", vegetables.getID());
        postValues.put("name", vegetables.getName());
        postValues.put("price", vegetables.getPrice());
        postValues.put("quantity", vegetables.getQuantity());
        mDatabaseRef.updateChildren(postValues);
    }

    public void deleteItem(Vegetable vegetable){
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef.child("Food").child(vegetable.getID()).setValue(null);
        }
}
