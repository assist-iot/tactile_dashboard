# Build the application for production
FROM node:12.22.1-alpine AS builder

WORKDIR /app
COPY . .
RUN npm install && npm run build

# Build an nginx-based image
FROM nginx:1.22.0-alpine

WORKDIR /usr/share/nginx/html
RUN rm -rf ./*
COPY --from=builder /app/dist ./assistiot

COPY ./docker/nginx/templates /etc/nginx/templates

# Dashboard back container name & port.
# Default back-dashboard-svc & 8080
ENV DASHBOARD_HOST_NAME=back-dashboard-svc
ENV DASHBOARD_HOST_PORT=8080

RUN envsubst '${DASHBOARD_HOST_NAME} ${DASHBOARD_HOST_PORT} ${ENABLE_AUTH_IDM}' < /etc/nginx/templates/default.conf.template > /etc/nginx/conf.d/default.conf
CMD ["nginx", "-g", "daemon off;"]
