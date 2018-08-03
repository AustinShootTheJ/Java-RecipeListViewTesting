package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import sample.DataModel.Recipe;
import sample.DataModel.RecipeData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {

    private List<Recipe> recipes;

    @FXML
    private ListView<Recipe> recipesListView;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private ContextMenu listContextMenu;




    public void initialize() {


        recipesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Recipe>() {
            @Override
            public void changed(ObservableValue<? extends Recipe> observable, Recipe oldValue, Recipe newValue) {
                if (newValue != null) {
                    Recipe recipe = recipesListView.getSelectionModel().getSelectedItem();

                }
            }
        });

        recipesListView.setCellFactory(new Callback<ListView<Recipe>, ListCell<Recipe>>() {
            @Override
            public ListCell<Recipe> call(ListView<Recipe> param) {
                ListCell<Recipe> cell = new ListCell<Recipe>() {
                    @Override
                    protected void updateItem(Recipe recipe, boolean empty) {
                        super.updateItem(recipe, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(recipe.getName());
                        }
                    }
                }
            }


        });

        



    }

    @FXML
    public void handleKeyPressed(KeyEvent keyEvent){
        Recipe selectedRecipe = recipesListView.getSelectionModel().getSelectedItem();
        if(selectedRecipe != null){
            if(keyEvent.getCode().equals(KeyCode.BACK_SPACE)){
                deleteItem(recipesListView.getSelectionModel().getSelectedItem());
            }
        }
    }

    public void deleteItem(Recipe recipe){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Recipe");
        alert.setHeaderText("Delete recipe: " + recipe.getName());
        alert.setContentText("Are you sure? Press OK to confirm, or cancel to back out");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && (result.get() == ButtonType.OK)){
            RecipeData.getInstance().deleteRecipe(recipe);
        }
    }

}
