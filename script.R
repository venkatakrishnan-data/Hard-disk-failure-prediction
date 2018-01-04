library(h2o)
h2o.init()
df1 <- h2o.importFile(path = normalizePath("/home/hduser/Desktop/sharedwindow/Smart.ini"))
tt<-read.csv("/home/hduser/Desktop/sharedwindow/Smart.ini")
pp<-tt[37:65,]
final_df <- t(pp)
out<-gsub("^.*\\="," ",final_df[1,c(1,2,4,6,9,10,24,27,19,23,9,23)])
out1<-as.numeric(out)
out1<-t(out1)
write.csv(out1,"/home/hduser/Desktop/APPLICATION/sample.csv")
colnames(out1) <- c("Temp","Power_On_Hr", "Read_Error","Spin_Up_Time"
                    ,"Reallocated_Sectors","Seek_Error",
                    "Uncorrectable_Errors", "High_Fly_Writes","Hardware_ECC",
                    "Pending_Sector1", "Reallocated_Sector", "Pending_Sector")

set.seed(456)
range01 <- function(x){(x-min(x))/(max(x)-min(x))}
train<-range01(out1)

train<-out1
label<-NA
train1<-cbind(train,label)

write.csv(train1,"my_test")
df1 <- h2o.importFile(path = normalizePath("/home/hduser/my_test"))
df1<-df1[,-1]


df <- h2o.importFile(path = normalizePath("/home/hduser/Desktop/linu_use.csv"))
df$label<-as.factor(df$label)
trainer<-df
#head(trainer,n=1)
rf1 <- h2o.randomForest(         
  training_frame = trainer,        
  x=3:14,                        
  y=2,                          
  model_id = "rf_covType_v1",    
  ntrees = 200,                  
  stopping_rounds = 2,          
  score_each_iteration = T,     
  seed = 1000000)

out<-h2o.predict(rf1, df1)
out
if(! file.exists("tmp")){
     dir.create("tmp")
     }
     #test
 h2o.download_pojo(rf1,path = "tmp")
 
 
 