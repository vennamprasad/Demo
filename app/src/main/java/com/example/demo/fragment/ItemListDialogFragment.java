package com.example.demo.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.demo.ImagePicker;
import com.example.demo.Utils;
import com.example.demo.database.DatabaseClient;
import com.example.demo.databinding.BottomSheetBinding;
import com.example.demo.tables.PropertyDetails;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.R;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.Objects;

public class ItemListDialogFragment extends BottomSheetDialogFragment {
    private BottomSheetBinding binding;
    private String ImageURL = "";

    public ItemListDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet, container, false);
        View view = binding.getRoot();
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    SaveTask st = new SaveTask();
                    st.execute();
                }
            }
        });
        binding.imgButUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Objects.requireNonNull(binding.inPropertyName.getText()).toString().equals("")) {
                    PickImageDialog.build(new PickSetup())
                            .setOnPickResult(new IPickResult() {
                                @Override
                                public void onPickResult(PickResult r) {
                                    binding.imgProperty.setImageBitmap(r.getBitmap());
                                    ImageURL = Utils.saveToInternalStorage(r.getBitmap(), binding.inPropertyName.getText().toString() + ".JPEG");
                                }
                            })
                            .setOnPickCancel(new IPickCancel() {
                                @Override
                                public void onCancelClick() {

                                }
                            }).show(getFragmentManager());
                } else {
                    binding.inPropertyName.setError("enter property name");
                }
            }
        });
        return view;
    }

    private boolean validate() {
        if (Objects.requireNonNull(binding.inPropertyName.getText()).toString().equals("")) {
            binding.inPropertyName.setError("enter property name");
            return false;
        } else if (binding.spBuildingType.getSelectedItemPosition() <= 0) {
            ((TextView) binding.spBuildingType.getSelectedView()).setError("select building type");
            return false;
        } else if (!Objects.requireNonNull(binding.inMobile.getText()).toString().matches(Utils.MobilePattern)) {
            binding.inMobile.setError("not a valid mobile number");
            return false;
        } else if (Objects.requireNonNull(binding.inAddress.getText()).toString().equals("")) {
            binding.inAddress.setError("enter property address");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @SuppressLint("StaticFieldLeak")
    class SaveTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            //creating a task
            PropertyDetails property_details = new PropertyDetails();
            try {
                property_details.setPropertyId(Utils.createPropertyId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            property_details.setPropertyName(Objects.requireNonNull(binding.inPropertyName.getText()).toString());
            property_details.setBuildingType(binding.spBuildingType.getSelectedItem().toString());
            property_details.setNoOfFloors(Objects.requireNonNull(binding.inNoOfFloors.getText()).toString());
            property_details.setNameOfSecurityGuard(Objects.requireNonNull(binding.inSecurityName.getText()).toString());
            property_details.setSecurityGuardMobileNumber(Objects.requireNonNull(binding.inMobile.getText()).toString());
            property_details.setAddress(Objects.requireNonNull(binding.inAddress.getText()).toString());
            property_details.setPropertyImage(ImageURL);
            //adding to database
            DatabaseClient.getInstance(getContext()).getAppDatabase().property_details_dao().insert(property_details);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
        }
    }

}
