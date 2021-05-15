# VaccineTrackerBot

Request doctolib search page to get vaccine offices around a location. 

Open a browser page if vaccine slots are available before two days

### Options

```
Usage: VaccineTrackerBot [options]
  Options:
  * -location
      City where you live. Example : "Paris"
    -cities
      Comma-separated list of cities eligible. If this parameter is not set 
      all cities are eligible. Example: "Paris,Antony"
      Default: []
    -period
      Period to check doctolib (in second). Example: 60
      Default: 60
```

### Run as executable

Download (from release tab) and install VaccineTrackerBot-1.0.exe

Installation folder is "C:\Program Files\VaccineTrackerBot"

Then run this command in a console:

`VaccineTrackerBot.exe -location "Paris"`

### Run as a jar

Build the jar, it can be executed with this console command :

`java -jar VaccineTrackerBot-1.0.jar -location "Paris"`
