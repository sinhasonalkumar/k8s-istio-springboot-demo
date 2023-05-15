
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts

helm repo update

helm install prometheus prometheus-community/prometheus

kubectl get all

kubectl get service

k edit svc prometheus-server -> update service type from ClusterIP to LoadBalancer



helm repo add grafana https://grafana.github.io/helm-charts

helm repo update

helm install grafana grafana/grafana

k edit svc grafana  -> update service type from ClusterIP to LoadBalancer

kubectl get secret --namespace default grafana -o jsonpath="{.data.admin-password}" | base64 --decode ; echo


---
Sample promQL

http_server_requests_seconds_count{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/v1/products",} 

sum(rate(http_server_requests_seconds_count{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/v1/products",} [2m]))

rate(http_server_requests_seconds_count{uri="/v1/products"}[5m])

sum(rate(http_server_requests_seconds_count[5m]))

rate(http_server_requests_seconds_count[5m])
