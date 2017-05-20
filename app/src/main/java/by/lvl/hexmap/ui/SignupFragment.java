package by.lvl.hexmap.ui;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;

import by.lvl.hexmap.R;
import by.lvl.hexmap.api.Api;
import by.lvl.hexmap.api.events.PostSignupEvent;
import by.lvl.hexmap.models.User.User;
import by.lvl.hexmap.models.User.repo.PrefUserRepository;
import by.lvl.hexmap.tools.PhoneHelper;
import by.lvl.hexmap.ui.base.BaseEventBusFragment;


public class SignupFragment extends BaseEventBusFragment {

    View llDistance, llTime, btnSignup, btnMap;
    TextInputEditText etName;
    TextView tvDistance, tvTime;
    SeekBar sbDistance, sbTime;


    public static SignupFragment newInstance(Bundle args) {
        SignupFragment fragment = new SignupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_singup, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        llDistance =  view.findViewById(R.id.distance_layout);
        llTime =  view.findViewById(R.id.time_layout);
        btnSignup = view.findViewById(R.id.btn_signup);
        btnMap = view.findViewById(R.id.btn_map);
        etName = (TextInputEditText) view.findViewById(R.id.et_username);
        tvDistance = (TextView) view.findViewById(R.id.tv_distance);
        tvTime = (TextView) view.findViewById(R.id.tv_time);
        sbDistance = (SeekBar) view.findViewById(R.id.sb_distance);
        sbTime = (SeekBar) view.findViewById(R.id.sb_time);
        initView();
    }


    private void initView() {

        User user = new PrefUserRepository().get();

        if (TextUtils.isEmpty(user.getId())) {

            etName.setEnabled(true);
            etName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    btnSignup.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE);
                }

                @Override
                public void afterTextChanged(Editable s) { }
            });

            btnSignup.setVisibility(TextUtils.isEmpty(etName.getText()) ? View.GONE : View.VISIBLE);
            btnSignup.setOnClickListener(v -> {
                PhoneHelper.hideSoftKeyboard(btnSignup);
                showProgress();
                Api.getInstance().signup(new User(etName.getText().toString()));
            });

            llDistance.setVisibility(View.GONE);
            llTime.setVisibility(View.GONE);
            btnMap.setVisibility(View.GONE);
        }
        else {
            etName.setText(user.getName());
            etName.setEnabled(false);
            btnSignup.setVisibility(View.GONE);
            llDistance.setVisibility(View.VISIBLE);
            llTime.setVisibility(View.VISIBLE);
            btnMap.setVisibility(View.VISIBLE);
            tvDistance.setText(String.valueOf(sbDistance.getProgress()) + " м");
            tvTime.setText(String.valueOf(sbTime.getProgress()) + " мин");

            sbDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    tvDistance.setText(String.valueOf(progress) + " м");
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            });

            sbTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    tvTime.setText(String.valueOf(progress) + " мин");
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            });

            btnMap.setOnClickListener(v -> Navigator.toMap(getActivity(), sbDistance.getProgress(), sbTime.getProgress() * 60));
        }
    }


    @Subscribe
    public void onSignupSucc(PostSignupEvent event) {
        new PrefUserRepository().put(event.getData());
        hideProgress();
        initView();
    }
}

