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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicBoolean;

public class AlertaConfirmacion {

    public static boolean mostrar(String tituloTexto, String mensajeTexto) {
        Stage alerta = new Stage();
        alerta.initModality(Modality.APPLICATION_MODAL);
        alerta.initStyle(StageStyle.TRANSPARENT);

        AtomicBoolean decision = new AtomicBoolean(false);

        Polygon triangulo = new Polygon();
        triangulo.getPoints().addAll(
                0.0, -50.0,
                43.3, 30.0,
                -43.3, 30.0
        );

        triangulo.setFill(Color.TRANSPARENT);
        triangulo.setStroke(Color.web("#FBC02D"));
        triangulo.setStrokeWidth(5);
        triangulo.setStyle("-fx-effect: dropshadow(gaussian, rgba(251,192,45,0.4), 20, 0.5, 0, 0);");


        Line palo = new Line(0, -25, 0, 5);
        palo.setStroke(Color.web("#FBC02D"));
        palo.setStrokeWidth(5);
        palo.setStrokeLineCap(StrokeLineCap.ROUND);

        Circle punto = new Circle(4);
        punto.setFill(Color.web("#FBC02D"));
        punto.setTranslateY(25);

        StackPane icono = new StackPane(triangulo, palo, punto);

        Label titulo = new Label(tituloTexto);
        titulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        titulo.setTextFill(Color.web("#2c3e50"));
        titulo.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0.6, 0, 1);");

        Label mensaje = new Label(mensajeTexto);
        mensaje.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 17));
        mensaje.setTextFill(Color.web("#5d6d7e"));
        mensaje.setWrapText(true);
        mensaje.setMaxWidth(350);
        mensaje.setAlignment(Pos.CENTER);
        mensaje.setStyle("-fx-text-alignment: center; -fx-line-spacing: 3px;");

        Button confirmar = new Button("Confirmar");
        confirmar.setPrefWidth(140);
        confirmar.setPrefHeight(45);
        confirmar.setStyle("""
                    -fx-background-color: linear-gradient(to bottom, #66bb6a, #4caf50);
                    -fx-text-fill: white;
                    -fx-font-family: 'Segoe UI';
                    -fx-font-size: 14px;
                    -fx-font-weight: bold;
                    -fx-background-radius: 25;
                    -fx-border-radius: 25;
                    -fx-cursor: hand;
                    -fx-effect: dropshadow(gaussian, rgba(76,175,80,0.3), 8, 0.5, 0, 2);
                """);

        confirmar.setOnMouseEntered(e -> {
            confirmar.setStyle("""
                        -fx-background-color: linear-gradient(to bottom, #81c784, #66bb6a);
                        -fx-text-fill: white;
                        -fx-font-family: 'Segoe UI';
                        -fx-font-size: 14px;
                        -fx-font-weight: bold;
                        -fx-background-radius: 25;
                        -fx-border-radius: 25;
                        -fx-cursor: hand;
                        -fx-effect: dropshadow(gaussian, rgba(76,175,80,0.4), 12, 0.6, 0, 3);
                    """);
            confirmar.setScaleX(1.05);
            confirmar.setScaleY(1.05);
        });

        confirmar.setOnMouseExited(e -> {
            confirmar.setStyle("""
                        -fx-background-color: linear-gradient(to bottom, #66bb6a, #4caf50);
                        -fx-text-fill: white;
                        -fx-font-family: 'Segoe UI';
                        -fx-font-size: 14px;
                        -fx-font-weight: bold;
                        -fx-background-radius: 25;
                        -fx-border-radius: 25;
                        -fx-cursor: hand;
                        -fx-effect: dropshadow(gaussian, rgba(76,175,80,0.3), 8, 0.5, 0, 2);
                    """);
            confirmar.setScaleX(1.0);
            confirmar.setScaleY(1.0);
        });

        Button cancelar = new Button("Cancelar");
        cancelar.setPrefWidth(140);
        cancelar.setPrefHeight(45);
        cancelar.setStyle("""
                    -fx-background-color: linear-gradient(to bottom, #f5f5f5, #e0e0e0);
                    -fx-text-fill: #555;
                    -fx-font-family: 'Segoe UI';
                    -fx-font-size: 14px;
                    -fx-font-weight: bold;
                    -fx-background-radius: 25;
                    -fx-border-radius: 25;
                    -fx-cursor: hand;
                    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0.4, 0, 2);
                """);

        cancelar.setOnMouseEntered(e -> {
            cancelar.setStyle("""
                        -fx-background-color: linear-gradient(to bottom, #ffffff, #f5f5f5);
                        -fx-text-fill: #333;
                        -fx-font-family: 'Segoe UI';
                        -fx-font-size: 14px;
                        -fx-font-weight: bold;
                        -fx-background-radius: 25;
                        -fx-border-radius: 25;
                        -fx-cursor: hand;
                        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 8, 0.5, 0, 3);
                    """);
            cancelar.setScaleX(1.05);
            cancelar.setScaleY(1.05);
        });

        cancelar.setOnMouseExited(e -> {
            cancelar.setStyle("""
                        -fx-background-color: linear-gradient(to bottom, #f5f5f5, #e0e0e0);
                        -fx-text-fill: #555;
                        -fx-font-family: 'Segoe UI';
                        -fx-font-size: 14px;
                        -fx-font-weight: bold;
                        -fx-background-radius: 25;
                        -fx-border-radius: 25;
                        -fx-cursor: hand;
                        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0.4, 0, 2);
                    """);
            cancelar.setScaleX(1.0);
            cancelar.setScaleY(1.0);
        });

        confirmar.setOnAction(e -> {
            decision.set(true);
            cerrar(alerta);
        });

        cancelar.setOnAction(e -> {
            decision.set(false);
            cerrar(alerta);
        });

        HBox botones = new HBox(25, confirmar, cancelar);
        botones.setAlignment(Pos.CENTER);

        VBox layout = new VBox(30, icono, titulo, mensaje, botones);
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
        sombraInterior.setColor(Color.web("#FBC02D"));

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

        fadeIn.play();

        Platform.runLater(() -> {
            Window ventanaPadre = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
            if (ventanaPadre != null) {
                alerta.setX(ventanaPadre.getX() + (ventanaPadre.getWidth() - alerta.getWidth()) / 2);
                alerta.setY(ventanaPadre.getY() + (ventanaPadre.getHeight() - alerta.getHeight()) / 2);
            }
        });

        alerta.showAndWait();

        return decision.get();
    }

    private static void cerrar(Stage alerta) {
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
    }
}
