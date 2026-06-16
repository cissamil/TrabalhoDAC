FROM node:20-alpine as builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .

RUN npm run build 

FROM nginx:alpine

COPY --from=builder /app/dist/sistema-bancario/browser /usr/share/nginx/html

# Expõe a porta 80 do container
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]