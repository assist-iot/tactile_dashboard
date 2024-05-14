## Docker
Build image

```bash
docker build -t pui9_dashboard-back:latest -f ./docker/Dockerfile .
```
Run container
```bash
docker run -d --name pui9_dashboard-back-container -p 8080:8080 pui9_dashboard-back:latest
```