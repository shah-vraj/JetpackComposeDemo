# Jetpack Compose Demo
This repository is an demo application for Jetpack Compose. Jetpack Compose is a modern UI toolkit for building native Android apps using Kotlin.
It's part of the Jetpack library suite provided by Google, aimed at making Android app development faster, easier, and more enjoyable. With Jetpack Compose,
developers can create UI components using a declarative approach, similar to other modern UI frameworks like React and SwiftUI.

## How to run project
1. If using Android Studio, File -> New -> Project from version control.<br>
   Link: https://github.com/shah-vraj/OnlineLearning.git<br>
   Otherwise, git clone https://github.com/shah-vraj/OnlineLearning.git
2. Sync gradle files
3. Build project
4. Run project

## Project Structure
- `data`
    - `local`
        - `dao`<br>
          All dao files of the database, e.g. UsersDao. Dao's are basically interfaces that defines all operations we want to do on the database while working with room database library
        - `entity`<br>
          All entity files of the database, e.g. UserEntity. Entity represent a table row in a database
        - `repository`<br>
          All repository files for the project. Repository files are bridges between the data layer and UI layer. They provide high level overview of what functionalities are provided by the data layer.
    - `model`<br>
      All model class that holds data.
- `ui`
    - Individual module packages e.g. authentication, home, profile, etc.
    - Base classes package which includes all base classes such as BaseActivity, BaseButton, BaseTextField, etc.
    - Theme package which includes
        - Color - Define all colors used in the project
        - Shape - Define all shapes used in the project
        - Theme - Define the theme of the appication
        - Type - Define all fonts and its typography used in the applicaiton
- `util`<br>
  All util classes reside here. Util files include extensions file, helper classes and different types which helps separate reusable code from main code. We can group similar file in separate package here for example, extensions, exceptions, etc.
- `viewmodel`<br>
  All view model files for the project.<br>

## Custom widgets created
### Circular progress indicator
|                                                                              Start                                                                               |                                                                             Mid                                                                              |                                                                             End                                                                              |
|:----------------------------------------------------------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| <img src="https://github.com/shah-vraj/JetpackComposeDemo/raw/master/media/ProgressCircleStart.png" width=120 height=120 title="Sample circular progress start"> | <img src="https://github.com/shah-vraj/JetpackComposeDemo/raw/master/media/ProgressCircleMid.png" width=120 height=120 title="Sample circular progress mid"> | <img src="https://github.com/shah-vraj/JetpackComposeDemo/raw/master/media/ProgressCircleEnd.png" width=120 height=120 title="Sample circular progress end"> |

### Base button
![alt text](https://github.com/shah-vraj/JetpackComposeDemo/raw/master/media/BaseButton.png "Sample base button")

### Base left image button
![alt text](https://github.com/shah-vraj/JetpackComposeDemo/raw/master/media/LeftImageButton.png "Sample base left image button")

### Base text field
![alt text](https://github.com/shah-vraj/JetpackComposeDemo/raw/master/media/BaseTextField.png "Sample base text field")

### Base search view
![alt text](https://github.com/shah-vraj/JetpackComposeDemo/raw/master/media/BaseSearchView.png "Sample base search view")

### Base linear progress indicator
![alt text](https://github.com/shah-vraj/JetpackComposeDemo/raw/master/media/BaseLinearProgressIndicator.png "Sample base linear progress indicator")

### Bottom navigation
![alt text](https://github.com/shah-vraj/JetpackComposeDemo/raw/master/media/BottomNavigation.gif "Sample bottom navigation")

# Thanks
Any suggestions or improvement points are always welcomed :)<br>
Peace ✌🏻️
> Code is like humor. When you have to explain it, it’s bad. <br>– Cory House