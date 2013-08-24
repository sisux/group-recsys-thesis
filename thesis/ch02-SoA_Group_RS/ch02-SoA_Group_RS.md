State-of-the-Art in Group Recommender Systems
==============================================

TOBE INDEX: (copy from: Jameson, A., & Smyth, B. (2007). Recommendation to Groups. The Adaptive Web: Methods and Strategies of Web Personalization, 4321, 596–627.

		1 Introduction
			1.1 Existing Group Recommenders
		2 Acquiring Information About Group Members’ Preferences
		3 Generating Recommendations
			3.1 Approaches to Preference Aggregation
			3.2 Alternative Goals and Procedures for Aggregation
			3.3 Further Complications Concerning Preference Aggregation
			3.4 Preference Specifications That Reflect More Than Personal Taste
		4 Explaining Recommendations
		5 Helping Group Members to Achieve Consensus
		6 General Conclusions
			6.1 Conclusions Concerning Group Recommender systems




---------------
[Cantador, I., & Castells, P. (2012). Group Recommender Systems: New Perspectives in the Social Web. In J. J. P. Arias, A. F. Vilas, & R. P. D. Redondo (Eds.), Recommender Systems for the Social Web. Springer Verlag, Intelligent Systems Reference Library, Vol. 32.]

Different degrees of group existence can be considered, from sets of people that meet, interact, or have some actual common bond in the physical world, to online contacts that have no relation outside the system, to latent groups of users that are not even directly aware of each other. The new perspectives bring an opportunity to creatively con- ceive new views and roles for groups in social environments, and perhaps a new angle on the traditional tension between the individual and the group.

The groups may be quite heterogeneous, in terms of age, gender, intelligence and personality influence on the perception and complacency with the system outputs each member of the groups may have. Thus, a major question that arises is how a recommender system can adapt itself to a group of users, in such a way that each individual enjoys and benefits from the results.


--------------

Jameson, A., & Smyth, B. (2007). Recommendation to Groups. The Adaptive Web: Methods and Strategies of Web Personalization, 4321, 596–627.

This chapter discusses the most important new issues that arise, organizing them in terms of four subtasks that can or must be dealt with by a group recommender:
1. acquiring information about the user’s preferences
2. generating recommendations
3. explaining recommendations
4. helping users to settle on a final decision

---------------------------

Fases:

---------
Mccarthy, K., Smyth, B., & Salamó, M. (2012). Generating recommendations for consensus negotiation in group personalization services. Personal Ubiquitous Comput., 16(5), 597–610. doi:10.1007/s00779-011-0413-1

1._Preference elicitation_ refers to the manner in which information is acquired from users, 
and in many cases, methods similar to those used in single-user recommender systems are applied.

For example, preferences may be acquired by asking users directly (_explicit preference elicitation_) or 
by inferring their preferences from their actions and feedback (_implicit preference elicitation_). 

Most of the research on group recommendation investigated the core algorithms used for recommendation generation.
Two different strategies have been mostly used for generating group recommendations: 
aggregating individual predictions into group predictions (aggregated predictions) 
or aggregating individual models into group models (aggregated models). 

Differences among these strategies differ in the timing of data aggregation step. 
The aggregated predictions strategy [4, 27] generated individual predictions based on individual preference models 
and then aggregates the individual predictions into a group prediction. 

The alternative strategy is to construct a group preference model prior to any item recommendation taking place [42].

The aggregated models strategy merges individual user models into a group-based model and then generates recommendations 
using the aggregated group model [31]. 

For example, group knowledge management (GMK) [9] proposes a generic framework for management in context-aware 
group applications and services that extracts group knowledge regarding the involved users. 
The final two subtasks (presenting and explaining recommendations and helping group members to reach consensus) have received less attention from researchers.

-----


Most well-known strategies used in collaborative filtering algorithms for recommendation generation.

1. The Least misery strategy, as defined in Eq. 13, choosesa product pi based on the minimum satisfaction of the individual preferences

The rationale is that a group is as satisfied as its least satisfied member. 
--veure formula

2. Most pleasure strategy selects the maximum satisfaction of the individual preferences, see Eq. 14. 
It considers that at least one member will be maximally satisfied.
--veure formula

Previous strategies assume the consensus based on the
satisfaction of one individual: the least or the most satisfied. There is no warranty that the recommendations will suit the whole group.

3. Multiplicative strategy multiplies the satisfaction of the individual users, see Eq. 15. 
With this strategy, it might happen that a member with unique tastes always lose out because their opinion happens to be a minority preference.

-----------------------------------------------7
Garcia, I., Pajares, S., Sebastia, L., & Onaindia, E. (2012). Preference elicitation techniques for group recommender systems. Inf. Sci., 189, 155–175. doi:10.1016/j.ins.2011.11.037

1. Average is a mechanism that aggregates the users’ preferences and rates each preference as the average
 value of the ratings given by the users to the preference
 
2. Average without Misery only assigns numerical ratings to the preferences that are shared by all the members
 in the group but without those individual preferences that score below a certain threshold.
 

-----------------------------------------------

Cantador, I., & Castells, P. (2012). Group Recommender Systems: New Perspectives in the Social Web. In J. J. P. Arias, A. F. Vilas, & R. P. D. Redondo (Eds.), Recommender Systems for the Social Web. Springer Verlag, Intelligent Systems Reference Library, Vol. 32.

3 Group Recommender Systems

As stated by several authors [4, 7, 27], group recommender systems can be classified into two main categories: 
aggregated models, which aggregate individual user data into a group data, 
and generate predictions based on the group data; and aggregated predictions, which aggregate the predictions for individual users into group predictions.

Other authors [10] have considered the way in which individual preferences are obtained (by content-based or collaborative filtering)
 as an additional dimension to be taken into account in such categorisation.

3.1 Group Recommendation based on Model Aggregation

The group modelling problem has been addressed by merging similar individual user profiles. 
In this scenario, user profiles are usually represented as sets of weighted preferences or
 as sets of personal scores assigned by the users to the existing items.

3.2 Group Recommendation based on Prediction Aggregation
 In addition to group modelling, there exist several approaches that have been applied to the problem of making recommendations 
 for groups of people under the framework of aggregating lists of recommendations for individual users belonging to the group. 
 
 For them, we can distinguish two main strategies, 
 namely collaborative filtering and rank aggregation.
 
- In collaborative filtering, a user provides ratings to items, and these ratings are
used to suggest her ranked lists that contain other items according to the overall preferences of people with similar rating patterns. 
Similarity rating patterns are calculated by using different metrics, such as Pearson and Spearman’s correla- tions, and cosine-based distance.

- In rank aggregation, item recommendation lists are generated for each individual, 
and afterwards are merged into a single recommendation list for the group. 
Analogously to model aggregation approaches, different social choice strategies can be used to combine several rankings.

Fases:

3.3 Group Formation
Many studies have examined systems that support group formation. The groups can be built intentionally (by explicit definition from the users) or non- intentionally (by automatic identification from the system).

3.4 Cooperative Consensus
In addition to applying an automatic group modelling algorithm, there exist approaches that make use of consensus mechanisms to achieve a final item recommendation policy agreed by the different members of a group.
Recently, these approaches have also been called role-based [5] and borderline [27] strategies.

------------------------------------

Fases:
Jameson, A. (n.d.). More Than the Sum of Its Members : Challenges for Group Recommender Systems.

Phase of the recommendation process        Difference from recommendation to individuals       Novel issue
---------------------------------------------------------------------------------------------------------
1. Members specify their preferences.      It may be desirable for members to examine          What benefits and drawbacks can such examination have, 
                                           each other’s preference specifications.             and how can it be supported by the system?
2. The system generates recommendations.   Some procedure for aggregating preferences          How can the aggregation procedure effectively discourage
                                           must be applied.                                    manipulative preference specification?
3. The system presents recommendations     The (possibly different) suitability of a solution  How can relevant information about suitability for
to the members.                            for the individual members becomes an important     individual members be presented effectively?
                                           aspect of a solution.
4. Members decide which recommendation     The final decision is not necessarily made by       How can the system support the process of arriving
(if any) to accept.                        a single person; negotiation may be required.       at a final decision when members cannot engage 
                                                                                               in face-to-face discussion?

Jameson, A., & Smyth, B. (2007). Recommendation to Groups. The Adaptive Web: Methods and Strategies of Web Personalization, 4321, 596–627.
Step 2)

