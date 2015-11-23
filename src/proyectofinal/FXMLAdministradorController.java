package proyectofinal;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import static proyectofinal.FXMLLoginController.resultUser;

/**
 * FXML Controller class
 *
 * @author Javier
 */
public class FXMLAdministradorController implements Initializable {

    @FXML //PaneAddUser
    TextField txtUsuario, txtContrasena, txtNombre, txtApellido, txtEdad,
            txtCorreo, txtDomicilio, txtTelefono;
    @FXML //pane perfil
    TextField txtUsuarioP, txtContrasenaP, txtPerfilP, txtNombreP, txtApellidoP,
            txtEdadP, txtCorreoP, txtDomicilioP, txtTelefonoP;
    @FXML //pane consultar
    TextField txtUsuario2, txtContrasena2,  txtNombre2, txtApellido2,
            txtEdad2, txtCorreo2, txtDomicilio2, txtTelefono2;
    @FXML //Pane borrar
    TextField txtUsuarioB, txtContrasenaB, txtPerfilB, txtNombreB, txtApellidoB,
            txtEdadB, txtCorreoB, txtDomicilioB, txtTelefonoB;
    @FXML
    AnchorPane pane, paneAddUser, paneBorrar, principal, paneConsultar, panePerfil;
    @FXML
    ChoiceBox choiceB,Perfil2;
    @FXML 
    Label lblWelcome;
    @FXML
    Button btnBorrar,btnB, btnActualizar, btnActualizar1, btnNext, btnPrev, btnCerrarS, btnPaint, btnBlock;
    @FXML
    TilePane admiPerfil, userPerfil;
 //---------------------Diseño-----------------------
    @FXML ColorPicker colorP,colorB;
//---------------------------------------------------
    private ResultSet rs = null;
    private Connection conecc;
    private Statement st = null;
    public String us;
    private String typeOfUser = "usuario", user = "";
    //Variables para deshabilitar botones
    private String usuarioH = "", contrasenaH = "", perfilH = "", nombreH = "", apellidoH = "", edadH = "", correoH = "", domicilioH = "", telefonoH;

