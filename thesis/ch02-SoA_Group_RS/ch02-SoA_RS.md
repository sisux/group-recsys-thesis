State-of-the-Art in Recommender Systems
========================================

Jannach, D., Zanker, M., Felfernig, A., & Friedrich, G. (2011). Recommender Systems An Introduction. Cambridge University Press.

Recommendation Techniques

2 Collaborative recommendation
2.1 User-based nearest neighbor recommendation
2.2 Item-based nearest neighbor recommendation
2.3 About ratings
2.4 Further model-based and preprocessing-based approac


3 Content-based recommendation
4 Knowledge-based recommendation
5 Hybrid recommendation approaches 
--------------------------------------------------



Since the appearance of the first papers on recommender systems in the mid-1990s a lot of
research has been conducted in this area [AT05]. Many algorithms and techniques have been
proposed by both industry and academia, and researchers are still working to improve recommender
systems. This chapter gives an overview of existing recommendation approaches,
as well as the state-of-the-art in the field.
In this chapter the following will be discussed

• Collaborative filtering algorithms (Section 2.2).
• Content-based filtering algorithms (Section 2.3).
• Hybrid forms that combine content-based and collaborative filtering (Section 2.4)

## Recommendation approaches

Recommender systems try to predict what items a specific user would find interesting. These
predictions are usually based on information about the user (called a user profile, user model,
or user preferences), its interaction history (what the user bought, viewed, watched, read,
etc.), properties of the items (called item features) and/or other users. Most systems try to
predict how a user would rate a specific item, based upon this information, and then recommend
the items with the highest ratings. In order to predict these ratings, different prediction
techniques can be used. These prediction techniques come in three varieties: content-based,
collaborative (or social) based and hybrid forms [AT05, vS05, AK07].
Content-based prediction techniques make recommendations based on the features of items
that a user liked in the past. Recommender systems using this technique recommend items
that are similar to these items. Collaborative filtering recommender systems recommend items
based on the items that people with a similar taste liked in the past. The third approach is
a hybrid form that combines both collaborative and content-based techniques.
Recommender systems can also be classified according to their algorithmic technique [BHK98].
Two different classes can be identified:
• Memory-based techniques calculate recommendations in real-time over the entire data
set.
• Model-based techniques learn a model from previous user activities and use this model
to classify new content according to its interestingness.
Usually the prediction approaches mentioned above try to estimate a utility function u :
A × B ! R0, that, given a user ai 2 A and an item bk 2 B returns how much the user will
like that item. A and B are the sets of all users and items respectively. R0 is a measure
of utility, usually represented by an integer or real value. The items that are already rated
(either implicitly or explicitly) can be gathered in a rating matrix R = A × B. Table 2.1
shows an example of a rating matrix where each cell is a rating ri (bk ) of user ai for item bk .
An empty cell means that the user did not rate the item. It is the recommender system’s
task to predict the value that should be in this cell.

User
Item 1 2 3 4
A 5 3 4
B 4 5
C 2 4
D 4 4
E 3 2 2
F 2 4
G 5 5
Table 2.1.: A sample rating matrix

In addition to algorithms that try to predict an item’s utility to a user, there are also algorithms
that try to order the items based on their interestingness without assigning an absolute
utility value to it. This approach is called preference-based filtering and is focusing on the
relative preference to a user [AT05]. Preference-based filtering will not be discussed in further
detail. Collaborative filtering, content-based filtering and hybrid recommendation approaches
will be discussed in the following sections.

## Collaborative filtering

Collaborative filtering (CF) is the most widely used prediction technique in recommender
systems [MH04, ZMKL05]. The technique makes recommendations based on ratings that
users have assigned to items (i.e. the ratings in the rating matrix). There exist two different
approaches to collaborative filtering: user-based and item-based CF. The underlying idea of
user-based CF is that users who have rated the same items the same way (or at least similar)
are likely to have a similar taste. These users are therefore likely to rate the same item the
same way. Item-based CF on the other hand, assumes that items that have been rated the
same way in the past are probably similar items. A user will probably rate the item the same
as he rated similar items.

### User-based collaborative filtering

