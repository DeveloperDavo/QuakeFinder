# Quake Finder

This is a simple app that displays a list of earthquakes from https://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php. 
The user is able to choose between "all" or "significant" earthquakes that have happened in the past day. 
After clicking an item in the list the user is re-directed to the USGS website which shows detailed information about the chosen earthquake. 

## Screenshot
<img src="screenshots/Screenshot_2017-01-05-17-58-26.png" width="400">

## Technical Design
* The project is split into three packages: `data`, `sync` and `ui`
* The app has one activity called `MainActivity` which uses `QuakeSyncTask` to connect to the server and a `RecyclerView` with `QuakeAdapter` to display the data
* The url building and json parsing are completed in `UrlUtil` and `JsonUtil`, which were developed using TDD
* The data that is displayed is contained in `Earthquake`
* `VisibilityToggle` toggles the visibility of the views in the `MainActivity`. These include the views for the data, progress bar and message, which is displayed if there are no earthquakes or there is an error.
* The data is either refreshed by swiping down from the top or clicking an option in the spinner

## Potential Improvements
### Non-Technical
* Open up a detail view upon clicking on an item in the list, which shows more detailed information
* Show the user a map of the exact location of the earthquake
* Allow the user to search for a specific region
* Have a settings screen that allows the user to choose the severity and timeframe of the earthquake data
* Add an app icon

### Technical
* The data could be persisted using an SQLite database and managed with a content provider. This would replace `Earthquake`.
* `QuakeSyncTask` could be replaced with a `SyncAdapter` and refreshed automatically at regular intervals
