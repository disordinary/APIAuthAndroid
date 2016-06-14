# APIAuthAndroid
React Native module for API_AUTH on Android.

```javascript

var APIAUTH = require("./android/apiAuth");
var access_id = 'ACCESS ID';
var secret = 'SECRET';

var apiAuth = new APIAUTH( access_id , secret );
	

var options = {
 host: 'api.endpoint.com',
 path: '/api/name/bob',
 method: 'GET',
 headers: {
   'Content-Type': 'application/json',
 }
}

apiAuth.sign_options( options, null , ( result ) => {


     fetch('http://api.endpoint.com/api/name/bob', result)
      .then( (response) => response.text() )
      .then((responseText) => { callback( null , JSON.parse( responseText  ) ); })
      .catch( (error) => { callback( error , null ) });
   });
```