User-based CF consists of three basic steps. First a neighbourhood of users that are most
similar to the active user (i.e. the user requesting recommendations) is constructed. This is
done by calculating the similarity sim(ai , aj ) between user ai and all other users aj 2 A. The
l most similar users then form ai ’s neighbourhood. In the second step the active user’s ratings
are predicted for items that are new to him (i.e. not yet rated by the user), but are rated by
members of his neighbourhood. The third and final step is the construction of a recommendation
list based on the predicted ratings. The prediction list does not necessarily consist of
the top-N rated items, since list diversification might be applied for example [ZMKL05].
In the neighbourhood formation step the similarity between the active user and all other users
must be calculated. There are several algorithms that provide a measure for user similarity.
Two of them, Pearson’s correlation and the cosine-based approach are described next.
sim(ai , aj ) =
P
bk2Bij
(ri (bk ) - ri )(rj (bk ) - rj )
qP
bk2Bij
(ri (bk ) - ri )2
P
bk2Bij
(rj (bk ) - rj )2
(2.1)
Pearson’s correlation coefficient is a measure for the strength and direction of a linear relationship
between two variables. Equation 2.1 shows the Pearson correlation coefficient that
calculates the similarity between two users. In this equation Bij indicates the set of items
that are rated by both users ai and aj . ri (bk ) is ai ’s rating from item bk and ri is the average
rating value for user ai as calculated in Equation 2.2. In this equation Bi is the set of items
rated by user ai .
ri =
1
|Bi |
X
bk2Bi
ri (bk ) (2.2)
In the cosine-based approach the users are considered as vectors -!ai and -!aj in a m-dimensional
space, where m = |Bij |. The vectors thus represent the rating values for the items that were
rated by both users. The similarity is then calculated as the angle between those two vectors,
as shown in Equation 2.3.
sim(ai , aj ) = cos(-!ai ,-!aj )
=
-!ai • -!aj
|-!ai | · |-!aj |
=
P
bk2Bij
ri (bk ) · rj (bk )
qP
bk2Bij
ri (bk )2
qP
bk2Bij
rj (bk )2
(2.3)

When Pearson’s correlation is applied to the running example as shown in Table 2.1, this
results in coefficients presented in Table 2.2. The table shows that users 3 and 4 are very
similar (correlation coefficient of 1.0), based on their ratings, while users 1 and 2 appear to
be each others opposites (strong negative correlation).

User
User 1 2 3 4
1 1.0000 -0.9978 0.5335 0.9962
2 -0.9978 1.0000 0.8682 -1.0000
3 0.5335 0.8682 1.0000 1.0000
4 0.9962 -1.0000 1.0000 1.0000
Table 2.2.: Pearson’s correlation coefficient indicates similarity among users

Once the user similarities are calculated, a neighbourhood eAi is formed of the l users that
are most similar to the active user ai . This neighbourhood is then used to make a prediction
pi (bk ) for each item bk that is rated by at least one neighbour, but not by the active user
itself. The ratings of the neighbours are used to make this prediction. Equations 2.4a-c show
three approaches, as identified in the literature, to aggregate the neighbours’ ratings to form
the prediction [AT05].
pi (bk ) =
1
|eAi |
X
aj2eA
i
rj (bk ) (2.4a)
pi (bk ) = 
X
aj2eA
i
sim(ai , aj ) · rj (bk ) (2.4b)
pi (bk ) = ri + 
X
aj2eA
i
sim(ai , aj ) · (rj (bk ) - rj ) (2.4c)
The normalisation factor  is defined as:
 =
P 1
aj2eA
i
|sim(ai , aj )|
Equation 2.4a just takes the average rating of all neighbours eAi . Equation 2.4b takes the similarity
between the active user and each of its neighbours into account and weights the rating
of more similar users higher (i.e. a weighted sum approach). On top of this Equation 2.4c
also takes into account how a certain user rates items on average.
In the running example, user 3 (a3) his neighbourhood (eA3) would consist of users 2 and 4
(when l = 2), since these two users are most similar to him. When the recommender tries to
predict a3’s rating for item C it uses the ratings of a2 and a4, and combines them using one
of the aggregation methods described in Equation 2.4. When Equation 2.4a is applied the
predicted value p3(C) would become 1
2 × 2 + 4 = 3. Equation 2.4b results in p3(C) = 3.0706
and Equation 2.4b in p3(C) = 3.2313.
Once the active user’s rating is predicted for all items rated by at least one of its neighbours,
a recommendation list can be constructed and presented to the user. Usually this is done by
taking the N items that have the highest predicted rating, but other approaches exist as well.
One such approach is presented in [ZMKL05].

### Item-based collaborative filtering

Item-based CF rests on the assumption that users that appreciate a certain item bk probably
also like a similar item bl (and thus give them the same rating). The three step approach
that is used in user-based CF is also applicable to item-base CF, besides that similarities are
calculated over pairs of items rather than users.

Pearson’s correlation can also be applied to calculate the similarity among items, based on the
ratings of all users. For the running example this would result in de correlation coefficients
as shown in Table 2.3.

Item
Item A B C D E F G
A 1.0000 -1.0000 1.0000 0.8944 -1.0000
B -1.0000 1.0000 1.0000 -0.9487 1.0000
C 1.0000 1.0000 1.0000 -1.0000 -1.0000
D 1.0000
E 0.8944 -0.9487 -1.0000 1.0000 -1.0000
F -1.0000 1.0000 -1.0000 -1.0000 1.0000
G 1.0000
Table 2.3.: Pearson’s correlation coefficient indicates similarity among items

These similarity measures can be used to determine the items that are most similar to the
item for which the prediction is calculated (analogous to the neighbourhood in user-based
CF). Based on the ratings for these similar items the predicted rating of the active user for
the item is calculated.

