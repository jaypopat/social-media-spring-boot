# Use node to build the app
FROM node:18 AS build
WORKDIR /app
COPY . .
RUN npm install && npm run build

# Use nginx to serve the frontend
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html

# Expose port
EXPOSE 80
