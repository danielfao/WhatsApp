package com.example.danieloliveira.whatsapp.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danieloliveira.whatsapp.R;
import com.example.danieloliveira.whatsapp.application.ConfiguracaoFirebase;
import com.example.danieloliveira.whatsapp.helper.Base64Custom;
import com.example.danieloliveira.whatsapp.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button botaoCadastrar;
    private Usuario usuario;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        nome = (EditText) findViewById(R.id.name_text);
        email = (EditText) findViewById(R.id.email_text_usuario);
        senha = (EditText) findViewById(R.id.senha_usuario);
        botaoCadastrar = (Button) findViewById(R.id.botao_cadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());

                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();
        firebaseAuth.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CadastroUsuarioActivity.this, "Sucesso ao cadastrar usuário", Toast.LENGTH_LONG).show();

                    //recupera o identificador do usuario
                    FirebaseUser usuarioFirebase = task.getResult().getUser();

                    //codifica o email para base64
                    String identificador = Base64Custom.converterBase64(usuario.getEmail());
                    usuario.setId(identificador);
                    usuario.salvar();

                    firebaseAuth.signOut();
                    finish();
                } else {
                    String erroExcessao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcessao = "Digite uma senha mais forte, contendo caracteres, letras e números";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcessao = "O e-mail digitado é inválido. digite um novo e-mail";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcessao = "O e-mail digitado já está em uso no App";
                    } catch (Exception e) {
                        erroExcessao = "Ao efetuar o cadastro";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroUsuarioActivity.this, "Erro: " + erroExcessao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}