## This service is used to retrieve the count of "common name" for all the species of trees in given search radius

#### Endpoint of the service-
`http://${server}:8080/treecount-service/latitude/{latitude}/longitude/{longitude}/radius/{radius}`

whereas latitude, longitude and radius are the path parameters and have to be provided by the user

Input
 - Street tree data has been feteched by direct API call to https://data.cityofnewyork.us/resource/nwxe-4ae8.json`

Output
 - Expected outcome from the api
```json
{
    "red maple": 30,
    "American linden": 1,
    "London planetree": 3
}
```
