# TelrosTestWebapp
docker run -d --hostname TelrosDB --name TelrosDB -p 5400:5432 -e POSTGRES_USER=usr -e POSTGRES_PASSWORD=pwd -e POSTGRES_DB=telrosdb -v C:\telros:/var/lib/postgresql/data --restart=unless-stopped postgres:14.5


админ - admin:admin
юзер - inav812:ivan812
