package uz.sanjar.note.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import uz.sanjar.note.R;
import uz.sanjar.note.core.cache.SetUpHelper;
import uz.sanjar.note.databinding.ActivityPasswordBinding;

public class Password extends AppCompatActivity {
    private ActivityPasswordBinding binding;

    private ArrayList<String> numbers_list = new ArrayList<>();
    private String passCode = "";
    private String num_01, num_02, num_03, num_04;

    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        SetUpHelper.getHelper().setBoard(true);

        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        binding.infoNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetFragment.show(getSupportFragmentManager(), "BottomSheetDialog");
            }
        });
        binding.confirmOrPassword.setText("PASSCODE");

        setListeners();
    }

    private void setListeners() {
        binding.btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numbers_list.add("1");
                passNumber(numbers_list);
            }
        });
        binding.btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numbers_list.add("2");
                passNumber(numbers_list);
            }
        });
        binding.btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numbers_list.add("3");
                passNumber(numbers_list);
            }
        });
        binding.btn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numbers_list.add("4");
                passNumber(numbers_list);
            }
        });
        binding.btn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numbers_list.add("5");
                passNumber(numbers_list);
            }
        });
        binding.btn06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numbers_list.add("6");
                passNumber(numbers_list);
            }
        });
        binding.btn07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numbers_list.add("7");
                passNumber(numbers_list);
            }
        });
        binding.btn08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numbers_list.add("8");
                passNumber(numbers_list);
            }
        });
        binding.btn09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numbers_list.add("9");
                passNumber(numbers_list);
            }
        });
        binding.btn00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numbers_list.add("0");
                passNumber(numbers_list);
            }
        });
        binding.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numbers_list.clear();
                passNumber(numbers_list);
            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void passNumber(ArrayList<String> numbers_list) {
        if (numbers_list.size() == 0) {
            binding.view01.setBackgroundResource(R.drawable.password_grey_oval);
            binding.view02.setBackgroundResource(R.drawable.password_grey_oval);
            binding.view03.setBackgroundResource(R.drawable.password_grey_oval);
            binding.view04.setBackgroundResource(R.drawable.password_grey_oval);
        } else {
            switch (numbers_list.size()) {
                case 1:
                    num_01 = numbers_list.get(0);
                    binding.view01.setBackgroundResource(R.drawable.password_blue_oval);
                    break;
                case 2:
                    num_02 = numbers_list.get(1);
                    binding.view02.setBackgroundResource(R.drawable.password_blue_oval);
                    break;
                case 3:
                    num_03 = numbers_list.get(2);
                    binding.view03.setBackgroundResource(R.drawable.password_blue_oval);
                    break;
                case 4:
                    num_04 = numbers_list.get(3);
                    binding.view04.setBackgroundResource(R.drawable.password_blue_oval);
                    passCode = num_01 + num_02 + num_03 + num_04;
                    if (getPassCode().length() == 0) {
                        Toast.makeText(this, "CONFIRM YOUR PASSWORD", Toast.LENGTH_SHORT).show();
                        binding.confirmOrPassword.setText("CONFIRM");
                        savePassCode(passCode);
                        numbers_list.clear();
                        passNumber(numbers_list);
                    } else {
                        matchPassCode();
                    }
                    break;
                // TODO: 4/7/2022 shunchaki boshidan boshleylik
            }
        }
    }

    private void matchPassCode() {
        if (getPassCode().equals(passCode)) {
            Intent intent = new Intent(this, NotesApp.class);
            startActivity(intent);
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            finish();
        } else {

            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            numbers_list.clear();
            passNumber(numbers_list);
            Animation animationUtils = AnimationUtils.loadAnimation(this, R.anim.shake);
            binding.passcodeMain.startAnimation(animationUtils);
            vibrator.vibrate(90);
        }
    }

    private SharedPreferences.Editor savePassCode(String passCode) {
        SharedPreferences preferences = getSharedPreferences("passcode_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("passcode", passCode);
        editor.apply();
        return editor;
    }

    private String getPassCode() {
        SharedPreferences preferences = getSharedPreferences("passcode_pref", Context.MODE_PRIVATE);
        return preferences.getString("passcode", "");
    }
}