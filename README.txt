The application contains the dummy app for adding / updating shops. With support of google maps api for 
updating the geo cordinates of the shops by given address. 
Following is the syntax for the using it via a REST client

Adding new shop data :

uri : /shops
Content-Type: application/json

Sample json:
{
  "name": "shop_name",
  "address": {
    "number": "99",
    "postcode": "12345"
  }
}

The response shall contain the longitude, latitude and version 
Response : 

{
  "name": "shop_name",
  "address": {
    "longitude": -80.71153199999999,
    "latitude": 41.377078,
    "number": 88,
    "postcode": 3000
  },
  "version": 1
}

Updating shop data (shop name is used as key):

uri : /shops
Sample json:
{
  "name": "shop_name",
  "address": {
    "number": "88",
    "postcode": "3000"
  }
}

The response shall contain the updated informatoion of the shop
Response : 

Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked


{
  "name": "shop_name",
  "address": {
    "longitude": 46.634409,
    "latitude": 24.7210991,
    "number": 55,
    "postcode": 12348
  },
  "version": 2
}

The tests can be executed via Gradle. The syntax for test execution and report generation: 
./gradlew test

and generated reports can be found in 
{$project-folde}\build\reports\tests\test\index.html

Further improvements :


Validation on different fields
Handling address with more details
Unsupported formats responses

