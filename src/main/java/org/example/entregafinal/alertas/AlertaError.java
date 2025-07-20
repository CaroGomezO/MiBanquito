package org.example.entregafinal.alertas;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

public class AlertaError {

    public static void mostrar(String tituloTexto, String mensajeTexto) {
        Stage alerta = new Stage();
        alerta.initModality(Modality.APPLICATION_MODAL);
        alerta.initStyle(StageStyle.TRANSPARENT);

        Circle circulo = new Circle(40, Color.TRANSPARENT);
        circulo.setStroke(Color.web("#ff6b6b"));
        circulo.setStrokeWidth(4);
        circulo.setStyle("-fx-effect: dropshadow(gaussian, rgba(255,107,107,0.3), 15, 0.5, 0, 0);");

        Line linea1 = new Line(-15, -15, 15, 15);
        Line linea2 = new Line(-15, 15, 15, -15);
        for (Line l : new Line[]{linea1, linea2}) {
            l.setStroke(Color.web("#ff6b6b"));
            l.setStrokeWidth(4);
            l.setStrokeLineCap(javafx.scene.shape.StrokeLineCap.ROUND);
        }

        StackPane icono = new StackPane(circulo, linea1, linea2);

        Label titulo = new Label(tituloTexto);
        titulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        titulo.setTextFill(Color.web("#2c3e50"));
        titulo.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0.6, 0, 1);");

        Label mensaje = new Label(mensajeTexto);
        mensaje.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 17));
        mensaje.setTextFill(Color.web("#5d6d7e"));
        mensaje.setWrapText(true);
        mensaje.setMaxWidth(350);
        mensaje.setAlignment(Pos.CENTER);
        mensaje.setStyle("-fx-text-alignment: center; -fx-line-spacing: 3px;");

        Button aceptar = new Button("Aceptar");
        aceptar.setPrefWidth(140);
        aceptar.setPrefHeight(45);
        aceptar.setStyle("""
                    -fx-background-color: linear-gradient(to bottom, #66c2ff, #42a5f5);
                    -fx-text-fill: white;
                    -fx-font-family: 'Segoe UI';
                    -fx-font-size: 14px;
                    -fx-font-weight: bold;
                    -fx-background-radius: 25;
                    -fx-border-radius: 25;
                    -fx-cursor: hand;
                    -fx-effect: dropshadow(gaussian, rgba(66,165,245,0.3), 8, 0.5, 0, 2);
                """);

        aceptar.setOnMouseEntered(e -> {
            aceptar.setStyle("""
                        -fx-background-color: linear-gradient(to bottom, #81d4fa, #66c2ff);
                        -fx-text-fill: white;
                        -fx-font-family: 'Segoe UI';
                        -fx-font-size: 14px;
                        -fx-font-weight: bold;
                        -fx-background-radius: 25;
                        -fx-border-radius: 25;
                        -fx-cursor: hand;
                        -fx-effect: dropshadow(gaussian, rgba(66,165,245,0.4), 12, 0.6, 0, 3);
                    """);
            aceptar.setScaleX(1.05);
            aceptar.setScaleY(1.05);
        });

        aceptar.setOnMouseExited(e -> {
            aceptar.setStyle("""
                        -fx-background-color: linear-gradient(to bottom, #66c2ff, #42a5f5);
                        -fx-text-fill: white;
                        -fx-font-family: 'Segoe UI';
                        -fx-font-size: 14px;
                        -fx-font-weight: bold;
                        -fx-background-radius: 25;
                        -fx-border-radius: 25;
                        -fx-cursor: hand;
                        -fx-effect: dropshadow(gaussian, rgba(66,165,245,0.3), 8, 0.5, 0, 2);
                    """);
            aceptar.setScaleX(1.0);
            aceptar.setScaleY(1.0);
        });

        aceptar.setOnAction(e -> {
            Timeline fadeOut = new Timeline(
                    new KeyFrame(Duration.millis(250),
                            new KeyValue(alerta.getScene().getRoot().opacityProperty(), 0),
                            new KeyValue(alerta.getScene().getRoot().scaleXProperty(), 0.85),
                            new KeyValue(alerta.getScene().getRoot().scaleYProperty(), 0.85),
                            new KeyValue(alerta.getScene().getRoot().translateYProperty(), -10)
                    )
            );
            fadeOut.setOnFinished(event -> alerta.close());
            fadeOut.play();
        });

        VBox layout = new VBox(30, icono, titulo, mensaje, aceptar);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefWidth(580);
        layout.setPrefHeight(450);
        layout.setStyle("""
                    -fx-background-color: white;
                    -fx-padding: 60 50 50 50;
                    -fx-background-radius: 30;
                    -fx-border-radius: 30;
                """);

        InnerShadow sombraInterior = new InnerShadow();
        sombraInterior.setRadius(18);
        sombraInterior.setChoke(0.3);
        sombraInterior.setColor(Color.web("#ff6b6b"));

        StackPane fondoConSombra = new StackPane(layout);
        fondoConSombra.setEffect(sombraInterior);
        fondoConSombra.setPadding(new Insets(0));

        Scene scene = new Scene(fondoConSombra);
        scene.setFill(Color.TRANSPARENT);

        layout.setOpacity(0);
        layout.setScaleX(0.7);
        layout.setScaleY(0.7);
        layout.setTranslateY(20);

        alerta.setScene(scene);

        Timeline fadeIn = new Timeline(
                new KeyFrame(Duration.millis(400),
                        new KeyValue(layout.opacityProperty(), 1),
                        new KeyValue(layout.scaleXProperty(), 1),
                        new KeyValue(layout.scaleYProperty(), 1),
                        new KeyValue(layout.translateYProperty(), 0)
                )
        );

        alerta.show();
        fadeIn.play();

        Platform.runLater(() -> {
            Window ventanaPadre = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
            if (ventanaPadre != null) {
                alerta.setX(ventanaPadre.getX() + (ventanaPadre.getWidth() - alerta.getWidth()) / 2);
                alerta.setY(ventanaPadre.getY() + (ventanaPadre.getHeight() - alerta.getHeight()) / 2);
            }
        });
    }
}