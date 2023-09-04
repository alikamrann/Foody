package ir.world.number.foody.util

class Constans {

    companion object{
        const val BASE_URL="https://api.spoonacular.com"
        const val BASE_IMAGE_URL ="https://spoonacular.com/cdn/ingredients_100x100/"
         const val API_KEY="566e2ff1021a4e2aa54b41f6cd62503e"

        const val QUERY_SEARCH="query"

        const val RECIPE_RESULT_KEY = "recipeBundle"

        const val QUERY_NUMBER="number"
        const val QUERY_API_KEY="apiKey"
        const val QUERY_TYPE="type"
        const val QUERY_DIET="diet"
        const val QUERY_ADD_RECIPE_INFORMATION="addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS="fillIngredients"

        //ROOM Database
        const val DATABASE_NAME="recipes_database"
        const val RECIPES_TABLE="recipes_table"
        const val FAVORITE_RECIPES_TABLE="favorite_recipes_table"

        //Bottom Sheet and Preferences
        const val DEFAULT_RECIPES_NUMBER="50"
        const val DEFAULT_MEAL_TYPE ="main course"
        const val DEFAULT_DIET_TYPE ="gluten free"

        const val PREFERENCE_MAME="foody_preferences"
        const val PREFERENCE_MEAL_TYPE ="mealType"
        const val PREFERENCE_MEAL_TYPE_ID ="mealTypeId"
        const val PREFERENCE_DIET_TYPE ="dietType"
        const val PREFERENCE_DIET_TYPE_ID ="dietTypeId"
        const val PREFERENCE_BACK_ONLINE ="backOnline"

        const val FOOD_JOKE_TABLE ="food_joke_table"
    }
}