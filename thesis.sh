pid=$(ps -fe | grep jettyRunWar | awk '{print $2}')
if [[ -n $pid ]]; then
    echo $pid
    kill -9 $pid
fi

echo "Starting server ..!"
cd /home/hduser/Machine/
./gradlew jettyRunWar -x generateModels >> /home/hduser/Machine/log.txt


