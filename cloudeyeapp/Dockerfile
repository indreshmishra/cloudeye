FROM digitallyseamless/nodejs-bower-grunt:latest
RUN mkdir /gruntapp
ADD . /gruntapp
WORKDIR /gruntapp
RUN bower install
RUN npm install
EXPOSE 9000
ENTRYPOINT grunt serve
