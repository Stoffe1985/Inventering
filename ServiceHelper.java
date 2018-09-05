package sthlm.malmo.christofferwiregren.gogogreen;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Service helper class -> Update - Write - Delete items on Firebase.
 *
 */

public class ServiceHelper {
    private DatabaseReference mDatabaseRef;
    private Context mContext;

    ServiceHelper() {}

    ServiceHelper(Context context) {
        this.mContext = context;
    }


    /**
     * Write item to Firebase
     * @param vegetables
     */

    public void writeItems(Vegetable vegetables) {

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Food").child(vegetables.getID());
        mDatabaseRef.setValue(vegetables).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                Toast.makeText(mContext.getApplicationContext(), R.string.success_add_dial, Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(mContext.getApplicationContext(),R.string.failedconnect, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Update item on Firebase
     * @param vegetables
     */
    public void UpdateItems(Vegetable vegetables) {

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Food").child(vegetables.getID());

        Map<String, Object> postValues = new HashMap<String,Object>();
        postValues.put("id", vegetables.getID());
        postValues.put("name", vegetables.getName());
        postValues.put("price", vegetables.getPrice());
        postValues.put("quantity", vegetables.getQuantity());
        mDatabaseRef.updateChildren(postValues);
    }

    /**
     * Delete item om Firebase
     * @param vegetable
     */
    public void deleteItem(Vegetable vegetable){
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef.child("Food").child(vegetable.getID()).setValue(null);
        }

}
