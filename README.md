# Scrummy Meal Planner

## Introduction
Meal planning web application that allows users to:
- Search for recipes using the Spoonacular external API,
- Enter custom recipes,
- Add recipes to meal plans, and
- Generate grocery lists from meal plans.

---
## Architecture
---
### Server
Java server uses:  
- Spring MVC,
- Spring JdbcTemplate, 
- A PostgreSQL database, and
- Spring RestTemplate to access the Spoonacular external API.

---
### Client
Vue client uses:  
- HTML, 
- CSS,
- JavaScript, and
- Axios.

---
## Setup

### Server

#### Database
- Create a PostgreSQL database, 
- Using the SQL files from the database directory of this project:
    - Run 1_schema.sql to create the database objects, 
    - Run 2_weekday_mealtype_data.sql to populate the meal_type and weekday tables, 
    - Create 3_user.sql, based on 3_Example_user.sql with updated user name and password, and run it.

#### Spoonacular
- Register for an API key from Spoonacular.com.

#### Application Properties
- Create an application.properties file in the resources folder, based on Example_application.properties.
- Update the datasource connection properties based on the created database and database user.
- Update recipe.api.key with a Spoonacular API key.

### Client
- In a terminal window, from the VUE directory of the project, run: 
```
    npm install
```
## Usage
- Before starting the Client, run the Java Application to start the API.
- To start the Client open the VUE directory of the project in a terminal and run: 
```
    npm run serve
```
- Open the Client application at http://localhost:8081/.
- Add an account, if needed, and login.

