Approaches to Preference Aggregation

Although the various approaches differ in the ways in which they gather and represent users’ preferences, almost all approaches make use of one of three schemas:

(a) merging of sets of individual recommendations
(b) aggregation of individuals’ ratings for particular items
(c) construction of group preference models.

<!-- -->

(a) **Merging of sets of individual recommendations**

In cases where what is to be presented is a set of candidate solutions, among which the group is to select one for
adoption, a simple aggregation method is that of generating a small number of recommended solutions for each member and then merging them into a single list:

1. For each group member $m_{j}$:
	* For each item candidate $c_{i}$, predict the rating $r_{i,j}$ of $c_{i}$ by $m_{j}$
	* Select the set of item candidates $C_{j}$ with the highest predicted ratings $r_{i,j}$ for $m_{j}$.
2. Recommend $\bigcup_{j}C_{j}$, the union of the set of item candidates with the highest predicted ratings for each group member.

(b) **Aggregation of individuals’ ratings for particular items**

This commonly applied approach starts with the assumption that, for each candidate item $c_{i}$ and each member $m_{j}$, the system can predict how $m_{j}$
would evaluate (or rate) $c_{j}$ if he or she were familiar with it:

1. For each item candidate $c_{i}$:
	* For each group member $m_{j}$ predict the rating $r_{i,j}$ of $c_{i}$ by $m_{j}$.
	* Compute an aggregate rating $R_{i}$ from the set ${r_{i,j}}$.
