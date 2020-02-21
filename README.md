# ifood-mobile-test
Create an app that given an Twitter username it will list user's tweets. When I tap one of the tweets the app will visualy indicate if it's a happy, neutral or sad tweet.

## Business rules
* Happy Tweet: We want a vibrant yellow color on screen with a 😃 emoji
* Neutral Tweet: We want a grey colour on screen with a 😐 emoji
* Sad Tweet: We want a blue color on screen with a 😔 emoji
* For the first release we will only support english language

### Hints
* You may use Twitter's oficial API (https://developer.twitter.com) to fetch user's tweets 
* Google's Natural Language API (https://cloud.google.com/natural-language/) may help you with sentimental analysis.

## Non functional requirements
* As this app will be a worldwide success, it must be prepared to be fault tolerant, responsive and resilient.
* Use whatever language, tools and frameworks you feel comfortable to.
* Briefly elaborate on your solution, architecture details, choice of patterns and frameworks.
* Fork this repository and submit your code.


## Setup
* Download the repository
* Add a credential file from https://cloud.google.com/natural-language/ in app/src/main/res/raw/credential.json
* Complete the file /app/src/main/java/com/renanrhoden/tweetfeelings/auth/twitterAuth.kt with the twitter API keys
* run the app

*PLEASE NOTE THAT USING THE CREDENTIAL.JSON AND THE TWITTER API KEYS IN THE PROJECT IS JUST A CONVENIENT WAY FOR THE TEST AND USERS SHOULD BE AUTHENTICATED FROM A BACKEND SERVER
