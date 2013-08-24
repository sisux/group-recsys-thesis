Introduction to the Research Project
====================================

----------------

Veure [Cantador, I., & Castells, P. (2012). Group Recommender Systems: New Perspectives in the Social Web. In J. J. P. Arias, A. F. Vilas, & R. P. D. Redondo (Eds.), Recommender Systems for the Social Web. Springer Verlag, Intelligent Systems Reference Library, Vol. 32.]

----------------

Nowadays the amount of content available to people is endless and it increases every minute.
It is impossible to go through all the items to decide whether they are useful or not, and
thus finding relevant items becomes more and more difficult. There are many situations in
which there are too many options to choose from. Suppose you want to buy a book from an
on-line bookstore selling millions of titles. Since most of the titles are unfamiliar to you, you
cannot decide whether you would like it or not, but among all these books there must be a
copy you will enjoy. Finding this item by browsing through the millions of titles offered by
the bookstore is impossible, the more since you probably cannot judge the item’s relevance
without reading it first. This problem is analogous to finding relevant CD’s, TV programmes,
mp3 files, movies, news articles, movie rentals, papers, results from search queries, (micro)
blog entries, and many many more. The growing popularity of user generated content like
movies on YouTube, photos on Flickr and blog entries on Blogger add to a further growth of
available content.

To overcome this so called ”information overload” problem, in the mid-1990s researchers
started to investigate recommender systems [AT05]. A recommender system (RS) uses knowledge
about your preferences (and those of others) to recommend items you are likely to enjoy.
Users can offer feedback on items they are familiar with for example, and the recommender
system uses the information to predict their preference for yet unseen items and subsequently
recommends items with the highest predicted relevance.

Many on-line shopping sites as well as other applications incorporate recommender systems in
order to offer personalised services and to boost their sales. Some popular examples include
Amazon.com, NetFlix, Last.fm, MyStrands and iTunes. Amazon.com recommends books,
music, video’s and many other products, while NetFlix recommends movies and Last.fm,
MyStrands and the iTunes store recommend music. Recommender systems also emerge in
Electronic Programme Guides (EPG), search engines and many other applications. TiVo is
a popular example of a television-viewing service that offers both an EPG and a recommendation system.

This research will primarily focus on recommender systems that recommend TV programmes
as part of an EPG, since the number of TV channels (and thus the number of TV shows) that
are available increased tremendously due to the introduction of digital television. Therefore
people watching television also suffer from information overloaded and recommender systems
start to emerge in this domain.

## Problem background

Since recommender system research began in the mid-1990s many different algorithms for generating
recommendations have been developed and nowadays a wide variety of recommender
systems exist. 

-------------
Jameson, A., & Smyth, B. (2007). Recommendation to Groups. The Adaptive Web: Methods and Strategies of Web Personalization, 4321, 596–627.
::
Recommender systems have traditionally recommended items to in- dividual users, 
but there has recently been a proliferation of recommenders that address their recommendations to groups of users. 
The shift of focus from an individual to a group makes more of a difference than one might at first expect. 
This chapter discusses the most important new issues that arise, 
organizing them in terms of four subtasks that can or must be dealt with by a group recommender:
 1. acquiring information about the user’s preferences;
 2. generating recommendations; 
 3. explaining recommendations; 
 and 4. helping users to settle on a final decision.
For each issue, we discuss how it has been dealt with in existing group recommender systems and what open questions call for further research.
-------------

This fosters the need for means to evaluate and compare different systems.
Recommender system evaluation has proven to be challenging [HKTR04, AT05, HMAC02,
ZMKL05] however, since a recommender system’s performance depends on, and is influenced
by many factors. Moreover, there is no consensus among researchers on what attributes determine
the quality of a recommender system and how these attributes should be measured.
Some call a recommender system successful when it delivers accurate recommendations, while
others utilise user satisfaction as the main indicator of success. In fact over a dozen methods
to determine a recommender system’s quality can be identified in the literature [HKTR04].
Furthermore, the data set on which the recommender system operates greatly influences its
performance [HKTR04, SKKR01]. A recommender system that performs well on a data set
with many users and only a few items may perform worse on a set with a large number of items
and much less users. Other examples of data set characteristics that affect a recommender
system’s performance are the rating density (number of ratings per user) and the rating scale.
Closely related to the first issue is the fact that the goal for which the recommender system
is evaluated may differ. The evaluation objective can be to compare two recommendation
algorithms with respect to accuracy for example, but one might also be interested in a systems
potential to increase sales. Both goals require a completely different evaluation approach and
the measurement of different attributes.