2. Recommend the set of item candidates with the highest predicted ratings $R_{i}$.

(c) **Construction of group preference models**

The second widely applied approach to aggregation does not involve any predictions of ratings of individual users.
Instead, the system somehow uses its information about the preferences of individual group members to arrive at a model of the preferences of the group as a whole:

1. Construct a preference model $M$ that represents the preferences of the group as a whole.
2. For each candidate $c_{i}$,use $M$ to predict the rating $R_{i}$ for the group as a whole.
3. Recommend the set of candidates with the highest predicted ratings $R_{i}$.
																							   
																							   
------------------------------------

## Existing Group Recommenders

-------------

Jameson, A. (n.d.). More Than the Sum of Its Members : Challenges for Group Recommender Systems.

It will be helpful to refer for concreteness to four of the best-known group recommender systems that have been presented so far: 
MUSICFX ([12]) selects music channels for the music to be played in a fitness center. 
On the basis of the preferences that have been previously specified by the members who are currently working out, the system chooses one of 91 possible music channels, including some randomness in the choice procedure in order to ensure variety.

LET’S BROWSE ([10]) recommends web pages to a group of two or more persons who are browsing the web together.

POLYLENS ([13]) is a generalization of the MOVIELENS system (http://www.movielens.umn.edu/) that recommends movies to groups of users.
The system has recently been modified to yield BUDDY SEARCH, which makes it easier to form ephemeral groups.

INTRIGUE ([1]) recommends tourist attractions for heterogeneous groups of tourists that include relatively homogeneous subgroups (e.g., “children”).

The recently developed TRAVELDECISION FORUM prototype helps a group of users to agree on the desired attributes of a vacation that they are planning to take together. 
Special attention is given to support for users who are not collocated and who can therefore not engage in face-to-face discussions.

Since our focus is on general issues rather than specific systems, for each of these systems we will mention only the aspects that are relevant to the issues under discussion.

-------------

Jameson, A., & Smyth, B. (2007). Recommendation to Groups. The Adaptive Web: Methods and Strategies of Web Personalization, 4321, 596–627.

Figure 20.1 lists almost all of the group recommender systems that, according to the authors’ knowledge, have been described in the literature up to the time of the writing of this chapter. Although a number of these systems have been described only briefly and some were not implemented as web-based systems, we will refer to aspects of all of them, so as to convey an idea of the variety of application settings, design issues, and possible methods that designers of group recommender systems should be aware of.


Table 20.1. Overview of the group recommender systems mentioned in this chapter. System Reference
(Examples of) Groups of Users

* Web / news pages

System         Reference         Group of users                     Items Recommended
-------------- ----------------- ---------------------------------- -----------------
Let’s Browse   [@Lieberman1999]  Persons browsing the web together  Web pages
G.A.I.N        [@Pizzutilo2005]  Persons viewing a wall display or  News items
                                 information kiosk
I-Spy          [@Smyth2004]      Employees of a company             Web pages
--------------------------------------------------------------------------------------

* Tourist attractions

System           Reference            Group of users               Items Recommended
---------------- -------------------- ---------------------------- --------------------------------
Intrigue         [@Ardissono2003]     Tourists                     Sightseeing tours
CATS             [@KevinMcCarthy2006] Friends planning a vacation  Vacation packages
Travel Decision  [@Jameson2004]       Friends planning a vacation  Criteria for choosing
Forum                                                              a vacation package
Group Modeler    [@Kay2005]           Persons visiting a museum    Information about exhibits
                                      together                     
Pocket           [@McCarthy2002]      Colleagues going out to dine Restaurants Items recommended
RestaurantFinder                      together                    
--------------------------------------------------------------------------------------------------

* Music tracks

System         Reference       Group of users               Items Recommended
-------------- --------------- ---------------------------- ---------------------------------
MusicFX        [@Mccarthy1998] Persons working out in a gym Music stations
Flytrap        [@Crossen2002]  Persons using a public area  Music tracks to be played
                               of a building
In-vehicle     [@Yu2005]       Passengers in a vehicle      Multimedia items to be played
multimedia     
recommender    
Adaptive Radio [@Chao2005]     Colleagues working together  Songs to be played on the radio
                               in an office
---------------------------------------------------------------------------------------------

* Television programs and movies

System           Reference        Group of users             Items Recommended
---------------- ---------------- -------------------------- -------------------------------
FIT              [@Goren-Bar2002] Family members watching    TV programs
                                  TV together               
TV program       [@Yu2006]        TV viewers                 Sequences of TV programs
recommender
PolyLens         [@O'Connor2001]  Persons planning to go to  Movies
                                  a movie together
Masthoff’s Group [@Masthoff2004]  TV viewers                 Sequence of music video clips
Recommender                                                  to be watched
--------------------------------------------------------------------------------------------