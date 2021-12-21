# build environment
FROM node:14.17.5-alpine3.14 as build
WORKDIR /app
RUN npm install -g npm@8.0.0
ENV PATH /app/node_modules/.bin:$PATH
COPY package.json ./
COPY package-lock.json ./
RUN npm ci -silent
COPY . ./
RUN npm run build

# production environment
FROM nginx:1.20.0-alpine
COPY --chown=nginx --from=build /app/build /usr/share/nginx/html
COPY --chown=nginx ./nginx-custom.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]