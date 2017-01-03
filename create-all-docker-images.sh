cd cloudeyeJWorker
gradle buildDocker
cd ..
cd cloudeyereporter
gradle buildDocker
cd ..
cd cloudeyejobs
gradle buildDocker
cd ..
cd cloudeyeservice
gradle buildDocker
cd ..
cd cloudeyeEmailnotifier
gradle buildDocker
cd ..
cd cloudeyenotifier
gradle buildDocker
cd ..
cd cloudeyeapp
docker build -t rworkdroplate/cloudeyeapp:1.0 .
cd ..
cd cloudeyeJWorkerBaseImage
docker build -t rworkdroplate/rwork-java-ssh .
cd ..
