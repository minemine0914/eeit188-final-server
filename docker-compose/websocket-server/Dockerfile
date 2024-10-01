# Base image
FROM node:alpine
# Create app directory
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY package.json /usr/src/app/
RUN yarn install && yarn cache clean
COPY . /usr/src/app
CMD [ "node", "websocket-server.js" ]

EXPOSE 3002
