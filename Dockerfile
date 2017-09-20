FROM java

LABEL maintainer "Jeff Sippel <jsippel@acorns.com>"

RUN apt-get update
RUN apt-get install -y ruby ruby-nokogiri

RUN adduser -u 9000 --disabled-password --quiet --gecos "" app
USER app

WORKDIR /usr/src/app

COPY scalastyle_config.xml /usr/src/app/
COPY scalastyle_2.11-0.6.0-batch.jar /usr/src/app/

COPY . /usr/src/app

VOLUME /code
WORKDIR /code

CMD ["/usr/src/app/bin/scalastyle"]
