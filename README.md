Spring boot application used to serve lyrics to a web application where users can listen to their spotify songs while reading their lyrics.

The appkication uses Spring boot and Sring MVC to build a web server that is attached to a Postgres database instance to store lyrics records.
JPA Hibernate was used for the applicaiton to communicate with the database and take care of the mappings between Java Pojos and databse records.
A single endpoint is exposed to serve lyrics information about a song from a specific artist, the applicaiton tries to serve the information by first tring to fetch any exisiting records of the information requested. If there is no records of such song, a web scraper is launced to look for lyrics over the web by going to genius.com website with the specified artist and song provided from the client.
The web sracper does a a few cleaning from the request parameters in order to best get a matching url for the song, however because some songs and artists names do not result in the url that has the lyrics for it, it is not always a successful retrieval of the lyrics. 

test
