
call kubectl apply -f .\k8s\configmap.yaml
call kubectl apply -f .\k8s\deployment.yaml
call kubectl apply -f .\k8s\service.yaml
call kubectl apply -f .\k8s\daemon-set.yaml
call kubectl apply -f .\k8s\cron-job.yaml
