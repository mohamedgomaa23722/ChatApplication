package gov.iti.Presentation.utils;

import javafx.scene.layout.HBox;
import gov.iti.Presentation.dto.Message;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class MessageItem extends HBox {
    public static final int RECIVE_MESSAGE = 0;
    public static final int SEND_MESSAGE = 1;

    public MessageItem(Message message, int status){
        //Circle imageCircle = new Circle(20);
        Label text = new Label(message.getMessage());
        this.setMargin(text, new Insets(10, 10, 0, 10));

        if (status == SEND_MESSAGE) {
            text.setStyle("-fx-background-color: #F84D43;" + "-fx-background-radius: 10;" + "-fx-border-radius: 10;" + "-fx-padding: 10 10 10 10;");
            text.setTextFill(Color.WHITE);
            this.setAlignment(Pos.CENTER_RIGHT);
            this.getChildren().add(text);

        } else {
            //this.getChildren().add(imageCircle);
            this.getChildren().add(text);
            this.setAlignment(Pos.CENTER_LEFT);
        }
    }  
}
