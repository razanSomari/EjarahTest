# Development of a Mobile Application for Help Request Using the Principle of Crowdsourcing: Ejarah
A location-based application that uses the concept of crowdsourcing to allow a group of people of the same or different skills to colaporate to solve a given problem. The application uses a text classification algorithm to classify posts into categories depending on their topic to use in a **points system** to award users for their contributions. The application has a **search function** that uses data from prevouis users to attach the most frequently requested items to the location they were posted at, the items are used to recommend to users when searching for a location.

**Timeline**: the timeline contains posts that were posted 1 kilometers around the users current location.

**Creating posts**: the user can create a new post by clicking on the floating button on the timeline. 

**Text Classification Algorithm**: each post is classified into a category depending on its topic (science, sports, politics...).

**Posts**: the user can contribute to solving the OP's problem by replying to their post, the OP can then thumps up the reply if it helped them.

**Points**: the user is awarded points in the posts designated category when an OP thumps up their reply.

**Search function**: the search function allows a user to search for a specific location, the application will then display a list of the items that the user might need there, based on data from previous users.

## Tools used:
- Android SDK.
- Programming languages: Java, XML and Python. 
- Firebase Realtime Database. 
- Google Places Autocomplete API. 
- TensorFlow.
- Jupyter notebook.
- Libraries: Keras, nltk, Scikit-Learn, Numpy and Pandas. 
- Version control.

## Interfaces:
The application contains 8 interfaces, which are: login, signup, timeline, creating a post, indiviual post, level, search and language.


### Login:
The login interface is the applications launcher. The user must fill the email and password fields to log into the application.

<img width="306" alt="logininterface" src="https://user-images.githubusercontent.com/60888719/90302577-bd9c4680-deaf-11ea-8cff-38cbb1e8237e.png">

### Signup:
If the user does not have an account already, they can create one by clicking on sign up in the login interface, and then the user must fill in their name, email, password and a confirmation of password. 

<img width="306" alt="signupinterface" src="https://user-images.githubusercontent.com/60888719/90302003-e8849b80-deab-11ea-83d0-6d48aff086d5.png">

### Timeline:
The timeline contains a list of posts that were created nearby the users location. The posts are requests of help that the user can participate in relieving.

<img width="306" alt="timelineinterface" src="https://user-images.githubusercontent.com/60888719/90302586-ce4cbc80-deaf-11ea-83f6-fdc43852ae25.png">

### Create a post:
The user can create a post by clicking on the create post button in the timeline. The user must fill in the contents of the post, and can optionally add a picture and/or tags.

<img width="306" alt="createpostinterface" src="https://user-images.githubusercontent.com/60888719/90302588-d0168000-deaf-11ea-91e5-cdf6a8cab2cb.png">

### Post:
When a post on the timeline is clicked, the post is shown individually. Containing the replies, as well as the number of responders and views. The user requesting help can approve a reply by clicking the thumbs up button next to the reply.

<img width="306" alt="postinterface" src="https://user-images.githubusercontent.com/60888719/90302591-d4429d80-deaf-11ea-9640-740552e18920.png">

### Replies:
The user can respond to the post by creating a reply using the button below.

<img width="306" alt="repliesinterface" src="https://user-images.githubusercontent.com/60888719/90302589-d147ad00-deaf-11ea-9f0d-cb98462c941f.png">

### Navigation Menu:
The navigation Menu allows the user to navigate the application. So that the user can switch between the functionalities easily.

The menu contains:
- Home 
- Level
- Search 
- Sign out 
- Language 

<img width="306" alt="navmenu" src="https://user-images.githubusercontent.com/60888719/90302122-c4758a00-deac-11ea-9bd9-db3e44f735e4.png">

### Level:
The rewards appointed to users in each category. The user gets points based on the posts category that they contributed in.
When the user completes 100 points they will go to the following level.

<img width="306" alt="levelinterface" src="https://user-images.githubusercontent.com/60888719/90302595-d60c6100-deaf-11ea-9690-6e9c97fa0db1.png">

### Search:
The search fragment takes a location string as input, and outputs the features of the posts that were posted in that location. By using Google’s Autocomplete API , the user types in the location string, the fragment predicts the rest of that string so that the user can just click it. The features of the posts posted within 5 kilometers of the location are displayed.

<img width="306" alt="searchinterface" src="https://user-images.githubusercontent.com/60888719/90302180-2930e480-dead-11ea-9a06-af8d5f96196d.png">

The search results displays the top ten most used words at a location are displayed, ordered by frequency.

<img width="306" alt="searchinterface2" src="https://user-images.githubusercontent.com/60888719/90302642-2f749000-deb0-11ea-8fa4-d57dcebee2ea.png">

### Language:
The user can change the language of the application to English or Arabic.

<img width="306" alt="langinterface" src="https://user-images.githubusercontent.com/60888719/90302596-d86ebb00-deaf-11ea-8c2c-ff39b0c9ae82.png">

## Machine Learning Model:
Text Classification Model for classifying users posts.
The goal to be accomplished via ML model:
- To determine in which category of expertise points users will earn.
- To suggest what the user might need when searching for a location in the application.
The dataset: 
Samples source: Reddit self-posts from 2016/06/01–2018/06/01
Shape: 65499 x2 
number of classes: 39 

<img width="150" alt="redditClasses" src="https://user-images.githubusercontent.com/60888719/90315578-40abb400-df25-11ea-9a35-cff394549f56.png">

### Machine Learning Wrokflow:

<img width="600" alt="mlworkflow" src="https://user-images.githubusercontent.com/60888719/90315625-7f416e80-df25-11ea-9dc4-b1d2052fa83a.png">

### Features & Preprocessing:

- Lower casing.
- Remove punctuation and white spaces. 
- Removing stop words using nltk.
- Lemmatization using nltk.

Tokenization:
- Keras Text Tokenizer.
- CountVectorizer, TfidfTransformer. 

Feature Selection:
- Keras Text Tokenizer: Select the most common 30,000 features. 
- Chi-square:  Select the best 30,000 features with the highest chi-square.

### Artificial Neural Network using Keras:
- Neural Networks are brain-inspired systems and used to process non-linear relationship between features set and target.
- Keras is a high-level Neural Networks API written in python and can be used in TensorFlow CNTK, or Theano.

<img width="600" alt="redditClasses" src="https://user-images.githubusercontent.com/60888719/90315784-ca0fb600-df26-11ea-9ade-08f9c62dcb50.png">


Sequential Model Layers:
- Input layer: Dense layer
- Activation layer: ReLU
- Dropout layer : 0.5 dropping rate
- Output layer: 39 units 
- Activation layer: softmax

Loss function = categorical_crossentropy 
Optimizer = Adam optimizer

Batch size = 32
Epochs = 20
validation split = 0.2

#### Results:
Accuracy: 0.68
Precision: 0.80
Recall: 0.58
Running time: 194 S

#### Deployment:
The process of deployment in Ejarah:
- load the ANN model (Keras model) as a TensorFlow model.
- convert the TensorFlow model to TensorFlow lite format.
- Store TensorFlow lite in assets folder and use it for predictions
with the help of TensorFlow Lite inference.
