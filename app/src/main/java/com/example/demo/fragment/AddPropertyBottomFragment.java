package com.example.demo.fragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.DemoUtils;
import com.example.demo.R;
import com.example.demo.database.DatabaseClient;
import com.example.demo.databinding.PropertySheetBinding;
import com.example.demo.tables.PropertyDetails;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

public class AddPropertyBottomFragment extends BottomSheetDialogFragment {
    private PropertySheetBinding binding;
    private String ImageURL = "";

    public AddPropertyBottomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.property_sheet, container, false);
        View view = binding.getRoot();
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("PROPERTY_DETAILS")) {
            PropertyDetails propertyDetails = (PropertyDetails) Objects.requireNonNull(bundle).getSerializable("PROPERTY_DETAILS");
            binding.setProperty(propertyDetails);
            assert propertyDetails != null;
            ImageURL = new File(propertyDetails.getPropertyImage()).getPath();
            Uri uri = Uri.fromFile(new File(propertyDetails.getPropertyImage()));
            Picasso.with(getActivity()).load(uri).into(binding.imgProperty);
            selectSpinnerValue(binding.spBuildingType, propertyDetails.getBuildingType());
        }
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatePropertyDetails()) {
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
                                    ImageURL = DemoUtils.saveToInternalStorage(r.getBitmap(), binding.inPropertyName.getText().toString() + ".JPEG");
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

    private void selectSpinnerValue(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(myString)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private boolean validatePropertyDetails() {
        if (Objects.requireNonNull(binding.inPropertyName.getText()).toString().equals("")) {
            binding.inPropertyName.setError("enter property name");
            return false;
        } else if (binding.spBuildingType.getSelectedItemPosition() <= 0) {
            ((TextView) binding.spBuildingType.getSelectedView()).setError("select building type");
            return false;
        } else if (!Objects.requireNonNull(binding.inMobile.getText()).toString().matches(DemoUtils.MobilePattern)) {
            binding.inMobile.setError("not a valid mobile number");
            return false;
        } else if (Objects.requireNonNull(binding.inAddress.getText()).toString().equals("")) {
            binding.inAddress.setError("enter property_sheet address");
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
    private class SaveTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            PropertyDetails property_details = new PropertyDetails();
            try {
                property_details.setPropertyId(DemoUtils.creRowId());
                property_details.setPropertyName(Objects.requireNonNull(binding.inPropertyName.getText()).toString());
                property_details.setBuildingType(binding.spBuildingType.getSelectedItem().toString());
                property_details.setNoOfFloors(Objects.requireNonNull(binding.inNoOfFloors.getText()).toString());
                property_details.setNameOfSecurityGuard(Objects.requireNonNull(binding.inSecurityName.getText()).toString());
                property_details.setSecurityGuardMobileNumber(Objects.requireNonNull(binding.inMobile.getText()).toString());
                property_details.setAddress(Objects.requireNonNull(binding.inAddress.getText()).toString());
                property_details.setPropertyImage(ImageURL);
                //adding to database
                if (DatabaseClient.getInstance(getContext()).getAppDatabase().property_details_dao().getCount(Objects.requireNonNull(binding.inMobile.getText()).toString()) >= 1)
                    DatabaseClient.getInstance(getContext()).getAppDatabase().property_details_dao().update(property_details);
                else
                    DatabaseClient.getInstance(getContext()).getAppDatabase().property_details_dao().insert(property_details);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return objects;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            dismiss();
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
        }
    }
}
