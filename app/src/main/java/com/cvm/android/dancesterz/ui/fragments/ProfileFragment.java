package com.cvm.android.dancesterz.ui.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dao.ProfilePicUploadDao;
import com.cvm.android.dancesterz.dao.SignUpDao;
import com.cvm.android.dancesterz.dto.User;
import com.cvm.android.dancesterz.dto.UserResponse;
import com.cvm.android.dancesterz.ui.listeners.ParameterListener;
import com.cvm.android.dancesterz.ui.listeners.ProfilePicResListener;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;
import com.cvm.android.dancesterz.utilities.URLs;
import com.robertlevonyan.views.chip.Chip;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class ProfileFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //Initializing the UI components
    ImageView userProfileImageView = null;
    EditText userNickNameEditText = null;
    ImageView updateUserNickNameImageView = null;
    TextView userVotesTextView = null;
    TextView userViewsTextView = null;
    EditText userFirstNameEditText = null;
    EditText userSecondNameEditText = null;
    DatePicker userDobDatePicker = null;
    RadioGroup genderRadioGroup = null;
    RadioButton maleRadioButton = null;
    RadioButton femaleRadioButton = null;
    RadioButton otherRadioButton = null;
    EditText userContactNumberEditText = null;
    EditText userEmailEditText = null;
    EditText userCountryEditText = null;
    EditText userStateEditText = null;
    EditText userRegionEditText = null;
    Button userFavStyleButton = null;
    Button userProfileUpdateButton = null;
    GridLayout closedChipsGridLayout = null;
    PreferencesManager preferenceManager;
    String username;
    String userid;

    public static List<String> favouriteStyleDtos;
    public static List<String> userDanceStyleWholeList;
    View view = null;
    public static List<String> favouriteDanceSelected;

    String gender = "";
    String date = "";
    int repeat = 0;

    ProgressDialog mProgressDialog;

    final int PERMISSION_REQUEST_CODE = 200;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage("Please Wait...");

        preferenceManager = new PreferencesManager(getActivity());
        username = preferenceManager.read(AppConstants.KEY_USERNAME);
        userid = preferenceManager.read(AppConstants.KEY_USERID);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Profile");

        new SignUpDao("", username, "", "", "", null, null, null,
                "", "", "", "", "", "", "", "", URLs.PROFILE, userid, new ParameterListener() {
            @Override
            public void OnTaskCompletedWithParameter(String votes) {
                mProgressDialog.dismiss();
                UserResponse userResponse = SignUpDao.getUserResponse();
                setUpUi(userResponse);
            }
        });

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }

    public void setUpUi(UserResponse userResponse) {

        //
//        professionalLevelPickerUi = view.findViewById(R.id.professionalLevelPickerUi);
        userProfileImageView = view.findViewById(R.id.profile_image);
        userNickNameEditText = view.findViewById(R.id.userNickNameEditText);
        updateUserNickNameImageView = view.findViewById(R.id.updateUserNickNameImageView);
        userVotesTextView = view.findViewById(R.id.userVotesTextView);
        userViewsTextView = view.findViewById(R.id.userViewsTextView);
        userFirstNameEditText = view.findViewById(R.id.userFirstNameEditText);
        userSecondNameEditText = view.findViewById(R.id.userSecondNameEditText);
        userDobDatePicker = view.findViewById(R.id.userDobDatePicker);
        genderRadioGroup = view.findViewById(R.id.genderRadioGroup);
        maleRadioButton = view.findViewById(R.id.userMaleRadioButton);
        femaleRadioButton = view.findViewById(R.id.userFemaleRadioButton);
        otherRadioButton = view.findViewById(R.id.userOtherRadioButton);
        userContactNumberEditText = view.findViewById(R.id.userContactNumberEditText);
        userEmailEditText = view.findViewById(R.id.userEmailAddressEditText);
        userCountryEditText = view.findViewById(R.id.userCountryEditText);
        userStateEditText = view.findViewById(R.id.userStateEditText);
        userRegionEditText = view.findViewById(R.id.userRegionEditText);
        userFavStyleButton = view.findViewById(R.id.userFavStyleSelectButton);
        userProfileUpdateButton = view.findViewById(R.id.userUpdateButton);
        closedChipsGridLayout = view.findViewById(R.id.closedChipsGridLayout);
        updateUserNickNameImageView.setOnClickListener(this);
        userFavStyleButton.setOnClickListener(this);
        userProfileUpdateButton.setOnClickListener(this);
        genderRadioGroup.setOnCheckedChangeListener(this);
        userProfileImageView.setOnClickListener(this);


        if (userResponse != null) {
            List<User> user = userResponse.getUser();
            for (User userProfile : user) {
//                professionalLevelList = userProfile.getProfessionalLevel();
//                favouriteStyleDtos = userProfile.getUserDanceStyle();

//                if (professionalLevelList != null) {
//                    professionalLevelPickerUi.setData(professionalLevelList);
//                }

                if (userProfile.getThumbnail() != null) {
                    Picasso.with(getActivity()).load(userProfile.getThumbnail()).placeholder(R.drawable.t2)// Place holder image from drawable folder
                            .error(R.drawable.t2).resize(110, 110).centerCrop()
                            .into(userProfileImageView);
                }
                userNickNameEditText.setText(userProfile.getNickName());
                userFirstNameEditText.setText(userProfile.getFirstName());
                userSecondNameEditText.setText(userProfile.getLastName());
                if (userProfile.getGender() != null) {
                    if (userProfile.getGender().equalsIgnoreCase(AppConstants.MALE)) {
                        maleRadioButton.setChecked(true);
                        gender = AppConstants.MALE;
                    } else if (userProfile.getGender().equalsIgnoreCase(AppConstants.FEMALE)) {
                        femaleRadioButton.setChecked(true);
                        gender = AppConstants.FEMALE;
                    } else if (userProfile.getGender().equalsIgnoreCase(AppConstants.OTHER)) {
                        otherRadioButton.setChecked(true);
                        gender = AppConstants.OTHER;
                    }
                }
                userContactNumberEditText.setText(userProfile.getPhoneNumber());
                userEmailEditText.setText(userProfile.getEmail());
                userCountryEditText.setText(userProfile.getCountry());
                userStateEditText.setText(userProfile.getState());
                userRegionEditText.setText(userProfile.getCity());
                String dob = userProfile.getDob();
                if (dob != null) {
                    String[] datesplit = dob.split("-");
                    int day = Integer.parseInt(datesplit[0]);
                    int month = (Integer.parseInt(datesplit[1])) - 1;
                    int year = Integer.parseInt(datesplit[2]);
                    userDobDatePicker.updateDate(year, month, day);
                }
//                if (favouriteStyleDtos == null) {
//                favouriteStyleDtos = userProfile.getUserDanceStyle();
//                if (favouriteStyleDtos != null) {
//                    addClosedChips();
//                }
                if (favouriteDanceSelected == null) {
                    favouriteDanceSelected = userProfile.getUserDanceStyle();
                    if (favouriteDanceSelected != null) {
                        addClosedChips(favouriteDanceSelected);
                        repeat = repeat + 1;
                    }
                } else {
                    addClosedChips(favouriteDanceSelected);
                    repeat = repeat + 1;
                }
//                }
                if (userDanceStyleWholeList == null) {
                    userDanceStyleWholeList = userProfile.getUserDanceStyleWholeList();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == updateUserNickNameImageView.getId()) {

        } else if (view.getId() == userFavStyleButton.getId()) {
            getFragmentManager().beginTransaction().replace(R.id.parentFrameLayout, new FavouriteDanceStyleFragment()).addToBackStack("Dance").commit();
        } else if (view.getId() == userProfileUpdateButton.getId()) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage("Please Wait...");
            mProgressDialog.show();
            int year = userDobDatePicker.getYear();
            int month = userDobDatePicker.getMonth() + 1;
            int dayOfMonth = userDobDatePicker.getDayOfMonth();
            if (dayOfMonth < 10) {
                date = "0" + dayOfMonth + "-" + month + "-" + year;
            } else {
                date = dayOfMonth + "-" + month + "-" + year;
            }

            String nickName = userNickNameEditText.getText().toString();
            String email = userEmailEditText.getText().toString();
            String firstName = userFirstNameEditText.getText().toString();
            String secondName = userSecondNameEditText.getText().toString();
            String contactNumber = userContactNumberEditText.getText().toString();
            String country = userCountryEditText.getText().toString();
            String state = userStateEditText.getText().toString();
            String region = userRegionEditText.getText().toString();
            new SignUpDao(nickName, username, "", "", email, null,
                    favouriteDanceSelected, userDanceStyleWholeList, firstName, secondName, date, gender,
                    contactNumber, country, state, region,
                    URLs.UPDATE, userid, new ParameterListener() {
                @Override
                public void OnTaskCompletedWithParameter(String votes) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getActivity(), "Update Completed", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (userProfileImageView.getId() == view.getId()) {
            if (checkPermission()) {
                showPictureDialog();
            } else {
                requestPermission();
            }
        }
    }

    //Permission Checking
    private boolean checkPermission() {
        int writeExternal = ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE);
        int readExternal = ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE);
        int camera = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        return writeExternal == PackageManager.PERMISSION_GRANTED && readExternal == PackageManager.PERMISSION_GRANTED && camera == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean writeAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean readAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (writeAccepted && readAccepted && cameraAccepted)
                        Toast.makeText(getActivity(), "Permission Granted, Now you can access location data and camera.", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(getActivity(), "Permission Denied, You cannot access location data and camera.", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE) && shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)
                                    && shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, Manifest.permission.CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory + File.separator + "profile.jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    void addClosedChips(List<String> favouriteDanceSelected) {
//        closedChipsGridLayout.removeAllViews();
        for (String danceStyle : favouriteDanceSelected) {
            Chip chip = new Chip(getActivity());
            chip.setChipText(danceStyle);
            chip.setClosable(true);
            chip.setCloseColor(R.color.darkgray);
            chip.setCornerRadius(50);
            if (repeat > 0) {
                closedChipsGridLayout.removeAllViews();
                repeat = 0;
            }
            closedChipsGridLayout.setColumnCount(3);
            closedChipsGridLayout.addView(chip);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path = "";
        if (resultCode == 0) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    path = saveImage(bitmap);
                    Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
                    userProfileImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            userProfileImageView.setImageBitmap(thumbnail);
            path = saveImage(thumbnail);
        }
        if (path != null && !path.equals("")) {
            try {
//                new ProfilePicUploadDao(new OnTaskCompleted() {
//                    @Override
//                    public void onTaskCompleted() {
//                    }
//
//                    @Override
//                    public void onTaskCompleted(boolean flag) {
//                        if (flag) {
//                            Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getContext(), "Something went wrong... Please try again later...", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }, getContext());
                new ProfilePicUploadDao(new ProfilePicResListener() {
                    @Override
                    public void profilePicUploaded(boolean flag) {
                        if (flag) {
                            Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Something went wrong... Please try again later...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, getActivity());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == 0) {
            for (String danceStyle : favouriteDanceSelected) {
                Log.e("Selected List", danceStyle);
                Chip chip = new Chip(getActivity());
                chip.setChipText(danceStyle);
                chip.setClosable(true);
                chip.setCloseColor(R.color.lightgray);
                chip.setCornerRadius(50);
                if (repeat > 0) {
                    closedChipsGridLayout.removeAllViews();
                    repeat = 0;
                }
                closedChipsGridLayout.setColumnCount(3);
                closedChipsGridLayout.addView(chip);
//                favouriteStyleDtos.add(danceStyle);
            }
            repeat = repeat + 1;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        RadioButton genderRadioButton = radioGroup.findViewById(checkedId);
        if (genderRadioButton.getText().equals(AppConstants.MALE_TEXT)) {
            gender = AppConstants.MALE;
        } else if (genderRadioButton.getText().equals(AppConstants.FEMALE_TEXT)) {
            gender = AppConstants.FEMALE;
        } else if (genderRadioButton.getText().equals(AppConstants.OTHER_TEXT)) {
            gender = AppConstants.OTHER;
        }
    }


//    private Badge addBadgeAt(int position, int number) {
    // add badge
//        return new QBadgeView(this)
//                .setBadgeNumber(number)
//                .setGravityOffset(12, 2, true)
//                .bindTarget(bind.bnve.getBottomNavigationItemView(position))
//                .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
//                    @Override
//                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
//                        if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState)
//                            Toast.makeText(BadgeViewActivity.this, R.string.tips_badge_removed, Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
}