As shown by the examples the number of similarities that need to be calculated differs when
using user-based or item-based collaborative filtering. When user-based CF is applied roughly
1
2 |A|2 similarities must be calculated, compared to 1
2 |B|2 with item-based CF. So when the 
number of users is much smaller than the number of items it is computationally cheaper to
apply user-based CF. Otherwise it is cheaper to use item-based CF.

### Cold start problem

One of the main difficulties with collaborative filtering is that it suffers from the cold start
problem. The cold start problem occurs when the recommender system cannot draw any
inferences for users or items about which it has not yet gathered sufficient information. The
first time a user visits a specific recommender system for example it did not have rated any of
the items. Hence the recommender system does not know what that user’s likes and dislikes
are. The same problem occurs when a new item is added to the recommender. Since nobody
has ever rated that item the recommender system cannot know to which other items it is
similar. Hence the recommender cannot recommend the item until a substantial number of
users rated it.

## Content-based filtering

In content-based recommendation systems, items are characterised by a set of predefined
features. A movie for example, could be characterised by its cast, genres, theme, director
and a description of the plot. This metadata can be provided explicitly or extracted from
the content. Like items, users also have a profile. A user’s profile contains information about
its tastes, preferences and needs. Users can construct such a profile explicitly, but it can also
be learned from the user’s interaction with the recommender system. Content-based filtering
uses these profiles to determine the interestingness of each item and recommends those that
are most interesting.
There are two common approaches to determine an item’s interestingness, a heuristics-based
and a model-based approach. In a heuristics-based approach the profile of an item bk is
defined as a vector -!wk = (w1k , ..., wnk ) of weights for each feature. These weights can be
TF-IDF (term frequency-inverse document frequency, a concept from information retrieval)
weights for example [Sal89, SB87]. A user’s profile is specified likewise: -!wi = (w1i , ..., wni ).
The interestingness of item bk to user ai can then be calculated using the cosine similarity
measure as shown in Equation 2.3.
In a model-based approach predictions for an item’s interestingness are not based on a heuristic
formula, but on a model learned from the underlying data. Such a model is constructed
using techniques such as Bayesian classifiers, machine learning, decision trees and neural
networks [AT05].
Content-based filtering techniques are limited by the features of the items that are recommended
by the system. When a feature is not associated with an item, the recommender
system cannot use it in the recommendation process. Suppose that the movie ”One flew over
the cuckoo’s nest” is not labelled with the actor ”Jack Nicholson”, a Jack Nicholson fan might
not get this movie recommended. It thus is important to have a sufficient set of features, but
feature extraction has proven to be either labour intensive (when it is done manually) or error
prone (when it is done automatically) [AT05].
Another issue with content-based recommender systems is that they only recommend items
that have a high similarity score with respect to the user’s profile. This implies that only items
that are similar to items that were rated in the past get recommended. This issue is known
as overspecialisation and implies that users will never receive serendipitous recommendations
[AT05].

## Hybrid forms

Hybrid recommender systems combine both collaborative and content-based filtering to overcome
the limitations (as described above) that each of the individual approaches suffers
from. Adomavicius and Tuzhilin [AT05] identified four methods to combine collaborative and
content-based filtering:
1. let both the content-based and collaborative technique make a prediction and then
combine them,
2. incorporate some content-based techniques into a collaborative filtering approach,
3. incorporate some collaborative filtering techniques into a content-based approach, and
4. fully incorporate both techniques when building a hybrid recommender system.
The ratings predicted by each of the techniques can be combined by a linear combination
or voting scheme for example, but it’s also possible to choose one of the results based on
certain metrics (like confidence). An example of incorporating content-based filtering in a
CF recommender system is to construct a user’s neighbourhood based on the user profiles,
instead of rating history. The third approach can encompass dimension reduction techniques
on a group of content-based profiles for example. A single unifying recommendation model
can use both content-based and collaborative characteristics (like age and gender of users or
the genre of movies) in a single rule-based classifier for example.

## Conclusion

This chapter presented three types of prediction strategies that can be used to predict a
user’s rating for an item that is not yet rated by him. The first and most widely used
strategy, called collaborative filtering, makes recommendations based on ratings that other
users have assigned to items. Based on these ratings the recommender tries to find either
users that are most similar to the active user or items that are similar to the item for which
the user’s rating is predicted. The first approach is called user-based collaborative filtering,
the latter is called item-based collaborative filtering.
The second strategy presented in this chapter is called content-based filtering. With contentbased
filtering items are represented by a set of characteristics, called features. Each user has
a user profile that describes which features are valued by the user and which are not. The
recommender system then tries to match the feature vectors of the items against the user
profile in order to predict the user’s rating.
Since both techniques have limitations hybrid approaches were developed. By combining
collaborative and content-based filtering techniques the cold start problem that collaborative
filtering suffers from can be overcome for example. So, in conclusion, hybrid recommender
systems combine the best of two worlds and help to overcome the limitations of both collaborative
and content-based filtering.
