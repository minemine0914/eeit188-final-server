# 使用官方 NGINX 基礎映像
FROM nginx:latest

# 將自定義的 NGINX 配置文件複製到容器內
COPY config/default.conf /etc/nginx/conf.d/default.conf
COPY config/api.key /etc/nginx/conf.d/api.key
COPY config/api.pem /etc/nginx/conf.d/api.pem

# 開放 NGINX 端口
EXPOSE 80