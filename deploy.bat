
call kubectl apply -f .\k8s\configmap.yaml
call kubectl apply -f .\k8s\deployment.yaml
call kubectl apply -f .\k8s\service.yaml
call kubectl apply -f .\k8s\daemon-set.yaml
call kubectl apply -f .\k8s\cron-job.yaml

call kubectl apply -f .\k8s\gateway.yaml
call kubectl apply -f .\k8s\peer-authentication.yaml
call kubectl apply -f .\k8s\virtual-service.yaml
call kubectl apply -f .\k8s\destination-rules.yaml

call kubectl apply -f prometheus.yaml
call kubectl apply -f service-monitor.yaml
