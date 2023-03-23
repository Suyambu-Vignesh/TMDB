# Tmdb
***

## Features
An Unofficial Android Client for https://www.themoviedb.org/ For learning.

This App provides support for the popular movies from https://www.themoviedb.org/ and a detail infomration about the movie. Launching the 
App will show the endless popular movies.

### Show Info Screen
Shows a paginated results of popular movie. The Tile of the Movie Info collection will provide
  * Image of the Show
  * Show Original title
  * Show popularity
  * Release date

Tapping this Show brief Will give detail information about the show

### Show Detail Page
Can learn about the detail info of show like
  * Language
  * Genre
  * Running time
  * Short story
  * Link to IMDB 
  * Link to Home page

## Architecture 
***

This App supports the latest MVVM Clean with Hilt. App is structured around the pages with an understanding when roadmap become bigger the app can become collection of page as Module. Where the common component are moved to shared folder, latter we can move its own repo.

This App Uses
  * MVVM Clean (View <> ViewModel <> UseCase <> Repo <> Data Source) (to keep the view model more clean and lean)
  * Hilt for Initialization with proper scope
  * Theme supported attr. To support faster re-skinning of Apps
  * Flow and State Flow to control the Flow of data as unidirectional. Keep the Usecase and beyond free from android packages

https://user-images.githubusercontent.com/8298720/227378546-744fa438-e8ee-4f4c-a339-a0c4bbd9252e.png


## Android Studio IDE setup
***

Gradle version : 7.4
Android Studio : Dolphin and Above

Please disable gradle offline and do a gradle sync.

## ScreenShots
***

<img src="https://user-images.githubusercontent.com/8298720/226824854-aa916ef8-3783-428a-910b-a27b7cc1fc7c.png" alt="alt text" width="629" height="136">
