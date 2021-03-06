package sthlm.malmo.christofferwiregren.gogogreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {
    private List<Vegetable> mVeggieArrayList = new ArrayList<>();
    private EditText mName, mPrice;
    private Button mButtonAdd;
    private String mUUID;
    private VeggieAdapter mAdapter;
    private ServiceHelper mServiceHelper;
    private EditText msearchField;
    private List<Vegetable> mTempListVeg, mTempListVegSecond;
    private int mSortValue;
    private TextView txtTotalAmount, txtTotalPrice;
    private Spinner mSortSpinner;
    private RecyclerView mRecyclerView;
    private int mtotalSum, mtotalamount;


    public StartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setupListViewListener();
        setUpEditTextSearch();
        setUpSpinner();
        setUpRecyclerView();
        setUpBtnAddAction();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(View view) {
        mTempListVeg = new ArrayList<>();
        mTempListVegSecond = new ArrayList<>();
        msearchField = view.findViewById(R.id.txt_search);
        mSortSpinner = view.findViewById(R.id.spinner1);
        mServiceHelper = new ServiceHelper(getContext());
        mRecyclerView = view.findViewById(R.id.veggie_list);
        mName = view.findViewById(R.id.addTitle);
        mPrice = view.findViewById(R.id.addContent);
        mButtonAdd = view.findViewById(R.id.btn_add);
        txtTotalAmount = view.findViewById(R.id.total_amout);
        txtTotalPrice = view.findViewById(R.id.total_sum);
        mtotalamount = 0;
        mtotalSum = 0;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpBtnAddAction() {


        mButtonAdd.setOnTouchListener((v, event) -> {


            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mButtonAdd.setAlpha(0.5f);

            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                mButtonAdd.setAlpha(1);

                AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity());
                adBuilder.setMessage(R.string.title_add_dial);
                final Context context = adBuilder.getContext();
                final LayoutInflater inflater = LayoutInflater.from(context);
                @SuppressLint("InflateParams") final View view1 = inflater.inflate(R.layout.add_task, null, false);
                adBuilder.setView(view1);
                adBuilder.setPositiveButton(
                        R.string.save_add_dial,
                        (dialog, id) -> {

                            if(!CheckNetwork.isNetworkConnected(getContext())){
                                Toast.makeText(getActivity(), R.string.network, Toast.LENGTH_SHORT).show();
                                return;
                            }
                            mName = view1.findViewById(R.id.addTitle);
                            mPrice = view1.findViewById(R.id.addContent);

                            if (mName.getText().toString().equals("") || mPrice.getText().toString().equals("")) {
                                Toast.makeText(getContext(), R.string.error_add_dial, Toast.LENGTH_SHORT).show();
                            } else {

                                if (Helper.searchInListIfNameExists(mName.getText().toString(), mVeggieArrayList)) {
                                    Toast.makeText(getContext(), R.string.item_exists, Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (Helper.isNumeric(String.valueOf(mPrice.getText()))) {
                                    Vegetable vegetable = new Vegetable();
                                    vegetable.setName(String.valueOf(mName.getText()));
                                    vegetable.setPrice(Integer.parseInt(String.valueOf(mPrice.getText())));
                                    try {
                                        mUUID = Helper.createTransactionID();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    vegetable.setID(mUUID);
                                    vegetable.setQuantity(1);
                                    mVeggieArrayList.add(vegetable);
                                    mServiceHelper.writeItems(vegetable);
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(getContext(), R.string.only_number, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                adBuilder.setNegativeButton(
                        R.string.cancel_add_dial,
                        (dialog, id) -> dialog.dismiss());

                AlertDialog alert11 = adBuilder.create();
                alert11.show();
            }
            return false;
        });
    }

    private void setUpRecyclerView() {
        mVeggieArrayList = new ArrayList<>();
        mVeggieArrayList.clear();
        mAdapter = new VeggieAdapter(mVeggieArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setUpSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()), R.array.numers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSortSpinner.setAdapter(adapter);
        mSortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSortValue = position;
                Helper.sortData(mSortValue, mVeggieArrayList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void setUpEditTextSearch() {

        msearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                replaceOldListWithNewList(mTempListVeg, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void replaceOldListWithNewList(List<Vegetable> vegetables, String word) {

        mTempListVegSecond.addAll(vegetables);
        mVeggieArrayList.clear();
        if (word.contains("x1x")) {
            mVeggieArrayList.addAll(Helper.searchInListByID(word, mTempListVegSecond));

        } else {
            mVeggieArrayList.addAll(Helper.searchInListByName(word, mTempListVegSecond));
        }
        mAdapter.notifyDataSetChanged();
        mTempListVegSecond.clear();
    }

    private void setupListViewListener() {

        DatabaseReference mMessageReference = FirebaseDatabase.getInstance().getReference().child("Food");
        mMessageReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Vegetable vegetable;
                mVeggieArrayList.clear();
                mTempListVeg.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    vegetable = ds.getValue(Vegetable.class);
                    assert vegetable != null;
                    mtotalSum += vegetable.getTotal();
                    mtotalamount += vegetable.getQuantity();
                    mVeggieArrayList.add(vegetable);
                    mTempListVeg.add(vegetable);


                }

                txtTotalAmount.setText(String.valueOf(mtotalamount)+ " st");
                txtTotalPrice.setText(String.valueOf(mtotalSum)+ " kr");


                Helper.sortData(mSortValue, mVeggieArrayList);
                mAdapter.notifyDataSetChanged();
                mtotalSum = 0;
                mtotalamount = 0;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
