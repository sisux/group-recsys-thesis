Structured Data
=================

## Aim

Create a file 

### Requirement `DT-REQ01`

*MoviLens* includes the following information about items:

* movie id
* movie title
* release date
* video release date
* [IMDb](http://www.imdb.com/) URL
* Genres witch the movie is of. (e.g. Action, Adventure, Animation, etc.)

Getting the correct URL
- microformats in http://ogp.me/ns using http://any23.apache.org/index.html
- linking freebase, ...

Step 01: items data verification

There are 3 items without imdb related data: 267, 1358 and 1359.

---------

PREFIX owl: <http://www.w3.org/2002/07/owl#>
SELECT ?freebase
WHERE {
{ <http://dbpedia.org/resource/Beverly_Hills_Cop_III> owl:sameAs ?freebase . }
UNION
{ ?freebase owl:sameAs <http://dbpedia.org/resource/Beverly_Hills_Cop_III> . }
FILTER (regex(?freebase , 'linkedmdb')) .
} LIMIT 10

