package sample.DataModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class RecipeData {

    private static RecipeData instance = new RecipeData();
    private static String filename = "Recipes.txt";
    private ObservableList<Recipe> recipes = FXCollections.observableArrayList();

    public static RecipeData getInstance(){
        return instance;
    }

    public void addRecipes(Recipe recipe){
        recipes.add(recipe);
    }

    public ObservableList<Recipe> getRecipes() {
        return recipes;
    }

    public void loadRecipes() throws IOException{
        recipes = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try{
          while((input = br.readLine()) != null){
              List<String> tempList = new ArrayList<>();
              String [] pieces = input.split(",");
              String name = pieces[0];
              for(String ingredient : pieces){
                  if(ingredient == pieces[0]){
                      continue;
                  }
                  tempList.add(ingredient);
              }
              Recipe recipe = new Recipe(name,tempList);
              System.out.println(recipe);
              recipes.add(recipe);
          }

        }finally{
            if (br != null){
            br.close();
        }
        }

    }


    public void storeRecipes()throws IOException{
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);

        try{
            Iterator<Recipe> iterator = recipes.iterator();
            while(iterator.hasNext()){
                Recipe recipe = iterator.next();
                bw.write(recipe.getName() + "," + recipe.getIngredients() );
                bw.newLine();
            }

        }finally{
            if(bw != null){
                bw.close();
            }
        }
    }

    public void deleteRecipe(Recipe recipe){
        recipes.remove(recipe);
    }

}
