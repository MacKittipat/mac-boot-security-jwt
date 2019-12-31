# mac-boot-security-jwt

Experiment spring boot with JWT

## Get JWT token
```
curl --location --request POST 'http://localhost:8080/api/authenticate' \
--header 'Content-Type: application/json' \
--data-raw '{
	"username":"admin",
	"password":"password"
}'

curl --location --request POST 'http://localhost:8080/api/authenticate' \
--header 'Content-Type: application/json' \
--data-raw '{
	"username":"mac",
	"password":"password"
}'
```

## Call API
```
curl --location --request GET 'http://localhost:8080/admin' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlIjpbIlJPTEVfQURNSU4iXSwiZXhwIjoxNTc4NDE3Nzg4LCJpYXQiOjE1Nzc4MTI5ODh9.3sdmWWPTDyyKQSbFVKC2O8N87Reo-ZYbvOvTCBGP-lHn9X9YhO6Ed7jffErpTtYV2q2wceKxk6yfpERucyonIA'

curl --location --request GET 'http://localhost:8080/user' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInNjb3BlIjpbIlJPTEVfQURNSU4iXSwiZXhwIjoxNTc4NDE3Nzg4LCJpYXQiOjE1Nzc4MTI5ODh9.3sdmWWPTDyyKQSbFVKC2O8N87Reo-ZYbvOvTCBGP-lHn9X9YhO6Ed7jffErpTtYV2q2wceKxk6yfpERucyonIA'
```
