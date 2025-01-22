# docker/Dockerfile

FROM postgres:13
ENV POSTGRES_DB weatherdb
ENV POSTGRES_USER yourusername
ENV POSTGRES_PASSWORD yourpassword

COPY docker-entrypoint-initdb.d /docker-entrypoint-initdb.d