    @FXML
    private void abrirPaint(ActionEvent e) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Paint/FXMLminipaint.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void abrirExplorer(ActionEvent e) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Explorador/FXMLExplorer.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void abrirBlock(ActionEvent e) {
        Stage stage = new Stage();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("Block/FXMLDocument.fxml"));
        } catch (IOException ex) {
            System.out.println("error");
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void openWindowWithOption(String file) {

        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(file));
        } catch (IOException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    //---------------------Pane Add User----------------------------------------

    @FXML
    private void ingresar() {
        principal.setVisible(false);
        paneAddUser.setVisible(true);
        paneBorrar.setVisible(false);
        panePerfil.setVisible(false);
        paneConsultar.setVisible(false);
        eraseB();
    }

    @FXML
    private void submitData(ActionEvent event) {;
        insertarDatosPuro();
        clean();
        
    }
private void clean() {
        txtContrasena.setText(null);
        txtUsuario.setText(null);
        txtNombre.setText(null);
        txtApellido.setText(null);
        txtEdad.setText(null);
        txtCorreo.setText(null);
        txtDomicilio.setText(null);
        txtTelefono.setText(null);
        choiceB.valueProperty().set(null);
    }
    private void insertarDatosPuro() {
        Connection conn = null;
        Statement st;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/pvisual", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = conn.createStatement();
            String usuario = txtUsuario.getText();
            String contrasena = txtContrasena.getText();
            String perfil = choiceB.getValue().toString();
            System.out.println(perfil);
            String nombre = txtNombre.getText();
            String apellidos = txtApellido.getText();
            String edad = txtEdad.getText();
            String email = txtCorreo.getText();
            String domicilio = txtDomicilio.getText();
            String telefono = txtTelefono.getText();
            Boolean resultado = false;
            resultado = st.execute("INSERT INTO usuarios(usuario, contraseña, perfil, nombre,apellidos,edad,email,domicilio,telefono) "
                    + "VALUES" + "('" + usuario + "','" + contrasena + "','" + perfil + "','" + nombre + "','" + apellidos + "','" + edad + "','" + email + "','" + domicilio + "','" + telefono + "');");
            if (!resultado) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("El proceso fue exitoso");
                alert.showAndWait();
            }

            System.out.println("El resultado es: " + resultado);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //--------------------------------------------------------------------------
    //----------------------Pane consultar-------------------------------------

    @FXML
    private void submitData2(ActionEvent event) {
        principal.setVisible(false);
        paneAddUser.setVisible(false);
        paneConsultar.setVisible(true);
        paneBorrar.setVisible(false);
        panePerfil.setVisible(false);
        consultarDatosPrimitive();
        eraseB();
    }

    private void consultarDatosPrimitive() {
        btnBorrar.setDisable(false);
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conecc = DriverManager.getConnection("jdbc:mysql://localhost/pvisual", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = conecc.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        String usuario = txtUsuario.getText();
        String instruccion = " select * from usuarios ";
        try {
            rs = st.executeQuery(instruccion); //Ejecuta el query de SQL
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        nextData();
    }

    @FXML
    private void nextData() {
        try {
            if (rs.next()) {

                nombreH = rs.getString("nombre");
                txtNombre2.setText(nombreH);
                contrasenaH = rs.getString("contraseña");
                txtContrasena2.setText(contrasenaH);
                usuarioH = rs.getString("usuario");
                txtUsuario2.setText(usuarioH);
                perfilH = rs.getString("perfil");
                Perfil2.valueProperty().set(perfilH);
                apellidoH = rs.getString("apellidos");
                txtApellido2.setText(apellidoH);
                edadH = rs.getString("edad");
                txtEdad2.setText(edadH);
                correoH = rs.getString("email");
                txtCorreo2.setText(correoH);
                domicilioH = rs.getString("domicilio");
                txtDomicilio2.setText(domicilioH);
                telefonoH = rs.getString("telefono");
                txtTelefono2.setText(telefonoH);
                if (typeOfUser.equals("administrador") && user.equals(usuarioH)) {
                    btnBorrar.setDisable(true);
                } else {
                    btnBorrar.setDisable(false);
                }

            }
            if (rs.isLast()) {
                btnNext.setDisable(true);
            } else {
                btnNext.setDisable(false);
            }
            if (rs.isFirst()) {
                btnPrev.setDisable(true);
            } else {
                btnPrev.setDisable(false);
            }
            btnActualizar.setDisable(true);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void prevData() {
        try {
            if (rs.previous()) {
                nombreH = rs.getString("nombre");
                txtNombre2.setText(nombreH);
                contrasenaH = rs.getString("contraseña");
                txtContrasena2.setText(contrasenaH);
                usuarioH = rs.getString("usuario");
                txtUsuario2.setText(usuarioH);
                perfilH = rs.getString("perfil");
                Perfil2.valueProperty().set(perfilH);
                apellidoH = rs.getString("apellidos");
                txtApellido2.setText(apellidoH);
                edadH = rs.getString("edad");
                txtEdad2.setText(edadH);
                correoH = rs.getString("email");
                txtCorreo2.setText(correoH);
                domicilioH = rs.getString("domicilio");
                txtDomicilio2.setText(domicilioH);
                telefonoH = rs.getString("telefono");
                txtTelefono2.setText(telefonoH);
                if (typeOfUser.equals("administrador") && user.equals(usuarioH)) {
                    btnBorrar.setDisable(true);
                } else {
                    btnBorrar.setDisable(false);
                }
            }
            if (rs.isLast()) {
                btnNext.setDisable(true);
            } else {
                btnNext.setDisable(false);
            }
            if (rs.isFirst()) {
                btnPrev.setDisable(true);
            } else {
                btnPrev.setDisable(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Delete(ActionEvent event) {
        Connection conn = null;
        Statement st;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/pvisual", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String usuario = txtUsuario2.getText();

        try {
            st = conn.createStatement();

            Boolean resultado = false;
            resultado = st.execute("DELETE FROM usuarios WHERE usuario = ('" + usuario + "');");
            System.out.println("El resultado es: " + resultado);
            if (!resultado) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Información");
                alert.setHeaderText("Alerta");
                alert.setContentText("¡Se han eliminado los datos!\n Los datos serán nuevamente consultados");
                alert.showAndWait();
                consultarDatosPrimitive();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Actualizar(ActionEvent event) {
        Connection conn = null;
        Statement st;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/pvisual", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = conn.createStatement();
            String usuario = txtUsuario2.getText();
            String contrasena = txtContrasena2.getText();
            String perfil = Perfil2.getValue().toString();
            String nombre = txtNombre2.getText();
            String apellidos = txtApellido2.getText();
            String edad = txtEdad2.getText();
            String email = txtCorreo2.getText();
            String domicilio = txtDomicilio2.getText();
            String telefono = txtTelefono2.getText();
            Boolean resultado = false;
            resultado = st.execute("UPDATE usuarios SET contraseña=('" + contrasena + "'),telefono=('" + telefono + "'),domicilio=('" + domicilio + "'),email=('" + email + "'),nombre=('" + nombre + "'),apellidos=('" + apellidos + "'), edad=('" + edad + "') WHERE usuario = ('" + usuario + "');");
            System.out.println("El resultado es: " + resultado);
            if (!resultado) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText("Alerta");
                alert.setContentText("¡Se han actualizado los datos!");
                alert.showAndWait();
                consultarDatosPrimitive();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //---------------------Pane perfil------------------------------------------

    private void viewPerfil() {
        paneBorrar.setVisible(false);
        principal.setVisible(false);
        paneAddUser.setVisible(false);
        paneConsultar.setVisible(false);
        panePerfil.setVisible(true);
        eraseB();
    }

    @FXML
    public void Perfil() {
        viewPerfil();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conecc = DriverManager.getConnection("jdbc:mysql://localhost/pvisual", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = conecc.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            us = resultUser.getString("usuario");
        } catch (Exception e) {

        }
        String instruccion = " select * from usuarios where usuario=('" + this.us + "') ";
        try {
            rs = st.executeQuery(instruccion); //Ejecuta el query de SQL
            try {
                if (rs.next()) {
                    nombreH = rs.getString("nombre");
                    txtNombreP.setText(nombreH);
                    contrasenaH = rs.getString("contraseña");
                    txtContrasenaP.setText(contrasenaH);
                    usuarioH = rs.getString("usuario");
                    txtUsuarioP.setText(usuarioH);
                    perfilH = rs.getString("perfil");
                    txtPerfilP.setText(perfilH);
                    apellidoH = rs.getString("apellidos");
                    txtApellidoP.setText(apellidoH);
                    edadH = rs.getString("edad");
                    txtEdadP.setText(edadH);
                    correoH = rs.getString("email");
                    txtCorreoP.setText(correoH);
                    domicilioH = rs.getString("domicilio");
                    txtDomicilioP.setText(domicilioH);
                    telefonoH = rs.getString("telefono");
                    txtTelefonoP.setText(telefonoH);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void ActualizarP(ActionEvent event) {
        Connection conn = null;
        Statement st;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/pvisual", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = conn.createStatement();
            String usuario = txtUsuarioP.getText();
            String contrasena = txtContrasenaP.getText();
            String perfil = txtPerfilP.getText();
            String nombre = txtNombreP.getText();
            String apellidos = txtApellidoP.getText();
            String edad = txtEdadP.getText();
            String email = txtCorreoP.getText();
            String domicilio = txtDomicilioP.getText();
            String telefono = txtTelefonoP.getText();
            Boolean resultado = false;
            resultado = st.execute("UPDATE usuarios SET contraseña=('" + contrasena + "'),telefono=('" + telefono + "'),domicilio=('" + domicilio + "'),email=('" + email + "'),nombre=('" + nombre + "'),apellidos=('" + apellidos + "'), edad=('" + edad + "') WHERE usuario = ('" + usuario + "');");
            System.out.println("El resultado es: " + resultado);
            if (!resultado) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText("Alerta");
                alert.setContentText("¡Se han actualizado los datos!");
                alert.showAndWait();
            }
            nombreH = rs.getString("nombre");
            contrasenaH = rs.getString("contraseña");
            usuarioH = rs.getString("usuario");
            perfilH = rs.getString("perfil");
            apellidoH = rs.getString("apellidos");
            edadH = rs.getString("edad");
            correoH = rs.getString("email");
            domicilioH = rs.getString("domicilio");
            telefonoH = rs.getString("telefono");

        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void welcome(ActionEvent event) {
        principal.setVisible(true);
        paneConsultar.setVisible(false);
        panePerfil.setVisible(false);
        paneAddUser.setVisible(false);
        eraseB();
    }

    @FXML
    void Borrar() {
        principal.setVisible(false);
        paneAddUser.setVisible(false);
        paneConsultar.setVisible(false);
        panePerfil.setVisible(false);
        paneBorrar.setVisible(true);

    }

    @FXML
    private void CerrarSesion(ActionEvent e) {
        Stage s = (Stage) btnCerrarS.getScene().getWindow();
        s.close();

        Stage stage = new Stage();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.show();
    }
//-------------------------------Pane Borrar------------------------------
    @FXML
    private void Buscar() {

        try {
            Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conecc = DriverManager.getConnection("jdbc:mysql://localhost/pvisual", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = conecc.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String usuario = txtUsuarioB.getText();
        String instruccion = ("SELECT *FROM usuarios WHERE usuario=('" + usuario + "');");
        System.out.println("Buscando...");
        try {
            rs = st.executeQuery(instruccion); //Ejecuta el query de SQL
            try {

                if (rs.next()) {
                    txtContrasenaB.setText(rs.getString("contraseña"));
                    txtUsuarioB.setText(rs.getString("usuario"));
                    txtPerfilB.setText(rs.getString("perfil"));
                    txtNombreB.setText(rs.getString("nombre"));
                    txtApellidoB.setText(rs.getString("apellidos"));
                    txtEdadB.setText(rs.getString("edad"));
                    txtCorreoB.setText(rs.getString("email"));
                    txtDomicilioB.setText(rs.getString("domicilio"));
                    txtTelefonoB.setText(rs.getString("telefono"));
                    if(user.equals(rs.getString("usuario"))&& typeOfUser.equals("administrador"))
                    btnB.setDisable(true);
                    else
                       btnB.setDisable(false); 
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Información");
                    alert.setHeaderText("Alerta");
                    alert.setContentText("¡No se han encontrado los datos!");
                    alert.showAndWait();
                    btnB.setDisable(true);
                }

            } catch (SQLException ex) {
                Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void DeleteB(ActionEvent event) {
        Connection conn = null;
        Statement st;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/pvisual", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = conn.createStatement();
            String usuario = txtUsuarioB.getText();
            Boolean resultado = false;
            resultado = st.execute("DELETE FROM usuarios WHERE usuario = ('" + usuario + "');");
            System.out.println("El resultado es: " + resultado);
            if (!resultado) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Información");
                alert.setHeaderText("Alerta");
                alert.setContentText("¡Se han eliminado los datos!");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        eraseB();
    }

    private void eraseB() {
        txtContrasenaB.setText(null);
        txtUsuarioB.setText(null);
        txtPerfilB.setText(null);
        txtNombreB.setText(null);
        txtApellidoB.setText(null);
        txtEdadB.setText(null);
        txtCorreoB.setText(null);
        txtDomicilioB.setText(null);
        txtTelefonoB.setText(null);
        btnB.setDisable(true);
    }
//-----------------------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (ProyectoFinal.get().getPerfil().equals("administrador")) {
            typeOfUser = "administrador";
            user = ProyectoFinal.get().getUser();
            admiPerfil.setVisible(true);
            userPerfil.setVisible(false);
        } else {
            user = ProyectoFinal.get().getUser();
            admiPerfil.setVisible(false);
            userPerfil.setVisible(true);
        }
        principal.setVisible(true);
        lblWelcome.setText("Bienvenido "+user);
        pane.getStylesheets().add(FXMLAdministradorController.class.getResource("Estilos/GreenStyle.css").toExternalForm());
        btnNext.setDisable(true);
        btnPrev.setDisable(true);
        btnActualizar.setDisable(true);
        btnBorrar.setDisable(true);
        btnConsultarListeners();
        txtPerfilP.setEditable(false);
        btnPerfilListeners();

    }

    private void btnConsultarListeners() {
        txtTelefono2.textProperty().addListener((observable, oldValue, nextValue) -> {
            if (!nextValue.equals(telefonoH)) {
                btnActualizar.setDisable(false);
            } else {
                btnActualizar.setDisable(true);
            }
        });
        txtDomicilio2.textProperty().addListener((observable, oldValue, nextValue) -> {
            if (!nextValue.equals(domicilioH)) {
                btnActualizar.setDisable(false);
            } else {
                btnActualizar.setDisable(true);
            }
        });
        txtCorreo2.textProperty().addListener((observable, oldValue, nextValue) -> {
            if (!nextValue.equals(correoH)) {
                btnActualizar.setDisable(false);
            } else {
                btnActualizar.setDisable(true);
            }
        });
        txtContrasena2.textProperty().addListener((observable, oldValue, nextValue) -> {
            if (!nextValue.equals(contrasenaH)) {
                btnActualizar.setDisable(false);
            } else {
                btnActualizar.setDisable(true);
            }
        });

        txtNombre2.textProperty().addListener((observable, oldValue, nextValue) -> {
            if (!nextValue.equals(nombreH)) {
                btnActualizar.setDisable(false);
            } else {
                btnActualizar.setDisable(true);
            }
        });
        txtApellido2.textProperty().addListener((observable, oldValue, nextValue) -> {
            if (!nextValue.equals(apellidoH)) {
                btnActualizar.setDisable(false);
            } else {
                btnActualizar.setDisable(true);
            }
        });
        txtEdad2.textProperty().addListener((observable, oldValue, nextValue) -> {
            if (!nextValue.equals(edadH)) {
                btnActualizar.setDisable(false);
            } else {
                btnActualizar.setDisable(true);
            }
        });
    }

    private void btnPerfilListeners() {
        txtTelefonoP.textProperty().addListener((observable, oldValue, nextValue) -> {
            if (!nextValue.equals(telefonoH)) {
                btnActualizar1.setDisable(false);
            } else {
                btnActualizar1.setDisable(true);
            }
        });
        txtDomicilioP.textProperty().addListener((observable, oldValue, nextValue) -> {
            if (!nextValue.equals(domicilioH)) {
                btnActualizar1.setDisable(false);
            } else {
                btnActualizar1.setDisable(true);
            }
        });
        txtCorreoP.textProperty().addListener((observable, oldValue, nextValue) -> {
            if (!nextValue.equals(correoH)) {
                btnActualizar1.setDisable(false);
            } else {
                btnActualizar1.setDisable(true);
            }
        });
        txtContrasenaP.textProperty().addListener((observable, oldValue, nextValue) -> {
            if (!nextValue.equals(contrasenaH)) {
                btnActualizar1.setDisable(false);
            } else {
                btnActualizar1.setDisable(true);
            }
        });

        txtNombreP.textProperty().addListener((observable, oldValue, nextValue) -> {
            if (!nextValue.equals(nombreH)) {
                btnActualizar1.setDisable(false);
            } else {
                btnActualizar1.setDisable(true);
            }
        });
        txtApellidoP.textProperty().addListener((observable, oldValue, nextValue) -> {
            if (!nextValue.equals(apellidoH)) {
                btnActualizar1.setDisable(false);
            } else {
                btnActualizar1.setDisable(true);
            }
        });
        txtEdadP.textProperty().addListener((observable, oldValue, nextValue) -> {
            if (!nextValue.equals(edadH)) {
                btnActualizar1.setDisable(false);
            } else {
                btnActualizar1.setDisable(true);
            }
        });
    }
}
