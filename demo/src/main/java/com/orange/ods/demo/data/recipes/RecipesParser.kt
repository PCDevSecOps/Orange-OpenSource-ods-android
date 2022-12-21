/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.demo.data.recipes

import com.orange.ods.demo.domain.recipes.Ingredient
import com.orange.ods.demo.domain.recipes.Recipe
import org.json.JSONArray
import org.json.JSONObject

class RecipesParser {

    companion object {

        private const val Title = "title"
        private const val Subtitle = "subtitle"
        private const val Description = "description"
        private const val Url = "url"
        private const val IconName = "iconName"
        private const val Ingredients = "ingredients"
        private const val Quantity = "quantity"
        private const val Name = "name"
        private const val Image = "image"
    }

    @Throws
    fun parseRecipes(jsonString: String): List<Recipe> {
        val jsonRecipes = JSONArray(jsonString)

        return List(jsonRecipes.length()) { parseRecipe(jsonRecipes.getJSONObject(it)) }
    }

    private fun parseRecipe(jsonRecipe: JSONObject): Recipe {
        val title = jsonRecipe.getString(Title)
        val subtitle = jsonRecipe.getString(Subtitle)
        val description = jsonRecipe.getString(Description)
        val url = jsonRecipe.getString(Url)
        val iconName = jsonRecipe.getString(IconName)
        val jsonIngredients = jsonRecipe.getJSONArray(Ingredients)
        val ingredients = List(jsonIngredients.length()) { parseIngredient(jsonIngredients.getJSONObject(it)) }

        return Recipe(title, subtitle, ingredients, description, url, iconName)
    }

    private fun parseIngredient(jsonIngredient: JSONObject): Ingredient {
        val quantity = jsonIngredient.getString(Quantity)
        val name = jsonIngredient.getString(Name)
        val image = jsonIngredient.getString(Image)

        return Ingredient(quantity, name, image)
    }
}
