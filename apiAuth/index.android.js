
 var { NativeModules } = require('react-native');
var API_AUTH = NativeModules.APIAUTH;


 var moment = require('moment');



var auth = function(access_id, secret) {
   this.access_id = access_id;
   this.secret = secret;

   this.sign_options = function(options, content_body , cb) {

     var content_type = options.headers['Content-Type'];
     if (isEmpty(content_type))
     {
       // Default to json
       content_type = options.headers['Content-Type'] = 'application/json';
     }

     var path = options.path;
     if (isEmpty(path))
     {
       // Default to the host's root
       path = options.path = '/'
     }

     var date = moment().utc().format('ddd, DD MMM YYYY HH:mm:ss') + ' GMT';
     date = "Sat, 27 Feb 2016 04:39:54 GMT";
     API_AUTH.md5( content_body , ( error , md5_hash ) => {
       var content_md5 = md5_hash.trim();
       var canonical_string = [content_type, content_md5, path, date].join();

      API_AUTH.hmac( this.secret , canonical_string , ( error , result ) => {

        var auth_header_value = 'APIAuth ' + this.access_id + ':' + result.trim();
        options.headers['Content-MD5'] = content_md5;
        options.headers['DATE'] = date;
        options.headers['Authorization'] = auth_header_value;
        options.headers["DEBUGSTRING"] = canonical_string;

        cb( options );
      } );




    } );

    //console.log( NativeModules );


   };

   return this;
 };

 function isEmpty(value)
 {
   return value === null || value === '' || typeof value === 'undefined';
 }

module.exports = auth;
