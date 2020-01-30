package com.uo226321.joel.gymnastics.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.shawnlin.numberpicker.NumberPicker;
import com.uo226321.joel.gymnastics.R;
import com.uo226321.joel.gymnastics.model.Usuario;


public class FormActivity extends AppCompatActivity {
    private EditText nombre;
    private EditText edad;
    private Button btSiguiente;
    private NumberPicker altura;
    private NumberPicker peso;
    private Switch personalizar;
    private TextView descripcion;
    private SharedPreferences prefs;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        prefs =
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        altura = (NumberPicker) findViewById(R.id.number_picker);
        peso = (NumberPicker) findViewById(R.id.number_picker2);
        nombre = (EditText) findViewById(R.id.nombre);
        edad = (EditText) findViewById(R.id.edad);
        btSiguiente = (Button) findViewById(R.id.btContinuar);
        btSiguiente = (Button) findViewById(R.id.btContinuar);
        personalizar = (Switch) findViewById(R.id.switchPersonalizar);
        descripcion = (TextView) findViewById(R.id.txDescription);

        Bundle bundle = getIntent().getExtras();
        usuario = (Usuario) bundle.get("usuario");


        String nombreS = prefs.getString("Nombre", null);
        String edadS = prefs.getString("Edad", null);

        if (edadS != null) {
            edad.setText(edadS);
        }

        if (nombreS != null) {
            nombre.setText(nombreS);
        }
        updateBtSiguiente();


        nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void afterTextChanged(Editable c) {
                updateBtSiguiente();

            }
        });

        edad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateBtSiguiente();
            }
        });


        btSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("Nombre", nombre.getText().toString());
                editor.putString("Peso", "" + peso.getValue());
                editor.putString("Altura", "" + altura.getValue());
                editor.putString("Edad", edad.getText().toString());
                editor.putBoolean("Personalizar", personalizar.isChecked());

                editor.commit();

                usuario.setAltura(altura.getValue());
                usuario.setPeso(peso.getValue());
                usuario.setPersonalizar(personalizar.isChecked());

                Intent intent = new Intent(FormActivity.this, SomatiposActivity.class);
                intent.putExtra("usuario", usuario);

                startActivity(intent);
                finish();

            }
        });


        altura.setMinValue(100);
        altura.setMaxValue(220);
        altura.setValue(170);
        altura.setWheelItemCount(3);

        NumberPicker.Formatter formatterKilos = new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return i + " kg";
            }
        };

        NumberPicker.Formatter formatterCms = new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return i + " cms";
            }
        };
        altura.setFormatter(formatterCms);

        peso.setOrientation(LinearLayout.HORIZONTAL);
        peso.setMinValue(30);
        peso.setMaxValue(150);
        peso.setValue(80);
        peso.setWheelItemCount(3);
        peso.setFormatter(formatterKilos);

        personalizar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    descripcion.setText("Atencion: no obtendrá ninguna rutina hasta que el entrenador personal de su gimnasio se la asigne.");
                } else {
                    descripcion.setText("");

                }
            }
        });
    }

    private boolean emptyName() {
        if (nombre.getText().length() == 0) {
            return true;
        }

        return false;
    }

    private boolean emptyEdad() {
        if (edad.getText().length() == 0) {
            return true;
        }

        return false;
    }

    private void updateBtSiguiente() {
        if (emptyEdad() && emptyName()) {
            btSiguiente.setEnabled(false);
            btSiguiente.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        } else {
            btSiguiente.setEnabled(true);
            btSiguiente.getBackground().setColorFilter(null);
        }
    }



    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}