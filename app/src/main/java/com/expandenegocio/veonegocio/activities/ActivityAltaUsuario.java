package com.expandenegocio.veonegocio.activities;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.expandenegocio.veonegocio.DAO.MunicipioDataSource;
import com.expandenegocio.veonegocio.DAO.ProvinciaDataSource;
import com.expandenegocio.veonegocio.DAO.UserDataSource;
import com.expandenegocio.veonegocio.R;
import com.expandenegocio.veonegocio.models.Municipio;
import com.expandenegocio.veonegocio.models.Provincia;
import com.expandenegocio.veonegocio.models.User;
import com.expandenegocio.veonegocio.utilities.DialogoDate1;
import com.expandenegocio.veonegocio.utilities.DialogoDate2;
import com.expandenegocio.veonegocio.utilities.ValidatorUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jesus on 20/03/2017.
 */

public class ActivityAltaUsuario extends AppCompatActivity {
    private Provincia provincia;
    private Municipio municipio;
    private User usuario;

    private Provincia provinciaSeleccionada;
    private Municipio municipioSeleccionado;

    private String correo;
    private String password;
    private String nombre;
    private String status;
    private String apellidos;
    private String telefono;
    private Spinner spnProvincia;
    private Spinner spnMunicipio;
    private String capital;
    private String capitalObservaciones;
    private Integer cerrada;
    private String cuandoEmpezar;
    private String dateEntered;
    private String dateModified;
    private Integer deleted;
    private String disponeContacto;
    private Integer disponeLocal;
    private String empresa;
    private String firstName;
    private String id2;
    private String lastName;
    private String negocio;
    private Integer negocioAnterior;
    private String perfilFranquicia;
    private String perfilProfesional;
    private String phoneHome;
    private String phoneMobile;
    private String recursosPropios;
    private String situacionProfesional;

