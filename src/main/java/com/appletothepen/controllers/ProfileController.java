package com.appletothepen.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ProfileController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;

    public void init(String userName, String userEmail) {
        nameField.setText(userName);
        emailField.setText(userEmail);
    }
}
