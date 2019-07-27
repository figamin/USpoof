# USpoof

USpoof is a GPS spoofing bot for SuperFanU based programs.

  - Automatically fetches events and checks into them
  - Live event countdown
  - Google Maps preview of event location
  - Randomized platform, device UUID, and GPS Location
  - Support for 20 schools (suggestions are welcome)
### Supported School List
- UMass Lowell
- Drake University
- College of St. Rose
- Harvard University
- Boston University
- University of Maine
- Southeast Missouri State University
- California State University
- University of Toledo
- Lee University
- Fairfield University
- Wichita State University
- University of Kentucky
- University of North Carolina at Charlotte
- University of Pennsylvania
- University of Hawaii at Manoa
- Minot State University
- Grand Valley State University
- Keene State College
- University of North Carolina at Pembroke
### Preview
```
Logging in with username IanA57 at school ID 694...
Login Successful!
Getting feed...
Feed successfully fetched!

EVENT ID = 2KNA5
EVENT DESCRIPTION = Women's Soccer vs. UMass-Amherst | 6:00 p.m.
*Check-in to receive 5 points (starting at 5:30pm)
POINT VALUE = 5
START TIME = 2019-08-29T17:30
END TIME = 2019-08-29T20:00
LATITUDE = 42.656935056830235
LONGITUDE = -71.32413454914382
RANDOM CLOSE LATITUDE = 42.65767
RANDOM CLOSE LONGITUDE = -71.32349
EXACT LOCATION PREVIEW = https://www.google.com/maps/search/?api=1&query=42.656935056830235,-71.32413454914382
RANDOM CLOSE LOCATION PREVIEW = https://www.google.com/maps/search/?api=1&query=42.65767,-71.32349
Event starts in 33 days, 20 hours, 20 mins.
```

### Dependencies
- OkHttp for GET and POST requests
- JSON-java for parsing JSON