    private EditText txtCorreo;
    private EditText txtPassword;
    private EditText txtNombre;
    private EditText txtApellidos;
    private EditText txtTelefono;
    private EditText txtCapital;
    private EditText txtCapitalObservaciones;
    private EditText txtCerrada;
    private EditText txtCuandoEmpezar;
    private EditText txtDisponeContacto;
    private EditText txtDisponeLocal;
    private EditText txtEmpresa;
    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtNegocio;
    private EditText txtNegocioAnterior;
    private EditText txtPerfilFranquicia;
    private EditText txtPerfilProfesional;
    private EditText txtPhoneHome;
    private EditText txtPhoneMobile;
    private EditText txtRecursosPropios;
    private EditText txtSituacionProfesional;
    private TextView tvFecha1;
    private TextView tvFecha2;
    private static int ID_FECHA1;
    private static int ID_FECHA2;
    private DialogFragment elDialogFragment;
    private String nCorreo;
    private String nPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_altas_users);
        nCorreo = getIntent().getStringExtra("correo");
        nPassword = getIntent().getStringExtra("password");

        tvFecha1 = (TextView) findViewById(R.id.tv_alta_fecha1);
        tvFecha2 = (TextView) findViewById(R.id.tv_alta_fecha2);
        ID_FECHA1 = tvFecha1.getId();
        Calendar ahora = Calendar.getInstance();
        actualizarLaFecha1EnTextView(ahora);
        ID_FECHA2 = tvFecha2.getId();
        ahora = Calendar.getInstance();
        actualizarLaFecha2EnTextView(ahora);

        txtCorreo = (EditText) findViewById(R.id.et_alta_correo);
        txtPassword = (EditText) findViewById(R.id.et_alta_password);
        txtNombre = (EditText) findViewById(R.id.et_alta_nombre);
        txtApellidos = (EditText) findViewById(R.id.et_alta_apellidos);
        txtTelefono = (EditText) findViewById(R.id.et_alta_telefono);
        spnProvincia = (Spinner) findViewById(R.id.spinner_provincia_alta_user);
        spnMunicipio = (Spinner) findViewById(R.id.spinner_municipio_alta_user);
        txtCapital = (EditText) findViewById(R.id.et_alta_capital);
        txtCapitalObservaciones = (EditText) findViewById(R.id.et_alta_capital_observaciones);
        txtCerrada = (EditText) findViewById(R.id.et_alta_cerrada);
        txtCuandoEmpezar = (EditText) findViewById(R.id.et_alta_cuando_empezar);
        txtDisponeContacto = (EditText) findViewById(R.id.et_alta_dispone_contacto);
        txtDisponeLocal = (EditText) findViewById(R.id.et_alta_dispone_local);
        txtEmpresa = (EditText) findViewById(R.id.et_alta_empresa);
        txtFirstName = (EditText) findViewById(R.id.et_alta_first_name);
        txtLastName = (EditText) findViewById(R.id.et_alta_last_name);
        txtNegocio = (EditText) findViewById(R.id.et_alta_negocio);
        txtNegocioAnterior = (EditText) findViewById(R.id.et_alta_negocio_anterior);
        txtPerfilFranquicia = (EditText) findViewById(R.id.et_alta_perfil_franquicia);
        txtPerfilProfesional = (EditText) findViewById(R.id.et_alta_perfil_profesional);
        txtRecursosPropios = (EditText) findViewById(R.id.et_alta_recursos_propios);
        txtSituacionProfesional = (EditText) findViewById(R.id.et_alta_situacion_profesional);
        txtPhoneHome = (EditText) findViewById(R.id.et_alta_phone_home);
        txtPhoneMobile = (EditText) findViewById(R.id.et_alta_phone_mobile);
        txtCorreo.setText(nCorreo);
        txtPassword.setText(nPassword);
        provincia = loadSpinnerProvincias();

    }


    private Provincia loadSpinnerProvincias() {

        ProvinciaDataSource dataSource = new ProvinciaDataSource(this);
        final ArrayList<Provincia> listaProv = dataSource.getProvincias();

        ArrayAdapter spinner_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaProv);

        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnProvincia = (Spinner) findViewById(R.id.spinner_provincia_alta_user);

        spnProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                                   @Override
                                                   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                       Provincia provinciaSeleccionada = listaProv.get(position);
                                                       if (provinciaSeleccionada != null) {
                                                           municipio = loadSpinnerMunicipios(provinciaSeleccionada.getId());
                                                       }
                                                   }

                                                   @Override
                                                   public void onNothingSelected(AdapterView<?> parent) {
                                                       Provincia provinciaSeleccionada = null;
                                                   }
                                               }
        );

        spnProvincia.setAdapter(spinner_adapter);

        return provinciaSeleccionada;

    }

    private Municipio loadSpinnerMunicipios(Integer numeroProvincia) {
        MunicipioDataSource dataSource = new MunicipioDataSource(this);
        final ArrayList<Municipio> listaMunicipios = dataSource.getMunicipios(numeroProvincia);

        ArrayAdapter spinner_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listaMunicipios);

        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnMunicipio = (Spinner) findViewById(R.id.spinner_municipio_alta_user);

        spnMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                                   @Override
                                                   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                       Municipio municipoSeleccionado = listaMunicipios.get(position);
                                                   }

                                                   @Override
                                                   public void onNothingSelected(AdapterView<?> parent) {
                                                       Municipio municipioSeleccionado = null;
                                                   }
                                               }
        );

        spnMunicipio.setAdapter(spinner_adapter);
        return municipioSeleccionado;
    }

    public void altaUsuario(View view) {

        String val = validate();

        if (val == null) {

            usuario = createUsuario();


            procesarInformacion();

        } else {
            Toast.makeText(getApplicationContext(), val, Toast.LENGTH_LONG).show();
        }


    }

    private String validate() {

        String output = null;

        correo = txtCorreo.getText().toString();
        password = txtPassword.getText().toString();
        nombre = txtNombre.getText().toString();
        apellidos = txtApellidos.getText().toString();
        telefono = txtTelefono.getText().toString();
        provincia = (Provincia) spnProvincia.getSelectedItem();
        municipio = (Municipio) spnMunicipio.getSelectedItem();
        capital = txtCapital.getText().toString();
        capitalObservaciones = txtCapitalObservaciones.getText().toString();
        cerrada = Integer.parseInt(txtCerrada.getText().toString());
        cuandoEmpezar = txtCuandoEmpezar.getText().toString();
        deleted = 0;
        disponeContacto = txtDisponeContacto.getText().toString();
        disponeLocal = Integer.parseInt(txtDisponeLocal.getText().toString());
        empresa = txtEmpresa.getText().toString();
        firstName = txtFirstName.getText().toString();
        lastName = txtLastName.getText().toString();
        id2 = "";
        negocio = txtNegocio.getText().toString();
        negocioAnterior = Integer.parseInt(txtNegocioAnterior.getText().toString());
        perfilFranquicia = txtPerfilFranquicia.getText().toString();
        perfilProfesional = txtPerfilProfesional.getText().toString();
        phoneHome = txtPhoneHome.getText().toString();
        phoneMobile = txtPhoneMobile.getText().toString();
        recursosPropios = txtRecursosPropios.getText().toString();
        situacionProfesional = txtSituacionProfesional.getText().toString();


        if (correo.trim().equals("")) {
            output = "El campo correo no puede estar vacío";
        }
        if (!ValidatorUtil.validateEmail(correo)) {
            output = "El correo no es válido";
        }
        if (password.trim().equals("")) {
            output = "el campo contraseña no puede estar vacío";
        }
        if (nombre.trim().equals("")) {
            output = "El nombre no puede estar vacío";
        }
        if (apellidos.trim().equals("")) {
            output = "El campo Apellidos no puede estar vacío";
        }
        if (telefono.trim().equals("")) {
            output = "El campo telefono no puede estar vacío";
        }
        if (!ValidatorUtil.validateTel(telefono.trim(), "34")) {
            output = "El formato de teléfono no es válido";
        }
        if (provincia == null || provincia.getId() == -1) {
            output = "El campo provincia no puede estar vacío";
        }
        if (municipio == null || municipio.getNombreMunicipio() == null) {
            output = "El campo municipio no puede estar vacío";
        }
        if (capital.trim().equals("")) {
            output = "El campo capital no puede estar vacío";
        }
        if (capitalObservaciones.trim().equals("")) {
            output = "El campo capital observaciones no puede estar vacío";
        }
        if (txtCerrada.getText().toString().trim().equals("")) {
            output = "El campo cerrada no puede estar vacío";
        }
        if (disponeContacto.trim().equals("")) {
            output = "El campo dispone contacto no puede estar vacío";
        }
        if (txtDisponeLocal.getText().toString().trim().equals("")) {
            output = "El campo dispone local no puede estar vacío";
        }
        if (empresa.trim().equals("")) {
            output = "El campo empresa no puede estar vacío";
        }
        if (firstName.trim().equals("")) {
            output = "El campo first name no puede estar vacío";
        }
        if (lastName.trim().equals("")) {
            output = "El campo last name no puede estar vacío";
        }

        if (negocio.trim().equals("")) {
            output = "El campo negocio no puede estar vacío";
        }
        if (txtNegocioAnterior.getText().toString().trim().equals("")) {
            output = "El campo negocio anterior no puede estar vacío";
        }
        if (perfilFranquicia.trim().equals("")) {
            output = "El campo perfil franquicia no puede estar vacío";
        }
        if (perfilProfesional.trim().equals("")) {
            output = "El campo perfil profesional no puede estar vacío";
        }


        return output;
    }

    private User createUsuario() {

        User usuario = new User();

        usuario.setId(UUID.randomUUID().toString());
        usuario.setEmail(correo);
        usuario.setPassword(password);
        usuario.setStatus("");
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setTelefono(telefono);
        usuario.setCodigoProv(provincia.getId());
        usuario.setCodigoMun(municipio.getCodigoMunicipio());
        usuario.setCapital(capital);
        usuario.setCapitalObservaciones(capitalObservaciones);
        usuario.setCerrada(cerrada);
        usuario.setCuandoEmpezar(cuandoEmpezar);
        dateEntered = tvFecha1.getText().toString();
        dateModified = tvFecha2.getText().toString();


        usuario.setDateEntered(new Date());
        usuario.setDateModified(new Date());


        usuario.setDeleted(deleted);
        usuario.setDisponeContacto(disponeContacto);
        usuario.setDisponeLocal(disponeLocal);
        usuario.setEmpresa(empresa);
        usuario.setFirstName(firstName);
        usuario.setId2(UUID.randomUUID().toString());
        usuario.setLastName(lastName);
        usuario.setNegocio(negocio);
        usuario.setNegocioAnterior(negocioAnterior);
        usuario.setPerfilFranquicia(perfilFranquicia);
        usuario.setPerfilProfesional(perfilProfesional);
        usuario.setPhoneHome(phoneHome);
        usuario.setPhoneMobile(phoneMobile);
        usuario.setRecursosPropios(recursosPropios);
        usuario.setSituacionProfesional(situacionProfesional);

        return usuario;

    }

    /**
     * Permite convertir un String en fecha (Date).
     *
     * @param fecha Cadena de fecha dd/MM/yyyy
     * @return Objeto Date
     */
    public static Date ParseFecha(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        } catch (ParseException ex) {
            System.out.println(ex);
        }
        return fechaDate;
    }

    private void procesarInformacion() {

        RequestParams params = new RequestParams();

        params.put(UserDataSource.ColumnUsuarios.ID, usuario.getId().toString());
        params.put(UserDataSource.ColumnUsuarios.EMAIL, correo);
        params.put(UserDataSource.ColumnUsuarios.PASSWORD, password);
        params.put(UserDataSource.ColumnUsuarios.STATUS, status);
        params.put(UserDataSource.ColumnUsuarios.NOMBRE, nombre);
        params.put(UserDataSource.ColumnUsuarios.APELLIDOS, apellidos);
        params.put(UserDataSource.ColumnUsuarios.TELEFONO, telefono);
        params.put(UserDataSource.ColumnUsuarios.CODIGO_PROVINCIA, usuario.getCodigoProv());
        params.put(UserDataSource.ColumnUsuarios.CODIGO_MUNICIPIO, usuario.getCodigoMun());
        params.put(UserDataSource.ColumnUsuarios.CAPITAL, usuario.getCapital());
        params.put(UserDataSource.ColumnUsuarios.CAPITAL_OBSERVACIONES, usuario.getCapitalObservaciones());
        params.put(UserDataSource.ColumnUsuarios.CERRADA, usuario.getCerrada());
        params.put(UserDataSource.ColumnUsuarios.CUANDO_EMPEZAR, usuario.getCuandoEmpezar());
        params.put(UserDataSource.ColumnUsuarios.DATE_ENTERED, usuario.getDateEntered());
        params.put(UserDataSource.ColumnUsuarios.DATE_MODIFIED, usuario.getDateModified());
        params.put(UserDataSource.ColumnUsuarios.DELETED, usuario.getDeleted());
        params.put(UserDataSource.ColumnUsuarios.DISP_CONTACTO, usuario.getDisponeContacto());
        params.put(UserDataSource.ColumnUsuarios.DISP_LOCAL, usuario.getDisponeLocal());
        params.put(UserDataSource.ColumnUsuarios.EMPRESA, usuario.getEmpresa());
        params.put(UserDataSource.ColumnUsuarios.FIRTS_NAME, usuario.getFirstName());
        params.put(UserDataSource.ColumnUsuarios.ID2, usuario.getId2());
        params.put(UserDataSource.ColumnUsuarios.LAST_NAME, usuario.getLastName());
        params.put(UserDataSource.ColumnUsuarios.NEGOCIO, usuario.getNegocio());
        params.put(UserDataSource.ColumnUsuarios.NEGOCIO_ANTES, usuario.getNegocioAnterior());
        params.put(UserDataSource.ColumnUsuarios.PERFIL_FRANQUICIA, usuario.getPerfilFranquicia());
        params.put(UserDataSource.ColumnUsuarios.PERFIL_PROFESIONAL, usuario.getPerfilProfesional());
        params.put(UserDataSource.ColumnUsuarios.PHONE_HOME, usuario.getPhoneHome());
        params.put(UserDataSource.ColumnUsuarios.PHONE_MOBILE, usuario.getPhoneMobile());
        params.put(UserDataSource.ColumnUsuarios.RECURSOS_PROPIOS, usuario.getRecursosPropios());
        params.put(UserDataSource.ColumnUsuarios.SITUACION_PROFESIONAL, usuario.getSituacionProfesional());


        invokeWS(params);


    }


    public void invokeWS(RequestParams params) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://www.expandenegocio.com/app/signup_jesus.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);


                try {

                    JSONObject obj = new JSONObject(response);

                    switch (obj.getInt("status")) {

                        case 0:
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                            break;
                        case 1:
                            Toast.makeText(getApplicationContext(), "Registrado correctamente!", Toast.LENGTH_LONG).show();
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(), "Ya hay un usuario registrado con ese correo", Toast.LENGTH_LONG).show();
                            break;
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable
                    error) {


                try {
                    if (responseBody != null) {
                        String response = new String(responseBody);
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }


    public void actualizarLaFecha2EnTextView(Calendar momento) {
        tvFecha2.setText(new StringBuilder()
                .append(momento.get(Calendar.DAY_OF_MONTH)).append("/")
                .append(momento.get(Calendar.MONTH) + 1).append("/")
                .append(momento.get(Calendar.YEAR)));
    }

    public void actualizarLaFecha1EnTextView(Calendar momento) {
        tvFecha1.setText(new StringBuilder()
                .append(momento.get(Calendar.DAY_OF_MONTH)).append("/")
                .append(momento.get(Calendar.MONTH) + 1).append("/")
                .append(momento.get(Calendar.YEAR)));
    }

    public void fecha1(View v) {

        int etiquetaView = v.getId();
        if (etiquetaView == ID_FECHA1) {

            elDialogFragment = new DialogoDate1();
            // Log.i("INFO", "se creo uno de fecha");

        }
        elDialogFragment.show(getSupportFragmentManager(), null);
    }

    public void fecha2(View v) {
        int etiquetaView = v.getId();
        if (etiquetaView == ID_FECHA2) {

            elDialogFragment = new DialogoDate2();
            // Log.i("INFO", "se creo uno de fecha");

        }
        elDialogFragment.show(getSupportFragmentManager(), null);
    }


}