Camel Java Router Project
=========================

Sample Apache Camel integration showing a twitter search that transforms results into JSON and 
routes them to CouchDB instance.

Requires an instance of CouchDB and creation of Twitter application via:
https://dev.twitter.com/apps/ then obtaining the security credentials for API access and using them
here.

Set Twitter and CouchDB configuration in applications.properties, eg:
search.type=polling
search.delay=60
search.keywords=camel
consumerKey=<obtain from https://dev.twitter.com/apps/>
consumerSecret=<obtain from https://dev.twitter.com/apps/>
accessToken=<obtain from https://dev.twitter.com/apps/>
accessTokenSecret=<obtain from https://dev.twitter.com/apps/>
url=couchdb:http://localhost/YourDB

------------------------------
To build this project use

    mvn install

To run this project from within Maven use

    mvn exec:java

For more help see the Apache Camel documentation

    http://camel.apache.org/