Finally, the recorded quality of a recommender system is only a snapshot in time. Users, for
example, do not rate items consistently [CLA+], since their liking of a certain item can differ,
depending on mood, changing taste, and so on. On the other hand it is unlikely that users
will notice a difference of say 0.001 in measured accuracy.

Despite these challenges, accuracy, the recommender system’s ability to predict the user’s
rating for an item, is the evaluation attribute of choice for most researchers [HKTR04, AT05,
SPUP02, SPUP05, MH04, ZMKL05, CKP07, BHL07, ZK07, CdVP+07]. As a result they try
to optimise a recommender system’s performance with respect to accuracy. A recent example
of this is in Netflix Prize, an initiative of Netflix, which is an on-line movie rental company
that seeks to substantially improve the accuracy of their recommender system. The team
that improves the accuracy of their system by at least 10% wins $1,000,000.
In the context of this research it is assumed that the ultimate goal of a recommender system
is to satisfy its users and help them to cope with the information overload problem, since
user satisfaction is thought of as the driving force behind other goals such as the increase
of sales. Although it is likely that accurate recommendations are important it might not be
the only aspect that determines user satisfaction. To illustrates this consider a recommender
system that recommends TV programmes for example. When a user likes Friends most, the
system would be very accurate when it recommends 10 episodes of Friends. It is unlikely
however that the user will be pleased with this list. So, user satisfaction probably does not
always correlate with high recommender accuracy [ZMKL05, MAC+02, MRK06], therefore
one should not only focus on accuracy when evaluating a recommender system.

## Relevance of the problem

The introduction of digital television made it possible to broadcast more TV channels using
the same amount of bandwidth as with traditional analogous television. Cable operators are
therefore able to broadcast more content to their customers. The number of channels that can
be broadcasted is still limited to the bandwidth of the cable however. When digital television
is offered over IP (IPTV) there is no need to stream channels simultaneously and therefore
the number of channels that can be offered is virtually endless. The amount of content that
becomes available through the television will increase even more with the introduction of
on-demand services such as video on demand. Recapitulating, people watching television are
likely to become overloaded with content and recommender systems can help them find TV
programmes that are relevant to them. Moreover, cable operators can offer a recommender
system through their set-top box and can thus distinguish themselves from other operators.
It is important that these recommender systems can be properly evaluated. An accurate
method for evaluation is mandatory to improve recommender systems help finding people
what they seek and to build systems that are valued by its users.

## Research question

The ultimate goal of the research is to develop a method that is able to predict the user
satisfaction achieved by an EPG/RS system, based on various off-line metrics (i.e. metrics
that do not require user feedback). The research question that will be answered by the thesis
therefore reads:

How can the user satisfaction achieved by an EPG/RS system be predicted based on various
off-line metrics?
The overall research question is subdivided into multiple sub questions. These sub questions
are:
1. What recommendation techniques do exist, how do they work and what are their benefits
/ issues?
2. What is the state-of-the-art in recommender system evaluation? What aspects are
important to evaluate?
3. What is user satisfaction and how can it be measured?
4. How do the individual properties of a recommendation list and user satisfaction cohere?

So, first detailed insight in the state-of-the-art in recommender systems and recommender
system evaluation will be gained. Furthermore methods for measuring user satisfaction will
be investigated. Then, after the user experiment has been completed, the coherence between
the observed user satisfaction and the off-line metrics will be scrutinised. Finally, based upon
the knowledge acquired by answering the sub research questions, the prediction method will
be developed. The methods that are used to answer the (sub) research questions will be
described in more detail in section 1.5.

## About TNO Information and Communication Technology

The research will be performed at the Multimedia Technology division of TNO Information
and Communication Technology 1 in Delft, The Netherlands. TNO Information and Communication
Technology is a knowledge and innovation centre that brings together knowledge
on ICT and telecom. With this knowledge it helps companies, governmental organisations
and (semi) public organisations to innovate in ICT. TNO’s approach to innovation is not limited
to technology only, it also focuses on business processes, user-friendliness and financial
aspects. The multimedia technology group’s expertise is primarily focused on media mining
and (digital) television.

## Research methods

In order to perform a sound empirical research the use of proper and well-considered methods
is important. This section discusses the methodology used to answer the overall research
question as well as the sub research questions.

### Engineering cycle

According to Wieringa the engineering cycle, the collection of tasks a rational problem solver
would follow to solve a world problem (i.e. the difference between the way the world is and
desired state of the world), has the following structure:
1. Problem investigation
2. Solution design
3. Design validation
4. Choose a solution
5. Implement the chosen solution
6. Implementation evaluation

