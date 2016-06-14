# APIAuthAndroid
React Native module for the api_auth HMAC authentication system as specified by [mgomes](https://github.com/mgomes/)

A rails server is located here: (https://github.com/mgomes/api_auth)
A nodejs server is located here: (https://github.com/disordinary/node_api_auth_server)

```javascript

var APIAuth = require("./android/apiAuth");
var access_id = 'ACCESS ID';
var secret = 'SECRET';

var apiAuth = new APIAuth( access_id , secret );
	

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