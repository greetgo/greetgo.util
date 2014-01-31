package kz.greetgo.homecreditdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    Button enter = (Button)findViewById(R.id.enter);
    enter.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        enter();
      }
    });
    
    login = (EditText)findViewById(R.id.login);
    password = (EditText)findViewById(R.id.password);
    
    ((Button)findViewById(R.id.toKarusel)).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        next();
      }
    });
  }
  
  private EditText login, password;
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
  
  private void enter() {
    String pass = password.getText().toString();
    password.setText(null);
    
    if ("asd".equals(login.getText().toString()) && "111".equals(pass)) {
      next();
      return;
    }
    
    AlertDialog.Builder b = new AlertDialog.Builder(this);
    b.setMessage(R.string.wronge_login_password);
    b.setTitle("Ошибка при входе");
    b.setCancelable(true);
    b.create().show();
  }
  
  private void next() {
    Intent intent = new Intent(this, Anim02Activity.class);
    startActivity(intent);
  }
}