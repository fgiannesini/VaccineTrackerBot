# VaccineTrackerBot

Request doctolib search page to get vaccine offices around a location. Open a browser page if vaccine slots are open
before two days

### Configuration

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

### Example

```
java -jar VaccineTrackerBot-1.0.jar 
      -location "Paris" 
      -period 20 
      -cities "Paris,Suresnes,Nanterre,Puteaux,Saint-Cloud,Neuilly-sur-Seine"
```