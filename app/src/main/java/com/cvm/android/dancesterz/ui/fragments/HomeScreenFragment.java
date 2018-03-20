package com.cvm.android.dancesterz.ui.fragments;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cvm.android.dancesterz.R;
import com.cvm.android.dancesterz.dao.SignUpDao;
import com.cvm.android.dancesterz.dto.User;
import com.cvm.android.dancesterz.dto.UserResponse;
import com.cvm.android.dancesterz.ui.ProfileInCompletionActivity;
import com.cvm.android.dancesterz.ui.listeners.ParameterListener;
import com.cvm.android.dancesterz.utilities.AppConstants;
import com.cvm.android.dancesterz.utilities.PreferencesManager;
import com.cvm.android.dancesterz.utilities.URLs;

import java.util.List;

public class HomeScreenFragment extends Fragment {

    LinearLayout firstLinearLayout;
    LinearLayout secondLinearLayout;
    LinearLayout bannerLinearLayout;
    LinearLayout newsFeedLinearLayout;

    PreferencesManager preferenceManager;
    String username;
    String userid;
    String nickName;

    public HomeScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeScreenFragment newInstance(String param1, String param2) {
        HomeScreenFragment fragment = new HomeScreenFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        firstLinearLayout = view.findViewById(R.id.firstLinearLayout);
        secondLinearLayout = view.findViewById(R.id.secondLinearLayout);
        bannerLinearLayout = view.findViewById(R.id.banerLinearLayout);
        newsFeedLinearLayout = view.findViewById(R.id.newsFeedLinearLayout);
        preferenceManager = new PreferencesManager(getActivity());
        username = preferenceManager.read(AppConstants.KEY_USERNAME);
        userid = preferenceManager.read(AppConstants.KEY_USERID);
        nickName = preferenceManager.read(AppConstants.KEY_NICK_NAME);
        if (nickName != null && !nickName.equals("")) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Good Morning " + nickName);
        }

        new SignUpDao("", username, "", "", "", null, null, null,
                "", "", "", "", "", "", "", "", URLs.PROFILE, userid, new ParameterListener() {
            @Override
            public void OnTaskCompletedWithParameter(String votes) {
                UserResponse userResponse = SignUpDao.getUserResponse();
                if (userResponse != null) {
                    List<User> user = userResponse.getUser();
                    for (User userProfile : user) {
                        preferenceManager.store(AppConstants.KEY_PROFILE_PIC, userProfile.getThumbnail());
                        preferenceManager.store(AppConstants.KEY_NICK_NAME, userProfile.getNickName());

                        if (userProfile.getFirstName() == null || userProfile.getLastName() == null || userProfile.getCity() == null
                                || userProfile.getCountry() == null || userProfile.getDob() == null || userProfile.getEmail() == null
                                || userProfile.getGender() == null || userProfile.getPhoneNumber() == null || userProfile.getState() == null
                                || userProfile.getThumbnail() == null || userProfile.getNickName() == null) {
                            Intent intent = new Intent(getActivity(), ProfileInCompletionActivity.class);
//                            startActivity(intent);
                            startActivityForResult(intent, 0);
                        }
                    }
                }

//                nicknameTextView.setText(pManager.read(AppConstants.KEY_NICK_NAME));
//                Picasso.with(getApplicationContext()).load(pManager.read(AppConstants.KEY_PROFILE_PIC)).placeholder(R.drawable.t2)// Place holder image from drawable folder
//                        .error(R.drawable.t2).resize(110, 110).centerCrop()
//                        .into(profilePicCircularImageView);
            }
        });

        getFragmentManager().beginTransaction().replace(R.id.firstLinearLayout, MyVideosFragment.newInstance(URLs.URL_GETHOTCHALLENGEVIDEOS, "Hot Challenges")).commit();
        getFragmentManager().beginTransaction().replace(R.id.secondLinearLayout, MyVideosFragment.newInstance(URLs.URL_GETTRENDINGNOWVIDEOS, "Trending Now")).commit();
        getFragmentManager().beginTransaction().replace(R.id.newsFeedLinearLayout, NewsFeedsFragment.newInstance(URLs.URL_NEWSFEEDS, "News Feeds")).commit();
        getFragmentManager().beginTransaction().replace(R.id.banerLinearLayout, new BannerFragment()).addToBackStack(null).commit();


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == getActivity().RESULT_OK) {
                getFragmentManager().beginTransaction().replace(R.id.parentFrameLayout, new ProfileFragment()).addToBackStack("").commit();
            }
        }
    }
}
