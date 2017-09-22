FROM openjdk:alpine

LABEL maintainer "Jeff Sippel <jsippel@acorns.com>"

RUN apk update && \
  apk upgrade && \
  apk add --no-cache ruby ruby-nokogiri ruby-json

RUN addgroup -g 9000 -S code && \
  adduser -S -G code app
USER app

COPY . /usr/src/app

WORKDIR /code
VOLUME /code

CMD ["/usr/src/app/bin/scalastyle"]
