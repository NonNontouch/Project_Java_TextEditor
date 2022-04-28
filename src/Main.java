import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Main extends Application {
  private static Stage stage;
  private static FileIO fileIO;
  private static UI ui;
  private static TextControl textcontroler;
  private static boolean must_save = false;

  @Override
  public void start(Stage primaryStage) {

    stage = primaryStage;

    ui = new UI();
    ui.getTextArea().setOnKeyPressed(e -> {
      if (!must_save) {
        isKeypressed();
      }
    });

    fileIO = new FileIO(ui.getTextArea(), primaryStage);
    textcontroler = new TextControl(primaryStage, ui.getTextArea());

    Scene scene = new Scene(ui, 800, 600);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Notepad--");
    try {
      primaryStage.getIcons().add(new Image(new FileInputStream("Picture/Icon.png")));
    } catch (FileNotFoundException e1) {
      e1.printStackTrace();
    }
    primaryStage.show();
    primaryStage.setOnCloseRequest(event -> {
      event.consume();
      EventBeforeExit(stage);
    });

  }

  public static void EventBeforeExit(Stage stage) {
    if (!ui.getTextArea().getText().equals("") && must_save) {
      ButtonType savebtn = new ButtonType("Save");
      ButtonType donotsavebtn = new ButtonType("Don't Save");
      ButtonType cancelbtn = new ButtonType("Cancel");
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Alert");
      alert.setHeaderText("You're about to exit");
      alert.setContentText("Do you want to save your file before exit");
      alert.getButtonTypes().clear();
      alert.getButtonTypes().addAll(savebtn, donotsavebtn, cancelbtn);

      Stage window = (Stage) alert.getDialogPane().getScene().getWindow();
      window.setOnCloseRequest(e -> alert.close());
      try {
        window.getIcons().add(new Image(new FileInputStream("Picture/Information.png")));
      } catch (FileNotFoundException e1) {
        e1.printStackTrace();
      }

      Optional<ButtonType> userinput = alert.showAndWait();
      userinput.ifPresent(e -> {
        if (e.equals(donotsavebtn)) {
          stage.close();
        } else if (e.equals(savebtn)) {
          fileIO.SaveFile();
          stage.close();
        } else if (e.equals(cancelbtn)) {
          alert.close();
        }
      });

    } else {
      stage.close();
    }
  }

  public static void onNew(String[] args) {
    launch(args);
  }

  public static void onOpen() {
    fileIO.OpenFile();
  }

  public static void onSave() {
    fileIO.SaveFile();
  }

  public static void onSaveAs() {
    fileIO.SaveAsFile();
    fileIO.ChangeTitle(stage);
  }

  public static void onExit() {
    EventBeforeExit(stage);
  }

  public static void onUndo() {
    ui.getTextArea().undo();
  }

  public static void onRedo() {
    ui.getTextArea().redo();
  }

  public static void onCut() {
    ui.getTextArea().cut();
  }

  public static void onCopy() {
    ui.getTextArea().copy();
  }

  public static void onPaste() {
    ui.getTextArea().paste();
  }

  public static void onFind(){
    textcontroler.TextFinderEvent();
  }

  public static void onAbout() {
    Stage alertStage = new Stage();
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setHeaderText(null);
    alert.setTitle("About");
    alert.setContentText(
        "This is a Notepad-- \nThis program is developed by \n64011106 ณรงค์พล กิจรังสรรค์ \n64011160 นนทัช มุกลีมาศ \n64011273 วิชชุ ศรีโยธี \n64011301 สิทธิ นวะมะวัฒน์");

    alert.getDialogPane().setStyle("-fx-font-size: 15;");
    alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
    try {
      alertStage.getIcons().add(new Image(new FileInputStream("Picture/Information.png")));
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    alertStage.show();
  }

  public static void onHelp() {
    System.out.println("Help");
  }

  public static void onFormat() {

    textcontroler.TextControlEvent();

  }

  public void isKeypressed() {
    must_save = true;
  }

  public static void setSavestage(boolean in) {
    must_save = in;
  }

  public static void main(String[] args) {
    launch(args);
  }
}