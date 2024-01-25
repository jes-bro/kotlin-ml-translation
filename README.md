# kotlin-ml-translation
AI and Society Final Project: Python -> Kotlin

## Kotline Tour

### What features do you like about Kotlin?
I like the Elvis operator, I don't know if that's a universal term for something that checks for a nulltype and returns something other than null. Also, I like the val vs. var distinction, I think it's very intuitive naming and is more legitimate than python's capitalization convention. The do while and when do syntax were interesting to me and I enjoyed thinking through the do-while statements in more English terms: "Do this while this is condition is true".

### Are there things you were expecting to find that you haven’t?
Something that I expected was a heavier use of if-statements, but they recommend using when instead. Also, I don't remember seeing "import as" syntax in the tutroial, though I know it exists in the language now after trying it. So "as" was also something I expected to see but didn't right away. 

### What questions do you have?
I think some of the use cases of the different lambda function versions aren't entirely clear to me- I think I generally understand when they are useful, but could use a little more practice implementing them in Kotlin. 

-Update: Got a lot of practice with lambda usage in kotlin via the translation. 

## Code Description
The purpose of this code is to create a confusion matrix and corresponding metrics for inference data that comes from a model trained to classify desserts. This project was originally from my AI and Societ Final module. The 
code involves loading the inference data from a json file using the gson library, calculating the number of classes from that data, calculating the confusion matrix, and finally the metrics for every class in the dataset. The main files in the repo are as follows:

- A1.kt: The main file where the aforementioned tasks take place. It generates the confusion matrix, etc., and prints the confusion matrix and metrics to the console.
- TestConfusionMatrix.kt: Tests the confusion matrix function.
- TestDetails.kt: Tests the confusion matrix details function. This implicitly tests the functions that calculate the metrics themselves. The confusion matrix details function calls the metrics functions and then stores the metrics as a unit (via a data class called Details) for each class.

There are data classes I generated using json2kotlin.com that essentially create data classes that correspond to json files so you can load them in kotlin. Those files generated using that site are: 
- Class_indices
- Json4KotlinBase
- Predictions_and_labels

I chose to use the site because at first I didn't know how to create data classes and I also didn't want to spend too much of my time on the json loading part of the translation. 

## Translation Reflection 
I had __zero__ idea that loading a json would be so difficult. I looked at so many different solutions for loading jsons in kotlin, I thought it would be a one-liner like it was in python. I was sorely mistaken. But I 
figured it out. I also learned that zip exists in kotlin, and I think its cool that a for each can be a one liner. I didn't expect to have to print matrices row by row. Overall, I spent a lot of time on this. It was a fun
process though, lookin at my A1.ipynb and then basically translating to kotlin line-by-line. It was like a fun puzzle to solve. Plus I learned a lot through doing that puzzle. I really like the when structure for whatever reason. 
I find it really intuitive, even though its basically just a switch statement. I also learned how to use maven to install libraries, and how to use the build.gradle file kind of. I still don't completely understand how build.gradle works and why you need that and the maven library install to the external libraries directory. 

Also there's a chance I have a lot of extraneous files in this repo. 
