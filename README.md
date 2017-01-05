# Quake Finder

This is a simple app that displays a list of earthquakes from https://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php. Upon clicking on an earthquake in the list the user is directed to the USGS website which shows detailed information about the chosen earthquake. The user is required to swipe down to refresh the data (until a SyncAdapter is implemented).

## Design
### main
* The project is split into three packages: `data`, `sync` and `ui`
* `ui` contains `MainActivity`, `QuakeAdapter`, which is the adapter used in the `RecyclerView` and `VisibilityToggle`, which toggles visibility for the data, progress bar and error message, in case there is no internet connection
* `sync` contains `QuakeSyncTask`, which is an `AsyncTask` and has supporting classes `JsonUtil` and `UrlUtil`, which were completed using TDD
* `data` contains `Earthquake`, which contains the necessary data to be displayed

### androidTest
* `sync` contains `JsonUtilTest` and `UrlUtilTest`

## Future Ideas
### Business
* Open up a detail view upon clicking on an earthquake, which shows more detailed information
* All the user to go to the website for more information but also open up a map showing the exact location of the earthquake
* Get the users current location and issue out a notification alert if there is an earthquake nearby
* Local settings and other languages could be accounted for
* Show earthquakes for a specific region
* Show the time of the earthquake occurrence

### Technical
* The data could be persisted using an SQLite database and managed with a content provider. This would replace `Earthquake`.
* `QuakeSyncTask` could be replaced with a `SyncAdapter` and refreshed automatically at regular intervals
