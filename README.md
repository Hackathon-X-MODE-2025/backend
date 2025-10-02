# 🚀 Deployment Guide for Hackathon-X-MODE-2025

Архитектура системы выполнена в **микросервисном стиле** и предназначена для работы в Kubernetes-кластере.  
Данный документ описывает шаги для воспроизведения и развертывания системы.

---

## 1. Подготовка инфраструктуры
- Установить и настроить Kubernetes-кластер:  
  - [kubeadm](https://kubernetes.io/docs/setup/production-environment/tools/kubeadm/)  
  - [kind](https://kind.sigs.k8s.io/)  
  - [minikube](https://minikube.sigs.k8s.io/)  
  - или управляемый кластер (**GKE**, **EKS**, **AKS**).
- Установить [Helm](https://helm.sh/) (пакетный менеджер для Kubernetes).
- Настроить доступ к кластеру:
  ```bash
  kubectl config use-context <your-cluster>
  ```

---

## 2. Установка зависимостей
Установите сторонние сервисы через Helm Charts:

- [bitnami/airflow](https://artifacthub.io/packages/helm/bitnami/airflow)  
- [bitnami/spark](https://artifacthub.io/packages/helm/bitnami/spark)  
- [bitnami/postgresql](https://artifacthub.io/packages/helm/bitnami/postgresql)  
- [bitnami/clickhouse](https://artifacthub.io/packages/helm/bitnami/clickhouse)  
- [pfisterer/hadoop](https://pfisterer.github.io/helm-charts/)  
- [ingress-nginx](https://kubernetes.github.io/ingress-nginx/)  
- [cert-manager](https://cert-manager.io/) (для AutoCerts + Let's Encrypt)  

---

## 3. Подготовка Docker-образов
1. Соберите Docker-образы для [микросервисов](https://github.com/Hackathon-X-MODE-2025/backend) проекта.  
2. Запушьте их в контейнерный репозиторий (Docker Hub, GitHub Container Registry, Nexus, Harbor и т.д.).  
3. Убедитесь, что кластер имеет доступ к репозиторию (при необходимости добавьте `imagePullSecrets`).  

---

## 4. Создание Namespace
```bash
kubectl create namespace hackathon-x-mode
kubectl config set-context --current --namespace=hackathon-x-mode
```

---

## 5. Настройка секретов и конфигураций
1. Перейдите в [infrastructure](https://github.com/Hackathon-X-MODE-2025/infrastructure).  
2. Заполните `values.yaml` или создайте свои override-файлы:  
   - доступы к БД,  
   - креды к внешним API,  
   - пути до хранилищ,  
   - настройки TLS.  
3. Деплой:
   ```bash
   helm upgrade --install infrastructure ./infrastructure -n hackathon-x-mode
   ```

---

## 6. Деплой микросервисов
Helm Charts для сервисов находятся в [репозитории](https://github.com/Hackathon-X-MODE-2025/_helm).

Для каждого сервиса выполните:
```bash
helm upgrade --install <service-name> ./_helm/<service-name> -n hackathon-x-mode
```

---

## 7. Настройка маршрутизации и SSL
1. Создайте Ingress-ресурсы для сервисов.  
2. Настройте `cert-manager` для автоматического получения и продления SSL-сертификатов от Let's Encrypt:
   ```bash
   kubectl apply -f cluster-issuer.yaml
   ```

---
