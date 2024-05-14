# Deploying Tactile dashboard Helm chart

1. Create a Secret to access Assist-iot's private container registry
```bash
kubectl create secret docker-registry regcred --docker-server=https://gitlab.assist-iot.eu:5050/ --docker-username=<your-name> --docker-password=<your-pword/token> --docker-email=<your-email>
```
2. Deploy dependencies
  - LTSE: https://gitlab.assist-iot.eu/wp4/data-mgmt/ltse
  - Business KPI enabler: https://gitlab.assist-iot.eu/wp4/applications/business-kpi-enabler/-/tree/main/

3. Deploy helm chart
```bash
helm install dashboard-pui9 .
```

Other commands

```bash
# Get all release
helm list

#Delete release
helm uninstall <name-release>

#Delete all release
helm ls --all --short | xargs -L1 helm delete

```

### Reference Links
- https://stackoverflow.com/questions/47817818/helm-delete-all-releases
- https://stackoverflow.com/questions/42564058/how-to-use-local-docker-images-with-minikube
- For initialization db & secrets. https://github.com/helm/charts/tree/master/stable/postgresql
