package com.appletothepen.views;

import com.appletothepen.ui.Card;
import com.appletothepen.ui.PageHeader;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProfileView extends VBox {

    public ProfileView(String userName, String userEmail) {
        getStyleClass().add("view-root");
        getChildren().add(new PageHeader("Profile"));

        Label avatar = new Label("AVATAR");
        avatar.getStyleClass().add("avatar-placeholder");

        VBox nameField = fieldBlock("Full Name", userName);
        VBox emailField = fieldBlock("Email", userEmail);

        HBox identityRow = new HBox(16, avatar, new VBox(10, nameField, emailField));
        identityRow.setAlignment(Pos.CENTER_LEFT);

        Card card = new Card("Account Details", identityRow);

        VBox contentWrap = new VBox(card);
        contentWrap.setPadding(new Insets(20));
        getChildren().add(contentWrap);
    }

    private VBox fieldBlock(String label, String value) {
        Label captionLabel = new Label(label);
        captionLabel.getStyleClass().add("field-label");
        TextField field = new TextField(value);
        field.setPrefWidth(280);
        return new VBox(4, captionLabel, field);
    }
}
