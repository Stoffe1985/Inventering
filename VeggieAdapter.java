package sthlm.malmo.christofferwiregren.gogogreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

/**
 * VeggieAdapter refreshes the recycler view
 */

public class VeggieAdapter extends RecyclerView.Adapter<VeggieAdapter.MyViewHolder> {
    private List<Vegetable> mVegetableList;
    private EditText mEName, mEPrice;


    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mName, mQuantity, mPrice, mTot;
        private Button mBtnPlus, mBtnMinus, mBtnDelete;
        private ConstraintLayout mRecyclerView;

        MyViewHolder(View view) {
            super(view);
            mBtnPlus =  view.findViewById(R.id.btn_plus);
            mBtnMinus = view.findViewById(R.id.btn_minus);
            mName = view.findViewById(R.id.txt_food);
            mQuantity = view.findViewById(R.id.txt_amount);
            mBtnDelete = view.findViewById(R.id.btn_delete);
            mRecyclerView = view.findViewById(R.id.holder_veggie);
            mPrice = view.findViewById(R.id.txt_price);
            mTot = view.findViewById(R.id.txt_saldo);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.veggie_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @SuppressLint({"ResourceType", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final ServiceHelper serviceHelper = new ServiceHelper();


        holder.mRecyclerView.setOnClickListener(v -> {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
            alertDialog.setMessage("Produktdetaljer");
            alertDialog.setCancelable(true);
            final Context context = alertDialog.getContext();
            final LayoutInflater inflater = LayoutInflater.from(context);
            @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.detail_task, null, false);
            alertDialog.setView(view);
            mEName = view.findViewById(R.id.editnameDetail);
            mEPrice = view.findViewById(R.id.editPriceDetail);
            mEName.setText(mVegetableList.get(position).getName());
            mEPrice.setText(Integer.toString(mVegetableList.get(position).getPrice()));
            alertDialog.setPositiveButton(
                    R.string.save_edit_dial,
                    (dialog, id) -> {
                        if (mEName.getText().toString().equals("") || mEPrice.getText().toString().equals("")) {
                            Toast.makeText(view.getContext(), R.string.error_add_dial, Toast.LENGTH_SHORT).show();
                        } else {

                            if(Helper.searchInListIfNameExists(mEName.getText().toString(),mVegetableList)){
                                Toast.makeText(view.getContext(), R.string.item_exists, Toast.LENGTH_SHORT).show();
                                return;
                            }

                            mVegetableList.get(position).setName(mEName.getText().toString());
                            mVegetableList.get(position).setPrice(Integer.valueOf(mEPrice.getText().toString()));
                            serviceHelper.UpdateItems(mVegetableList.get(position));
                            Toast.makeText(view.getContext(), R.string.success_add_dial, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });

            alertDialog.setNegativeButton(
                    R.string.done_edit_dial,
                    (dialog, id) -> {
                        serviceHelper.deleteItem(mVegetableList.get(position));
                        Toast.makeText(view.getContext(), R.string.deleted_edit_task, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    });

            alertDialog.setNeutralButton(
                    R.string.cancel_add_dial,
                    (dialog, id) -> dialog.cancel());

            AlertDialog alert11 = alertDialog.create();
            alert11.show();
        });
        final Vegetable vegetable = mVegetableList.get(position);

        holder.mName.setText(vegetable.getName());
        holder.mPrice.setText(Integer.toString(vegetable.getPrice())+" kr");
        holder.mQuantity.setText(Integer.toString(vegetable.getQuantity())+" st");
        holder.mTot.setText(Integer.toString(vegetable.getTotal())+ " kr");
        holder.mBtnDelete.setOnClickListener(v -> {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
            builder.setMessage(R.string.delete_message)
                    .setTitle(R.string.delete_task).setIcon(R.drawable.eraser)
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes, (dialogInterface, i) -> serviceHelper.deleteItem(vegetable))

                    .setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel());
            android.app.AlertDialog alert = builder.create();
            alert.show();
        });
        holder.mBtnPlus.setOnClickListener(v -> {

            int tempsum = vegetable.getQuantity();
            tempsum ++;
            vegetable.setQuantity(tempsum);
            holder.mQuantity.setText(Integer.toString(vegetable.getQuantity()));
            serviceHelper.UpdateItems(vegetable);

        });

        holder.mBtnMinus.setOnClickListener(v -> {

            int tempsum = vegetable.getQuantity();
            if(tempsum>0) {
                tempsum--;
                vegetable.setQuantity(tempsum);
                holder.mQuantity.setText(Integer.toString(vegetable.getQuantity()));
                serviceHelper.UpdateItems(vegetable);

            }
        });
    }

    VeggieAdapter(List<Vegetable> vegetables) {
        this.mVegetableList = vegetables;
    }


    @Override
    public int getItemCount() {
        return mVegetableList.size();
    }
}

