# Development of a Mobile Application for Help Request Using the Principle of Crowdsourcing: Ejarah
A location-based application that uses the concept of crowdsourcing to allow a group of people of the same or different skills to colaporate to solve a given problem. The application uses a text classification algorithm to classify posts into categories depending on their topic to use in a **points system** to award users for their contributions. The application has a **search function** that uses data from prevouis users to attach the most frequently requested items to the location they were posted at, the items are used to recommend to users when searching for a location.

**Timeline**: the timeline contains posts that were posted 5 kilometers around the users current location.

**Creating posts**: the user can create a new post by clicking on the floating button on the timeline. 

**Text Classification Algorithm:**: each post is classified into a category depending on its topic (science, sports, politics...).

**Posts**: the user can contribute to solving the OP's problem by replying to their post, the OP can then thumps up the reply if it helped them.

**Points:** the user is awarded points in the posts designated category when an OP thumps up their reply.

**Search function**: the search function allows a user to search for a specific location, the application will then display a list of the items that the user might need there, based on data from previous users.

## Tools used:
- Android SDK.
- Programming languages: Java, XML and Python. 
- Firebase Realtime Database. 
- Google Places Autocomplete API. 
- TensorFlow.
- Jupyter notebook.
- Libraries: Keras, nltk, Scikit-Learn, Numpy and Pandas. 
- GitHub (Version control).
