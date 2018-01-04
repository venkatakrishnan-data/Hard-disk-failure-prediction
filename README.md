# Self-Monitoring-system to predict the internal disk status using machine learning. 

This example shows a generated Java POJO being called using a REST API from a JavaScript Web application.


(Front-end)   

1.  Web browser

(Back-end)   

1.  Jetty servlet container

> Note:  Not to be confused with the H2O embedded web port (default 54321) which is also powered by Jetty.


## Files

(Offline)
* build.gradle
* data/loan.csv
* script.R

(Front-end)
* src/main/webapp/index.html
* src/main/webapp/app.js

(Back-end)
* src/main/java/org/gradle/PredictServlet.java
* lib/h2o-genmodel.jar (downloaded)
* src/main/java/org/gradle/BadLoanModel.java (generated)
* src/main/java/org/gradle/InterestRateModel.java (generated)


## Steps to run

##### Step 1: Create the gradle wrapper to get a stable version of gradle.

```
$ gradle wrapper
```

##### Step 2: Install H2O's R package if you don't have it yet.

<http://h2o-release.s3.amazonaws.com/h2o/rel-tibshirani/2/index.html#R>

##### Step 3: Build the project.

```
$ ./gradlew build
```

##### Step 4: Deploy the .war file in a Jetty servlet container.

```
$ ./gradlew jettyRunWar -x generateModels
```

(If you don't include the -x generateModels above, you will build the models again, which is time consuming.)

##### Step 5: Visit the webapp in a browser.

<http://localhost:8080/>


## Underneath the hood

Make a prediction with curl and get a JSON response.

```
$ curl "localhost:8080/predict?loan_amnt=10000&term=36+months&emp_length=5&home_ownership=RENT&annual_inc=60000&verification_status=verified&purpose=debt_consolidation&addr_state=FL&dti=3&delinq_2yrs=0&revol_util=35&total_acc=4&longest_credit_length=10"
{
  "labelIndex" : 1,
  "label" : "1",
  "classProbabilities" : [
    0.8581645524025798,
    0.14183544759742012
  ]
}
```

Notes:


* The threshold is the max-F1 calculated for the model, in this case approximately .20.
* A label of '1' means the disk is good.
* A label of '0' means the disk would fail.



```
$ curl "localhost:8080/predict?loan_amnt=10000&term=36+months&emp_length=5&home_ownership=RENT&annual_inc=60000&verification_status=blahblah&purpose=debt_consolidation&addr_state=FL&dti=3&delinq_2yrs=0&revol_util=35&total_acc=4&longest_credit_length=10"
[... HTTP error response simplified below ...]
Unknown categorical level (verification_status,blahblah)
```


## Model building

1.  Set VERBOSE to false in src/main/java/org/gradle/PredictServlet.java

1./gradlew build --stacktrace

1.  The model is built in the following way:

```
 df1 <- h2o.importFile(path = normalizePath("/home/hduser/Desktop/sharedwindow/Smart.ini"))
  |======================================================================| 100%
> tt<-read.csv("/home/hduser/Desktop/sharedwindow/Smart.ini")
> pp<-tt[37:65,]
> final_df <- t(pp)
> out<-gsub("^.*\\="," ",final_df[1,c(1,2,4,6,9,10,24,27,19,23,9,23)])
> out1<-as.numeric(out)
> out1<-t(out1)
> write.csv(out1,"/home/hduser/Desktop/APPLICATION/sample.csv")
> colnames(out1) <- c("Temp","Power_On_Hr", "Read_Error","Spin_Up_Time"
+                     ,"Reallocated_Sectors","Seek_Error",
+                     "Uncorrectable_Errors", "High_Fly_Writes","Hardware_ECC",
+                     "Pending_Sector1", "Reallocated_Sector", "Pending_Sector")
> 
> set.seed(456)
> range01 <- function(x){(x-min(x))/(max(x)-min(x))}
> train<-range01(out1)
> 
> label<-NA
> train1<-cbind(train,label)
> 
> write.csv(train1,"my_test")
> df1 <- h2o.importFile(path = normalizePath("/home/hduser/my_test"))
  |======================================================================| 100%
> df1<-df1[,-1]
> 
> 
> df <- h2o.importFile(path = normalizePath("/home/hduser/Desktop/linu_use.csv"))
  |======================================================================| 100%
> df$label<-as.factor(df$label)
> trainer<-df
> #head(trainer,n=1)
> rf1 <- h2o.randomForest(         
+   training_frame = trainer,        
+   x=3:14,                        
+   y=2,                          
+   model_id = "rf_covType_v1",    
+   ntrees = 200,                  
+   stopping_rounds = 2,          
+   score_each_iteration = T,     
+   seed = 1000000)
:placeGenModelJar UP-TO-DATE
:placeInterestRateModel
:generateModels
:compileJava
:processResources UP-TO-DATE
:classes
:war
:assemble
:compileTestJava UP-TO-DATE
:processTestResources UP-TO-DATE
:testClasses UP-TO-DATE
:test UP-TO-DATE
:check UP-TO-DATE
:build

BUILD SUCCESSFUL

Total time: 1 mins 46.1 secs


```

## Data
The predictive model is built using Random Forest (RF) which shows maximum prediction after a comparision with Feed Forward Neural Net, K- means clustering. The model is trained with a disk failure data available at <http://pan.baidu.com/share/link?shareid=189977 &
uk=4278294944>. The trained model is tested with internal disk data of the system. The internal disk data is collected using a open source software named 'crystaldisk Info'. This data is provided to the model every test fiver seconds and the predicted status of the disk is available to the user on his local browser.  
