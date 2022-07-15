## Comand to create the Mongo container
```bash
docker run -d -p 27017:27017 -p 28017:28017 -e AUTH=no tutum/mongodb
```