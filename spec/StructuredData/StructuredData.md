Structured Data
=================

##Data Requirements

The data needed for our study in *Group Recommender Systems* requires to cover the following issues:

* `DT-REQ01` To be fully structured items - in order to set a baseline of *Content-Based Recommender System*
* `DT-REQ02` To have real end users ratings - in order to evaluate our expected results with the actual ones and also, be able to use a *Collaborative Recommender System*
* `DT-OPT01` To have users demographical information (OPTIONAL) - in order to extend the recommender system with a *Demographic Recommender System* module.

##Movilens Dataset

MovieLens data sets were collected by the GroupLens Research Project
at the University of Minnesota.

The Internet Movie Database lists details of millions of movies, tv shows, etc. with many detatils such as the director, the cast and genre for cinema fans. 
Being a vast database, it presents a good opportunity to study and experiment Semantic Web concepts; also, developed tools may be useful for many people


### Requirement `DT-REQ02`

The [*MoviLens* dataset](http://presspot.cs.umn.edu/datasets/movielens/) already offers a solution for requirement `DT-REQ02`, provinding three subsets:

* *100k*: 100,000 ratings from 1000 users on 1700 movies.
* *1M*: 1 million ratings from 6000 users on 4000 movies.
* *10M*: 10 million ratings and 100,000 tag applications applied to 10,000 movies by 72,000 users.


### Requirement `DT-REQ01`

*MoviLens* includes the following information about items:

* movie id
* movie title
* release date
* video release date
* [IMDb](http://www.imdb.com/) URL
* Genres witch the movie is of. (e.g. Action, Adventure, Animation, etc.)

However, our *Recommender System* needs to operate from any ontology. Instead of creating an ontology from scratch,
we have taken advatage of the *Linked Data* movement in Semantic Web.

-------------FROM: Linked Data: Evolving the Web into a Global Data Space
Tom Heath , Talis 
Christian Bizer , Freie Universität Berlin

The term Linked Data refers to a set of best practices for publishing and interlinking structured data on the Web. These best practices were introduced by Tim Berners-Lee in his Web architecture note Linked Data [16] and have become known as the Linked Data principles. These principles are the following:

Use URIs as names for things.
Use HTTP URIs, so that people can look up those names.
When someone looks up a URI, provide useful information, using the standards (RDF, SPARQL).
Include links to other URIs, so that they can discover more things.
The basic idea of Linked Data is to apply the general architecture of the World Wide Web [67] to the task of sharing structured data on global scale


-------------------

Currently,
the system relies on one of the most relevant datasets in the Linked Data [3]
cloud: DBpedia [4], and on the semantic-enabled version of the Internet Movie Database (IMDB): LinkedMDB [11].

model-based recommender
system leveraging the datasets publicly available in
the Linked Open Data (LOD) cloud as DBpedia and Linked-
MDB


There are several advantages in the use of semantic data in recommendation
tasks spanning from a pure knowledge-based perspective (e.g., we may have a richer representation of data) to
more practical points of view such as the easy adaptation of the same approach to different domains. In the recent
years, thanks to the Web of Data advance, we are assisting to a ourishing of semantic datasets freely available on the
Web encoding machine-understandable RDF triples related to different domains and sometimes representing different
points of view on the same domain. All this information can be exploited to model items and user profiles in an 
LOD-enabled content-based recommender system where the domain knowledge plays a fundamental role. One of the main 
components of these systems is represented by the Content Analyzer (CA).

This module is responsible for the preprocessing of the information usually coming from textual
sources and for extracting keywords used to model both the items and the user profile. The use of the Web of Data can contribute to reduce the effort associated to the definition of the Content Analyzer. In fact, in order to retrieve and use data related to a specific domain of interest, the LOD-based
CA has just to formulate SPARQL1 queries. Moreover, as resources in LOD datasets are identified by unique URIs and
are semantically interlinked with each other, the problems related to a keyword-based approach such as synonymy and polysemy [8] are automatically solved.

### Optional Requirement `DT-OPT01`

Demographic information about the users includes: 

* user id
* age
* gender
* occupation
* zip code
