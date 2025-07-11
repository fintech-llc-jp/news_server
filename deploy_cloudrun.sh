#!/bin/bash

# Cloud Run デプロイスクリプト
set -e

# 設定変数
PROJECT_ID="bitcoinnewscraper"
SERVICE_NAME="news-server"
REGION="asia-northeast1"
IMAGE_NAME="gcr.io/${PROJECT_ID}/${SERVICE_NAME}"

echo "=== Cloud Run デプロイ開始 ==="

# プロジェクトIDを設定
echo "プロジェクトID設定: ${PROJECT_ID}"
gcloud config set project ${PROJECT_ID}

# Docker認証を有効化
echo "Docker認証設定"
gcloud auth configure-docker

# Dockerイメージをビルド（マルチアーキテクチャ対応）
echo "Dockerイメージビルド中..."
docker buildx build --platform linux/amd64 -t ${IMAGE_NAME}:latest .

# Container Registryにプッシュ
echo "Container Registryにプッシュ中..."
docker push ${IMAGE_NAME}:latest

# Cloud Runにデプロイ
echo "Cloud Runにデプロイ中..."
gcloud run deploy ${SERVICE_NAME} \
  --image ${IMAGE_NAME}:latest \
  --platform managed \
  --region ${REGION} \
  --allow-unauthenticated \
  --port 8080 \
  --memory 1Gi \
  --cpu 1 \
  --timeout 300 \
  --max-instances 10 \
  --set-env-vars="SPRING_PROFILES_ACTIVE=prod"

# デプロイ完了
echo "=== デプロイ完了 ==="
echo "サービスURL:"
gcloud run services describe ${SERVICE_NAME} \
  --region ${REGION} \
  --format "value(status.url)"

echo ""
echo "ログ確認:"
echo "gcloud logs read --service=${SERVICE_NAME} --limit=50"