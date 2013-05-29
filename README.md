camelTwitterSinkDb
==================

Sample Apache Camel project showing consuming of twitter messages into a NoSQL DB

The integration is showing a twitter search that transforms results into JSON and 
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
