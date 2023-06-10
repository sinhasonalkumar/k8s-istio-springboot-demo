
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts

helm repo update

helm install prometheus prometheus-community/prometheus

k get all

k get service

k edit svc prometheus-server -> update service type from ClusterIP to LoadBalancer



helm repo add grafana https://grafana.github.io/helm-charts

helm repo update

helm install grafana grafana/grafana

k edit svc grafana  -> update service type from ClusterIP to LoadBalancer

k get secret --namespace default grafana -o jsonpath="{.data.admin-password}" | base64 --decode ; echo


---


helm repo add kedacore https://kedacore.github.io/charts
helm repo update
k create namespace keda
helm install keda kedacore/keda --namespace keda

k get pods -n keda


---
Sample promQL

http_server_requests_seconds_count{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/v1/products",} 

sum(rate(http_server_requests_seconds_count{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/v1/products",} [2m]))

rate(http_server_requests_seconds_count{uri="/v1/products"}[5m])

sum(rate(http_server_requests_seconds_count[5m]))

rate(http_server_requests_seconds_count[5m])
