# dashboard

## Compiles and hot-reloads for development from local node installation

```
docker compose up postgres dashboard -d
npm install
npm run serve

```

## Compiles and hot-reloads for development from environment with docker compose

```bash
docker compose up
```

## Compiles and minifies for production

```
npm run build
```

### Lints and fixes files

```
npm run lint
```

### Customize configuration

See [Configuration Reference](https://cli.vuejs.org/config/).


## Docker

Build image

```bash
docker build -t pui9_dashboard-client:latest -f ./docker/Dockerfile .
```
Run container
```bash
docker run -d --name pui9_dashboard-client-container -p 80:80 pui9_dashboard-client:latest
```

