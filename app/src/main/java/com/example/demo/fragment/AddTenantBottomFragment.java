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
import com.example.demo.databinding.TenantSheetBinding;
import com.example.demo.tables.TenantDetails;
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

public class AddTenantBottomFragment extends BottomSheetDialogFragment {
    private TenantSheetBinding binding;
    private String ImageURL = "";

    public AddTenantBottomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.tenant_sheet, container, false);
        View view = binding.getRoot();
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("TENANT_DETAILS")) {
            TenantDetails tenantDetails = (TenantDetails) Objects.requireNonNull(bundle).getSerializable("TENANT_DETAILS");
            binding.setTenant(tenantDetails);
            assert tenantDetails != null;
            ImageURL = new File(tenantDetails.getTenantImage()).getPath();
            Uri uri = Uri.fromFile(new File(tenantDetails.getTenantImage()));
            Picasso.get().load(uri).into(binding.imgTenant);
            selectSpinnerValue(binding.spProfileType, tenantDetails.getTenantProfileStatus());
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
                if (!Objects.requireNonNull(binding.inTenantName.getText()).toString().equals("")) {
                    PickImageDialog.build(new PickSetup())
                            .setOnPickResult(new IPickResult() {
                                @Override
                                public void onPickResult(PickResult r) {
                                    binding.imgTenant.setImageBitmap(r.getBitmap());
                                    ImageURL = DemoUtils.saveToInternalStorage(r.getBitmap(), binding.inTenantName.getText().toString() + ".JPEG");
                                }
                            })
                            .setOnPickCancel(new IPickCancel() {
                                @Override
                                public void onCancelClick() {

                                }
                            }).show(getFragmentManager());
                } else {
                    binding.inTenantName.setError("enter tenant name");
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
        if (Objects.requireNonNull(binding.inTenantName.getText()).toString().equals("")) {
            binding.inTenantName.setError("enter tenant name");
            return false;
        } else if (binding.spProfileType.getSelectedItemPosition() <= 0) {
            ((TextView) binding.spProfileType.getSelectedView()).setError("select profile status");
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
            TenantDetails tenantDetails = new TenantDetails();
            try {
                tenantDetails.setTenantId(DemoUtils.creRowId());
                tenantDetails.setTenantName(Objects.requireNonNull(binding.inTenantName.getText()).toString());
                tenantDetails.setTenantEmail(Objects.requireNonNull(binding.inTenantEmail.getText()).toString());
                tenantDetails.setTenantProfileStatus(binding.spProfileType.getSelectedItem().toString());
                tenantDetails.setNumberOfMembers(Objects.requireNonNull(binding.inNoOfFloors.getText()).toString());
                tenantDetails.setGender(String.valueOf(DemoUtils.getId(binding.rgGender)));
                tenantDetails.setTenantMobile(Objects.requireNonNull(binding.inMobile.getText()).toString());
                tenantDetails.setAddress(Objects.requireNonNull(binding.inAddress.getText()).toString());
                tenantDetails.setTenantImage(ImageURL);
                //adding to database
                if (DatabaseClient.getInstance(getContext()).getAppDatabase().tenant_details_dao().getCount(Objects.requireNonNull(binding.inMobile.getText()).toString()) >= 1)
                    DatabaseClient.getInstance(getContext()).getAppDatabase().tenant_details_dao().update(tenantDetails);
                else
                    DatabaseClient.getInstance(getContext()).getAppDatabase().tenant_details_dao().insert(tenantDetails);
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
