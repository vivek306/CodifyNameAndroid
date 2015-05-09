package com.example.vivek.codifyname;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private String radioValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize components
        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        // All the buttons
        final Button button = (Button) findViewById(R.id.codifyName);
        final Button encodeButton = (Button) findViewById(R.id.encodeButton);
        encodeButton.setTypeface(font);
        final Button decodeButton = (Button) findViewById(R.id.decodeButton);
        decodeButton.setTypeface(font);
        final Button copyButton = (Button) findViewById(R.id.copyButton);
        final Button pasteButton = (Button) findViewById(R.id.pasteButton);
        // All the editTexts
        final EditText editTextName = (EditText) findViewById(R.id.editTextName);
        editTextName.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        final EditText editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        // All the hint textviewa
        final TextView encodeTextView = (TextView) findViewById(R.id.encodeText);
        final TextView decodeTextView = (TextView) findViewById(R.id.decodeText);
        final TextView modeTextView = (TextView) findViewById(R.id.modeText);
        final TextView nameTextView = (TextView) findViewById(R.id.nameText);
        final TextView informationTextView = (TextView) findViewById(R.id.messageText);

        // Events
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                try {
                    String radio = radioValue;
                    String input = editTextMessage.getText().toString();
                    String name = editTextName.getText().toString().toLowerCase() + editTextName.getText().toString().toUpperCase();
                    String[] nameList = name.replaceAll(" ", "").split("");
                    if (radio != null && !radio.isEmpty()) {
                        // remove duplicates
                        Set<String> temp = new HashSet<String>(Arrays.asList(nameList));
                        nameList = temp.toArray(new String[temp.size()]);
                        if (nameList.length > 5) {
                            if (input != null && !input.isEmpty()) {
                                CodifyNameJava cfj = new CodifyNameJava();
                                if (radio == "encode")
                                    editTextMessage.setText(cfj.CodifyName(input, name, true));
                                else if (radio == "decode")
                                    editTextMessage.setText(cfj.CodifyName(input, name, false));
                                else
                                    editTextMessage.setText(editTextMessage.getText());
                            } else {
                                editTextMessage.setError("Please enter the message");
                            }
                        } else {
                            editTextName.setError("Your name should have atleast 5 distinct characters");
                        }
                    } else {
                        CharSequence text = "Please select if you want to Encrypt or Decrypt!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                        toast.show();
                    }
                } catch (Exception ex) {
                    editTextMessage.setError("Not valid message");
                }
            }
        });
        encodeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OnEncreyptionModeSelect(encodeButton, decodeButton, encodeTextView, decodeTextView);
                button.setText("Encrypt");
                informationTextView.setText("Information you want to encode");
                radioValue = "encode";
            }
        });
        decodeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OnEncreyptionModeSelect(decodeButton, encodeButton, decodeTextView, encodeTextView);
                button.setText("Decrypt");
                informationTextView.setText("Information you want to decode");
                radioValue = "decode";
            }
        });
        editTextMessage.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
                for (int i = s.length(); i > 0; i--) {
                    if (s.subSequence(i - 1, i).toString().equals("\n"))
                        s.replace(i - 1, i, "");
                }
                String myTextString = s.toString();
            }
        });

        // Custom Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("CodifyName()");
    }

    // Radio Button effect
    public void OnEncreyptionModeSelect(Button darkButton, Button lightButton, TextView darkText, TextView lightText) {
        // Dark style
        GradientDrawable darkBackground = (GradientDrawable) darkButton.getBackground();
        darkBackground.setColor(Color.BLACK);
        darkButton.setTextColor(Color.WHITE);
        darkText.setTypeface(null, Typeface.BOLD);
        // Light stylr
        GradientDrawable lightBackground = (GradientDrawable) lightButton.getBackground();
        lightBackground.setColor(Color.WHITE);
        lightButton.setTextColor(Color.BLACK);
        lightText.setTypeface(null, Typeface.NORMAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


}
