![InfoMail-logo](https://imgur.com/EyQPoMx.png)

# InfoMail

## About the project

> InfoMail is an email service that allows you:
> 1) send emails to one or more addresses (include CC and BCC);
> 2) schedule a message to be sent on a date;
> 3) resend emails at specified intervals;
> 4) creating and sharing templates

## Features

1) JWT-token authentication
2) Two-factor authentication
3) Docker compose  with all services

### Project links

* [infomail](https://infomail-frontend.herokuapp.com/) - InfoMail site
* [swagger-ui](https://infomail-backend.herokuapp.com/swagger-ui/index.html?configUrl=/api/v1/api-docs/swagger-config) - API
* [infomail-frontend](https://github.com/Lastzlo/Infomail-frontend) - Front-end part of the project

## Start the project locally

### Required to install

* Java 17
* PostgreSQL (9.5.9 or higher)
* Docker
* MailHog in Docker or a Gmail account that allows you to use SMTP

### Installing

1) Clone this repository to your local machine using:

```shell
git clone https://github.com/Lastzlo/InfoMail.git
```

2) Create a database
3) Set up environmental variables in `application.yml` or in Intellij
   Idea (`Edit Configurations... -> Environmental variables:`)

```properties example
INFOMAIL_DEFAULT_TIMEZONE=Europe/Kiev;
INFOMAIL_FRONT_REG_LINK=http://localhost:4200/#/auth/registration/;
INFOMAIL_FRONT_URL=http://localhost:4200;
INFOMAIL_SECURITY_SECRET=onfn923oiNkfJj32fsl230cdl3mcLNL42lfl932O23Fknlfsdlsf32f04f;
MAIL_EMAIL=infomail;
MAIL_HOST=localhost;
MAIL_PASSWORD=infomail;
MAIL_PORT=1025;
SPRING_DATASOURCE_PASSWORD=postgrespw;
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/infomail;
SPRING_DATASOURCE_USERNAME=postgres
```