So to solve a world problem the current situation must be investigated, possible actions must
be considered, these actions must be ranked according to their problem-solving effect, one
of these actions must be chosen and performed/implemented, and finally the result must be
evaluated to see whether it has the desired effect [WH06].
In concrete terms this results in the following steps to be taken:
1http://www.tno.nl/content.cfm?context=overtno&content=overtnosub&laag1=32&item id=63

1. Perform a literature study and an in-depth problem investigation.
2. Think of possible solutions that would solve the problem identified in 1. A possible
solution might be to use an existing data set to develop the prediction method using a
certain machine learning technique for example.
3. Verify the validity of each of the in step 2 proposed solutions (i.e. check to see whether
the solution indeed solves the problem).
4. Choose the solution that best solves the problem.
5. Then the solution chosen in step 4 needs to be implemented. This might entail the construction
of the evaluation application for example, or the development of the prediction
method using a certain technique.
6. The final step is to verify whether the problem is indeed solved and to what extent.
This means for example that the performance of the prediction method is evaluated.
Several of these steps are performed according to other research methods. These methods
will be described in the following subsections.

### Literature study

The research starts with an in-depth literature study of about six weeks. First the context of
the problem (as described above) will be investigated and important aspects and issues with
respect to recommender systems and recommender system evaluation will be identified. Next
the aspects and issues identified will be investigated more thoroughly and detailed insight
in recommender systems (i.e. existing systems, techniques, etc.) and the state-of-the-art in
recommender system evaluation (i.e. methods, metrics, etc.) will be gained. The result of
this literature study is described in chapters 2 and 3 and appendices A and B.

### User Study

For the user study the following steps are taken:
1. Determine goals
2. Analyse what data is necessary to achieve these goals
3. Design the application that will be used to gather the data
4. Validate the design
5. Implement and test the evaluation application
6. Deploy the application

First the goals of the user study are determined, after which an analysis will be made of the
data that should be gathered in order to be able to achieve these goals. Next the evaluation
application needed to gather the data must be designed and a static prototype will be build.
This prototype is then evaluated using a number of ”friendly users”, users that know the goals
of the user study and help to verify whether these goals would be achieved with the prototype.
Then the prototype is extended to the fully operational evaluation application. Again the
friendly users help to test and evaluate the system. After the evaluation period, which will
Evaluating Recommender Systems 13
Chapter 1. Introduction to the Research Project
last one week, the friendly users are interviewed and fill out a short questionnaire. After their
feedback is incorporated in the evaluation application it will go ”live” and everybody can
subscribe to the research.
The user study will be described in more detail in Chapter 4.

### Software Development

During the software development phase, in which the evaluation application will be developed,
an iterative development process will be applied. An iterative development process allows
you to cope with advancing insights that arise while building the system [Som00]. Each of
the iterations encompasses a number of subsequent tasks: requirements elicitation, analysis
and design, implementation and testing. Once multiple iterations are completed and the
evaluation application is ”finished” it will be deployed. The iterative development process is
schematically displayed in Figure 1.1.


Figure 1.1.: Iterative development process

### Validation of the model

To verify the validity of the model that is created to predict the user satisfaction based upon
a number of off-line metrics, a cross-validation method will be applied. With cross-validation
the data set is divided into training/validation set pairs that share data. This way even
relatively small data sets can be used for validating the model [Alp04].
Section 6.2 will describe the validation of the prediction model in great detail.

## Reader’s guide

The target audience of this thesis primarily consists of master students and researchers in the
field of (EPG) recommender systems that focus on the evaluation of such systems. Another
audience for which this thesis is relevant consists of professionals that actually want to evaluate
a recommender system. Furthermore people that tend to build a recommender system
or perform research on recommendation algorithms might benefit from this thesis. Some
background knowledge with respect to mathematics and mathematical notations is desirable.
The structure of this thesis is largely analogous to the sub research questions presented in
section 1.3 and the steps taken in the engineering cycle described in subsection 1.5.1. Chapter
2 describes the results of the literature study that investigated existing recommender system
algorithms while Chapter 3 focusses on the state-of-the-art in recommender system evaluation.
Next, Chapter 4 explains how the user study carried out in this research was conducted, what
metrics were measured, how they were measures, and so on. Chapter 5 elaborates further
on the coherence discovered among user satisfaction and numerous evaluation metrics. The
ultimate goal of this research, the development and validation of a prediction method to
predict user satisfaction, will be described in Chapter 6. Finally the thesis is concluded in
Chapter 7. This chapter also describes future work provoked by this research.
Evaluating Recommender Systems 15